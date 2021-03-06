package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class DPadButton extends Button {

    /*
    * Custom button class to use the dpad buttons on a logitech or xbox controller
    *
    * This is not our original code. I believe it's on chief delphi but I don't
    * have a link.
    **/
    
    Joystick joystick;
    Direction direction;

    public DPadButton(Joystick joystick, Direction direction) {
        this.joystick = joystick;
        this.direction = direction;
    }

    public enum Direction {
        UP(0), RIGHT(90), DOWN(180), LEFT(270);

        int direction;

         Direction(int direction) {
            this.direction = direction;
        }
    }

    public boolean get() {
        int dPadValue = joystick.getPOV();
        return (dPadValue == direction.direction) || (dPadValue == (direction.direction + 45) % 360)
                || (dPadValue == (direction.direction + 315) % 360);
    }

}