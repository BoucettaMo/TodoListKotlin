package com.example.todolistkotlin

import Task
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    private val activity: MainActivity,
    private val list: List<Task>,
    private val
    task: EditText,
    private val delay: EditText
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.main_simple, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        holder.textView.text = list[position].task
        holder.textView2.text = list[position].delay
        holder.deleteButton.setOnClickListener {
            val dbManager = DbManager(activity)
            val builder = AlertDialog.Builder(activity)
            with(builder) {
                setMessage("Do you really want  to delete this task?")
                setPositiveButton("Yes") { _, _ ->
                    dbManager.deleteTask(holder.textView.text.toString())
                    activity.recreate()
                }
                setNegativeButton("No") { _, _ -> }
                show()
            }

        }
        holder.updateButton.setOnClickListener {
            val dbManager = DbManager(activity)
            if (task.text.isEmpty() || delay.text.isEmpty()) {
                Toast.makeText(activity, "Please Update task and delay", Toast.LENGTH_LONG).show()
            } else {

                dbManager.updateTask(
                    holder.textView.text.toString(),
                    task.text.toString(), delay.text.toString()
                )

                activity.recreate()

            }


        }

    }

    override fun getItemCount() = list.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        val textView2: TextView = itemView.findViewById(R.id.textView2)
        val deleteButton: Button = itemView.findViewById(R.id.delete)
        val updateButton: Button = itemView.findViewById(R.id.update)

    }


}
