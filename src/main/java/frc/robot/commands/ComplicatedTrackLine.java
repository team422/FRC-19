package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

public class ComplicatedTrackLine extends Command {

    private NetworkTableEntry lineX0;
    private NetworkTableEntry lineX1;
    private NetworkTableEntry lineY0;
    private NetworkTableEntry lineY1;
    private double correction;
    private double deadband = 5;
    private double out;
    private double across;
    private static double cameraOffset = 11.375;
    private double lineOffset1;
    private double lineOffset2;
    private double slope;
    
    private static double camera_pitch = 54.0;
    private static double fov_h = 60;
    private static double fov_v = 40;
    private static double camera_left = 0; //x coordinate of the left side of the camera field of view
    private static double camera_right = 78; //x coodinate of the rightside of the camera field of view
    private static double camera_far = 0; //y coordinate of the far sige of the camera field of view
    private static double camera_near = 51; //y coordinate of the near side of the camera field of view
    private static double camera_height = 11.375;
    private static double centroid_x = 11.375;
    private static double centroid_y = 14.5;
    private double idealAngle;
    private double xdistance;
    private double ydistance;
    private double camera_to_y_pixel_distance;

    public ComplicatedTrackLine() {
        super("ComplicatedTrackLine");
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
        if (lineY0.getDouble(-404) > lineY1.getDouble(-404)) {
            SmartDashboard.putNumber("Y-distanceClose", compute_camera_to_y_pixel_distance(compute_y_inches(lineY0.getDouble(-404))));
            SmartDashboard.putNumber("X-distanceClose", compute_x_inches(lineX0.getDouble(-404), compute_camera_to_y_pixel_distance(compute_y_inches(lineY0.getDouble(-404)))));
            lineOffset1 = compute_x_inches(lineX0.getDouble(-404), compute_camera_to_y_pixel_distance(compute_y_inches(lineY0.getDouble(-404))));
            lineOffset2 = compute_x_inches(lineX1.getDouble(-404), compute_camera_to_y_pixel_distance(compute_y_inches(lineY1.getDouble(-404))));
            SmartDashboard.putNumber("Y-distanceFar", compute_camera_to_y_pixel_distance(compute_y_inches(lineY1.getDouble(-404))));
            SmartDashboard.putNumber("X-distanceFar", compute_x_inches(lineX1.getDouble(-404), compute_camera_to_y_pixel_distance(compute_y_inches(lineY1.getDouble(-404)))));
            ydistance = compute_camera_to_y_pixel_distance(compute_y_inches(lineY1.getDouble(-404))) - compute_camera_to_y_pixel_distance(compute_y_inches(lineY0.getDouble(-404)));
        } else {
            SmartDashboard.putNumber("Y-distanceClose", compute_camera_to_y_pixel_distance(compute_y_inches(lineY1.getDouble(-404))));
            SmartDashboard.putNumber("X-distanceClose", compute_x_inches(lineX1.getDouble(-404), compute_y_inches(lineY1.getDouble(-404))));
            SmartDashboard.putNumber("Y-distanceFar", compute_camera_to_y_pixel_distance(compute_y_inches(lineY0.getDouble(-404))));
            SmartDashboard.putNumber("X-distanceFar", compute_x_inches(lineX0.getDouble(-404), compute_camera_to_y_pixel_distance(compute_y_inches(lineY0.getDouble(-404)))));
            lineOffset1 = compute_x_inches(lineX1.getDouble(-404), compute_camera_to_y_pixel_distance(compute_y_inches(lineY1.getDouble(-404))));
            lineOffset2 = compute_x_inches(lineX0.getDouble(-404), compute_camera_to_y_pixel_distance(compute_y_inches(lineY0.getDouble(-404))));
            ydistance = compute_y_inches(lineY0.getDouble(-404)) - compute_camera_to_y_pixel_distance(compute_y_inches(lineY1.getDouble(-404)));
        }
        if(lineOffset1 > lineOffset2) {
            xdistance = lineOffset1 - lineOffset2;
        } else {
            xdistance = lineOffset2 - lineOffset1;
        }
        idealAngle = Math.atan2(xdistance,ydistance) * (180 / Math.PI);
        SmartDashboard.putNumber("Ideal Angle", idealAngle);
        System.out.println("Setting ideal angle to " + idealAngle);
        RobotMap.setIdeal(idealAngle);
    }
    
