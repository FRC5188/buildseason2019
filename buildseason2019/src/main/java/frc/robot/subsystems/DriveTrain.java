/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.Drive;
import edu.wpi.first.wpilibj.SerialPort;


public class DriveTrain extends PIDSubsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.


	// as a note, the methods in PIDSubsytems seem to call the
	// PID controller automattically
	// so, this.enable() should be the same as this.getPIDController.enable()
	// which, would make all of our wrappers kinda useless

	// "defines" motors
	private VictorSP leftDrive1;
	private VictorSP rightDrive1;
	private VictorSP leftDrive2;
	private VictorSP rightDrive2;
	private VictorSP strafe;

	//include another instance of gyro to send to the screen
	public static AHRS gyro= RobotMap.gyro;

  	private double targetThrottle=0, targetStrafe=0;
	

	private static double kp = 0.016, ki = 0, kd = .26, kf = 0;

	public DriveTrain() {
		// initializes motors
		super(kp, ki, kd, kf);
		leftDrive1 = new VictorSP(RobotMap.frontLeft);
		leftDrive2 = new VictorSP(RobotMap.backLeft);
		rightDrive1 = new VictorSP(RobotMap.frontRight);
		rightDrive2 = new VictorSP(RobotMap.backRight);
		strafe = new VictorSP(RobotMap.hWheel);

		this.getPIDController().setContinuous(false);
		this.getPIDController().setOutputRange(-1, 1);
		this.getPIDController().setAbsoluteTolerance(.5);



	}

	// may want to look into some sort of acceleration control to limit

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
	public void gyroDrive(double throttle, double turn, double strafe) {
		
		targetThrottle=throttle;
		targetStrafe=strafe;
		double temp=getPIDController().getSetpoint();
		System.out.print("Turn:"+turn+"Setpoint:"+temp);
		SmartDashboard.putNumber("Turn", turn);
		SmartDashboard.putNumber("setpoint", temp);
		getPIDController().setSetpoint(temp+turn);

	}

	/**
	 * stop motors
	 */
	public void stop() {
		driveRaw(0, 0, 0);
	}

	@Override
	protected double returnPIDInput() {
		//System.out.println("Gyro Angle: " + RobotMap.gyro.getAngle());
		return RobotMap.gyro.getAngle();
		//return (60);
	
	}
	@Override
	protected void usePIDOutput(double output) {
		System.out.println("PID Output: " + output);
		SmartDashboard.putNumber("output",output);
	//	this.drive(targetThrottle-output, targetThrottle+output, targetStrafe);
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new Drive());
		//this command runs automatically by the scheduler as soon as the 
		//DriveTrain subsystem is created
	}

}
