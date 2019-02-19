package frc.robot.commands.other;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class DriveStraight extends Command {

  private double ticks;
  private double speed;
  private boolean forward;

  public DriveStraight(double Inches, double Speed, double Timeout) {
    super("DriveStraight");
    requires(Subsystems.driveBase);
    ticks = convertToTicks(Inches);
    speed = Speed;
    setTimeout(Timeout);
    if (Inches >= 0) {
      forward = true;
    } else {
      forward = false;
    }                    
  }

  @Override
  protected void initialize() {
    Subsystems.driveBase.zeroEncoderPosition();
    Subsystems.driveBase.zeroGyroAngle();
  }

  @Override
  protected void execute() {
    double correction = Subsystems.driveBase.getGyroAngle();
    correction *= 0.075;
    correction += 1.0;
    if (forward) {
        Subsystems.driveBase.setMotors(speed, speed * correction);
    } else {
        Subsystems.driveBase.setMotors(-speed * correction, -speed);
    }
  }

  @Override
  protected boolean isFinished() {
    int leftPosition = Math.abs(Subsystems.driveBase.getLeftPosition());
    int rightPosition = Math.abs(Subsystems.driveBase.getRightPosition());
    return (leftPosition > ticks) || (rightPosition > ticks) || isTimedOut();
  }

  @Override
  protected void interrupted() {
    Subsystems.driveBase.stopMotors();
  }
  
  @Override
  protected void end() {
    Subsystems.driveBase.stopMotors();
  }

  public double convertToTicks(double inches) {
    return (4096 / (6 * 3.1415926) * inches);
  }

}