package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class BackPushClimber extends Command {

    private double speed;
    private double timeout;

    public BackPushClimber (double Speed, double TimeOut) {
        super("BackPushClimber");
        timeout = TimeOut;
        requires(Subsystems.climber);
        speed = Speed;
    } 

    @Override
    protected void initialize() {
        setTimeout(timeout);
        Subsystems.climber.zeroBackClimbEncoders();
    }
  
    @Override
    protected void execute() {
        Subsystems.climber.setBackClimbMotors(speed);
    }
  
    @Override
    protected boolean isFinished() {
        return (Subsystems.climber.getBackClimbMotors() >= convertAngleToTicks(120)) || isTimedOut();
    }

    @Override
    protected void interrupted() {
      Subsystems.climber.setFrontClimbMotors(0.0);
    } 

    @Override
    protected void end() {
      Subsystems.climber.setBackClimbMotors(0.0);
    }

    public double convertAngleToTicks(double angle) {
        return (11.3777777778 * angle);
    }
}