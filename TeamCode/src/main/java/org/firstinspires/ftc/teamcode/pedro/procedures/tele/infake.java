package org.firstinspires.ftc.teamcode.pedro.procedures.tele;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class infake extends LinearOpMode {
    private DcMotor rounder;

    @Override
    public void runOpMode() throws InterruptedException {
        rounder = hardwareMap.get(DcMotor.class,"intake");
        rounder.setDirection(DcMotor.Direction.REVERSE);
        waitForStart();
        while (opModeIsActive()){
            rounder.setPower(.5);
            telemetry.addData("stuff",rounder.getPower());
        }
    }
}