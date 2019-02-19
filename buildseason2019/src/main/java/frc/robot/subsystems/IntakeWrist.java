package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.OperateIntakeWrist;

public class IntakeWrist extends Subsystem {

    private VictorSP intakeWrist;
    AnalogInput pot;

    private int MAX_WRIST_POT = 2750;
    private int MIN_WRIST_POT = 620;

    public IntakeWrist(){
        this.intakeWrist = new VictorSP(RobotMap.INTAKE_WRIST);
        this.pot = new AnalogInput(RobotMap.INTAKE_WRIST_POT);

        this.log();
    }

    // TODO: control wrist position with potentiometer

    /**
     * sets the power to the intake wrist motor
     * @param power power to give motor
     */
    public void setIntakeWrist(double power){
        this.intakeWrist.set(power);
    }

    public void printPotValue(){
        System.out.println("POT vlaue: " + this.getPotValue());
    }
    public double getPotValue(){
        return this.pot.getValue();
    }

    /**
     * sets the power to the intake wrist motor
     * @param power power to give motor
     * @param shifter shifts intake wrist to .5 power
     */
    public void setIntakeWrist(double power, boolean shifter){
        double shiftVal = shifter ? .5 : 1;

        if(!this.isValid(power))
            power = 0;

        this.intakeWrist.set(power * shiftVal);
    }

    /***
     * Checks if the intake wrist is capable of moving in
     * a desired direction before applying power by using a potentiometer and
     * upper and lower limits.
     *
     * @param power power at which motors are to move
     * @return true if move is valid
     */
    private boolean isValid(double power){
        boolean isValid;
        if(power > 0 && this.getPotValue() > MAX_WRIST_POT)
            isValid = false;
        else if(power < 0 && this.getPotValue() < MIN_WRIST_POT)
            isValid = false;
        else
            isValid = true;

        return isValid;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new OperateIntakeWrist());
    }

    public void log(){
        //data from subsytem to log to the dashboard
        SmartDashboard.putData("Intake Wrist", this.intakeWrist);
    }
}
