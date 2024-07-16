package com.android.projecttest



import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.projecttest.data.User
import com.android.projecttest.db.pharmacieDataBase


class RegisterActivity : AppCompatActivity() {
    //declaration de la variable pour la base de donnee
    lateinit var db:pharmacieDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //faire le couleur de l'action barre
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.gris)))

        //Recuperation des variables
        db= pharmacieDataBase(this)
        val editName=findViewById<EditText>(R.id.editName)
        val editEmail=findViewById<EditText>(R.id.editEmail)
        val editPassword=findViewById<EditText>(R.id.editPassword)
        val editConfirmPassword=findViewById<EditText>(R.id.editConfirmPassword)
        val btnSave=findViewById<TextView>(R.id.btnSave)
        val tvError=findViewById<TextView>(R.id.tvError)

        //Ajouter une gestionnaire de clis sur le boutton
        btnSave.setOnClickListener{
            tvError.visibility= View.INVISIBLE
            tvError.text=""

            val name=editName.text.toString()
            val email=editEmail.text.toString()
            val password=editPassword.text.toString()
            val confirmPassword=editConfirmPassword.text.toString()

            if(name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                tvError.text="tout les champs sont obligatoirs"
                tvError.visibility=View.VISIBLE
            }else{
                //verification si password==confirmPassword
                if(password!=confirmPassword){
                    tvError.text="Les mots de passe sont differents"
                    tvError.visibility=View.VISIBLE
                }else{
                    val user= User(name,email,password)
                    val isInserted=db.addUser(user)
                    if(isInserted){
                        Toast.makeText(this, "Votre compte a été créer avec succés", Toast.LENGTH_SHORT).show()
                        //Redirection vers la page principale
                        Intent(this,MainActivity::class.java).also{
                            it.putExtra("email",email)
                            startActivity(it)

                        }
                        finishAfterTransition()

                    }


                }                }
        }
    }
}