/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.driveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.I2C;

public class Drive extends Command {

	//we orgianally had h-drive but now have 6 wheel tank...

	I2C wire = Robot.i2c;

	public Drive() {
		requires(Robot.driveTrain);
		SmartDashboard.putBoolean("Drive Running", false);
		//bool to let us see if drive or pixydrive is running
	}

	@Override
	protected void initialize() {
		setInterruptible(true);
		//allows the pixyDrive command to interrupt and take over
	}

	@Override
	protected void execute() {
        //get all of the driver's joystick values
		double throttle = OI.drive.getRawAxis(OI.Axis.LY);
		double turn =  OI.drive.getRawAxis(OI.Axis.RX);
		double quickTurn = OI.drive.getRawAxis(OI.Axis.LTrigger);
		boolean shifter = OI.drive.getRawButton(OI.Buttons.R);

		//actual driveTrain method
		//Robot.driveTrain.driveArcade(throttle, turn, 0, shifter);

		if(Math.abs(throttle) < .1)
			quickTurn = 1;

		// if Math.abs(turn)
		
        Robot.driveTrain.cheesyDrive(throttle, -turn, quickTurn, shifter);//is called during normal drive, as well as pixy, so that we
		//can see if the robot can see the tape without having to enable the pixydrive%

		//this makes the overall driving experience for the driver easier and more efficent 
		wire.isTape();
		SmartDashboard.putBoolean("Drive Running", true);
	}

	@Override
	protected boolean isFinished() {
		/*
		 * driveTrain command should always run but, should be incorruptible so other
		 * commands can control the drivetrain
		 */
		return false;
	}

	@Override
	protected void end() {
		SmartDashboard.putBoolean("Drive Running", false);
		Robot.driveTrain.stop();
		// stops motors when command is finished
	}

	@Override
	protected void interrupted() {
		this.end();
		// ends command when interrupted
	}
}
