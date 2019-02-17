package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class ParallelEscalator extends Command {
    public ParallelEscalator() {
        super("ParallelEscalator");
        requires(Subsystems.cargo);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        Subsystems.cargo.setEscalatorMotors(-0.75);
    }

    @Override
    public boolean isFinished() {
        return Subsystems.cargo.getBeamBrakeValue();
    }

    @Override
    public void interrupted() {
        Subsystems.cargo.setEscalatorMotors(0);
    }

    @Override
    public void end() {
        Subsystems.cargo.setEscalatorMotors(0);
    }

}