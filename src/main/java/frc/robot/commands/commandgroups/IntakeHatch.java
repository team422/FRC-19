package frc.robot.commands.commandgroups;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class IntakeHatch extends CommandGroup {

  public IntakeHatch() {
    //vision tracking and driving stuff
    addSequential(new HatchClamp());
    addSequential(new DriveStraight(-3, 0.1, 3));
  }

}