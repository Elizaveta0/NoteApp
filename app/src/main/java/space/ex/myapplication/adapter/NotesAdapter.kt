package space.ex.myapplication.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_rv_notes.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import space.ex.myapplication.R
import space.ex.myapplication.database.NotesDatabase
import space.ex.myapplication.database.NotesDatabase.Companion.notesDatabase
import space.ex.myapplication.entities.Notes

class NotesAdapter(val context: Context, val arrList: MutableList<Notes>) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_notes, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.itemView.tvTitle.text = arrList[position].title
        holder.itemView.tvDesc.text = arrList[position].subTitle
        holder.itemView.tvDateTime.text = arrList[position].dateTime
        holder.itemView.tvItem.setOnLongClickListener {
            AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK) // TODO: make dialog theme
                .setTitle("Delete Note")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes") { _, _ ->
                    GlobalScope.launch {
                        NotesDatabase.getDatabase(context).noteDao().deleteNote(arrList[position])
                        arrList.removeAt(position);
                        notifyItemRemoved(position)
                        notifyDataSetChanged()
                    }
                }
                .setNegativeButton("No") {_, _  ->}
                .show()
            true
        }

    }

    class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view){

    }
}