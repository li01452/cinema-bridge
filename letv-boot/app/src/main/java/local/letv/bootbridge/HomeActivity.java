package local.letv.bootbridge;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.ComponentName;
import android.util.Log;
public class HomeActivity extends Activity {
 @Override public void onCreate(Bundle state) {
  super.onCreate(state);
  try {
   Intent target = new Intent(Intent.ACTION_MAIN);
   target.setComponent(new ComponentName("com.huawei.himovceie", "com.github.tvbox.osc.ui.activity.HomeActivity"));
   target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
   startActivity(target);
  } catch (Exception e) {
   Log.e("BootBridge", "Cannot launch 影视仓; restoring desktop", e);
   try { Intent fallback = new Intent(Intent.ACTION_MAIN); fallback.setComponent(new ComponentName("com.stv.appdaemon", "com.stv.appdaemon.ui.DaemonActivity")); fallback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); startActivity(fallback); } catch (Exception ignored) { Log.e("BootBridge", "Desktop unavailable", ignored); }
  }
  finish();
 }
}
