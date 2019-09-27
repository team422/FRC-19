package frc.robot.commandgroups;

import frc.robot.commands.other.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Rotations extends CommandGroup {

  public Rotations() {
    // *** Write your commands here! ***

    addSequential(new DriveStraight(10, 0.3, 5));
  }

}