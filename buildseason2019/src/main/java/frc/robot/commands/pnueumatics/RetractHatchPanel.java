package frc.robot.commands.pnueumatics;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RetractHatchPanel extends Command{

    /*
     * this command sets the hatch solenoids false to retract them
     * after being fired
     *
     * this command should be assigned to the whenReleased of the hatch button in OI
     */

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {

        Robot.pneumatics.setHatchSolenoids(false);

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
