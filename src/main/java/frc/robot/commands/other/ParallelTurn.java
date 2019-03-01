package frc.robot.commands.other;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;

public class ParallelTurn extends Command {

  private double angle;
  private double parallel;
  private double correction;
  private double slope;
  private NetworkTableEntry lineX0;
  private NetworkTableEntry lineX1;
  private NetworkTableEntry lineY0;
  private NetworkTableEntry lineY1;
  private double deadband = 5;

  public ParallelTurn() {
    super("ParallelTurn");
    requires(Subsystems.driveBase);
  }

  @Override
  protected void initialize() {
    setTimeout(40);
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
  protected void execute() {
    slope = Math.abs(((lineY1.getDouble(-404)-lineY0.getDouble(-404))/(lineX1.getDouble(-404)-lineX0.getDouble(-404))));
    angle = -Math.atan(slope)*(180/Math.PI)+90;
    parallel = Math.abs((((lineX0.getDouble(-404)+lineY0.getDouble(-404)/2)-39)*0.19872-5.5)*5.08);
    correction = angle - parallel; 
    correction *= 0.3d;
    correction += 1d;
    correction = Math.abs(correction);
    if (slope >= 0) {
      Subsystems.driveBase.setMotors(-0.25*correction, 0.25*correction);
    } else {
      Subsystems.driveBase.setMotors(0.25*correction, -0.25*correction);
    }
  }

  @Override
  protected boolean isFinished() {
    return Math.abs(angle-parallel) < deadband || isTimedOut();
  }

  @Override
  protected void interrupted() {
    Subsystems.driveBase.stopMotors();
  }
  
  @Override
  protected void end() {
    Subsystems.driveBase.stopMotors();
  }

}