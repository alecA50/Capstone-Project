package com.example.capstoneproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.capstoneproject.model.User_Model_Faculty_Update
import com.example.capstoneproject.model.User_Model_Student_Update
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_profile_page.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_user_model_faculty.*
import kotlinx.android.synthetic.main.activity_user_model_student.*
import kotlinx.android.synthetic.main.activity_user_model_student_update.*
import kotlinx.android.synthetic.main.activity_user_registration.*


class ProfilePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

                                                                                    // END

        btn_User_Model_Faculty.setOnClickListener {
            // Logout from app.
            FirebaseAuth.getInstance().currentUser

            startActivity(Intent(this@ProfilePage, User_Model_Faculty_Update::class.java))
            finish()


            // END
        }
        btn_User_Model_student.setOnClickListener {
            // Logout from app.
            FirebaseAuth.getInstance().currentUser

            startActivity(Intent(this@ProfilePage, User_Model_Student_Update::class.java))
            finish()


            // END
        }
        btn_menu.setOnClickListener {
            // Profile login from app.
            FirebaseAuth.getInstance().currentUser

            startActivity(Intent(this@ProfilePage, MainActivity::class.java))
            finish()


            // END
        }
    }
    // END
}
// END

