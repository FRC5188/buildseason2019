package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.ManualIntake;
import frc.robot.commands.OperateIntake;

public class Intake extends Subsystem {

    private VictorSP intakeWrist, intakeMotor;
    private Compressor compressor;
    private Solenoid hatchsolenoid;

    public Intake(){
        this.intakeMotor = new VictorSP(RobotMap.intakeMotor);
        this.intakeWrist = new VictorSP(RobotMap.intakeWrist);

        this.compressor = RobotMap.compressor;
        this.hatchsolenoid = new Solenoid(RobotMap.HatchSolenoid);
    }
    public void setHatchSelenoids(boolean val) {
        hatchsolenoid.set(val);
    }


    public void setIntakeWrist(double power){
        this.intakeWrist.set(power);
    }

    public void setIntakeMotor(double power){
        this.intakeMotor.set(power);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new OperateIntake());
    }
}
