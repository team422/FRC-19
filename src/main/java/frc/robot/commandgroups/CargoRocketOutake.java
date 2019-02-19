package frc.robot.commandgroups;

import frc.robot.commands.cargo.*;
import edu.wpi.first.wpilibj.command.CommandGroup;


public class CargoRocketOutake extends CommandGroup {
    public CargoRocketOutake() {
        addSequential(new CargoFlapUp());
        addSequential(new CargoEscalatorOut());
    }
}