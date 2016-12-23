package com.high_mobility.HMLink.Command;

import com.high_mobility.HMLink.ByteUtils;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import static com.high_mobility.HMLink.Command.Command.Identifier.FAILURE;

/**
 * Created by ttiganik on 25/05/16.
 */
public class Command {
    public enum Identifier {
        FAILURE(new byte[] { 0x00, (byte)0x02 }),
        CAPABILITIES(new byte[] { 0x00, (byte)0x10 }),
        VEHICLE_STATUS(new byte[] { 0x00, (byte)0x11 }),
        DOOR_LOCKS(new byte[] { 0x00, (byte)0x20 }),
        TRUNK_ACCESS(new byte[] { 0x00, (byte)0x21 }),
        WAKE_UP(new byte[] { 0x00, (byte)0x22 }),
        CHARGING(new byte[] { 0x00, (byte)0x23 }),
        CLIMATE(new byte[] { 0x00, (byte)0x24 }),
        ROOFTOP(new byte[] { 0x00, (byte)0x25 }),
        HONK_FLASH(new byte[] { 0x00, (byte)0x26 }),
        REMOTE_CONTROL(new byte[] { 0x00, (byte)0x27 }),
        VALET_MODE(new byte[] { 0x00, (byte)0x28 }),
        HEART_RATE(new byte[] { 0x00, (byte)0x29 }),
        VEHICLE_LOCATION(new byte[] { 0x00, (byte)0x30 }),
        NAVI_DESTINATION(new byte[] { 0x00, (byte)0x31 }),
        DELIVERED_PARCELS(new byte[] { 0x00, (byte)0x32 });

        public static Identifier fromIdentifier(byte[] bytes) {
            return fromIdentifier(bytes[0], bytes[1]);
        }

        public static Identifier fromIdentifier(byte firstByte, byte secondByte) {
            if (is(FAILURE, firstByte, secondByte)) {
                return FAILURE;
            }
            else if (is(CAPABILITIES, firstByte, secondByte)) {
                return CAPABILITIES;
            }
            else if (is(VEHICLE_STATUS, firstByte, secondByte)) {
                return VEHICLE_STATUS;
            }
            else if (is(DOOR_LOCKS, firstByte, secondByte)) {
                return DOOR_LOCKS;
            }
            else if (is(TRUNK_ACCESS, firstByte, secondByte)) {
                return TRUNK_ACCESS;
            }
            else if (is(WAKE_UP, firstByte, secondByte)) {
                return WAKE_UP;
            }
            else if (is(CHARGING, firstByte, secondByte)) {
                return CHARGING;
            }
            else if (is(CLIMATE, firstByte, secondByte)) {
                return CLIMATE;
            }
            else if (is(ROOFTOP, firstByte, secondByte)) {
                return ROOFTOP;
            }
            else if (is(HONK_FLASH, firstByte, secondByte)) {
                return HONK_FLASH;
            }
            else if (is(REMOTE_CONTROL, firstByte, secondByte)) {
                return REMOTE_CONTROL;
            }
            else if (is(VALET_MODE, firstByte, secondByte)) {
                return VALET_MODE;
            }
            else if (is(HEART_RATE, firstByte, secondByte)) {
                return HEART_RATE;
            }
            else if (is(VEHICLE_LOCATION, firstByte, secondByte)) {
                return VEHICLE_LOCATION;
            }
            else if (is(NAVI_DESTINATION, firstByte, secondByte)) {
                return NAVI_DESTINATION;
            }
            else if (is(DELIVERED_PARCELS, firstByte, secondByte)) {
                return DELIVERED_PARCELS;
            }
            else {
                return null;
            }
        }

        Identifier(byte[] identifier) {
            this.identifier = identifier;
        }
        private byte[] identifier;
        public byte[] getIdentifier() {
            return identifier;
        }

        static boolean is (Identifier feature, byte firstByte, byte secondByte) {
            return feature.getIdentifier()[0] == firstByte && feature.getIdentifier()[1] == secondByte;
        }
    }

    public interface Type {
        byte getType();
        Identifier getIdentifier();
        byte[] getIdentifierAndType();
    }

