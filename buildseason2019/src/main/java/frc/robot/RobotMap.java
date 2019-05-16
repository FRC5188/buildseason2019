/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Compressor;

public class RobotMap {

    /*motor mappings*/

    /*Drive motors are talonsSRX's or VictorSPX's and use CAN*/
    /*The rest are PWM*/
    public static final int FRONT_LEFT = 0;
    public static final int FRONT_RIGHT = 1;
    public static final int BACK_LEFT = 0;
    public static final int BACK_RIGHT = 1;
    public static final int ELEVATOR_LEFT = 5;
    public static final int ELEVATOR_RIGHT = 6;
    public static final int INTAKE_MOTOR = 8;

    /*Solenoid block mappings*/
    public static final int HATCH_SOLENOID = 1;
    public static final int SLIDE_SOLENOID = 2;

    /*elevator halleffect*/
    public static final int BOTTOM_HALLEFFECT = 3;
    public static final int TOP_HALLEFFECT = 9;

    /*elevator encoder*/
    public static final int ELEVATOR_ENCODER_A = 5;
    public static final int ELEVATOR_ENCODER_B = 6;

    /*Gyro*/
    public static final AHRS gyro = new AHRS(I2C.Port.kMXP);

    /*I2C bus, uses address 4*/
    public static final I2C wire = new I2C(I2C.Port.kOnboard, 4);
    //public static final I2C wire = new I2C(I2C.Port.kMXP, 4);

    public static final int COMPRESSOR_CAN_ID = 0;
    public static final Compressor compressor = new Compressor(COMPRESSOR_CAN_ID);
}