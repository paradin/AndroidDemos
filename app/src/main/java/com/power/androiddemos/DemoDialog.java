package com.power.androiddemos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public class DemoDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_dialog);

        setupAlertDialog();
    }

    /*
    // Error: cannot resolve Handler
    class ButtonHandler extends Handler {
        private WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface dialog) {
            mDialog = new WeakReference<DialogInterface>(dialog);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DialogInterface.BUTTON_POSITIVE:
                case DialogInterface.BUTTON_NEUTRAL:
                case DialogInterface.BUTTON_NEGATIVE:
                    ((DialogInterface.OnClickListener)msg.obj).onClick(mDialog.get(), msg.what);
                    break;
            }
        }
    }
    */

    void setupAlertDialog() {
        Button btn_alert = (Button)findViewById(R.id.btn_alert_dlg);
        btn_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // AlertDialog after handle click message, will send dismiss message to handler
                AlertDialog dlg = new AlertDialog.Builder(DemoDialog.this)
                        .setTitle("Reflect Control not CLOSE")
                        .setMessage("1. Reset ButtonHandler of AlertDialog \n" +
                                    "2. Access property of AlertDialog to ignore dismiss")
                        .setIcon(R.drawable.ic_launcher)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // default, the dialog will closed after handle positive button click

                                        // 2. set mShowing=false, to avoid close dialog, after onClick
                                        try {
                                            Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                            field.setAccessible(true);

                                            field.set(dialogInterface, false);
                                        }
                                        catch (Exception e) {

                                        }
                                    }
                                }
                        )
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        // if mShowing modified when click Positive button
                                        // also effect current click

                                        // to recover change
                                        try {
                                            Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                            field.setAccessible(true);

                                            field.set(dialogInterface, true);
                                        }
                                        catch (Exception e) {

                                        }

                                        dialogInterface.dismiss();
                                    }
                                }
                        )
                        .create();

                // Reset ButtonHandler
                try {
                    // access mAlert property of AlertDialog by reflection
                    Field field = dlg.getClass().getDeclaredField("mAlert");
                    field.setAccessible(true);

                    // get property object
                    Object obj = field.get(dlg);
                    field = obj.getClass().getDeclaredField("mHandler");
                    field.setAccessible(true);

                    // change custom Handler
                    // field.set(obj, new ButtonHandler(dlg));
                }
                catch (Exception e) {

                }

                dlg.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo_dialog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
