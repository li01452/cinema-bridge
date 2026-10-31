# TCL 电视 ADB 连接与快捷键设置攻略

本文介绍如何在 TCL Android 电视上连接 ADB、检查预装应用，并用 Cinema Bridge 将遥控器的“同步院线”键设置为打开指定应用。不同型号和固件的菜单、包名和限制可能不同，请先确认设备情况。

## 连接电视

1. 将电脑和电视连接到同一局域网，在电视的网络设置中查看电视 IP 地址。
2. 在电视进入 **设置 → 关于 → 本机信息**，依次按遥控器 **上、下、左、右**，在出现的选项中打开 ADB。不同固件的菜单名称可能略有不同。
3. 在电脑上安装 [Android SDK Platform-Tools](https://developer.android.com/tools/releases/platform-tools)，然后运行：

   ```bash
   adb connect <电视IP>:5555
   adb devices -l
   ```

4. 如果电视弹出调试授权，选择允许；设备列表显示 `device` 后即可继续。

**连接排查：**如果连接失败，先检查网络和 IP。若电脑能连接网络但 ADB 报错，可尝试 `adb kill-server` 后执行 `adb start-server`，再重新连接。若显示 `unauthorized`，请查看电视是否有授权提示；也可尝试关闭再打开电视的 ADB 开关。部分固件重启后可能需要重新开启 ADB。

## 查看和清理应用

查看电视当前用户的应用包：

```bash
adb shell pm list packages --user 0
```

常见的可选应用包括教育、购物、游戏、K 歌和音乐应用；也有用户会关注广告、推送和统计组件。**不要根据名称批量卸载**：同一包在不同固件上可能承担不同功能。

确定不需要某个应用后，先用 `adb shell pm path 包名` 查看并备份它的所有 APK，再检查依赖。用户级移除命令示例：

```bash
adb shell pm uninstall -k --user 0 包名
```

`-k` 请求保留应用数据；APK 备份不包含私有数据。对仍保留在系统中的预装包，可以尝试 `adb shell cmd package install-existing 包名` 恢复，但是否能恢复原版本和数据取决于设备。

部分 TCL 机型的设置面板依赖天气组件，信源和遥控器也依赖系统服务；清理前应查阅对应机型资料。某些固件对系统应用执行停用／启用还可能造成异常。参考 [Aerial 的 TCL 机型说明](https://github.com/pengchujin/aerial-tv/blob/main/docs/devices/tcl-m7221-cn.md)。

## 安装 Cinema Bridge

Cinema Bridge 适用于同步院线键会启动 `com.golive.cinema.MainActivity` 的电视。它提供一个跳板，将启动请求转给你选择的已安装应用；不同电视不保证兼容。

1. 从 [Releases](https://github.com/li01452/cinema-bridge/releases) 下载 APK，按电视支持的方式安装，例如：

   ```bash
   adb install cinema-bridge-v2.0-debug.apk
   ```

2. 首次按遥控器的**同步院线**键，在列表中选择目标应用并按确认。
3. 以后按该键会打开所选应用；需要更换时，在电视应用列表打开 **“同步院线键设置”**，重新选择。

如果原版同步院线应用仍在电视上，可能出现包名或签名冲突；先检查和备份，不要盲目覆盖系统应用。当前发布包使用调试签名，不同构建的签名可能不同，覆盖安装可能失败。

更多兼容性、安装和构建信息见 [README](../README.md)。