    public static Type typeFromBytes(byte identifierByteOne, byte identifierByteTwo, byte type) throws CommandParseException {
        Identifier identifier = Identifier.fromIdentifier(identifierByteOne, identifierByteTwo);
        switch (identifier) {
            case FAILURE: {
                if (type == FailureMessage.FAILURE_MESSAGE.getType())
                    return FailureMessage.FAILURE_MESSAGE;
                break;
            }
            case CAPABILITIES: {
                if (type == Capabilities.GET_CAPABILITIES.getType())
                    return Capabilities.GET_CAPABILITIES;
                else if (type == Capabilities.CAPABILITIES.getType())
                    return Capabilities.CAPABILITIES;
                else if (type == Capabilities.GET_CAPABILITY.getType())
                    return Capabilities.GET_CAPABILITY;
                else if (type == Capabilities.CAPABILITY.getType())
                    return Capabilities.CAPABILITY;
                break;
            }
            case VEHICLE_STATUS: {
                if (type == VehicleStatus.GET_VEHICLE_STATUS.getType())
                    return VehicleStatus.GET_VEHICLE_STATUS;
                else if (type == VehicleStatus.VEHICLE_STATUS.getType())
                    return VehicleStatus.VEHICLE_STATUS;
                break;
            }
            case DOOR_LOCKS: {
                if (type == DoorLocks.GET_LOCK_STATE.getType())
                    return DoorLocks.GET_LOCK_STATE;
                else if (type == DoorLocks.LOCK_STATE.getType())
                    return DoorLocks.LOCK_STATE;
                else if (type == DoorLocks.LOCK_UNLOCK.getType())
                    return DoorLocks.LOCK_UNLOCK;
                break;
            }
            case TRUNK_ACCESS: {
                if (type == TrunkAccess.GET_TRUNK_STATE.getType())
                    return TrunkAccess.GET_TRUNK_STATE;
                else if (type == TrunkAccess.TRUNK_STATE.getType())
                    return TrunkAccess.TRUNK_STATE;
                else if (type == TrunkAccess.OPEN_CLOSE.getType())
                    return TrunkAccess.OPEN_CLOSE;
                break;
            }
            case WAKE_UP: {
                if (type == WakeUp.WAKE_UP.getType())
                    return WakeUp.WAKE_UP;
                break;
            }
            case CHARGING: {
                if (type == Charging.GET_CHARGE_STATE.getType())
                    return Charging.GET_CHARGE_STATE;
                else if (type == Charging.CHARGE_STATE.getType())
                    return Charging.CHARGE_STATE;
                else if (type == Charging.START_STOP_CHARGING.getType())
                    return Charging.START_STOP_CHARGING;
                else if (type == Charging.SET_CHARGE_LIMIT.getType())
                    return Charging.SET_CHARGE_LIMIT;
                break;
            }
            case CLIMATE: {
                if (type == Climate.GET_CLIMATE_STATE.getType())
                    return Climate.GET_CLIMATE_STATE;
                else if (type == Climate.CLIMATE_STATE.getType())
                    return Climate.CLIMATE_STATE;
                else if (type == Climate.SET_CLIMATE_PROFILE.getType())
                    return Climate.SET_CLIMATE_PROFILE;
                else if (type == Climate.START_STOP_HVAC.getType())
                    return Climate.START_STOP_HVAC;
                else if (type == Climate.START_STOP_DEFOGGING.getType())
                    return Climate.START_STOP_DEFOGGING;
                else if (type == Climate.START_STOP_DEFROSTING.getType())
                    return Climate.START_STOP_DEFROSTING;
                break;
            }
            case ROOFTOP: {
                if (type == RooftopControl.GET_ROOFTOP_STATE.getType())
                    return RooftopControl.GET_ROOFTOP_STATE;
                else if (type == RooftopControl.ROOFTOP_STATE.getType())
                    return RooftopControl.ROOFTOP_STATE;
                else if (type == RooftopControl.CONTROL_ROOFTOP.getType())
                    return RooftopControl.CONTROL_ROOFTOP;
                break;
            }
            case HONK_FLASH: {
                if (type == HonkFlash.HONK_FLASH.getType())
                    return HonkFlash.HONK_FLASH;
                break;
            }
            case REMOTE_CONTROL: {
                if (type == RemoteControl.GET_CONTROL_MODE.getType())
                    return RemoteControl.GET_CONTROL_MODE;
                else if (type == RemoteControl.CONTROL_MODE.getType())
                    return RemoteControl.CONTROL_MODE;
                else if (type == RemoteControl.START_CONTROL_MODE.getType())
                    return RemoteControl.START_CONTROL_MODE;
                else if (type == RemoteControl.STOP_CONTROL_MODE.getType())
                    return RemoteControl.STOP_CONTROL_MODE;
                else if (type == RemoteControl.CONTROL_COMMAND.getType())
                    return RemoteControl.CONTROL_COMMAND;
                break;
            }
            case VALET_MODE: {
                if (type == ValetMode.GET_VALET_MODE.getType())
                    return ValetMode.GET_VALET_MODE;
                else if (type == ValetMode.VALET_MODE.getType())
                    return ValetMode.VALET_MODE;
                break;
            }
            case HEART_RATE: {
                if (type == HeartRate.SEND_HEART_RATE.getType())
                    return HeartRate.SEND_HEART_RATE;
                break;
            }
            case VEHICLE_LOCATION: {
                if (type == VehicleLocation.GET_VEHICLE_LOCATION.getType())
                    return VehicleLocation.GET_VEHICLE_LOCATION;
                else if (type == VehicleLocation.VEHICLE_LOCATION.getType())
                    return VehicleLocation.VEHICLE_LOCATION;
                break;
            }
            case NAVI_DESTINATION: {
                if (type == NaviDestination.SET_DESTINATION.getType()) {
                    return NaviDestination.SET_DESTINATION;
                }
                break;
            }
            case DELIVERED_PARCELS: {
                if (type == DeliveredParcels.GET_DELIVERED_PARCELS.getType())
                    return DeliveredParcels.GET_DELIVERED_PARCELS;
                else if (type == DeliveredParcels.DELIVERED_PARCELS.getType())
                    return DeliveredParcels.DELIVERED_PARCELS;
                break;
            }
        }

        throw new CommandParseException();
    }

