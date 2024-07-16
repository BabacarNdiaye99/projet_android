package com.android.projecttest.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.android.projecttest.data.Post
import com.android.projecttest.data.User

import kotlin.collections.ArrayList


class pharmacieDataBase(mContext: Context): SQLiteOpenHelper(
    mContext,
    DB_NAME,
    null,
    DB_VERSION

) {
    override fun onCreate(db: SQLiteDatabase?) {
        //Creation des tables
        val createTableUser="""
            CREATE TABLE users(
            $USER_ID integer primary key,
            $NAME varchar(50),
            $EMAIL varchar(100),
            $PASSWORD varchar(20)
         )
        """.trimIndent()

        //Creation de la table Posts
        val createTablePosts="""
            CREATE TABLE $POSTS_TABLE_NAME(
            $POST_ID integer primary key,
            $TITLE varchar(50),
            $DESCRIPTION TEXT,
            $IMAGE blob,
            $NUMBER String,
            $LOCATION String
            
            
            )
        """.trimIndent()


        //db?.execSQL(createTableUser)
        db?.execSQL(createTablePosts)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //La suppression des anciens tables
        //et la re creation des nouveaux
        //db?.execSQL("DROP TABLE IF EXISTS $USERS_TABLE_NAME ")
        db?.execSQL("DROP TABLE IF EXISTS $POSTS_TABLE_NAME ")
        onCreate(db)

    }


    //Creation de la fonction d'ajout d'un utilisateur
    fun addUser(user: User):Boolean{
        //Insertion d'un nouveau utilisateur sur notre base de donnee
        val db=this.writableDatabase
        val values= ContentValues()
        values.put(NAME,user.name)
        values.put(EMAIL,user.email)
        values.put(PASSWORD,user.password)
        val result=db.insert(USERS_TABLE_NAME,null,values).toInt()

        db.close()

        return result!=-1

    }


    //Creation de la fonction de recherche d'un utilisateur
    fun findUser(email:String,password:String):User?{
        val user:User?=null
        val db=this.readableDatabase
        val selectionArgs=arrayOf(email,password)
        val cursor=db.query(USERS_TABLE_NAME,null,"$EMAIL=?AND $PASSWORD=? ",selectionArgs,null,null,null )
        if(cursor!=null){
            if(cursor.moveToFirst()){
                val id=cursor.getInt(0)
                val name=cursor.getString(1)
                val email=cursor.getString(2)
                //val password=cursor.getString(3)
                //Creation d'un utilisateur
                val user=User(id,name,email,"")
                return user
            }
        }
        db.close()

        return user
    }

    //Fonction d'insertion d'un post
    fun addPost(post: Post):Boolean{
        val db=writableDatabase
        val values=ContentValues();
        values.put(TITLE,post.titre)
        values.put(DESCRIPTION,post.description)
        values.put(IMAGE,post.image)
        values.put(NUMBER,post.phoneNumber)
        values.put(LOCATION,post.location)

        val result=db.insert(POSTS_TABLE_NAME,null,values)
        db.close()
        return result!=-1L
    }

    //Afficher les posts
    fun findPosts():ArrayList<Post>{
        val posts=ArrayList<Post>()
        val db=readableDatabase
        val selectQuery="SELECT * FROM $POSTS_TABLE_NAME"
        val cursor=db.rawQuery(selectQuery,null)
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    val id=cursor.getInt(cursor.getColumnIndexOrThrow(POST_ID))
                    val titre=cursor.getString(cursor.getColumnIndexOrThrow(TITLE))
                    val description=cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION))
                    val image=cursor.getBlob(cursor.getColumnIndexOrThrow(IMAGE))
                    val phoneNumber=cursor.getString(cursor.getColumnIndexOrThrow(NUMBER))
                    val location=cursor.getString(cursor.getColumnIndexOrThrow(LOCATION))
                    val post=Post(titre, description, image, phoneNumber, location)
                    posts.add(post)

                }while (cursor.moveToNext())
            }
        }
        db.close()

        return posts
    }


    //Creation de la fonction pour supprimer un post
    fun deletePost(title: String): Boolean {
        val db = writableDatabase
        val whereClause = "$TITLE = ?"
        val whereArgs = arrayOf(title)
        val result = db.delete(POSTS_TABLE_NAME, whereClause, whereArgs)
        db.close()
        return result > 0
    }


    //Modifier une pharmacie

     fun updatePostDescription(postTitle: String, newDescription: String): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put(DESCRIPTION, newDescription)

        val whereClause = "$TITLE = ?"
        val whereArgs = arrayOf(postTitle)

        val result = db.update(POSTS_TABLE_NAME, values, whereClause, whereArgs)
        db.close()
        return result > 0
    }



    //Creation du companion object
    companion object{
        private val DB_NAME="ph_db"
        private val DB_VERSION=2
        //pour la table users
        private val USERS_TABLE_NAME="users"
        private val USER_ID="id"
        private val NAME="name"
        private val EMAIL="email"
        private val PASSWORD="password"
        //Pour la table posts
        private val POSTS_TABLE_NAME="posts"
        private val POST_ID="id"
        private val TITLE="titre"
        private val DESCRIPTION="description"
        private val IMAGE="image"
        private val NUMBER="phoneNumber"
        private val LOCATION="location"




    }
}