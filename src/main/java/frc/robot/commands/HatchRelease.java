package main.java.frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import main.java.frc.robot.subsystems.Subsystems;

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
    return false;
  }

  @Override
  protected void interrupted() {}
  
  @Override
  protected void end() {}

}