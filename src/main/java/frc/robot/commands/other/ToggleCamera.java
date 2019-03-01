package frc.robot.commands.other;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;
import frc.robot.Robot;

public class ToggleCamera extends Command {

    public ToggleCamera() {
        super("ToggleCamera");
    }

    @Override
    public void initialize() {
        if(RobotMap.getCamera()) {
            RobotMap.setCamera(false);
            Robot.server.setSource(Robot.camera2);
        } else {
            RobotMap.setCamera(true);
            Robot.server.setSource(Robot.camera1);
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