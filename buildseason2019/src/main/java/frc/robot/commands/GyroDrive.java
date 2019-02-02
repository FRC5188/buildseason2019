/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class GyroDrive extends PIDCommand {
    
    private static double kp = 0.016, ki = 0, kd = 0.26, kf = 0;
    
    double throttle;
    double turn;
    double strafe;
    boolean shifter;
    double setpoint = 0;

    public GyroDrive() {
        super(kp, ki, kd, kf);
        requires(Robot.driveTrain);
        this.getPIDController().setContinuous(false);
        this.getPIDController().setOutputRange(-1, 1);
        this.getPIDController().setAbsoluteTolerance(.5);
    }
    
    
    @Override
    protected void initialize() {
        setInterruptible(true);
		// should allow the PIDTest command to take over
        System.out.println("initializing");
        this.getPIDController().enable();
        this.getPIDController().setSetpoint(0);
    }

	@Override
	protected void execute() {
        throttle = OI.drive.getRawAxis(OI.Axis.LY);
		turn = OI.drive.getRawAxis(OI.Axis.RX);
		strafe = OI.drive.getRawAxis(OI.Axis.LX);
        shifter = OI.drive.getRawButton(OI.Buttons.R);
        
        setpoint += turn;
        this.getPIDController().setSetpoint(setpoint);
		// actual drive method
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
        return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
        Robot.driveTrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
        this.end();
    }

    @Override
    protected double returnPIDInput() {
        return RobotMap.gyro.getAngle();
    }

    @Override
    protected void usePIDOutput(double output) {
        Robot.driveTrain.driveArcade(throttle, output, strafe, shifter);
    }
}
