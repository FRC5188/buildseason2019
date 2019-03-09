package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.OperateCargoIntake;

public class Intake extends Subsystem {

    private VictorSP intakeMotor;

    public Intake(){
        this.intakeMotor = new VictorSP(RobotMap.INTAKE_MOTOR);
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
        setDefaultCommand(new OperateCargoIntake());
    }
}
