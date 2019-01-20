package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class FlapDown extends Command {

    public FlapDown() {
        super("FlapDown");
        requires(Subsystems.cargo);
        setTimeout(4);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        Subsystems.cargo.setFlapDown();
    }

    @Override
    public boolean isFinished() {
        return isTimedOut();
    }

    @Override
    public void interrupted() {
       
    }

    @Override
    public void end() {
       
    }
}