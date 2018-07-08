package com.chernowii.dolist

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout

import kotlinx.android.synthetic.main.activity_task_list.*

class TaskListActivity : AppCompatActivity() {

    var nameList = mutableListOf<String>()
    val PREFS_FILENAME = "com.chernowii.dolist.prefs"
    val TASK_LIST = "task_list"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            newTask(view)

        }

        //Populate RecyclerView
        val prefs = this.getSharedPreferences(PREFS_FILENAME, 0)
        var list = prefs.getStringSet(TASK_LIST, setOf<String>())
        if (!list.isEmpty()) {
            for (s: String in list) {
                Log.d("TASK", s)
            }
        }

        val rv = findViewById<RecyclerView>(R.id.task_rv_list)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        val users = ArrayList<String>()
        users.add("TASK 1")
        users.add("TASK 2")
        users.add("TASK 3")

        var adapter = TaskListAdapter(users)
        rv.adapter = adapter

    }

    fun newTask(view: View){
        val prefs = this.getSharedPreferences(PREFS_FILENAME, 0)

        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.new_task_dialog, null)
        dialogBuilder.setView(dialogView)

        val editText = dialogView.findViewById<View>(R.id.taskInputName) as EditText

        dialogBuilder.setTitle("Add task")
        dialogBuilder.setPositiveButton("Save", { dialog, whichButton ->

            val list = prefs.getStringSet(TASK_LIST, null)
            var newList = list
            newList.add(editText.text.toString())
            val editor = prefs!!.edit()
            editor.putStringSet(TASK_LIST, newList)
            editor.apply()
            Snackbar.make( view, "Task added", Snackbar.LENGTH_LONG).show()

        })
        dialogBuilder.setNegativeButton("Cancel", { dialog, whichButton ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_task_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
