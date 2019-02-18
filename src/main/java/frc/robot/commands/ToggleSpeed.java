package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;

public class ToggleSpeed extends Command {

    public ToggleSpeed() {
        super("ToggleSpeed");
    }

    @Override
    public void initialize() {
        if(RobotMap.isToggledFast) {
            RobotMap.setSpeedAndRotationCaps(0.2, 0.1);
            RobotMap.isToggledFast = false;
        } else {
            RobotMap.setSpeedAndRotationCaps(0.5, 0.3);
            RobotMap.isToggledFast = true;
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