    @Override
    public void execute() {
        if (lineY0.getDouble(-404) > lineY1.getDouble(-404)) {
            SmartDashboard.putNumber("Y-distanceClose", compute_camera_to_y_pixel_distance(compute_y_inches(lineY0.getDouble(-404))));
            SmartDashboard.putNumber("X-distanceClose", compute_x_inches(lineX0.getDouble(-404), compute_camera_to_y_pixel_distance(compute_y_inches(lineY0.getDouble(-404)))));
            lineOffset1 = compute_x_inches(lineX0.getDouble(-404), compute_camera_to_y_pixel_distance(compute_y_inches(lineY0.getDouble(-404))));
            lineOffset2 = compute_x_inches(lineX1.getDouble(-404), compute_camera_to_y_pixel_distance(compute_y_inches(lineY1.getDouble(-404))));
            SmartDashboard.putNumber("Y-distanceFar", compute_camera_to_y_pixel_distance(compute_y_inches(lineY1.getDouble(-404))));
            SmartDashboard.putNumber("X-distanceFar", compute_x_inches(lineX1.getDouble(-404), compute_camera_to_y_pixel_distance(compute_y_inches(lineY1.getDouble(-404)))));
            ydistance = compute_camera_to_y_pixel_distance(compute_y_inches(lineY1.getDouble(-404))) - compute_camera_to_y_pixel_distance(compute_y_inches(lineY0.getDouble(-404)));
        } else {
            SmartDashboard.putNumber("Y-distanceClose", compute_camera_to_y_pixel_distance(compute_y_inches(lineY1.getDouble(-404))));
            SmartDashboard.putNumber("X-distanceClose", compute_x_inches(lineX1.getDouble(-404), compute_y_inches(lineY1.getDouble(-404))));
            SmartDashboard.putNumber("Y-distanceFar", compute_camera_to_y_pixel_distance(compute_y_inches(lineY0.getDouble(-404))));
            SmartDashboard.putNumber("X-distanceFar", compute_x_inches(lineX0.getDouble(-404), compute_camera_to_y_pixel_distance(compute_y_inches(lineY0.getDouble(-404)))));
            lineOffset1 = compute_x_inches(lineX1.getDouble(-404), compute_camera_to_y_pixel_distance(compute_y_inches(lineY1.getDouble(-404))));
            lineOffset2 = compute_x_inches(lineX0.getDouble(-404), compute_camera_to_y_pixel_distance(compute_y_inches(lineY0.getDouble(-404))));
            ydistance = compute_y_inches(lineY0.getDouble(-404)) - compute_camera_to_y_pixel_distance(compute_y_inches(lineY1.getDouble(-404)));
        }
        if(lineOffset1 > lineOffset2) {
            xdistance = lineOffset1 - lineOffset2;
        } else {
            xdistance = lineOffset2 - lineOffset1;
        }
        idealAngle = Math.atan2(xdistance,ydistance) * (180 / Math.PI);
        SmartDashboard.putNumber("Ideal Angle", idealAngle);
        System.out.println("Setting ideal angle to " + idealAngle);
        RobotMap.setIdeal(idealAngle);
        // correction = lineOffset2 - lineOffset1;
        // correction *= 0.3;
        // //correction += 1;
        // Subsystems.driveBase.setMotors(-0.15*correction,0.15*correction);
        SmartDashboard.putNumber("Ideal Angle", idealAngle);
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

    double compute_y_inches(double camera_y) {
        double camera_y_pixels = camera_near - camera_far;
        double degrees_per_pixel = fov_v / (camera_y_pixels);
        double angle_to_pixel = (camera_pitch - (fov_v / 2)) + degrees_per_pixel * (camera_near-camera_y);
        double y_inches = Math.tan(angle_to_pixel * (Math.PI / 180)) * camera_height;
        return y_inches;
    }

    double compute_x_inches(double camera_x, double y_dist) {
        double camera_x_pixels = camera_right - camera_left;
        double degrees_per_pixel = fov_h / (camera_x_pixels);
        double angle_to_pixel = -(fov_h / 2) + degrees_per_pixel * camera_x;
        double x_inches = Math.tan(angle_to_pixel * (Math.PI/180)) * y_dist;
        return x_inches;
    }

 
    private double compute_camera_to_y_pixel_distance(double y_floor_distance) {
        camera_to_y_pixel_distance = Math.sqrt(Math.pow(camera_height,2)+Math.pow(y_floor_distance,2));
        return camera_to_y_pixel_distance;
    }
}