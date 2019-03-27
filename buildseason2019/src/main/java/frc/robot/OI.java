/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.commands.driveTrain.PixyDrive;
import frc.robot.commands.elevator.PIDSetpoints.ElevatorToHatchLevel1;
import frc.robot.commands.elevator.PIDSetpoints.ElevatorToHatchLevel2;
import frc.robot.commands.elevator.PIDSetpoints.ElevatorToHatchLevel3;
import frc.robot.commands.elevator.PIDSetpoints.ElevatorToLoadingStation;
import frc.robot.commands.pnueumatics.DropHWheel;
import frc.robot.commands.pnueumatics.ExtendHatch;
import frc.robot.commands.pnueumatics.FireHatchPanel;
import frc.robot.commands.pnueumatics.LiftHWheel;
import frc.robot.commands.pnueumatics.RetractHatch;
import frc.robot.commands.pnueumatics.RetractHatchPanel;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

 //contains controller axis and buttons
 //Joysticks are normal logitech gamepads
public class OI {

  //driver controllers
	private static class Controller {
		public static final int DRIVE = 0, OPERATOR = 1;
	}

	Command pixyDrive;
	Command elevatorPID;
	//create a pid command for the elevator the same way that
    //the pixyDrive is a pid command for the driveTrain;
	
  //buttons of controller 
  //access with OI.Buttons.BUTTONNAME
	public static class Buttons {
		public static int
		A = 1,
		B = 2,
		X = 3,
		Y = 4,
		L = 5,
		R = 6,
		BACK = 7,
		START = 8,
		L_STICK = 9,
		R_STICK = 10,
		TOTAL_BUTTONS = 10;
	}
	
  //controller axis  (including rTrigger and lTrigger) 
  //access with OI.Axis.AXISNAME
	public static class Axis {
		public static int
		LX = 0,
		LY = 1,
		LTrigger = 2,
		RTrigger = 3,
		RX = 4,
		RY = 5,
		AXIS_TOTAL = 6;
	}
	
	//controllers
	public static Joystick drive;
	public static Joystick operator;

	//Buttons
	public static PixyButton pixyButton;

	public static Button hatchPanelButton;
	public static Button hWheelDownButton;
    public static Button hWheelUpButton;
    public static Button extendHatch;
    public static Button retractHatch;

	public static DPadButton rocketLevel1Button;
    public static DPadButton rocketLevel2Button;
	public static DPadButton rocketLevel3Button;
    public static DPadButton loadingStationButton;

    public OI() {
		//create controllers
		drive = new Joystick(Controller.DRIVE);
		operator = new Joystick(Controller.OPERATOR);

		/*create buttons*/
		// pixyButton = new PixyButton(drive, OI.Buttons.L );
		hatchPanelButton = new JoystickButton(operator, Buttons.A);
		hWheelDownButton = new JoystickButton(drive, Buttons.A);
        hWheelUpButton = new JoystickButton(drive, Buttons.B);

        rocketLevel1Button = new DPadButton(operator, DPadButton.Direction.LEFT);
        rocketLevel2Button = new DPadButton(operator, DPadButton.Direction.UP);
		rocketLevel3Button = new DPadButton(operator, DPadButton.Direction.RIGHT);
        loadingStationButton = new DPadButton(operator, DPadButton.Direction.DOWN);

        extendHatch = new JoystickButton(operator, Buttons.X);
        retractHatch = new JoystickButton(operator, Buttons.B);

        /*pixy drive command to be reused*/
        // pixyDrive = new PixyDrive();

		/*create button mappings*/
		hatchPanelButton.whenPressed(new FireHatchPanel());
		hatchPanelButton.whenReleased(new RetractHatchPanel());
        retractHatch.whenPressed(new RetractHatch());
        extendHatch.whenPressed(new ExtendHatch());

		hWheelDownButton.whenPressed(new DropHWheel());
		hWheelUpButton.whenPressed(new LiftHWheel());

		rocketLevel1Button.whenPressed(new ElevatorToHatchLevel1());
		rocketLevel2Button.whenPressed(new ElevatorToHatchLevel2());
		rocketLevel3Button.whenPressed(new ElevatorToHatchLevel3());
        loadingStationButton.whenPressed(new ElevatorToLoadingStation());

		// pixyButton.whenPressed(pixyDrive);
		// pixyButton.whenReleased(new Command(){
		// 	@Override
		// 	protected void initialize(){
		// 		if(pixyDrive.isRunning()) pixyDrive.cancel();
		// 	}
		// 	@Override
		// 	protected boolean isFinished() {
		// 		return true;
		// 	}
		// });
	}
}
