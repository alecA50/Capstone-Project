package com.example.capstoneproject.model

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.capstoneproject.MainActivity
import com.example.capstoneproject.ProfilePage
import com.example.capstoneproject.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btn_Profile
import kotlinx.android.synthetic.main.activity_user_model_faculty.*
import kotlinx.android.synthetic.main.activity_user_model_faculty.btn_update
import kotlinx.android.synthetic.main.activity_user_model_faculty.et_department
import kotlinx.android.synthetic.main.activity_user_model_faculty.et_name
import kotlinx.android.synthetic.main.activity_user_model_student.*
import kotlinx.android.synthetic.main.activity_user_registration.*

class User_Model_Student: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_model_student)



        btn_update.setOnClickListener {

            when {
                TextUtils.isEmpty(et_name.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@User_Model_Student,
                        "Please enter name.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_register_email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@User_Model_Student,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(et_department.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@User_Model_Student,
                        "Please enter et_department.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_roll_number.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@User_Model_Student,
                        "Confirm Roll number.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_student_id_number.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@User_Model_Student,
                        "Confirm Student_id_number.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_year.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@User_Model_Student,
                        "Confirm Year.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val name: String = et_register_name.text.toString().trim { it <= ' ' }
                    val email: String = et_register_email.text.toString().trim { it <= ' ' }
                    val password: String = et_register_password.text.toString().trim { it <= ' ' }
                    val department: String = et_department.text.toString().trim { it <= ' ' }
                    val roll_number: String = et_roll_number.text.toString().trim { it <= ' ' }
                    val student_id_number: String = et_student_id_number.text.toString().trim { it <= ' ' }
                    val year: String = et_year.text.toString().trim { it <= ' ' }

                    // Create an instance and create a register a user with email and password.
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->

                                // If the registration is successfully done
                                if (task.isSuccessful) {

                                    // Firebase registered user
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(
                                        this@User_Model_Student,
                                        "You have updated your profile.",
                                        Toast.LENGTH_SHORT
                                    ).show()


                                    val intent =
                                        Intent(this@User_Model_Student, User_Model_Student::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("name", name)
                                    intent.putExtra("user_id", firebaseUser.uid)
                                    intent.putExtra("email_id", email)
                                    intent.putExtra("department", department)
                                    intent.putExtra("roll_number", roll_number)
                                    intent.putExtra("student_id_number", student_id_number)
                                    intent.putExtra("year", year)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    // If the registering is not successful then show error message.
                                    Toast.makeText(
                                        this@User_Model_Student,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                    fun userRegisteredSuccess() {

                        Toast.makeText(
                            this@User_Model_Student,
                            "You have updated your profile.",
                            Toast.LENGTH_SHORT
                        ).show()


                        /**
                         * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
                         * and send him to Intro Screen for Sign-In
                         */
                        FirebaseAuth.getInstance().signOut()
                        // Finish the Sign-Up Screen
                        finish()
                    }
                }
            }
        }
            // Profile login from app.
            FirebaseAuth.getInstance().currentUser

            startActivity(Intent(this@User_Model_Student, ProfilePage::class.java))
            finish()


            // END
        }


    /*val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address
            val name = user.displayName
            val email = user.email



            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid
        }

         */
    }




