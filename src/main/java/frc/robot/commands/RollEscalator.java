package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class RollEscalator extends Command {
    public RollEscalator() {
        super("RollEscalator");
        requires(Subsystems.cargo);
        setTimeout(5);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        Subsystems.cargo.setEscalatorMotors(1.0);
    }

    @Override
    public boolean isFinished() {
        return isTimedOut();
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