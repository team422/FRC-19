package main.java.frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import main.java.frc.robot.subsystems.Subsystems;

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
        return false;
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