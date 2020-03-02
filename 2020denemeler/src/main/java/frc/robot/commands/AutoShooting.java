
package frc.robot.commands;


import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class AutoShooting extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter shooter_subsystem;
  private final Feeder feeder_subsystem; 
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public AutoShooting(Shooter shooter,Feeder feeder) {
    shooter_subsystem = shooter;
    feeder_subsystem  =  feeder;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter_subsystem,feeder_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(feeder_subsystem.getShooterSensor())
    feeder_subsystem.runFeeder(false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

      shooter_subsystem.useShooters(3000);
      if(shooter_subsystem.atSetpoint())
      {
        feeder_subsystem.runFeeder(true); 
      }
      else if(!feeder_subsystem.getShooterSensor() &&  !shooter_subsystem.atSetpoint())
      {
        feeder_subsystem.stopFeeder();
      }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
      shooter_subsystem.stopShooters();
      feeder_subsystem.stopFeeder();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
