package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.RobotMap;

public class Hatch extends Subsystem {

    private DoubleSolenoid arm;
    private DoubleSolenoid hatch;

    public Hatch() {
        super("Hatch");
        this.arm = new DoubleSolenoid(0, RobotMap.hatchArmOut, RobotMap.hatchArmIn);
        this.hatch = new DoubleSolenoid(0, RobotMap.hatchClamp, RobotMap.hatchRelease);
    }

    @Override
    public void initDefaultCommand() {}
    
    public void hatchRelease() {
        hatch.set(DoubleSolenoid.Value.kReverse);
    }

    public void hatchClamp() {
        hatch.set(DoubleSolenoid.Value.kForward);
    }

    public void armOut() {
        arm.set(DoubleSolenoid.Value.kForward);
    }

    public void armIn() {
        arm.set(DoubleSolenoid.Value.kReverse);
    }

    public boolean isHatchClamped() {
        return (hatch.get() == DoubleSolenoid.Value.kForward);
    }
}