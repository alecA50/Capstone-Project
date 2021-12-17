package com.example.capstoneproject.model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.capstoneproject.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class User_Model_Faculty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_model_faculty)

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

}