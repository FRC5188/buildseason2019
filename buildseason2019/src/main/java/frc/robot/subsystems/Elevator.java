package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.RobotMap;
import frc.robot.commands.elevator.ManualElevatorRaiseLower;
import frc.robot.commands.elevator.PIDElevatorRaiseLower;

public class Elevator extends PIDSubsystem {


    //the elevator should be controlled with a joystick and buttons
    //the buttons are most likely the primary interface and will
    //raise and lower the elevator to a specific height on the rocket ship.
    //the joystick should allow for the operator to adjust the height manually
    //once at a setpoint. Using the joystick while the elevator is using
    //pid to reach a setpoint should disable the pid and return control to
    //the operator.
    private static double kp = 0, ki = 0, kd = 0, kf = 0, period = .02;
    private VictorSP leftMotor, rightMotor;
    private DigitalInput bottomHalleffect, topHalleffect;
    Encoder elevatorEncoder;

    public Elevator() {
        super(kp, ki, kd, kf, period);
        leftMotor = new VictorSP(RobotMap.elevatorLeft);
        rightMotor = new VictorSP(RobotMap.elevatorRight);

        bottomHalleffect =  new DigitalInput(RobotMap.bottomHalleffect);
        topHalleffect =  new DigitalInput(RobotMap.topHalleffect);

        elevatorEncoder = new Encoder(RobotMap.elevatorEncoderA, RobotMap.elevatorEncoderB);

        this.resetElevatorEnocder();
        this.getPIDController().disable();

    }

    public void moveElevator(double speed) {
        if(!this.validMove(speed)) speed = 0;
        leftMotor.set(-speed);
        rightMotor.set(speed);//may need flipped

    }

    public void stop() {
        moveElevator(0);
    }

    private boolean validMove(double power) {
        boolean isValid;
        if(power < 0 && !bottomHalleffect.get()) {
            isValid = false;
        }
        else if(power > 0 && !topHalleffect.get()){
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
    public void printHalleffects() {
        System.out.println("Top HallEffect " + topHalleffect.get());
        System.out.println("Bottom HallEffect " + bottomHalleffect.get());
    }

    @Override
    protected double returnPIDInput() {
        return this.getElevatorEncoder();
    }

    @Override
    protected void usePIDOutput(double output) {
        this.moveElevator(output);
    }

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new PIDElevatorRaiseLower());
    }
}
