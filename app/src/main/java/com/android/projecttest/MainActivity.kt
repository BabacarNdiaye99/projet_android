package com.android.projecttest

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.projecttest.db.pharmacieDataBase

class MainActivity : AppCompatActivity() {

    //Creation de la variable sharedPreferences
    lateinit var sharedPreferences: SharedPreferences
    //Declaration de la variable pour la base de donnee
    lateinit var db:pharmacieDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //faire le couleur de l'action barre
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.gris)))


        //Initialisation de la variable sharedPreferences
        sharedPreferences=this.getSharedPreferences("app_state", Context.MODE_PRIVATE)
        val isAuthentificated=sharedPreferences.getBoolean("is_authentificated",false)
        val emailShared=sharedPreferences.getString("email","")
        if(isAuthentificated){
            Intent(this,HomeActivity::class.java).also{
                it.putExtra("email",emailShared)
                startActivity(it)
            }
        }


        //Initialisation de la variable db
        db=pharmacieDataBase(this)

        //Recuperation des donnees
        val email=findViewById<EditText>(R.id.email)
        val password=findViewById<EditText>(R.id.password)
        val connect=findViewById<Button>(R.id.connect)
        val tvRegister=findViewById<TextView>(R.id.tvRegister)
        val error=findViewById<TextView>(R.id.error)

        //Ecouteur de clic sur connect
        connect.setOnClickListener {
            error.visibility= View.GONE
            val txtEmail=email.text.toString()
            val txtPassword=password.text.toString()
            if(txtEmail.trim().isEmpty() ||txtPassword.trim().isEmpty() ){
                error.text="Vous devez remplir tous les champs"
                error.visibility= View.VISIBLE

            }else{
                //val correctEmail="baba@gmail.com"
                //val correctPassword="baba123"
                val user=db.findUser(txtEmail,txtPassword)
                if(user!=null){
                    email.setText("")
                    password.setText("")

                    //Enregistrement des sharedPreferences le Boolean isAuthentificated
                    val editor=sharedPreferences.edit()
                    editor.putBoolean("is_authentificated",true)
                    editor.putString("email",txtEmail)
                    editor.apply()


                    //declaration de Intent explicit
                    Intent(this,HomeActivity::class.java).also{
                        startActivity(it)

                    }
                }else{
                    error.text="Email ou Mot de passe incorrect"
                    error.visibility= View.VISIBLE                }
            }
        }

        //Ecouteur de clic sur crrer un compte
        tvRegister.setOnClickListener{
            Intent(this,RegisterActivity::class.java).also{
                startActivity(it)
            }
        }

    }
}