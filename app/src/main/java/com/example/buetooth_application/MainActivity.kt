package com.example.buetooth_application

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.ACTION_DISCOVERY_STARTED
import android.bluetooth.BluetoothAdapter.STATE_ON
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.ACTION_FOUND
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        bluetoothAdapter!!.startDiscovery()
        val tvDevice: TextView = findViewById(R.id.tvDevice)
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)

        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
        }
        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            val enableDiscovery = Intent(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
            var REQUEST_ENABLE_BT = 0
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            //startActivityForResult(enableDiscovery, STATE_ON)
        }
        if(bluetoothAdapter?.isDiscovering == false){
            val intent = Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))

        }


        /* val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
         pairedDevices?.forEach { device ->
             val deviceName = device.name
             val deviceHardwareAddress = device.address // MAC address
             tvDevice.append("\n $deviceName, $deviceHardwareAddress")
         */

    }
    private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val action: String = intent.action!!
            when(action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)!!
                    val deviceName = device.name
                    val deviceHardwareAddress = device.address // MAC address
                    val tvDevice: TextView = findViewById(R.id.tvDevice)
                    tvDevice.append("\n $deviceName $deviceHardwareAddress")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver)

    }
}