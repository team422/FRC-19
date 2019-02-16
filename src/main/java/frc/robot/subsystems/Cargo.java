package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class Cargo extends Subsystem {   

    private WPI_VictorSPX intakeWheels;
    private WPI_TalonSRX intakePivot; 
    private WPI_VictorSPX escalator; 
    private DigitalInput limitSwitch;
    private DigitalInput ultrasonicSensor; 
    private DoubleSolenoid directorFlap; 

    public Cargo(){
        super("Cargo");
        this.intakeWheels = new WPI_VictorSPX(RobotMap.cargoIntakeWheels); 
        this.intakePivot = new WPI_TalonSRX(RobotMap.cargoIntakePivot);
        this.escalator = new WPI_VictorSPX(RobotMap.cargoEscalator);
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
        escalator.set(ControlMode.PercentOutput, power);
    }
    
    public void setIntakeMotors(double power) {
        intakeWheels.set(ControlMode.PercentOutput, power);
    }

    public void pivotIntake(double power) {
        intakePivot.set(ControlMode.PercentOutput, power);
    }

    //if we get encoders on the cargo pivot
    // public double getIntakePivot() {
    //     return intakePivot.getSelectedSensorPosition(0);
    // }
        
    public void setFlapUp() {
        directorFlap.set(DoubleSolenoid.Value.kForward);
    }

    public void setFlapDown() {
        directorFlap.set(DoubleSolenoid.Value.kReverse);
    }
    
}