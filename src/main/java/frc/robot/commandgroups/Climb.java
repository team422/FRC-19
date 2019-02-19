package frc.robot.commandgroups;

import frc.robot.commands.climber.*;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class Climb extends CommandGroup {

  public Climb() {
    addSequential(new ClimberDriveToWall(0.1,8));
    addParallel(new ClimberFrontLegsUp(0.1,8)); 
    addSequential(new WaitCommand(0.2));
    addSequential(new ClimberBackLegsUp(0.1,8));
    addParallel(new ClimberDriveToWall(0.5,8));
  }
}