package org.firstinspires.ftc.teamcode.pedro.procedures.subsystems;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    public static double kP = 0.0;
    public static double kI = 0.0;
    public static double kD = 0.0;
    public static double kF = 0.0;

    public static double TICKS_PER_REV = 28.0;
    public static double TARGET_RPM = 1000;

    private final DcMotorEx shooter;

    public Shooter(HardwareMap hardwareMap) {
        shooter = hardwareMap.get(DcMotorEx.class,"shooter");
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter.setVelocityPIDFCoefficients(kP, kI, kD, kF);
    }

    public static double targetTicksPerSec() {
        return (TARGET_RPM * TICKS_PER_REV) / 60.0;
    }

    public Command shoot() {
        return Command.build()
                .setStart(() -> shooter.setVelocity(targetTicksPerSec()))
                .setEnd(endCondition -> shooter.setVelocity(0))
                .requiring(this);
    }
}
