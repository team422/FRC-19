package main.java.frc.robot.userinterface;

import main.java.frc.robot.RobotMap;

public class UserInterface {

    public static final XboxController driverController = new XboxController(RobotMap.driverXboxController);
    public static final XboxController operatorController = new XboxController(RobotMap.operatorXboxController);

}