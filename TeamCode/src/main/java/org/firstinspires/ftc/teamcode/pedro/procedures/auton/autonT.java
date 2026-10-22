package org.firstinspires.ftc.teamcode.pedro.procedures.auton;

import static com.pedropathing.api.Paths.*;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.procedures.pedroPathing.Constants;
@Autonomous
public class autonT extends OpMode {

    private Follower follower;
    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose start = poseFactory.of(58, 11, 90);
    private final Pose point1 = poseFactory.of(58, 35, 90);
    private final Pose point2 = poseFactory.of(83, 35, 90);
    private final Pose point3 = poseFactory.of(35, 35, 90);

    public Path path1() {
        return Paths.line(start, point1).linear(start, point1);
    }
    public Path path2() {
        return Paths.line(point1, point2).linear(point1, point2);
    }
    public Path path3() {
        return Paths.line(point2, point3).linear(point2, point3);
    }
    public Path path4() {
        return Paths.line(point3, point1).linear(point3, point1);
    }
    private Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                follow(follower, path2()),
                follow(follower, path3()),
                follow(follower, path4())
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
        schedule(follow(follower, path1()));
        schedule(autoRoutine());
    }
    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
    }
}