package frc.robot.commands.climber;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class ClimberBackLegsUp extends Command {

    private double speed;

    public ClimberBackLegsUp (double Speed, double Timeout) {
        super("ClimberBackLegsUp");
        requires(Subsystems.climber);
        speed = Speed;
        setTimeout(Timeout);
    } 

    @Override
    protected void initialize() {
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