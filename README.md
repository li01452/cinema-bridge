# Cinema Bridge｜同步院线键跳板

[English](#english) · [TCL 电视 ADB 连接与快捷键攻略](docs/TCL电视ADB连接与快捷键攻略.md)

Cinema Bridge 是一个 Android 电视快捷键跳板。它让支持的 TCL 遥控器上的**“同步院线”键**打开你选择的已安装应用，例如影视仓。选定后，再按该键即可直接启动目标应用；你可以随时更改选择。

本应用不提供影视内容，不需要网络权限，也不需要常驻按键监听服务。

## 工作原理与兼容性

部分 TCL 固件会用同步院线键启动 `com.golive.cinema/com.golive.cinema.MainActivity`。本应用提供这个组件，将启动请求转发给用户所选应用。

- 已在 **TCL 75V2、Android 8.0** 上测试：按键可以打开所选应用。不同电视、固件和遥控器可能使用其他启动方式，不能保证兼容。
- 如果电视上已有原版“同步院线”应用，同包名可能发生安装冲突；系统预装版本即使对当前用户卸载，也可能因签名不同而无法安装。请先确认并备份原应用，不要随意删除系统组件。
- 应用需要能被电视识别为可启动应用；不出现在选择列表中的后台服务或信号源无法通过本应用选择。

## 安装

1. 在仓库的 [Actions](https://github.com/li01452/cinema-bridge/actions) 页面打开一次**成功**的 `Build APK` 运行，在 Artifacts 下载 `cinema-bridge-apk`，解压得到 `app-debug.apk`。下载 Actions 产物可能需要登录 GitHub。
2. 按电视的方式安装 APK，例如通过 ADB：

   ```bash
   adb install app-debug.apk
   ```

3. 如果电视提示安装授权，请在电视上确认。不同 TCL 固件可能限制通过 ADB 安装，具体以电视的提示为准。

当前 Actions 生成的是**调试签名** APK。不同运行产生的签名可能不同，覆盖安装可能报签名不一致。处理前请备份旧 APK；卸载旧版会丢失已保存的选择，需要重新设置。

## 使用

1. 首次按遥控器的**同步院线**键，出现应用列表后，用方向键选择应用并按确认。
2. 以后按同步院线键，将直接打开所选应用。
3. 要修改选择，在电视桌面的应用列表中打开 **“同步院线键设置”**，再选择其他应用。
4. 如果所选应用已被卸载或无法启动，跳板会提示并打开选择列表。

选择保存在电视本地。安装或重新安装后，请实际测试遥控器；部分设备可能需要重启后再确认。卸载应用会删除其设置。

## 乐视电视开机启动影视仓（实验功能）

此功能在独立分支 [letv-boot-bridge](https://github.com/li01452/cinema-bridge/tree/letv-boot-bridge) 中，不影响上面的同步院线键功能。它提供一个开机接收器，在系统启动完成后等待 5 秒，再打开已安装的影视仓（包名 `com.huawei.himovceie`）。影视仓自身的开机接收器在测试电视上未能通过系统权限检查，因此使用这个助手代为启动。

### 下载与安装

1. 打开 [乐视启动助手的 GitHub Actions](https://github.com/li01452/cinema-bridge/actions/workflows/letv-boot.yml?branch=letv-boot-bridge)，选择成功的 **Build LeTV boot bridge** 运行。
2. 下载该次运行的 `letv-boot-bridge-apk` Artifact 并解压 `app-debug.apk`。
3. 通过 ADB 安装并重启电视验证：

   ```bash
   adb connect 电视IP:5555
   adb install app-debug.apk
   adb reboot
   ```

这台乐视 Android 9 电视已实测成功：重启后自动进入影视仓。助手需要 `RECEIVE_BOOT_COMPLETED` 权限；首次安装或更新后，若电视的权限管理界面询问，请允许它开机启动。乐视桌面和遥控器功能保持安装状态。

### 更新与卸载

GitHub Actions 输出的是调试签名 APK，不同构建的签名可能不同。若覆盖安装提示签名不匹配，先运行 `adb uninstall local.letv.bootbridge` 再安装新 APK。卸载助手会停止自动启动影视仓；不会卸载影视仓或乐视桌面。

## 构建

项目使用 Java 17、Gradle 8.9、Android Gradle Plugin 8.7.3 和 Android SDK 35；最低支持 Android 5.0（API 21）。可通过 GitHub Actions 构建，也可在已配置 Android SDK 和 Gradle 的电脑上运行：

```bash
gradle assembleDebug
```

输出位于 `app/build/outputs/apk/debug/app-debug.apk`。

---

<a id="english"></a>
# Cinema Bridge

Cinema Bridge is an Android TV shortcut bridge. On supported TCL televisions, it lets the remote's **Cinema** button launch an installed app of your choice, such as FongMi's TV app. Once selected, the button opens that app directly; you can change the selection at any time.

The app provides no video content, requests no network permission, and does not need an always-running key listener.

## How it works and compatibility

Some TCL firmware launches `com.golive.cinema/com.golive.cinema.MainActivity` when the Cinema button is pressed. Cinema Bridge provides that component and forwards the request to the selected app.

- Tested on a **TCL 75V2 running Android 8.0**: the button opened the selected app. Other televisions, firmware versions, and remotes may handle the button differently and are not guaranteed to work.
- An existing Cinema app can conflict with this package name. Even if a preinstalled system app was removed for the current user, a signature mismatch may prevent installation. Check and back up the original app first; do not remove system components indiscriminately.
- Only apps that the TV exposes as launchable can be selected. Background services and input sources may not appear in the list.

## Installation

1. Open a **successful** `Build APK` run on the repository's [Actions](https://github.com/li01452/cinema-bridge/actions) page. Download the `cinema-bridge-apk` artifact and extract `app-debug.apk`. GitHub may require you to sign in to download artifacts.
2. Install the APK using your television's supported method, for example with ADB:

   ```bash
   adb install app-debug.apk
   ```

3. Approve installation on the television if prompted. Some TCL firmware restricts ADB installation; follow the prompts shown by your device.

Actions currently produces a **debug-signed** APK. The signing key may differ between builds, preventing an in-place update. Back up the old APK before changing it; uninstalling the old version removes its saved selection.

## Usage

1. Press the remote's **Cinema** button for the first time. In the app list, use the direction keys to select an app and press OK.
2. Subsequent presses open the selected app directly.
3. To change it, open **同步院线键设置** (Cinema Button Settings) from the TV's app list and select another app.
4. If the selected app has been removed or cannot be launched, the bridge displays a message and opens the selection list.

The selection is stored locally on the TV. Test the button after installation or reinstallation; on some devices, also verify it after a restart. Uninstalling Cinema Bridge removes its settings.

## LeTV boot launch for 影视仓 (experimental)

This feature is on the separate [letv-boot-bridge](https://github.com/li01452/cinema-bridge/tree/letv-boot-bridge) branch and is independent of the Cinema button feature above. The helper receives the boot-completed broadcast, waits five seconds, and launches the installed 影视仓 app (`com.huawei.himovceie`). It works around the boot receiver permission failure observed on the tested television.

### Download and install

1. Open [LeTV boot bridge Actions](https://github.com/li01452/cinema-bridge/actions/workflows/letv-boot.yml?branch=letv-boot-bridge) and select a successful **Build LeTV boot bridge** run.
2. Download its `letv-boot-bridge-apk` artifact and extract `app-debug.apk`.
3. Install it with ADB and reboot to verify:

   ```bash
   adb connect TV_IP:5555
   adb install app-debug.apk
   adb reboot
   ```

Verified on the LeTV Android 9 television in this repository's setup: after reboot it opened 影视仓 automatically. The helper requests `RECEIVE_BOOT_COMPLETED`; allow its auto-start permission if the TV asks. The LeTV desktop and remote control remain installed.

### Update and uninstall

Actions creates a debug-signed APK, and signatures may differ between builds. If an update reports a signature mismatch, uninstall `local.letv.bootbridge` with `adb uninstall local.letv.bootbridge`, then install the new APK. Removing the helper stops automatic launch; it does not uninstall 影视仓 or the LeTV desktop.

## Build

The project uses Java 17, Gradle 8.9, Android Gradle Plugin 8.7.3, and Android SDK 35. The minimum supported Android version is 5.0 (API 21). Build with GitHub Actions or run the following on a machine configured with the Android SDK and Gradle:

```bash
gradle assembleDebug
```

The APK is written to `app/build/outputs/apk/debug/app-debug.apk`.
