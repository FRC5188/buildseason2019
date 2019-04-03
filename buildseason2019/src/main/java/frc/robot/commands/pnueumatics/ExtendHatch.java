package frc.robot.commands.pnueumatics;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ExtendHatch extends Command{

    //command to extend our hatch and cargo mech out when placing on the rocket

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        //don't require pneumatics so other commands can use it
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //set the solenoids true to drop the h-wheel
        Robot.pneumatics.setSlideSolenoid(true);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        //this command immediately finishes since it needs to set
        //the solenoids once for them to stay
        return true;
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
