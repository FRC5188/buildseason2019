package frc.robot;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.I2C;


public class PixyButton extends Trigger{
    /**
     * Implementation of the trigger class to make a 'pixy button."
     * The button functions the same as a normal button but has to
     * both be pressed AND see the tape to return true. If the tape is not
     * seen, but the button is pressed, then it'll return false as if it wasn't
     * even pressed. This means if the tape is lost the pixydrive command will
     * stop running, just as if the driver had let go of the pixydrive button.
     * This helps save time on the field.
     */

    /*Most of this code is copied from the button class*/

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
      /*Return true only when the button is pressed and the robot see the tape*/
    return m_joystick.getRawButton(m_buttonNumber) && wire.isTape();
  }
}