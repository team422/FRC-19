package main.java.frc.robot;

public class RobotMap {

    /**
     *  Whether or not this is the competition bot
     *  Changing this variable will change all the ports accordingly.
     */
    public static final boolean compBot = false;

    /**
     * Various Ports
     */

    // Talon/Victor IDS2
    
    public static final int leftFrontFollower = (compBot) ? 0 : 44;
    public static final int leftMiddleMaster = (compBot) ? 1 : 40;
    public static final int leftRearFollower = (compBot) ? 2 : 21;
    public static final int rightFrontFollower = (compBot) ? 13 : 42;
    public static final int rightMiddleMaster = (compBot) ? 14 : 39;
    public static final int rightRearFollower = (compBot) ? 15 : 46;

    public static final int leftBackClimb = (compBot) ? 443 : 2331;
    public static final int leftFrontClimb = (compBot) ? 4343 : 39435;
    public static final int rightBackClimb = (compBot) ? 555 : 45522;
    public static final int rightFrontClimb = (compBot) ? 634 : 44326;

    public static final int cargoIntakeWheels = 9001;
    public static final int cargoIntakePivot = 9002; 
    public static final int cargoEscalator1 = 1234567890; 
    public static final int cargoEscalator2 = 505005005;

    // Analog IO Ports
    public static final int intakeUltrasonic = 3;
    public static final int cargoUltrasonicSensor = 42; 

    // UI Ports
    public static final int operatorXboxController = 2;
    public static final int driverXboxController = 1;
    public static final int launchpad  = 0;

    // Double Solenoid Values

    public static final int cargoDirectorFlapPush = 8788787; 
    public static final int cargoDirectorFlapPull = 8788788;

    // Digital IO Ports
    public static final int cargoLimitSwitch = 536871066;

}