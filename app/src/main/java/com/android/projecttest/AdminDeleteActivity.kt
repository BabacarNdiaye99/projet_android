package com.android.projecttest

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AdminDeleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_delete)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //faire le couleur de l'action barre
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.gris)))

        val textPrenom = findViewById<EditText>(R.id.TextPrenom)
        val textIdentifiant = findViewById<EditText>(R.id.TextIdentifiant)
        val buttonEntrer = findViewById<Button>(R.id.Entrer)

        buttonEntrer.setOnClickListener {
            val prenom = textPrenom.text.toString()
            val identifiant = textIdentifiant.text.toString()

            if (prenom.isEmpty() || identifiant.isEmpty()) {
                Toast.makeText(this, "Remplissage des champs obligatoires.", Toast.LENGTH_SHORT).show()
            } else {
                textPrenom.setText("")
                textIdentifiant.setText("")

                val isBabacar = prenom == "babacar" && identifiant == "2024"
                val isAlioune = prenom == "alioune" && identifiant == "2023"

                if (isBabacar || isAlioune) {
                    val intent = Intent(this, DeletePostActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Identifiant ou prénom incorrect.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}