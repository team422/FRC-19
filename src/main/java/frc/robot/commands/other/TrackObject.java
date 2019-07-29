package frc.robot.commands.other;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;
import frc.robot.RobotMap;


public class TrackObject extends Command {

    private NetworkTableEntry blockX;
    private NetworkTableEntry blockY;
    private double oldX;
    private double oldY;

    public TrackObject() {
        super("TrackObject");
        requires(Subsystems.driveBase);
    }

    @Override
    public void initialize() {
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable pixy = inst.getTable("pixy");
        blockX = pixy.getEntry("blockX");
        blockY = pixy.getEntry("blockY");
        Subsystems.driveBase.zeroEncoderPosition();
        Subsystems.driveBase.zeroGyroAngle();
    }

    @Override
    public void execute() {
        double correction = (160.0d - blockX.getDouble(-404)) / 160.0d;
        //correction *= 0.17d;
        //correction += 1d;

        System.out.println(correction);
        if (Math.abs(correction) > 0.2) {
            if (Math.abs(correction)*0.25 + 0.1 <= 1) {
                Subsystems.driveBase.setMotors(0.25*correction + 0.1, -0.25*correction + 0.1);
            } else {
                Subsystems.driveBase.setMotors(0.25*correction, -0.25*correction);
            }
        } 
        else {
            if (Subsystems.cargo.getIntakeBeamBroken()) {
                Subsystems.cargo.stopIntakeMotors();
                Subsystems.driveBase.setMotors(0, 0);
            } else {
                Subsystems.driveBase.setMotors(0.25, 0.25);
            }

            if (Subsystems.cargo.getEscalatorBeamBroken()) {
                Subsystems.cargo.stopIntakeMotors();
                Subsystems.cargo.stopEscalatorMotors();
            } else {
                Subsystems.cargo.setIntakeMotors(-0.75);
                Subsystems.cargo.setEscalatorMotors(-0.75);

            }

        }
    }

    @Override
    public boolean isFinished() {
        return Subsystems.cargo.getEscalatorBeamBroken();
    }

    @Override
    public void interrupted() {
        Subsystems.driveBase.setMotors(0, 0);
        Subsystems.cargo.setEscalatorMotors(0);
        Subsystems.cargo.setIntakeMotors(0);    
    }

    @Override
    public void end() {
        Subsystems.driveBase.setMotors(0, 0);
        Subsystems.cargo.setEscalatorMotors(0);
        Subsystems.cargo.setIntakeMotors(0);
    } 
} 