    public static Type typeFromBytes(byte[] bytes) throws CommandParseException {
        if (bytes.length < 3) throw new CommandParseException();
        return typeFromBytes(bytes[0], bytes[1], bytes[2]);
    }

    /**
     * Commands for the Failure Message category of the Auto API.
     */
    public enum FailureMessage implements Type {
        FAILURE_MESSAGE((byte)0x01);

        FailureMessage(byte messageType) {
            this.messageType = messageType;
        }

        private byte messageType;
        public byte getType() {
            return messageType;
        }

        @Override
        public Identifier getIdentifier() {
            return FAILURE;
        }

        @Override
        public byte[] getIdentifierAndType() {
            return ByteUtils.concatBytes(getIdentifier().getIdentifier(), getType());
        }
    }

    /**
     * Commands for the Vehicle Status category of the Auto API.
     */
    public enum Capabilities implements Type {
        GET_CAPABILITIES((byte)0x00),
        CAPABILITIES((byte)0x01),
        GET_CAPABILITY((byte)0x02),
        CAPABILITY((byte)0x03);

        /**
         * Get the vehicle capabilities. The car will respond with the Capabilities message that
         * manifests all different APIs that are enabled on the specific car. It is good practice
         * to only inspect the vehicle capabilities the first time when access is gained. The
         * capabilities are fixed for each car type and will not change between every session
         * unless the user meanwhile receives new permissions (requires a whole new certificate).
         *
         * @return the command bytes
         */
        public static byte[] getCapabilities() {
            return GET_CAPABILITIES.getIdentifierAndType();
        }

        /**
         * Get the capability of a certain feature. The car will respond with the Capability command
         * - to what extent the capability is supported, if at all.
         * @return the command bytes
         */
        public static byte[] getCapability(Identifier identifier) {
            return ByteUtils.concatBytes(GET_CAPABILITY.getIdentifierAndType(), identifier.getIdentifier());
        }

        Capabilities(byte messageType) {
            this.messageType = messageType;
        }
        private byte messageType;
        public byte getType() {
            return messageType;
        }

        @Override
        public Identifier getIdentifier() {
            return Identifier.CAPABILITIES;
        }

        @Override
        public byte[] getIdentifierAndType() {
            return ByteUtils.concatBytes(getIdentifier().getIdentifier(), getType());
        }
    }

    /**
     * Commands for the Vehicle Status category of the Auto API.
     */
    public enum VehicleStatus implements Type {
        GET_VEHICLE_STATUS((byte)0x00),
        VEHICLE_STATUS((byte)0x01);

        /**
         * Get the vehicle status. The car will respond with the Vehicle Status command.
         *
         * @return the command bytes
         */
        public static byte[] getVehicleStatus() {
            return GET_VEHICLE_STATUS.getIdentifierAndType();
        }

        VehicleStatus(byte messageType) {
            this.messageType = messageType;
        }

        private byte messageType;
        public byte getType() {
            return messageType;
        }

