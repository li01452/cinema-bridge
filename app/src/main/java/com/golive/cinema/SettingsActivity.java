package com.golive.cinema;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.*;
public final class SettingsActivity extends Activity {
 @Override public void onCreate(Bundle state) {
  super.onCreate(state);
  PackageManager pm=getPackageManager(); Map<String,String> apps=new LinkedHashMap<>();
  for(String category:new String[]{Intent.CATEGORY_LEANBACK_LAUNCHER,Intent.CATEGORY_LAUNCHER}) {
   for(ResolveInfo info:pm.queryIntentActivities(new Intent(Intent.ACTION_MAIN).addCategory(category),0)) {
    String pkg=info.activityInfo.packageName;
    if(!getPackageName().equals(pkg)) apps.put(pkg,info.loadLabel(pm).toString());
   }
  }
  List<String> packages=new ArrayList<>(apps.keySet());
  Collections.sort(packages,(a,b)->apps.get(a).compareToIgnoreCase(apps.get(b)));
  List<String> labels=new ArrayList<>(); String current=getSharedPreferences("bridge",MODE_PRIVATE).getString("target","");
  for(String pkg:packages) labels.add((pkg.equals(current)?"✓ ":"")+apps.get(pkg)+"  ("+pkg+")");
  ListView list=new ListView(this); list.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,labels));
  list.setOnItemClickListener((parent,view,position,id)->{ String pkg=packages.get(position); getSharedPreferences("bridge",MODE_PRIVATE).edit().putString("target",pkg).apply(); Toast.makeText(this,"已选择："+apps.get(pkg),Toast.LENGTH_LONG).show(); finish(); });
  setContentView(list); list.requestFocus();
 }
}
