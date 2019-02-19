package frc.robot.commands.cargo;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class CargoEscalatorOut extends Command {
    public CargoEscalatorOut() {
        super("CargoEscalatorOut");
        requires(Subsystems.cargo);
        setTimeout(3);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        Subsystems.cargo.setEscalatorMotors(-1.0);
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