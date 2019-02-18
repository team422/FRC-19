package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import frc.robot.RobotMap;

public class ToggleSpeed extends Command {

    public ToggleSpeed() {
        super("ToggleSpeed");
    }

    @Override
    public void initialize() {
        if(RobotMap.getCap() == 0.5) {
            RobotMap.setCap(0.2, 0.1);
        } else {
            RobotMap.setCap(0.5, 0.3);
        }
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void interrupted() {
       
    }

    @Override
    public void end() {
       
    }
}