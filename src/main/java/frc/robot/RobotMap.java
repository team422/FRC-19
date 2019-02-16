package frc.robot;

public class RobotMap {

    /**
     *  Whether or not this is the competition bot
     *  Changing this variable will change all the ports accordingly.
     */
    public static final boolean compBot = false;//right now true is pbot, false is Axiom
    public static double idealAngle = 0;

    /**
     * Various Ports
     */

    // Talon/Victor IDS2
    
    public static final int leftFrontFollower = (compBot) ? 2 : 4;
    public static final int leftMiddleMaster = (compBot) ? 1 : 11;
    public static final int leftRearFollower = (compBot) ? 3 : 6;
    public static final int rightFrontFollower = (compBot) ? 5 : 3;
    public static final int rightMiddleMaster = (compBot) ? 4 : 12;
    public static final int rightRearFollower = (compBot) ? 6 : 5;

    public static final int leftBackClimb = (compBot) ? 40 : 1;
    public static final int leftFrontClimb = (compBot) ? 12 : 1;
    public static final int rightBackClimb = (compBot) ? 10 : 1;
    public static final int rightFrontClimb = (compBot) ? 17 : 1;

    public static final int cargoIntakeWheels = 8;
    public static final int cargoIntakePivot = 13; 
    public static final int cargoEscalator = 9; 

    // Analog IO Ports
    public static final int intakeUltrasonic = 3;
    public static final int cargoUltrasonicSensor = 4; 

    // UI Ports
    public static final int operatorXboxController = 2;
    public static final int driverXboxController = 1;
    public static final int launchpad  = 0;

    // Double Solenoid Values

    public static final int cargoDirectorFlapPush = 0; 
    public static final int cargoDirectorFlapPull = 1;
    public static final int punchingOutwards = 6;
    public static final int punchingInwards = 7;

    //Servo Ports        
    public static final int leftHatchGrabber = 8;
    public static final int rightHatchGrabber = 9;

    // Digital IO Ports
    public static final int cargoLimitSwitch = 1;

    public static double getIdeal() {
        return idealAngle;
    }

    public static void setIdeal(double angle) {
        idealAngle = angle;
        System.out.println("Ideal angle is now " + idealAngle);
    }

}