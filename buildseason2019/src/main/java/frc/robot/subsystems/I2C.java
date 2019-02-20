package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

public class I2C extends Subsystem {


    /*
        This subsystem's job is to read data from the arduino.
        It parses the data and makes it available to commands like PixyDrive
     */

    //has to be defined this way because the class is also named I2C
	private edu.wpi.first.wpilibj.I2C wire;
	private static final int MAX_BYTES = 32;

    public I2C() {
		this.wire = RobotMap.wire;
        SmartDashboard.putNumber("Pixy Data", 400);
        //400 is just a random number. It's higher than any number we would ever see
        //so if we see 400, then we know the pixy is not giving us any data a the moment
    }


    //we don't actually write any date to the arduino, it's just here
	/**
	 * Writes data to the Arduino over the I2C bus
	 * @param input String to be sent to Arduino
	 * 
	 */
	public void write(String input){
			char[] charArray = input.toCharArray();//creates a char array from the input string
			byte[] writeData = new byte[charArray.length];//creates a byte array from the char array
			for (int i = 0; i < charArray.length; i++) {//writes each byte to the arduino
				writeData[i] = (byte) charArray[i];//adds the char elements to the byte array 
			}
			wire.transaction(writeData, writeData.length, null, 0);//sends each byte to arduino
		}
	
	/**
	 * Reads data from the Arduino on the I2C bus
	 * @return String recieed from the I2C bus
	 */
	public String read(){
		byte[] data = new byte[MAX_BYTES];//create a byte array to hold the incoming data
		wire.read(4, MAX_BYTES, data);//use address 4 on i2c and store it in data
		String output = new String(data);//create a string from the byte array
		int pt = output.indexOf((char)255);
		return (String) output.subSequence(0, pt < 0 ? 0 : pt);//im not sure what these last two lines do
													//sorry :(
	}

	public double getPixyAngle(){
		double angle;
		//the arduino sends the angle to robot needs to turn to face the target.
        //if the traget is not found by the pixy on the arduino than it sends an empty string, ""
        //Since it is possible to have an empty string and not a number, than parseDouble may
        // throw an exception, requiring it to be surrounded in a try catch
		 angle = Double.parseDouble(this.read());
		System.out.println("Pixy Angle: " + angle);
        SmartDashboard.putNumber("Pixy Data", angle);
		return angle;
	}

	@Override
	protected void initDefaultCommand() {
	    //this subsystem doesn't actaully have a command that uses it.
        //instead it is just "used" as needed
        //this doesn't quite fit the flow of command based very well
        //and it should probably be a static class or somthing like Robot or RobotMap
    }

}
