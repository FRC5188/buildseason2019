/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;


public class Drive extends Command {
	public Drive() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveTrain);
	
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		setInterruptible(true);
		// should allow the PIDTest command to take over
		Robot.driveTrain.setSetpoint(0);
		RobotMap.gyro.zeroYaw();
		Robot.driveTrain.getPIDController().enable();
		System.out.println("initializing");
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double throttle = OI.drive.getRawAxis(OI.Axis.LY);
		double turn = OI.drive.getRawAxis(OI.Axis.RX);
		double strafe = OI.drive.getRawAxis(OI.Axis.LX);
		double shifter = OI.drive.getRawButton(OI.Buttons.R) ? .5 : 1;

		


		if (Math.abs(throttle) < 0.05) {
			// quick turn if no throttle
			turn=turn*shifter*0.6;
		} 
		
		//actual drive method

		Robot.driveTrain.gyroDrive(throttle, turn, strafe);
		
		System.out.println("Gyro Angle Drive: " + RobotMap.gyro.getAngle());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		/*
		 * drive command should always run but, should be interruptable so other
		 * commands can control the drivetrain
		 */
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveTrain.stop();
		// stops motors when command is finsihed
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		// the docs say most times its acceptable to just call end()
		this.end();
		// ends command when interrupted
	}
}
