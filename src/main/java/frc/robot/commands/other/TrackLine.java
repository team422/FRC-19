package frc.robot.commands.other;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TrackLine extends Command {

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

    public TrackLine() {
        super("TrackLine");
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
            out = -10.24*Math.log(lineY0.getDouble(-404)) + 48.61;
            across = 0.8814 * (out) + 8.722;
            lineOffset1 = (lineX0.getDouble(-404) - (78/2)) * (across / 78);
            out = -10.24*Math.log(lineY1.getDouble(-404)) + 48.61;
            across = 0.8814 * (out) + 8.722;
            lineOffset2 = (lineX1.getDouble(-404) - (78/2)) * (across / 78);            
            SmartDashboard.putNumber("Distance Out", out);
            SmartDashboard.putNumber("Distance Across", across);
            SmartDashboard.putNumber("Distance Offset", lineOffset1);
            //SmartDashboard.putNumber("Distance", -0.0017*Math.pow((lineY0.getDouble(-404)),3) + 0.1673*Math.pow((lineY0.getDouble(-404)),2)-5.8355*(lineY0.getDouble(-404))+95.281);
            //SmartDashboard.putNumber("Distance", 0.0135*Math.pow((lineY0.getDouble(-404)),2)-1.6311*(lineY0.getDouble(-404))+62.856);
        } else {
            out = -10.24*Math.log(lineY1.getDouble(-404)) + 48.61;
            across = 0.8814 * (out) + 8.722;
            lineOffset1 = (lineX1.getDouble(-404) - (78/2)) * (across / 78);
            out = -10.24*Math.log(lineY0.getDouble(-404)) + 48.61;
            across = 0.8814 * (out) + 8.722;
            lineOffset2 = (lineX0.getDouble(-404) - (78/2)) * (across / 78);
            SmartDashboard.putNumber("Distance Out", out);
            SmartDashboard.putNumber("Distance Across", across);
            SmartDashboard.putNumber("Distance Offset", lineOffset1);            
            //SmartDashboard.putNumber("Distance", -0.0017*Math.pow((lineY1.getDouble(-404)),3) + 0.1673*Math.pow((lineY1.getDouble(-404)),2)-5.8355*(lineY1.getDouble(-404))+95.281);
            //SmartDashboard.putNumber("Distance", 0.0135*Math.pow((lineY1.getDouble(-404)),2)-1.6311*(lineY1.getDouble(-404))+62.856);
        }
        correction = lineOffset1 - lineOffset2;

        slope = Math.abs(((lineY1.getDouble(-404)-lineY0.getDouble(-404))/(lineX1.getDouble(-404)-lineX0.getDouble(-404))));
        SmartDashboard.putNumber("Angle", -Math.atan(slope)*(180/Math.PI)+90);
        SmartDashboard.putNumber("Slope", slope);
        SmartDashboard.putNumber("Parallel Angle", (lineOffset1*5.08));

        correction *= 0.3d;
        correction += 1d;
        Subsystems.driveBase.setMotors(0.15*correction,-0.15*correction);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void interrupted() {
        Subsystems.driveBase.stopMotors();
    }

    @Override
    public void end() {
        Subsystems.driveBase.stopMotors();
    } 
}