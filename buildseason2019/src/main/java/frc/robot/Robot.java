/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Drive;
import frc.robot.commands.PixyDrive;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pneumatics;
import frc.robot.subsystems.I2C;

public class Robot extends TimedRobot {

  public static DriveTrain driveTrain;
  public static I2C i2c;
  public static Pneumatics pneumatics;
  public static Elevator elevator;
  public static Intake intake;
  public static OI oi;

  @Override
  public void robotInit() {
    // 160x120 30fps 0/HW used 1.2 Mbps min, 1.7 Mbps during testing //
    CameraServer.getInstance().startAutomaticCapture();
    RobotMap.gyro.zeroYaw();// reset gyro on robot start

    driveTrain = new DriveTrain();
    i2c = new I2C();
    pneumatics = new Pneumatics();
    elevator = new Elevator ();
    intake = new Intake();
    oi = new OI();// oi needs to be created last

    //Adds buttons to start commands from the dashboard
    SmartDashboard.putData("Drive", new Drive());
    SmartDashboard.putData("Pixy Drive", new PixyDrive());
    LiveWindow.disableAllTelemetry();
    this.log();
  }

  @Override
  public void disabledInit() {
    Scheduler.getInstance().removeAll();
    // commands don't stop running when entering disabled
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
    this.log();
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
      this.log();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    this.log();
  }


  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testInit() {
    
  }

  @Override
  public void testPeriodic() {

    if (reset){
      RobotMap.gyro.zeroYaw();
    }

		double lDrive;
		double rDrive;

		if (Math.abs(throttle) < 0.05) {
			// quick turn if no throttle
			lDrive = -turn * shifter * 0.60;
			rDrive = turn * shifter * 0.60;
			// else: drive in arcade
		} else {
			lDrive = shifter * throttle * (1 + Math.min(0, turn));
			rDrive = shifter * throttle * (1 - Math.max(0, turn));
		}
  
    //Robot.driveTrain.driveArcade(throttle,turn, strafe, true);
    
    this.log();
  }

  private void log() {
      SmartDashboard.putNumber("Gyro Angle", RobotMap.gyro.getAngle());
      SmartDashboard.putData("Scheduler", Scheduler.getInstance());
      //The scheduler will show running commands
  }

}
