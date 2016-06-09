package com.high_mobility.digitalkey.broadcast;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.high_mobility.HMLink.AccessCertificate;
import com.high_mobility.HMLink.Commands.AccessResponse;
import com.high_mobility.HMLink.Commands.AutoCommand;
import com.high_mobility.HMLink.Broadcasting.ByteUtils;
import com.high_mobility.HMLink.Broadcasting.Link;
import com.high_mobility.HMLink.Broadcasting.LinkListener;
import com.high_mobility.HMLink.Broadcasting.LocalDevice;
import com.high_mobility.HMLink.Broadcasting.LocalDeviceListener;
import com.high_mobility.HMLink.Commands.AutoCommandNotification;
import com.high_mobility.HMLink.Commands.LockStatusChangedNotification;
import com.high_mobility.HMLink.Commands.GetVehicleStatusResponse;
import com.high_mobility.HMLink.Commands.CommandParseException;
import com.high_mobility.HMLink.Constants;
import com.high_mobility.HMLink.DeviceCertificate;
import com.high_mobility.HMLink.LinkException;
import com.high_mobility.digitalkey.R;

/**
 * Created by ttiganik on 02/06/16.
 */
public class BroadcastActivity extends AppCompatActivity implements LocalDeviceListener, LinkListener {
    static final String TAG = "BroadcastActivity";
    static final byte[] CA_PUBLIC_KEY = ByteUtils.bytesFromHex("***REMOVED***");

    TextView serialTextView;
    TextView publicKeyTextView;

    TextView statusTextView;
    Switch broadcastSwitch;

    LinearLayout pairingView;
    Button confirmPairButton;

    ViewPager pager;
    LinkPagerAdapter adapter;

    LocalDevice device;
    Constants.ApprovedCallback pairApproveCallback;

    void onBroadcastCheckedChanged() {
        if (broadcastSwitch.isChecked()) {
            try {
                device.startBroadcasting();
            } catch (LinkException e) {
                e.printStackTrace();
            }
        }
        else {
            device.stopBroadcasting();
        }
    }

    void onPairConfirmClick() {
        pairingView.setVisibility(View.GONE);
        pairApproveCallback.approve();
        pairApproveCallback = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broadcast_view);

        createViews();
        initializeDevice();
        onStateChanged(device.getState(), device.getState()); // set the initial broadcasting status text
        publicKeyTextView.setText(ByteUtils.hexFromBytes(device.getCertificate().getPublicKey()));
        serialTextView.setText(ByteUtils.hexFromBytes(device.getCertificate().getSerial()));

