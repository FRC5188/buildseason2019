package frc.robot.commands.elevator;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;

public class ManualElevatorRaiseLower extends Command {

    /*
    * This is the default command for the elevator. It runs by default 
    * when no other command, i.e. PID,. needs to use the elevator
    */

    public ManualElevatorRaiseLower()
    {
        requires(Robot.elevator);
        SmartDashboard.putBoolean("Man Elevator", false);
        //boolean to tell when elevator is in manual mode on the dashboard
    }

    public void initialize() {
        this.setInterruptible(true);
        //allow PID to interrupt and use the elevator
    }

    public void execute() {
        //grab joystick pos and shifter val
        double power = OI.operator.getRawAxis(OI.Axis.LY);
        boolean shifter = OI.operator.getRawButton(OI.Buttons.R);

        //move elevator
        Robot.elevator.move(power, shifter);

        //put data to the dashboard for viewing
         SmartDashboard.putBoolean("Man Elevator Running", true);
         SmartDashboard.putNumber("Elevator Encoder", Robot.elevator.getElevatorEncoderDistance());
    }

    public void end(){
        //when interrupted stop the motors
        Robot.elevator.stop();
        SmartDashboard.putBoolean("Man Elevator Running", false);
        //notify on dashboard that its stopped
    }

    public void interrupted() {
        this.end();
    }

    @Override
    protected boolean isFinished() {
        return false;
        //this command never finishes. it only is interrupted by the pid command
    }

}