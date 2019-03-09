package frc.robot.commandgroups;

import frc.robot.commands.other.*;
import edu.wpi.first.wpilibj.command.CommandGroup;
// import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.RobotMap;

public class GaffTapeTrack extends CommandGroup {

  public GaffTapeTrack() {
    addSequential(new IdealFinder());
    //addSequential(new WaitCommand(3));
    addSequential(new TurnIdeal(0.25, 100000));
    addSequential(new DriveStraightIdeal(0.2, 100000000));
    //addSequential(new TurnUntilLine(0.3,1000000));
    if(RobotMap.getIdealAngle() >= 0) {
      addSequential(new Turn(-90, 0.3, 100000));
      //addSequential(new TurnUntilLine());
    } else {
      addSequential(new Turn(90, 0.3, 100000));
      //addSequential(new TurnUntilLine());
    }
    //addSequential(new DriveStraight(5, 0.2, 20));
  }
}