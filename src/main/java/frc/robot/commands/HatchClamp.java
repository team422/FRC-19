package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class HatchClamp extends Command {

  public HatchClamp() {
    super("HatchClamp");
    requires(Subsystems.hatch);
  }

  @Override
  protected void initialize() {
    setTimeout(3);
  }

  @Override
  protected void execute() {
    Subsystems.hatch.hatchClamp();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void interrupted() {}
  
  @Override
  protected void end() {}

}