/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.Drive;
import frc.robot.commands.PIDTest;

public class DriveTrain extends PIDSubsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	/**
	 *
	 */

	// "defines" motors
	private VictorSP leftDrive1;
	private VictorSP rightDrive1;
	private VictorSP leftDrive2;
	private VictorSP rightDrive2;
	private VictorSP strafe;

	private static double kp = 0.01, ki = 0, kd = 0;

	public DriveTrain() {
		// initializes motors
		super(kp, ki, kd);
		leftDrive1 = new VictorSP(RobotMap.frontLeft);
		leftDrive2 = new VictorSP(RobotMap.backLeft);
		rightDrive1 = new VictorSP(RobotMap.frontRight);
		rightDrive2 = new VictorSP(RobotMap.backRight);
		strafe = new VictorSP(RobotMap.hWheel);
	}

	/**
	 * This is used to allow autonomous to get through acceleration and velocity
	 * controls we may place and instead use PID.
	 */
	// may want to look into some sort of scceleration control to limit
	// current draw and brownouts
	private void driveRaw(double left, double right, double strafe) {
		leftDrive1.set(-left);
		leftDrive2.set(-left);
		rightDrive1.set(right);
		rightDrive2.set(right);
		this.strafe.set(strafe);
		}

	/**
	 * Drive in teleop.
	 */
	public void drive(double left, double right, double strafe) {
		driveRaw(left, right, strafe);
	}

	public void stop() {
		driveRaw(0, 0, 0);
	}

	private void drivePID(double left, double right) {
		leftDrive1.pidWrite(-left);
		leftDrive2.pidWrite(-left);
		rightDrive1.pidWrite(right);
		rightDrive2.pidWrite(right);
		}	

	public void enablePID(){
		this.getPIDController().enable();
	}

	public void disablePID(){
		this.getPIDController().disable();
	}

	public void setPIDSetPoint(double setPoint){
		this.getPIDController().setSetpoint(setPoint);
	}
	@Override
	protected double returnPIDInput() {
		return RobotMap.gyro.getAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
		this.drivePID(output, -output);
	}
	
	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new Drive());
	}

	
}
