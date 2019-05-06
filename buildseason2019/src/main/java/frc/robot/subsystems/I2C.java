package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

public class I2C extends Subsystem {

    /**
        This subsystem's job is to read data from the arduino.
        It parses the data and makes it available to commands like PixyDrive.

        Ideally we use a RIODuino, which is a Arduino clone with a MXP header
        on it, plugged right into the RoboRIO. However, we had some issues at
        our last comp and just used a regular Arduino plugged into the onboard
        I2C port, not the MXP port.
     */

    /*has to be defined this way because the class is also named I2C*/
	private edu.wpi.first.wpilibj.I2C wire;
	private static final int MAX_BYTES = 32;

    public I2C() {
		this.wire = RobotMap.wire;
        SmartDashboard.putNumber("Pixy Data", 400);
        /**
            400 is just a random number. It's higher than any number we would ever see
            so if we see 400, then we know the pixy is not giving us any data a the moment.
         */
    }


    /*we don't actually write any date to the arduino, it's just here*/

	/**
	 * Writes data to the Arduino over the I2C bus
     *
	 * @param input String to be sent to Arduino
	 */
	public void write(String input){
	        /*This code was copied from CD a couple years ago*/

			char[] charArray = input.toCharArray();//creates a char array from the input string
			byte[] writeData = new byte[charArray.length];//creates a byte array from the char array
			for (int i = 0; i < charArray.length; i++)
				writeData[i] = (byte) charArray[i];//adds the char elements to the byte array 

			wire.transaction(writeData, writeData.length, null, 0);//sends each byte to arduino
		}
	
	/**
	 * Reads data from the Arduino on the I2C bus
     *
	 * @return String recieed from the I2C bus
	 */
	private String read(){
	    /*Also copied from CD a couple years ago*/

		byte[] data = new byte[MAX_BYTES];//create a byte array to hold the incoming data
		wire.read(4, MAX_BYTES, data);//use address 4 on i2c and store it in data
		String output = new String(data);//create a string from the byte array
		int pt = output.indexOf((char)255);
		return (String) output.subSequence(0, pt < 0 ? 0 : pt);//im not sure what these last two lines do
													//sorry :(
	}


    // TODO: 5/4/2019 return seperate error codes for easier debugging
    /***
     * Returns the angle received from the Arduino. An angle of 400 is returned
     * as an error code if the Pixy does not see any targets or the Arduino returns bad
     * or NULL data.
     *
     * @return Angle from the robot to the nearest set of vision targets
     */
	public double getPixyAngle(){
        /**
           The arduino sends the angle to the robot as a string. If something goes wrong somewhere
           it is possible to get a value which is not a number, which would break the robot code
           during a match. The try catch will "throw an error" of 400 if no angle could be found
           from the Arduino.

         */
        double angle;

        try{
            angle = Double.parseDouble(this.read());
        } catch(NumberFormatException e) {
			angle = 400;
		}

        SmartDashboard.putNumber("Pixy Data", angle);
		return angle;
	}


    /***
     * Returns if the vision tape can currently be seen. This can be used
     * for driver feedback on the dashboard or to help determine when
     * certain commands should run.
     *
     * @return  True if the Pixy successfully sees a set of targets
     */
	public boolean isTape(){
		boolean tape = true;
		int angle = (int)this.getPixyAngle();
		if(angle == 400)
			tape = false;

		/*Display the tape to the driver*/
		SmartDashboard.putBoolean("Tape", tape);
		return tape;
	}
	
	@Override
	protected void initDefaultCommand() {
        /**
	      This subsystem doesn't actaully have a command that uses it.
          Instead, it is just "used" as needed.
          This doesn't quite fit the flow of command based very well
          and it should probably be a static class or something like Robot or RobotMap.
        */
    }

}
