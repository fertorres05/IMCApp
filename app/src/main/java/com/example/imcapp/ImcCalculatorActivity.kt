package com.example.imcapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.internal.ViewUtils.getBackgroundColor
import com.google.android.material.slider.RangeSlider
import java.text.DecimalFormat

class ImcCalculatorActivity : AppCompatActivity() {

    //Definimos los CardView como Clases

    //Sexo
    private lateinit var viewMale:CardView
    private lateinit var viewFemale:CardView

    //Altura
    private lateinit var tvHeight:TextView
    private lateinit var rsHeight:RangeSlider

    //Peso
    private lateinit var tvWeight: TextView
    private lateinit var viewWeight:CardView
    private lateinit var btnSubtractWeightRemove:FloatingActionButton
    private lateinit var btnSubtractWeightAdd:FloatingActionButton

    //Edad
    private lateinit var tvAge: TextView
    private lateinit var viweAge:CardView
    private lateinit var btnSubtractAgeRemove:FloatingActionButton
    private lateinit var btnSubtractAgeAdd:FloatingActionButton

    //Calcular
    private lateinit var btnCalcular:AppCompatButton

    //Variables para almacenar peso y edad

    private var weight: Int = 60
    private var age: Int = 25

    var isMaleSelected:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imc_calculator)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponents()
        initListeners()
        initUI()

        //Indica formato decimal
        rsHeight.addOnChangeListener { _, value, _ ->
            //tvHeight.text = value.toString()
            tvHeight.text = DecimalFormat("#.##").format(value) + " cm"
        }

    }

    //Inicializamos los card view
    private fun initComponents() {

        //Sexo
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)

        //Altura
        tvHeight =findViewById(R.id.tvHeight)
        rsHeight =findViewById(R.id.rsHeight)

        //Peso
        tvWeight = findViewById(R.id.tvWeight)
        viewWeight= findViewById(R.id.viewWeight)
        btnSubtractWeightRemove=findViewById(R.id.btnSubtractWeightRemove)
        btnSubtractWeightAdd= findViewById(R.id.btnSubtractWeightAdd)

        //Edad
        tvAge = findViewById(R.id.tvAge)
        viweAge=findViewById(R.id.viweAge)
        btnSubtractAgeRemove=findViewById(R.id.btnSubtractAgeRemove)
        btnSubtractAgeAdd=findViewById(R.id.btnSubtractAgeAdd)

        //Calcular
        btnCalcular=findViewById(R.id.btnCalcular)

    }



    //Definimos los Listeners
    fun initListeners(){
        viewMale.setOnClickListener {
            isMaleSelected = true
            setGenderColor()
        }
        viewFemale.setOnClickListener {
            isMaleSelected = false
            setGenderColor()
        }
        btnSubtractWeightRemove.setOnClickListener{
            if (weight > 0) {
                weight--
                setWeight()
            }
        }
        btnSubtractWeightAdd.setOnClickListener{
            weight++
            setWeight()
        }

        btnSubtractAgeRemove.setOnClickListener{
            if (age > 0) {
                age--
                setAge()
            }
        }
        btnSubtractAgeAdd.setOnClickListener{
            age++
            setAge()
        }

        btnCalcular.setOnClickListener{
            val imc=calculateIMC()
            navigate2result(imc)
        }

    }

    fun setGenderColor() {
        viewMale.setCardBackgroundColor(getBackGroundColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackGroundColor(!isMaleSelected))
    }


    fun getBackGroundColor(isComponentSelected:Boolean): Int {
        //Comprueba si el componente esta seleccionado
        val colorReference = if(isComponentSelected) {
            R.color.bg_comp_sel
        } else {
            R.color.bg_comp
        }
        return ContextCompat.getColor(this,colorReference)
    }

    // Actualiza el TextView con el valor de el peso actual

    private fun setWeight() {
        tvWeight.text = weight.toString()
    }

    private fun setAge() {
        tvAge.text = age.toString()
    }

    private fun calculateIMC(): Double {
        // Convierte altura de cm a metros
        val heightInMeters = rsHeight.values[0] / 100
        //Lo convierte en decimal y lo eleva a 2 con Mat.pow
        return weight / Math.pow(heightInMeters.toDouble(), 2.0)
    }

    private fun navigate2result(imc: Double){

        val intentGA = Intent(this, ImcResultActivity::class.java)
        intentGA.putExtra("EXTRA_RESULT", imc)
        startActivity(intentGA)
    }

    fun initUI(){
        setGenderColor()
        setWeight() // Inicializa el valor de peso en el TextView
        setAge() // Inicializa el valor de edad en el TextView
    }

}