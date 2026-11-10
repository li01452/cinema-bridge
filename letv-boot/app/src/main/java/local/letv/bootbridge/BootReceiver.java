package local.letv.bootbridge;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
public class BootReceiver extends BroadcastReceiver {
 @Override public void onReceive(Context context, Intent intent) {
  if (!Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) return;
  final PendingResult pending = goAsync();
  new Handler(Looper.getMainLooper()).postDelayed(() -> {
   try {
    Intent target = new Intent(Intent.ACTION_MAIN);
    target.setComponent(new ComponentName("com.huawei.himovceie", "com.github.tvbox.osc.ui.activity.HomeActivity"));
    target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(target);
    Log.i("BootBridge", "Launched target after boot");
   } catch (Exception e) { Log.e("BootBridge", "Could not launch target", e); }
   finally { pending.finish(); }
  }, 5000);
 }
}
