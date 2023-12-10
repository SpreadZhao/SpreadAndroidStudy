package com.spread.recyclerviewstudy

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spread.recyclerviewstudy.recyclerview.Female
import com.spread.recyclerviewstudy.recyclerview.Gender
import com.spread.recyclerviewstudy.recyclerview.Male
import com.spread.recyclerviewstudy.recyclerview.People
import com.spread.recyclerviewstudy.recyclerview.PeopleAdapter
import com.spread.recyclerviewstudy.recyclerview.RecyclerViewManager
import com.spread.recyclerviewstudy.recyclerview.generateItemView

class MainActivity : AppCompatActivity() {

  private val recyclerViewManager = RecyclerViewManager(this)

  private val newPeople = People("xiedaowang", 999, Male)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_main)
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

  private fun findButtonAndSetClick(id: Int, onClick: OnClickListener) {
    findViewById<Button>(id).setOnClickListener(onClick)
  }

  private fun RecyclerViewManager.checkNull(): RecyclerViewManager? = run {
    takeIf { recyclerViewManager.recyclerViewInitialized }
  }
}