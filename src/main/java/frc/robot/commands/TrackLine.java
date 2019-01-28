package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;


public class TrackLine extends Command {
   
    private NetworkTableEntry lineX0;
    private NetworkTableEntry lineX1;
    private NetworkTableEntry lineY0;
    private NetworkTableEntry lineY1;
    private double correction;
    private boolean isTracking0;
    private double deadband = 5; 
    
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
            correction = (39 - lineX0.getDouble(-404)) / 5.0d;
            isTracking0 = true;
        } else {
            correction = (39 - lineX1.getDouble(-404)) / 5.0d;
            isTracking0 = false;
        }
        correction *= 0.15d;
        if (correction > 0) {
            correction += 1d;
        } else {
            correction -= 1d;
        }
        if ((isTracking0 && lineX0.getDouble(-404) > (39 - deadband) && lineX0.getDouble(-404) < (39 + deadband)) || (!isTracking0 && lineX1.getDouble(-404) > (39 - deadband) && lineX1.getDouble(-404) < (39 + deadband))) {
            correction = 0;
        }
        Subsystems.driveBase.setMotors(0.25*correction, -0.25*correction);
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
}