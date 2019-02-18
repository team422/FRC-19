package frc.robot.commands.climber;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class ClimberDriveToWall extends Command {

  private double ticks;
  private boolean forward;
  private double speed;

  public ClimberDriveToWall(double Speed, double Timeout) {
    super("ClimberDriveToWall");
    requires(Subsystems.driveBase);
    speed = Speed;
    setTimeout(Timeout);                
  }

  @Override
  protected void initialize() {
    Subsystems.driveBase.zeroEncoderPosition();
    Subsystems.driveBase.zeroGyroAngle();
  }

  @Override
  protected void execute() {
        Subsystems.driveBase.setMotors(speed, speed); 
  }

  @Override
  protected boolean isFinished() {
    return Subsystems.climber.climberUltrasonic.get() || isTimedOut();
  }

  @Override
  protected void interrupted() {}
  
  @Override
  protected void end() {
    Subsystems.driveBase.setMotors(0,0);
  }
}