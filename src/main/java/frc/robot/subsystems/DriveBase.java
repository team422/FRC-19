package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.TankDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.SPI;

public class DriveBase extends Subsystem {

    public WPI_TalonSRX leftMiddleMaster;
    public WPI_TalonSRX rightMiddleMaster;
    public WPI_VictorSPX leftFrontFollower;
    public WPI_VictorSPX leftRearFollower;
    public WPI_VictorSPX rightFrontFollower;
    public WPI_VictorSPX rightRearFollower;//last 4 are victors on comp        
    public ADXRS450_Gyro gyro;
    private SpeedControllerGroup leftSide;
    private SpeedControllerGroup rightSide;
    public DifferentialDrive cheesyDrive; 
    private static final SPI.Port kGyroPort = SPI.Port.kOnboardCS0;

    public DriveBase() {
        super("DriveBase");
        this.leftMiddleMaster = new WPI_TalonSRX(RobotMap.leftMiddleMaster); 
        this.rightMiddleMaster = new WPI_TalonSRX(RobotMap.rightMiddleMaster);
        this.leftFrontFollower = new WPI_VictorSPX(RobotMap.leftFrontFollower);
        this.leftRearFollower = new WPI_VictorSPX(RobotMap.leftRearFollower);
        this.rightFrontFollower = new WPI_VictorSPX(RobotMap.rightFrontFollower);
        this.rightRearFollower = new WPI_VictorSPX(RobotMap.rightRearFollower);

        leftMiddleMaster.setInverted(true);
        leftFrontFollower.setInverted(true);
        leftRearFollower.setInverted(true);

        // leftMiddleMaster.setExpiration(1);
        // rightMiddleMaster.setExpiration(1);
        // leftFrontFollower.setExpiration(1);
        // leftRearFollower.setExpiration(1);
        // rightFrontFollower.setExpiration(1);
        // rightRearFollower.setExpiration(1);

        // leftMiddleMaster.setSafetyEnabled(false);
        // rightMiddleMaster.setSafetyEnabled(false);
        // leftFrontFollower.setSafetyEnabled(false);
        // leftRearFollower.setSafetyEnabled(false);
        // rightFrontFollower.setSafetyEnabled(false);
        // rightRearFollower.setSafetyEnabled(false);

        this.gyro = new ADXRS450_Gyro(kGyroPort);
        this.leftSide = new SpeedControllerGroup(leftMiddleMaster, leftFrontFollower, leftRearFollower);
        this.rightSide = new SpeedControllerGroup(rightMiddleMaster, rightFrontFollower, rightRearFollower);        
        this.cheesyDrive = new DifferentialDrive(leftSide, rightSide);
    }

    public void initDefaultCommand() {this.setDefaultCommand(new TankDrive());}

    public void setMotors(double left, double right) {
        leftSide.set(left);
        rightSide.set(right);
    }

    public int getLeftPosition() {
        return leftMiddleMaster.getSelectedSensorPosition(0);
    }

    public int getRightPosition() {
        return rightMiddleMaster.getSelectedSensorPosition(0);
    }

    public double getGyroAngle() {
        //sSystem.out.println(gyro.getAngle());
        return gyro.getAngle();       
    }

    public void zeroEncoderPosition() {
        leftMiddleMaster.setSelectedSensorPosition(0,0,10);
        rightMiddleMaster.setSelectedSensorPosition(0,0,10);
    }

    public void zeroGyroAngle() {
        gyro.reset();
    }
}