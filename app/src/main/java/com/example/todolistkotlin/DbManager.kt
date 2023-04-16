package com.example.todolistkotlin

import Task
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbManager(private val context: Context) : SQLiteOpenHelper(context, tableName, null, version) {


    companion object {
        private const val tableName = "listTasks";
        private const val version = 1
        private const val colTask = "task";
        private const val colDelay = "delay";
        private const val idCol = "id";

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "CREATE TABLE $tableName ($idCol INTEGER PRIMARY KEY AUTOINCREMENT,$colTask TEXT,$colDelay TEXT)"
        db?.execSQL(query)
    }

    fun addTask(task: String, delay: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(colTask, task)
        contentValues.put(colDelay, delay)
        db.insert(tableName, null, contentValues)
        db.close()
    }

    fun readDb(): MutableList<Task> {
        val list = mutableListOf<Task>()
        val db = this.readableDatabase
        val cursorList = db.rawQuery("SELECT * FROM $tableName",null)
         if (cursorList.moveToFirst()) {
             do {
                 list.add(Task(cursorList.getString(1),cursorList.getString(2)))
             } while (cursorList.moveToNext())
         }

        cursorList.close()

        return list

    }

    fun updateTask(originalTask: String, task:String, delay:String) {
        val db = this.writableDatabase
        val contentValues =ContentValues()
        contentValues.put(colTask,task)
        contentValues.put(colDelay,delay)

        db.update(tableName,contentValues,"task=?", arrayOf(originalTask))
        db.close()
    }

    fun deleteTask(task:String) {
        val db = this.writableDatabase
        db.delete(tableName,"task=?", arrayOf(task))

     db.close()
    }



        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS  $tableName")
        onCreate(db)


    }


}
