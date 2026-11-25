package org.firstinspires.ftc.teamcode.pedro.procedures;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

/**
 * Push the robot by hand to check if Pinpoint encoder directions need reversing.
 *
 * Pedro / Pinpoint convention (with both pods set to FORWARD):
 *   - Push FORWARD  → X should increase. If X goes negative, reverse the X (forward) pod.
 *   - Push LEFT     → Y should increase. If Y goes negative, reverse the Y (strafe) pod.
 *
 * Press A to zero pose. Update Constants.localizerConfig xPodDirection / yPodDirection
 * with whatever this OpMode recommends.
 */
@TeleOp(name = "Pinpoint Direction Test", group = "Test")
public class PinpointDirectionTest extends LinearOpMode {

    private static final double DEADZONE_INCHES = 1.0;

    private GoBildaPinpointDriver pinpoint;

    @Override
    public void runOpMode() {
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        // Offsets do not matter for direction checks; both pods forced FORWARD.
        pinpoint.setOffsets(0, 0, DistanceUnit.INCH);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(
                GoBildaPinpointDriver.EncoderDirection.FORWARD,
                GoBildaPinpointDriver.EncoderDirection.FORWARD
        );
        pinpoint.resetPosAndIMU();

        telemetry.addLine("Pinpoint Direction Test (4-Bar)");
        telemetry.addLine("Both pods set to FORWARD.");
        telemetry.addLine("Wait for IMU, then press Start.");
        telemetry.update();

        waitForStart();

        pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0));

        while (opModeIsActive()) {
            if (gamepad1.a) {
                pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0));
            }

            pinpoint.update();
            Pose2D pose = pinpoint.getPosition();
            double x = pose.getX(DistanceUnit.INCH);
            double y = pose.getY(DistanceUnit.INCH);
            double heading = pose.getHeading(AngleUnit.DEGREES);

            telemetry.addLine("=== HOW TO TEST ===");
            telemetry.addLine("1. Push robot FORWARD  → X should go +");
            telemetry.addLine("2. Press A to zero");
            telemetry.addLine("3. Push robot LEFT     → Y should go +");
            telemetry.addLine("");

            telemetry.addData("X (forward)", "%.2f in", x);
            telemetry.addData("Y (strafe)", "%.2f in", y);
            telemetry.addData("Heading", "%.1f deg", heading);
            telemetry.addLine("");

            telemetry.addData("X pod", directionAdvice(x, "FORWARD", "xPodDirection"));
            telemetry.addData("Y pod", directionAdvice(y, "LEFT", "yPodDirection"));
            telemetry.addLine("");
            telemetry.addLine("Press A to zero pose");
            telemetry.update();
        }
    }

    private String directionAdvice(double reading, String expectedPositiveMove, String configField) {
        if (Math.abs(reading) < DEADZONE_INCHES) {
            return "push " + expectedPositiveMove + " first";
        }
        if (reading > 0) {
            return "OK — keep " + configField + " FORWARD";
        }
        return "REVERSE — set " + configField + " to REVERSED";
    }
}