package com.example.capstoneproject

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;






class Chat : AppCompatActivity() {
    private var mDatabase: DatabaseReference? = null
    var layout: LinearLayout? = null
    var layout_2: RelativeLayout? = null
    var sendButton: ImageView? = null
    var messageArea: EditText? = null
    var scrollView: ScrollView? = null
    var reference1: Firebase? = null
    var reference2: Firebase? = null
    var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var currentDateandTime = sdf.format(Date())
    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        mDatabase = FirebaseDatabase.getInstance().reference
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        layout = findViewById<View>(R.id.layout1) as LinearLayout
        sendButton = findViewById<View>(R.id.sendButton) as ImageView
        messageArea = findViewById<View>(R.id.messageArea) as EditText
        scrollView = findViewById<View>(R.id.scrollView) as ScrollView
        Firebase.setAndroidContext(this)
        //reference1 = new Firebase("https://mycapstoneprojecta-default-rtdb.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference1 =
            Firebase("https://mycapstoneprojecta-default-rtdb.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith)
        //reference2 = new Firebase("https://mycapstoneprojecta-default-rtdb.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);
        reference2 =
            Firebase("https://mycapstoneprojecta-default-rtdb.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username)
        sendButton!!.setOnClickListener {
            val messageText = messageArea!!.text.toString()
            if (messageText != "") {
                val map: MutableMap<String, String> =
                    HashMap()
                map["message"] = messageText
                map["user"] = UserDetails.username
                map["msg_date"] = currentDateandTime
                map["msg_viwe_date"] = " "
                reference1!!.push().setValue(map)
                reference2!!.push().setValue(map)
                messageArea!!.setText("")
            }
        }
        reference1!!.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String) {
                val map = dataSnapshot.getValue(Map::class.java) as Map<String, String>

                val msg_date = map["msg_date"].toString()
                val message = map["message"].toString()
                val userName = map["user"].toString()
                if (userName == UserDetails.username) {
                    addMessageBox("You:-\n$msg_date-\n$message", 1)
                } else {
                    addMessageBox(
                        """
                            ${UserDetails.chatWith}:-
                            $msg_date-
                            $message
                            """.trimIndent(), 2
                    )
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String) {}
            override fun onCancelled(firebaseError: FirebaseError) {}
        })
    }

    fun addMessageBox(message: String?, type: Int) {
        val textView = TextView(this@Chat)
        textView.text = message
        val lp2 = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        lp2.weight = 1.0f
        if (type == 1) {
            lp2.gravity = Gravity.LEFT
            textView.setBackgroundResource(R.drawable.rounded_corner1)
        } else {
            lp2.gravity = Gravity.RIGHT
            textView.setBackgroundResource(R.drawable.rounded_corner2)
        }
        textView.layoutParams = lp2
        layout!!.addView(textView)
        scrollView!!.fullScroll(View.FOCUS_DOWN)
    }
}