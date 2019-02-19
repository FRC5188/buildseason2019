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
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {

  public static DriveTrain driveTrain;
  public static I2C i2c;
  public static Pneumatics pneumatics;
  public static Elevator elevator;
  public static Intake intake;
  public static IntakeWrist intakeWrist;
  public static OI oi;

  @Override
  public void robotInit() {

      //i found this code on the screensteps. i takes a camera stream from usb
      //and then changes the color scale and then sends it to the dashboard.
      //this can be used to both create a image frame and help display where the pixy is
      //looking and to rotate our driver camera since it is mounted at 90 degrees

//      new Thread(() -> {
//          UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
//          camera.setResolution(640, 480);
//
//          CvSink cvSink = CameraServer.getInstance().getVideo();
//          CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);
//
//          Mat source = new Mat();
//          Mat output = new Mat();
//
//          while(!Thread.interrupted()) {
//              cvSink.grabFrame(source);
//              Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
//              outputStream.putFrame(output);
//          }
//      }).start();

      // 160x120 30fps 0/HW used 1.2 Mbps min, 1.7 Mbps during testing //
    CameraServer.getInstance().startAutomaticCapture();
    RobotMap.gyro.reset();// reset gyro on robot start
    
    driveTrain = new DriveTrain();
    i2c = new I2C();
    pneumatics = new Pneumatics();
    elevator = new Elevator ();
    intake = new Intake();
    intakeWrist = new IntakeWrist();
    oi = new OI();// oi needs to be created last

    //prevents CTRE timeout erros
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
    //  this.log();

  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

    this.log();
  }


  @Override
  public void testInit() {
    
  }


  @Override
  public void testPeriodic() {

  }

  private void log() {
      SmartDashboard.putBoolean("L Bumper", OI.drive.getRawButton(OI.Buttons.L));
      SmartDashboard.putBoolean("R Bumper", OI.drive.getRawButton(OI.Buttons.R));
      SmartDashboard.putData("Scheduler", Scheduler.getInstance());
      //SmartDashboard.putNumber("Gyro Angle", RobotMap.gyro.getAngle());
  }
}
