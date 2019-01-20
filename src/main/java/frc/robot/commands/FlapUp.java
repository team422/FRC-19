package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class FlapUp extends Command {

    public FlapUp() {
        super("FlapUp");
        requires(Subsystems.cargo);
        setTimeout(4);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        Subsystems.cargo.setFlapUp();
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