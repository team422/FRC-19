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
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.hatch.*;
import frc.robot.commands.other.*;
import frc.robot.commandgroups.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;

public class Robot extends TimedRobot {

    // private NetworkTableEntry blockX;
    // private NetworkTableEntry blockY;
    // private NetworkTableEntry blockW;
    // private NetworkTableEntry blockH;
    // private NetworkTableEntry blockArea;
    private CommandGroup GaffTapeTrack;
    private NetworkTableEntry lineX0;
    private NetworkTableEntry lineX1;
    private NetworkTableEntry lineY0;
    private NetworkTableEntry lineY1;

    private Command wait = new WaitCommand(2);

    private UsbCamera camera;

    private Command DriveStraight;

    /**
     * Camera Toggling Variables (Dont work yet)
     */
    public static UsbCamera camera1;
    public static UsbCamera camera2;
    // public NetworkTable camera;
    public static VideoSink server;

    boolean toggleCloseOn = false;
    boolean toggleClosePressed = false;

    boolean toggleFarOn = false;
    boolean toggleFarPressed = false;

    public Robot() {
        super(0.08);
    }

    public void robotInit() {
        System.out.println("Initializing " + RobotMap.botName + "\n");

        camera = CameraServer.getInstance().startAutomaticCapture();
        /**
         * Camera Toggling initialization
         */
        // RobotMap.setCamera(true);
        // camera1 = CameraServer.getInstance().startAutomaticCapture(0);//may be 1,2
        // camera2 = CameraServer.getInstance().startAutomaticCapture(1);
        // server = CameraServer.getInstance().getServer();
        // server.setSource(camera1);
        // camera1.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        // camera2.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable pixy = inst.getTable("pixy");
        lineX0 = pixy.getEntry("lineX0");
        lineX1 = pixy.getEntry("lineX1");
        lineY0 = pixy.getEntry("lineY0");
        lineY1 = pixy.getEntry("lineY1");

        Subsystems.driveBase.cheesyDrive.setSafetyEnabled(false);

        /**
         * Sets Drivebase speed to fast as default and sets RB on driverController to
         * toggle the speed when pressed
         */
        RobotMap.isFastMode = false;
        RobotMap.setSpeedAndRotationCaps(0.2, 0.2);
        UserInterface.driverController.RB.whenPressed(new ToggleSpeed());
        // UserInterface.driverController.LB.whenPressed(new ToggleCamera());

        /**
         * Turns isHoldingPivotUp, cargoIsIn, & armIsOut booleans to false and flapIsUp
         * is true at bot Initialization.
         */
        RobotMap.isHoldingPivotUp = false;
        RobotMap.cargoIsIn = false;
        RobotMap.flapIsUp = true;
        RobotMap.armIsOut = false;
        RobotMap.highPivotCurrent = false;

        // GaffTapeTrack = new GaffTapeTrack();
        //DriveStraight = new DriveStraight(10,0.3,10000);

        Subsystems.climber.closeClimbRetract();
        Subsystems.climber.farClimbRetract();
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
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        System.out.println("Autonomous Initalized");
        // ParallelTurnBetter.start();
        //DriveStraight.start();

        /**
         * This makes sure that any old commands/command groups are stopped upon
         * Autonomous Initialization.
         */
        Scheduler.getInstance().removeAll();
        /**
         * Start TeleOpInit Code in Auto
         */
        /**
         * This makes sure that all of the motors are set to 0% upon TeleOp
         * Initialization.
         */
        Subsystems.cargo.stopPivot();
        Subsystems.cargo.stopIntakeMotors();
        Subsystems.cargo.stopEscalatorMotors();
        
        Subsystems.climber.closeClimbRetract();
        Subsystems.climber.farClimbRetract();

        /**
         * This makes sure that the bot is set to normal speed and rotation caps upon
         * TeleOp Initialization.
         */
        
        RobotMap.isFastMode = false;

        /**
         * Turns isHoldingPivotUp, cargoIsIn, & armIsOut booleans to false and flapIsUp
         * is true at TeleOp Initialization.
         */
        RobotMap.isHoldingPivotUp = false;
        RobotMap.cargoIsIn = false;
        RobotMap.armIsOut = false;
        RobotMap.flapIsUp = true;
        RobotMap.highPivotCurrent = false;

        /**
         * This makes sure that any old commands/command groups are stopped upon TeleOp
         * Initialization.
         */
        Scheduler.getInstance().removeAll();
        Subsystems.hatch.hatchClamp();
        /**
         * End TeleopInit Code in Auto
         */

    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        printDataToSmartDashboard();

        /**
         * Start TeleOpPeriodic Code in Auto
         */

        /**
         * Hatch Buttons
         */
        if (UserInterface.operatorController.RB.get()) {
            Subsystems.hatch.hatchClamp();
        }
        if (UserInterface.operatorController.LB.get()) {
            Subsystems.hatch.hatchRelease();
        }
        if (UserInterface.operatorController.A.get()) {
            Subsystems.hatch.armIn();
            RobotMap.armIsOut = false;
            Subsystems.hatch.hatchClamp();
        }
        if (UserInterface.operatorController.X.get()) {
            Subsystems.hatch.armOut();
            RobotMap.armIsOut = true;
        }

        /**
         * Cargo Buttons
         */
        if (UserInterface.operatorController.B.get()) {
            Subsystems.cargo.setFlapDown();
            RobotMap.flapIsUp = false;
        }
        if (UserInterface.operatorController.Y.get()) {
            Subsystems.cargo.setFlapUp();
            RobotMap.flapIsUp = true;
        }
        if (!RobotMap.highPivotCurrent) {
            RobotMap.highPivotCurrent = Subsystems.cargo.isPivotCurrentTooHigh();
        }
        if (UserInterface.operatorController.getPOVAngle() == 0) {
            RobotMap.isHoldingPivotUp = true;
        }
        if (RobotMap.isHoldingPivotUp) {
            /** 
             * Potential use for highPivotCurrent to hold pivot at a lesser speed
             */
            // if (!RobotMap.highPivotCurrent) {
            //     Subsystems.cargo.pivotIntake(0.5, Direction.Up);
            // } else {
            //     Subsystems.cargo.holdPivotIntakeUp();
            // }
            Subsystems.cargo.holdPivotIntakeUp();
        }
        if (!RobotMap.cargoIsIn) {
            RobotMap.cargoIsIn = Subsystems.cargo.getIntakeBeamBroken();
        } else {
            RobotMap.cargoIsIn = false;
        }
        if (UserInterface.operatorController.getLeftTrigger() > 0.1) {
            Subsystems.cargo.setEscalatorMotors(-1);
            RobotMap.cargoIsIn = false;
        }
        if (UserInterface.operatorController.getRightTrigger() > 0.1) {
            if (!Subsystems.cargo.getEscalatorBeamBroken()) {
                Subsystems.cargo.setEscalatorMotors(-1);
                Subsystems.cargo.setIntakeMotors(-0.75);
                if (RobotMap.cargoIsIn) {
                    Subsystems.cargo.stopIntakeMotors();
                } else {
                    Subsystems.cargo.setIntakeMotors(-0.75);
                }
            } else {
                Subsystems.cargo.stopIntakeMotors();
                Subsystems.cargo.stopEscalatorMotors();
                RobotMap.cargoIsIn = false;
            }
        }
        if (!(UserInterface.operatorController.getLeftTrigger() > 0.1)
                && !(UserInterface.operatorController.getRightTrigger() > 0.1)) {
            Subsystems.cargo.stopIntakeMotors();
            Subsystems.cargo.stopEscalatorMotors();
        }
        if (UserInterface.operatorController.getLeftJoystickY() < -0.1) {
            // pivots down
            Subsystems.cargo.pivotIntake(Math.abs(UserInterface.operatorController.getLeftJoystickY()) * 0.2, Direction.Down);
            RobotMap.isHoldingPivotUp = false;
            RobotMap.highPivotCurrent = false;
        } else if (UserInterface.operatorController.getLeftJoystickY() > 0.1) {
            // pivots up
            Subsystems.cargo.pivotIntake(Math.abs(UserInterface.operatorController.getLeftJoystickY()) * 0.4, Direction.Up);
            RobotMap.isHoldingPivotUp = false;
            RobotMap.highPivotCurrent = false;
        } else {
            Subsystems.cargo.stopPivot();
        }
        /**
         * End TeleopPeriodic Code in Auto
         */

    }

