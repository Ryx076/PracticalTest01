package ro.pub.cs.systems.eim.PracticalTest01

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Colocviu1_1MainActivity : AppCompatActivity() {

    private lateinit var input1: EditText
    private lateinit var input2: EditText

    private var leftNumber = 0
    private var rightNumber = 0

    private val intentFilter = IntentFilter()

    private val messageBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                Log.d("RECEIVER", it.action.toString())
                Log.d("RECEIVER", it.getStringExtra("message").toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_colocviul1_1_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        input1 = findViewById(R.id.input1)
        input2 = findViewById(R.id.input2)
        input1.setText("0")
        input2.setText("0")
        val pressMeButton = findViewById<Button>(R.id.press_me_button)
        pressMeButton.setOnClickListener {
            leftNumber++;
            input1.setText(leftNumber.toString())
            if (leftNumber + rightNumber == 4) {
                val intent = Intent(applicationContext, Colocviu1_1Service::class.java).apply {
                    putExtra("total_clicks", rightNumber+leftNumber)
                }
                applicationContext.startService(intent)
            }
        }

        val pressMeToo = findViewById<Button>(R.id.press_me_too_button)
        pressMeToo.setOnClickListener {

            rightNumber++;
            input2.setText(rightNumber.toString())

            if (leftNumber + rightNumber == 4) {
                val intent = Intent(applicationContext, Colocviu1_1Service::class.java).apply {
                    putExtra("total_clicks", rightNumber+leftNumber)
                }
                applicationContext.startService(intent)
            }
        }

        val activityResultsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "The activity returned with result OK", Toast.LENGTH_LONG).show()
            }
            else if (result.resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "The activity returned with result CANCELED", Toast.LENGTH_LONG).show()
                leftNumber = 0;
                rightNumber = 0;
                input1.setText("0")
                input2.setText("0")
            }
        }

        val navigateToSecondaryActivityButton = findViewById<Button>(R.id.navigate_to_second_activity)
        navigateToSecondaryActivityButton.setOnClickListener {
            val intent = Intent(this, Colocviu1_1SecondaryActivity::class.java)
            intent.putExtra("leftNumber", leftNumber)
            intent.putExtra("rightNumber", rightNumber)
            activityResultsLauncher.launch(intent)
        }

        intentFilter.addAction("com.eim.BROADCAST_MESSAGE");
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        //registerReceiver(messageBroadcastReceiver, intentFilter, RECEIVER_NOT_EXPORTED)
        registerReceiver(messageBroadcastReceiver, intentFilter, RECEIVER_EXPORTED)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("leftNumber", leftNumber)
        outState.putInt("rightNumber", rightNumber)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.containsKey("leftNumber") && savedInstanceState.containsKey("rightNumber")) {
            input1.setText(savedInstanceState.getInt("leftNumber").toString())
            input2.setText(savedInstanceState.getInt("rightNumber").toString())
            leftNumber = savedInstanceState.getInt("leftNumber")
            rightNumber = savedInstanceState.getInt("rightNumber")
        }
    }

}