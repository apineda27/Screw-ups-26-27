package org.firstinspires.ftc.teamcode.pedro.procedures.auton;
import com.pedropathing.api.Paths;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedro.procedures.pedroPathing.Constants;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import org.firstinspires.ftc.teamcode.pedro.procedures.pedroPathing.Constants;

@Autonomous
public class DumbAuton extends OpMode {
    private Follower follower;
    private DcMotorEx intake;
    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose start = poseFactory.of(56, 9.5, 90);
    private final Pose score = poseFactory.of(56, 15, 90);
    private final Pose park = poseFactory.of(14, 92, 90);
    private final Pose control1 = poseFactory.of(13, 56, 90);
    private final Pose control2 = poseFactory.of(36, 60, 45);

    private Path park() {
        return curve(start, control1, park);

    }
    private Command runIntake() {
        return Command.build()
                .setStart(() -> intake.setVelocity(ShooterConstants.targetTicksPerSec()))
                .setEnd(end -> intake.setVelocity(0));
    }
    private Command autoRoutine() {
        return sequential(
                follow(follower, park())

        );
    }

    @Override
    public void init() {
        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();

        DcMotorEx intake = hardwareMap.get(DcMotorEx.class, "intake");
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake.setVelocityPIDFCoefficients(
                ShooterConstants.kP, ShooterConstants.kI,
                ShooterConstants.kD, ShooterConstants.kF);
    }

    @Override
    public void start() {
        schedule(follow(follower, park()));
        schedule(autoRoutine());
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
    }
    public static class ShooterConstants {
        public static final double kP = 0.0; // from the tuner telemetry
        public static final double kI = 0.0;
        public static final double kD = 0.0;
        public static final double kF = 0.0;

        public static final double TICKS_PER_REV = 28.0; // same value you tuned with
        public static final double TARGET_RPM = 1000;

        public static double targetTicksPerSec() {
            return (TARGET_RPM * TICKS_PER_REV) / 60.0;
        }
    }
}
