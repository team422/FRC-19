package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class FrontToVerticalClimber extends Command {

    private double speed;
    private double timeout;

    public FrontToVerticalClimber (double Speed, double TimeOut) {
        super("FrontToVerticalClimber");
        requires(Subsystems.climber);
        timeout = TimeOut;
        speed = Speed;
    } 

    @Override
    protected void initialize() {
        setTimeout(timeout);
        Subsystems.climber.zeroFrontClimbEncoders();
    }
  
    @Override
    protected void execute() {
        Subsystems.climber.setFrontClimbMotors(speed);
    }

    @Override
    protected boolean isFinished() {
        return (Subsystems.climber.getFrontClimbMotors() >= convertAngleToTicks(40)) || isTimedOut();
    }
  
    @Override
    protected void interrupted() {
      Subsystems.climber.setFrontClimbMotors(0.0);
    }        
  
    @Override
    protected void end() {
      Subsystems.climber.setFrontClimbMotors(0.0);
    }

    public double convertAngleToTicks(double angle) {
        return (11.3777777778 * angle);
    }
}