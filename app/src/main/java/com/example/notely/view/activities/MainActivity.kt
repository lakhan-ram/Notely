package com.example.notely.view.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notely.R
import com.example.notely.model.entities.Note
import com.example.notely.view.adapters.NotesAdapter
import com.example.notely.viewmodel.NotesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAddNote: FloatingActionButton
    private lateinit var tvNoNotes: TextView
    private lateinit var viewModel: NotesViewModel
    private lateinit var adapter: NotesAdapter

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.recyclerView)
        btnAddNote = findViewById(R.id.btnAddNote)
        tvNoNotes = findViewById(R.id.tvNoNotes)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NotesAdapter()
        recyclerView.adapter = adapter

        viewModel = NotesViewModel(application)

        viewModel.allNotes.observe(this, Observer {list ->
            if (list.isEmpty()) {
                tvNoNotes.visibility = View.VISIBLE
            } else {
                adapter.updateList(list)
                tvNoNotes.visibility = View.GONE
            }
        })
        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val title = data?.getStringExtra("title").toString()
                val description = data?.getStringExtra("description").toString()
                val currentTime = System.currentTimeMillis()
                val dateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm")
                val timeStamp = dateFormat.format(currentTime).toString()
                val note = Note(0, title, description, timeStamp)
                viewModel.addNote(note)
            }
        }
        btnAddNote.setOnClickListener {
            val intent = Intent(this, NotesActivity::class.java)
            launcher.launch(intent)
        }
    }
}