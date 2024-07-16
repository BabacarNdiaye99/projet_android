package com.android.projecttest

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import com.android.projecttest.data.Post

class PostsAdapter(
    var mContext: Context,
    var resource: Int,
    var values: ArrayList<Post>
) : ArrayAdapter<Post>(mContext, resource, values) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var post = values[position]
        val itemView = LayoutInflater.from(mContext).inflate(resource, parent, false)

        val tvTitre = itemView.findViewById<TextView>(R.id.tvTitre)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        val imagePost = itemView.findViewById<ImageView>(R.id.ImagePost)
        val imageShowPurpup = itemView.findViewById<ImageView>(R.id.imageShowPurpup)

        tvTitre.text = post.titre
        tvDescription.text = post.description
        //conversion de l'image en ByteArray
        val bitmap=getBitmap(post.image)
        //imagePost.setImageResource(post.image)
        imagePost.setImageBitmap(bitmap)
        // Ajoutez un écouteur de clic sur l'icône pour afficher le menu contextuel
        imageShowPurpup.setOnClickListener {
            val popupMenu = PopupMenu(mContext, imageShowPurpup)
            popupMenu.menuInflater.inflate(R.menu.list_popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.itemInduc -> {
                        val location = values[position].location
                        openMaps(mContext, location)
                    }
                    R.id.itemNum -> {
                        val phoneNumber = values[position].phoneNumber
                        openWhatsAppWithNumber(mContext, phoneNumber)
                    }
                }
                true
            }
            popupMenu.show()
        }

        return itemView
    }
    //Creation de la fonction getBitmap()
    fun getBitmap(byteArray:ByteArray): Bitmap {
        val bitmap=BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
        return bitmap

    }
}

private fun openWhatsAppWithNumber(mContext: Context, number: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$number")
        mContext.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(mContext, "WhatsApp n'est pas installé sur votre appareil", Toast.LENGTH_SHORT).show()
    }
}

private fun openMaps(mContext: Context, location: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$location")
        mContext.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(mContext, "Impossible d'ouvrir Google Maps", Toast.LENGTH_SHORT).show()
    }
}

