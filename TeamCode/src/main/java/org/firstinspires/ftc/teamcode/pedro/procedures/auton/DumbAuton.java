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
import static com.pedropathing.ivy.groups.Groups.deadline;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.pedropathing.ivy.Command;
import org.firstinspires.ftc.teamcode.pedro.procedures.subsystems.Intake;
import org.firstinspires.ftc.teamcode.pedro.procedures.subsystems.Shooter;

@Autonomous
public class DumbAuton extends OpMode {
    private Follower follower;
    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose start = poseFactory.of(56, 9.5, 90);
    private final Pose score = poseFactory.of(56, 15, 90);
    private final Pose park = poseFactory.of(14, 92, 90);
    private final Pose control1 = poseFactory.of(13, 56, 90);
    private final Pose control2 = poseFactory.of(36, 60, 45);

    private Path park() {
        return curve(start, control1, park).linear(start, park);

    }
    private Command autoRoutine() {
        return sequential(
                deadline(follow(follower, park()))
        );
    }

    @Override
    public void init() {
        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();

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
