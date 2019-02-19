package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class Pneumatics extends Subsystem{

    private Compressor compressor;
    private Solenoid hSolenoid, hatchSolenoid;

    public Pneumatics(){
        compressor = RobotMap.compressor;
        hSolenoid = new Solenoid(RobotMap.H_SOLENOID);
        hatchSolenoid = new Solenoid(RobotMap.HATCH_SOLENOID);
        compressor.setClosedLoopControl(true);
        //^^tells compressor to use pressure switch to automatically
        //regulate pressure
    }

    /**
     * Sets the H-Wheel selenoids either extended, true, or 
     * retracted, false
     * @param val value passed to selenoids
     */
    public void setHWheelSolenoids(boolean val) {
        hSolenoid.set(val);
    }

    /***
     * sets hatch panel seleniods to fire hatch panel
     * @param val true to fire, false to retract
     */
    public void setHatchSolenoids(boolean val) {
        hatchSolenoid.set(val);
    }


    /**
     * Returns true if selenoids are active and false 
     * if inactive
     * @return state of h-Wheel selenoids
     */
    public boolean getHWeelSelenoid() {
        return hSolenoid.get();
        //^^left and right solenoids are the same
    }

	@Override
	protected void initDefaultCommand() {
        //this command does have a default command since
        //operating the pneumatics happens across multiple commands
        //and having a default command would stop the other commands
        //from running
	}

}