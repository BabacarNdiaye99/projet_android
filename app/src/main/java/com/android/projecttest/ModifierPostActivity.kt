package com.android.projecttest

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
import com.android.projecttest.db.pharmacieDataBase

class ModifierPostActivity : AppCompatActivity() {

    private lateinit var champTitle: EditText
    private lateinit var champDescription: EditText
    private lateinit var modifierButton: Button
    private lateinit var db:pharmacieDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_modifier_post)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //faire le couleur de l'action barre
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.gris)))

        // Initialiser la base de données
        db = pharmacieDataBase(this)
        //Recuperation des donnees
        champTitle = findViewById(R.id.champTitle)
        champDescription = findViewById(R.id.champDescription)
        modifierButton = findViewById(R.id.Modifier)

        modifierButton.setOnClickListener{
            val title = champTitle.text.toString().trim()
            val description = champDescription.text.toString().trim()
            if(title.isEmpty() ||description.isEmpty()){
                Toast.makeText(this, "Remplissage des champs est obligatoire", Toast.LENGTH_SHORT).show()
            }else{
                // Mettre à jour la description dans la base de données
                if (db.updatePostDescription(title, description)) {
                    Toast.makeText(this, "La description a été mise à jour avec succès.", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(this, "Erreur lors de la mise à jour de la description.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}