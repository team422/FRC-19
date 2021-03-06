package frc.robot.commands.hatch;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class HatchRelease extends Command {

  public HatchRelease() {
    super("HatchRelease");
    requires(Subsystems.hatch);
  }

  @Override
  protected void initialize() {
    setTimeout(3);
  }

  @Override
  protected void execute() {
    Subsystems.hatch.hatchRelease();
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