package com.example.notely.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notely.R

class NotesActivity : AppCompatActivity() {
    private lateinit var tvNoteTitle: TextView
    private lateinit var etTextTitleNote: EditText
    private lateinit var etTextDescriptionNote: EditText
    private lateinit var btnSaveNote: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tvNoteTitle = findViewById(R.id.tvNoteTitle)
        etTextTitleNote = findViewById(R.id.etTextTitleNote)
        etTextDescriptionNote = findViewById(R.id.etTextDescriptionNote)
        btnSaveNote = findViewById(R.id.btnSaveNote)

        btnSaveNote.setOnClickListener {
            val title = etTextTitleNote.text.toString()
            val description = etTextDescriptionNote.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val intent = Intent()
                intent.putExtra("title", title)
                intent.putExtra("description", description)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}