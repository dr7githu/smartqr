package com.egov.smartqr;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.egov.smartqr.common.Common;
import com.egov.smartqr.fragment.DetailEditFragment;
import com.egov.smartqr.fragment.DetailInfoFragment;
import com.egov.smartqr.fragment.GojangSingoFragment;
import com.egov.smartqr.fragment.MapSearchFragment;
import com.egov.smartqr.fragment.SetupFragment;
import com.egov.smartqr.util.NaviUtil;
import com.egov.smartqr.util.UIUtil;
import com.egov.smartqr.vo.QrVO;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sds.mobile.servicebrokerLib.ServiceBrokerLib;
import com.sds.mobile.servicebrokerLib.event.ResponseEvent;
import com.sds.mobile.servicebrokerLib.event.ResponseListener;
import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuItem;
import com.shehabic.droppy.DroppyMenuPopup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.frame_menu) FrameLayout frame_menu;

    Fragment curFragment = null;
    MapSearchFragment mapSearchFragment = null;
    DetailInfoFragment detailInfoFragment = null;
    DetailEditFragment detailEditFragment = null;
    GojangSingoFragment gojangSingoFragment = null;
    SetupFragment setupFragment = null;

    boolean isQr = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
            startActivity(intent);

            finish();
            System.exit(0);
            return;
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        frame_menu.setOnClickListener(this);

        screenMove(MapSearchFragment.class);
    }

    @Override
    public void onClick(View view) {
        if(view == frame_menu) {
            DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(this, frame_menu);

            // Add normal items (text only)
            droppyBuilder
                    .addMenuItem(new DroppyMenuItem(" 시설물 조회"))
                    .addSeparator()
                    .addMenuItem(new DroppyMenuItem(" QR코드 조회"))
                    .addSeparator()
                    .addMenuItem(new DroppyMenuItem(" 시설 점검"))
                    .addSeparator()
                    .addMenuItem(new DroppyMenuItem(" 고장 신고"))
                    .addSeparator()
                    .addMenuItem(new DroppyMenuItem(" 환경설정"))
                    .addSeparator()
                    .addMenuItem(new DroppyMenuItem(" 로그아웃"))
                    .addSeparator();

            // Add Item with icon
            //droppyBuilder.addMenuItem(new DroppyMenuItem("test3", R.drawable.ic_launcher));


            // Set Callback handler
            droppyBuilder.setOnClick(new DroppyClickCallbackInterface() {
                @Override
                public void call(View v, int id) {
                    if(id == 0) {
                        screenMove(MapSearchFragment.class);
                    } else if(id == 1) {
                        isQr = true;
                        NaviUtil.gotoQr(MainActivity.this);
                    } else if(id == 2) {
                        screenMove(DetailEditFragment.class);
                    } else if(id == 3) {
                        screenMove(GojangSingoFragment.class);
                    } else if(id == 4) {
                        screenMove(SetupFragment.class);
                    } else if(id == 5) {
                        NaviUtil.gotoLogin(MainActivity.this);
                        finish();
                    }
                }
            });

            DroppyMenuPopup droppyMenu = droppyBuilder.build();
            droppyMenu.show();
        }
    }

    @Override
    public void onBackPressed() {
        if(curFragment != null && !curFragment.getClass().equals(MapSearchFragment.class)) {
            boolean mainBack = true;
            if(curFragment.getClass().equals(DetailEditFragment.class)) {
                DetailEditFragment detailEditFragment = (DetailEditFragment)curFragment;
                if(detailEditFragment.isNewMode) {
                    detailEditFragment.setNewMode(false);
                    mainBack = false;
                }
            }

            if(mainBack) {
                screenMove(MapSearchFragment.class);
            }
        } else {
            UIUtil.alert(this, "알림", "프로그램을 종료하시겠습니까?", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            }, null);
        }
    }

    public void screenMove(Class clazz, String... args) {
        if(clazz.equals(MapSearchFragment.class)) {
            if(mapSearchFragment == null) mapSearchFragment = MapSearchFragment.newInstance();
            replaceFragment(mapSearchFragment);
        } else if(clazz.equals(DetailInfoFragment.class)) {
            if(detailInfoFragment != null) {
                removeFragment(detailInfoFragment);
                detailInfoFragment = null;
            }
            detailInfoFragment = DetailInfoFragment.newInstance();

            Bundle bundle = new Bundle();
            bundle.putString("stc", args[0]);
            bundle.putString("pnn", args[1]);
            detailInfoFragment.setArguments(bundle);

            //replaceFragment(detailInfoFragment);
            addFragment(detailInfoFragment, "detailInfoFragment");
        } else if(clazz.equals(DetailEditFragment.class)) {
            if(detailEditFragment != null) {
                removeFragment(detailEditFragment);
                detailEditFragment = null;
            }
            detailEditFragment = DetailEditFragment.newInstance();

            if(args.length >= 2) {
                Bundle bundle = new Bundle();
                bundle.putString("sf_team_code", args[0]);
                bundle.putString("perm_nt_no", args[1]);
                detailEditFragment.setArguments(bundle);
            }

            //replaceFragment(detailEditFragment);
            addFragment(detailEditFragment, "detailEditFragment");
        } else if(clazz.equals(GojangSingoFragment.class)) {
            if(gojangSingoFragment != null) {
                removeFragment(gojangSingoFragment);
                gojangSingoFragment = null;
            }
            gojangSingoFragment = GojangSingoFragment.newInstance();
            //replaceFragment(gojangSingoFragment);
            addFragment(gojangSingoFragment, "gojangSingoFragment");
        } else if(clazz.equals(SetupFragment.class)) {
            if(setupFragment != null) {
                removeFragment(setupFragment);
                setupFragment = null;
            }
            setupFragment = SetupFragment.newInstance();
            //replaceFragment(setupFragment);
            addFragment(setupFragment, "setupFragment");
        }
    }

    void replaceFragment(Fragment fragment) {
        curFragment = fragment;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }

    void addFragment(Fragment fragment, String tag) {
        curFragment = fragment;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment, tag).commit();
    }

    void removeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //QR코드
        if(requestCode == 49374) {
            if (resultCode == RESULT_OK) {
                // QR코드/ 바코드를 스캔한 결과
                if (isQr) {//메인메뉴 실행
                    IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                    String text = result.getContents();
                    int find = text.indexOf("gid=");
                    String gid = text.substring(find + 4, text.length());
                    reqGetPermAndSf(gid);
                } else {//각 프래그먼트 실행
                    curFragment.onActivityResult(requestCode, resultCode, data);
                }
            } else {
                UIUtil.alert(this, "관정에 대한 QR코드를 인식해주세요.");
            }
            isQr = false;
        }
    }

    //QR검색
    void reqGetPermAndSf(final String gid) {
        UIUtil.showProgress(this);
        ServiceBrokerLib lib = new ServiceBrokerLib(this,
                new ResponseListener() {
                    @Override
                    public void receive(ResponseEvent re) {
                        UIUtil.hideProgress();
                        if(re.getResultCode() == 0) {
                            //{"sf_team_code":"5580000","perm_nt_no":"2201500013"}
                            QrVO qr = null;
                            try {
                                qr = new Gson().fromJson(re.getResultData(), QrVO.class);
                            } catch (Exception e) {
                            }

                            screenMove(DetailInfoFragment.class, qr.sf_team_code, qr.perm_nt_no);
                        } else {
                            UIUtil.alert(MainActivity.this, "통신오류가 발생하였습니다.", re.getResultData() + "(" + re.getResultCode() + ")");
                        }
                    }
                });

        String p = "method=getPermAndSf";
        p += ";gid=" + gid;

        Intent intent = new Intent();
        intent.putExtra("sCode", Common.sCode);
        intent.putExtra("parameter", p);
        lib.request(intent);
    }
}
