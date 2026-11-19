package org.firstinspires.ftc.teamcode.pedro.procedures.tele;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Config
@TeleOp(name = "Turret Aim Tuner", group = "Tuner")
public class TurretAimTuner extends LinearOpMode {

    public static double kP = 0.03;
    public static double deadbandDeg = 1.0;
    public static double maxPower = 0.35;
    public static double txOffsetDeg = 0.0;
    public static int goalId = -1;          // -1 = best tag; else require this id
    public static boolean reversed = false;

    @Override
    public void runOpMode() throws InterruptedException {
        Limelight3A limelight = hardwareMap.get(Limelight3A.class, "limelight");
        DcMotorEx turret = hardwareMap.get(DcMotorEx.class, "turret");

        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        limelight.pipelineSwitch(2);
        limelight.start();

        telemetry.addLine("Right stick: manual");
        telemetry.addLine("Hold A: track tag");
        telemetry.addLine("Dpad U/D: kP   L/R: offset");
        telemetry.addLine("Bumpers: max power   Y: flip direction");
        telemetry.update();
        waitForStart();

        boolean lastUp = false, lastDown = false, lastLeft = false, lastRight = false;
        boolean lastY = false, lastLeftBumper = false, lastRightBumper = false;

        while (opModeIsActive()) {
            if (gamepad1.dpad_up && !lastUp) kP += 0.005;
            if (gamepad1.dpad_down && !lastDown) kP = Math.max(0, kP - 0.005);
            if (gamepad1.dpad_right && !lastRight) txOffsetDeg += 0.5;
            if (gamepad1.dpad_left && !lastLeft) txOffsetDeg -= 0.5;
            if (gamepad1.right_bumper && !lastRightBumper) maxPower = Math.min(1, maxPower + 0.05);
            if (gamepad1.left_bumper && !lastLeftBumper) maxPower = Math.max(0, maxPower - 0.05);
            if (gamepad1.y && !lastY) reversed = !reversed;

            lastUp = gamepad1.dpad_up;
            lastDown = gamepad1.dpad_down;
            lastLeft = gamepad1.dpad_left;
            lastRight = gamepad1.dpad_right;
            lastY = gamepad1.y;
            lastLeftBumper = gamepad1.left_bumper;
            lastRightBumper = gamepad1.right_bumper;

            Double tx = readTx(limelight);
            double power = 0;
            String mode = "idle";

            if (gamepad1.a && tx != null) {
                double error = tx - txOffsetDeg;
                mode = Math.abs(error) <= deadbandDeg ? "locked" : "tracking";
                if (Math.abs(error) > deadbandDeg) {
                    power = Math.max(-maxPower, Math.min(maxPower, kP * error));
                }
            } else if (Math.abs(gamepad1.right_stick_x) > 0.05) {
                mode = "manual";
                power = gamepad1.right_stick_x * maxPower;
            } else if (gamepad1.a) {
                mode = "no target";
            }

            turret.setPower(reversed ? -power : power);

            telemetry.addData("mode", mode);
            telemetry.addData("tx (deg)", tx == null ? "none" : String.format("%.2f", tx));
            telemetry.addData("error (deg)", tx == null ? "none" : String.format("%.2f", tx - txOffsetDeg));
            telemetry.addData("power", "%.2f", power);
            telemetry.addData("kP", "%.3f", kP);
            telemetry.addData("offset (deg)", "%.1f", txOffsetDeg);
            telemetry.addData("deadband", "%.1f", deadbandDeg);
            telemetry.addData("maxPower", "%.2f", maxPower);
            telemetry.addData("reversed", reversed);
            telemetry.update();
        }

        turret.setPower(0);
        limelight.stop();
    }

    private Double readTx(Limelight3A limelight) {
        LLResult result = limelight.getLatestResult();
        if (result == null || !result.isValid()) return null;

        if (goalId < 0) return result.getTx();

        for (LLResultTypes.FiducialResult tag : result.getFiducialResults()) {
            if (tag.getFiducialId() == goalId) return tag.getTargetXDegrees();
        }
        return null;
    }
}
