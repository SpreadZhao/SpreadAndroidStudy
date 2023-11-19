package com.spread.zxingtest

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarCodeSurface() {

  var input by remember {
    mutableStateOf("")
  }

  var scanResult by remember {
    mutableStateOf("")
  }
  
  var getResult by remember {
    mutableStateOf(false)
  }

  val context = LocalContext.current

  var bitmap by remember {
    mutableStateOf(ImageBitmap(500, 200))
  }

  val launcher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.StartActivityForResult()
  ) {
    if (it.resultCode == RESULT_OK) {
      val result =
        ScanIntentResult.parseActivityResult(it.resultCode, it.data)
      if (result.contents == null) {
        Toast.makeText(context, "null content", Toast.LENGTH_SHORT).show()
      } else {
        scanResult = result.contents
        getResult = true
      }
    }
  }

  Column {
    Image(
      bitmap = bitmap,
      contentDescription = "Bar code"
    )
    Row {
      TextField(value = input, onValueChange = { input = it }, modifier = Modifier.weight(2f))
      Button(
        onClick = {
          bitmap = ZXingWorker.textToBar(context, input).asImageBitmap()
        },
        modifier = Modifier
          .width(0.dp)
          .weight(1f)
          .align(CenterVertically)
      ) {
        Text(text = "Submit")
      }
    }
    Button(onClick = {
      getResult = false
      launcher.launch(ScanContract().createIntent(context, ScanOptions()))
    }) {
      Text(text = "Scan code")
    }
    if (getResult) {
      Text(text = "Scan result: $scanResult")
    }
  }
}