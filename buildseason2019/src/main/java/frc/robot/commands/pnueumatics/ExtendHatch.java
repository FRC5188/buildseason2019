package frc.robot.commands.pnueumatics;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ExtendHatch extends Command{


    /*command to extend our hatch and cargo mech out when placing on the rocket*/

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        /*set the solenoids true to drop the h-wheel*/
        Robot.pneumatics.setSlideSolenoid(true);
    }

    @Override
    protected boolean isFinished() {
        //this command immediately finishes since it needs to set
        //the solenoids once for them to stay
        return true;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}
