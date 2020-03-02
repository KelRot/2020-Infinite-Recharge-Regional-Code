/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.analog.adis16470.frc.ADIS16470_IMU;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;


public class Drive extends SubsystemBase {
  private final Talon topLeft = new Talon(DriveConstants.onSol);
  private final Talon topRight = new Talon(DriveConstants.onSag);

  private final Talon bottomLeft = new Talon(DriveConstants.arkaSol);
  private final Talon bottomRight = new Talon(DriveConstants.arkaSag);

  private final SpeedControllerGroup left = new SpeedControllerGroup(topLeft, bottomLeft);
  private final SpeedControllerGroup right = new SpeedControllerGroup(topRight, bottomRight);

  private final Encoder leftDriveEncoder = new Encoder(DriveConstants.DriveLeftEncoderPorts[0],
      DriveConstants.DriveLeftEncoderPorts[1]);
  private final Encoder rightDriveEncoder = new Encoder(DriveConstants.DriveRightEncoderPorts[0],
      DriveConstants.DriveRightEncoderPorts[1]);

  private final DifferentialDrive drive = new DifferentialDrive(left, right);
  private final ADIS16470_IMU gyro = new ADIS16470_IMU();

  private final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(DriveConstants.kS, DriveConstants.kV,
      DriveConstants.kA);

  

  public Drive() {
     leftDriveEncoder.setDistancePerPulse(DriveConstants.distancePerPulse);
     rightDriveEncoder.setDistancePerPulse(DriveConstants.distancePerPulse);
  }

  @Override
  public void periodic() {
   
  }

  public double getEncoderAverages(){

    return leftDriveEncoder.getDistance()+rightDriveEncoder.getDistance() /2f;

  }

  public double getGyroHeading(){
    return gyro.getAngle();
  }

  public void resetGyro(){
    gyro.reset();
  }

  public void setVoltage(double voltage){

    left.setVoltage(voltage);
    right.setVoltage(-voltage);
    
  }

  public DifferentialDrive getDifferentialDrive() {
    return drive;
  }

  public SimpleMotorFeedforward getFeedforward() {
    return feedforward;
  }
}
