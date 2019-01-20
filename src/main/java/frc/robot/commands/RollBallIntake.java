package main.java.frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import main.java.frc.robot.subsystems.Subsystems;

public class RollBallIntake extends Command {
    public RollBallIntake() {
        super("RollBallIntake");
        requires(Subsystems.cargo);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        Subsystems.cargo.setIntakeMotors(.5);
    }
    
    @Override
    public boolean isFinished() {
        return Subsystems.cargo.getBeamBrakeValue();
    }

    @Override
    public void interrupted() {
        Subsystems.cargo.setIntakeMotors(0);
    }
    
    @Override
    public void end() {
        Subsystems.cargo.setIntakeMotors(0);
    }
    
}
