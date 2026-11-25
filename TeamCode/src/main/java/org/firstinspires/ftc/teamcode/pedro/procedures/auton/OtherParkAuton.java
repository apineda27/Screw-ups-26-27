package org.firstinspires.ftc.teamcode.pedro.procedures.auton;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.pedro.procedures.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedro.procedures.subsystems.auton.Poses;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.pedropathing.ivy.Command;

@Autonomous(name = "BlueUPark")
public class OtherParkAuton extends OpMode {

    private Follower follower;
    private final Poses p = new Poses(true);

    private Path park() {
        return curve(p.startU, p.parkCU, p.parkU).linear(p.startU, p.parkU);
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
        follower.setPose(p.startU);
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