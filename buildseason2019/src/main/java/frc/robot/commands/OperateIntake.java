package frc.robot.commands;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class OperateIntake extends Command {

    private PowerDistributionPanel pdp;
    private boolean isValid = true;

    public OperateIntake(){
        this.requires(Robot.pneumatics);
        pdp = new PowerDistributionPanel();
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute(){
        double intakeWristPower = OI.operator.getRawAxis(OI.Axis.RY);
        double intakeMotorPower = OI.operator.getRawAxis(OI.Axis.RTrigger) - OI.operator.getRawAxis(OI.Axis.LTrigger);

        boolean shifter = OI.operator.getRawButton(OI.Buttons.R);

        if(Math.abs(intakeMotorPower) < .01) intakeMotorPower = 0;
        if(Math.abs(intakeWristPower) < .01) intakeWristPower = 0;

        Robot.intake.setIntakeMotor(intakeMotorPower);
        Robot.intake.setIntakeWrist(intakeWristPower, shifter);
        //sets intake wrist with shifter if enabled
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
