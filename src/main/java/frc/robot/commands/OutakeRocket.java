package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subsystems.Subsystems;

public class OutakeRocket extends CommandGroup {

  public OutakeRocket() {
    //vision tracking onto velcro
    addSequential(new PunchOutwards());
    addSequential(new HatchRelease());
    addSequential(new PunchInwards());
  }

}