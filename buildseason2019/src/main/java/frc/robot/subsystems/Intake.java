package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.OperateCargoIntake;

public class Intake extends Subsystem {

    /**
     * This intake consist of a single 775 pro and a versa-planetary
     * gear box. It simply moves a set of rollers to suck in or spit
     * out cargo. We only get cargo from the loading station and
     * the intake is a part of the carriage that moves with the
     * elevator.
     */

    private VictorSP intakeMotor;

    public Intake(){
        this.intakeMotor = new VictorSP(RobotMap.INTAKE_MOTOR);
    }

    /**
     * Sets the power to the intake motor
     *
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
