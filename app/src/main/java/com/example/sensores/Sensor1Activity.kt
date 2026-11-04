package com.example.sensores

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Sensor1Activity : AppCompatActivity(), SensorEventListener {

    private lateinit var tvValores : TextView
    private lateinit var tvEstado : TextView
    private lateinit var tvTitulo : TextView
    private lateinit var mainLayout : ConstraintLayout

    // Objeto que permite ocupar cualquier sensor
    private lateinit var sensorManager: SensorManager

    // Atributo que permite ocupar un sensor en particular
    private var sensorLuz : Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sensor1)

        tvTitulo = findViewById(R.id.tvTitulo)
        tvEstado = findViewById(R.id.tvEstado)
        tvValores = findViewById(R.id.tvValores)
        mainLayout = findViewById(R.id.mainLuz)

        // Permitir el uso del sensor que se va ocupar
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // Ocupar el sensor de Luz
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        // Verificar si el sensor existe
        if(sensorLuz == null){
            tvEstado.text = "No hay sensor de luz :("
        }
    }

    // Metodos Obligatorios de los Sensores

    // Metodo para detectar cambios en el sensor
    override fun onSensorChanged(event: SensorEvent?) {

        // Evaluar si el sensor seleccionado es de Luz
        if(event?.sensor?.type == Sensor.TYPE_LIGHT){

            // Variable que guarde los valores del sensor
            var luz = event.values[0]

            // Imprimir el valor del sensor
            tvValores.text = "Luz: $luz lx"

            // Evaluar los niveles de luz para cambiar de Modo Oscuro a Claro
            if (luz < 50){
                // Activar Modo Oscuro (Fondo negro y texto blanco)
                tvEstado.text = "Modo: Oscuro"
                mainLayout.setBackgroundColor(Color.BLACK)
                tvTitulo.setTextColor(Color.WHITE)
                tvValores.setTextColor(Color.WHITE)
                tvEstado.setTextColor(Color.WHITE)

            } else if (luz > 500){
                // Activar Modo Claro (Fondo blanco y texto negro)
                tvEstado.text = "Modo: Claro"
                mainLayout.setBackgroundColor(Color.WHITE)
                tvTitulo.setTextColor(Color.BLACK)
                tvValores.setTextColor(Color.BLACK)
                tvEstado.setTextColor(Color.BLACK)
            }

        }

    }

    // Metodo para verificar los cambios en valores matematicos
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    // Metodo para la deteccion del evento
    override fun onResume() {
        super.onResume()

        // Si la aplicacion se quita, entonces se deja de usar el sensor
        sensorLuz?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }
}