        @Override
        public Identifier getIdentifier() {
            return Identifier.VEHICLE_STATUS;
        }

        @Override
        public byte[] getIdentifierAndType() {
            return ByteUtils.concatBytes(getIdentifier().getIdentifier(), getType());
        }
    }

    /**
     * Commands for the Door Locks category of the Auto API.
     */
    public enum DoorLocks implements Type {
        GET_LOCK_STATE((byte)0x00),
        LOCK_STATE((byte)0x01),
        LOCK_UNLOCK((byte)0x02);

        /**
         * Get the lock state, which either locked or unlocked. The car will respond with the
         * Lock State command.
         *
         * @return the command bytes
         */
        public static byte[] getLockState() {
            return GET_LOCK_STATE.getIdentifierAndType();
        }

        /**
         * Attempt to lock or unlock the car. The result is received through the Lock State command.
         *
         * @param lock whether to lock or unlock the car
         * @return the command bytes
         */
        public static byte[] lockDoors(boolean lock) {
            return ByteUtils.concatBytes(LOCK_UNLOCK.getIdentifierAndType(), booleanByte(lock));
        }

        DoorLocks(byte messageType) {
            this.messageType = messageType;
        }
        private byte messageType;
        public byte getType() {
            return messageType;
        }

        @Override
        public Identifier getIdentifier() {
            return Identifier.DOOR_LOCKS;
        }

        @Override
        public byte[] getIdentifierAndType() {
            return ByteUtils.concatBytes(getIdentifier().getIdentifier(), getType());
        }
    }

    /**
     * Commands for the Trunk Access category of the Auto API.
     */
    public enum TrunkAccess implements Type {
        GET_TRUNK_STATE((byte)0x00),
        TRUNK_STATE((byte)0x01),
        OPEN_CLOSE((byte)0x02);

        /**
         * Get the trunk state, if it's locked/unlocked and closed/open. The car will respond with
         * the Trunk State command.
         *
         * @return the command bytes
         */
        public static byte[] getTrunkState() {
            return GET_TRUNK_STATE.getIdentifierAndType();
        }

        /**
         * Unlock/Lock and Open/Close the trunk. The result is received through the evented
         * Trunk State command.
         *
         * @param lockState whether to lock or unlock the trunk
         * @param position whether to open or close the trunk
         * @return the command bytes
         */
        public static byte[] setTrunkState(Constants.TrunkLockState lockState, Constants.TrunkPosition position) {
            byte[] bytes = new byte[5];
            bytes[0] = OPEN_CLOSE.getIdentifier().getIdentifier()[0];
            bytes[1] = OPEN_CLOSE.getIdentifier().getIdentifier()[1];
            bytes[2] = OPEN_CLOSE.getType();
            bytes[3] = (byte)(lockState == Constants.TrunkLockState.UNLOCKED ? 0x00 : 0x01);
            bytes[4] = (byte)(position == Constants.TrunkPosition.CLOSED ? 0x00 : 0x01);
            return bytes;
        }

        TrunkAccess(byte messageType) {
            this.messageType = messageType;
        }
        private byte messageType;
        public byte getType() {
            return messageType;
        }

        @Override
        public Identifier getIdentifier() {
            return Identifier.TRUNK_ACCESS;
        }

        @Override
        public byte[] getIdentifierAndType() {
            return ByteUtils.concatBytes(getIdentifier().getIdentifier(), getType());
        }
    }

    /**
     * Commands for the Wake Up category of the Auto API.
     */
    public enum WakeUp implements Type {
        WAKE_UP((byte)0x02);

        /**
         * Wake up the car. This is necessary when the car has fallen asleep, in which case the car
         * responds with the Failure Message to all incoming messages.
         *
         * The car is also waken up by the Lock/Unlock Doors message.
         *
         * @return the command bytes
         */
        public static byte[] wakeUp() {
            return WAKE_UP.getIdentifierAndType();
        }

        WakeUp(byte messageType) {
            this.messageType = messageType;
        }
        private byte messageType;
        public byte getType() {
            return messageType;
        }

        @Override
        public Identifier getIdentifier() {
            return Identifier.WAKE_UP;
        }

        @Override
        public byte[] getIdentifierAndType() {
            return ByteUtils.concatBytes(getIdentifier().getIdentifier(), getType());
        }
    }

