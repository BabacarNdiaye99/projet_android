package com.android.projecttest.data


data class Post(
    val titre:String,
    val description:String,
    val image:ByteArray,
    val phoneNumber:String,
    val location:String
){
    var id:Int=-1
    constructor(id:Int,titre:String,description:String,image:ByteArray,phoneNumber:String,location:String):this(titre,description,image,phoneNumber,location){
        this.id=id
    }
}

