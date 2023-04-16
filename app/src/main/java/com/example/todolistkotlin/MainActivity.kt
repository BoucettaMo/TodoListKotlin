package com.example.todolistkotlin

import Task
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private lateinit var addButton: Button
    private lateinit var dbmanager: DbManager
    private lateinit var task: EditText
    private lateinit var delay: EditText
    private lateinit var list: MutableList<Task>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "My todo List"
        addButton = findViewById(R.id.addtask)
        task = findViewById(R.id.task)
        delay = findViewById(R.id.delay)
        recyclerView = findViewById(R.id.recyclerView)
        dbmanager = DbManager(this@MainActivity)

        list = dbmanager.readDb()

        myAdapter = MyAdapter(this, list, task, delay)
        recyclerView.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        recyclerView.adapter = myAdapter




        addButton.setOnClickListener {
            if (task.text.isEmpty() || delay.text.isEmpty()) {
                Toast.makeText(this@MainActivity, "Please write a task and a delay", Toast.LENGTH_LONG).show()

            } else {

                dbmanager.addTask(task.text.toString(), delay.text.toString())
                task.setText("")
                delay.setText("")


                this.recreate()


            }

        }

    }











}