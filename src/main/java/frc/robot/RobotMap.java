package frc.robot;

public class RobotMap {

    /**
     *  Whether or not this is the competition bot
     *  Changing this variable will change all the ports accordingly.
     */
    public static final boolean compBot = true;
    /**
     *  Sets which joystick of the driverXboxController that
     *  the throttle for the drivebase is controlled by.
     *  DEFAULT IS RIGHT. 
     */
    public static final boolean isLeftThrottle = false;
    public static double idealAngle = 0;
    public static double speedCap = 0.5;
    public static double angleCap = 0.5;

    /**
     * Various Ports
     */

    // Talon/Victor IDs
    
    public static final int leftFrontFollower = (compBot) ? 4 : 35;
    public static final int leftMiddleMaster = (compBot) ? 11 : 32;
    public static final int leftRearFollower = (compBot) ? 6 : 33;
    public static final int rightFrontFollower = (compBot) ? 3 : 20;
    public static final int rightMiddleMaster = (compBot) ? 12 : 23;
    public static final int rightRearFollower = (compBot) ? 5 : 22;

    public static final int leftFrontClimb = (compBot) ? 1 : 21;
    public static final int leftBackClimb = (compBot) ? 7 : 26;
    public static final int rightFrontClimb = (compBot) ? 2 : 34;
    public static final int rightBackClimb = (compBot) ? 14 : 24;

    public static final int cargoEscalatorWheels = (compBot) ? 9 : 30;
    public static final int cargoIntakeWheels = (compBot) ? 8 : 29;
    public static final int cargoIntakePivot = (compBot) ? 13 : 31;

    public static final int extraMotorController = (compBot) ? 10 : 25;

    // Servo Ports        
    public static final int hatchLeftGrabber = 8;
    public static final int hatchRightGrabber = 9;

    // Double Solenoid Values
    public static final int cargoFlapUp = 0; 
    public static final int cargoFlapDown = 1;
    public static final int hatchArmOut = 6;
    public static final int hatchArmIn = 7;

    // Digital IO Ports
    public static final int cargoPivotLimitSwitch = 1; // check with Nathan/Mason for type used
    public static final int cargoEscalatorUltrasonic = 7; 
    public static final int climberUltrasonic = 3;

    // UI Ports
    public static final int launchpad = 0;
    public static final int driverXboxController = 1;
    public static final int operatorXboxController = 2;

    public static double getIdeal() {
        return idealAngle;
    }

    public static void setIdeal(double angle) {
        idealAngle = angle;
        System.out.println("Ideal angle is now " + idealAngle);
    }

    public static double getCap() {
        return speedCap;
    }

    public static double getRot() {
        return angleCap;
    }

    public static void setCap(double speed, double angle) {
        speedCap = speed;
        angleCap = angle;
    }

}