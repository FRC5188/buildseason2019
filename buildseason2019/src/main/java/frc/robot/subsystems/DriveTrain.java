/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.Drive;

public class DriveTrain extends Subsystem {
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

	private Compressor compressor;
    private Solenoid hSoleniod;
	// include another instance of gyro to send to the screen
	public static AHRS gyro = RobotMap.gyro;

	public DriveTrain() {
		// initializes motors
		leftDrive1 = new VictorSP(RobotMap.frontLeft);
		leftDrive2 = new VictorSP(RobotMap.backLeft);
		rightDrive1 = new VictorSP(RobotMap.frontRight);
		rightDrive2 = new VictorSP(RobotMap.backRight);
		strafe = new VictorSP(RobotMap.hWheel);

		compressor = RobotMap.compressor;
		hSoleniod = new Solenoid(RobotMap.HSolenoid);
	}

	// may want to look into some sort of acceleration control to limit

    /**
     * Underlying drive function. Feeds power directly to the motors
     *
     * @param left left side of drive power
     * @param right right side of drive power
     * @param strafe H-wheel power
     */
	private void driveRaw(double left, double right, double strafe) {
		leftDrive1.set(-left);
		leftDrive2.set(-left);
		rightDrive1.set(right);
		rightDrive2.set(right);
		this.strafe.set(-strafe);
	}

    /**
     * Used to drive with arcade controls
     * @param throttle forward motor speed
     * @param turn value to turn
     * @param strafe h-wheel speed
     * @param shifter true for shifter to be enabled
     */
	public void driveArcade(double throttle, double turn, double strafe, boolean shifter) {

	    //creates shifter multiplier
		double shiftVal = shifter ? .5 : 1;

		double lDrive;
		double rDrive;
		// if there is no throttle do a zero point turn, or a "quick turn"
		if (Math.abs(throttle) < 0.05) {
			lDrive = -turn * shiftVal * 0.60;
			rDrive = turn * shiftVal * 0.60;
		} else {
			lDrive = shiftVal * throttle * (1 + Math.min(0, turn));
			rDrive = shiftVal * throttle * (1 - Math.max(0, turn));
			// if not driving with quick turn then drive with split arcade
		}

		driveRaw(lDrive, rDrive, strafe * .85);
	}

    public void setHWheelSelenoids(boolean val) {
        hSoleniod.set(val);
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
		// The default command will run as soon as the robot is enabled.
        // If the default command is interrupted it will resume once
        // the command which interrupted it is finished.
	}

	private void logSubsystem(){
        SmartDashboard.putData("Left Motors", this.leftDrive1);
        SmartDashboard.putData("Right Motors", this.rightDrive1);
        SmartDashboard.putData("H Motor", this.strafe);
    }


}
