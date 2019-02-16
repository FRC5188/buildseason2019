package frc.robot.commands;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class OperateIntake extends Command {

    private PowerDistributionPanel pdp;
    private boolean isValid = true;

    public OperateIntake(){
        this.requires(Robot.intake);
        pdp = new PowerDistributionPanel();
        this.requires(Robot.intake);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute(){
        double intakeWristPower = OI.operator.getRawAxis(OI.Axis.RY);
        double intakeMotorPower = OI.operator.getRawAxis(OI.Axis.RTrigger) - OI.operator.getRawAxis(OI.Axis.LTrigger);
        if(OI.operator.getRawButton(OI.Buttons.A)){

        }
        boolean shifter = OI.operator.getRawButton(OI.Buttons.R);
        double shiftVal = shifter ? .5 : 1;

        if(Math.abs(intakeMotorPower) < .01) intakeMotorPower = 0;
        if(Math.abs(intakeWristPower) < .01) intakeWristPower = 0;

        if(pdp.getCurrent(4) > 10) this.isValid = false;

        Robot.intake.setIntakeMotor(intakeMotorPower);

        if(!this.isValid && intakeWristPower > 0){
            Robot.intake.setIntakeWrist(intakeWristPower * shiftVal);
            this.isValid = true;
        }
        else if(this.isValid) {
            Robot.intake.setIntakeWrist(intakeWristPower * shiftVal);
        }
        else{
            Robot.intake.setIntakeWrist(0);
        }
    }
    @Override
    protected boolean isFinished() {
        return false;
    }
}
