package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Subsystems;
import frc.robot.RobotMap;

public class Turn extends Command {

    private double degrees;
    private double speed;
    private double timeout;
    private boolean isCorrecting = false;


    public Turn(double Degrees, double Speed, double Timeout) {
        super("Turn");
        requires(Subsystems.driveBase);
        degrees = Degrees;
        speed = Speed;
        setTimeout(Timeout);
    }

    public void initialize() {
        degrees = RobotMap.getIdealAngle();
        System.out.println("idealAngle: " + RobotMap.getIdealAngle());
        Subsystems.driveBase.zeroGyroAngle();
        Subsystems.driveBase.zeroEncoderPosition();
    }

    public void execute() {
        if ((degrees > 0) && !isCorrecting) {
            // Turning to the right
            Subsystems.driveBase.setMotors(-speed, speed);
        } else if ((degrees < 0) && !isCorrecting) {
            // Turning to the left
            Subsystems.driveBase.setMotors(speed, -speed);
        } else if (degrees > 0) {
            // Turned to the right, but correcting to the left
            Subsystems.driveBase.setMotors(speed / 2, -speed / 2);
        } else {
            // Turned to the left, but correcting to the right
            Subsystems.driveBase.setMotors(-speed / 2, speed / 2);
        }
        //System.out.println("Gyro angle: " + Subsystems.driveBase.getGyroAngle());
    }

    public boolean isFinished() {
        double angle = Subsystems.driveBase.getGyroAngle();
        if (degrees > 0) {
            // Turning to the right
            if (!isCorrecting) {
                if (angle > degrees) {
                    isCorrecting = true;
                }
                return isTimedOut();
            }
            return (angle < degrees) || isTimedOut();
        } else {
            // Turning to the left
            if (!isCorrecting) {
                if (angle < degrees) {
                    isCorrecting = true;
                }
                return isTimedOut();
            }
            return (angle > degrees) || isTimedOut();
        }
    }

    public void interrupted() {
        Subsystems.driveBase.setMotors(0,0);
    }

    public void end() {
        Subsystems.driveBase.setMotors(0,0);
    }

}