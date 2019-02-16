package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class DriveToWall extends Command {

  private double ticks;
  private boolean forward;
  private double speed;
  private double timeout;

 public DriveToWall(double Speed, double TimeOut) {
    super("DriveToWall");
    requires(Subsystems.driveBase);
    speed = Speed;
    timeout = TimeOut;
  }

  @Override
  protected void initialize() {
    setTimeout(timeout);                
    Subsystems.driveBase.zeroEncoderPosition();
    Subsystems.driveBase.zeroGyroAngle();
  }

  @Override
  protected void execute() {
        Subsystems.driveBase.setMotors(speed, speed); 
  }

  @Override
  protected boolean isFinished() {
    return Subsystems.climber.ultrasonic.get() || isTimedOut();
  }

  @Override
  protected void interrupted() {}
  
  @Override
  protected void end() {
    Subsystems.driveBase.setMotors(0,0);
  }
}