package ro.pub.cs.systems.eim.PracticalTest01

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Colocviu1_1SecondaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colocviu1_1_secondary)

        val sumaDisplay = findViewById<TextView>(R.id.sumaClickuri)
        val input1 = intent.getIntExtra("leftNumber", 0)
        val input2 = intent.getIntExtra("rightNumber", 0)
        val suma = input1 + input2
        sumaDisplay.text = suma.toString()


        val okButton = findViewById<Button>(R.id.register_button)
        okButton.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

        val cancelButton = findViewById<Button>(R.id.cancel_button)
        cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}