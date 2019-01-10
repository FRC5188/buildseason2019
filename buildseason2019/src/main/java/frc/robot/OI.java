/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

 //contains controller axis and buttons
public class OI {

  //user controllers
  //access with OI.Controller.CONTROLLERNAME
	private static class Controller {
		public static final int DRIVE = 0, OPERATOR = 1;
	}
	
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
		
	public OI() {
		//create controllers
		drive = new Joystick(Controller.DRIVE);
		operator = new Joystick(Controller.OPERATOR);
	}
}
