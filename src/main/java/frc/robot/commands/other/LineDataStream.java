package frc.robot.commands.other;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

public class LineDataStream extends Command {

    private NetworkTableEntry lineX0;
    private NetworkTableEntry lineX1;
    private NetworkTableEntry lineY0;
    private NetworkTableEntry lineY1;
    private double lineOffset1;
    private double lineOffset2;
    private double distance_to_align;
    private static final double camera_to_robot_center = 14.5; //inches
    
    // private static double camera_pitch = 54.0;
    // private static double fov_h = 60;
    // private static double fov_v = 40;
    // private static double camera_left = 0; //x coordinate of the left side of the camera field of view
    // private static double camera_right = 78; //x coodinate of the rightside of the camera field of view
    // private static double camera_far = 0; //y coordinate of the far sige of the camera field of view
    // private static double camera_near = 51; //y coordinate of the near side of the camera field of view
    // private static double camera_height = 12;
    // private static double centroid_x = 11.375;
    // private static double centroid_y = 14.5;
    private double idealAngle;
    private double xdistance;
    private double ydistance;
    private double camera_to_y_pixel_distance;

    public LineDataStream() {
        super("LineDataStream");
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
            SmartDashboard.putNumber("Y-distanceClose", computeYExpiremental(lineY0.getDouble(-404)));
            SmartDashboard.putNumber("X-distanceClose", compute_x_inches(lineX0.getDouble(-404), computeYExpiremental(lineY0.getDouble(-404))));
            SmartDashboard.putNumber("Y-distanceFar", computeYExpiremental(lineY1.getDouble(-404)));
            SmartDashboard.putNumber("X-distanceFar", compute_x_inches(lineX1.getDouble(-404), computeYExpiremental(lineY1.getDouble(-404))));
            ydistance = computeYExpiremental(lineY1.getDouble(-404)) - computeYExpiremental(lineY0.getDouble(-404));
        } else {
            lineOffset1 = compute_x_inches(lineX1.getDouble(-404), computeYExpiremental(lineY1.getDouble(-404)));
            lineOffset2 = compute_x_inches(lineX0.getDouble(-404), computeYExpiremental(lineY0.getDouble(-404)));
            SmartDashboard.putNumber("Y-distanceClose", computeYExpiremental(lineY1.getDouble(-404)));
            SmartDashboard.putNumber("X-distanceClose", compute_x_inches(lineX1.getDouble(-404), computeYExpiremental(lineY1.getDouble(-404))));
            SmartDashboard.putNumber("Y-distanceFar", computeYExpiremental(lineY0.getDouble(-404)));
            SmartDashboard.putNumber("X-distanceFar", compute_x_inches(lineX0.getDouble(-404), computeYExpiremental(lineY0.getDouble(-404))));
            ydistance = computeYExpiremental(lineY0.getDouble(-404)) - computeYExpiremental(lineY1.getDouble(-404));
        }
        xdistance = lineOffset1 - lineOffset2;
        
        idealAngle = Math.atan2(xdistance,ydistance) * (180 / Math.PI);
        SmartDashboard.putNumber("Ideal Angle", (90 - Math.abs(idealAngle)) * (idealAngle/Math.abs(idealAngle)));

        if (lineY0.getDouble(-404) > lineY1.getDouble(-404)) {
            distance_to_align = Math.sin(Math.toRadians(90-idealAngle)) * (camera_to_robot_center + computeYExpiremental(lineY0.getDouble(-404)));
        } else {
            distance_to_align = Math.sin(Math.toRadians(90-idealAngle)) * (camera_to_robot_center + computeYExpiremental(lineY1.getDouble(-404)));
        }

        SmartDashboard.putNumber("Drive Offset", idealAngle);
    }

    @Override
    public boolean isFinished() {
        return false;
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