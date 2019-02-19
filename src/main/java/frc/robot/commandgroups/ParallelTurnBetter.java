package frc.robot.commandgroups;

import frc.robot.commands.other.*;
import edu.wpi.first.wpilibj.command.CommandGroup;
// import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;
import frc.robot.RobotMap;

public class ParallelTurnBetter extends CommandGroup {  

  private NetworkTableEntry lineX0;

  public ParallelTurnBetter() {
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable pixy = inst.getTable("pixy");
    lineX0 = pixy.getEntry("lineX0");
    addSequential(new IdealFinder());
    //addSequential(new WaitCommand(3));
    addSequential(new Turn(RobotMap.getIdealAngle(), 0.4, 100000));
    if(lineX0.getDouble(-404) >= 39) {
      addSequential(new Turn(90, 0.4, 100000));
      addSequential(new Turn(-90, 0.4, 100000));
    } else {
      addSequential(new Turn(-90, 0.4, 100000));
      addSequential(new Turn(90, 0.4, 100000));
    }
    addSequential(new DriveStraight(18,0.2,20));
  }

}