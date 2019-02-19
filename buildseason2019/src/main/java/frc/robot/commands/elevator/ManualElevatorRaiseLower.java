package frc.robot.commands.elevator;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;

public class ManualElevatorRaiseLower extends Command {


    /*
        This is currently the only command run by the elevator. The PID
        logic for the elevator still needs work to be functional.
     */

    public ManualElevatorRaiseLower()
    {
        requires(Robot.elevator);
        SmartDashboard.putBoolean("Man Elevator", false);
    }

    public void initialize() {
        this.setInterruptible(true);
    }

    public void execute() {
        double power = OI.operator.getRawAxis(OI.Axis.LY);
        boolean shifter = OI.operator.getRawButton(OI.Buttons.R);

        Robot.elevator.move(power, shifter);

         SmartDashboard.putBoolean("Man Elevator Running", true);
         SmartDashboard.putNumber("Elevator Encoder", Robot.elevator.getElevatorEncoderDistance());
    }

    public void end(){
        Robot.elevator.stop();
        SmartDashboard.putBoolean("Man Elevator Running", false);
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