    public void teleopInit() {
        System.out.println("TeleOp Initalized");

        Scheduler.getInstance().removeAll();

        /**
         * This makes sure that all of the motors are set to 0% upon TeleOp
         * Initialization.
         */
        Subsystems.cargo.stopPivot();
        Subsystems.cargo.stopIntakeMotors();
        Subsystems.cargo.stopEscalatorMotors();
        Subsystems.climber.closeClimbRetract();
        Subsystems.climber.farClimbRetract();

        /**
         * This makes sure that the bot is set to normal speed and rotation caps upon
         * TeleOp Initialization.
         */
        // RobotMap.isFastMode = false;

        /**
         * Turns isHoldingPivotUp, cargoIsIn, & armIsOut booleans to false and flapIsUp
         * is true at TeleOp Initialization.
         */
        RobotMap.isHoldingPivotUp = false;
        RobotMap.cargoIsIn = false;
        RobotMap.armIsOut = false;
        RobotMap.flapIsUp = true;
        RobotMap.highPivotCurrent = false;

        /**
         * This makes sure that any old commands/command groups are stopped upon TeleOp
         * Initialization.
         */
        Subsystems.hatch.hatchClamp();
    }

    public void teleopPeriodic() {
        /**
         * This makes sure that TankDrive and other Commands used during TeleOp are run.
         */
        Scheduler.getInstance().run();

        printDataToSmartDashboard();


        updateToggle();
        /**
         * Unused Buttons
         */

        // if (UserInterface.operatorController.START.get()) {
        // }
        // if (UserInterface.operatorController.BACK.get()) {
        // }
        // if (UserInterface.operatorController.getPOVAngle() == 90) {

        // } else if (UserInterface.operatorController.getPOVAngle() == 180) {

        // } else if (UserInterface.operatorController.getPOVAngle() == 270) {
        // }
        // if (UserInterface.operatorController.getRightJoystickY() > 0.1) {

        // } else if (UserInterface.operatorController.getRightJoystickY() < -0.1) {

        // } else {
        // }
        // if (UserInterface.operatorController.getRightJoystickX() > 0.1) {

        // } else if (UserInterface.operatorController.getRightJoystickX() < -0.1) {

        // } else {
        // }
        // if (UserInterface.operatorController.getLeftJoystickX() > 0.1) {

        // } else if (UserInterface.operatorController.getLeftJoystickX() < -0.1) {

        // } else {
        // }


        /**
         * Hatch Buttons
         */
        if (UserInterface.operatorController.RB.get()) {
            Subsystems.hatch.hatchClamp();
        }
        if (UserInterface.operatorController.LB.get()) {
            Subsystems.hatch.hatchRelease();
        }
        if (UserInterface.operatorController.A.get()) {
            Subsystems.hatch.armIn();
            RobotMap.armIsOut = false;
            Subsystems.hatch.hatchClamp();
        }
        if (UserInterface.operatorController.X.get()) {
            Subsystems.hatch.armOut();
            RobotMap.armIsOut = true;
        }

        /**
         * Cargo Buttons
         */
        if (UserInterface.operatorController.B.get()) {
            Subsystems.cargo.setFlapDown();
            RobotMap.flapIsUp = false;
        }
        if (UserInterface.operatorController.Y.get()) {
            Subsystems.cargo.setFlapUp();
            RobotMap.flapIsUp = true;
        }
        if (!RobotMap.highPivotCurrent) {
            RobotMap.highPivotCurrent = Subsystems.cargo.isPivotCurrentTooHigh();
        }
        if (UserInterface.operatorController.getPOVAngle() == 0) {
            RobotMap.isHoldingPivotUp = true;
        }
        if (RobotMap.isHoldingPivotUp) {
            /** 
             * Potential use for highPivotCurrent to hold pivot at a lesser speed
             */
            // if (!RobotMap.highPivotCurrent) {
            //     Subsystems.cargo.pivotIntake(0.5, Direction.Up);
            // } else {
            //     Subsystems.cargo.holdPivotIntakeUp();
            // }
            Subsystems.cargo.holdPivotIntakeUp();
        }
        if (!RobotMap.cargoIsIn) {
            RobotMap.cargoIsIn = Subsystems.cargo.getIntakeBeamBroken();
        } else {
            wait.start();
            RobotMap.cargoIsIn = false;
        }
        if (UserInterface.operatorController.getLeftTrigger() > 0.1) {
            Subsystems.cargo.setEscalatorMotors(-1);
            RobotMap.cargoIsIn = false;
        }
        if (UserInterface.operatorController.getRightTrigger() > 0.1) {
            if (!Subsystems.cargo.getEscalatorBeamBroken()) {
                Subsystems.cargo.setEscalatorMotors(-1);
                Subsystems.cargo.setIntakeMotors(-0.75);
                if (RobotMap.cargoIsIn) {
                    Subsystems.cargo.stopIntakeMotors();
                } else {
                    Subsystems.cargo.setIntakeMotors(-0.75);
                }
            } else {
                Subsystems.cargo.stopIntakeMotors();
                Subsystems.cargo.stopEscalatorMotors();
                RobotMap.cargoIsIn = false;
            }
        }
        if (!(UserInterface.operatorController.getLeftTrigger() > 0.1)
                && !(UserInterface.operatorController.getRightTrigger() > 0.1)) {
            Subsystems.cargo.stopIntakeMotors();
            Subsystems.cargo.stopEscalatorMotors();
        }
        if (UserInterface.operatorController.getLeftJoystickY() < -0.1) {
            // pivots down
            Subsystems.cargo.pivotIntake(Math.abs(UserInterface.operatorController.getLeftJoystickY()) * 0.2, Direction.Down);
            RobotMap.isHoldingPivotUp = false;
            RobotMap.highPivotCurrent = false;
        } else if (UserInterface.operatorController.getLeftJoystickY() > 0.1) {
            // pivots up
            Subsystems.cargo.pivotIntake(Math.abs(UserInterface.operatorController.getLeftJoystickY()) * 0.4, Direction.Up);
            RobotMap.isHoldingPivotUp = false;
            RobotMap.highPivotCurrent = false;
        } else {
            Subsystems.cargo.stopPivot();
        }

        if(toggleCloseOn){
            Subsystems.climber.closeClimbExtend();
        } else {
            Subsystems.climber.closeClimbRetract();
        }
        if(toggleFarOn){
            Subsystems.climber.farClimbExtend();
            Subsystems.climber.closeClimbRetract();
            toggleClosePressed = false;
            toggleCloseOn = false;
        } else {
            Subsystems.climber.farClimbRetract();
        }

    }

