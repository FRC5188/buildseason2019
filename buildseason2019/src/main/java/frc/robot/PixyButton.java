package frc.robot;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.I2C;


public class PixyButton extends Trigger{

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