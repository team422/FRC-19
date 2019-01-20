package main.java.frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import main.java.frc.robot.RobotMap;

public class Hatch extends Subsystem {

    private Servo leftGrabber;
    private Servo rightGrabber;
    private DoubleSolenoid puncher;

    public Hatch() {
        super("Hatch");
        this.leftGrabber = new Servo(RobotMap.leftHatchGrabber);
        this.rightGrabber = new Servo (RobotMap.rightHatchGrabber);
        this.puncher = new DoubleSolenoid(RobotMap.punchingOutwards, RobotMap.punchingInwards);
    }

    @Override
    public void initDefaultCommand() {}

    public void hatchRelease() {
        leftGrabber.setAngle(0);
        rightGrabber.setAngle(0);
    }

    public void hatchClamp() {
        leftGrabber.setAngle(-90);
        rightGrabber.setAngle(90);
    }

    public void punchOutwards() {
      puncher.set(DoubleSolenoid.Value.kForward);
    }

    public void punchInwards() {
      puncher.set(DoubleSolenoid.Value.kReverse);
    }
}