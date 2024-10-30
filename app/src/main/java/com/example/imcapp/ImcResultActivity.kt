package com.example.imcapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat

class ImcResultActivity : AppCompatActivity() {

    private lateinit var composicionCorporal:TextView
    private lateinit var resultImc:TextView
    private lateinit var descripcion:TextView
    private lateinit var btnRecalcular:TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imc_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        initComponents()
        initListeners()
        initUI()


    }

    private fun initComponents(){
        composicionCorporal=findViewById(R.id.composicionCorporal)
        resultImc=findViewById(R.id.resultImc)
        descripcion=findViewById(R.id.descripcion)
        btnRecalcular=findViewById(R.id.btnRecalcular)

    }

    private fun initListeners(){

        btnRecalcular.setOnClickListener {
            val intentGA = Intent(this, ImcCalculatorActivity::class.java)
            startActivity(intentGA)
        }
    }

    private fun initUI(){
        val imc = intent.extras?.getDouble("EXTRA_RESULT") ?:-1.0
        setIMC(imc)
        setcomposicionCorporal(imc)
        setdescripcion(imc)
    }

    private fun setIMC(imc:Double){
        val df = DecimalFormat("#.##")
        resultImc.text = df.format(imc)
    }

    private fun setcomposicionCorporal(imc: Double) {
        composicionCorporal.text=when{
            imc < 18.5-> getString(R.string.comInferior)
            imc in 18.5..24.9-> getString(R.string.comNormal)
            imc in 25.0..29.9-> getString(R.string.comSuperior)
            else -> getString(R.string.comObesidad)
        }
    }

    private fun setdescripcion(imc: Double) {
        descripcion.text=when{
            imc < 18.5-> getString(R.string.comInferior)
            imc in 18.5..24.9-> getString(R.string.comNormal)
            imc in 25.0..29.9-> getString(R.string.comSuperior)
            else -> getString(R.string.comObesidad)
        }
    }

}