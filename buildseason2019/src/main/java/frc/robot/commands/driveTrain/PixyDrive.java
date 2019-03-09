/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.driveTrain;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;

public class PixyDrive extends PIDCommand {
    //TODO increase responsiveness of PID
    //TODO handle losing arduino and pixy seperately

    /*
        PixyDrive is our main "auto" feature this season.
        PixyDrive's goal is to allow the driver to press a button and
        have the robot always stay pointed towards the cargo and hatch bays
        on the cargo ship, rocket ship, and loading station. PixyDrive only takes
        control of the robot's turning, allowing the driver to continue moving the
        robot towards the cargo and hatch stations by driving forwards, backwards,
        and strafing with the h-wheel.

        USE:
        The driver begins driving to a desired location, either to place hatch or cargo, or grab a hatch.
        As the driver nears the location they enable the PixyDrive to take care of the small lining up
        movements for them.
        The driver continues to drive towards the location while the PixyDrive is still making small adjustments
        to the robot's angle.
        After placing, or grabbing, the driver releases the PixyDrive button and seamlessly transitions back to
        normal driving.

        One strength of PixyDrive is the driver is never committed to a "auto" loop. They may release the PixyDrive
        at any moment on the field and immediately go back to arcade drive. I.E if strategy changes mid placing
     */

    //need some refining on the comp bot
    private static double kp = 0.06, ki = 0, kd = 0.49;
    private double throttle;
    private double turn;//currently not using
    private double strafe;
    private boolean shifter;
    private double setpoint = 0;

    public PixyDrive() {
        //init PID loop
        //passing a period helps reduce RoboRIO CPU usage by
        //not allowing the PID loop to continuously run in the background without stopping.
        //pwm motor controls can only update at 5ms anyway, and the main teleop loop runs every 20ms
        super("PixyDrive",kp, ki, kd, .02);
        this.requires(Robot.driveTrain);
        this.requires(Robot.i2c);
        this.setInterruptible(true);
        //it shouldn't make or break this PID loop to have an output range since the motors don't
        //allow for output past -1 or 1 anyway
        this.getPIDController().setOutputRange(-1, 1);
        this.getPIDController().setAbsoluteTolerance(.5);

        SmartDashboard.putData("Pixy Drive", this);
        //puts the PID controller on the dashboard, should be able to tune PID from dashboard
        //with this
        SmartDashboard.putBoolean("Pixy Running", false);
        //since switching between driving manually and with the pixy is one of our most import
        //"auto" features this year, we put booleans on the dashboard letting us visually see if
        //the commands are running correctly
    }


    @Override
    protected void initialize() {
        this.getPIDController().setSetpoint(this.setpoint);
        this.getPIDController().enable();
        //set and enable PID just before running the PixyDrive Command
    }

    @Override
    protected void execute() {
        //grab driver joystick values
        //the values are not used in the method though
        throttle = OI.drive.getRawAxis(OI.Axis.LY);
        strafe = OI.drive.getRawAxis(OI.Axis.LX);
        turn = OI.drive.getRawAxis(OI.Axis.RX);
        shifter = OI.drive.getRawButton(OI.Buttons.R);

		SmartDashboard.putBoolean("Pixy Running", true);
    }

    @Override
    protected boolean isFinished() {
        //this command will start and finish a decent amount during a match.
        //however, when the command is running it never "needs to finish" since
        //it's sole job is to stay running to keep the robot looking at the tape.
        //the command is instead canceled when it is "finished", I.E. the driver
        //stops using the PixyDrive command and switches back to normal Drive.
        return false;
    }

    @Override
    protected void end() {
        this.getPIDController().disable();
        SmartDashboard.putBoolean("Pixy Running", false);
        Robot.driveTrain.stop();
        //might be worth deleting ^^^
        //disable PID before exiting
        //tell us on the dashboard that this command is no longer running
        //Stop the driveTrain to avoid motors becoming "stuck" with power
    }


    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        this.end();
        //When the driver switches back to normal driving this command is canceled.
        //Canceling any command will run it's interrupted method if the command has
        //been set interruptible.
    }

    @Override
    protected double returnPIDInput() {
        //This uses the getPixyAngle method from our I2C subsystem to
        //tell our PID loop how many degrees we are off from facing the Tape
        double angle = Robot.i2c.getPixyAngle();
		return angle;
    }

    @Override
    protected void usePIDOutput(double output) {
        /*DriveArcade takes (throttle, turn, strafe, shifter)
          replacing turn in driveArcade with the PID output, and feeding
          the normal joystick values to the rest, allows us to keep our
          robot always facing the tape while allowing the driver to
          still move forwards, backwards, and sideways(h-wheel).
          This makes PIxyDrive effectively  an "aimbot" for our robot
          and the tape on the field.
        */

        Robot.driveTrain.driveArcade(throttle, output, strafe, shifter);
    }
}
