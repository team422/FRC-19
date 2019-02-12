package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.DigitalInput;

public class Climber extends Subsystem {
    
    private SpeedControllerGroup backClimb;
    private SpeedControllerGroup frontClimb;
    private WPI_TalonSRX leftFrontClimb;
    private WPI_TalonSRX leftBackClimb;
    private WPI_TalonSRX rightFrontClimb;
    private WPI_TalonSRX rightBackClimb;
    public DigitalInput ultrasonic;

    public Climber() {
        super("Climber"); 
        this.leftFrontClimb = new WPI_TalonSRX(RobotMap.leftFrontClimb);
        this.leftBackClimb = new WPI_TalonSRX(RobotMap.leftBackClimb);
        this.rightFrontClimb = new WPI_TalonSRX(RobotMap.rightFrontClimb);
        this.rightBackClimb = new WPI_TalonSRX(RobotMap.rightBackClimb);
        // this.backClimb = new SpeedControllerGroup(leftBackClimb, rightBackClimb);
        // this.frontClimb = new SpeedControllerGroup(leftFrontClimb, rightFrontClimb);
        this.frontClimb = new SpeedControllerGroup(rightFrontClimb);
        this.backClimb = new SpeedControllerGroup(leftFrontClimb);
        this.ultrasonic = new DigitalInput(RobotMap.intakeUltrasonic);
    }

    public void initDefaultCommand() {}

    public void setBackClimbMotors(double speed) {
        backClimb.set(speed);
    }

    public void setFrontClimbMotors(double speed) {
        frontClimb.set(speed);
    }

    public void setFrontRightMotors(double speed) {
        rightFrontClimb.set(ControlMode.PercentOutput, speed);
    }

    public void setFrontLeftMotors(double speed) {
        leftFrontClimb.set(ControlMode.PercentOutput, speed);
    }

    public boolean getUltrasonic() {
        return ultrasonic.get();
    }

    public void zeroFrontClimbEncoders() {
        leftFrontClimb.setSelectedSensorPosition(0,0,10);
        rightFrontClimb.setSelectedSensorPosition(0,0,10);        
    }

    public void zeroBackClimbEncoders() {
        leftBackClimb.setSelectedSensorPosition(0,0,10);
        rightBackClimb.setSelectedSensorPosition(0,0,10);
    }

    public int getFrontClimbMotors() {
        return leftFrontClimb.getSelectedSensorPosition(0);
    }

    public int getBackClimbMotors() {
        return rightBackClimb.getSelectedSensorPosition(0);
    }


}