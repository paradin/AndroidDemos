package com.power.androiddemos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ZoomButton;
import android.widget.ZoomButtonsController;
import android.widget.ZoomControls;

/**
 * Created by liyunliu on 14-10-3.
 */
public class DemoButton extends Activity // can only extends one base class
                        implements View.OnClickListener // can implements many interface
                                 , View.OnLongClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Error: R cannot resolve, check the build.gradle "applicationId" with right package
        setContentView(R.layout.activity_demo_button);

        setupBtnBack();

        setupBtns();

        setupCheckBox();

        setupRadioBox();

        steupToggleBtn();

        setupZoomBtn();
    }

    public static final int RESULT_CODE = 1;
    private View.OnClickListener back_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.putExtra("back", "come from DemoButton");

            setResult(RESULT_CODE, intent);
            finish();
        }
    };

    void setupBtnBack() {
        // get parameter of from Intent
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String str = bundle.getString("str");

        ToastTip(str);

        Button btn_back = (Button)findViewById(R.id.btn_btn_back);

        btn_back.setOnClickListener(back_listener);
    }

    void setupBtns() {
        // normal button
        Button btn_simple = (Button)findViewById(R.id.btn_simple);
        // set button text color
        btn_simple.setTextColor(Color.BLUE);
        // set button font size
        btn_simple.setTextSize(30);
        // set button listener options:
        // 1. (standalone listener instance)
        // setStandaloneButtonListener(btn_simple);
        // 2. (a listener instance which implements View.OnClickListener)
        // btn_simple.setOnClickListener(this);
        // 3. (set listener by layout) See activity_demo_index.xml
        // android:onClick="onClick"

        // long click button
        Button btn_long_presss = (Button)findViewById(R.id.btn_long_press);
        btn_long_presss.setTextColor(Color.RED);
        btn_long_presss.setOnLongClickListener(this);

        // image button
        ImageButton btn_image = (ImageButton)findViewById(R.id.btn_image);
        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DemoButton.this, "you click a image button", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setStandaloneButtonListener(Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Usage: Toast
                Toast.makeText(DemoButton.this, "you click a button", Toast.LENGTH_LONG).show();
            }
        });
    }

    // implements interface View.OnClickListener
    @Override
    public void onClick(View view) {
        // judge which button been clicked
        Button btn_simple = (Button)findViewById(R.id.btn_simple);
        Button btn_longpress = (Button)findViewById(R.id.btn_long_press);
        ImageButton btn_image = (ImageButton)findViewById(R.id.btn_image);

        String click_desc = "you click ";
        if(view.equals(btn_image))
            click_desc += "ImageButton";
        else if(view.equals(btn_simple) || view.equals(btn_longpress))
            click_desc += ((Button)view).getText();
        else
            return;

        ToastTip(click_desc);
    }

    // implements interface View.OnLongClickListener
    @Override
    public boolean onLongClick(View view) {

        ToastTip("you long click a button");

        // return true to stop pass click message
        return true;
        // return false to pass click message
    }

    void setupCheckBox() {
        CheckBox show_tips = (CheckBox)findViewById(R.id.checkbox_startshow);

        show_tips.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ToastTip(b ? "Open start tips!" : "Close start tips!");
            }
        });
    }

    void setupRadioBox() {
        RadioGroup sex_group = (RadioGroup)findViewById(R.id.group_sex);

        sex_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                String sex = R.id.radio_male == i ? "Male" : R.id.radio_female == i ? "Female" : "Unknown";

                ToastTip(String.format("Select %s", sex));
            }
        });
    }

    void ToastTip(String tips) {
        Toast.makeText(DemoButton.this, tips, Toast.LENGTH_SHORT).show();
    }

    void steupToggleBtn() {
        ToggleButton btn_toggle = (ToggleButton)findViewById(R.id.btn_toggle);

        btn_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ToastTip(b ? "On" : "Off");
            }
        });
    }

    private int mZoomSize = 0;
    void setupZoomBtn() {
        ZoomButton btn_zoom = (ZoomButton)findViewById(R.id.btn_zoom);
        final TextView zoom_text = (TextView)findViewById(R.id.txt_zoombtn);

        btn_zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mZoomSize += 2;
                zoom_text.setTextSize(mZoomSize);
            }
        });

        ZoomControls btn_zoomctrls = (ZoomControls)findViewById(R.id.btn_zoom_controls);
        btn_zoomctrls.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastTip( "ZoomIn" );
            }
        });
        btn_zoomctrls.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastTip( "ZoomOut" );
            }
        });
        btn_zoomctrls.setIsZoomInEnabled(true);
        btn_zoomctrls.setIsZoomOutEnabled(true);
    }

}
