package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.ComplicatedTrackLine;
import frc.robot.RobotMap;

public class ParallelTurnBetter extends CommandGroup {

  public ParallelTurnBetter() {
    addSequential(new ComplicatedTrackLine());
    System.out.println("idealAngle: " + RobotMap.idealAngle);
    addSequential(new Turn(RobotMap.idealAngle, 0.2, 100000));
  }

}