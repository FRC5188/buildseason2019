package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.HSoleniod;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class Pneumatics extends Subsystem{

    private Compressor compressor;
    private Solenoid hSolenoid;

    public Pneumatics(){
        compressor = RobotMap.compressor;
        hSolenoid = new Solenoid(RobotMap.HSolenoid);
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
    public void setHWheelSolenoids(boolean val) { hSolenoid.set(val);
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
		setDefaultCommand(new HSoleniod());
	}

}