    /**
     * Commands for the Charging category of the Auto API.
     */
    public enum Charging implements Type {
        GET_CHARGE_STATE((byte)0x00),
        CHARGE_STATE((byte)0x01),
        START_STOP_CHARGING((byte)0x02),
        SET_CHARGE_LIMIT((byte)0x03);

        /**
         * Get the charge state. The car will respond with the Charge State message.
         *
         * @return the command bytes
         */
        public static byte[] getChargeState() {
            return GET_CHARGE_STATE.getIdentifierAndType();
        }

        /**
         * Start or stop charging, which can only be controlled when the car is plugged in. The
         * result is sent through the evented Charge State message.
         *
         * @param start Set to true if charging should start
         * @return the command bytes
         */
        public static byte[] startCharging(boolean start) {
            return ByteUtils.concatBytes(START_STOP_CHARGING.getIdentifierAndType(), booleanByte(start));
        }

        /**
         * Set the charge limit, to which point the car will charge itself. The result is sent
         * through the evented Charge State message.
         *
         * @param limit The charge limit as percentage between 0-1
         * @return the command bytes
         */
        public static byte[] setChargeLimit(float limit) throws IllegalArgumentException {
            if (limit < 0 || limit > 1) throw new IllegalArgumentException();
            byte limitByte = (byte)(int)(limit * 100);
            return ByteUtils.concatBytes(SET_CHARGE_LIMIT.getIdentifierAndType(), limitByte);
        }

        Charging(byte messageType) {
            this.messageType = messageType;
        }
        private byte messageType;
        public byte getType() {
            return messageType;
        }

        @Override
        public Identifier getIdentifier() {
            return Identifier.CHARGING;
        }

        @Override
        public byte[] getIdentifierAndType() {
            return ByteUtils.concatBytes(getIdentifier().getIdentifier(), getType());
        }
    }

    /**
     * Commands for the Climate category of the Auto API.
     */
    public enum Climate implements Type {
        GET_CLIMATE_STATE((byte)0x00),
        CLIMATE_STATE((byte)0x01),
        SET_CLIMATE_PROFILE((byte)0x02),
        START_STOP_HVAC((byte)0x03),
        START_STOP_DEFOGGING((byte)0x04),
        START_STOP_DEFROSTING((byte)0x05);

        Climate(byte messageType) {
            this.messageType = messageType;
        }
        private byte messageType;
        public byte getType() {
            return messageType;
        }

        @Override
        public Identifier getIdentifier() {
            return Identifier.CLIMATE;
        }

        @Override
        public byte[] getIdentifierAndType() {
            return ByteUtils.concatBytes(getIdentifier().getIdentifier(), getType());
        }

        /**
         * Get the climate state. The car will respond with the Climate State message.
         *
         * @return The command bytes
         */
        public static byte[] getClimateState() {
            return GET_CLIMATE_STATE.getIdentifierAndType();
        }

        /**
         * Set the climate profile. The result is sent through the evented Climate State message
         * with the new state.
         *
         * @param states The Auto HVAC states for every weekday. States for all 7 days of the week are expected.
         * @param autoHvacConstant Whether the auto HVAC is constant.
         * @param driverTemperature The driver temperature in Celsius. // TODO: what is the limit? test as well
         * @param passengerTemperature The passenger temperature in Celsius.
         * @return The command bytes
         * @throws IllegalArgumentException When the input is incorrect
         */
        public static byte[] setClimateProfile(AutoHvacState[] states,
                                               boolean autoHvacConstant,
                                               float driverTemperature,
                                               float passengerTemperature) throws IllegalArgumentException {
            if (states.length != 7) throw new IllegalArgumentException();
            byte[] command = new byte[26];
            ByteUtils.setBytes(command, SET_CLIMATE_PROFILE.getIdentifierAndType(), 0);

            byte autoHvacDatesByte = 0x00;
            for (int i = 0; i < 7; i++) {
                if (states[i].isActive()) {
                    autoHvacDatesByte = (byte) (autoHvacDatesByte | (1 << i));

                }

                command[4 + i * 2] = (byte)states[i].getStartHour();
                command[4 + i * 2 + 1] = (byte)states[i].getStartMinute();
            }
            if (autoHvacConstant) autoHvacDatesByte = (byte)(autoHvacDatesByte | (1 << 7));
            command[3] = autoHvacDatesByte;

            byte[] driverTempByte = ByteBuffer.allocate(4).putFloat(driverTemperature).array();
            ByteUtils.setBytes(command, driverTempByte, 18);

            byte[] passengerTempByte = ByteBuffer.allocate(4).putFloat(passengerTemperature).array();
            ByteUtils.setBytes(command, passengerTempByte, 22);

            return command;
        }

