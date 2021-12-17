package com.example.capstoneproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.capstoneproject.model.User_Model_Faculty
import com.example.capstoneproject.model.User_Model_Student
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile_page.*



class ProfilePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        btn_User_Model_Faculty.setOnClickListener {
            // Logout from app.
            FirebaseAuth.getInstance().currentUser

            startActivity(Intent(this@ProfilePage, User_Model_Faculty::class.java))
            finish()


            // END
        }
        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in
        } else {
            // No user is signed in
        }

        btn_User_Model_student.setOnClickListener {
            // Logout from app.
            FirebaseAuth.getInstance().currentUser

            startActivity(Intent(this@ProfilePage, User_Model_Student::class.java))
            finish()


            // END
        }
    }
    // END
}
// END

