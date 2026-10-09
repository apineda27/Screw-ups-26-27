package org.firstinspires.ftc.teamcode.pedro.procedures.pedroPathing;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("frontL");
        c.frontRightName.set("frontR");
        c.backLeftName.set("backL");
        c.backRightName.set("backR");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });
    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-3.351003391536202);
        c.yPodOffset.set(0.6917246120182549);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });
    public static ForesightConfig foresightConfig = new ForesightConfig( c -> {
        Controller primaryTranslationalForward = Controller.proportional(0.1930650745996322);
        Controller secondaryTranslationalForward = Controller.proportional(0.07133238627075994);
        Controller primaryTranslationalLateral = Controller.proportional(0.21116910151793472);
        Controller secondaryTranslationalLateral = Controller.proportional(0.07802134046856413);
        c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
        c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));
        c.coast.set(Controller.proportionalFeedforward(0.012627575275271072));
        c.brake.set(Controller.proportionalFeedforward(0.01073343898398041));
        c.headingFeedback.set(Controller.proportional(2.5965570731395298));
        c.headingBrakeCoefficients.set(Vector2D.cartesian(0.04439873135990511, 0.006164936085926514));
        c.linearBrakeCoefficients.set(Matrix.diag(0.06448242833735605, 0.04238309534810886));
        c.quadraticBrakeCoefficients.set(Matrix.diag(0.0010996466211500755, 0.0018951223873482472));
        c.maxAchievableForwardVelocity.set(80.90722989531832); c.maxAchievableStrafeVelocity.set(60.92765786931792);
        c.naturalForwardDeceleration.set(43.3374567653378); c.naturalStrafeDeceleration.set(65.7009035880893); } );
}