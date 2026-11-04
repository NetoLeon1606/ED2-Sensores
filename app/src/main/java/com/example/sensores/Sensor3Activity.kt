package com.example.sensores

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.abs

class Sensor3Activity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var sensorMagnetico: Sensor? = null

    private lateinit var layoutPrincipal: LinearLayout
    private lateinit var tvEstado: TextView
    private lateinit var tvValores: TextView
    private lateinit var ivImagen: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sensor3)

        layoutPrincipal = findViewById(R.id.layoutPrincipal)
        tvEstado = findViewById(R.id.tvEstado)
        tvValores = findViewById(R.id.tvValores)
        ivImagen = findViewById(R.id.ivImagen)

        // Inicializar SensorManager y obtener el sensor magnético
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorMagnetico = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        if (sensorMagnetico == null) {
            tvEstado.text = "No hay sensor magnético :("
        }
    }

    override fun onResume() {
        super.onResume()
        sensorMagnetico?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            tvValores.text = "X: ${String.format("%.1f", x)} µT\nY: ${String.format("%.1f", y)} µT\nZ: ${String.format("%.1f", z)} µT"

            val fuerza = maxOf(abs(x), abs(y), abs(z))

            if (fuerza > 100) {
                layoutPrincipal.setBackgroundColor(Color.BLACK)
                tvEstado.text = "Campo magnético fuerte detectado"
                tvEstado.setTextColor(Color.WHITE)
                tvValores.setTextColor(Color.WHITE)
                ivImagen.setImageResource(R.drawable.iman)

            } else if (fuerza >= 50) {
                layoutPrincipal.setBackgroundColor(Color.BLUE)
                tvEstado.text = "Campo magnético débil"
                tvEstado.setTextColor(Color.WHITE)
                tvValores.setTextColor(Color.WHITE)
                ivImagen.setImageResource(R.drawable.iman)

            } else {
                layoutPrincipal.setBackgroundColor(Color.GREEN)
                tvEstado.text = "Campo magnético normal"
                tvEstado.setTextColor(Color.BLACK)
                tvValores.setTextColor(Color.BLACK)
                ivImagen.setImageResource(R.drawable.otra_imagen)
            }
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}