/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.ResetGyro;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {

  /*Init subsystems*/
  public static DriveTrain driveTrain;
  public static I2C i2c;
  public static Pneumatics pneumatics;
  public static Elevator elevator;
  public static Intake intake;
  public static OI oi;

  public CameraServer server;
  public UsbCamera camera;
  public UsbCamera camera1;


  @Override
  public void robotInit() {

    RobotMap.gyro.reset();// reset gyro on robot start

    camera = CameraServer.getInstance().startAutomaticCapture(0);
    camera.setBrightness(50);
    camera.setFPS(30);
    camera.setResolution(160, 120);

    camera1 = CameraServer.getInstance().startAutomaticCapture(1);
    camera1.setBrightness(50);
    camera1.setFPS(25);
    camera1.setResolution(160, 120);

    driveTrain = new DriveTrain();
    i2c = new I2C();
    pneumatics = new Pneumatics();
    elevator = new Elevator ();
    intake = new Intake();
    oi = new OI();// oi needs to be created last

    //prevents CTRE timeout erros
    LiveWindow.disableAllTelemetry();

  }

  @Override
  public void disabledInit() {
      // commands don't stop running when entering disabled
      Scheduler.getInstance().removeAll();
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
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

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }


  @Override
  public void testInit() {
    
  }


  @Override
  public void testPeriodic() {

  }

  /**
   * Used to log data to the shuffleboard. When used it is
   * called at the end of the basic robot modes, like telop. 
   * 
   * It was taken out due to possible making us lose comms during matches by
   * over populationg the dashboard
   */
  private void log() {
      SmartDashboard.putBoolean("L Bumper", OI.drive.getRawButton(OI.Buttons.L));
      SmartDashboard.putBoolean("R Bumper", OI.drive.getRawButton(OI.Buttons.R));
      SmartDashboard.putData("reset gyro", new ResetGyro());
      SmartDashboard.putData("Scheduler", Scheduler.getInstance());
      //SmartDashboard.putNumber("Gyro Angle", RobotMap.gyro.getAngle());
  }
}
