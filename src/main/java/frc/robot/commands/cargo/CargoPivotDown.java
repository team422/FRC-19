package frc.robot.commands.cargo;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class CargoPivotDown extends Command {

  private double ticks;
  private double speed;

  public CargoPivotDown(/*double Angle, */double Speed, double Timeout) {
    super("CargoPivotDown");
    requires(Subsystems.cargo);
    //ticks = convertAngleToTicks(Angle);
    speed = Speed;
    setTimeout(Timeout);
    // if (Angle >= 0) {
    //   forward = true;
    // } else {
    //   forward = false;
    // }       
    //if we need get encoders             
  }

  @Override
  protected void initialize() {
    //Subsystems.driveBase.zeroEncoderPosition();
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