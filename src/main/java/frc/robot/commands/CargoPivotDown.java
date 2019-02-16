package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class CargoPivotDown extends Command {

  private double ticks;
  private double speed;
  private double timeout;

  public CargoPivotDown(/*double Angle, */double Speed, double TimeOut) {
    super("CargoPivotDown");
    requires(Subsystems.cargo);
    //ticks = convertAngleToTicks(Angle);
    speed = Speed;
    timeout = TimeOut;
    // if (Angle >= 0) {
    //   forward = true;
    // } else {
    //   forward = false;
    // }       
    //if we need get encoders             
  }

  @Override
  protected void initialize() {
    setTimeout(timeout);
    //Subsystems.driveBase.zeroEncoderPosition();
    Subsystems.driveBase.zeroGyroAngle();
  }

  @Override
  protected void execute() {
    Subsystems.cargo.pivotIntake(-speed);
  }

  @Override
  protected boolean isFinished() {
    //int position = Math.abs(Subsystems.cargo.getIntakePivot());
    return (/*leftPosition > ticks) || */isTimedOut());
  }

  @Override
  protected void interrupted() {}
  
  @Override
  protected void end() {
    Subsystems.cargo.pivotIntake(0);
  }

  // public double convertAngleToTicks(double angle) {
  //   return (11.3777777778 * angle);
  // }

}