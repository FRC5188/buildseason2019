package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.HSoleniod;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class Pneumatics extends Subsystem{

    private Compressor compressor;
    private Solenoid hSelenoid;

    public Pneumatics(){
        compressor = RobotMap.compressor;
        hSelenoid = new Solenoid(RobotMap.hSelenoid);
        compressor.setClosedLoopControl(true);
        compressor.enabled();
        //^^tells compressor to use pressure switch to automatically
        //regulate pressure
    }

    /**
     * Sets the H-Wheel selenoids either extended, true, or 
     * retracted, false
     * @param val value passed to selenoids
     */
    public void setHWheelSelenoids(boolean val) {
        hSoleniod.set(val);
    }
    
    /**
     * Returns true if selenoids are active and false 
     * if inactive
     * @return state of h-Wheel selenoids
     */
    public boolean getHWeelSelenoid() {
        return hSelenoid.get();
        //^^left and right selenoids are the same
    }

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new HSoleniod());
	}

}