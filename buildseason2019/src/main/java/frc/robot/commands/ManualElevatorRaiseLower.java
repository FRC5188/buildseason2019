package frc.robot.commands;


import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class ManualElevatorRaiseLower extends Command {

    public ManualElevatorRaiseLower() {
        requires(Robot.elevator);
    }

    public void initialize() {

    }

    public void execute() {
        double power = OI.operator.getRawAxis(OI.Axis.LY);

        if(Math.abs(power) < .01) power = 0;
//        Robot.elevator.printHalleffects();
      Robot.elevator.move(power);
    }

    public void interrupted() {
        //isFinished = true;
    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub

        return false;
    }

}