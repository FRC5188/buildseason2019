package frc.robot.commands.pnueumatics;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class FireHatchPanel extends Command{

    /*
    * this command sets the hatch panel solenoids true to fire
    *
    * this command should be assigned to the whenPressed of the hatch button in OI
    */

    @Override
    protected void initialize() {
        //don't require pneumatics so other commands can use it
    }

    @Override
    protected void execute() {
        Robot.pneumatics.setHatchSolenoids(true);
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
