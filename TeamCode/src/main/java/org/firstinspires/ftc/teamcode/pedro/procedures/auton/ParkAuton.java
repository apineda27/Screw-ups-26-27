package org.firstinspires.ftc.teamcode.pedro.procedures.auton;
import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.procedures.pedroPathing.Constants;

@Autonomous(name = "Park Auton")
public class ParkAuton extends OpMode {

    private Follower follower;
    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose start = poseFactory.of(56, 8, 90);
    private final Pose waypoint = poseFactory.of(136, 34, 0);
    private final Pose park = poseFactory.of(57, 38, 0);

    private Path toWaypoint() {
        return line(start, waypoint).linear(start, waypoint);
    }

    private Path toPark() {
        return line(waypoint, park).linear(waypoint, park);
    }

    private Command autoRoutine() {
        return sequential(
                follow(follower, toWaypoint()),
                follow(follower, toPark())
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