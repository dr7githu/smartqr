package com.egov.smartqr.fragment;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egov.smartqr.R;
import com.egov.smartqr.common.Common;
import com.egov.smartqr.util.KeyboardUtil;
import com.egov.smartqr.util.MyLocation;
import com.egov.smartqr.util.NaviUtil;
import com.egov.smartqr.util.StrUtil;
import com.egov.smartqr.util.UIUtil;
import com.egov.smartqr.vo.QrVO;
import com.egov.smartqr.vo.SearchVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sds.mobile.servicebrokerLib.ServiceBrokerLib;
import com.sds.mobile.servicebrokerLib.event.ResponseEvent;
import com.sds.mobile.servicebrokerLib.event.ResponseListener;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by onlytree on 2018. 1. 10..
 */

public class SetupFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.btn_network_test) Button btn_network_test;
    @BindView(R.id.btn_gps_test) Button btn_gps_test;
    @BindView(R.id.btn_camera_test) Button btn_camera_test;
    @BindView(R.id.textview_network) TextView textview_network;
    @BindView(R.id.textview_gps) TextView textview_gps;
    @BindView(R.id.textview_camera) TextView textview_camera;

    public static SetupFragment newInstance() {
        return new SetupFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup, container, false);
        ButterKnife.bind(this, view);

        btn_network_test.setOnClickListener(this);
        btn_gps_test.setOnClickListener(this);
        btn_camera_test.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == btn_network_test) {
            textview_network.setText("통신 상태 : " + getNetworkStatus(getContext()));
        }
        else if(view == btn_gps_test) {
            MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
                @Override
                public void gotLocation(final Location location){
                    UIUtil.hideProgress();
                    //Got the location!
                    try {
                        //Toast.makeText(ReservationsSearchAroundMeActivity.this, String.format("%f,  %f", location.getLongitude(), location.getLatitude()), Toast.LENGTH_SHORT).show();
                        textview_gps.setText(String.format("GPS 상태 : 정상(경도 : %.7f, 위도 : %.7f)", location.getLongitude(), location.getLatitude()));
                    }
                    catch(Exception x){
                        x.getMessage();
                    }
                }
            };
            if(!new MyLocation().getLocation(getContext(), locationResult)) {
                textview_gps.setText("GPS 상태 : 비정상");
            } else {
                UIUtil.showProgress(getContext());
            }
        }
        else if(view == btn_camera_test) {
            try {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);

                textview_camera.setText("카메라 상태 : 정상");
            } catch (Exception e) {
                textview_camera.setText("카메라 상태 : 비정상");
            }
        }
    }

    String getNetworkStatus(Context context) {
//        states[Connection.UNKNOWN]  = 'Unknown';
//        states[Connection.ETHERNET] = 'Ethernet';
//        states[Connection.WIFI]     = 'WiFi';
//        states[Connection.CELL_2G]  = '2G';
//        states[Connection.CELL_3G]  = '3G';
//        states[Connection.CELL_4G]  = '4G';
//        states[Connection.NONE]     = 'disconnection';

        int videoRate = 2048;

        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo lte_4g = manager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);

        boolean bmobile = false, bwifi = false, blte4g = false;
        if(wifi != null && wifi.isConnected()) bwifi = true;
        if(mobile != null && mobile.isConnected()) bmobile = true;
        if(lte_4g != null && lte_4g.isConnected()) blte4g = true;

//        Dlog.d("====== Wifi: " + (bwifi ? "O":"X") + ", LTE: " + (blte4g ? "O":"X") + ", 3G: " + (bmobile ? "O":"X"));

//        if(bwifi || blte4g) videoRate = 2048;
//        else videoRate = 768;

        String strNetType = "--";
        if(bwifi) {
            videoRate = 2048;
            strNetType = "WiFi";
        }
        else {
            TelephonyManager mTelephonyManager = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
            int networkType = mTelephonyManager.getNetworkType();

            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    //case TelephonyManager.NETWORK_TYPE_GSM:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    videoRate = 768;
                    strNetType = "2G";
                    break;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    //case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                    videoRate = 768;
                    strNetType = "3G";
                    break;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    //case TelephonyManager.NETWORK_TYPE_IWLAN:
                    videoRate = 2048;
                    strNetType = "4G";
                    break;
                default:
                    videoRate = 2048;
                    strNetType = "Unknown";
            }
        }

        String rtn = "";
        if(bwifi || bmobile || blte4g) {
            rtn = "정상(" + strNetType + ")";
        } else {
            rtn = "비정상";
        }
        return rtn;
    }
}
