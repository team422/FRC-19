package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.ComplicatedTrackLine;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.RobotMap;

public class ParallelTurnBetter extends CommandGroup {  

  public ParallelTurnBetter() {
    addSequential(new ComplicatedTrackLine());
    //addSequential(new WaitCommand(3));
    addSequential(new Turn(RobotMap.getIdeal(), 0.4, 100000));
  }

}