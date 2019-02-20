package frc.robot;

public class RobotMap {

    /**
     * Whether or not this is the competition bot. Changing this variable
     * will change all ports accordingly. MAKE SURE YOU GO THROUGH DRIVEBASE
     * AND CARGO SUBSYSTEMS AND CHANGE VICTOR/TALON CLASSES ACCORDINGLY.
     */
    public static final boolean isCompBot = true;

    /**
     * Sets which joystick of the driverXboxController that
     * the throttle for the drivebase is controlled by.
     * DEFAULT IS RIGHT. 
     */
    public static final boolean isLeftThrottle = false;

    public static double idealAngle = 0;
    public static double driveOffset = 0;
    public static double turnDirection = 0;

    public static boolean isFastMode = true;
    public static double speedCap = 1;
    public static double rotationCap = 0.35;
    public static String botName = (isCompBot) ? "Meridian" : "Hot Take";
    public static boolean isHoldingPivotUp = false;
    public static boolean cargoIsIn = false;
    public static boolean flapIsUp = true;
    public static boolean armIsOut = false;
    public static boolean highPivotCurrent = false;


    /**
     * Various Ports
     */

    // Talon/Victor IDs
    public static final int leftFrontFollower = (isCompBot) ? 4 : 35;
    public static final int leftMiddleMaster = (isCompBot) ? 11 : 32;
    public static final int leftRearFollower = (isCompBot) ? 6 : 33;
    public static final int rightFrontFollower = (isCompBot) ? 3 : 20;
    public static final int rightMiddleMaster = (isCompBot) ? 12 : 23;
    public static final int rightRearFollower = (isCompBot) ? 5 : 22;

    public static final int leftFrontClimb = (isCompBot) ? 1 : 21;
    public static final int leftBackClimb = (isCompBot) ? 7 : 26;
    public static final int rightFrontClimb = (isCompBot) ? 2 : 34;
    public static final int rightBackClimb = (isCompBot) ? 14 : 24;

    public static final int cargoEscalatorWheels = (isCompBot) ? 9 : 30;
    public static final int cargoIntakeWheels = (isCompBot) ? 8 : 29;
    public static final int cargoIntakePivot = (isCompBot) ? 13 : 31;

    public static final int extraMotorController = (isCompBot) ? 10 : 25;

    // Servo Ports        
    public static final int hatchLeftGrabber = 8;
    public static final int hatchRightGrabber = 9;

    // Double Solenoid Values
    public static final int cargoFlapUp = 7; 
    public static final int cargoFlapDown = 0;
    public static final int hatchArmOut = 6;
    public static final int hatchArmIn = 1;

    // Digital IO Ports
    public static final int cargoPivotUltrasonic = 8;
    public static final int cargoEscalatorUltrasonic = 7;
    public static final int cargoIntakeUltrasonic = 9;
    public static final int climberUltrasonic = 3;

    // UI Ports
    public static final int launchpad = 0;
    public static final int driverXboxController = 1;
    public static final int operatorXboxController = 2;

    /**
     * End Port Setting
     */

    public static double getIdealAngle() {
        return idealAngle;
    }

    public static void setIdealAngle(double angle) {
        idealAngle = angle;
        System.out.println("Ideal angle is now " + idealAngle);
    }

    public static double getSpeedCap() {
        return speedCap;
    }

    public static double getRotationCap() {
        return rotationCap;
    }

    public static void setSpeedAndRotationCaps(double newSpeedCap, double newRotationCap) {
        speedCap = newSpeedCap;
        rotationCap = newRotationCap;
    }

    public static void setDriveOffset(double offset) {
        driveOffset = offset;
        System.out.println("Drive offset is now " + driveOffset);
    }

    public static double getDriveOffset() {
        return driveOffset;                
    }
    
    public static double getTurnDirection() {
        return turnDirection;
    }
    
    public static void setTurnDirection(double direction) {
        turnDirection = direction;
    }

}