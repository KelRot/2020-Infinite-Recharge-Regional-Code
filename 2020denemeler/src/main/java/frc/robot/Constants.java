/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class DriveConstants{
        public static final int arkaSol= 3;
        public static final int onSol= 2;
        public static final int onSag= 0;
        public static final int arkaSag= 1;

        public static final int[] DriveLeftEncoderPorts = {5,6};
        public static final int[] DriveRightEncoderPorts = {7,8};

        public static final double distancePerPulse = Math.PI* 0.1525f /1024f;

        public static final float kS=0.5f;
        public static final float kV=0.4f;
        public static final float kA= 0.6f;

        public static final float kP = 0.05f;



    }
    public static final class   ShooterConstants{
        public static final int LeftMotorID=3;
        public static final int RightMotorID =2;

        public static final int feederMotorPWM =3;

        public static final int[] RightEncoderPorts = {0,1};
        public static final int[] LeftEncoderPorts = {2,3};

        public static final float kV = 0.00019f;
        public static final float kP = 0.00009f;
    
        public static final double setPointRPS =3800.0;
        public static final double positionSetPointTolerance = 200.0;
        

        public static final double freeSpeed =0.8f; 

        public static final double encoderRPP =3f*60f/600f;
    
    }
    public static final class   FeederConstants{
        public static final int upMotorID=0;
        public static final int downMotorID =1;

        public static final double feederSpeed=0.3f;
        
        public static final int intakeSensorPort=0;
        public static final int shooterSensorPort=1;
    }
    //2 



    
}

