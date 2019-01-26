#include <Wire.h>
#include <Pixy2.h>
//built in class from arduino, strongly suggest looking at it on their website
//it is not a complicated class

//plug sda on RoboRIO into A4
//plug scl on RoboRIO into A5
//connect the two grounds

String output = "1";
Pixy2 pixy;
int x1, x2, center;

void setup(){
  Serial.begin(9600);
  Wire.begin(4);                // join i2c bus with address #4 as a slave device
  Wire.onReceive(receiveEvent); // Registers a function to be called when a slave device receives a transmission from a master
  Wire.onRequest(requestEvent); // Register a function to be called when a master requests data from this slave device
  pinMode (10,OUTPUT);
  pixy.init();
}
void loop(){
  digitalWrite (10, LOW);
  pixy.ccc.getBlocks();
  x1 = pixy.ccc.blocks[0].m_x;
  x2 = pixy.ccc.blocks[1].m_x;
  x1 += pixy.ccc.blocks[0].m_width;
  x2 += pixy.ccc.blocks[1].m_width;
  center = (x1 + x2) / 2;
  output.replace(output, String(center));
  Serial.println (center);  
  
   

}

void requestEvent(){//called when RoboRIO request a message from this device
  Wire.write(output.c_str()); //writes data to the RoboRIO, converts it to string
   digitalWrite (10, HIGH);
   delay(10);
 Serial.println (output);
}

void receiveEvent(int bytes){//called when RoboRIO "gives" this device a message

}
