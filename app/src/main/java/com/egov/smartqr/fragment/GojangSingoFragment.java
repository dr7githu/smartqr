package com.egov.smartqr.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Toast;

import com.egov.smartqr.R;
import com.egov.smartqr.common.Common;
import com.egov.smartqr.util.BitmapUtil;
import com.egov.smartqr.util.KeyboardUtil;
import com.egov.smartqr.util.NaviUtil;
import com.egov.smartqr.util.StrUtil;
import com.egov.smartqr.util.UIUtil;
import com.egov.smartqr.vo.EditSearchInfo;
import com.egov.smartqr.vo.QrVO;
import com.egov.smartqr.vo.SearchVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sds.mobile.servicebrokerLib.ServiceBrokerLib;
import com.sds.mobile.servicebrokerLib.event.ResponseEvent;
import com.sds.mobile.servicebrokerLib.event.ResponseListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by onlytree on 2018. 1. 10..
 */

public class GojangSingoFragment extends Fragment implements View.OnClickListener {

    final int RESULT_LOAD_IMG = 1005;

    @BindView(R.id.frame_sigungu) FrameLayout frame_sigungu;
    @BindView(R.id.textview_sigungu) TextView textview_sigungu;
    @BindView(R.id.edittext_num) EditText edittext_num;
    @BindView(R.id.btn_search) Button btn_search;
    @BindView(R.id.btn_qr) Button btn_qr;
    @BindView(R.id.recycler) RecyclerView recycler;
    @BindView(R.id.linear_no_result) LinearLayout linear_no_result;
    //---고장 지하수 시설 사진 등록---
    @BindView(R.id.textview_1) TextView textview_1;
    @BindView(R.id.textview_2) TextView textview_2;
    @BindView(R.id.textview_3) TextView textview_3;
    @BindView(R.id.textview_4) TextView textview_4;
    @BindView(R.id.imageview_1) ImageView imageview_1;
    @BindView(R.id.imageview_2) ImageView imageview_2;
    @BindView(R.id.imageview_3) ImageView imageview_3;
    @BindView(R.id.imageview_4) ImageView imageview_4;
    //---내역 상세 입력---
    @BindView(R.id.edittext_text) EditText edittext_text;
    //---사후관리---
    @BindView(R.id.edittext_name) EditText edittext_name;
    @BindView(R.id.edittext_email) EditText edittext_email;

    @BindView(R.id.btn_send) Button btn_send;


    ArrayList<SearchVO> arySearch;//검색결과
    GojangSingoFragment.SearchRecyclerAdapter searchRecyclerAdapter = null;

    int loadImageIndex = -1;

    String fileImg01 = "";
    String fileImg02 = "";
    String fileImg03 = "";
    String fileImg04 = "";

    public static GojangSingoFragment newInstance() {
        return new GojangSingoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gojang_singo, container, false);
        ButterKnife.bind(this, view);

        btn_search.setOnClickListener(this);
        btn_qr.setOnClickListener(this);
        textview_1.setOnClickListener(this);
        textview_2.setOnClickListener(this);
        textview_3.setOnClickListener(this);
        textview_4.setOnClickListener(this);
        imageview_1.setOnClickListener(this);
        imageview_2.setOnClickListener(this);
        imageview_3.setOnClickListener(this);
        imageview_4.setOnClickListener(this);
        frame_sigungu.setOnClickListener(this);
        btn_send.setOnClickListener(this);

