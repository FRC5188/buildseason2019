package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;

public class OperateIntakeWrist extends Command {

    //this command is responsible for operating the intake wrist

    public OperateIntakeWrist(){
        this.requires(Robot.intakeWrist);
    }

    @Override
    protected void initialize() {
        this.log();
    }

    @Override
    protected void execute(){
        double intakeWristPower = OI.operator.getRawAxis(OI.Axis.RY);
        boolean shifter = OI.operator.getRawButton(OI.Buttons.R);

        if(Math.abs(intakeWristPower) < .01) intakeWristPower = 0;
        //create a deadband for the joystick

        Robot.intakeWrist.setIntakeWrist(intakeWristPower, shifter);
        //sets intake wrist with shifter if enabled

        this.log();
    }

    @Override
    protected boolean isFinished() {
        //this command never needs to finish since it's the only command using the intake wrist
        return false;
    }

    public void log(){
        //data to log
        SmartDashboard.putNumber("POT Value", Robot.intakeWrist.getPotValue());
        //Robot.intakeWrist.printPotValue();
    }
}
