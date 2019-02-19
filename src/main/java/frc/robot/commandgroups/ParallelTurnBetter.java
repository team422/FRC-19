package frc.robot.commandgroups;

import frc.robot.commands.other.*;
import edu.wpi.first.wpilibj.command.CommandGroup;
// import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.RobotMap;

public class ParallelTurnBetter extends CommandGroup {  

  public ParallelTurnBetter() {
    addSequential(new ComplicatedTrackLine());
    //addSequential(new WaitCommand(3));
    addSequential(new Turn(RobotMap.getIdealAngle(), 0.4, 100000));
  }

}