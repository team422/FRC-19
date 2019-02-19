package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.cscore.UsbCamera;
// import edu.wpi.cscore.VideoSink;
// import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;
// import edu.wpi.first.wpilibj.command.Command;
// import edu.wpi.first.wpilibj.command.CommandGroup;
// import frc.robot.commands.cargo.*;
// import frc.robot.commands.climber.*;
// import frc.robot.commands.hatch.*;
import frc.robot.commands.other.*;
// import frc.robot.commandgroups.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;

public class Robot extends TimedRobot {

    // private NetworkTableEntry blockX;
    // private NetworkTableEntry blockY;
    // private NetworkTableEntry blockW;
    // private NetworkTableEntry blockH;
    // private NetworkTableEntry blockArea;
    private NetworkTableEntry lineX0;
    private NetworkTableEntry lineX1;
    private NetworkTableEntry lineY0;
    private NetworkTableEntry lineY1;

    private UsbCamera camera;
    /**
     * Camera Toggling Variables (Dont work yet)
     */
    // private UsbCamera camera1;
    // private UsbCamera camera2;
    // public NetworkTableInstance inst2;
    // public NetworkTableInstance inst3;
    // public NetworkTable camera;
    // public VideoSink server;
    // public boolean isCamera1;

    public Robot() {
        super(0.08);
    }

    public void robotInit() {
        System.out.println("Initializing " + RobotMap.botName + "\n");

        camera = CameraServer.getInstance().startAutomaticCapture();
        /**
         * Camera Toggling initialization
         */
        // isCamera1 = true;
        // camera1 = CameraServer.getInstance().startAutomaticCapture(1);
        // camera2 = CameraServer.getInstance().startAutomaticCapture(2);
        // server = CameraServer.getInstance().getServer();
        // camera1.setConnectionStrategy(VideoSource.ConnectionStrategy.kForceClose);
        // camera2.setConnectionStrategy(VideoSource.ConnectionStrategy.kForceClose);
        // inst2 = NetworkTableInstance.getDefault();
        // inst3 = NetworkTableInstance.getDefault();
        // NetworkTable camera = inst2.getTable("");
        
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable pixy = inst.getTable("pixy");
        // blockX = pixy.getEntry("blockX");
        // blockY = pixy.getEntry("blockY");
        // blockW = pixy.getEntry("blockW");
        // blockH = pixy.getEntry("blockH");
        // blockArea = pixy.getEntry("blockArea");
        lineX0 = pixy.getEntry("lineX0");
        lineX1 = pixy.getEntry("lineX1");
        lineY0 = pixy.getEntry("lineY0");
        lineY1 = pixy.getEntry("lineY1");

        Subsystems.driveBase.cheesyDrive.setSafetyEnabled(false);

        /**
         * Sets Drivebase speed to fast as default and sets RB on driverController 
         * to toggle the speed when pressed 
         */
        RobotMap.isFastMode = true;
        RobotMap.setSpeedAndRotationCaps(1, 0.35);
        UserInterface.driverController.RB.whenPressed(new ToggleSpeed());

        /**
         * Makes sure  holdPivot & cargoIn booleans are false at the initialization of the bot.
         */
        RobotMap.holdPivot = false;
        RobotMap.cargoIn = false;
    }

    public void disabledInit() {
        System.out.println("Disabled Initialized");
        Scheduler.getInstance().removeAll();
        
        /**
         * This makes sure that all of the motors are set to 0% following disable
         */
        Subsystems.cargo.stopPivot();
        Subsystems.cargo.stopIntakeMotors();
        Subsystems.cargo.stopEscalatorMotors();
    }

    public void disabledPeriodic() {        
        printDataToSmartDashboard();
    }
    

    public void autonomousInit() {
        System.out.println("Autonomous Initalized");
        
        /**
         * This makes sure that any old commands/command groups are stopped upon Autonomous Initialization.
         */
        Scheduler.getInstance().removeAll();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        printDataToSmartDashboard();
    }


    public void teleopInit() {
        System.out.println("TeleOp Initalized");
        /**
         * This makes sure that all of the motors are set to 0% upon TeleOp Initialization.
         */
        Subsystems.cargo.stopPivot();
        Subsystems.cargo.stopIntakeMotors();
        Subsystems.cargo.stopEscalatorMotors();

        /**
         * This makes sure that the bot is set to normal speed and rotation caps upon TeleOp Initialization.
         */
        RobotMap.isFastMode = true;
        RobotMap.setSpeedAndRotationCaps(1, 0.35);

        /**
         * Turns holdPivot & cargoIn booleans to false at TeleOp Initialization.
         */
        RobotMap.holdPivot = false;
        RobotMap.cargoIn = false;

        /**
         * This makes sure that any old commands/command groups are stopped upon TeleOp Initialization.
         */
        Scheduler.getInstance().removeAll();
    }
    
