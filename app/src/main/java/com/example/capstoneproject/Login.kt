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
import org.json.JSONException
import org.json.JSONObject


class Login : AppCompatActivity() {
    var register: TextView? = null
    var username: EditText? = null
    var password: EditText? = null
    var loginButton: Button? = null
    var user: String? = null
    var pass: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        register = findViewById<View>(R.id.register) as TextView
        username = findViewById<View>(R.id.username) as EditText
        password = findViewById<View>(R.id.password) as EditText
        loginButton = findViewById<View>(R.id.loginButton) as Button
        register!!.setOnClickListener {
            startActivity(
                Intent(
                    this@Login,
                    Register::class.java
                )
            )
        }
        loginButton!!.setOnClickListener {
            user = username!!.text.toString()
            pass = password!!.text.toString()
            if (user == "") {
                username!!.error = "can't be blank"
            } else if (pass == "") {
                password!!.error = "can't be blank"
            } else {
                val url = "https://android-chat-app-e711d.firebaseio.com/users.json"
                val pd = ProgressDialog(this@Login)
                pd.setMessage("Loading...")
                pd.show()
                val request = StringRequest(
                    Request.Method.GET, url,
                    { s ->
                        if (s == "null") {
                            Toast.makeText(this@Login, "user not found", Toast.LENGTH_LONG).show()
                        } else {
                            try {
                                val obj = JSONObject(s)
                                if (!obj.has(user)) {
                                    Toast.makeText(this@Login, "user not found", Toast.LENGTH_LONG)
                                        .show()
                                } else if (obj.getJSONObject(user)
                                        .getString("password") == pass
                                ) {
                                    UserDetails.username = user as String
                                    UserDetails.password = pass as String
                                    startActivity(Intent(this@Login, Users::class.java))
                                } else {
                                    Toast.makeText(
                                        this@Login,
                                        "incorrect password",
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
                val rQueue = Volley.newRequestQueue(this@Login)
                rQueue.add(request)
            }
        }
    }
}