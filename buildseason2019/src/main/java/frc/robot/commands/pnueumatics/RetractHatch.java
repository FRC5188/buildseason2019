package frc.robot.commands.pnueumatics;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RetractHatch extends Command{

    /*command to rectract our hatch and cargo mech in placing placing on the rocket*/


    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        //set the solenoids true to drop the h-wheel
        Robot.pneumatics.setSlideSolenoid(false);
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
