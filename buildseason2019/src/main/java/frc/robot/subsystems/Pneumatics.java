package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class Pneumatics extends Subsystem{

    /**
     * The pneumatics subsystem contains all of the methods required
     * to use any of the pneumatics on the robot. Our pneumatics system
     * consist of: three pistons on our hatch manipulator to push off and
     * place a hatch, and one piston to extend our manipulator outside of
     * our frame perimeter to place a hatch on the rocket, since there are no
     * cut outs for bumpers.
     *
     * The three hatch pistons are tied to the same solenoid.
     */

    private Compressor compressor;
    private Solenoid hatchSolenoid, slideSolenoid;

    /*constructor*/
    public Pneumatics(){
        compressor = RobotMap.compressor;
        hatchSolenoid = new Solenoid(RobotMap.HATCH_SOLENOID);
        slideSolenoid = new Solenoid(RobotMap.SLIDE_SOLENOID);

        compressor.setClosedLoopControl(true);
        /**
         * tells compressor to use pressure switch to automatically
         * regulate pressure
         */
    }

    /***
     * Sets the state of the linear slide piston
     *
     * @param state The state to set the piston
     */
    public void setSlideSolenoid(boolean state){
        slideSolenoid.set(state);
    }

    /***
     * Sets the state of the hatch panel pistons
     *
     * @param val true to fire, false to retract
     */
    public void setHatchSolenoids(boolean val) {
        hatchSolenoid.set(val);
    }

	@Override
	protected void initDefaultCommand() {
        /**
         * This command does have a default command since
         * operating the pneumatics happens across multiple subsystems
         * and having a default command would stop the other commands
         * from running.
        */
    }

}