        /**
         * Start or stop the HVAC system to reach driver and passenger set temperatures. The car
         * will use cooling, defrosting and defogging as appropriate. The result is sent through
         * the evented Climate State message.
         *
         * @param start Whether to start the HVAC
         * @return The command bytes
         */
        public static byte[] startHvac(boolean start) {
            return ByteUtils.concatBytes(START_STOP_HVAC.getIdentifierAndType(), booleanByte(start));
        }

        /**
         * Manually start or stop defogging. The result is sent through the evented Climate State
         * message.
         *
         * @param start Whether to start the defog
         * @return The command bytes
         */
        public static byte[] startDefog(boolean start) {
            return ByteUtils.concatBytes(START_STOP_DEFOGGING.getIdentifierAndType(), booleanByte(start));
        }

        /**
         * Manually start or stop defrosting. The result is sent through the evented Climate State
         * message.
         *
         * @param start Whether to start the defrost
         * @return The command bytes
         */
        public static byte[] startDefrost(boolean start) {
            return ByteUtils.concatBytes(START_STOP_DEFROSTING.getIdentifierAndType(), booleanByte(start));
        }
    }

    /**
     * Commands for the Rooftop Control category of the Auto API.
     */
    public enum RooftopControl implements Type {
        GET_ROOFTOP_STATE((byte)0x00),
        ROOFTOP_STATE((byte)0x01),
        CONTROL_ROOFTOP((byte)0x02);
        /**
         * Get the rooftop state. The car will respond with the Rooftop State command.
         *
         *  @return the command bytes
         */
        public static byte[] getRooftopState() {
            return GET_ROOFTOP_STATE.getIdentifierAndType();
        }

        /**
         * Set the rooftop state. The result is sent through the evented Rooftop State command.
         *
         * @param dimPercentage 0 - 1 percentage of how dimmed the rooftop should be
         * @param openPercentage 0 - 1 percentage of how open the rooftop should be
         * @return the command bytes
         */
        public static byte[] controlRooftop(float dimPercentage, float openPercentage) throws IllegalArgumentException {
            if (dimPercentage < 0f || dimPercentage > 1f || openPercentage < 0f || openPercentage > 1f) {
                throw new IllegalArgumentException();
            }

            byte[] identifier = CONTROL_ROOFTOP.getIdentifierAndType();
            byte[] message = new byte[5];
            message[0] = identifier[0];
            message[1] = identifier[1];
            message[2] = identifier[2];
            message[3] = (byte)(int)(dimPercentage * 100);
            message[4] = (byte)(int)(openPercentage * 100);
            return message;
        }

        RooftopControl(byte messageType) {
            this.messageType = messageType;
        }
        private byte messageType;
        public byte getType() {
            return messageType;
        }

        @Override
        public Identifier getIdentifier() {
            return Identifier.ROOFTOP;
        }

        @Override
        public byte[] getIdentifierAndType() {
            return ByteUtils.concatBytes(getIdentifier().getIdentifier(), getType());
        }
    }

    /**
     * Commands for the Honk Horn & Flash Lights category of the Auto API.
     */
    public enum HonkFlash implements Type {
        HONK_FLASH((byte)0x00),
        EMERGENCY_FLASHER((byte)0x01);

        /**
         * Honk the horn and/or flash the lights. This can be done simultaneously or just one
         * action at the time. It is also possible to pass in how many times the lights should
         * be flashed and how many seconds the horn should be honked.
         *
         * @param seconds how many seconds the horn should be honked, between 0 and 5
         * @param lightFlashCount how many times the light should be flashed, between 0 and 10
         * @return the command bytes
         * @throws IllegalArgumentException when the seconds or lightCount parameter is not in the
         * valid range
         */
        public static byte[] honkFlash(int seconds, int lightFlashCount) throws IllegalArgumentException {
            if (seconds < 0 || seconds > 5 || lightFlashCount < 0 || lightFlashCount > 10) {
                throw new IllegalArgumentException();
            }

            byte[] payLoad = new byte[2];
            payLoad[0] = (byte)seconds;
            payLoad[1] = (byte)lightFlashCount;
            return ByteUtils.concatBytes(HONK_FLASH.getIdentifierAndType(), payLoad);
        }

