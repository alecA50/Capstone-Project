package com.example.capstoneproject

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.firebase.client.Firebase
import org.json.JSONException
import org.json.JSONObject


class Register : AppCompatActivity() {
    var username: EditText? = null
    var password: EditText? = null
    var registerButton: Button? = null
    var user: String? = null
    var pass: String? = null
    var login: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        username = findViewById<View>(R.id.username) as EditText
        password = findViewById<View>(R.id.password) as EditText
        registerButton = findViewById<View>(R.id.registerButton) as Button
        login = findViewById<View>(R.id.login) as TextView
        Firebase.setAndroidContext(this)
        login!!.setOnClickListener { startActivity(Intent(this@Register, Chat::class.java)) }
        registerButton!!.setOnClickListener {
            user = username!!.text.toString()
            pass = password!!.text.toString()
            if (user == "") {
                username!!.error = "can't be blank"
            } else if (pass == "") {
                password!!.error = "can't be blank"
            } else if (user!!.length < 5) {
                username!!.error = "at least 5 characters long"
            } else if (pass!!.length < 5) {
                password!!.error = "at least 5 characters long"
            } else {
                val pd = ProgressDialog(this@Register)
                pd.setMessage("Loading...")
                pd.show()
                val url = "https://mycapstoneprojecta-default-rtdb.firebaseio.com/user.json"
                val request = StringRequest(
                    Request.Method.GET, url,
                    { s ->
                        val reference =
                            Firebase("https://mycapstoneprojecta-default-rtdb.firebaseio.com/user.json")
                        if (s == "null") {
                            reference.child(user).child("password").setValue(pass)
                            Toast.makeText(
                                this@Register,
                                "registration successful",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            try {
                                val obj = JSONObject(s)
                                if (!obj.has(user)) {
                                    reference.child(user).child("password").setValue(pass)
                                    Toast.makeText(
                                        this@Register,
                                        "registration successful",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        this@Register,
                                        "username already exists",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }
                        pd.dismiss()
                    }) { volleyError ->
                    println("" + volleyError)
                    pd.dismiss()
                }
                val rQueue = Volley.newRequestQueue(this@Register)
                rQueue.add(request)
            }
        }
    }
}