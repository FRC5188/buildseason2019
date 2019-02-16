package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.ManualElevatorRaiseLower;

public class Elevator extends PIDSubsystem {

    VictorSP leftMotor;
    VictorSP rightMotor;
    DigitalInput bottomHalleffect;
    DigitalInput topHalleffect;
    Encoder elevatorEncoder;

    private static double kp = .2, ki = 0, kd =0, kf = 0, period = 20;


    public Elevator() {
        super(kp, ki, kd, kf, period);
        leftMotor = new VictorSP(RobotMap.elevatorLeft);
        rightMotor = new VictorSP(RobotMap.elevatorRight);

        bottomHalleffect =  new DigitalInput(RobotMap.bottomHalleffect);
        topHalleffect =  new DigitalInput(RobotMap.topHalleffect);

        elevatorEncoder = new Encoder(RobotMap.elevatorEnocderA, RobotMap.elevatorEnocderB);

        SmartDashboard.putNumber("Elevator Encoder", this.elevatorEncoder.get());
    }

    public void stop() {
        move(0);
    }

    private boolean validMove(double power) {
        boolean isValid = false;
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

    public double getElevatorEncoder() {
        return elevatorEncoder.getDistance();
    }

    public void resetElevatorEnocder() {
        elevatorEncoder.reset();
    }

    public void move(double speed) {
        if(!this.validMove(speed)) speed = 0;
        leftMotor.set(-speed);
        rightMotor.set(speed);//may need flipped

        this.printEncoder();
        SmartDashboard.putNumber("Elevator Encoder", this.elevatorEncoder.get());
        this.encoderResetOnBottom();
    }

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
}