        /**
         * This activates or deactivates the emergency flashers of the car, typically the blinkers
         * to alarm other drivers.
         *
         * @param start wheter to start the emergency flasher or not
         * @return the command bytes
         */
        public static byte[] startEmergencyFlasher(boolean start) {
            return ByteUtils.concatBytes(EMERGENCY_FLASHER.getIdentifierAndType(), booleanByte(start));
        }

        HonkFlash(byte messageType) {
            this.messageType = messageType;
        }
        private byte messageType;
        public byte getType() {
            return messageType;
        }

        @Override
        public Identifier getIdentifier() {
            return Identifier.HONK_FLASH;
        }

        @Override
        public byte[] getIdentifierAndType() {
            return ByteUtils.concatBytes(getIdentifier().getIdentifier(), getType());
        }
    }

    /**
     * Commands for the Remote Control category of the Auto API.
     */
    public enum RemoteControl implements Type {
        GET_CONTROL_MODE((byte)0x00),
        CONTROL_MODE((byte)0x01),
        START_CONTROL_MODE((byte)0x02),
        STOP_CONTROL_MODE((byte)0x03),
        CONTROL_COMMAND((byte)0x04);

        /**
         * Get the current remote control mode. The car will respond with the Control Mode command.
         *
         * @return the command bytes
         */
        public static byte[] getControlMode() {
            return GET_CONTROL_MODE.getIdentifierAndType();
        }

        /**
         * Attempt to start the control mode of the car. The result is sent through the
         * Control Mode command with either Control Started or Control Failed to Start mode.
         *
         * @return the command bytes
         */
        public static byte[] startControlMode() {
            return START_CONTROL_MODE.getIdentifierAndType();
        }

        /**
         * Attempt to stop the control mode of the car. The result is sent through the
         * Control Mode command with Control Ended mode.
         *
         * @return the command bytes
         */
        public static byte[] stopControlMode() {
            return STOP_CONTROL_MODE.getIdentifierAndType();
        }

        /**
         * To be sent every time the controls for the car wants to be changed or once a second if
         * the controls remain the same. If the car does not receive the command every second it
         * will stop the control mode.
         *
         * @param speed Speed in km/h, can range between -5 to 5 whereas a negative speed will
         *              reverse the car.
         * @param angle angle of the car.
         * @return the command bytes
         */
        public static byte[] controlCommand(int speed, int angle) {
            byte msb = (byte) ((angle & 0xFF00) >> 8);
            byte lsb = (byte) (angle & 0xFF);

            return ByteUtils.concatBytes(CONTROL_COMMAND.getIdentifierAndType(), new byte[] {(byte)speed, msb, lsb});
        }

        RemoteControl(byte messageType) {
            this.messageType = messageType;
        }
        private byte messageType;
        public byte getType() {
            return messageType;
        }

        @Override
        public Identifier getIdentifier() {
            return Identifier.REMOTE_CONTROL;
        }

        @Override
        public byte[] getIdentifierAndType() {
            return ByteUtils.concatBytes(getIdentifier().getIdentifier(), getType());
        }
    }

    /**
     * Commands for the Valet Mode category of the Auto API.
     */
    public enum ValetMode implements Type {
        GET_VALET_MODE((byte)0x00),
        VALET_MODE((byte)0x01),
        ACTIVATE_DEACTIVATE_VALET_MODE((byte)0x02);

        /**
         * Get the valet mode, which either activated or not. The car will respond with the Valet
         * Mode message.
         *
         * @return The command bytes
         */
        public static byte[] getValetMode() {
            return GET_VALET_MODE.getIdentifierAndType();
        }

        /**
         * Activate or deactivate the Valet Mode. The result is sent through the evented Valet Mode
         * message with either the mode Deactivated or Activated.
         *
         * @param activate Whether to activate the Valet Mode
         * @return The command bytes
         */
        public static byte[] activateValetMode(boolean activate) {
            return ByteUtils.concatBytes(ACTIVATE_DEACTIVATE_VALET_MODE.getIdentifierAndType(),
                    booleanByte(activate));
        }

        ValetMode(byte messageType) {
            this.messageType = messageType;
        }
        private byte messageType;
        public byte getType() {
            return messageType;
        }

        @Override
        public Identifier getIdentifier() {
            return Identifier.VALET_MODE;
        }

        @Override
        public byte[] getIdentifierAndType() {
            return ByteUtils.concatBytes(getIdentifier().getIdentifier(), getType());
        }
    }

    public enum HeartRate implements Type {
        SEND_HEART_RATE((byte)0x00);

