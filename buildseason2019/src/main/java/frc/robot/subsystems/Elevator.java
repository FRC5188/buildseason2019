package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.ManualElevatorRaiseLower;

public class Elevator extends Subsystem {

    VictorSP leftMotor;
    VictorSP rightMotor;
    DigitalInput bottomHalleffect;
    DigitalInput topHalleffect;
    Encoder elevatorEncoder;


    public Elevator() {
        leftMotor = new VictorSP(RobotMap.elevatorLeft);
        rightMotor = new VictorSP(RobotMap.elevatorRight);

        bottomHalleffect =  new DigitalInput(RobotMap.bottomHalleffect);
        topHalleffect =  new DigitalInput(RobotMap.topHalleffect);

        //	elevatorEncoder = new Encoder(RobotMap.elevatorEncoderA, RobotMap.elevatorEncoderB);
    }

    public void stop() {
        move(0);
    }

    private boolean validMove(double power) {
        boolean isValid = false;
        if(power > 0 && !bottomHalleffect.get()) {
            isValid = false;
        }
        else if(power < 0 && !topHalleffect.get()){
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
        //disable checking for testing without halleffect
        leftMotor.set(-speed);
        rightMotor.set(speed);//may need flipped

    }

    public void printHalleffects() {
        System.out.println("Top HallEffect " + topHalleffect.get());
        System.out.println("Bottom HallEffect " + bottomHalleffect.get());
    }

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new ManualElevatorRaiseLower());
    }
}