    public void updateToggle() {
        // Cargo Intake side is the close climber side, which is the "front"
        if(UserInterface.driverController.getPOVAngle() == 180) {
            if(!toggleClosePressed){
                toggleCloseOn = !toggleCloseOn;
                toggleClosePressed = true;
            }
        } else {
            toggleClosePressed = false;
        }
        // Hatch side is the far climber side, which is the "back"
        if(UserInterface.driverController.getPOVAngle() == 0) {
            if(!toggleFarPressed){
                toggleFarOn = !toggleFarOn;
                toggleFarPressed = true;
            }
        } else {
            toggleFarPressed = false;
        }
    }

    private void printDataToSmartDashboard() {
        SmartDashboard.putNumber("lineX0", lineX0.getDouble(-404));
        SmartDashboard.putNumber("lineX1", lineX1.getDouble(-404));
        SmartDashboard.putNumber("lineY0", lineY0.getDouble(-404));
        SmartDashboard.putNumber("lineY1", lineY1.getDouble(-404));
        SmartDashboard.putNumber("Right Drive Position", Subsystems.driveBase.getRightPosition());
        SmartDashboard.putNumber("Left Drive Position", Subsystems.driveBase.getLeftPosition());
        SmartDashboard.putNumber("Gyro angle", Subsystems.driveBase.getGyroAngle());
        SmartDashboard.putNumber("POV Angle", UserInterface.operatorController.getPOVAngle());
        SmartDashboard.putBoolean("Left Throttle", RobotMap.isLeftThrottle);
        SmartDashboard.putBoolean("Fast Mode", RobotMap.isFastMode);
        SmartDashboard.putBoolean("Hold Pivot", RobotMap.isHoldingPivotUp);
        SmartDashboard.putBoolean("Cargo In", RobotMap.cargoIsIn);
        SmartDashboard.putBoolean("Pivot Beam Broken", Subsystems.cargo.getPivotBeamBroken());
        SmartDashboard.putBoolean("Flap Up", RobotMap.flapIsUp);
        SmartDashboard.putBoolean("Hatch Arm Out", RobotMap.armIsOut);
        SmartDashboard.putBoolean("isPivotCurrentTooHigh", Subsystems.cargo.isPivotCurrentTooHigh());
        SmartDashboard.putBoolean("Hatch Clamp", Subsystems.hatch.isHatchClamped());
        SmartDashboard.putNumber("Pivot Current", Subsystems.cargo.pivotCurrent());
        SmartDashboard.putNumber("Speed Cap", RobotMap.speedCap);
        SmartDashboard.putNumber("Rotation Cap", RobotMap.rotationCap);
        SmartDashboard.putNumber("Turn Direction", RobotMap.turnDirection);
        SmartDashboard.putNumber("Drive Offset", RobotMap.driveOffset);
        SmartDashboard.putBoolean("isCamera1", RobotMap.isCamera1);
        SmartDashboard.putBoolean("Escalator Occupied", Subsystems.cargo.getEscalatorBeamBroken());
        SmartDashboard.putBoolean("CloseClimbUp", Subsystems.climber.isCloseExtended());
        SmartDashboard.putBoolean("FarClimbUp", Subsystems.climber.isFarExtended());
    }
}
