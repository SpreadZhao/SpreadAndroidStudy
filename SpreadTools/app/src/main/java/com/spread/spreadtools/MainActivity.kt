package com.spread.spreadtools

import android.app.AlertDialog
import android.app.KeyguardManager
import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.spread.spreadtools.ui.theme.SpreadToolsTheme

class MainActivity : ComponentActivity() {

  private lateinit var dpm: DevicePolicyManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      SpreadToolsTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          ToolsScreen(dpm)
        }
      }
    }
    dpm = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
  }
}

fun addAdmin(context: Context) {
  val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
  val mDeviceAdminSample = ComponentName(context, MyAdmin::class.java)
  intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample)
  intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Fuck you!")
  context.startActivity(intent)
}

fun lockScreen(dpm: DevicePolicyManager, context: Context) {
  val who = ComponentName(context, MyAdmin::class.java)
  if (dpm.isAdminActive(who)) {
//    dpm.resetPasswordWithToken(who, "", byteArrayOf(), 0)
    dpm.lockNow()
  } else {
    context.showToast("not activate")
  }
}

fun showKeyguard(context: Context) {
  val keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && keyguardManager.isDeviceSecure) {
    val cancellationSignal = CancellationSignal()
    keyguardManager.requestDismissKeyguard(context as ComponentActivity, MyKeyguardCallback())
  } else {
    // Device does not have secure lock screen
    context.showToast("Device does not have secure lock screen")
  }
}


fun removeAdmin(dpm: DevicePolicyManager, context: Context) {
  val who = ComponentName(context, MyAdmin::class.java)
  if (dpm.isAdminActive(who)) {
    dpm.removeActiveAdmin(who)
  }
}

fun testAdmin(dpm: DevicePolicyManager, context: Context) {
  val who = ComponentName(context, MyAdmin::class.java)
  val dialog = AlertDialog.Builder(context)
    .setTitle("Admin")
    .setMessage("${dpm.isAdminActive(who)}")
    .create()
  dialog.show()
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(
    text = "Hello $name!",
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  SpreadToolsTheme {
    Greeting("Android")
  }
}

@Composable
fun ToolsScreen(dpm: DevicePolicyManager) {
  val context = LocalContext.current
  LazyColumn {
    item {
      Button(onClick = { addAdmin(context) }) {
        Text(text = "Request Admin")
      }
    }
    item {
      Button(onClick = { lockScreen(dpm, context) }) {
        Text(text = "Lock Screen")
      }
    }
    item {
      Button(onClick = { removeAdmin(dpm, context) }) {
        Text(text = "Remove Admin")
      }
    }

    item {
      Button(onClick = { testAdmin(dpm, context) }) {
        Text(text = "Is Admin Active?")
      }
    }
  }
}

class MyAdmin : DeviceAdminReceiver() {
  override fun onDisabled(context: Context, intent: Intent) {
    super.onDisabled(context, intent)
    context.showToast("onDisable")
  }

  override fun onEnabled(context: Context, intent: Intent) {
    super.onEnabled(context, intent)
    context.showToast("onEnable")
  }

  override fun onDisableRequested(context: Context, intent: Intent): CharSequence {
    return "do you want disable it?"
  }
}

class MyKeyguardCallback : KeyguardManager.KeyguardDismissCallback() {
  override fun onDismissSucceeded() {
    super.onDismissSucceeded()
    // Handle successful unlock
  }

  override fun onDismissCancelled() {
    super.onDismissCancelled()
    // Handle unlock cancellation
  }

  override fun onDismissError() {
    super.onDismissError()
    // Handle unlock error
  }
}

private fun Context.showToast(msg: String) {
  Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}