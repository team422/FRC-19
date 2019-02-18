package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
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

    /** Sets throttle for driveBase to the left stick Y-axis and sets the rotation
     *  for driveBase to the right stick X-axis on on the driverXboxController*/ 
    if (RobotMap.isLeftThrottle) {
      if (UserInterface.driverController.getLeftJoystickY() < -0.1) {
        speed = -(Math.pow(UserInterface.driverController.getLeftJoystickY(), 2));
      } else if (UserInterface.driverController.getLeftJoystickY() > 0.1) {
        speed = (Math.pow(UserInterface.driverController.getLeftJoystickY(), 2));
      } else {
        speed = 0;
      }
      updatedSpeed = speed;
  
      if (UserInterface.driverController.getRightJoystickX() < -0.05) {
        rotation = (Math.pow(UserInterface.driverController.getRightJoystickX(), 5));
      } else if (UserInterface.driverController.getRightJoystickX() > 0.05) {
        rotation = (Math.pow(UserInterface.driverController.getRightJoystickX(), 5));
      } else {
        rotation = 0;
      }
    /** Sets throttle for driveBase to the right stick Y-axis and sets the rotation
     *  for driveBase to the left stick X-axis on on the driverXboxController*/ 
    } else {
      if (UserInterface.driverController.getRightJoystickY() < -0.1) {
        speed = -(Math.pow(UserInterface.driverController.getRightJoystickY(), 2));
      } else if (UserInterface.driverController.getRightJoystickY() > 0.1) {
        speed = (Math.pow(UserInterface.driverController.getRightJoystickY(), 2));
      } else {
        speed = 0;
      }
      updatedSpeed = speed;

      if (UserInterface.driverController.getLeftJoystickX() < -0.05) {
        rotation = (Math.pow(UserInterface.driverController.getLeftJoystickX(), 5));
      } else if (UserInterface.driverController.getLeftJoystickX() > 0.05) {
        rotation = (Math.pow(UserInterface.driverController.getLeftJoystickX(), 5));
      } else {
        rotation = 0;
      }
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
    /** Because of a weird glitch with how curvatureDrive is set up,
     *  the rotation actually goes in as the first input, followed by the speed,
     *  rather than speed then rotation */
    Subsystems.driveBase.cheesyDrive.curvatureDrive(RobotMap.getRot() * rotation, RobotMap.getCap() * speed, true);
  }

  protected boolean isFinished() {
    return false;
  }

  protected void interrupted() {}

  protected void end() {}
}