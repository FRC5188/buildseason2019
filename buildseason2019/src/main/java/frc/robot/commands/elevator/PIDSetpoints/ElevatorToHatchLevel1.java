package frc.robot.commands.elevator.PIDSetpoints;


import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ElevatorToHatchLevel1 extends Command {
    
    //SEE THIS DOCUMENTATION FOR THE REST OF THE SETPOINTS
    
    //the idea of this command is to interface between the elevator's
    //pid loop and the operator controller.
    //when a button is pressed it should call this command to change the setpoint
    //of the elevator pid loop

    public ElevatorToHatchLevel1() {
        requires(Robot.elevator);
    }

    public void initialize() {
        //set pid setpoint
        Robot.elevator.setSetpoint(4.5);
        Robot.elevator.enable();

        this.setInterruptible(true);
        //allow to be interrupted by manual elevator movement
    }

    public void execute() {
        //nothing needs to be done during this command since it only 
        //sets the subsystem setpoint
    }

    public void interrupted() {
        this.end();
    }

    @Override
    protected boolean isFinished() {
        //end the command when PID has done its job or 
        //the operator no longer wants to use PID
        return Robot.elevator.onTarget() || Robot.elevator.operatorRequested();
    }

    @Override
    protected void end(){
        //disable the PID loop on end
        Robot.elevator.disable();
    }

}