/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Pneumatics;

public class Robot extends TimedRobot {

  public static DriveTrain driveTrain;
  public static Pneumatics pneumatics;
  public static OI oi;


  public final String PIDTESTCOMMAND = "PID Test Command";
  public final String DRIVECOMMAND = "Drive Command";
  public final String DRIVETRAIN = "Drive Train";
  public final String GYROANGLE = "Gyro Angle";
  public final String RESETCOMMAND = "Reset Command";

  @Override
  public void robotInit() {
    // 160x120 30fps 0/HW used 1.2 Mbps min, 1.7 Mbps during testing //
    CameraServer.getInstance().startAutomaticCapture();
    RobotMap.gyro.zeroYaw();// reset gyro on robot start

    driveTrain = new DriveTrain();
    pneumatics = new Pneumatics();
    oi = new OI();// oi needs to be created last
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
 double angle;


 double throttle;
 double turn;
 double strafe;
 double shifter;
 boolean reset;

  @Override
  public void testPeriodic() {
    //angle = RobotMap.gyro.getAngle();
    //gyroAngle.setDouble(angle);
    
		throttle = OI.drive.getRawAxis(OI.Axis.LY);
		turn = OI.drive.getRawAxis(OI.Axis.RX);
		strafe = OI.drive.getRawAxis(OI.Axis.LX);
    shifter = OI.drive.getRawButton(OI.Buttons.R) ? .5 : 1;
    reset = OI.drive.getRawButton(OI.Buttons.A);

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
  
    Robot.driveTrain.driveArcade(throttle,turn, strafe, true);
    
    this.log();
  }

  private void log() {
    SmartDashboard.putNumber(this.GYROANGLE, RobotMap.gyro.getAngle());
  }

}
