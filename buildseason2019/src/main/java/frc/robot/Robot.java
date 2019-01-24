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
import frc.robot.commands.Drive;
import frc.robot.commands.PIDTest;
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
    this.log();
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
    // LiveWindow.add(Robot.driveTrain);
    // LiveWindow.add(Robot.pneumatics);
    // LiveWindow.add(new PIDTest());
    // LiveWindow.add(new Drive());
    // LiveWindow.setEnabled(true);
    // shouldn't need this add statements
  }

  @Override
  public void testPeriodic() {
  }

  private void log() {
    SmartDashboard.putData(this.DRIVETRAIN, Robot.driveTrain);
    SmartDashboard.putData(this.DRIVECOMMAND, new Drive());
    SmartDashboard.putData(this.PIDTESTCOMMAND, new PIDTest());
    SmartDashboard.putNumber(this.GYROANGLE, RobotMap.gyro.getAngle());
  }
}
