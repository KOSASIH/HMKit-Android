package com.high_mobility.HMLink.Commands;

/**
 * Created by ttiganik on 07/06/16.
 */
public class AccessResponse extends AutoCommandResponse {
    Types.LockStatus lockStatus;

    public AccessResponse(byte[] bytes) throws CommandParseException {
        super(bytes);

        if (bytes.length != 3) {
            throw new CommandParseException(CommandParseException.CommandExceptionCode.PARSE_ERROR);
        }
        else {
            type = Type.ACCESS;

            if (bytes[2] == 0x00) {
                lockStatus = Types.LockStatus.UNLOCKED;
            }
            else if (bytes[2] == 0x01) {
                lockStatus = Types.LockStatus.LOCKED;
            }
            else {
                throw new CommandParseException(CommandParseException.CommandExceptionCode.PARSE_ERROR);
            }
        }
    }

    public Types.LockStatus getLockStatus() {
        return lockStatus;
    }
}
