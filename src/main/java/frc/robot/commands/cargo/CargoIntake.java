package frc.robot.commands.cargo;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class CargoIntake extends Command {
    public CargoIntake() {
        super("CargoIntake");
        requires(Subsystems.cargo);
        setTimeout(3);
    }

    @Override
    public void initialize() { }

    @Override
    public void execute() {
        Subsystems.cargo.setIntakeMotors(0.75);
    }
    
    @Override
    public boolean isFinished() {
        return (Subsystems.cargo.getBeamBrakeValue() || isTimedOut());
    }

    @Override
    public void interrupted() {
        Subsystems.cargo.stopIntakeMotors();
    }
    
    @Override
    public void end() {
        Subsystems.cargo.stopIntakeMotors();
    }
    
}
