package com.example.mindNest.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = DBHandler.TABLE_NAME)
data class  NoteEntity(

    @PrimaryKey(autoGenerate = true) val id:Int = 0,

    @ColumnInfo var title:String,

    @ColumnInfo val description:String,

    @ColumnInfo val date:String,

    @ColumnInfo val image :String?,

    @ColumnInfo val voice: String?,

    @ColumnInfo val category: String,


    @ColumnInfo val BackgroundImage: Int?





)
