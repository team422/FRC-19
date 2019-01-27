package frc.robot;

public class RobotMap {

    /**
     *  Whether or not this is the competition bot
     *  Changing this variable will change all the ports accordingly.
     */
    public static final boolean compBot = true;

    /**
     * Various Ports
     */

    // Talon/Victor IDS2
    
    public static final int leftFrontFollower = (compBot) ? 1 : 44;
    public static final int leftMiddleMaster = (compBot) ? 2 : 40;
    public static final int leftRearFollower = (compBot) ? 3 : 21;
    public static final int rightFrontFollower = (compBot) ? 4 : 42;
    public static final int rightMiddleMaster = (compBot) ? 5 : 39;
    public static final int rightRearFollower = (compBot) ? 6 : 46;

    public static final int leftBackClimb = (compBot) ? 1 : 1;
    public static final int leftFrontClimb = (compBot) ? 1 : 1;
    public static final int rightBackClimb = (compBot) ? 1 : 1;
    public static final int rightFrontClimb = (compBot) ? 1 : 1;

    public static final int cargoIntakeWheels = 1;
    public static final int cargoIntakePivot = 1; 
    public static final int cargoEscalator1 = 1; 
    public static final int cargoEscalator2 = 1;

    // Analog IO Ports
    public static final int intakeUltrasonic = 3;
    public static final int cargoUltrasonicSensor = 4; 

    // UI Ports
    public static final int operatorXboxController = 2;
    public static final int driverXboxController = 1;
    public static final int launchpad  = 0;

    // Double Solenoid Values

    public static final int cargoDirectorFlapPush = 1; 
    public static final int cargoDirectorFlapPull = 2;
    public static final int punchingOutwards = 3;
    public static final int punchingInwards = 4;

    //Servo Ports        
    public static final int leftHatchGrabber = 1;
    public static final int rightHatchGrabber = 2;

    // Digital IO Ports
    public static final int cargoLimitSwitch = 1;

}