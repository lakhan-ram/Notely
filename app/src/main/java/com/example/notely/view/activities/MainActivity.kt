package com.example.notely.view.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
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

        viewModel.allNotes.observe(this) { list ->
            if (list.isEmpty()) {
                tvNoNotes.visibility = View.VISIBLE
            } else {
                adapter.updateList(list)
                tvNoNotes.visibility = View.GONE
            }
        }
        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val title = data?.getStringExtra("title")
                val description = data?.getStringExtra("description")
                val id = data?.getIntExtra("id", 0)
                val edit = data?.getBooleanExtra("edit", false)

                if (edit == true) {
                    val note = Note(id!!, title!!, description!!, SimpleDateFormat("dd MMMM yyyy, HH:mm").format(System.currentTimeMillis()))
                    viewModel.updateNote(note)
                } else {
                    val note = Note(title = title!!, description = description!!, timeStamp = SimpleDateFormat("dd MMMM yyyy, HH:mm").format(System.currentTimeMillis()))
                    viewModel.addNote(note)
                }
            }
        }
        btnAddNote.setOnClickListener {
            val intent = Intent(this, NotesActivity::class.java)
            launcher.launch(intent)
        }

        adapter.setOnItemClickLister(object : NotesAdapter.ItemClickListener{
            override fun onLongClick(note: Note) {
                val intent = Intent(this@MainActivity, NotesActivity::class.java)
                intent.putExtra("isEdit", true)
                intent.putExtra("id", note.id)
                intent.putExtra("title", note.title)
                intent.putExtra("description", note.description)
                launcher.launch(intent)
            }

            override fun onClick(note: Note) {
                val intent = Intent(this@MainActivity, NoteViewActivity::class.java)
                intent.putExtra("title", note.title)
                intent.putExtra("timeStamp", note.timeStamp)
                intent.putExtra("description", note.description)
                startActivity(intent)
            }

        })

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = adapter.list[position]
                val alertDialog = AlertDialog.Builder(this@MainActivity)
                alertDialog.setTitle("Delete Note")
                alertDialog.setMessage("Are you sure you want to delete this note?")

                alertDialog.setPositiveButton("Yes") { _, _ ->
                    viewModel.deleteNote(note)
                }

                alertDialog.setNegativeButton("No") { dialog, _ ->
                    adapter.notifyItemChanged(position)
                    dialog.dismiss()
                }
                val dialog = alertDialog.create()
                dialog.show()
            }

        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }
}