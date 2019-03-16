package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
//import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Climber extends Subsystem {
    
    // public DigitalInput climberBeamBreak;
    private DoubleSolenoid frontClimb;
    private DoubleSolenoid backClimb;

    public Climber() {
        super("Climber");
        this.frontClimb = new DoubleSolenoid(1, RobotMap.frontClimberUp, RobotMap.frontClimberDown);
        this.backClimb = new DoubleSolenoid(1, RobotMap.backClimberUp, RobotMap.backClimberDown);
        // this.climberBeamBreak = new DigitalInput(RobotMap.climberBeamBreak);
    }

    public void initDefaultCommand() {}

    public void frontClimbExtend() {
        frontClimb.set(DoubleSolenoid.Value.kForward);
    }

    public void frontClimbRetract() {
        frontClimb.set(DoubleSolenoid.Value.kReverse);
        backClimb.set(DoubleSolenoid.Value.kReverse);
    }

    public void backClimbExtend() {
        backClimb.set(DoubleSolenoid.Value.kForward);
    }

    public void backClimbRetract() {
        backClimb.set(DoubleSolenoid.Value.kReverse);
    }

    public boolean isFrontExtended() {
        return (frontClimb.get() == DoubleSolenoid.Value.kForward);
    }

    public boolean isBackExtended() {
        return (backClimb.get() == DoubleSolenoid.Value.kReverse);
    }
    
    // public boolean getBeamBreak() {
    //    return climberBeamBreak.get();
    // }
}