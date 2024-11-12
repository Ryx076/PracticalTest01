package ro.pub.cs.systems.eim.PracticalTest01

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



class Colocviu1_1Service : Service() {

    private var isTaskScheduled = false
    private var handler = Handler()

    val INSTRUCTION_EXTRA: String = "total_clicks"

    val BROADCAST_ACTION: String = "com.eim.BROADCAST_MESSAGE"

    val BROADCAST_EXTRA_MESSAGE: String = "message"


    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("PracticalTest01Service", "onStartCommand() callback method was invoked")

        if (intent != null && !isTaskScheduled) {
            val instruction = intent.getIntExtra(INSTRUCTION_EXTRA, 0);
            scheduleDelayedBroadcast(instruction.toString())
            isTaskScheduled = true
        }
        return START_NOT_STICKY
    }

    private fun scheduleDelayedBroadcast(instruction: String) {
        handler.postDelayed(Runnable {
            sendInstructionBroadcast(instruction)
            stopSelf()
        }, 5000)
    }

    private fun sendInstructionBroadcast(instruction: String) {
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDateTime: String = sdf.format(Date())

        val message = String.format("DateTime: %s, Instruction: %s", currentDateTime, instruction)

        val broadcastIntent: Intent = Intent(BROADCAST_ACTION)
        broadcastIntent.putExtra(BROADCAST_EXTRA_MESSAGE, message)

        sendBroadcast(broadcastIntent)
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}