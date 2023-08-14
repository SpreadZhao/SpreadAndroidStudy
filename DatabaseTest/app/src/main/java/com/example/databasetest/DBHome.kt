package com.example.databasetest

import android.database.sqlite.SQLiteConstraintException
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.databasetest.data.DeformVerb
import com.example.databasetest.data.Sentence
import com.example.databasetest.data.Word
import com.example.databasetest.data.WordConstant
import com.example.databasetest.data.WordConstant.VERB_1
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

@Composable
fun DBHome(
    helper: MyDatabaseHelper,
    wordDao: WordDao
) {
    val mContext = LocalContext.current

    val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                123 -> {
                    Toast.makeText(mContext, msg.obj.toString(), Toast.LENGTH_SHORT).show()
                    Log.d("DBHome", msg.obj.toString())
                }
            }
        }
    }

    Column {
        Button(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            onClick = {
                helper.writableDatabase
            }
        ) {
            Text(text = "Create database")
        }

        Button(onClick = {
            thread {
                try {
                    wordDao.insertSentence(
                        Sentence(
                            id = 1,
                            key = 456,
                            content = "林檎を3個買いました。"
                        )
                    )
                    wordDao.insertDeformVerb(
                        DeformVerb(
                            id = 1
                        )
                    )
                    wordDao.insertWord(
                        Word(
                            kanji = "買う",
                            gana = "かう",
                            meaning = "购买",
                            notice = "か开头的动词",
                            type = VERB_1,
                            deformVerb = 1,
                            sentenceKey = 1
                        )
                    )
                } catch (e: SQLiteConstraintException) {
                    e.printStackTrace()
                    val msg = Message().apply {
                        what = 123
                        obj = e
                    }
                    handler.sendMessage(msg)
                }
            }
        }) {
            Text(text = "Insert sentence")
        }
        Button(onClick = {
            thread {
                wordDao.getAllSentences().forEach {
                    Log.d("DBHome", "sentence: $it")
                }
                wordDao.getAllWords().forEach {
                    Log.d("DBHome", "word: $it")
                }
            }
        }) {
            Text(text = "Query sentences")
        }
    }

}