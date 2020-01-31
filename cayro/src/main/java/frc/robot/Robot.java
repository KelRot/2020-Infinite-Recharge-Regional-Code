/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import com.analog.adis16470.frc.ADIS16470_IMU;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Encoder;


public class Robot extends TimedRobot {

  double ilerlemeHizi;
  boolean mode=true,ilerliyor=true;


  Talon solOn= new Talon(6); Talon solArka= new Talon(4); 
  Talon sagOn= new Talon(2); Talon sagArka= new Talon(1);

  double gyro_kI=0.07;
  PIDController gyroPID = new PIDController(0.04, gyro_kI, 0.01);

  SpeedControllerGroup sol= new SpeedControllerGroup(solOn, solArka);
  SpeedControllerGroup sag= new SpeedControllerGroup(sagOn, sagArka);
 
  private final DifferentialDrive drive
  = new DifferentialDrive(sol,sag);

  public int step=0;
  //
  public double distance;
  public double donusSetPoint=0.0;
  public double turningValue;
  private double kAngleSetpoint = 0.0;
//ENCODER
  private final Encoder sagCoder = new Encoder(2, 3, true );
 
  double aci=0;
 
  private final Joystick kumanda = new Joystick(1);
  private ADIS16470_IMU gyro= new ADIS16470_IMU();

  @Override
  public void robotInit() 
  {
    gyro.reset();;
    sagCoder.reset();
    sagCoder.setSamplesToAverage(5);
    sagCoder.setDistancePerPulse(Math.PI *0.15 /1024);
    gyroPID.setTolerance(2);
    
  }

   @Override
  public void robotPeriodic() 
  {  
      aci = gyro.getAngle()%360;
  
    gyroPID.setSetpoint(kAngleSetpoint);

    turningValue = -gyroPID.calculate(aci);
  
    if(Math.abs(gyroPID.getPositionError() )<10)
    gyroPID.setI(gyro_kI);
  
    else
    gyroPID.setI(0);
   // System.out.println("Aldıgı Yol: "+ sagCoder.getDistance());
   // System.out.println("getRate:"+gyro.getRate());
  //  System.out.println("Aci: "+aci);
   // System.out.println("AngleSetpoint: "+kAngleSetpoint);
  }
@Override
public void autonomousInit() {
  step=0;
}
  @Override
public void autonomousPeriodic() 
{
 // System.out.println(kAngleSetpoint);
  System.out.println(step);

  switch (step) {
    case 0:
      drive.arcadeDrive(0.5, turningValue);
      if(sagCoder.getDistance()>=2){
      step=1;
      kAngleSetpoint=90;
    }
      break;
    case 1:
      drive.arcadeDrive(0.0,turningValue);
    if(gyroPID.atSetpoint() &&  Math.abs(gyro.getRate())<=0.5)
    {
    step=2;
    sagCoder.reset(); 
    gyro.reset();
    kAngleSetpoint=0;  
    } 
    break;

    case 2:
      drive.arcadeDrive(0.5, turningValue);
      if(sagCoder.getDistance()>=2){
      kAngleSetpoint=90;
      step++;
    }
      break;
    case 3:
      drive.arcadeDrive(0.0,turningValue);
    if(gyroPID.atSetpoint() &&  Math.abs(gyro.getRate())<=0.5)
    {
    step++;  
    sagCoder.reset();
    gyro.reset();
    kAngleSetpoint=0;
    } 
    break;
    case 4:
      drive.arcadeDrive(0.5, turningValue);
      if(sagCoder.getDistance()>=2){
        step++;
      kAngleSetpoint=90;
    }
      break;
    case 5:
      drive.arcadeDrive(0.0,turningValue);
    if(gyroPID.atSetpoint() &&  Math.abs(gyro.getRate())<=0.5)
    {
      step++;   
      sagCoder.reset();
      gyro.reset();
      kAngleSetpoint=0;
    } 
    break;
    case 6:
      drive.arcadeDrive(0.5,turningValue);
      if(sagCoder.getDistance()>=2)
      {
      step++;
      kAngleSetpoint=90;
    }
    break;

      case 7:
      drive.arcadeDrive(0.0,turningValue);
    if(gyroPID.atSetpoint() &&  Math.abs(gyro.getRate())<=0.5)
    {
      step++;   
      sagCoder.reset();
      gyro.reset();
      kAngleSetpoint=0;
     
    } break;
    case 8:
      drive.arcadeDrive(0.5,turningValue);
      if(sagCoder.getDistance()>=2){
        step++;
      kAngleSetpoint=90;}
      break;

      case 9:
      drive.arcadeDrive(0.0,turningValue);
    if(gyroPID.atSetpoint() &&  Math.abs(gyro.getRate())<=0.5)
    {
      step++;   
      sagCoder.reset();
      gyro.reset();
      kAngleSetpoint=0;
    } 
    break;

    default:
    drive.arcadeDrive(0,0);
      break;
  }

}

@Override
public void teleopInit() 
{
 
}
  
  
  @Override
  public void teleopPeriodic() {

    
  
    if(!mode)
  {
    if(kumanda.getRawButtonPressed(6))
  {
    kAngleSetpoint=kAngleSetpoint+90;
    kAngleSetpoint=kAngleSetpoint%360;
    
    }
  
    drive.arcadeDrive(kumanda.getRawAxis(1),  -
    turningValue);
  }

  else drive.curvatureDrive(-kumanda.getRawAxis(1), kumanda.getRawAxis(4), kumanda.getRawButton(5));
  
  if(kumanda.getRawButtonPressed(1)) mode=!mode;
    
  }
}
