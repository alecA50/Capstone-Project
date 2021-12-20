package com.example.capstoneproject.model

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.capstoneproject.MainActivity
import com.example.capstoneproject.ProfilePage
import com.example.capstoneproject.R
import com.firebase.client.Firebase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_model_student_update.*
import org.json.JSONException
import org.json.JSONObject

class User_Model_Student_Update : AppCompatActivity() {
    var username: androidx.appcompat.widget.AppCompatEditText? = null
    var password: androidx.appcompat.widget.AppCompatEditText? = null
    var email: androidx.appcompat.widget.AppCompatEditText? = null
    var department: androidx.appcompat.widget.AppCompatEditText? = null
    var roll_number: androidx.appcompat.widget.AppCompatEditText? = null
    var year: androidx.appcompat.widget.AppCompatEditText? = null
    var registerButton: Button? = null
    var user: String? = null
    var pass: String? = null
    var depart: String? = null
    var rollnumber: String? = null
    var Y: String? = null // year

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_model_student_update)
        username = findViewById<View>(R.id.username) as androidx.appcompat.widget.AppCompatEditText
        password = findViewById<View>(R.id.password) as androidx.appcompat.widget.AppCompatEditText
        department = findViewById<View>(R.id.et_department) as androidx.appcompat.widget.AppCompatEditText
        roll_number = findViewById<View>(R.id.et_roll_number) as androidx.appcompat.widget.AppCompatEditText
        year = findViewById<View>(R.id.et_year) as androidx.appcompat.widget.AppCompatEditText



        registerButton = findViewById<View>(R.id.registerButton) as Button
        Firebase.setAndroidContext(this)
        registerButton!!.setOnClickListener {
            user = username!!.text.toString()
            pass = password!!.text.toString()
            depart = department!!.text.toString()
            rollnumber = roll_number!!.text.toString()
            Y = year!!.text.toString()
            if (user == "") {
                username!!.error = "can't be blank"
            } else if (pass == "") {
                password!!.error = "can't be blank"
            } else if (user!!.length < 5) {
                username!!.error = "at least 5 characters long"
            } else if (pass!!.length < 5) {
                password!!.error = "at least 5 characters long"
            } else {
                val pd = ProgressDialog(this@User_Model_Student_Update)
                pd.setMessage("Loading...")
                pd.show()
                val url = "https://mycapstoneprojecta-default-rtdb.firebaseio.com/user.json"
                val request = StringRequest(
                    Request.Method.GET, url,
                    { s ->
                        val reference =
                            Firebase("https://mycapstoneprojecta-default-rtdb.firebaseio.com/user")
                        if (s == "null") {
                            reference.child(user).child("password").setValue(pass)
                            Toast.makeText(
                                this@User_Model_Student_Update,
                                "registration or update successful",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            try {
                                val obj = JSONObject(s)
                                if (!obj.has(user)) {
                                    reference.child(user).child("password").setValue(pass)
                                    Toast.makeText(
                                        this@User_Model_Student_Update,
                                        "registration or update successful",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        this@User_Model_Student_Update,
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
                val rQueue = Volley.newRequestQueue(this@User_Model_Student_Update)
                rQueue.add(request)
            }
        }
        returnButton.setOnClickListener {
            // Profile login from app.
            FirebaseAuth.getInstance().currentUser

            startActivity(Intent(this@User_Model_Student_Update, ProfilePage::class.java))
            finish()


            // END
        }
    }
}