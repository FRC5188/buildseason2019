package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.commands.elevator.ManualElevatorRaiseLower;

public class Elevator extends PIDSubsystem {
    /*
        The Elevator is comprised of:
            Two cims on a raw box
            Two Halleffect senors: placed at the very top and bottom of the elevator.
                These are used to detect if the elevator has traveled to the bottom or top.
            An encoder of the raw box shaft: measures the distance the elevator has traveled

        The elevator is meant to be a PIDSubsystem so that the operator may push buttons for
        the PID loop to move to preset heights.
     */

    private VictorSP leftMotor;
    private VictorSP rightMotor;
    private DigitalInput bottomHallEffect;
    private DigitalInput topHallEffect;
    private Encoder elevatorEncoder;

    /*Determined using 3 trails of moving 6 inches each trail*/
    private double TICKS_PER_INCH = 28.944444444;

    /*PID values, PID loop will run every 20ms*/
    private static double kp = 0.365, ki = 0, kd = 0.2, kf = 0, period = 0.02;

    /*Constructor*/
    public Elevator() {
        /*pass P, I, D, and period values to the PID loop/controller*/
        super(kp, ki, kd, kf, period);

        /*Initialize motors*/
        leftMotor = new VictorSP(RobotMap.ELEVATOR_LEFT);
        rightMotor = new VictorSP(RobotMap.ELEVATOR_RIGHT);

        /*Initialize hall effects*/
        bottomHallEffect =  new DigitalInput(RobotMap.BOTTOM_HALLEFFECT);
        topHallEffect =  new DigitalInput(RobotMap.TOP_HALLEFFECT);

        /*Initialize encoders*/
        elevatorEncoder = new Encoder(RobotMap.ELEVATOR_ENCODER_A, RobotMap.ELEVATOR_ENCODER_B);
        elevatorEncoder.setDistancePerPulse(1/TICKS_PER_INCH * 2);

        /*Give the PID loop a range for error*/
        this.getPIDController().setAbsoluteTolerance(.5);
    }

    /***
     * Moves the elevator with a given motor power
     * 
     * @param power power at which to move the elevator
     */
    public void move(double power) {
        /*create small deadband*/
        if(Math.abs(power) < .01) power = 0;

        /*Help eliminate error by resetting the encoder*/
        this.encoderResetOnBottom();

        /*if move is not valid than set the motors to 0*/
        if(!this.validMove(power)) power = 0;

        leftMotor.set(-power);
        rightMotor.set(power);

        this.log();
    }

    /***
     * Moves the elevator with a given motor power and optionally
     * at a shifted power.
     *
     * @param power power at which to move the elevator
     * @param shifter true to enable shifting, false to disable
     */
    public void move(double power, boolean shifter) {
        /*move at half power if shifter is enabled*/
        double shiftVal = shifter ? .6 : 1;
        this.move(power * shiftVal);
    }


    /*
    **this logic seems weird but it does work
    **I misunderstood if the halleffects were normally high or low
     */

    /***
     * Determines if it is safe for the elevator to move
     * up or down from its current position by checking the direction of 
     * the elevator's movement and if it is already at the top or bottom.
     *
     * @param power Power the elevator wants to move at
     * @return returns if it is safe for the elevator to move
     */
    private boolean validMove(double power) {
        boolean isValid;
        
        /*if the elevator is moving up but is at the top already*/
        if(power < 0 && this.getTopHallEffect())
            isValid = false;

        /*if the elevator is moving down but at the bottom already*/
        else if(power > 0 && this.getBottomHallEffect())
            isValid = false;

        else
            isValid = true;

        return isValid;
    }

    /***
     * stops the elevator
     */
    public void stop() {
        move(0);
    }


    /***
     * Returns the current distance from the elevator encoder
     * 
     * @return current elevator distance
     */
    public double getEncoderDistance() {
        return elevatorEncoder.getDistance();
    }


    /***
     * Returns the current tick count from the Encoder
     *
     * @return current elevator ticks
     */
    public int getEncoderTicks(){
        return this.elevatorEncoder.get();
    }


    /***
     * Checks to see if the operator has requested to use the elevator.
     * This is determined by checking the value of the elevator joystick
     * on the operator controller.
     *
     * @return True if the operator has tried to move the elevator
     */
    public boolean operatorRequested(){

        /*LY is the joystick the operator uses, .07 is a small deadband*/
        if(Math.abs(OI.operator.getRawAxis(OI.Axis.LY)) > .07)
            return  true;

        return false;
    }

    /***
     * Resets the elevator encoder to zero if the elevator has
     * traveled back to the bottom. Helps eliminate drift in encoder distances.
     */
    private void encoderResetOnBottom(){
        if(this.getBottomHallEffect())
            this.elevatorEncoder.reset();
    }


    public void printEncoder(){
        /*used for debugging*/
        System.out.println("Encoder: " + this.getEncoderDistance());
    }

    public void printEncoderTicks(){
        /*used for debugging*/
        System.out.println("Encoder Ticks: " + this.getEncoderTicks());

    }

    public void printHalleffects() {
        /*used for debugging*/
        System.out.println("Top HallEffect " + this.getTopHallEffect());
        System.out.println("Bottom HallEffect " + this.getBottomHallEffect());
    }

    private boolean getTopHallEffect(){
        /*
          I inverted this because I thought the Halleffects were normally
          true and then became false when triggered. But, I think I was wrong and that's
          why the isValid logic looks weird. It works right now though...
         */
        return !this.topHallEffect.get();
    }

    private boolean getBottomHallEffect(){
        /*same as top halleffect*/
        return !this.bottomHallEffect.get();
    }

    @Override
    protected void initDefaultCommand() {
        /*
          Makes the elevator start in operator controller mode.
          This also insures that whenever the PID loop ends control
          is automatically passed back the operator.
         */
        this.setDefaultCommand(new ManualElevatorRaiseLower());
    }

    @Override
    protected double returnPIDInput() {
        /* Use the encoder distance as the input for our PID*/
        return this.getEncoderDistance();
    }

    @Override
    protected void usePIDOutput(double output) {
        /*Apply the output of the PID loop to the elevator motors*/
        this.move(-output);
    }

    /***
     * Sets the PID controller setpoint
     *
     * @param val Setpoint for PID controller
     */
    public void setSetpoint(double val){
        this.getPIDController().setSetpoint(val);
    }

    public boolean onTarget(){
        return this.getPIDController().onTarget();
    }

    // TODO: 5/4/2019 log better data. watch for shuffle board causing comms issues
    private void log(){
        SmartDashboard.putNumber("Elevator Encoder", this.getEncoderDistance());
        SmartDashboard.putData("Elevator", this);
        SmartDashboard.putData("Left Elevator", this.leftMotor);
        SmartDashboard.putData("Right Elevator", this.rightMotor);
        SmartDashboard.putData("Elevator PID", this.getPIDController());
        SmartDashboard.putData("Elevator Encoder", this.elevatorEncoder);
        SmartDashboard.putBoolean("Elevator PID", this.getPIDController().isEnabled());
    }
}
