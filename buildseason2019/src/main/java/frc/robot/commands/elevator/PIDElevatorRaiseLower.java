package frc.robot.commands.elevator;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;

public class PIDElevatorRaiseLower extends Command {
    //this command should be the only command used during a match
    //it should configure the PID loop with its settings if needed
    //it is responsible for the elevator moving to the correct setpoints
    //and for allowing the operator to both move the elevator manually
    //and for giving control back to the operator if the operator cancels
    //the pid loop by manually moving the elevator, including handling the
    //disabling of the pid loop.

    //this command shouldn't set any setpoints. it should just handle when and how
    //the pidloop runs. setpoints should be set with specific commands so the can be mapped to buttons.

    public PIDElevatorRaiseLower() {
        requires(Robot.elevator);
    }

    public void initialize() {
        //log elevator encoder
        SmartDashboard.putNumber("Elevator Encoder", Robot.elevator.getElevatorEncoder());
        //log the current setpoint
        SmartDashboard.putNumber("Elevator Setpoint", Robot.elevator.getPIDController().getSetpoint());
        //log pid enabled status
        SmartDashboard.putBoolean("Elevator PID", Robot.elevator.getPIDController().isEnabled());
        //set pid tolerance and max and min output and input
        Robot.elevator.getPIDController().setAbsoluteTolerance(1.5);
        //elevator can be off by 1.5 inches
        Robot.elevator.getPIDController().setInputRange(0, 70);
        //not sure what max height in inches is
        Robot.elevator.getPIDController().setOutputRange(-1, 1);
        //pretty sure it starts disabled, so this may be useless
        Robot.elevator.getPIDController().disable();
    }


    //expected operation:
        //when the robot starts pid should be disabled*
            //the elevator moves only on joystick*
        //when a setpoint button is pressed than the pid takes the elevator to the setpoint
            //when at the setpoint, pid should hold the elevator in that position
            //if the operator uses the joystick than pid should disable and give control to the operator
        //if a setpoint has been canceled than the elevator should remain in "manual" mode until a new button is pressed
    public void execute() {
        //get joystick value
        double power = OI.operator.getRawAxis(OI.Axis.LY);

        //if joystick is above a deadband
        if(power > .01){
            //disable pid and do checks
            //check if pid is running
            if(Robot.elevator.getPIDController().isEnabled())
                Robot.elevator.getPIDController().disable();

            //operator elevator manually
            Robot.elevator.move(power);
        }
//        if there is no joystick value
//            should do nothing if a setpoint button isn't pressed
//            if a setpoint button has been pressed then let pid run to that setpoint

        //log elevator encoder
        SmartDashboard.putNumber("Elevator Encoder", Robot.elevator.getElevatorEncoder());
        //log the current setpoint
        SmartDashboard.putNumber("Elevator Setpoint", Robot.elevator.getPIDController().getSetpoint());
        //log pid enabled status
        SmartDashboard.putBoolean("Elevator PID", Robot.elevator.getPIDController().isEnabled());
    }

    public void interrupted() {
        //this command shouldn't be interrupted
    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        //this command should always run
        return false;
    }

}