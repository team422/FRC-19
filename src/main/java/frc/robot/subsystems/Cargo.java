package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class Cargo extends Subsystem {   

    private WPI_TalonSRX intakePivot;
    private SpeedController intakeWheels;
    private SpeedController escalator; 
    private DigitalInput cargoPivotLimitSwitch;
    private DigitalInput cargoEscalatorUltrasonic; 
    private DoubleSolenoid flap; 

    public Cargo(){
        super("Cargo");
        this.intakePivot = new WPI_TalonSRX(RobotMap.cargoIntakePivot);
        if(RobotMap.isCompBot) {
            this.intakeWheels = new WPI_VictorSPX(RobotMap.cargoIntakeWheels);
            this.escalator = new WPI_VictorSPX(RobotMap.cargoEscalatorWheels);
        } else {
            this.intakeWheels = new WPI_TalonSRX(RobotMap.cargoIntakeWheels);
            this.escalator = new WPI_TalonSRX(RobotMap.cargoEscalatorWheels);
        }
        this.cargoPivotLimitSwitch = new DigitalInput(RobotMap.cargoPivotLimitSwitch);
        this.cargoEscalatorUltrasonic = new DigitalInput(RobotMap.cargoEscalatorUltrasonic);
        this.flap = new DoubleSolenoid(RobotMap.cargoFlapUp, RobotMap.cargoFlapDown);
    }

    protected void initDefaultCommand() {} 

    public boolean getLimitSwitchValue() {
        return !cargoPivotLimitSwitch.get();
    }

    public boolean getBeamBrakeValue() {
        return !cargoEscalatorUltrasonic.get();      
    }
    
    public void setEscalatorMotors(double power) {
        escalator.set(power);
    }
    
    public void setIntakeMotors(double power) {
        intakeWheels.set(power);
    }

    public void pivotIntake(double power) {
        intakePivot.set(ControlMode.PercentOutput, power);
    }

    //if we get encoders on the cargo pivot
    // public double getIntakePivot() {
    //     return intakePivot.getSelectedSensorPosition(0);
    // }
        
    public void setFlapUp() {
        flap.set(DoubleSolenoid.Value.kForward);
    }

    public void setFlapDown() {
        flap.set(DoubleSolenoid.Value.kReverse);
    }
    
}