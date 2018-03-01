package com.example.duangniu000.test2.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.duangniu000.test2.R;
import com.example.duangniu000.test2.Request.Url;
import com.example.duangniu000.test2.Util.ToastUtil;
import com.example.duangniu000.test2.data.Guessing;
import com.example.duangniu000.test2.data.GuessingResponse;
import com.example.duangniu000.test2.retrofit.Get_interface;
import com.example.duangniu000.test2.retrofit.RequestField;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GuessingFragment extends BaseFragment {

    @BindView(R.id.action)
    Button after;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.edit)
    EditText edit;
    Unbinder unbinder;

    private List<Guessing> list;
    private int index;
    private String answer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guessing_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        netWork();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void commit() {

        String toString = edit.getText().toString();
        if (answer != null) {
            if (answer.equals("谜底：" + toString)) {
                ToastUtil.showToast(getContext(), "正确");
                updateUI();
            } else {
                ToastUtil.showToast(getContext(), "错误");
            }
        }
    }

    private void updateUI() {
        if (index >= list.size()) {
            netWork();
        } else {
            Guessing ben = list.get(index);
            content.setText(ben.getTitle());
            answer = ben.getAnswer();
            edit.setText("");
            index++;
        }
    }

    private void netWork() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = dateFormat.format(new Date());
//        Parms parms = Parms.getInstance();
//        parms.add("showapi_appid", "57487");
//        parms.add("showapi_sign", "f4cec95e4bb34f249627d873bdd28537");
//        parms.add("showapi_timestamp", format);
//        parms.add("page", "1");
//        parms.add("typeId", Guessing.type1);
//        RequestClient.Build().url(Url.caiyicai).from().parms(parms).newCall(new ReCallBack() {
//            @Override
//            public void Response(Call call, String response) {
//                GuessingResponse fromJson = new Gson().fromJson(response, GuessingResponse.class);
//                List contentlist = fromJson.getShowapi_res_body().getPb().getContentlist();
//                list.addAll(contentlist);
//                updateUI();
//            }
//
//            @Override
//            public void Failure(Call call, IOException e) {
//
//            }
//        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.m1n2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestField parms = new RequestField();
        parms.add("showapi_appid", "57487");
        parms.add("showapi_sign", "f4cec95e4bb34f249627d873bdd28537");
        parms.add("showapi_timestamp", format);
        parms.add("page", "1");
        parms.add("typeId", Guessing.type1);

        retrofit.create(Get_interface.class).getCall4(parms.getField()).enqueue(new Callback<GuessingResponse>() {
            @Override
            public void onResponse(retrofit2.Call<GuessingResponse> call, Response<GuessingResponse> response) {
                List<Guessing> contentlist = response.body().getShowapi_res_body().getPb().getContentlist();
                list.addAll(contentlist);
                updateUI();
            }

            @Override
            public void onFailure(retrofit2.Call<GuessingResponse> call, Throwable t) {

            }
        });

    }

    @OnClick({R.id.action, R.id.answer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action:
                commit();
                break;
            case R.id.answer:
                ToastUtil.showToast(getContext(), answer);
                break;
        }
    }
}
