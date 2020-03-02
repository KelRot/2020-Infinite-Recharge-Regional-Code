/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.AutoShooting;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.TurnAngle;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
    private final Joystick joystick = new Joystick(0);

    private final Shooter shooter_subsystem = new Shooter();
    private final Feeder feeder_subystem = new Feeder(); 
    public final Drive driveSubsystem= new Drive();

    private final DriveCommand driveCommand = new DriveCommand(driveSubsystem, joystick);
    private final AutoShooting autoShooting=new AutoShooting(shooter_subsystem, feeder_subystem);
    private final DriveDistance autoCommand = new DriveDistance(driveSubsystem, 3);
   
     
  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    driveSubsystem.setDefaultCommand(driveCommand);
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    new JoystickButton(joystick, Button.kStart.value).whenHeld(autoShooting);                                                                                                                                                                          
    new JoystickButton(joystick, Button.kX.value).whileHeld(new RunCommand(() -> shooter_subsystem.useShooters(ShooterConstants.setPointRPS),shooter_subsystem) ).whenReleased(new InstantCommand(shooter_subsystem::stopShooters));
    new JoystickButton(joystick,Button.kB.value).whenPressed(new InstantCommand(shooter_subsystem::stopShooters));
    new JoystickButton(joystick, Button.kY.value).whileHeld(new InstantCommand( ()-> feeder_subystem.runFeeder(false)  ) ).whenReleased(new InstantCommand(feeder_subystem::stopFeeder));
    new JoystickButton(joystick, Button.kA.value).whileHeld(new InstantCommand( ()-> feeder_subystem.runFeeder(true)  ) ).whenReleased(new InstantCommand(feeder_subystem::stopFeeder));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autoCommand;
  }
}
