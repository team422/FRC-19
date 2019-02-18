package frc.robot.commandgroups;

import frc.robot.commands.cargo.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class CargoShipOutake extends CommandGroup {
    public CargoShipOutake() {
        addSequential(new CargoFlapDown());
        addSequential(new CargoEscalatorOut());
    }
}