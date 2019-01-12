package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class Pneumatics extends Subsystem{

    Compressor compressor;
    Solenoid leftHSelenoid;
    Solenoid rightHSelenoid;

    public Pneumatics(){
        compressor = new Compressor(0);
        compressor.setClosedLoopControl(true);
    }

    public void setHWheelSelenoids(boolean val) {
        System.out.println("setting H Wheel Selenoids " + val);
        leftHSelenoid.set(val);
        rightHSelenoid.set(val);
    }
    
	@Override
	protected void initDefaultCommand() {
		
	}

}