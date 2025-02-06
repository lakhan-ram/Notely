package com.example.notely.view.activities

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notely.R

class NoteViewActivity : AppCompatActivity() {
    private lateinit var tvNoteTitleView: TextView
    private lateinit var tvNoteTimeStampView: TextView
    private lateinit var tvNoteDescriptionView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_note_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tvNoteTitleView = findViewById(R.id.tvNoteTitleView)
        tvNoteTimeStampView = findViewById(R.id.tvNoteTimeStampView)
        tvNoteDescriptionView = findViewById(R.id.tvNoteDescriptionView)
        val title = intent.getStringExtra("title")
        val timeStamp = intent.getStringExtra("timeStamp")
        val description = intent.getStringExtra("description")
        tvNoteTitleView.text = title
        tvNoteTimeStampView.text = timeStamp
        tvNoteDescriptionView.text = description

    }
}