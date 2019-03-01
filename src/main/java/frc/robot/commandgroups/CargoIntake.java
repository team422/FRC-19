package frc.robot.commandgroups;

import frc.robot.commands.cargo.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class CargoIntake extends CommandGroup {
    public CargoIntake() {
        addSequential(new CargoIntake());
        addParallel(new CargoEscalatorOut());
    }
}