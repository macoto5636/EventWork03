package sk3a280434.eventWork03

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PostDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase) {

        var sql = ""
        sql += "CREATE TABLE events ("
        sql += "ID INTEGER PRIMARY KEY AUTOINCREMENT"
        sql += ",name TEXT"
        sql += ",user TEXT"
        sql += ",genre_id TEXT"
        sql += ",begin_date TEXT"
        sql += ",end_date TEXT"
        sql += ",place TEXT"
        sql += ",url TEXT"
        sql += ",image_id TEXT"
        sql += ",poster TEXT"
        sql += ",detail TEXT"
        sql += ",favorite INTEGER"
        sql += ")"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //db.execSQL("alter table SampleTable add column deleteFlag integer default 0")
        //onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "posterDB"
    }
}
