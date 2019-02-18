package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.RobotMap;

public class Hatch extends Subsystem {

    private Servo leftGrabber;
    private Servo rightGrabber;
    private DoubleSolenoid arm;

    public Hatch() {
        super("Hatch");
        this.leftGrabber = new Servo(RobotMap.hatchLeftGrabber);
        this.rightGrabber = new Servo (RobotMap.hatchRightGrabber);
        this.arm = new DoubleSolenoid(RobotMap.hatchArmOut, RobotMap.hatchArmIn);
    }

    @Override
    public void initDefaultCommand() {}

    public void hatchRelease() {
        leftGrabber.setAngle(30);
        rightGrabber.setAngle(145);
    }

    public void hatchClamp() {
        leftGrabber.setAngle(150);
        rightGrabber.setAngle(15);
    }

    public void punchOutwards() {
      arm.set(DoubleSolenoid.Value.kForward);
    }

    public void punchInwards() {
        arm.set(DoubleSolenoid.Value.kReverse);
      }
    
    public double getLeftPosition() {
        return leftGrabber.get();
    }

    public double getRightPosition() {
        return rightGrabber.get();
    }
}