package com.highmobility.hmkit.Command.VehicleStatus;
import com.highmobility.hmkit.ByteUtils;
import com.highmobility.hmkit.Command.Command.Identifier;
import com.highmobility.hmkit.Command.CommandParseException;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by ttiganik on 14/12/2016.
 */

public class ParkingTicket extends FeatureState {
    String operatorName;
    int operatorTicketId;
    Date ticketStart;
    Date ticketEnd;
    com.highmobility.hmkit.Command.Incoming.ParkingTicket.State state;

    /**
     *
     * @return The ticket state
     */
    public com.highmobility.hmkit.Command.Incoming.ParkingTicket.State getState() {
        return state;
    }

    /**
     *
     * @return The operator name
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     *
     * @return The ticket id
     */
    public int getOperatorTicketId() {
        return operatorTicketId;
    }

    /**
     *
     * @return Ticket start date
     */
    public Date getTicketStartDate() {
        return ticketStart;
    }

    /**
     *
     * @return Ticket end date. null if not set
     */
    public Date getTicketEndDate() {
        return ticketEnd;
    }

    public ParkingTicket(byte[] bytes) throws CommandParseException {
        super(Identifier.PARKING_TICKET);
        if (bytes.length < 4) throw new CommandParseException();

        state = com.highmobility.hmkit.Command.Incoming.ParkingTicket.State.fromByte(bytes[3]);
        int operatorNameSize = bytes[4];
        int position = 5;

        try {
            operatorName = new String(Arrays.copyOfRange(bytes, position, position + operatorNameSize), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new CommandParseException();
        }

        int ticketIdSize = bytes[position + operatorNameSize];
        position = position + operatorNameSize + 1;

        operatorTicketId = ByteUtils.getInt(Arrays.copyOfRange(bytes, position, position + ticketIdSize));
        position = position + ticketIdSize;

        ticketStart = ByteUtils.getDate(Arrays.copyOfRange(bytes, position, position + 6));
        position = position + 6;
        ticketEnd = ByteUtils.getDate(Arrays.copyOfRange(bytes, position, position + 6));
    }
}