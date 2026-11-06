package org.firstinspires.ftc.teamcode.pedro.procedures.auton;
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
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.deadline;
import static com.pedropathing.ivy.groups.Groups.race;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.pedropathing.ivy.Command;
import org.firstinspires.ftc.teamcode.pedro.procedures.subsystems.Shooter;

@Autonomous
public class rotation {
    private rotation rotation;
    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56, 8, 0);
    private final Pose path1 = poseFactory.of(58, 116, 90);
    private final Pose path1Control1 = poseFactory.of(0, 76.8932, 0);

    private Path rotate(){ return curve(start, path1Control1, path1);}

    private Command autoRoutine() {
        return sequential(
                follow(follower, rotate())
                /*follow(follower, shoot1())
                race(shooter.shoot(),
                        waitMs(1500))*/
        );
    }
    @Override
    public void init() {
        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();

        /*shooter = new Shooter(hardwareMap);*/
    }
    @Override
    public void start() {
        schedule(autoRoutine());
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
    }
}
