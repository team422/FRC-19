package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.TankDrive;
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
        this.leftMasterMotor = new WPI_TalonSRX(RobotMap.leftMiddleMaster); 
        this.rightMasterMotor = new WPI_TalonSRX(RobotMap.rightFrontFollower);
        this.leftFollower1 = new WPI_VictorSPX(RobotMap.leftFrontFollower);
        this.leftFollower2 = new WPI_VictorSPX(RobotMap.leftRearFollower);
        this.rightFollower1 = new WPI_VictorSPX(RobotMap.rightFrontFollower);
        this.rightFollower2 = new WPI_VictorSPX(RobotMap.rightRearFollower);
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