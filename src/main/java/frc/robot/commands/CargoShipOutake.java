package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CargoShipOutake extends CommandGroup {
    public CargoShipOutake() {
        addSequential(new FlapDown());
        addSequential(new RollEscalator());
    }
}