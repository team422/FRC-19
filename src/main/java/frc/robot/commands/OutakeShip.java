package main.java.frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import main.java.frc.robot.subsystems.Subsystems;

public class OutakeShip extends CommandGroup {

  public OutakeShip() {
    //vision tracking onto velcro
    addSequential(new HatchRelease());
    addSequential(new DriveStraight(-5, 0.1, 3));
  }

}