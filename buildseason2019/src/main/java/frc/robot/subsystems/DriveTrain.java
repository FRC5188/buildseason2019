/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.driveTrain.Drive;

public class DriveTrain extends Subsystem {
    /**
     * DriveTrain consist of 6 wheel drop tank drive powered
     * by two Cims. Each side of the dive has one TalonSRX and one
     * VictorSPX.
     *
     * The only methods that should exist here are methods to
     * control the driveTrain or any hardware components of the subsystem.
     *
     * Call these methods from commands to drive the robot.
     */

	/*Motor Controllers*/
	private TalonSRX leftDrive1;
	private TalonSRX rightDrive1;
	private VictorSPX leftDrive2;
	private VictorSPX rightDrive2;

	/*cheesy drive variables*/
    private double wheelNonLinearity = .6;
    private double negInertia, oldWheel;
    private double sensitivity;
    private double angularPower;
    private double linearPower;

    /*Constructor*/
	public DriveTrain() {
		/*initializes motors*/
		leftDrive1 = new TalonSRX(RobotMap.FRONT_LEFT);
		leftDrive2 = new VictorSPX(RobotMap.BACK_LEFT);
		rightDrive1 = new TalonSRX(RobotMap.FRONT_RIGHT);
		rightDrive2 = new VictorSPX(RobotMap.BACK_RIGHT);

		/*config current limiting, helps with browning out*/
		leftDrive1.configPeakCurrentLimit(50, 10); /* 35 A */
		leftDrive1.configPeakCurrentDuration(200, 10); /* 200ms */
		leftDrive1.configContinuousCurrentLimit(35, 10); /* 30 */
		leftDrive1.enableCurrentLimit(true); /* turn it on */

		rightDrive1.configPeakCurrentLimit(50, 10); /* 35 A */
		rightDrive1.configPeakCurrentDuration(200, 10); /* 200ms */
		rightDrive1.configContinuousCurrentLimit(35, 10); /* 30 */
		rightDrive1.enableCurrentLimit(true); /* turn it on */
	}

    /**
     * Underlying driveTrain function. Feeds power directly to the motors.
     *
     * @param left left side of driveTrain power
     * @param right right side of driveTrain power
     */
	private void driveRaw(double left, double right) {
	    /*
	       Percent output takes a "speed" from -1 to 1.
	       Follow tells a motor controller to match the speed of another.
	     */
		leftDrive1.set(ControlMode.PercentOutput, -left);
		leftDrive2.follow(leftDrive1);
		rightDrive1.set(ControlMode.PercentOutput, right);
		rightDrive2.follow(rightDrive1);

		this.logSubsystem();
	}

    /***
     *  An advanced arcade driver function. Cheesy Drive applies some math to
     *  normal arcade drive to make turning, and overall driving, more smooth.
     *  NOTE: If no throttle is applied, Cheesy Drive will not turn, hence quick turn.
     *
     * @param throttle Value of the throttle, or "speed", joystick
     * @param wheel Value of the turning Joystick
     * @param quickTurn Value quickturn axis, normally one of the bottom triggers of the logitech controller
     * @param shifter  State of the software-shift button
     */
    public void cheesyDrive(double throttle, double wheel, double quickTurn, boolean shifter) {
        /*This code belongs to the poofs*/

        negInertia = wheel - oldWheel;
        oldWheel = wheel;

        wheelNonLinearity = 0.5;
        //wheelNonLinearity = shifter ? 0.6:0.5;

        /*Apply a sin function that's scaled to make it feel better.*/
        wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel)
                / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
        wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel)
                / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
        wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel)
                / Math.sin(Math.PI / 2.0 * wheelNonLinearity);

        if(shifter){
            sensitivity = .5;
        } else {
            sensitivity = 1;
        }

        wheel += negInertia;
        linearPower = throttle;

        if (quickTurn > 0.5){
            angularPower = wheel;
        } else {
            angularPower = Math.abs(throttle) * wheel;
        }

        double left = -((linearPower + angularPower) * sensitivity);
        double right = (linearPower - angularPower) * sensitivity;
        driveRaw(left, right);
    }

    /**
     * Drives the robot with arcade drive. Meaning, one joystick axis for
     * throttle, or "speed", and one for turning.
     *
     * NOTE: this dive function is buggy and will invert steering sometimes
     *
     * @param throttle Forward motor speed
     * @param turn Value to turn
     * @param shifter Value of software-shifter
     */
	public void driveArcade(double throttle, double turn, boolean shifter) {

	    /*shifter multiplier*/
		double shiftVal = shifter ? 1 : .5;

		double lDrive;
        double rDrive;

        /*reverse turning when driving backwards*/
		if(-throttle < 0){
			turn = turn * -1;
		}

        /*creates a dead band*/
        if(Math.abs(throttle) < .01) throttle = 0;
        if(Math.abs(turn) < .01) turn = 0;

		/*if there is no throttle do a zero point turn, or a "quick turn"*/
		if (Math.abs(throttle) < 0.05) {
			lDrive = -turn * shiftVal * 0.75;
			rDrive = turn * shiftVal * 0.75;
		} else {
			/*if not driving with quick turn then driveTrain with split arcade*/
			lDrive = shiftVal * throttle * (1 + Math.min(0, turn));
			rDrive = shiftVal * throttle * (1 - Math.max(0, turn));
		}

		driveRaw(lDrive, rDrive);
	}


	/**
	 * stop motors
	 */
	public void stop() {
		driveRaw(0, 0);
	}

	@Override
	public void initDefaultCommand() {
		/*
            The default command will run as soon as the robot is enabled.
            If the default command is interrupted it will resume once
            the command which interrupted it is finished.
		 */
	    setDefaultCommand(new Drive());
    }


    // TODO: 5/4/2019 LOG BETTER DATA, MAKE SURE IT DOESNT CRASH COMMS
    private void logSubsystem(){
	    /*We had possible issues with logging too much data and losing comms*/

        //SmartDashboard.putData("Left Motors", this.leftDrive1);
        //SmartDashboard.putData("Right Motors", this.rightDrive1);
		SmartDashboard.putNumber("Gyro Val", RobotMap.gyro.getAngle());
		SmartDashboard.putData("Gyro", RobotMap.gyro);
		
	}
}
