package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class OperateCargoIntake extends Command {

    /*
    * This command runs our cargo intake. The intake only consist of
    * a set of wheels, powered by a 775 pro, sitting on top of the hatch intake.
    **/

    public OperateCargoIntake(){
        this.requires(Robot.intake);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute(){
        /*use the left and right triggers on the logitech controller for intaking the cargo*/
        double intakeMotorPower = OI.operator.getRawAxis(OI.Axis.RTrigger) -
                                  OI.operator.getRawAxis(OI.Axis.LTrigger);

        /*create a small deadband for the motor power*/
        if(Math.abs(intakeMotorPower) < .01) intakeMotorPower = 0;

        /*set intake power*/
        Robot.intake.setIntakeMotor(intakeMotorPower);
    }

    @Override
    protected boolean isFinished() {
        /*
          this command never needs to finish since it's the only command
          using the cargo intake
         */
        return false;
    }
}
