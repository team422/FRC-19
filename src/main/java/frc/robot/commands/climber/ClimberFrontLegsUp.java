package frc.robot.commands.climber;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;

public class ClimberFrontLegsUp extends Command {

    private double speed;

    public ClimberFrontLegsUp (double Speed, double Timeout) {
        super("ClimberFrontLegsUp");
        requires(Subsystems.climber);
        speed = Speed;
        setTimeout(Timeout);
    } 

    @Override
    protected void initialize() {
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