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
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Pneumatics;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */

public class Robot extends TimedRobot {

  public static DriveTrain driveTrain;
  public static Pneumatics pneumatics;
  public static OI oi;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    /*
    Settings during testing: 
        160x120
        30fps 
        0/HW  
        used 1.2 Mbps min, 1.7 Mbps max 
    */

    CameraServer.getInstance().startAutomaticCapture();
    driveTrain = new DriveTrain();
    pneumatics = new Pneumatics();
    // oi needs to be created last
    oi = new OI();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode. You
   * can use it to reset any subsystem information you want to clear when the
   * robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {

    /**
     * shows example of running an auto schedule the autonomous command (example) if
     * (m_autonomousCommand != null) { m_autonomousCommand.start(); }
     */
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    // if (m_autonomousCommand != null) {
    // m_autonomousCommand.cancel();
    // }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

    }
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
