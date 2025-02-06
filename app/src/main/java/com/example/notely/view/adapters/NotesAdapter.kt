package com.example.notely.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notely.R
import com.example.notely.model.entities.Note

class NotesAdapter: RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    val list: List<Note> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_each_note, parent, false)
        return NotesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = list[position]
        holder.tvTitle.text = note.title
        holder.tvTimeStamp.text = note.timeStamp
        holder.tvDescription.text = note.description
    }

    class NotesViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvNoteTitle)
        val tvTimeStamp: TextView = view.findViewById(R.id.tvNoteTimeStamp)
        val tvDescription: TextView = view.findViewById(R.id.tvNoteDescription)
    }
}