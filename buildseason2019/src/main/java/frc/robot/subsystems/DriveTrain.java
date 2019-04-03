/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.driveTrain.Drive;

public class DriveTrain extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	// "defines" motors
	private TalonSRX leftDrive1;
	private TalonSRX rightDrive1;
	private TalonSRX leftDrive2;
	private TalonSRX rightDrive2;
	private VictorSP strafe;


	public DriveTrain() {
		// initializes motors
		leftDrive1 = new TalonSRX(RobotMap.FRONT_LEFT);
		leftDrive2 = new TalonSRX(RobotMap.BACK_LEFT);
		rightDrive1 = new TalonSRX(RobotMap.FRONT_RIGHT);
		rightDrive2 = new TalonSRX(RobotMap.BACK_RIGHT);
		strafe = new VictorSP(RobotMap.H_WHEEL);

		//config current limiting
		// leftDrive1.configPeakCurrentLimit(35, 10); /* 35 A */
		// leftDrive1.configPeakCurrentDuration(200, 10); /* 200ms */
		// leftDrive1.configContinuousCurrentLimit(30, 10); /* 30 */

	}

    /**
     * Underlying driveTrain function. Feeds power directly to the motors
     *
     * @param left left side of driveTrain power
     * @param right right side of driveTrain power
     * @param strafe H-wheel power
     */
	private void driveRaw(double left, double right, double strafe) {
		leftDrive1.set(ControlMode.PercentOutput, -left);
		leftDrive2.follow(leftDrive1);
		rightDrive1.set(ControlMode.PercentOutput, right);
		rightDrive2.follow(rightDrive1);
		this.strafe.set(-strafe);

		this.logSubsystem();
	}

    /**
     * Used to driveTrain with arcade controls
     * @param throttle forward motor speed
     * @param turn value to turn
     * @param strafe h-wheel speed
     * @param shifter true for shifter to be enabled
     */
	public void driveArcade(double throttle, double turn, double strafe, boolean shifter) {

	    //creates shifter multiplier
		double shiftVal = shifter ? .75 : 1;

		double lDrive;
        double rDrive;

		if(-throttle < 0){
			turn = turn * -1;
		}

        //creates a dead band
        if(Math.abs(throttle) < .01) throttle = 0;
        if(Math.abs(turn) < .01) turn = 0;
        if(Math.abs(strafe) < .01) strafe = 0;

		// if there is no throttle do a zero point turn, or a "quick turn"
		if (Math.abs(throttle) < 0.05) {
			lDrive = -turn * shiftVal * 0.75;
			rDrive = turn * shiftVal * 0.75;
		} else {
			lDrive = shiftVal * throttle * (1 + Math.min(0, turn));
			rDrive = shiftVal * throttle * (1 - Math.max(0, turn));
			// if not driving with quick turn then driveTrain with split arcade
		}

	
		driveRaw(lDrive, rDrive, strafe * .85);
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
        //SmartDashboard.putData("Left Motors", this.leftDrive1);
        //SmartDashboard.putData("Right Motors", this.rightDrive1);
		SmartDashboard.putData("H Motor", this.strafe);
		SmartDashboard.putNumber("Gyro Val", RobotMap.gyro.getAngle());
		SmartDashboard.putData("Gyro", RobotMap.gyro);
		
	}
}
