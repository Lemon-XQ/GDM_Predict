package com.lemonxq.gdmpredict;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * @author: Lemon-XQ
 * @date: 2017/11/5
 */

public class AnalyzeFragment extends Fragment {

    private ProgressBar progressBar;
    private Button analyseBtn;
    private Button ageBtn;
    private Button heightBtn;
    private Button weightBtn;
    private Button ogttBtn;
    private EditText ogttText;
    private TextView timeText;
    private View view;
    private int age;
    private float height;
    private float weight;
    private int weight_Int;
    private float weight_Float;
    private float ogtt;
    private int ogtt_Int;
    private float ogtt_Float;

    private Handler handler;

    // 存储选项列表
    private ArrayList<String> ageList = new ArrayList<String>();
    private ArrayList<String> heightList = new ArrayList<String>();
    private ArrayList<String> weightList = new ArrayList<String>();
    private ArrayList<String> ogttList = new ArrayList<String>();
    private ArrayList<String> zeroToNineList = new ArrayList<String>();

    private int green;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity activity = (MainActivity) getActivity();
        handler = activity.handler;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_analyze_cn, container, false);
        InitComponent();
        SetListeners();
        updateTime();
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateTime();
        age = 0;
        weight = 0;
        height = 0;
        ogtt = 0;
    }

    private void InitComponent() {
        analyseBtn = view.findViewById(R.id.btn_analyse);
        ageBtn = view.findViewById(R.id.ageBtn);
        weightBtn = view.findViewById(R.id.weightBtn);
        heightBtn = view.findViewById(R.id.heightBtn);
        ogttBtn = view.findViewById(R.id.ogttBtn);
        timeText = view.findViewById(R.id.currentTime);

        // 填充列表
        ageList.clear();
        heightList.clear();
        weightList.clear();
        ogttList.clear();
        zeroToNineList.clear();
        for (int i = 18; i <= 50; i++){
            ageList.add(i+""); // age: 18-50
        }
        for (int i = 150; i <= 180; i++){
            heightList.add(i+""); // height: 150-180
        }
        for (int i = 30; i <= 150; i++){
            weightList.add(i+""); // weight: 30.0-150.0
        }
        for (int i = 0; i <= 9; i++){
            zeroToNineList.add(i+"");
        }
        for (int i = 0; i <= 30; i++){
            ogttList.add(i+""); // ogtt: 0.0-30.0
        }

        // Init Color
        green = getActivity().getApplicationContext().getResources().getColor(R.color.green);
    }

    private void SetListeners() {
        // 设置分析按钮监听
        analyseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 发送指令给Activity
                Log.d("Age", age+"");
                Log.d("Height", height+"");
                Log.d("Weight", weight+"");
                Log.d("OGTT", ogtt+"");

                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putInt("age", age);
                bundle.putFloat("height", height);
                bundle.putFloat("weight", weight);
                bundle.putFloat("OGTT", ogtt);
                msg.what = 1;
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });

        // 输入框文本内容监听
//        ageText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                try {
//                    age = Integer.parseInt(ageText.getText().toString());
//                } catch (NumberFormatException e) {
//                    Toast.makeText(getActivity(), Consts.AGE_INVALID, Toast.LENGTH_SHORT).show();
//                    age = 0;
//                }
//
//            }
//        });
//        weightText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                try {
//                    weight = Float.parseFloat(weightText.getText().toString());
//                } catch (NumberFormatException e) {
//                    Toast.makeText(getActivity(), Consts.WEIGHT_INVALID, Toast.LENGTH_SHORT).show();
//                    weight = 0;
//                }
//
//            }
//        });
//        heightText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                try {
//                    height = Float.parseFloat(heightText.getText().toString());
//                } catch (NumberFormatException e) {
//                    Toast.makeText(getActivity(), Consts.HEIGHT_INVALID, Toast.LENGTH_SHORT).show();
//                    height = 0;
//                }
//
//            }
//        });
//        ogttText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                try {
//                    ogtt = Float.parseFloat(ogttText.getText().toString());
//                } catch (NumberFormatException e) {
////                    Toast.makeText(getActivity(), Consts.OGTT_INVALID, Toast.LENGTH_SHORT).show();
//                    ogtt = 0;
//                }
//
//            }
//        });

        // 输入按钮监听
        ageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoiceDialog(R.id.wheel_view,ageList,ageBtn,
                        new WheelView.OnWheelViewListener(){
                            @Override
                            public void onSelected(int selectedIndex, String item) {
                                selectText = item; // 更新显示的内容
                                age = Integer.parseInt(item); // 更新age
                            }
                        });
            }
        });
        heightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoiceDialog(R.id.wheel_view,heightList,heightBtn,
                        new WheelView.OnWheelViewListener(){
                            @Override
                            public void onSelected(int selectedIndex, String item) {
                                selectText = item;
                                height = Float.parseFloat(item)/100.0f;
                            }
                        });
            }
        });
        weightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weight_Float = 0;
                weight_Int = 0;
                showChoiceDialog(R.id.wheel_view1, R.id.wheel_view2,
                        weightList, zeroToNineList, weightBtn,
                        new WheelView.OnWheelViewListener(){
                            @Override
                            public void onSelected(int selectedIndex, String item) {
                                selectText_left = item;
                                weight_Int = Integer.parseInt(item);
                                weight = weight_Int + weight_Float * 0.1f;
                                Log.d("WEIGHT",weight+"");
                            }
                        },
                        new WheelView.OnWheelViewListener(){
                            @Override
                            public void onSelected(int selectedIndex, String item) {
                                selectText_right = item;
                                weight_Float = Float.parseFloat(item);
                                weight = weight_Int + weight_Float * 0.1f;
                                Log.d("WEIGHT",weight+"");
                            }
                        }
                );
