package main.java.frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import main.java.frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class Cargo extends Subsystem {   

    private WPI_VictorSPX intakeWheels;
    private WPI_VictorSPX intakePivot; 
    private WPI_VictorSPX escalator1; 
    private WPI_VictorSPX escalator2;
    private DigitalInput limitSwitch;
    private DigitalInput ultrasonicSensor; 
    private DoubleSolenoid directorFlap; 

    public Cargo(){
        super("Cargo");
        this.intakeWheels = new WPI_VictorSPX(RobotMap.cargoIntakeWheels); 
        this.intakePivot = new WPI_VictorSPX(RobotMap.cargoIntakePivot);
        this.escalator1 = new WPI_VictorSPX(RobotMap.cargoEscalator1);
        this.escalator2 = new WPI_VictorSPX(RobotMap.cargoEscalator2); 
        this.limitSwitch = new DigitalInput(RobotMap.cargoLimitSwitch);
        this.ultrasonicSensor = new DigitalInput(RobotMap.cargoUltrasonicSensor);
        this.directorFlap = new DoubleSolenoid(RobotMap.cargoDirectorFlapPush, RobotMap.cargoDirectorFlapPull);
    }

    protected void initDefaultCommand() {} 

    public boolean getLimitSwitchValue() {
        return !limitSwitch.get();
    }

    public boolean getBeamBrakeValue() {
        return ultrasonicSensor.get();      
    }
    
    public void setEscalatorMotors(double power) {
        escalator1.set(ControlMode.PercentOutput, power);
        escalator2.set(ControlMode.PercentOutput, power);
    }
    
    public void setIntakeMotors(double power) {
        intakeWheels.set(ControlMode.PercentOutput, power);
    }

    public void pivotIntakeDown(double power) {
        intakePivot.set(ControlMode.PercentOutput, -power);
    }

    public void pivotIntakeUp(double power) {
        intakePivot.set(ControlMode.PercentOutput, power);
    }
        
    public void setFlapUp() {
        directorFlap.set(DoubleSolenoid.Value.kForward);
    }

    public void setFlapDown() {
        directorFlap.set(DoubleSolenoid.Value.kReverse);
    }
    
}