        /**
         * Send the driver heart rate to the car.
         *
         * @param heartRate The heart rate in BPM.
         * @return the command bytes
         */
        public static byte[] sendHeartRate(int heartRate) {
            return ByteUtils.concatBytes(SEND_HEART_RATE.getIdentifierAndType(), (byte)heartRate);
        }

        HeartRate(byte messageType) {
            this.messageType = messageType;
        }
        private byte messageType;
        public byte getType() {
            return messageType;
        }

        @Override
        public Identifier getIdentifier() {
            return Identifier.HEART_RATE;
        }

        @Override
        public byte[] getIdentifierAndType() {
            return ByteUtils.concatBytes(getIdentifier().getIdentifier(), getType());
        }
    }

    /**
     * Commands for the Vehicle Location category of the Auto API.
     */
    public enum VehicleLocation implements Type {
        GET_VEHICLE_LOCATION((byte)0x00),
        VEHICLE_LOCATION((byte)0x01);

        /**
         * Get the vehicle location, which will return the latest recorded coordinates of the car.
         * The car will respond with the Vehicle Location message.
         *
         * @return The command bytes
         */
        public static byte[] getLocation() {
            return GET_VEHICLE_LOCATION.getIdentifierAndType();
        }

        VehicleLocation(byte messageType) {
            this.messageType = messageType;
        }
        private byte messageType;
        public byte getType() {
            return messageType;
        }

        @Override
        public Identifier getIdentifier() {
            return Identifier.VEHICLE_LOCATION;
        }

        @Override
        public byte[] getIdentifierAndType() {
            return ByteUtils.concatBytes(getIdentifier().getIdentifier(), getType());
        }
    }

    /**
     * Commands for the Navi Destination category of the Auto API.
     */
    public enum NaviDestination implements Type {
        SET_DESTINATION((byte)0x00);

        /**
         * Set the navigation destination. This will be forwarded to the navigation system of the car.
         *
         * @param latitude the latitude of the destination
         * @param longitude the longitude of the destination
         * @param name the destination name
         * @return the command bytes
         * @throws UnsupportedEncodingException when the name string is in wrong format
         */
        public static byte[] setDestination(float latitude, float longitude, String name) throws UnsupportedEncodingException {
            byte[] latitudeBytes = ByteBuffer.allocate(4).putFloat(latitude).array();
            byte[] longitudeBytes = ByteBuffer.allocate(4).putFloat(longitude).array();
            byte[] nameBytes = name.getBytes("UTF-8");
            byte[] destinationBytes = new byte[3 + 8 + 1 + nameBytes.length];

            System.arraycopy(SET_DESTINATION.getIdentifierAndType(), 0, destinationBytes, 0, 3);
            System.arraycopy(latitudeBytes, 0, destinationBytes, 3, 4);
            System.arraycopy(longitudeBytes, 0, destinationBytes, 7, 4);
            destinationBytes[11] = (byte)nameBytes.length;
            System.arraycopy(nameBytes, 0, destinationBytes, 12, nameBytes.length);
            return destinationBytes;
        }

        NaviDestination(byte messageType) {
            this.messageType = messageType;
        }
        private byte messageType;
        public byte getType() {
            return messageType;
        }

        @Override
        public Identifier getIdentifier() {
            return Identifier.NAVI_DESTINATION;
        }

        @Override
        public byte[] getIdentifierAndType() {
            return ByteUtils.concatBytes(getIdentifier().getIdentifier(), getType());
        }
    }

    /**
     * Commands for the Delivered Parcels category of the Auto API.
     */
    public enum DeliveredParcels implements Type {
        GET_DELIVERED_PARCELS((byte)0x00),
        DELIVERED_PARCELS((byte)0x01);

        /**
         * Get information about all parcels that have been delivered to the car. The car will
         * respond with the Delivered Parcels command.
         *
         * @return the command bytes
         */
        public static byte[] getDeliveredParcels() {
            return GET_DELIVERED_PARCELS.getIdentifierAndType();
        }

        DeliveredParcels(byte messageType) {
            this.messageType = messageType;
        }
        private byte messageType;
        public byte getType() {
            return messageType;
        }

        @Override
        public Identifier getIdentifier() {
            return Identifier.DELIVERED_PARCELS;
        }

        @Override
        public byte[] getIdentifierAndType() {
            return ByteUtils.concatBytes(getIdentifier().getIdentifier(), getType());
        }
    }

    static byte booleanByte(boolean value) {
        return (byte)(value == true ? 0x01 : 0x00);
    }
}