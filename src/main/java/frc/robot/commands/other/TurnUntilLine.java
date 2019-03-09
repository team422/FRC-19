package frc.robot.commands.other;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;
import frc.robot.RobotMap;

public class TurnUntilLine extends Command {

    private NetworkTableEntry lineX0;
    private NetworkTableEntry lineX1;
    private NetworkTableEntry lineY0;
    private NetworkTableEntry lineY1;

    private double correction;
    private double deadband = 0.01;
    private double lineOffset1;
    private double lineOffset2;
    
    private double xdistance;

    public TurnUntilLine() {
        super("TurnUntilLine");
        requires(Subsystems.driveBase);
    }

    @Override
    public void initialize() {
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable pixy = inst.getTable("pixy");
        lineX0 = pixy.getEntry("lineX0");
        lineX1 = pixy.getEntry("lineX1");
        lineY0 = pixy.getEntry("lineY0");
        lineY1 = pixy.getEntry("lineY1");
        Subsystems.driveBase.zeroEncoderPosition();
        Subsystems.driveBase.zeroGyroAngle();
    }
    
    @Override
    public void execute() {
        if (lineY0.getDouble(-404) > lineY1.getDouble(-404)) {
            lineOffset1 = compute_x_inches(lineX0.getDouble(-404), computeYExpiremental(lineY0.getDouble(-404)));
            lineOffset2 = compute_x_inches(lineX1.getDouble(-404), computeYExpiremental(lineY1.getDouble(-404)));
        } else {
            lineOffset1 = compute_x_inches(lineX1.getDouble(-404), computeYExpiremental(lineY1.getDouble(-404)));
            lineOffset2 = compute_x_inches(lineX0.getDouble(-404), computeYExpiremental(lineY0.getDouble(-404)));
        }
        xdistance = lineOffset1 - lineOffset2;//may need to replace below w this if it only turns one way
        // if(lineOffset1 > lineOffset2) {
        //     xdistance = lineOffset1 - lineOffset2;                        
        // } else {
        //     xdistance = lineOffset2 - lineOffset1;
        // }
        correction = xdistance;
        correction *= 0.1;
        if(correction > 0) {//thinkk this is right, may need to cahnge
            correction += 1;
        } else {
            correction -= 1;
        }
        Subsystems.driveBase.setMotors(0.1*correction,-0.1*correction);
    }

    @Override
    public boolean isFinished() {
        return (Math.abs(xdistance) < deadband);
    }

    @Override
    public void interrupted() {
        Subsystems.driveBase.setMotors(0, 0);
    }

    @Override
    public void end() {
        Subsystems.driveBase.setMotors(0, 0);
    } 

    double compute_x_inches(double camera_x, double y_dist) {
        double width = 0.8604*y_dist + 9.131;
        double prop = camera_x / 39;
        double x_inches = width * prop;
        return x_inches;
    }   

    private double computeYExpiremental(double yValue) {
        return (38.453*Math.pow(Math.E,-0.033*yValue));
    }

}