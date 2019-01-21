/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.Drive;

public class DriveTrain extends Subsystem {
	// Put methods for controlling the driveTrain
	// here. This class does not control the driveTrain 
	// directly. Call these from Commands.

	// "defines" motors
	private VictorSP leftDrive1;
	private VictorSP rightDrive1;
	private VictorSP leftDrive2;
	private VictorSP rightDrive2;
	private VictorSP strafe;

	public DriveTrain() {
		// initializes motors
		leftDrive1 = new VictorSP(RobotMap.frontLeft);
		leftDrive2 = new VictorSP(RobotMap.backLeft);
		rightDrive1 = new VictorSP(RobotMap.frontRight);
		rightDrive2 = new VictorSP(RobotMap.backRight);
		strafe = new VictorSP(RobotMap.hWheel);
	}


	// may want to look into some sort of acceleration control to limit
	// current draw and brownouts
	private void driveRaw(double left, double right, double strafe) {
		leftDrive1.set(-left);
		leftDrive2.set(-left);
		rightDrive1.set(right);
		rightDrive2.set(right);
		this.strafe.set(strafe);
		System.out.println("left value " + -left );
		System.out.println("right value " + right );
		System.out.println("strafe value " + strafe);

	}

	/**
	 * Drive in teleop.
	 */
	public void drive(double left, double right, double strafe) {
		driveRaw(left, right, strafe);
	}

	/**
	 * stop motors
	 */
	public void stop() {
		driveRaw(0, 0, 0);
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new Drive());
		//this command runs automatically by the scheduler as soon as the 
		//DriveTrain subsystem is created
	}
}
