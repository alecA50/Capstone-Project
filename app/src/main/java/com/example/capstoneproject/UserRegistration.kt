package com.example.capstoneproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_user_registration.*

class UserRegistration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_user_registration)

        // TODO Step 9: Assign the click event to the register button and perform the functionality.
        // START
        btn_register.setOnClickListener {
            when {
                TextUtils.isEmpty(et_register_name.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@UserRegistration,
                        "Please enter name.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_register_email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@UserRegistration,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(et_register_password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@UserRegistration,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_register_confirm_password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@UserRegistration,
                        "Confirm password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val name: String = et_register_name.text.toString().trim { it <= ' ' }
                    val email: String = et_register_email.text.toString().trim { it <= ' ' }
                    val password: String = et_register_password.text.toString().trim { it <= ' ' }
                    val confirm_password: String = et_register_confirm_password.text.toString().trim { it <= ' ' }

                    // Create an instance and create a register a user with email and password.
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->

                                // If the registration is successfully done
                                if (task.isSuccessful) {

                                    // Firebase registered user
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(
                                        this@UserRegistration,
                                        "You are registered successfully.",
                                        Toast.LENGTH_SHORT
                                    ).show()


                                    val intent =
                                        Intent(this@UserRegistration, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("name", firebaseUser.displayName)
                                    intent.putExtra("user_id", firebaseUser.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    // If the registering is not successful then show error message.
                                    Toast.makeText(
                                        this@UserRegistration,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                    fun userRegisteredSuccess() {

                        Toast.makeText(
                            this@UserRegistration,
                            "You have successfully registered.",
                            Toast.LENGTH_SHORT
                        ).show()


                        FirebaseAuth.getInstance().signOut()
                        // Finish the Sign-Up Screen
                        finish()
                    }
                }
            }
        }

        tv_login.setOnClickListener {

            onBackPressed()
        }

    }

}