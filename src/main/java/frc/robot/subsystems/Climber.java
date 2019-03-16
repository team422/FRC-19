package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
//import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Climber extends Subsystem {
    
    // public DigitalInput climberBeamBreak;
    private DoubleSolenoid closeClimb;
    private DoubleSolenoid farClimb;

    public Climber() {
        super("Climber");
        /**
         * closeClimb refers to the cargo intake side, which is the first to climb, and therefor closer
         * to the driver. farClimb refers to the hatch intake side.
         */
        this.closeClimb = new DoubleSolenoid(1, RobotMap.closeClimberExtend, RobotMap.closeClimberRetract);
        this.farClimb = new DoubleSolenoid(1, RobotMap.farClimberExtend, RobotMap.farClimberRetract);
        // this.climberBeamBreak = new DigitalInput(RobotMap.climberBeamBreak);
    }

    public void initDefaultCommand() {}

    public void closeClimbExtend() {
        closeClimb.set(DoubleSolenoid.Value.kForward);
    }

    public void closeClimbRetract() {
        closeClimb.set(DoubleSolenoid.Value.kReverse);
    }

    public void farClimbExtend() {
        farClimb.set(DoubleSolenoid.Value.kForward);
    }

    public void farClimbRetract() {
        farClimb.set(DoubleSolenoid.Value.kReverse);
    }

    public boolean isCloseExtended() {
        return (closeClimb.get() == DoubleSolenoid.Value.kForward);
    }

    public boolean isFarExtended() {
        return (farClimb.get() == DoubleSolenoid.Value.kReverse);
    }
    
    // public boolean getBeamBreak() {
    //    return climberBeamBreak.get();
    // }
}