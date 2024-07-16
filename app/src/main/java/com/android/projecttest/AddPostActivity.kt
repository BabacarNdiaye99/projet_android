package com.android.projecttest

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.projecttest.data.Post
import com.android.projecttest.db.pharmacieDataBase
import java.io.ByteArrayOutputStream

class AddPostActivity : AppCompatActivity() {
    lateinit var btnSave: Button
    lateinit var editTitle: EditText
    lateinit var editDescription: EditText
    lateinit var imagePost: ImageView
    lateinit var editPhone: EditText
    lateinit var editLocalisation: EditText
    lateinit var db: pharmacieDataBase
    var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_post)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.gris
                )
            )
        )
        db = pharmacieDataBase(this)
        btnSave = findViewById(R.id.Save)
        editTitle = findViewById(R.id.editTitle)
        editDescription = findViewById(R.id.editDescription)
        imagePost = findViewById(R.id.imagePost)
        editPhone = findViewById(R.id.editPhone)
        editLocalisation = findViewById(R.id.editLocalisation)

        val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { data ->
            val uri = data
            val inputStream = contentResolver.openInputStream(uri!!)
            bitmap = BitmapFactory.decodeStream(inputStream)
            imagePost.setImageBitmap(bitmap)
        }

        imagePost.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        btnSave.setOnClickListener {
            val titre = editTitle.text.toString().trim()
            val description = editDescription.text.toString().trim()
            val imagesBlob: ByteArray? = bitmap?.let { getByte(it) }
            val phone = editPhone.text.toString().trim()
            val localisation = editLocalisation.text.toString().trim()

            if (titre.isNotEmpty() && description.isNotEmpty() && imagesBlob != null && phone.isNotEmpty() && localisation.isNotEmpty()) {
                val post = Post(titre, description, imagesBlob, phone, localisation)
                if (db.addPost(post)) {
                    Toast.makeText(this, "Post ajouté avec succès", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Erreur lors de l'ajout du post", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getByte(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }
}
