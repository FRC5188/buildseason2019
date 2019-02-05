/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.OI;
import frc.robot.Robot;

public class PixyDrive extends PIDCommand {

    private static double kp = 0.016, ki = 0, kd = 0.26, kf = 0;

    double throttle;
    double turn;
    double strafe;
    boolean shifter;
    double setpoint = 0;

    public PixyDrive() {
        super(kp, ki, kd, kf);
        this.requires(Robot.driveTrain);
        this.getPIDController().setContinuous(false);
        this.getPIDController().setOutputRange(-1, 1);
        this.getPIDController().setAbsoluteTolerance(.5);
    }


    @Override
    protected void initialize() {
        this.setInterruptible(true);
        // should allow the PIDTest command to take over
        this.getPIDController().setSetpoint(0);
        this.getPIDController().enable();
    }

    @Override
    protected void execute() {
        throttle = OI.drive.getRawAxis(OI.Axis.LY);
        strafe = OI.drive.getRawAxis(OI.Axis.LX);
        shifter = OI.drive.getRawButton(OI.Buttons.R);
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
        this.getPIDController().disable();
        this.end();
    }

    @Override
    protected double returnPIDInput() {
        //System.out.println("PID Input " + Robot.i2c.getPixyAngle());
        return Robot.i2c.getPixyAngle();
    }

    @Override
    protected void usePIDOutput(double output) {
        Robot.driveTrain.driveArcade(throttle, -output, strafe, shifter);
    }
}
