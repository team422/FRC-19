package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class PunchOutwards extends Command {

  public PunchOutwards() {
    super("PunchOutwards");
    requires(Subsystems.hatch);
  }

  @Override
  protected void initialize() {
    setTimeout(1);
  }

  @Override
  protected void execute() {
    Subsystems.hatch.punchOutwards();
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected void interrupted() {}
  
  @Override
  protected void end() {}

}