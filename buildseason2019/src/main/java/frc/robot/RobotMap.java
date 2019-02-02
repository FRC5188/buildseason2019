/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;

import edu.wpi.first.wpilibj.Compressor;


//basic motor mappings for h-drrive
public class RobotMap {
  
  //Everything on the CAN bus has an ID
  //The default ID for only one item on the can bus is 0
  public static final int COMPRESSOR_CAN_ID = 0;

  public static Compressor compressor = new Compressor(COMPRESSOR_CAN_ID);
  
  public static final int frontLeft = 0;
  public static final int frontRight = 2;
  public static final int backLeft = 1;
  public static final int backRight = 3;
  public static final int hWheel = 4;

  //Selenoids to raise and lower H-Wheel

  public static final int leftHSelenoid = 1;
  public static final int rightHSelenoid = 2;
  
  public static AHRS gyro = new AHRS(SerialPort.Port.kMXP); 
}
