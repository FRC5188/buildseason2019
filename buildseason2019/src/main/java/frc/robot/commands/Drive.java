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

/**
 * An example command. You can replace me with your own command.
 */
public class Drive extends Command {
	public Drive() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		setInterruptible(true);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// double leftThrottle = OI.drive.getRawAxis(OI.Axis.LY);
		// double rightThrottle = OI.drive.getRawAxis(OI.Axis.RY);
		// double strafe = OI.drive.getRawAxis(OI.Axis.LX);
		double throttle = OI.drive.getRawAxis(OI.Axis.LY);
		double turn =  OI.drive.getRawAxis(OI.Axis.RX);
		double strafe =  OI.drive.getRawAxis(OI.Axis.LX);
		double shifter = OI.drive.getRawButton(OI.Buttons.R) ? .5 : 1;

		double lDrive;
		double rDrive;
		
		if (Math.abs(throttle) < 0.05) {
			// quick turn
			lDrive = -turn * shifter * 0.60;
			rDrive = turn * shifter * 0.60;
			// drive in arcade
		} else {
			lDrive = shifter * throttle * (1 + Math.min(0, turn));
			rDrive = shifter * throttle * (1 - Math.max(0, turn));
		}
		
		Robot.driveTrain.drive(lDrive, rDrive, strafe);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
