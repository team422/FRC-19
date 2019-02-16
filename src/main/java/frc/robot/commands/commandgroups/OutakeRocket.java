package frc.robot.commands.commandgroups;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class OutakeRocket extends CommandGroup {

  public OutakeRocket() {
    //vision tracking onto velcro
    addSequential(new PunchOutwards());
    addSequential(new HatchRelease());
    addSequential(new PunchInwards());
  }

}