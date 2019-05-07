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

    /**
     *
     */

	I2C wire = Robot.i2c;

	/*Constructor*/
	public Drive() {
		requires(Robot.driveTrain);
		SmartDashboard.putBoolean("Drive Running", false);
		/*bool to let us see if drive or PixyDrive is running*/
	}

	@Override
	protected void initialize() {
		/*Allows the PixyDrive command to interrupt and take over*/
		setInterruptible(true);
	}

	@Override
	protected void execute() {
        /*get all of the driver's joystick values*/

		double throttle = OI.drive.getRawAxis(OI.Axis.LY);
		double turn =  OI.drive.getRawAxis(OI.Axis.RX);
		double quickTurn = OI.drive.getRawAxis(OI.Axis.LTrigger);
		boolean shifter = OI.drive.getRawButton(OI.Buttons.R);

        // TODO: 5/6/2019 make sperate drive commands for arcade drive and cheesy drive

		//Robot.driveTrain.driveArcade(throttle, turn, 0, shifter);
        Robot.driveTrain.cheesyDrive(throttle, turn,quickTurn, shifter);

        /*
          Calling "isTape()" will get the Pixy data from the arduino and allow the
          driver and operator to see on the dashboard if the robot currently is
          able to see any vision targets
        */
		wire.isTape();
		SmartDashboard.putBoolean("Drive Running", true);
	}

	@Override
	protected boolean isFinished() {
		/*
		 * driveTrain command should always run but, should be interruptable so other
		 * commands can control the drivetrain
		 */
		return false;
	}

	@Override
	protected void end() {
		/*stops motors when operator control is finished, notifies dashboard*/

		SmartDashboard.putBoolean("Drive Running", false);
		Robot.driveTrain.stop();
	}

	@Override
	protected void interrupted() {
		/*It is normal for commands to just call end*/
		this.end();
	}
}
