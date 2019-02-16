package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;

public class TankDrive extends Command {

  private double updatedSpeed = 0;
  private double updatedRotation = 0;
  private static final double maxChange = 0.06;
  
  public TankDrive() {
    super("TankDrive");
    requires(Subsystems.driveBase);
  }

  protected void initialize() {}
  
  protected void execute() {
    double speed;
    double rotation;

    //System.out.println("Running Tankdrive");

    if (UserInterface.driverController.getRightJoystickY() < -0.1) {
      speed = -(Math.pow(UserInterface.driverController.getRightJoystickY(), 2));
    } else if (UserInterface.driverController.getRightJoystickY() > 0.1) {
      speed = Math.pow(UserInterface.driverController.getRightJoystickY(), 2);
    } else {
      speed = 0;
    }
    updatedSpeed = speed;

    if (UserInterface.driverController.getLeftJoystickX() < -0.05) {
      rotation = (Math.pow(UserInterface.driverController.getLeftJoystickX(), 5));
    } else if (UserInterface.driverController.getLeftJoystickX() > 0.05) {
      rotation = Math.pow(UserInterface.driverController.getLeftJoystickX(), 5);
    } else {
      rotation = 0;
    }
    updatedRotation = -rotation;    

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

    Subsystems.driveBase.cheesyDrive.curvatureDrive(0.5*rotation, 0.5*speed, true);
  }

  protected boolean isFinished() {
    return false;
  }

  protected void interrupted() {}

  protected void end() {}
}