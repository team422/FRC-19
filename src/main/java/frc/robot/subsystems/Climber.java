package main.java.frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import main.java.frc.robot.RobotMap;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.AnalogInput;

public class Climber extends Subsystem {
    
    private SpeedControllerGroup backClimb;
    private SpeedControllerGroup frontClimb;
    private WPI_TalonSRX leftFrontClimb;
    private WPI_TalonSRX leftBackClimb;
    private WPI_TalonSRX rightFrontClimb;
    private WPI_TalonSRX rightBackClimb;
    private AnalogInput ultrasonic;

    public Climber() {
        super("Climber"); 
        this.leftFrontClimb = new WPI_TalonSRX(RobotMap.leftFrontClimb);
        this.leftBackClimb = new WPI_TalonSRX(RobotMap.leftBackClimb);
        this.rightFrontClimb = new WPI_TalonSRX(RobotMap.rightFrontClimb);
        this.rightBackClimb = new WPI_TalonSRX(RobotMap.rightBackClimb);
        this.backClimb = new SpeedControllerGroup(leftBackClimb, rightBackClimb);
        this.frontClimb = new SpeedControllerGroup(leftFrontClimb, rightFrontClimb);
        this.ultrasonic = new AnalogInput(RobotMap.intakeUltrasonic);
    }

    public void initDefaultCommand() {}

    public void setBackClimbMotors(double back) {
        backClimb.set(back);
    }

    public void setFrontClimbMotors(double front) {
        backClimb.set(front);
    }

    public double getUltrasonicDistance() {
        return ultrasonic.getAverageVoltage();
    }

}