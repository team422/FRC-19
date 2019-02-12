package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class Climb extends CommandGroup {

  public Climb() {
    addSequential(new DriveToWall(0.1,8));
    addParallel(new FrontToVerticalClimber(0.1,8)); 
    addSequential(new WaitCommand(0.2));
    addSequential(new BackPushClimber(0.1,8));
    addParallel(new DriveToWall(0.5,8));
  }
}