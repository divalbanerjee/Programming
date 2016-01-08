package org.siliconedgerobotics.ftc.utils;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Wei on 1/8/2016.
 */

/*
* Utility class for wrapping registration methods for robot components.
* This allows the same hardware to be easily used by different opmodes.
*/
public class HardwareRegistry {

    /*
    * Hardware map, provided by the opmode utilizing this class
    */
    HardwareMap map;

    /*
    * Constructor
    * @param map The hardware map provided by an opmode
    */
    public HardwareRegistry(HardwareMap map) {
        this.map = map;
    }


}
