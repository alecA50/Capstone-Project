package com.example.capstoneproject

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.message_area.*
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList


class Users : AppCompatActivity() {
    var usersList: ListView? = null
    var noUsersText: TextView? = null
    var al = ArrayList<String>()
    var totalUsers = 0
    var pd: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        usersList = findViewById<View>(R.id.usersList) as ListView
        noUsersText = findViewById<View>(R.id.noUsersText) as TextView
        pd = ProgressDialog(this@Users)
        pd!!.setMessage("Loading...")
        pd!!.show()
        val url = "https://mycapstoneprojecta-default-rtdb.firebaseio.com/user.json"
        val request = StringRequest(
            Request.Method.GET, url,
            { s -> doOnSuccess(s) }
        ) { volleyError -> println("" + volleyError) }
        val rQueue = Volley.newRequestQueue(this@Users)
        rQueue.add(request)
        usersList!!.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                UserDetails.chatWith = al[position]
                startActivity(Intent(this@Users, Chat::class.java))
            }
    }

    fun doOnSuccess(s: String?) {
        try {
            val obj = JSONObject(s)
            val i: Iterator<*> = obj.keys()
            var key = ""
            while (i.hasNext()) {
                key = i.next().toString()
                if (key != UserDetails.username) {
                    al.add(key)
                }
                totalUsers++
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        if (totalUsers <= 1) {
            noUsersText!!.visibility = View.VISIBLE
            usersList!!.visibility = View.GONE
        } else {
            noUsersText!!.visibility = View.GONE
            usersList!!.visibility = View.VISIBLE
            usersList!!.adapter =
                ArrayAdapter(this, android.R.layout.simple_list_item_1, al)
        }
        pd!!.dismiss()
    }
}