package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class Pneumatics extends Subsystem{

    Compressor compressor;
    Solenoid leftHSelenoid;
    Solenoid rightHSelenoid;

    public Pneumatics(){
        compressor = RobotMap.compressor;
        compressor.setClosedLoopControl(true);
        //^^tells compressor to use pressure switch to automatically
        //regulate pressure
    }

    /**
     * Sets the H-Wheel selenoids either extended, true, or 
     * retracted, false
     * @param val value passed to selenoids
     */
    public void setHWheelSelenoids(boolean val) {
        leftHSelenoid.set(val);
        rightHSelenoid.set(val);
    }
    
    /**
     * Returns true if selenoids are active and false 
     * if inactive
     * @return state of h-Wheel selenoids
     */
    public boolean getHWeelSelenoids() {
        return leftHSelenoid.get();
        //^^left and right selenoids are the same
    }

	@Override
	protected void initDefaultCommand() {
		
	}

}