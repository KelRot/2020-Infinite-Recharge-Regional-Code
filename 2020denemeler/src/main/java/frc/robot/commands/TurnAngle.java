package frc.robot.commands;


import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class TurnAngle extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drive m_subsystem;
  private DifferentialDrive drive;
  private final float angle;
  private final PIDController gyroPID= new PIDController(0.5f, 0f,0f);


  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public TurnAngle(Drive subsystem,float _angle) {
    m_subsystem = subsystem;
    angle= _angle;
    drive=m_subsystem.getDifferentialDrive();
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      gyroPID.setSetpoint(angle);
      m_subsystem.resetGyro();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {    
    double rotation = gyroPID.calculate(m_subsystem.getGyroHeading());
    drive.arcadeDrive(0, rotation);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return gyroPID.atSetpoint();

  }
}