//                weight = weight_Int + weight_Float * 0.1f;
//                Log.d("WEIGHT",weight+"");
            }
        });
        // 单栏显示版
//        ogttBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showChoiceDialog(R.id.wheel_view,ogttList,ogttBtn,
//                        new WheelView.OnWheelViewListener(){
//                            @Override
//                            public void onSelected(int selectedIndex, String item) {
//                                selectText = item;
//                                ogtt = Float.parseFloat(item);
//                                Log.d("WheelView", "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
//                            }
//                        });
//            }
//        });
        // 双栏显示版
        ogttBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ogtt_Float = 0;
                ogtt_Int = 0;
                showChoiceDialog(R.id.wheel_view1, R.id.wheel_view2,
                        ogttList, zeroToNineList, ogttBtn,
                        new WheelView.OnWheelViewListener(){
                            @Override
                            public void onSelected(int selectedIndex, String item) {
                                selectText_left = item;
                                ogtt_Int = Integer.parseInt(item);
                                ogtt = ogtt_Int + ogtt_Float * 0.1f;
                                Log.d("OGTT",ogtt+"");
                            }
                        },
                        new WheelView.OnWheelViewListener(){
                            @Override
                            public void onSelected(int selectedIndex, String item) {
                                selectText_right = item;
                                ogtt_Float = Float.parseFloat(item);
                                ogtt = ogtt_Int + ogtt_Float * 0.1f;
                                Log.d("OGTT",ogtt+"");
                            }
                        }
                );
            }
        });
    }

    // 更新时间显示
    private void updateTime(){
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分 E", Locale.CHINA);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm E", Locale.ENGLISH);
        timeText.setText(format.format(date));
    }

    private String selectText;
    /**
     * 单滑动标尺对话框
     * @param wheelviewId 滑动标尺id
     * @param dataList 数据列表
     * @param infoBtn 激活对话框的按钮（用于显示所选项）
     */
    private void showChoiceDialog(int wheelviewId, ArrayList<String> dataList,
                                  final Button infoBtn, WheelView.OnWheelViewListener listener){
        selectText = "";
        View outerView = LayoutInflater.from(this.getActivity()).inflate(R.layout.dialog_wheelview, null);
        final WheelView wv = outerView.findViewById(wheelviewId);
        wv.setOffset(2);
        wv.setItems(dataList);
        wv.setSeletion(3);
        wv.setOnWheelViewListener(listener);

        new AlertDialog.Builder(this.getActivity())
                .setView(outerView)
                .setPositiveButton(getResources().getString(R.string.confirm_cn),
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        infoBtn.setText(selectText);
                        infoBtn.setTextColor(green);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel_cn),null)
                .show();
    }

    private String selectText_left;
    private String selectText_right;
    /**
     * 双滑动标尺对话框
     * @param leftwheelviewId 左滑动标尺id
     * @param rightwheelviewId 右滑动标尺id
     * @param dataList_L 左数据列表
     * @param dataList_R 右数据列表
     * @param infoBtn 激活对话框的按钮（用于显示所选项）
     */
    private void showChoiceDialog(int leftwheelviewId, int rightwheelviewId,
                                  ArrayList<String> dataList_L, ArrayList<String> dataList_R,
                                  final Button infoBtn,
                                  WheelView.OnWheelViewListener leftListener,
                                  WheelView.OnWheelViewListener rightListener){
        selectText_left = "";
        selectText_right = "";
        View outerView = LayoutInflater.from(this.getActivity()).inflate(R.layout.dialog_twowheelview, null);
        final WheelView wv_L = outerView.findViewById(leftwheelviewId);
        wv_L.setOffset(2);
        wv_L.setItems(dataList_L);
        wv_L.setSeletion(3);
        wv_L.setOnWheelViewListener(leftListener);

        final WheelView wv_R = outerView.findViewById(rightwheelviewId);
        wv_R.setOffset(2);
        wv_R.setItems(dataList_R);
        wv_R.setSeletion(3);
        wv_R.setOnWheelViewListener(rightListener);

        new AlertDialog.Builder(this.getActivity())
                .setView(outerView)
                .setPositiveButton(getResources().getString(R.string.confirm_cn),
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String text = selectText_left + "." + selectText_right;
                        infoBtn.setText(text);
                        infoBtn.setTextColor(green);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel_cn),null)
                .show();
    }

}