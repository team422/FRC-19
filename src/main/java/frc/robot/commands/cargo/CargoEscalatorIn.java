package frc.robot.commands.cargo;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class CargoEscalatorIn extends Command {
    public CargoEscalatorIn() {
        super("CargoEscalatorIn");
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
        Subsystems.cargo.stopEscalatorMotors();
    }

    @Override
    public void end() {
        Subsystems.cargo.stopEscalatorMotors();
    }

}