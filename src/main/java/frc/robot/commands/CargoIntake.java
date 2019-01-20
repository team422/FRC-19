package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CargoIntake extends CommandGroup {
    public CargoIntake() {
        addSequential(new RollBallIntake());
        addParallel(new ParallelEscalator());
    }
}