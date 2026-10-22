package org.firstinspires.ftc.teamcode.pedro.procedures.flywheel;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
public class TESTY extends LinearOpMode {
    private DcMotorEx Fella;


    @Override
    public void runOpMode() throws InterruptedException {
        Fella = hardwareMap.get(DcMotorEx.class,"bang");
        Fella.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while (opModeIsActive()){
            Fella.setVelocity(4000);
            telemetry.addData("stuff",Fella.getVelocity());
        }
    }
}
