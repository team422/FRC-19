package frc.robot.commands.hatch;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class HatchArmIn extends Command {

  public HatchArmIn() {
    super("HatchArmIn");
    requires(Subsystems.hatch);
  }

  @Override
  protected void initialize() { }

  @Override
  protected void execute() {
    Subsystems.hatch.armIn();
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void interrupted() { }
  
  @Override
  protected void end() { }

}