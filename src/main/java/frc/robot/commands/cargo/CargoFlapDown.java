package frc.robot.commands.cargo;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class CargoFlapDown extends Command {

    public CargoFlapDown() {
        super("CargoFlapDown");
        requires(Subsystems.cargo);
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
        return true;
    }

    @Override
    public void interrupted() {
       
    }

    @Override
    public void end() {
       
    }
}