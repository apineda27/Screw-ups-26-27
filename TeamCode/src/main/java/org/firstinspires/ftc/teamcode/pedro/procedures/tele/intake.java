package org.firstinspires.ftc.teamcode.pedro.procedures.tele;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class intake extends LinearOpMode {
    private DcMotor bombo;

    @Override
    public void runOpMode() throws InterruptedException {
        bombo = hardwareMap.get(DcMotor.class,"intake");
        bombo.setDirection(DcMotor.Direction.REVERSE);
        waitForStart();
        while (opModeIsActive()){
            bombo.setPower(.5);
            telemetry.addData("stuff",bombo.getPower());
        }
    }
}