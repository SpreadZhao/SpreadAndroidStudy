package com.spread.recyclerviewstudy.itemactivity

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.spread.recyclerviewstudy.R
import com.spread.recyclerviewstudy.recyclerview.Male
import com.spread.recyclerviewstudy.recyclerview.People
import com.spread.recyclerviewstudy.recyclerview.RecyclerViewManager

class SimpleRecyclerViewActivity : AppCompatActivity() {

  private val recyclerViewManager = RecyclerViewManager(this)

  private val newPeople = People("xiedaowang", 999, Male)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_simple_recycler_view)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root_layout)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    recyclerViewManager.initRecyclerView()
    initButtons()
  }

  private fun initButtons() {
    findButtonAndSetClick(R.id.add_marque) {
      recyclerViewManager.checkNull()?.addPeople(newPeople)
    }
    findButtonAndSetClick(R.id.change_third) {
      recyclerViewManager.checkNull()?.changePeople(3, newPeople)
    }
    findButtonAndSetClick(R.id.refresh) {
      recyclerViewManager.checkNull()?.reversePeople()
    }
  }

  private fun findButtonAndSetClick(id: Int, onClick: View.OnClickListener) {
    findViewById<Button>(id).setOnClickListener(onClick)
  }

  private fun RecyclerViewManager.checkNull(): RecyclerViewManager? = run {
    takeIf { recyclerViewManager.recyclerViewInitialized }
  }
}