package frc.robot.commandgroups;

import frc.robot.commands.hatch.*;
import frc.robot.commands.other.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class IntakeHatch extends CommandGroup {

  public IntakeHatch() {
    //vision tracking and driving stuff
    addSequential(new HatchClamp());
    addSequential(new DriveStraight(-3, 0.1, 3));
  }

}