package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;
import edu.wpi.first.wpilibj.XboxController;

public class Robot extends TimedRobot {

    private UsbCamera camera;
    private NetworkTableEntry blockX;
    private NetworkTableEntry blockY;
    private NetworkTableEntry blockW;
    private NetworkTableEntry blockH;
    private NetworkTableEntry blockArea;
    private NetworkTableEntry lineX0;
    private NetworkTableEntry lineX1;
    private NetworkTableEntry lineY0;
    private NetworkTableEntry lineY1;
    private Command TrackObject;
    private Command ComplicatedTrackLine;
    private Command TrackLine;
    private Command TankDrive;
    private Command DriveStraight;
    private CommandGroup ParallelTurnBetter;
    private double slope = 0;

    public Robot() {
        super(0.08);
    }

    public void robotInit() {
        System.out.println("Initializing Hot Take");
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable pixy = inst.getTable("pixy");
        blockX = pixy.getEntry("blockX");
        blockY = pixy.getEntry("blockY");
        blockW = pixy.getEntry("blockW");
        blockH = pixy.getEntry("blockH");
        lineX0 = pixy.getEntry("lineX0");
        lineX1 = pixy.getEntry("lineX1");
        lineY0 = pixy.getEntry("lineY0");
        lineY1 = pixy.getEntry("lineY1");
        blockArea = pixy.getEntry("blockArea");
        //TrackObject = new TrackObject();
        ParallelTurnBetter = new ParallelTurnBetter();
        TrackLine = new TrackLine();
        ComplicatedTrackLine = new ComplicatedTrackLine();
        TankDrive = new TankDrive();
        DriveStraight = new DriveStraight(10000,-0.1,30);    
        Subsystems.driveBase.cheesyDrive.setSafetyEnabled(false);
        // Subsystems.driveBase.leftMiddleMaster.setInverted(true);
        // Subsystems.driveBase.leftFrontFollower.setInverted(true);
        // Subsystems.driveBase.leftRearFollower.setInverted(true);
        Subsystems.driveBase.gyro.reset();
        Subsystems.driveBase.gyro.calibrate();
    }

    public void disabledInit() {
        Scheduler.getInstance().removeAll();
    }

    public void autonomousInit() {
        Scheduler.getInstance().removeAll();
        //TankDrive.start();
        //TrackLine.start();
        //DriveStraight.start();
        //ComplicatedTrackLine.start();
        ParallelTurnBetter.start();
    }

    public void teleopInit() {
        System.out.println("This print statement works");
        Scheduler.getInstance().removeAll();
    }

    public void disabledPeriodic() {
        printDataToSmartDashboard();
    }
    
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        printDataToSmartDashboard();
    }

    public void teleopPeriodic() {
        //System.out.println("Teleoping periodically");
        Scheduler.getInstance().run();
        printDataToSmartDashboard();
        if(UserInterface.driverController.A.get()) {
            Subsystems.driveBase.setMotors(0.08,0.08);
        }
    }

    private void printDataToSmartDashboard() {
        SmartDashboard.putNumber("blockX", blockX.getDouble(-404));
        SmartDashboard.putNumber("blockY", blockY.getDouble(-404));
        SmartDashboard.putNumber("blockW", blockW.getDouble(-404));
        SmartDashboard.putNumber("blockH", blockH.getDouble(-404));
        SmartDashboard.putNumber("blockArea", blockArea.getDouble(-404));
        SmartDashboard.putNumber("lineX0", lineX0.getDouble(-404));
        SmartDashboard.putNumber("lineX1", lineX1.getDouble(-404));
        SmartDashboard.putNumber("lineY0", lineY0.getDouble(-404));
        SmartDashboard.putNumber("lineY1", lineY1.getDouble(-404));
        SmartDashboard.putNumber("RightDrivePosition", Subsystems.driveBase.getRightPosition());
        SmartDashboard.putNumber("LeftDrivePosition", Subsystems.driveBase.getRightPosition());
        SmartDashboard.putNumber("Gyro angle", Subsystems.driveBase.getGyroAngle());
    }
}