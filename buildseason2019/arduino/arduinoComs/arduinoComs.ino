#include <Wire.h>
#include <Pixy2.h>
//built in class from arduino, strongly suggest looking at it on their website
//it is not a complicated class

//plug sda on RoboRIO into A4
//plug scl on RoboRIO into A5
//connect the two grounds

//TODO: deploy to arduino
    //commented line 36
    //commented line 24
    //commented line 20
    //commented line 54
    //commented line 61-63

String output = "1";
Pixy2 pixy;
int x1, x2, center, ox, sx = 316;
double angle;

void setup(){
  //Serial.begin(9600);
  Wire.begin(4);                // join i2c bus with address #4 as a slave device
  Wire.onReceive(receiveEvent); // Registers a function to be called when a slave device receives a transmission from a master
  Wire.onRequest(requestEvent); // Register a function to be called when a master requests data from this slave device
 // pinMode (10,OUTPUT);
  pixy.init();
}

void loop(){
  
 // digitalWrite (10, LOW);
  pixy.ccc.getBlocks(); //Update the information from the pixy to the arduino
  x1 = pixy.ccc.blocks[0].m_x;  //Read the x position of the first and second 'tape' blocks
  x2 = pixy.ccc.blocks[1].m_x;
  x1 += pixy.ccc.blocks[0].m_width; //Add the width of the tape on each block to determine the center coordinate
  x2 += pixy.ccc.blocks[1].m_width;
  if(pixy.ccc.numBlocks==2){  //Confirm that the pixy has detected both pieces of tape
    center = (x1 + x2) / 2; //Calculate the center of both tapes, simple average
    ox = 100*sx/2 - 100*center;//in hundredths of degrees
    angle = ox/sx * 60;
    //output is now angle to turn
     output.replace(output, String(angle/100)); //Updates the output string
     //^^this should be the gyro angle at end of writing
      //Serial.println (output);
  }
  else{
     output.replace(output, ""); //Updates the output string
  }
  
  

  //(screen width / 2 - center) / screen width
//multiply by FOV



}

void requestEvent(){//called when RoboRIO request a message from this device
  Wire.write(output.c_str()); //writes data to the RoboRIO, converts it to string
   //digitalWrite (10, HIGH);
   //delay(10);
  // Serial.println (output);
}

void receiveEvent(int bytes){//called when RoboRIO "gives" this device a message

}

//distance centerX is away from center of screen, divived by total number of pixels in the scrren