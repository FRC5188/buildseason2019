package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class Pneumatics extends Subsystem{

    private Compressor compressor;
    private Solenoid hSolenoid, hatchSoleniod;

    public Pneumatics(){
        compressor = RobotMap.compressor;
       // hSolenoid = new Solenoid(RobotMap.HSolenoid);
      //  hatchSoleniod = new Solenoid(RobotMap.HatchSolenoid);
        compressor.setClosedLoopControl(true);
        //^^tells compressor to use pressure switch to automatically
        //regulate pressure
    }

    public void setHatchSelenoids(boolean val) {
        hatchSoleniod.set(val);
    }


    /**
     * Sets the H-Wheel selenoids either extended, true, or 
     * retracted, false
     * @param val value passed to selenoids
     */
    public void setHWheelSelenoids(boolean val) {
        hSolenoid.set(val);
    }
    
    /**
     * Returns true if selenoids are active and false 
     * if inactive
     * @return state of h-Wheel selenoids
     */
    public boolean getHWeelSelenoid() {
        return hSolenoid.get();
        //^^left and right selenoids are the same
    }

	@Override
	protected void initDefaultCommand() {

        //setDefaultCommand(new HSoleniodOut());
	}

}