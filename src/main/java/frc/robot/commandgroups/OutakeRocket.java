package frc.robot.commandgroups;

import frc.robot.commands.hatch.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class OutakeRocket extends CommandGroup {

  public OutakeRocket() {
    //vision tracking onto velcro
    addSequential(new HatchRelease());
    addSequential(new HatchArmIn());
  }

}