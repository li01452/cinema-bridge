package com.golive.cinema;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public final class MainActivity extends Activity {
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        try {
            Intent target = new Intent();
            target.setClassName("com.fongmi.android.tv", "com.fongmi.android.tv.ui.activity.HomeActivity");
            target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(target);
        } catch (ActivityNotFoundException | SecurityException e) {
            Toast.makeText(this, "无法打开影视仓，请确认已安装且可用", Toast.LENGTH_LONG).show();
        } finally {
            finish();
        }
    }
}
