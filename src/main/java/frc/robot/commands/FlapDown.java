package main.java.frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import main.java.frc.robot.subsystems.Subsystems;

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
        return false;
    }

    @Override
    public void interrupted() {
       
    }

    @Override
    public void end() {
       
    }
}