package main.java.frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import main.java.frc.robot.RobotMap;
import main.java.frc.robot.commands.TankDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveBase extends Subsystem {

    private WPI_TalonSRX leftMasterMotor;
    private WPI_TalonSRX rightMasterMotor;
    private WPI_VictorSPX leftFollower1;
    private WPI_VictorSPX leftFollower2;
    private WPI_VictorSPX rightFollower1;
    private WPI_VictorSPX rightFollower2;        
    private ADXRS450_Gyro gyro;
    private SpeedControllerGroup leftSide;
    private SpeedControllerGroup rightSide;
    public DifferentialDrive cheesyDrive; 

    public DriveBase() {
        super("DriveBase");
        this.leftMasterMotor = new WPI_TalonSRX(RobotMap.leftMasterMotor); 
        this.rightMasterMotor = new WPI_TalonSRX(RobotMap.rightMasterMotor);
        this.leftFollower1 = new WPI_VictorSPX(RobotMap.leftFollower1);
        this.leftFollower2 = new WPI_VictorSPX(RobotMap.leftFollower2);
        this.rightFollower1 = new WPI_VictorSPX(RobotMap.rightFollower1);
        this.rightFollower2 = new WPI_VictorSPX(RobotMap.rightFollower2);
        this.gyro = new ADXRS450_Gyro();
        this.leftSide = new SpeedControllerGroup(leftMasterMotor, leftFollower1, leftFollower2);
        this.rightSide = new SpeedControllerGroup(rightMasterMotor, rightFollower1, rightFollower2);        
        this.cheesyDrive = new DifferentialDrive(leftSide, rightSide);
        leftMasterMotor.setInverted(true);
        leftFollower1.setInverted(true);
        leftFollower2.setInverted(true);
    }

    public void initDefaultCommand() {this.setDefaultCommand(new TankDrive());}

    public void setMotors(double left, double right) {
        leftSide.set(left);
        rightSide.set(right);
    }

    public int getLeftPosition() {
        return leftMasterMotor.getSelectedSensorPosition(0);
    }

    public int getRightPosition() {
        return rightMasterMotor.getSelectedSensorPosition(0);
    }

    public double getGyroAngle() {
        return gyro.getAngle();       
    }

    public void zeroEncoderPosition() {
        leftMasterMotor.setSelectedSensorPosition(0,0,10);
        rightMasterMotor.setSelectedSensorPosition(0,0,10);
    }

    public void zeroGyroAngle() {
        gyro.reset();
    }
}