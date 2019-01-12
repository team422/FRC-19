package main.java.frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import main.java.frc.robot.subsystems.Subsystems;
import main.java.frc.robot.userinterface.UserInterface;

public class TankDrive extends Command {

  private double updatedSpeed = 0;
  private double updatedRotation = 0;
  private static final double maxChange = 0.06;
  
  public TankDrive() {
    super("TankDrive");
    requires(Subsystems.driveBase);
  }
  
  protected void execute() {
    double speed;
    double rotation;

    if (UserInterface.driverController.getRightJoystickY() < -0.1) {
      speed = -(Math.pow(UserInterface.driverController.getRightJoystickY(), 2));
    } else if (UserInterface.driverController.getRightJoystickY() > 0.1) {
      speed = Math.pow(UserInterface.driverController.getRightJoystickY(), 2);
    } else {
      speed = 0;
    }
    updatedSpeed = speed;

    if (UserInterface.driverController.getLeftJoystickX() < -0.1) {
      rotation = -(Math.pow(UserInterface.driverController.getLeftJoystickX(), 3));
    } else if (UserInterface.driverController.getLeftJoystickX() > 0.1) {
      rotation = Math.pow(UserInterface.driverController.getLeftJoystickX(), 3);
    } else {
      rotation = 0;
    }
    updatedRotation = rotation;    

    double speedDifference = speed - updatedSpeed;
    if (speedDifference > maxChange) {
      speed = updatedSpeed + maxChange;
    } else if (speedDifference < -maxChange) {
      speed = updatedSpeed - maxChange;
    }

    double rotationDifference = rotation - updatedRotation;
    if (rotationDifference > maxChange) {
      rotation = updatedRotation + maxChange;
    } else if (rotationDifference < -maxChange) {
      rotation = updatedRotation - maxChange;
    }

    Subsystems.driveBase.cheesyDrive.curvatureDrive(rotation, speed, true);
  }

  protected boolean isFinished() {
    return false;
  }

  protected void interrupted() {}

  protected void end() {}
}