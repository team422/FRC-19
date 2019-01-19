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
    
    @Override
    protected void initDefaultCommand() {}    

    private WPI_VictorSPX intakeWheels;
    private WPI_VictorSPX intakePivot; 
    private WPI_VictorSPX escalator1; 
    private WPI_VictorSPX escalator2;
    private DigitalInput limitSwitch;
    private AnalogInput ultrasonicSensor; 
    private DoubleSolenoid directorFlap; 

    public Cargo(){
        super("Cargo");
        this.intakeWheels = new WPI_VictorSPX(RobotMap.cargoIntakeWheels); 
        this.intakePivot = new WPI_VictorSPX(RobotMap.cargoIntakePivot);
        this.escalator1 = new WPI_VictorSPX(RobotMap.cargoEscalator1);
        this.escalator2 = new WPI_VictorSPX(RobotMap.cargoEscalator2); 
        this.limitSwitch = new DigitalInput(RobotMap.cargoLimitSwitch);
        this.ultrasonicSensor = new AnalogInput(RobotMap.cargoUltrasonicSensor);
        this.directorFlap = new DoubleSolenoid(RobotMap.cargoDirectorFlapPush, RobotMap.cargoDirectorFlapPull);
    }

    public boolean getLimitSwitchValue() {
        return !limitSwitch.get();
    }

    public double getBeamBrakeValue() {
        return ultrasonicSensor.getAverageVoltage();
    }
    
    public void setEscalatorMotors() {
        escalator1.set(ControlMode.PercentOutput, 0.50);
        escalator2.set(ControlMode.PercentOutput, 0.50);
    }
    
    public void setIntakeMotors() {
        intakeWheels.set(ControlMode.PercentOutput, 0.50);
    }

    public void pivotIntakeDown() {
        intakePivot.set(ControlMode.PercentOutput, -0.50);
    }

    public void pivotIntakeUp() {
        intakePivot.set(ControlMode.PercentOutput, 0.50);
    }

    public void setFlapUp() {
        directorFlap.set(DoubleSolenoid.Value.kForward);
    }

    public void setFlapDown() {
        directorFlap.set(DoubleSolenoid.Value.kReverse);
    }
    
    
}