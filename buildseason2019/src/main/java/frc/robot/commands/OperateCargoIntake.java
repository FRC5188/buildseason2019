package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class OperateCargoIntake extends Command {

    public OperateCargoIntake(){
        this.requires(Robot.intake);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute(){
        double intakeMotorPower;
        intakeMotorPower = OI.operator.getRawAxis(OI.Axis.RTrigger) - OI.operator.getRawAxis(OI.Axis.LTrigger);
        //use the left and right triggers on the logitech controller for intaking the cargo

        if(Math.abs(intakeMotorPower) < .01) intakeMotorPower = 0;
        //create a small deadband for the motor power

        Robot.intake.setIntakeMotor(intakeMotorPower);
        //set intake power
    }

    @Override
    protected boolean isFinished() {
        //this command never needs to finish since it's the only command
        //using the cargo intake
        return false;
    }
}
