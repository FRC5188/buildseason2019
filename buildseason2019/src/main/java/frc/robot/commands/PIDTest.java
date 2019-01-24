/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class PIDTest extends PIDCommand {
  static final double kp = 0.01, ki = 0, kd = 0, setPoint = 30;
  public PIDTest() {
    // Use requires() here to declare subsystem dependencies
    super(kp,ki,kd, kf);
    requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    RobotMap.gyro.zeroYaw();
    this.getPIDController().setPID(kp, ki, kd);
    setSetpoint(setPoint);
    //this.getPIDController().setAbsoluteTolerance(3);
    this.getPIDController().enable();
    System.out.println("int");

  }

  // Called repeatedly when this Command is scheduled to run

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {

    boolean finished = this.getPIDController().onTarget();
    SmartDashboard.putBoolean("PID End", finished);
    return finished;

  }
  // Called once after isFinished returns true
  @Override
  protected void end() {

    System.out.println("end");
    Robot.driveTrain.stop (); }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }

  @Override
  protected double returnPIDInput() {
    System.out.println(RobotMap.gyro.getAngle());
    return RobotMap.gyro.getAngle();
    
  }

  @Override
  protected void usePIDOutput(double output) {
    Robot.driveTrain.drive(output,-output,0);
    System.out.println("driving");
  }
}
