package com.high_mobility.digitalkey.broadcast;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.high_mobility.digitalkey.R;
import com.highmobility.common.ILinkView;
import com.highmobility.common.ILinkViewController;
import com.highmobility.common.LinkViewController;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LinkView extends AppCompatActivity implements ILinkView {
    static final String TAG = "LinkView";

    private ILinkViewController controller;
    @BindView (R.id.lock_button) Button lockButton;
    @BindView (R.id.progress_bar) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.link_view);
        ButterKnife.bind(this);
        controller = new LinkViewController(this);

        lockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onLockDoorsClicked();
            }
        });
    }

    @Override
    public void showLoadingView(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            lockButton.setVisibility(View.GONE);
        }
        else {
            progressBar.setVisibility(View.GONE);
            lockButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDoorsLocked(boolean locked) {
        if (locked) {
            lockButton.setText("unlock");
        }
        else {
            lockButton.setText("lcok");
        }
    }

    @Override
    public void onTrunkLocked(boolean locked) {

    }

    @Override
    public void enableTrunkButton(boolean enable) {

    }

    @Override
    public void enableLockButton(boolean enable) {
        lockButton.setEnabled(enable);
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
