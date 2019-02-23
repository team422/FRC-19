package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
// import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.Direction;

public class Cargo extends Subsystem {   

    private WPI_TalonSRX intakePivot;

    private WPI_VictorSPX intakeWheels;
    private WPI_VictorSPX escalator; 
    
    // private WPI_TalonSRX intakeWheels;
    // private WPI_TalonSRX escalator; 

    private DigitalInput cargoPivotUltrasonic;
    private DigitalInput cargoEscalatorUltrasonic;
    private DigitalInput cargoIntakeUltrasonic;  
    private DoubleSolenoid flap; 

    public Cargo(){
        super("Cargo");
        this.intakePivot = new WPI_TalonSRX(RobotMap.cargoIntakePivot);
        
        this.intakeWheels = new WPI_VictorSPX(RobotMap.cargoIntakeWheels);
        this.escalator = new WPI_VictorSPX(RobotMap.cargoEscalatorWheels);
        
        // this.intakeWheels = new WPI_TalonSRX(RobotMap.cargoIntakeWheels);
        // this.escalator = new WPI_TalonSRX(RobotMap.cargoEscalatorWheels);
        
        this.cargoPivotUltrasonic = new DigitalInput(RobotMap.cargoPivotUltrasonic);
        this.cargoEscalatorUltrasonic = new DigitalInput(RobotMap.cargoEscalatorUltrasonic);
        this.cargoIntakeUltrasonic = new DigitalInput(RobotMap.cargoIntakeUltrasonic);
        this.flap = new DoubleSolenoid(RobotMap.cargoFlapUp, RobotMap.cargoFlapDown);

    }

    protected void initDefaultCommand() {} 

    public boolean getPivotBeamBroken() {
        return !cargoPivotUltrasonic.get();
    }

    public boolean getIntakeBeamBroken() {
        return !cargoIntakeUltrasonic.get();      
    }

    public boolean getEscalatorBeamBroken() {
        return !cargoEscalatorUltrasonic.get();      
    }
    
    public void setEscalatorMotors(double power) {
        escalator.set(ControlMode.PercentOutput, power);
    }

    public void stopEscalatorMotors() {
        escalator.set(ControlMode.PercentOutput, 0.0);
    }
    
    public void setIntakeMotors(double power) {
        intakeWheels.set(ControlMode.PercentOutput, power);
    }

    public void stopIntakeMotors() {
        intakeWheels.set(ControlMode.PercentOutput, 0.0);
    }

    public void pivotIntake(double power, Direction direction) {
        if (direction == Direction.Up) {
            intakePivot.set(ControlMode.PercentOutput, power);
        } else if (direction == Direction.Down) {
            intakePivot.set(ControlMode.PercentOutput, -power);
        } else {
            intakePivot.set(ControlMode.PercentOutput, 0.0);
        }
    }

    public void stopPivot() {
        intakePivot.set(ControlMode.PercentOutput, 0.0);
    }

    public void holdPivotIntakeUp() {
        intakePivot.set(ControlMode.PercentOutput, 0.15);
    }

    public double pivotCurrent() {
        return intakePivot.getOutputCurrent();
    }


    public boolean isPivotCurrentTooHigh() {
        return (intakePivot.getOutputCurrent() > 19);
    }
        
    public void setFlapUp() {
        flap.set(DoubleSolenoid.Value.kForward);
    }

    public void setFlapDown() {
        flap.set(DoubleSolenoid.Value.kReverse);
    }
    
}