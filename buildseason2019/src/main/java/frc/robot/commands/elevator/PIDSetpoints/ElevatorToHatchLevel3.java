package frc.robot.commands.elevator.PIDSetpoints;


import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ElevatorToHatchLevel3 extends Command {
    //the idea of this command is to interface between the elevator's
    //pid loop and the operator controller.
    //when a button is pressed it should call this command to change the setpoint
    //of the elevator pid loop

    public ElevatorToHatchLevel3() {
        requires(Robot.elevator);
    }

    public void initialize() {
        //set pid setpoint
        Robot.elevator.setSetpoint(59);
        Robot.elevator.enable();

        this.setInterruptible(true);
    }

    public void execute() {

    }

    public void interrupted() {
        this.end();
    }

    @Override
    protected boolean isFinished() {

        return Robot.elevator.onTarget() || Robot.elevator.operatorRequested();
    }

    @Override
    protected void end(){
        Robot.elevator.disable();
    }

}