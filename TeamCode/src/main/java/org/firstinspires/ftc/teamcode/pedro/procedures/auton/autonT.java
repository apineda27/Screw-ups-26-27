package org.firstinspires.ftc.teamcode.pedro.procedures.auton;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous
public class autonT extends OpMode {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(58.4964, 11.1869, 90);
    private final Pose path1 = poseFactory.of(58.6258, 35.4165, 90);
    private final Pose point2Start = poseFactory.of(58.6258, 35.4165, 90);
    private final Pose point2 = poseFactory.of(82.8289, 35.5154, 90);
    private final Pose autonTStart = poseFactory.of(82.8289, 35.5154, 90);
    private final Pose autonT = poseFactory.of(35.7866, 35.5465, 90);

    public Path path1() {
        return Paths.line(start, path1).linear(start, path1);
    }

    public Path path2() {
        return Paths.line(point2Start, point2).linear(point2Start, point2);
    }

    public Path autonT() {
        return Paths.line(autonTStart, autonT).linear(autonTStart, autonT);
    }
    @Override
    public void init() {

    }
    @Override
    public void start() {

    }
    @Override
    public void loop() {

    }
}