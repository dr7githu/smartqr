package com.egov.smartqr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egov.smartqr.fragment.DetailEditFragment;
import com.egov.smartqr.fragment.DetailInfoFragment;
import com.egov.smartqr.fragment.GojangSingoFragment;
import com.egov.smartqr.fragment.MapSearchFragment;
import com.egov.smartqr.util.UIUtil;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuItem;
import com.shehabic.droppy.DroppyMenuPopup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZxingActivity extends CaptureActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ZxingActivity.this.onKeyDown(KeyEvent.KEYCODE_VOLUME_UP, null);
            }
        }, 100);
    }
}
