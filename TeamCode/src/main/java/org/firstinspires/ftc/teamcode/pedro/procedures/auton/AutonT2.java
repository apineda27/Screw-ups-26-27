package org.firstinspires.ftc.teamcode.pedro.procedures.auton;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
public class AutonT2 {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(58.4964, 11.1869, 90);
    private final Pose path1 = poseFactory.of(58.6258, 35.4165, 90);
    private final Pose point2Start = poseFactory.of(58.6258, 35.4165, 90);
    private final Pose point2 = poseFactory.of(82.8289, 35.5154, 90);
    private final Pose autonTStart = poseFactory.of(82.8289, 35.5154, 90);
    private final Pose autonT = poseFactory.of(33.0862, 35.2764, 90);
    private final Pose point4Start = poseFactory.of(33.0862, 35.2764, 90);
    private final Pose point4 = poseFactory.of(58.8607, 35.4857, 90);
    private final Pose point5 = poseFactory.of(58.4421, 11.3895, 89.0049);

    public Path path1() {
        return Paths.line(start, path1).linear(start, path1);
    }

    public Path path2() {
        return Paths.line(point2Start, point2).linear(point2Start, point2);
    }

    public Path autonT() {
        return Paths.line(autonTStart, autonT).linear(autonTStart, autonT);
    }

    public Path path4() {
        return Paths.line(point4Start, point4).linear(point4Start, point4);
    }

    public Path path5() {
        return Paths.line(point4, point5).reverseTangent();
    }
}
