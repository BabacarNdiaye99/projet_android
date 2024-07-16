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

class AdminModifActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_modif)
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

                val isDoudou = prenom == "doudou" && identifiant == "2024"
                val isFatima = prenom == "fatima" && identifiant == "2023"
                val isAssane = prenom == "assane" && identifiant == "2025"
                val isRokhaya = prenom == "rokhaya" && identifiant == "2028"

                if (isDoudou || isFatima ||isAssane || isRokhaya) {
                    val intent = Intent(this, ModifierPostActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Identifiant ou prénom incorrect.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}