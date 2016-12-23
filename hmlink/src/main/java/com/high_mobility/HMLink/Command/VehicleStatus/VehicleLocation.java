package com.high_mobility.HMLink.Command.VehicleStatus;

import com.high_mobility.HMLink.Command.CommandParseException;
import com.high_mobility.HMLink.Command.Command.Identifier;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by ttiganik on 16/12/2016.
 */

public class VehicleLocation extends FeatureState {
    float latitude;
    float longitude;

    VehicleLocation(byte[] bytes) throws CommandParseException {
        super(Identifier.VEHICLE_LOCATION);

        if (bytes.length != 11) throw new CommandParseException();
        latitude = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 3, 3 + 4)).getFloat();
        longitude = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 7, 7 + 4)).getFloat();
    }

    /**
     *
     * @return The latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     *
     * @return The longitude
     */
    public double getLongitude() {
        return longitude;
    }
}
