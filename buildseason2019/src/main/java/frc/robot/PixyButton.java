package frc.robot;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.I2C;


public class PixyButton extends Trigger{

  //implementation of the trigger class to make a "pixy button"
  //the button acts exactly like a normal button excpet the robot has to see
  //the tape and have the button be pressed to return true. This makes it so if 
  //the tape is lost the pixydrive command will stop running even if the driver
  // hasn't released the button yet. Saving vailuable time

    private final GenericHID m_joystick;
    private final int m_buttonNumber;

    I2C wire = Robot.i2c;
  
    /**
     * Create a joystick button for triggering commands.
     *
     * @param joystick     The GenericHID object that has the button (e.g. Joystick, KinectStick,
     *                     etc)
     * @param buttonNumber The button number (see {@link GenericHID#getRawButton(int) }
     */
    public PixyButton (GenericHID joystick, int buttonNumber) {
      m_joystick = joystick;
      m_buttonNumber = buttonNumber;
    }

      /**
   * Starts the given command whenever the button is newly pressed.
   *
   * @param command the command to start
   */
  public void whenPressed(final Command command) {
    whenActive(command);
  }

  /**
   * Starts the command when the button is released.
   *
   * @param command the command to start
   */
  public void whenReleased(final Command command) {
    whenInactive(command);
  }

  /**
   * Gets the value of the joystick button.
   *
   * @return The value of the joystick button
   */
  @Override
  public boolean get() {
    return m_joystick.getRawButton(m_buttonNumber) && wire.isTape();
    //return false;
  }
}