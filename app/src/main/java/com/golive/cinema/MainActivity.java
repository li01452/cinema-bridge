package com.golive.cinema;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
public final class MainActivity extends Activity {
 @Override public void onCreate(Bundle state) {
  super.onCreate(state);
  String pkg=getSharedPreferences("bridge",MODE_PRIVATE).getString("target",null);
  Intent target=pkg==null?null:getPackageManager().getLeanbackLaunchIntentForPackage(pkg);
  if(target==null && pkg!=null) target=getPackageManager().getLaunchIntentForPackage(pkg);
  if(target==null) { if(pkg!=null) Toast.makeText(this,"所选应用不可用，请重新选择",Toast.LENGTH_LONG).show(); startActivity(new Intent(this,SettingsActivity.class)); }
  else try { startActivity(target); } catch(RuntimeException e) { Toast.makeText(this,"无法打开应用，请重新选择",Toast.LENGTH_LONG).show(); startActivity(new Intent(this,SettingsActivity.class)); }
  finish();
 }
}