        broadcastSwitch.setChecked(true);
    }

    @Override
    protected void onDestroy() {
        device.stopBroadcasting();
        super.onDestroy();
    }

    // LocalDeviceListener

    @Override
    public void onStateChanged(LocalDevice.State state, LocalDevice.State state1) {
        broadcastSwitch.setEnabled(state != LocalDevice.State.BLUETOOTH_UNAVAILABLE);
        setTitle(device.getName());

        switch (state) {
            case IDLE:
                statusTextView.setText("idle");
                break;
            case BLUETOOTH_UNAVAILABLE:
                statusTextView.setText("N/A");
                break;

            case BROADCASTING:
                statusTextView.setText("broadcasting");
                break;
        }
    }

    @Override
    public void onLinkReceived(Link link) {
        link.setListener(this);
        adapter.setLinks(device.getLinks());
    }

    @Override
    public void onLinkLost(Link link) {
        link.setListener(null);
        adapter.setLinks(device.getLinks());
    }

    // LinkListener

    @Override
    public void onStateChanged(Link link, Link.State state) {
        adapter.setLinks(device.getLinks());
    }

    @Override
    public byte[] onCommandReceived(Link link, byte[] bytes) {
        try {
            AutoCommandNotification notification = AutoCommandNotification.create(bytes);

            if (notification.getType() == AutoCommand.Type.LOCK_STATUS_CHANGED) {
                LockStatusChangedNotification changedNotification = (LockStatusChangedNotification)notification;
                Log.i(TAG, "LockStatusChanged " + changedNotification.getLockStatus());
            }
        }
        catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
        }


        return null;
    }

    @Override
    public void onPairingRequested(Link link, Constants.ApprovedCallback approvedCallback) {
        this.pairApproveCallback = approvedCallback;
        pairingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPairingRequestTimeout(Link link) {
        pairingView.setVisibility(View.GONE);
    }

    void initializeDevice() {
        device = LocalDevice.getInstance(getApplicationContext());
        // Delete the previous certificates from the device.
        // This is not needed in real scenario where certificates are not faked.
        device.reset();

        // Demo key-pair, in a real scenario these are generated by the Crypto helper class and stored securely on device
        // Reference: http://dc-9141.high-mobility.com/android-tutorial/#creating-key-pair
        final byte[] DEVICE_PUBLIC_KEY = ByteUtils.bytesFromHex("***REMOVED***");
        final byte[] DEVICE_PRIVATE_KEY = ByteUtils.bytesFromHex("***REMOVED***");

        // Create a demo certificate. In real life situation the certificate should be queried from the server
        // Reference: http://dc-9141.high-mobility.com/android-tutorial/#setting-device-certificate
        // Reference: http://dc-9141.high-mobility.com/android-reference-device-certificate/#convenience-init
        final byte[] APP_IDENTIFIER = ByteUtils.bytesFromHex("***REMOVED***");
        final byte[] ISSUER = ByteUtils.bytesFromHex("48494D4F");
        final byte[] DEVICE_SERIAL = ByteUtils.bytesFromHex("01231910D62CA571EF");

        DeviceCertificate cert = new DeviceCertificate(ISSUER, APP_IDENTIFIER, DEVICE_SERIAL, DEVICE_PUBLIC_KEY);
        cert.setSignature(ByteUtils.bytesFromHex("***REMOVED***")); // original

        // set the device certificate.
        device.setDeviceCertificate(cert, DEVICE_PRIVATE_KEY, CA_PUBLIC_KEY);
        // set the device listener
        device.setListener(this);

        // create the AccessCertificates for the car to read(stored certificate)
        // and register ourselves with the car already(registeredCertificate)
        AccessCertificate registeredCertificate = CertUtils.demoRegisteredCertificate(DEVICE_SERIAL);
        try {
            device.registerCertificate(registeredCertificate);
        } catch (LinkException e) {
            e.printStackTrace();
        }

        AccessCertificate storedCertificate = CertUtils.demoStoredCertificate(DEVICE_SERIAL, DEVICE_PUBLIC_KEY);
        try {
            device.storeCertificate(storedCertificate);
        } catch (LinkException e) {
            e.printStackTrace();
        }
    }

    void createViews() {
        serialTextView = (TextView) findViewById(R.id.serial_textview);
        publicKeyTextView = (TextView) findViewById(R.id.public_key_textview);

        statusTextView = (TextView) findViewById(R.id.status_textview);
        broadcastSwitch = (Switch) findViewById(R.id.broadcast_switch);
        broadcastSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onBroadcastCheckedChanged();
            }
        });

        pairingView = (LinearLayout) findViewById(R.id.pairing_view);
        confirmPairButton = (Button) findViewById(R.id.confirm_pairing_button);
        confirmPairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPairConfirmClick();
            }
        });

        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new LinkPagerAdapter(this, getSupportFragmentManager());
        pager.setAdapter(adapter);
    }

    void onLockClicked(Link link) {
        final LinkFragment fragment = adapter.getFragment(link);

        ViewUtils.enableView(fragment.authView, false);
        link.sendCommand(AutoCommand.lockDoorsBytes(), true, new Constants.DataResponseCallback() {
            @Override
            public void response(byte[] bytes, LinkException exception) {
                ViewUtils.enableView(fragment.authView, true);
                try {
                    AccessResponse response = new AccessResponse(bytes);

                    if (response.getErrorCode() == 0) {
                        Log.i(TAG, "successfully locked the vehicle");
                    }
                    else {
                        Log.i(TAG, "failed to lock the vehicle");
                    }
                } catch (CommandParseException e) {
                    Log.e(TAG, "CommandParseException ", e);
                }

            }
        });
    }

    void onUnlockClicked(Link link) {
        final LinkFragment fragment = adapter.getFragment(link);

        ViewUtils.enableView(fragment.authView, false);
        link.sendCommand(AutoCommand.unlockDoorsBytes(), true, new Constants.DataResponseCallback() {
            @Override
            public void response(byte[] bytes, LinkException exception) {
                ViewUtils.enableView(fragment.authView, true);
                try {
                    AccessResponse response = new AccessResponse(bytes);

                    if (response.getErrorCode() == 0) {
                        Log.i(TAG, "successfully unlocked the vehicle");
                    }
                    else {
                        Log.i(TAG, "failed to unlock the vehicle");
                    }
                } catch (CommandParseException e) {
                    Log.e(TAG, "CommandParseException ", e);
                }
            }
        });
    }}
