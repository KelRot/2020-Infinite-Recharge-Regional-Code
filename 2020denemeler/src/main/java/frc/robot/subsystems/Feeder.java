/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.FeederConstants;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;

public class Feeder extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
  private VictorSPX motor1;
  private VictorSPX motor2;
  private AnalogInput intake_Sensor;
  private AnalogInput shooter_Sensor;
  public Feeder() {
    motor1=new VictorSPX(Constants.FeederConstants.upMotorID);
    motor2=new VictorSPX(Constants.FeederConstants.downMotorID);
    
    intake_Sensor=new AnalogInput(Constants.FeederConstants.intakeSensorPort);
    shooter_Sensor=new AnalogInput(Constants.FeederConstants.shooterSensorPort);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void runFeeder(Boolean forward)
  {
    double speed=FeederConstants.feederSpeed;
    if(!forward)
      speed= -speed;
    motor1.set(VictorSPXControlMode.PercentOutput,speed);
    motor2.set(VictorSPXControlMode.PercentOutput,speed);
  }
  public void runFeederBackwards( )
  {
    double speed=FeederConstants.feederSpeed;
    speed= -speed;
    motor1.set(VictorSPXControlMode.PercentOutput,speed);
    motor2.set(VictorSPXControlMode.PercentOutput,speed);
  }

  public void stopFeeder()
  {
    double speed=0.0;
    motor1.set(VictorSPXControlMode.PercentOutput,speed);
    motor2.set(VictorSPXControlMode.PercentOutput,speed);
  }
  public boolean getIntakeSensor(){//ölç
    return intake_Sensor.getVoltage()>0.5;
  }
  public boolean getShooterSensor(){
    return shooter_Sensor.getVoltage()>0.5;

  }
}