        imageview_1.setImageResource(R.drawable.no_image);
        imageview_2.setImageResource(R.drawable.no_image);
        imageview_3.setImageResource(R.drawable.no_image);
        imageview_4.setImageResource(R.drawable.no_image);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        searchRecyclerAdapter = new SearchRecyclerAdapter();
        recycler.setAdapter(searchRecyclerAdapter);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == btn_search) {
            String sel_type = "no";
            String search_text = edittext_num.getText().toString();
            String stc = Common.sfteamcodeOfName(textview_sigungu.getText().toString());
            String sgg = "";
            String umd = "";
            String ri = "";
            reqSelectSearchJihasuList(sel_type, search_text, stc, sgg, umd, ri);
        }
        else if(view == btn_qr) {
            NaviUtil.gotoQr(this);
        }
        else if(view == textview_1 || view == imageview_1) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            loadImageIndex = 0;
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        }
        else if(view == textview_2 || view == imageview_2) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            loadImageIndex = 1;
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        }
        else if(view == textview_3 || view == imageview_3) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            loadImageIndex = 2;
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        }
        else if(view == textview_4 || view == imageview_4) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            loadImageIndex = 3;
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        }
        else if(view == btn_send) {
            SearchVO search = null;
            if(arySearch != null && arySearch.size() > 0) {
                for (int i = 0; i < arySearch.size(); i++) {
                    if(arySearch.get(i).is_selected) {
                        search = arySearch.get(i);
                        break;
                    }
                }
            }
            if(search != null) {
                TelephonyManager tm = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
                String pno = tm.getLine1Number();
                pno = pno.replace("+82", "0");

                reqInsertReport(search.sf_team_code, search.perm_nt_no, pno);
            } else {
                UIUtil.alert(getContext(), "선택된 관정이 없습니다.");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMG) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    if(loadImageIndex == 0) {
                        imageview_1.setImageBitmap(selectedImage);
                        fileImg01 = "data:image/jpg;base64," + BitmapUtil.bitmap2String(getContext(), selectedImage);
                    } else if(loadImageIndex == 1) {
                        imageview_2.setImageBitmap(selectedImage);
                        fileImg02 = "data:image/jpg;base64," + BitmapUtil.bitmap2String(getContext(), selectedImage);
                    } else if(loadImageIndex == 2) {
                        imageview_3.setImageBitmap(selectedImage);
                        fileImg03 = "data:image/jpg;base64," + BitmapUtil.bitmap2String(getContext(), selectedImage);
                    } else if(loadImageIndex == 3) {
                        imageview_4.setImageBitmap(selectedImage);
                        fileImg04 = "data:image/jpg;base64," + BitmapUtil.bitmap2String(getContext(), selectedImage);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(getContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
            }
        }
        else {
            // QR코드/ 바코드를 스캔한 결과
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            String text = result.getContents();
            int find = text.indexOf("gid=");
            String gid = text.substring(find + 4, text.length());
            reqGetPermAndSf(gid);
        }
    }

    //고장신고
    void reqInsertReport(String sf_team_code, String perm_nt_no, String pno) {
        UIUtil.showProgress(getContext());
        ServiceBrokerLib lib = new ServiceBrokerLib(getContext(),
                new ResponseListener() {
                    @Override
                    public void receive(ResponseEvent re) {
                        UIUtil.hideProgress();
                        KeyboardUtil.hide(getContext(), edittext_num);

                        if(re.getResultCode() == 0) {
                            UIUtil.alert(getContext(), "고장신고를 완료하였습니다.");
                            clearData();
                        } else {
                            UIUtil.alert(getContext(), re.getResultData());
                        }
                    }
                });

        String p = "method=insertReport";
        p += ";sf_team_code=" + sf_team_code;
        p += ";input_search_text=" + perm_nt_no;
        p += ";nameText=" + StrUtil.utf8(edittext_name.getText().toString());
        p += ";pnoText=" + pno;
        p += ";emailText=" + StrUtil.utf8(edittext_email.getText().toString());
        p += ";reportText=" + StrUtil.utf8(edittext_text.getText().toString());
        //if(!StrUtil.isEmpty(fileImg01))
            p += ";fileImg01=" + fileImg01;
        //if(!StrUtil.isEmpty(fileImg02))
            p += ";fileImg02=" + fileImg02;
        //if(!StrUtil.isEmpty(fileImg03))
            p += ";fileImg03=" + fileImg03;
        //if(!StrUtil.isEmpty(fileImg04))
            p += ";fileImg04=" + fileImg04;

        Intent intent = new Intent();
        intent.putExtra("sCode", Common.sCode);
        intent.putExtra("parameter", p);
        lib.request(intent);
    }

    //시설물조회 - 검색
    void reqSelectSearchJihasuList(final String sel_type, final String search_text, final String stc, final String sgg, final String umd, final String ri) {
        // 중계서버 연계 API 객체 생성
        UIUtil.showProgress(getContext());
        ServiceBrokerLib lib = new ServiceBrokerLib(getContext(),
                new ResponseListener() {
                    @Override
                    public void receive(ResponseEvent re) {
                        UIUtil.hideProgress();
                        KeyboardUtil.hide(getContext(), edittext_num);

                        if(re.getResultCode() == 0) {
                            arySearch = null;
                            try {
                                Type listType = new TypeToken<ArrayList<SearchVO>>() {}.getType();
                                arySearch = new Gson().fromJson(re.getResultData(), listType);
                            } catch (Exception e) {
                            }

                            //TODO : 기존 하이브리드 앱과 순서가 다른것 같은데.. 필요하면 소팅 넣으면 됨!

                            if(arySearch == null || arySearch.size() <= 0) {
                                recycler.setVisibility(View.GONE);
                                linear_no_result.setVisibility(View.VISIBLE);
                            } else {
                                recycler.setVisibility(View.VISIBLE);
                                linear_no_result.setVisibility(View.GONE);

                                SearchVO search = arySearch.get(0);
                                search.is_selected = true;

                                searchRecyclerAdapter.notifyDataSetChanged();
                                recycler.setScrollY(0);
                            }
                        } else {
                            UIUtil.alert(getContext(), "통신오류가 발생하였습니다.", re.getResultData() + "(" + re.getResultCode() + ")");
                        }
                    }
                });

        String p = "method=selectSearchJihasuList";
        p += ";sel_type=" + sel_type;
        p += ";search_text=" + search_text;
        p += ";stc=" + stc;
        p += ";sgg=" + sgg;
        p += ";umd=" + umd;
        p += ";ri=" + ri;

        Intent intent = new Intent();
        intent.putExtra("sCode", Common.sCode);
        intent.putExtra("parameter", p);
        lib.request(intent);
    }

    //QR검색
    void reqGetPermAndSf(final String gid) {
        UIUtil.showProgress(getContext());
        ServiceBrokerLib lib = new ServiceBrokerLib(getContext(),
                new ResponseListener() {
                    @Override
                    public void receive(ResponseEvent re) {
                        //UIUtil.hideProgress();
                        if(re.getResultCode() == 0) {
                            //{"sf_team_code":"5580000","perm_nt_no":"2201500013"}
                            QrVO qr = null;
                            try {
                                qr = new Gson().fromJson(re.getResultData(), QrVO.class);
                            } catch (Exception e) {
                            }

                            textview_sigungu.setText(Common.nameOfSfteamcode(qr.sf_team_code));
                            edittext_num.setText(qr.perm_nt_no);
                            onClick(btn_search);
                        } else {
                            UIUtil.alert(getContext(), "통신오류가 발생하였습니다.", re.getResultData() + "(" + re.getResultCode() + ")");
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

    void clearData() {
        imageview_1.setImageResource(R.drawable.no_image);
        imageview_2.setImageResource(R.drawable.no_image);
        imageview_3.setImageResource(R.drawable.no_image);
        imageview_4.setImageResource(R.drawable.no_image);

        edittext_text.setText("");
        edittext_name.setText("");
        edittext_email.setText("");
    }

    View.OnClickListener onClickList = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SearchVO search = (SearchVO) view.getTag();

            //선택처리
            for(int i=0; i<arySearch.size(); i++) {
                if(arySearch.get(i).is_selected) {
                    arySearch.get(i).is_selected = false;
                    searchRecyclerAdapter.notifyItemChanged(i);
                }
            }
            search.is_selected = true;
            searchRecyclerAdapter.notifyItemChanged(arySearch.indexOf(search));


            String stc = "", pnn = "";
            if(search != null) {
                stc = search.sf_team_code;
                pnn = search.perm_nt_no;
            }
            //reqSelectJihasuDetailResult(stc, pnn);
        }
    };


    public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {

        public SearchRecyclerAdapter(){
        }

        @Override
        public SearchRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_edit_search_info, viewGroup,false);
            return new SearchRecyclerAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SearchRecyclerAdapter.ViewHolder viewHolder, int position) {
            SearchVO search = arySearch.get(position);
            viewHolder.imagebtn_click.setTag(search);
            viewHolder.imagebtn_click.setOnClickListener(onClickList);

            viewHolder.linear_root.setSelected(search.is_selected);

            String addr = "";
            if(StrUtil.notEmpty(search.sido)){
                addr += search.sido + " ";
            }
            if(StrUtil.notEmpty(search.sgg)){
                addr += search.sgg + " ";
            }
            if(StrUtil.notEmpty(search.umd)){
                addr += search.umd + " ";
            }
            if(StrUtil.notEmpty(search.ri)){
                addr += search.ri + " ";
            }
            if(StrUtil.notEmpty(search.jibun)){
                addr += search.jibun + " ";
            }

            viewHolder.textview_num.setText(search.perm_nt_no);
            viewHolder.textview_addr.setText(addr);
        }

        @Override
        public int getItemCount() {
            if(arySearch == null) return 0;
            else return arySearch.size();
        }

        /**
         * 뷰 재활용을 위한 viewHolder
         */
        public class ViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.linear_root) LinearLayout linear_root;
            @BindView(R.id.imagebtn_click) ImageButton imagebtn_click;
            @BindView(R.id.textview_num) TextView textview_num;
            @BindView(R.id.textview_addr) TextView textview_addr;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
