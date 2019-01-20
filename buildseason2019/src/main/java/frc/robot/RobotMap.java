/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

//basic motor mappings for h-drrive
public class RobotMap {
  public static final int frontLeft = 0;
  public static final int frontRight = 2;
  public static final int backLeft = 1;
  public static final int backRight = 3;
  public static final int hWheel = 4;

  public static final int leftHSelenoid = 1;
  public static final int rightHSelenoid = 2;
  public static I2C wire = new I2C(Port.kMXP, 4); //uses the i2c port on the RoboRIO
}
