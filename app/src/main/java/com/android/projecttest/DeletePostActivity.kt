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

class DeletePostActivity : AppCompatActivity() {

    private lateinit var nomPharmacieEditText: EditText
    private lateinit var supprimerButton: Button
    lateinit var db:pharmacieDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_delete_post)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //faire le couleur de l'action barre
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.gris)))

        //Recuperation des donnees
        nomPharmacieEditText = findViewById(R.id.NomPharmacie)
        supprimerButton = findViewById(R.id.Supprimer)

        // Ajouter un écouteur de clic sur le bouton "Supprimer"
        supprimerButton.setOnClickListener{
            // Récupérer le nom de la pharmacie saisi par l'utilisateur
            val nomPharmacie = nomPharmacieEditText.text.toString()

            // Vérifier si le nom de la pharmacie n'est pas vide
            if (nomPharmacie.isNotEmpty()) {
                // Appeler la fonction pour supprimer le post dans la base de données
                val db = pharmacieDataBase(this)
                val postDeleted = db.deletePost(nomPharmacie)
                db.close()

                // Vérifier si la suppression a réussi
                if (postDeleted) {
                    Toast.makeText(this, "Pharmacie a été bien supprime ", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "La suppression est a échoué", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Remplissage du champ est obligatoire", Toast.LENGTH_SHORT).show()
            }
        }
        }
    }
