package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.OperateIntake;

public class Intake extends Subsystem {

    private VictorSP intakeWrist, intakeMotor;

    public Intake(){
        this.intakeMotor = new VictorSP(RobotMap.INTAKE_MOTOR);
        this.intakeWrist = new VictorSP(RobotMap.INTAKE_WRIST);
    }

    // TODO: control wrist position with potentiometer

    /**
     * sets the power to the intake wrist motor
     * @param power power to give motor
     */
    public void setIntakeWrist(double power){
        this.intakeWrist.set(power);
    }


    /**
     * sets the power to the intake wrist motor
     * @param power power to give motor
     * @param shifter shifts intake wrist to .5 power
     */
    public void setIntakeWrist(double power, boolean shifter){
        double shiftVal = shifter ? .5 : 1;
        this.intakeWrist.set(power * shiftVal);
    }
    /**
     * sets the power to the intake motor
     * @param power power to give motor
     */
    public void setIntakeMotor(double power){
        this.intakeMotor.set(power);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new OperateIntake());
    }
}
