/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;

public class PixyDrive extends PIDCommand {
    //TODO increase responsiveness of PID
    //TODO possible coms issue, check to see if it can be replicated
    //TODO handle losing arduino and pixy seperately

    private static double kp = 0.06, ki = 0, kd = 0.49;

    double throttle;
    double turn;
    double strafe;
    boolean shifter;
    boolean isNull = false;
    double setpoint = 0;

    public PixyDrive() {
        super("PixyDrive",kp, ki, kd, .02);
        this.requires(Robot.driveTrain);
        this.setInterruptible(true);
        this.getPIDController().setOutputRange(-1, 1);
        this.getPIDController().setAbsoluteTolerance(.5);

        SmartDashboard.putData("Pixy Drive", this);
		SmartDashboard.putBoolean("Pixy Running", false);

    }


    @Override
    protected void initialize() {
        // should allow the PIDTest command to take over
        this.getPIDController().setSetpoint(0);
        this.getPIDController().enable();
    }

    @Override
    protected void execute() {
        throttle = OI.drive.getRawAxis(OI.Axis.LY);
        strafe = OI.drive.getRawAxis(OI.Axis.LX);
        turn = OI.drive.getRawAxis(OI.Axis.RX);
        shifter = OI.drive.getRawButton(OI.Buttons.R);
		SmartDashboard.putBoolean("Pixy Running", true);

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
        // return !OI.drive.getRawButton(OI.Buttons.L);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
		SmartDashboard.putBoolean("Pixy Running", false);
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
        double angle = Robot.i2c.getPixyAngle();
        if(angle == 400){
            angle = 0;
            this.isNull = true;
            
            return angle;
        }
        this.isNull = false;
		return angle;
    }

    @Override
    protected void usePIDOutput(double output) {
        if(this.isNull)
        {
            Robot.driveTrain.driveArcade(throttle, turn, strafe, shifter);
        }
        else{
        Robot.driveTrain.driveArcade(throttle, output, strafe, shifter);
        }
    }
}
