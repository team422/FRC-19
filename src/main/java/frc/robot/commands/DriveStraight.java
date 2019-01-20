package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class DriveStraight extends Command {

  private double ticks;
  private boolean forward;
  private double speed;
  private double timeout;

  public DriveStraight(double Inches, double Speed, double TimeOut) {
    super("DriveStraight");
    requires(Subsystems.driveBase);
    ticks = convertToTicks(Inches);
    speed = Speed;
    timeout = TimeOut;
    if (Inches >= 0) {
      forward = true;
    } else {
      forward = false;
    }                    
  }

  @Override
  protected void initialize() {
    setTimeout(timeout);
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
  protected void interrupted() {}
  
  @Override
  protected void end() {
    Subsystems.driveBase.setMotors(0,0);
  }

  public double convertToTicks(double inches) {
    return (4096 / (6 * 3.1415926) * inches);
  }

}