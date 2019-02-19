package frc.robot.commands.pnueumatics;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LiftHWheel extends Command{

    /*
    * this command sets the h-wheel solenoids false to retract the h-wheel
     */

    @Override
    protected void initialize() {
        //don't require pneumatics so other commands can use it
    }

    @Override
    protected void execute() {
        Robot.pneumatics.setHWheelSolenoids(false);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}
