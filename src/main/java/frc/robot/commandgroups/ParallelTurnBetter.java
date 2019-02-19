package frc.robot.commandgroups;

import frc.robot.commands.other.*;
import edu.wpi.first.wpilibj.command.CommandGroup;
// import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.RobotMap;

public class ParallelTurnBetter extends CommandGroup {

  public ParallelTurnBetter() {
    addSequential(new IdealFinder());
    //addSequential(new WaitCommand(3));
    addSequential(new Turn(RobotMap.getIdealAngle(), 0.4, 100000));
    addSequential(new DriveStraight(RobotMap.getDriveOffset(), 0.2, 100000000));
    if(RobotMap.getTurnDirection() >= 0) {
      addSequential(new Turn(90, 0.4, 100000));
    } else {
      addSequential(new Turn(-90, 0.4, 100000));
    }
    addSequential(new DriveStraight(5, 0.2, 20));
  }
  
}