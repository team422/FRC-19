package frc.robot.commands.other;

import edu.wpi.first.wpilibj.command.Command;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Subsystems;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;

public class TurnUntilLine extends Command {

    private double degrees;
    private double speed;
    private boolean isCorrecting = false;
    private NetworkTableEntry old;
    private NetworkTableEntry current;
    private NetworkTable pixy;

    public TurnUntilLine(double Speed, double Timeout) {
        super("TurnUntilLine");
        requires(Subsystems.driveBase);
        speed = Speed;
        setTimeout(Timeout);
    }

    public void initialize() {
        Subsystems.driveBase.zeroGyroAngle();
        Subsystems.driveBase.zeroEncoderPosition();
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        pixy = inst.getTable("pixy");
        old = pixy.getEntry("lineY0");
    }

    public void execute() {
      Subsystems.driveBase.setMotors(speed, -speed);
      current = pixy.getEntry("lineY0");
    }

    public boolean isFinished() {
      return (old != current);
    }

    public void interrupted() {
        Subsystems.driveBase.stopMotors();
    }

    public void end() {
        Subsystems.driveBase.stopMotors();
    }

}