    public void teleopPeriodic() {
        /**
         * This makes sure that TankDrive and other Commands used during TeleOp are run.
         */
        Scheduler.getInstance().run();

        printDataToSmartDashboard();

        /**
         * Unused Buttons
         */
        if(UserInterface.operatorController.START.get()) { }
        if(UserInterface.operatorController.BACK.get()) { }
        if(UserInterface.operatorController.getPOVAngle() == 90) { 

        } else if(UserInterface.operatorController.getPOVAngle() == 180) { 

        } else if(UserInterface.operatorController.getPOVAngle() == 270) { }
        if(UserInterface.operatorController.getRightJoystickY() > 0.1) {

        } else if (UserInterface.operatorController.getRightJoystickY() < -0.1) { 

        } else { }
        if(UserInterface.operatorController.getRightJoystickX() > 0.1) {

        } else if (UserInterface.operatorController.getRightJoystickX() < -0.1) { 
            
        } else { }
        if(UserInterface.operatorController.getLeftJoystickX() > 0.1) {

        } else if (UserInterface.operatorController.getLeftJoystickX() < -0.1) { 
            
        } else { }

        /**
         * Hatch Buttons
         */
        if(UserInterface.operatorController.RB.get()) {
            Subsystems.hatch.hatchClamp();
        }
        if(UserInterface.operatorController.LB.get()) {
            Subsystems.hatch.hatchRelease();
        }
        if(UserInterface.operatorController.A.get()) {
            Subsystems.hatch.armIn();
        }
        if(UserInterface.operatorController.X.get()) {
            Subsystems.hatch.armOut();
        }

        /**
         * Cargo Buttons
         */
        if(UserInterface.operatorController.B.get()) {
            Subsystems.cargo.setFlapDown();
        }     
        if(UserInterface.operatorController.Y.get()) {
            Subsystems.cargo.setFlapUp();
        }
        if(UserInterface.operatorController.getPOVAngle() == 0) {
            RobotMap.holdPivot = true;
        }
        if(RobotMap.holdPivot) {
            Subsystems.cargo.holdPivotIntakeUp();
        } else {
            Subsystems.cargo.stopPivot();
        }
        RobotMap.cargoIn = Subsystems.cargo.getIntakeBeamBroken();
        if(UserInterface.operatorController.getLeftTrigger() > 0.1) {
            Subsystems.cargo.setEscalatorMotors(-1);
            RobotMap.cargoIn = false;
        }
        if(UserInterface.operatorController.getRightTrigger() > 0.1) {
            if(!Subsystems.cargo.getEscalatorBeamBroken()) {
                Subsystems.cargo.setEscalatorMotors(-1);    
                if (RobotMap.cargoIn) {
                    Subsystems.cargo.stopIntakeMotors();
                } else {
                    Subsystems.cargo.setIntakeMotors(-0.75);
                }
            } else {
                Subsystems.cargo.stopIntakeMotors();
                Subsystems.cargo.stopEscalatorMotors();
            }
        } 
        if(!(UserInterface.operatorController.getLeftTrigger() > 0.1) && !(UserInterface.operatorController.getRightTrigger() > 0.1)) {
            Subsystems.cargo.stopIntakeMotors();
            Subsystems.cargo.stopEscalatorMotors();
        }
        if(UserInterface.operatorController.getLeftJoystickY() > 0.1) {
            // pivots down
            Subsystems.cargo.pivotIntake(UserInterface.operatorController.getLeftJoystickY() * 0.2, Direction.Down);
            RobotMap.holdPivot = false;
        } else if(UserInterface.operatorController.getLeftJoystickY() < -0.1) {
            // pivots up
            Subsystems.cargo.pivotIntake(UserInterface.operatorController.getLeftJoystickY() * 0.3, Direction.Up);
            RobotMap.holdPivot = false;
        }
        
    }

    private void printDataToSmartDashboard() {
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
        SmartDashboard.putBoolean("isFastMode", RobotMap.isFastMode);
        SmartDashboard.putBoolean("holdPivot", RobotMap.holdPivot);
        SmartDashboard.putBoolean("cargoIn", RobotMap.cargoIn);
        SmartDashboard.putNumber("SpeedCap", RobotMap.speedCap);
        SmartDashboard.putNumber("RotationCap", RobotMap.rotationCap);
    }
}