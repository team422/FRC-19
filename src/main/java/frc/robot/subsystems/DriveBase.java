package frc.robot.subsystems;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.TankDrive;
import edu.wpi.first.wpilibj.SpeedController;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SPI;


public class DriveBase extends Subsystem {

    public WPI_TalonSRX leftMiddleMaster;
    public WPI_TalonSRX rightMiddleMaster;
    public SpeedController leftFrontFollower;
    public SpeedController leftRearFollower;
    public SpeedController rightFrontFollower;
    public SpeedController rightRearFollower;        
    public ADXRS450_Gyro gyro;
    private SpeedControllerGroup leftSide;
    private SpeedControllerGroup rightSide;
    public DifferentialDrive cheesyDrive; 
    private static final SPI.Port kGyroPort = SPI.Port.kOnboardCS0;

    public DriveBase() {
        super("DriveBase");
        this.leftMiddleMaster = new WPI_TalonSRX(RobotMap.leftMiddleMaster); 
        this.rightMiddleMaster = new WPI_TalonSRX(RobotMap.rightMiddleMaster);
        if (RobotMap.isCompBot) {
            this.leftFrontFollower = new WPI_VictorSPX(RobotMap.leftFrontFollower);
            this.leftRearFollower = new WPI_VictorSPX(RobotMap.leftRearFollower);
            this.rightFrontFollower = new WPI_VictorSPX(RobotMap.rightFrontFollower);
            this.rightRearFollower = new WPI_VictorSPX(RobotMap.rightRearFollower);
        } else {
            this.leftFrontFollower = new WPI_TalonSRX(RobotMap.leftFrontFollower);
            this.leftRearFollower = new WPI_TalonSRX(RobotMap.leftRearFollower);
            this.rightFrontFollower = new WPI_TalonSRX(RobotMap.rightFrontFollower);
            this.rightRearFollower = new WPI_TalonSRX(RobotMap.rightRearFollower);
        }
        leftMiddleMaster.setInverted(true);
        leftFrontFollower.setInverted(true);
        leftRearFollower.setInverted(true);

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