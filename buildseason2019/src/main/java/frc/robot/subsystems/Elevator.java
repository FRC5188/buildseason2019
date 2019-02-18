package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.elevator.ManualElevatorRaiseLower;

public class Elevator extends PIDSubsystem {

    private VictorSP leftMotor;
    private VictorSP rightMotor;
    private DigitalInput bottomHalleffect;
    private DigitalInput topHalleffect;
    private Encoder elevatorEncoder;

    //needs tuned
    private static double kp = .2, ki = 0, kd =0, kf = 0, period = 20;


    public Elevator() {
        super(kp, ki, kd, kf, period);
        leftMotor = new VictorSP(RobotMap.ELEVATOR_LEFT);
        rightMotor = new VictorSP(RobotMap.ELEVATOR_RIGHT);

        bottomHalleffect =  new DigitalInput(RobotMap.BOTTOM_HALLEFFECT);
        topHalleffect =  new DigitalInput(RobotMap.TOP_HALLEFFECT);

        elevatorEncoder = new Encoder(RobotMap.ELEVATOR_ENCODER_A, RobotMap.ELEVATOR_ENCODER_B);

        elevatorEncoder.setDistancePerPulse(.0012);
        //i have no idea what the distance per pulse is
    }

    /***
     * main method for moving the elevator
     * @param speed speed at which to move the elevator
     */
    public void move(double speed) {
        if(!this.validMove(speed)) speed = 0;
        leftMotor.set(-speed);
        rightMotor.set(speed);

        this.log();
    }

    /*
    **this logic seems weird but it does work
    **I misunderstood if the halleffects were normally high or low
     */

    /***
     * determines if it is safe for the elevator to move
     * up or down from its current position
     *
     * @param power
     * @return returns if it is safe for the elevator to move
     */
    private boolean validMove(double power) {
        boolean isValid;
        if(power < 0 && this.getTopHalleffect()) {
            isValid = false;
        }
        else if(power > 0 && this.getBottomHalleffect()){
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
    public double getElevatorEncoder() {
        return elevatorEncoder.getDistance();
    }

    /***
     * resets the elevator encoder
     */
    public void resetElevatorEnocder() {
        elevatorEncoder.reset();
    }


    /***
     * resets the elevator encoder to zero if the elevator has
     * traveled back to the bottom
     */
    private void encoderResetOnBottom(){
        if(this.bottomHalleffect.get()){
            this.elevatorEncoder.reset();
        }
}

    public void printEncoder(){
        System.out.println("Encoder: " + this.elevatorEncoder.get());
    }

    public void printHalleffects() {
        System.out.println("Top HallEffect " + this.getTopHalleffect());
        System.out.println("Bottom HallEffect " + this.getBottomHalleffect());
    }

    public boolean getTopHalleffect(){
        //i thought the they were constant true but i think i was wrong
        return !this.topHalleffect.get();
    }

    public boolean getBottomHalleffect(){

        return !this.bottomHalleffect.get();
    }

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new ManualElevatorRaiseLower());
    }

    @Override
    protected double returnPIDInput() {
        return 0;
    }

    @Override
    protected void usePIDOutput(double output) {

    }

    private void log(){
        SmartDashboard.putNumber("Elevator Encoder", this.elevatorEncoder.get());
        SmartDashboard.putData("Elevator", this);
        SmartDashboard.putBoolean("Elevator PID", this.getPIDController().isEnabled());
    }
}
