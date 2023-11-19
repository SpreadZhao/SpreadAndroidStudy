package com.spread.zxingtest

import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

object ZXingWorker {

  fun textToBar(context: Context, text: String): Bitmap {
    val bitMatrix = MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, 500, 200)
    return toBitMap(bitMatrix, context)
  }

  private fun toBitMap(bitMatrix: BitMatrix, context: Context): Bitmap {
    val height = bitMatrix.height
    val width = bitMatrix.width
    val bitMap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    for (x in 0 until width) {
      for (y in 0 until height) {
        bitMap.setPixel(
          x,
          y,
          if (bitMatrix.get(x, y)) ContextCompat.getColor(context, R.color.black)
          else ContextCompat.getColor(context, R.color.white))
      }
    }
    return bitMap
  }
}