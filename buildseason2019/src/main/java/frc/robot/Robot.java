/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Drive;
import frc.robot.commands.PixyDrive;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.I2C;

public class Robot extends TimedRobot {

  public static DriveTrain driveTrain;
  public static I2C i2c;
  public static OI oi;

  private Command pixyDrive;

  @Override
  public void robotInit() {
    // 160x120 30fps 0/HW used 1.2 Mbps min, 1.7 Mbps during testing //
    //CameraServer.getInstance().startAutomaticCapture();
    RobotMap.gyro.zeroYaw();// reset gyro on robot start

    
    driveTrain = new DriveTrain();
    i2c = new I2C();
    oi = new OI();// oi needs to be created last
    
    //Adds buttons to start commands from the dashboard
    SmartDashboard.putBoolean("L Bumper", false);
    SmartDashboard.putBoolean("R Bumper", false);
    LiveWindow.disableAllTelemetry();
   // this.log();
  }

  @Override
  public void disabledInit() {
    Scheduler.getInstance().removeAll();
    // commands don't stop running when entering disabled
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  //  this.log();
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    //  this.log();
      pixyDrive = new PixyDrive();
      if(pixyDrive.isRunning()) pixyDrive.cancel();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    SmartDashboard.putBoolean("L Bumper", OI.drive.getRawButton(OI.Buttons.L));
    SmartDashboard.putBoolean("R Bumper", OI.drive.getRawButton(OI.Buttons.R));
    // if(OI.drive.getRawButton(OI.Buttons.L)) 
    //   if(!pixyDrive.isRunning()) 
    //   {
    //     pixyDrive.start();
    //   }
    // else{
    //   if(pixyDrive.isRunning()) pixyDrive.cancel();
    // }
  //  this.log();
  }


  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testInit() {
    
  }

  @Override
  public void testPeriodic() {
 
  }

  private void log() {
      SmartDashboard.putNumber("Gyro Angle", RobotMap.gyro.getAngle());
      SmartDashboard.putData("Scheduler", Scheduler.getInstance());
      //The scheduler will show running commands
  }
}
