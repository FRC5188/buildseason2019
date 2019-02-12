package frc.robot.commands.elevator.PIDSetpoints;


import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class ElevatorToHatchLevel1 extends Command {
    //the idea of this command is to interface between the elevator's
    //pid loop and the operator controller.
    //when a button is pressed it should call this command to change the setpoint
    //of the elevator pid loop

    public ElevatorToHatchLevel1() {
        //requires(Robot.elevator);
        // i dont think we want this to require the elevator
    }

    public void initialize() {
        //set pid setpoint
        //enable pid
        Robot.elevator.getPIDController().setSetpoint(20);
        //not sure what the setpoint for level one should be
        Robot.elevator.getPIDController().enable();

        //not sure if anything else needs to be done
    }

    public void execute() {

    }

    public void interrupted() {
        //should't ever be interrupted because isfinished is always true
        //isFinished = true;
    }

    @Override
    protected boolean isFinished() {

        return true;
    }

}