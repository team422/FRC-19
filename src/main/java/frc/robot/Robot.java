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
import frc.robot.commands.commandgroups.*;
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
    // private Command TrackObject;
    // private Command ComplicatedTrackLine;
    // private Command TrackLine;
    // private Command TankDrive;
    // private Command DriveStraight;
    // private CommandGroup ParallelTurnBetter;

    // private CommandGroup CargoIntake;
    // private Command CargoPivotDown;
    // private CommandGroup CargoRocketOutake;
    // private CommandGroup CargoShipOutake;
    // private Command FlapDown;
    // private Command FlapUp;
    // private Command HatchClamp;
    // private Command HatchRelease;
    // private CommandGroup IntakeHatch;
    // private CommandGroup OutakeRocket;
    // private CommandGroup OutakeShip;
    // private Command ParallelEscalator;
    // private Command PunchInwards;  x  
    // private Command PunchOutwards;
    // private Command RollBallIntake;
    // private Command RollEscalator;

    private double slope = 0;

    public Robot() {
        super(0.08);
    }

    public void robotInit() {
        SmartDashboard.setDefaultString("Throttle Side", "Right");
        System.out.println("Initializing Hot Take");
        camera = CameraServer.getInstance().startAutomaticCapture();
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
        Subsystems.driveBase.cheesyDrive.setSafetyEnabled(false);

        //TrackObject = new TrackObject();
        // ParallelTurnBetter = new ParallelTurnBetter();
        // TrackLine = new TrackLine();
        // ComplicatedTrackLine = new ComplicatedTrackLine();
        // TankDrive = new TankDrive();
        // DriveStraight = new DriveStraight(10000,-0.1,30);    

        // CargoIntake = new CargoIntake(); 
        // CargoPivotDown = new CargoPivotDown(0.1,1);
        // CargoRocketOutake = new CargoRocketOutake();
        // CargoShipOutake = new CargoShipOutake();
        // FlapDown = new FlapDown();
        // FlapUp = new FlapUp();
        // HatchClamp = new HatchClamp();
        // HatchRelease = new HatchRelease();
        // IntakeHatch = new IntakeHatch();
        // OutakeRocket = new OutakeRocket();
        // OutakeShip = new OutakeShip();
        // ParallelEscalator = new ParallelEscalator();
        // PunchInwards = new PunchInwards();
        // PunchOutwards = new PunchOutwards();
        // RollBallIntake = new RollBallIntake();
        // RollEscalator = new RollEscalator();
        // UserInterface.operatorController.B.whenPressed(new HatchClamp());
        // Subsystems.driveBase.gyro.reset();
        // Subsystems.driveBase.gyro.calibrate();
    }

    public void disabledInit() {
        Scheduler.getInstance().removeAll();
        Subsystems.cargo.pivotIntake(0);
        Subsystems.cargo.setIntakeMotors(0);
        Subsystems.cargo.setEscalatorMotors(0);
    }

    public void autonomousInit() {
        Scheduler.getInstance().removeAll();
        //TankDrive.start();
        //TrackLine.start();
        //DriveStraight.start();
        //ComplicatedTrackLine.start();
        //ParallelTurnBetter.start();
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
        // Scheduler.getInstance().removeAll();//may be necessary
        Scheduler.getInstance().run();
        printDataToSmartDashboard();
        if(UserInterface.operatorController.START.get()) {

        }
        if(UserInterface.operatorController.BACK.get()) {

        }
        if(UserInterface.operatorController.RB.get()) {
            Subsystems.hatch.hatchClamp();
        }
        if(UserInterface.operatorController.LB.get()) {
            Subsystems.hatch.hatchRelease();
        }
        if(UserInterface.operatorController.A.get()) {
            Subsystems.hatch.punchOutwards();
        }
        if(UserInterface.operatorController.B.get()) {
            Subsystems.hatch.punchInwards();
        }
        if(UserInterface.operatorController.X.get()) {
            Subsystems.cargo.setFlapDown();
        }
        if(UserInterface.operatorController.Y.get()) {
            Subsystems.cargo.setFlapUp();
        }
        if(UserInterface.operatorController.getPOVAngle() == 0) {

        }
        if(UserInterface.operatorController.getPOVAngle() == 90) {

        }
        if(UserInterface.operatorController.getPOVAngle() == 180) {

        }
        if(UserInterface.operatorController.getPOVAngle() == 270) {

        }
        if(UserInterface.operatorController.getLeftTrigger() > 0.1) {
            Subsystems.cargo.setEscalatorMotors(-UserInterface.operatorController.getLeftTrigger());
        } else if(UserInterface.operatorController.getRightTrigger() > 0.1) {
            Subsystems.cargo.setEscalatorMotors(UserInterface.operatorController.getRightTrigger());
        } else {
            Subsystems.cargo.setEscalatorMotors(0);
        }
        if(UserInterface.operatorController.getRightJoystickY() > 0.1) {
            Subsystems.cargo.setIntakeMotors(UserInterface.operatorController.getRightJoystickY() * 0.75);
        } else if (UserInterface.operatorController.getRightJoystickY() < -0.1) {
            Subsystems.cargo.setIntakeMotors(UserInterface.operatorController.getRightJoystickY() * 0.75);
        } else {
            Subsystems.cargo.setIntakeMotors(0);
        }
        if(UserInterface.operatorController.getLeftJoystickY() < -0.1) {
            Subsystems.cargo.pivotIntake(UserInterface.operatorController.getLeftJoystickY() * 0.25);
        } else if (UserInterface.operatorController.getLeftJoystickY() > 0.1) {
            Subsystems.cargo.pivotIntake(UserInterface.operatorController.getLeftJoystickY() * 0.25);
        } else {
            Subsystems.cargo.pivotIntake(0);    
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
        SmartDashboard.putNumber("Hatch Left Angle", Subsystems.hatch.getLeftPosition());
        SmartDashboard.putNumber("Hatch Right Angle", Subsystems.hatch.getRightPosition());
        SmartDashboard.putNumber("POV Angle", UserInterface.operatorController.getPOVAngle());
        SmartDashboard.putBoolean("isLeftThrottle", RobotMap.isLeftThrottle);
    }
}