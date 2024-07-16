package com.android.projecttest

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.projecttest.data.Post
import com.android.projecttest.db.pharmacieDataBase

class HomeActivity : AppCompatActivity() {
    var postArray = ArrayList<Post>()
    lateinit var listPosts: ListView
    lateinit var adapter: PostsAdapter
    lateinit var db:pharmacieDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Récupération de la liste
        db=pharmacieDataBase(this)

        listPosts = findViewById(R.id.ListPosts)
        postArray=db.findPosts()

        adapter = PostsAdapter(this, R.layout.item_post, postArray)
        listPosts.adapter = adapter

        // Ajout d'un écouteur de clic sur la liste
        listPosts.setOnItemClickListener { _, _, position, _ ->
            val checkerPost = postArray[position]
            Intent(this, postDetailActivity::class.java).also {
                it.putExtra("titre", checkerPost.titre)
                startActivity(it)
            }
        }
    }

    // Création du menu optionnel
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Sélection de l'item du menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemAdd -> startActivity(Intent(this, AdminActivity::class.java))
            R.id.itemDelete -> startActivity(Intent(this, AdminDeleteActivity::class.java))
            R.id.itemModif -> startActivity(Intent(this, AdminModifActivity::class.java))
            R.id.itemConfig -> Toast.makeText(this, "Configuration", Toast.LENGTH_SHORT).show()
            R.id.itemLogout -> showLogoutConfirmDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    // Fonction de confirmation de la déconnexion
    private fun showLogoutConfirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation!")
        builder.setMessage("Êtes-vous sûr de vouloir quitter l'application?")
        builder.setPositiveButton("Oui") { dialogInterface: DialogInterface, id: Int ->
            val editor = getSharedPreferences("app_state", Context.MODE_PRIVATE).edit()
            editor.remove("is_authentificated")
            editor.apply()
            finish()
        }
        builder.setNegativeButton("Non") { dialogInterface, id: Int ->
            dialogInterface.dismiss()
        }
        builder.setNeutralButton("Annuler") { dialogInterface, id: Int ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}
