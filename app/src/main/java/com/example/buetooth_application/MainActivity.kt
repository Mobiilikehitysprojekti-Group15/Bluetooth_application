package com.example.buetooth_application

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import java.nio.channels.InterruptedByTimeoutException


class MainActivity : AppCompatActivity() {

    //SupresLint for disabling premissin check for bluetothAdapter. Is a part of uppdate
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        val tvDevice: TextView = findViewById(R.id.tvDevice)


        if (bluetoothAdapter == null) {
            val toast = Toast.makeText(applicationContext,
                "Your device does not support bluetooth",
                Toast.LENGTH_LONG)
            toast.show()
        }

        //This commented section is for turning bluetooth on automaticly, but in this demo we don't use it
        //Lets turn bluetooth on if it is turned off
        //if (bluetoothAdapter?.isEnabled == false) {
        //   val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        //    var REQUEST_ENABLE_BT = 0
         //   startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
       // }

        // Button to refresh the view
        val bu_refresh = findViewById(R.id.bu_refresh) as Button

        bu_refresh.setOnClickListener{
            recreate()

        }

        val bu_on = findViewById(R.id.bu_on) as Button

        bu_on.setOnClickListener{
            bluetoothAdapter?.enable()
        }
        val bu_off = findViewById(R.id.bu_off) as Button

        bu_off.setOnClickListener{
            bluetoothAdapter?.disable()
        }

       //Let's print previously paired devices to textView
         val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
         pairedDevices?.forEach { device ->
             val deviceName = device.name
             val deviceHardwareAddress = device.address // MAC address
             tvDevice.append("\n $deviceName, $deviceHardwareAddress")
         }
    }
}
