package com.example.gcglfz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(val event: TestEvent) :
  RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

  private var context: Context? = null

  inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val stuView = view.findViewById<ImageView>(R.id.stu_view)
    val stuId = view.findViewById<TextView>(R.id.stu_id)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.stu_item, parent, false)
    if (context == null) context = parent.context
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val list = event.currStudents.toList()
    val idStr = list[position].id.toString()
    val stu = list[position]
    val finishedEvent = arrayListOf<String>().apply {
      for (ev in stu.finishedEvent) {
        if (ev.value) add(Events.getName(ev.key))
      }
    }
    holder.stuId.text = idStr
    holder.stuView.setOnClickListener {
      context?.let {
        val dialog = AlertDialog.Builder(it)
          .setTitle("${idStr}号同学信息")
          .setMessage("期望分数：${stu.expectedScore}\n" +
            "当前状态：${stu.status}\n" +
            "当前测试项目：${stu.currentEvent}\n" +
            "已完成项目：${finishedEvent}\n" +
            "当前总分：${stu.currentScore}")
          .create()
        dialog.show()
      }
    }
  }

  override fun getItemCount() = event.currStudents.size

//  fun updateNewList(newStudents: Queue<Student>) {
//    students = newStudents.toList()
//    notifyDataSetChanged()
//  }
}