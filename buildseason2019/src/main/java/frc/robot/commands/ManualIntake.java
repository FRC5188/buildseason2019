package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class ManualIntake extends Command {

    public ManualIntake(){
        this.requires(Robot.intake);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute(){
        double intakeWristPower = OI.operator.getRawAxis(OI.Axis.RY);
        double intakeMotorPower = OI.operator.getRawAxis(OI.Axis.RTrigger) - OI.operator.getRawAxis(OI.Axis.LTrigger);

        if(Math.abs(intakeMotorPower) < .01) intakeMotorPower = 0;
        if(Math.abs(intakeWristPower) < .01) intakeWristPower = 0;

        Robot.intake.setIntakeMotor(intakeMotorPower);
        Robot.intake.setIntakeWrist(intakeWristPower);

    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
