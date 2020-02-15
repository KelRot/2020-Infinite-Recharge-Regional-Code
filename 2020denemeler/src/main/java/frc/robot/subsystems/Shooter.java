/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;


public class Shooter extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
  
   private final VictorSP leftMotor= new VictorSP(ShooterConstants.MotorPWM1);
   private final VictorSP rightMotor= new VictorSP(ShooterConstants.MotorPWM2);

   private final VictorSP feederMotor = new VictorSP(ShooterConstants.feederMotorPWM);

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
       
  leftEncoder.setDistancePerPulse(ShooterConstants.encoderPPR);
  rightEncoder.setDistancePerPulse(ShooterConstants.encoderPPR);
      
         
        
}                                          

  @Override
  public void periodic() {

}



  public void useShooters(double setPoint){

    //!!!!!!!!!! COMMENT THIS WHEN YOU GET DISTANCE DATA !!!!!!!!!1
    setPoint = ShooterConstants.setPointRPS;

    double leftOutput = feedforward.calculate(setPoint) + controllerLeft.calculate(leftEncoder.getRate(), setPoint);
    double rightOutput = feedforward.calculate(setPoint) + controllerRight.calculate(rightEncoder.getRate(), setPoint);
    
    leftMotor.setVoltage(leftOutput);
    rightMotor.setVoltage(rightOutput);
    

  }

  public boolean atSetpoint(){

    return controllerRight.atSetpoint() && controllerLeft.atSetpoint();
  }

  public void useFeeder(boolean reversed){
      if(reversed)
      feederMotor.setSpeed(-ShooterConstants.feederSpeed);
      else
      feederMotor.setSpeed(-ShooterConstants.feederSpeed);
  }


  public void stopFeeder(){
   feederMotor.setSpeed(0);
  }

  public void stopShooters(){
    leftMotor.setVoltage(0);
    rightMotor.setVoltage(0);
  }
}
