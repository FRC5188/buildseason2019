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
            Two Halleffect senors: placed at the very top and bottom of the elevator
                these are used to detect if the elevator has traveled to the bottom or top
            An encoder of the raw box shaft: measures the distance the elevator has traveled

        The elevator is meant to be a PIDSubsystem so that the operator may push buttons for
        the PID loop to move to preset heights. However, the logic for the PID commands is not finished
        and the elevator is only being used manually.
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

        /*Initialize motors&*/
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
     * main method for moving the elevator
     * @param speed speed at which to move the elevator
     */
    public void move(double speed) {
        //create small deadband
        if(Math.abs(speed) < .01) speed= 0;

        this.encoderResetOnBottom();

        // outputMin = prevOutput - accel;
        // outputMax = prevOutput + accel;

        //if move is not valid than set the motors to 0
        if(!this.validMove(speed)) speed = 0;

        // if(speed > outputMax){
        //     speed = outputMax;
        // }
        // else if(speed < outputMin){
        //     speed = outputMin;
        // }

        leftMotor.set(-speed);
        rightMotor.set(speed);

        // prevOutput = speed;

        this.log();
    }

    /***
     * main method for moving the elevator
     * @param speed speed at which to move the elevator
     * @param shifter true to enable shifting, false to disable
     */
    public void move(double speed, boolean shifter) {
        //move at half speed if shifter is enabled
        double shiftVal = shifter ? .6 : 1;
        this.move(speed * shiftVal);
    }


    /*
    **this logic seems weird but it does work
    **I misunderstood if the halleffects were normally high or low
     */

    /***
     * determines if it is safe for the elevator to move
     * up or down from its current position. The halleffects should be
     * mounted at the very top and bottom of the elevator to ensure
     * max elevator movement.
     *
     * @param power
     * @return returns if it is safe for the elevator to move
     */
    private boolean validMove(double power) {
        boolean isValid;
        //if the elevator is moving up but is at the top already
        if(power < 0 && this.getTopHallEffect()) {
            isValid = false;
        }
        //if the elevator is moving down but at the bottom already
        else if(power > 0 && this.getBottomHallEffect()){
            isValid = false;
        }
        else {
            isValid = true;
        }

        return isValid;
    }

    /***
     * stops the elevator
     */
    public void stop() {
        move(0);
    }


    /***
     * returns the current distance from the elevator encoder
     * @return current elevator distance
     */
    public double getElevatorEncoderDistance() {
        return elevatorEncoder.getDistance();
    }

    public int getEncoderTicks(){
        return this.elevatorEncoder.get();
    }

    public boolean operatorRequested(){
        boolean controlRequested = false;

        if(Math.abs(OI.operator.getRawAxis(OI.Axis.LY)) > .07){
            controlRequested = true;
        }

        return controlRequested;
    }

    /***
     * resets the elevator encoder to zero if the elevator has
     * traveled back to the bottom
     */
    private void encoderResetOnBottom(){
        //this method is currently not used but will be later
        if(this.getBottomHallEffect())
            this.elevatorEncoder.reset();
}

    public void printEncoder(){
        //used for debugging
        System.out.println("Encoder: " + this.getElevatorEncoderDistance());
    }

    public void printEncoderTicks(){
        System.out.println("Encoder Ticks: " + this.getEncoderTicks());

    }

    public void printHalleffects() {
        //used for debugging
        System.out.println("Top HallEffect " + this.getTopHallEffect());
        System.out.println("Bottom HallEffect " + this.getBottomHallEffect());
    }

    public boolean getTopHallEffect(){
        //I inverted this because I thought the Halleffects were normally
        //true and then became false when triggered. But, I think I was wrong and thats
        //why the isValid logic looks weird. It works right now though...
        return !this.topHallEffect.get();
    }

    public boolean getBottomHallEffect(){
        //same as top halleffect
        return !this.bottomHallEffect.get();
    }

    @Override
    protected void initDefaultCommand() {
        //this.setDefaultCommand(new PIDElevatorRaiseLower());
        this.setDefaultCommand(new ManualElevatorRaiseLower());
        //run the elevator in manual mode right now
    }

    @Override
    protected double returnPIDInput() {
        //return 0;
        return this.getElevatorEncoderDistance();

        //we aren't using PID right now so...
    }

    @Override
    protected void usePIDOutput(double output) {
        this.move(-output);
        //this probably isn't right but we aren't
        //using PID right now so...
    }

    public void setSetpoint(double val){
        this.getPIDController().setSetpoint(val);
    } 

    public boolean onTarget(){
        return this.getPIDController().onTarget();
    }

    private void log(){
        SmartDashboard.putNumber("Elevator Encoder", this.getElevatorEncoderDistance());
        SmartDashboard.putData("Elevator", this);
        SmartDashboard.putData("Left Elevator", this.leftMotor);
        SmartDashboard.putData("Right Elevator", this.rightMotor);
        SmartDashboard.putData("Elevator PID", this.getPIDController());
        SmartDashboard.putData("Elevator Encoder", this.elevatorEncoder);
        SmartDashboard.putBoolean("Elevator PID", this.getPIDController().isEnabled());
        //this.printEncoder();
        //this.printHalleffects();
    }
}
