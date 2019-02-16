package frc.robot.commands.commandgroups;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.command.CommandGroup;


public class CargoRocketOutake extends CommandGroup {
    public CargoRocketOutake() {
        addSequential(new FlapUp());
        addSequential(new RollEscalator());
    }
}