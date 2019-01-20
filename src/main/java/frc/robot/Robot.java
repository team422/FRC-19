package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import frc.robot.subsystems.Subsystems;
import frc.robot.userinterface.UserInterface;

public class Robot extends TimedRobot {

    private UsbCamera camera;
    private CommandGroup autonomous;
    private NetworkTableEntry blockX;
    private NetworkTableEntry blockY;
    private NetworkTableEntry blockW;
    private NetworkTableEntry blockH;
    private NetworkTableEntry blockArea;

    public void robotInit() {
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable pixy = inst.getTable("pixy");
        blockX = pixy.getEntry("blockX");
        blockY = pixy.getEntry("blockY");
        blockW = pixy.getEntry("blockW");
        blockH = pixy.getEntry("blockH");
        blockArea = pixy.getEntry("blockArea");
    }

    public void disabledInit() {}

    public void autonomousInit() {}

    public void teleopInit() {}

    public void disabledPeriodic() {
        printDataToSmartDashboard();
    }
    
    public void autonomousPeriodic() {
        printDataToSmartDashboard();
    }

    public void teleopPeriodic() {
        System.out.println("Test");
        printDataToSmartDashboard();
    }

    private void printDataToSmartDashboard() {
        SmartDashboard.putNumber("blockX", blockX.getDouble(404));
        SmartDashboard.putNumber("blockY", blockY.getDouble(404));
        SmartDashboard.putNumber("blockW", blockW.getDouble(404));
        SmartDashboard.putNumber("blockH", blockH.getDouble(404));
        SmartDashboard.putNumber("blockArea", blockArea.getDouble(404));
    }
}