package com.example.sensores

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.abs

class Sensor2Activity : AppCompatActivity(), SensorEventListener {

    private lateinit var tvValores : TextView
    private lateinit var tvEstado : TextView
    private lateinit var tvTitulo : TextView
    private lateinit var imgGiro : ImageView
    private lateinit var mainLayout : ConstraintLayout

    // Objeto que permite ocupar cualquier sensor
    private lateinit var sensorManager: SensorManager

    // Atributo que permite ocupar un sensor en particular
    private var sensorGiroscopio : Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sensor2)

        tvTitulo = findViewById(R.id.tvTitulo)
        tvEstado = findViewById(R.id.tvEstado)
        tvValores = findViewById(R.id.tvValores)
        imgGiro = findViewById(R.id.imgGiro)
        mainLayout = findViewById(R.id.mainGiroscopio)

        // Permitir el uso del sensor que se va ocupar
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // Ocupar el sensor de Giroscopio
        sensorGiroscopio = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        // Verificar si el sensor existe
        if(sensorGiroscopio == null){
            tvEstado.text = "No hay sensor de giroscopio :("
        }

    }

    // Metodos Obligatorios de los Sensores

    // Metodo para detectar cambios en el sensor
    override fun onSensorChanged(event: SensorEvent?) {

        // Evaluar si el sensor seleccionado es de Giroscopio
        if(event?.sensor?.type == Sensor.TYPE_GYROSCOPE){

            // Variables que guardan los valores del sensor en los ejes X, Y, Z
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            // Imprimir los valores del sensor en pantalla
            tvValores.text = "X: ${String.format("%.2f", x)} rad/s\nY: ${String.format("%.2f", y)} rad/s\nZ: ${String.format("%.2f", z)} rad/s"

            // Evaluar los giros en los tres ejes
            if (abs(x) >= 2) {
                // Giro fuerte en X (Fondo VERDE)
                tvEstado.text = "Giro fuerte en el eje X"
                mainLayout.setBackgroundColor(Color.GREEN)
                imgGiro.setImageResource(R.drawable.x)
                actualizarColorTexto(Color.BLACK)

            } else if (abs(y) >= 2) {
                // Giro fuerte en Y (Fondo NARANJA)
                tvEstado.text = "Giro fuerte en el eje Y"
                mainLayout.setBackgroundColor(Color.parseColor("#FF9800"))
                imgGiro.setImageResource(R.drawable.y)
                actualizarColorTexto(Color.WHITE)

            } else if (abs(z) >= 2) {
                // Giro fuerte en Z (Fondo AZUL)
                tvEstado.text = "Giro fuerte en el eje Z"
                mainLayout.setBackgroundColor(Color.BLUE)
                imgGiro.setImageResource(R.drawable.z)
                actualizarColorTexto(Color.WHITE)

            } else {
                // Ningún movimiento detectado (Fondo BLANCO)
                tvEstado.text = "Ningún movimiento detectado"
                mainLayout.setBackgroundColor(Color.WHITE)
                imgGiro.setImageResource(R.drawable.sin_movimiento)
                actualizarColorTexto(Color.BLACK)
            }
        }
    }

    // Metodo auxiliar para cambiar el color de los textos según el color de fondo
    private fun actualizarColorTexto(color: Int) {
        tvTitulo.setTextColor(color)
        tvValores.setTextColor(color)
        tvEstado.setTextColor(color)
    }

    // Metodo para verificar los cambios en valores matematicos
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    // Metodo para la deteccion del evento
    override fun onResume() {
        super.onResume()

        // Si la aplicacion se quita, entonces se deja de usar el sensor
        sensorGiroscopio?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }
}