/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class PIDTest extends Command {

  public PIDTest() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("int");// data
    SmartDashboard.putBoolean("PID End", false);// a boolean box on dashboard
    this.setInterruptible(true);
    // ^^hopefully allow for drive command to take back control
    
    Robot.driveTrain.setSetpoint(60);// set PID setpoint to 60 degrees
    System.out.println("Gyro Angle : " + Robot.driveTrain.gyro.getAngle());
    Robot.driveTrain.gyro.zeroYaw();// reset gyro
    Robot.driveTrain.enable();// start PID
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // shouldn't need to do anything in run
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    SmartDashboard.putBoolean("PID End", Robot.driveTrain.onTarget());
    // update the end boolean on dashboard
    return Robot.driveTrain.onTarget();
    // if on target end
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("end");// data
    Robot.driveTrain.disable();// disables PID after it is finished
    // motors are stopped in disablePID
  } 

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    System.out.println("interupted");
    end();    // if interrupted than stop PID
  }
}
