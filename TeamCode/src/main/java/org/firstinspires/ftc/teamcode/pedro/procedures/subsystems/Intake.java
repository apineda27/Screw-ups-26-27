package org.firstinspires.ftc.teamcode.pedro.procedures.subsystems;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    public static double kP = 0.0;
    public static double kI = 0.0;
    public static double kD = 0.0;
    public static double kF = 0.0;

    public static double TICKS_PER_REV = 28.0;
    public static double TARGET_RPM = 1000;

    private final DcMotorEx intake;

    public Intake(HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotorEx.class,"intake");
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake.setVelocityPIDFCoefficients(kP, kI, kD, kF);
    }

    public static double targetTicksPerSec() {
        return (TARGET_RPM * TICKS_PER_REV) / 60.0;
    }

    public Command pickup() {
        return Command.build()
                .setStart(() -> intake.setVelocity(targetTicksPerSec()))
                .setEnd(endCondition -> intake.setVelocity(0))
                .requiring(this);
    }
}
