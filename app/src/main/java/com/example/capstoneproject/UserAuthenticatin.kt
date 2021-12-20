package com.example.capstoneproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_user_authenticatin.*
import android.app.ProgressDialog;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

class UserAuthenticatin : AppCompatActivity() {
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
                    this@UserAuthenticatin,
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
                val url = "https://mycapstoneprojecta-default-rtdb.firebaseio.com/user.json"
                val pd = ProgressDialog(this@UserAuthenticatin)
                pd.setMessage("Loading...")
                pd.show()
                val request = StringRequest(
                    Request.Method.GET, url,
                    { s ->
                        if (s == "null") {
                            Toast.makeText(this@UserAuthenticatin, "user not found", Toast.LENGTH_LONG).show()
                        } else {
                            try {
                                val obj = JSONObject(s)
                                if (!obj.has(user)) {
                                    Toast.makeText(this@UserAuthenticatin, "user not found", Toast.LENGTH_LONG)
                                        .show()
                                } else if (obj.getJSONObject(user)
                                        .getString("password") == pass
                                ) {
                                    UserDetails.username = user as String
                                    UserDetails.password = pass as String
                                    startActivity(Intent(this@UserAuthenticatin, Users::class.java))
                                } else {
                                    Toast.makeText(
                                        this@UserAuthenticatin,
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
                val rQueue = Volley.newRequestQueue(this@UserAuthenticatin)
                rQueue.add(request)
            }
        }


        // This is used to align the xml view to this class
        setContentView(R.layout.activity_user_authenticatin)


        tv_register.setOnClickListener {

            startActivity(Intent(this@UserAuthenticatin, UserRegistration::class.java))
        }

        tv_forgot_password.setOnClickListener {

            // Launch the forgot password screen when the user clicks on the forgot password text.
            startActivity(Intent(this@UserAuthenticatin, ForgotPasswordActivity::class.java))
        }

        btn_login.setOnClickListener {

            when {
                TextUtils.isEmpty(et_login_email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@UserAuthenticatin,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(et_login_password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@UserAuthenticatin,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {

                    val email: String = et_login_email.text.toString().trim { it <= ' ' }
                    val password: String = et_login_password.text.toString().trim { it <= ' ' }

                    // Log-In using FirebaseAuth
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful) {

                                Toast.makeText(
                                    this@UserAuthenticatin,
                                    "You are logged in successfully.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent =
                                    Intent(this@UserAuthenticatin, ProfilePage::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra(
                                    "user_id",
                                    FirebaseAuth.getInstance().currentUser!!.uid
                                )
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()
                            } else {

                                // If the login is not successful then show error message.
                                Toast.makeText(
                                    this@UserAuthenticatin,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        }
                }
            }
        }
    }
}
