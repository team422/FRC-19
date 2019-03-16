package frc.robot.commands.other;

import edu.wpi.first.wpilibj.command.Command;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Subsystems;
import frc.robot.RobotMap;

public class Turn90 extends Command {

    private double degrees;
    private double speed;
    private boolean isCorrecting = false;


    public Turn90(double Speed, double Timeout) {
        super("Turn90");
        requires(Subsystems.driveBase);
        degrees = 90;
        speed = Speed;
        setTimeout(Timeout);
    }

    public void initialize() {
        Subsystems.driveBase.zeroGyroAngle();
        Subsystems.driveBase.zeroEncoderPosition();
        if(RobotMap.getIdealAngle() >= 0) {
            degrees = -90;
          } else {
            degrees = 90;
        }
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
        Subsystems.driveBase.stopMotors();
    }

    public void end() {
        Subsystems.driveBase.stopMotors();
    }

}