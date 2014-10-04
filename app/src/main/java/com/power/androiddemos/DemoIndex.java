package com.power.androiddemos;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class DemoIndex  extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Error: R cannot resolve, check the build.gradle "applicationId" with right package
        setContentView(R.layout.activity_demo_index);

        //SetupBtnOkCancel();

    }

    private static final int REQUEST_CODE = 1;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.demo_btn: {
                Toast.makeText(DemoIndex.this, "you click demobutton", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.setClass(DemoIndex.this, DemoButton.class);
                intent.putExtra("str", "from DemoIndex");

                // Methods of start activity:
                // 1. start activity, without result
                // startActivity(intent);

                // 2. start activity, with result
                startActivityForResult(intent, REQUEST_CODE);

                // 3. start unspecific activity
                // startUnspecificActivity();
            }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE) {
            if(resultCode == DemoButton.RESULT_CODE) {
                Bundle bundle = data.getExtras();
                String str = bundle.getString("back");

                Toast.makeText(DemoIndex.this, str, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private  void startUnspecificActivity() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:10086"));
        startActivity(intent);
    }
}
