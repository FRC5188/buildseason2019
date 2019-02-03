package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.ReadArduino;

public class I2C extends Subsystem {

	private edu.wpi.first.wpilibj.I2C wire;
	private static final int MAX_BYTES = 32;

    public I2C() {
		this.wire = RobotMap.wire;
    }

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
	private String read(){
		byte[] data = new byte[MAX_BYTES];//create a byte array to hold the incoming data
		wire.read(4, MAX_BYTES, data);//use address 4 on i2c and store it in data
		String output = new String(data);//create a string from the byte array
		int pt = output.indexOf((char)255);
		return (String) output.subSequence(0, pt < 0 ? 0 : pt);//im not sure what these last two lines do
													//sorry :(
	}

	public double getPixyAngle(){
		double angle = Double.parseDouble(this.read());
		System.out.println("Pixy Angle: " + angle);
		return angle;
	}

	@Override
	protected void initDefaultCommand() {
		//this.setDefaultCommand(new ReadArduino());
    }

}