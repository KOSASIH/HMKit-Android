package com.high_mobility.HMLink.Command.Capability;

import com.high_mobility.HMLink.Command.CommandParseException;
import com.high_mobility.HMLink.Command.Incoming.VehicleStatus;

/**
 * Created by ttiganik on 13/12/2016.
 */

public class HonkFlashCapability extends StateCapability {
    AvailableCapability.Capability honkHornCapability;
    AvailableCapability.Capability flashLightsCapability;
    AvailableCapability.Capability emergencyFlasherCapability;

    public AvailableCapability.Capability getHonkHornCapability() {
        return honkHornCapability;
    }

    public AvailableCapability.Capability getFlashLightsCapability() {
        return flashLightsCapability;
    }

    public AvailableCapability.Capability getEmergencyFlasherCapability() {
        return emergencyFlasherCapability;
    }

    public HonkFlashCapability(byte[] bytes) throws CommandParseException {
        super(VehicleStatus.State.HONK_FLASH);
        if (bytes.length != 5) throw new CommandParseException();
        honkHornCapability = AvailableCapability.Capability.fromByte(bytes[2]);
        flashLightsCapability = AvailableCapability.Capability.fromByte(bytes[3]);
        emergencyFlasherCapability = AvailableCapability.Capability.fromByte(bytes[4]);
    }
}
