package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.commandgroups.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;

public class Robot extends TimedRobot {

    private UsbCamera camera1;
    private UsbCamera camera2;
    
    private NetworkTableEntry blockX;
    private NetworkTableEntry blockY;
    private NetworkTableEntry blockW;
    private NetworkTableEntry blockH;
    private NetworkTableEntry blockArea;
    private NetworkTableEntry lineX0;
    private NetworkTableEntry lineX1;
    private NetworkTableEntry lineY0;
    private NetworkTableEntry lineY1;

    private String botName;
    // private Command TrackObject;
    // private Command ComplicatedTrackLine;
    // private Command TrackLine;
    // private Command TankDrive;
    // private Command DriveStraight;
    // private CommandGroup ParallelTurnBetter;

    NetworkTableInstance inst2;
    NetworkTableInstance inst3;
    NetworkTable camera;

     private CommandGroup CargoIntake;
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
    // private Command PunchInwards; 
    // private Command PunchOutwards;
    // private Command RollBallIntake;
    // private Command RollEscalator;

    private double slope = 0;
    public VideoSink server;

    public Robot() {
        super(0.08);
    }

    public void robotInit() {

        botName = (RobotMap.isCompBot) ? "Meridian" : "Hot Take";
        System.out.println("Initializing " + botName + "\n");

        camera1 = CameraServer.getInstance().startAutomaticCapture(1);
        camera2 = CameraServer.getInstance().startAutomaticCapture(2);
        server = CameraServer.getInstance().getServer();
        camera1.setConnectionStrategy(VideoSource.ConnectionStrategy.kForceClose);
        camera2.setConnectionStrategy(VideoSource.ConnectionStrategy.kForceClose);
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        inst2 = NetworkTableInstance.getDefault();
        inst3 = NetworkTableInstance.getDefault();
        NetworkTable pixy = inst.getTable("pixy");
        NetworkTable camera = inst2.getTable("");
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

        RobotMap.isToggledFast = true;
        RobotMap.setSpeedAndRotationCaps(1, 0.4);
        UserInterface.driverController.RB.whenPressed(new ToggleSpeed());

        // TrackObject = new TrackObject();
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
        System.out.println("Disabled Initialized");
        Scheduler.getInstance().removeAll();
        
        /**
         * This makes sure that all of the motors are set to 0% following disable
         */
        Subsystems.cargo.pivotIntake(0);
        Subsystems.cargo.setIntakeMotors(0);
        Subsystems.cargo.setEscalatorMotors(0);
    }

    public void disabledPeriodic() {
        System.out.println("Bot Disabled");
        
        printDataToSmartDashboard();
    }
    

    public void autonomousInit() {
        System.out.println("Autonomous Initalized");
        
        /**
         * This makes sure that any old commands/command groups are stopped upon Autonomous Initialization.
         */
        Scheduler.getInstance().removeAll();

        //TankDrive.start();
        //TrackLine.start();
        //DriveStraight.start();
        //ComplicatedTrackLine.start();
        //ParallelTurnBetter.start();
    }

    public void autonomousPeriodic() {
        System.out.println("Bot in Autonomous");
        Scheduler.getInstance().run();
        printDataToSmartDashboard();
    }


    public void teleopInit() {
        System.out.println("TeleOp Initalized");
        /**
         * This makes sure that all of the motors are set to 0% upon TeleOp Initialization.
         */
        Subsystems.cargo.pivotIntake(0);
        Subsystems.cargo.setIntakeMotors(0);
        Subsystems.cargo.setEscalatorMotors(0);

        /**
         * This makes sure that the bot is set to normal speed and rotation caps upon TeleOp Initialization.
         */
        RobotMap.isToggledFast = true;

        /**
         * This makes sure that any old commands/command groups are stopped upon TeleOp Initialization.
         */
        Scheduler.getInstance().removeAll();
    }
    
    public void teleopPeriodic() {
        System.out.println("Bot in TeleOp");
        /**
         * This makes sure that TankDrive and other Commands used during TeleOp are run.
         */
        Scheduler.getInstance().run();

        printDataToSmartDashboard();
        // if(Subsystems.cargo.getBeamBrakeValue()) {
        //     System.out.println("TRIGGERED");
        // }
        if (UserInterface.driverController.LB.get()) {
            server.setSource(camera1);
        } else if (UserInterface.driverController.A.get()) {
            server.setSource(camera2);
        }
        if(UserInterface.operatorController.START.get()) {
            //Subsystems.cargo.setEscalatorMotors(-1);
            Subsystems.cargo.setIntakeMotors(0.75);
        } else {
            //Subsystems.cargo.setEscalatorMotors(0);
            Subsystems.cargo.setIntakeMotors(0);
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
            Subsystems.hatch.punchInwards();
        }
        if(UserInterface.operatorController.B.get()) {
            Subsystems.cargo.setFlapDown();
        }
        if(UserInterface.operatorController.X.get()) {
            Subsystems.hatch.punchOutwards();
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
            Subsystems.cargo.setEscalatorMotors(-1);
        }
        if(UserInterface.operatorController.getRightTrigger() > 0.1) {
            if(!Subsystems.cargo.getBeamBrakeValue()) {
                Subsystems.cargo.setIntakeMotors(-0.75);
                Subsystems.cargo.setEscalatorMotors(-1);
            } else {
                Subsystems.cargo.setIntakeMotors(0);
                Subsystems.cargo.setEscalatorMotors(0);
            }
        } 
        if(!(UserInterface.operatorController.getLeftTrigger() > 0.1) && !(UserInterface.operatorController.getRightTrigger() > 0.1)) {
            Subsystems.cargo.setIntakeMotors(0);
            Subsystems.cargo.setEscalatorMotors(0);
        }
        //  else {
        //     Subsystems.cargo.setIntakeMotors(0);
        //     Subsystems.cargo.setEscalatorMotors(0);
        // }
        if(UserInterface.operatorController.getRightJoystickY() > 0.1) {
            //Subsystems.cargo.setIntakeMotors(UserInterface.operatorController.getRightJoystickY() * 0.75);
        } else if (UserInterface.operatorController.getRightJoystickY() < -0.1) {
            //Subsystems.cargo.setIntakeMotors(UserInterface.operatorController.getRightJoystickY() * 0.75);
        } else {
            //Subsystems.cargo.setIntakeMotors(0);
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
        SmartDashboard.putBoolean("isToggledFast", RobotMap.isToggledFast);
    }
}