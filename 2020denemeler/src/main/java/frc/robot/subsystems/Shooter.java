/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Encoder;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;


public class Shooter extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
  
   private final VictorSPX leftMotor= new VictorSPX(ShooterConstants.LeftMotorID);
   private final VictorSPX rightMotor= new VictorSPX(ShooterConstants.RightMotorID);


  

   private final Encoder leftEncoder = new Encoder(ShooterConstants.LeftEncoderPorts[0],
                                                   ShooterConstants.LeftEncoderPorts[1]);
                                            
   private final Encoder rightEncoder = new Encoder(ShooterConstants.RightEncoderPorts[0],
                                                     ShooterConstants.RightEncoderPorts[1]);

    private final PIDController controllerLeft = new PIDController(ShooterConstants.kP , 0,0);
    private final PIDController controllerRight = new PIDController(ShooterConstants.kP , 0,0);

    private final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(0, ShooterConstants.kV);
   
   
  public Shooter(){
  controllerLeft.setTolerance(ShooterConstants.positionSetPointTolerance,ShooterConstants.velocitySetPointTolerance);
  controllerRight.setTolerance(ShooterConstants.positionSetPointTolerance,ShooterConstants.velocitySetPointTolerance);
       
  leftMotor.configVoltageCompSaturation(12);
  rightMotor.configVoltageCompSaturation(12);

  leftMotor.enableVoltageCompensation(true);
  rightMotor.enableVoltageCompensation(true);


  leftEncoder.setDistancePerPulse(ShooterConstants.encoderRPP);
  rightEncoder.setDistancePerPulse(ShooterConstants.encoderRPP);
      
         
        
}                                          

  @Override
  public void periodic() {
    System.out.print("SOL ENCODER: "+leftEncoder.get());
    System.out.print("RIGHT ENCODER: "+rightEncoder.get()+"\n");

  }



  public void useShooters(double setPoint){

    //!!!!!!!!!! COMMENT THIS WHEN YOU GET DISTANCE DATA !!!!!!!!!1
    setPoint = ShooterConstants.setPointRPS;

    double leftOutput = feedforward.calculate(setPoint) + controllerLeft.calculate(leftEncoder.getRate(), setPoint);
    double rightOutput = feedforward.calculate(setPoint) + controllerRight.calculate(rightEncoder.getRate(), setPoint);
    
    leftMotor.set(ControlMode.Current,leftOutput);
    rightMotor.set(ControlMode.Current, rightOutput);
    

  }

  public void useShootersFree(){
    leftMotor.set(ControlMode.PercentOutput, ShooterConstants.freeSpeed);
    rightMotor.set(ControlMode.PercentOutput  , -ShooterConstants.freeSpeed);
  }

  public boolean atSetpoint(){

    return controllerRight.atSetpoint() && controllerLeft.atSetpoint();
  }

 



  public void stopShooters(){
    leftMotor.set(VictorSPXControlMode.PercentOutput,0);
    rightMotor.set(VictorSPXControlMode.PercentOutput,0);
  }
}
