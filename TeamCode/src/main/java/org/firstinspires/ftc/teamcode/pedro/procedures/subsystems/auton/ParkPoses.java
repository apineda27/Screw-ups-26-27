package org.firstinspires.ftc.teamcode.pedro.procedures.subsystems.auton;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;

public class ParkPoses {
    public final Pose startU;
    public final Pose parkU;
    public final Pose parkCU;
    public final Pose startD;
    public final Pose parkD;

    public ParkPoses(boolean mirror) {
        PoseFactory poseFactory = PoseFactory.degrees();
        if (mirror) {
            poseFactory = poseFactory.mirrorX(70.75).mirrorY(70.75);
        }

        startU = poseFactory.of(56, 9.5, 90);
        parkU = poseFactory.of(14, 92, 90);
        parkCU = poseFactory.of(13, 56, 90);
        startD = poseFactory.of(32, 132, 270);
        parkD = poseFactory.of(14, 120, 270);
    }
}
