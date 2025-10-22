package com.example.myapplication.ui.ASBC

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri

class MyContentProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.example.myapp.provider"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/data")
        private const val TABLE_NAME = "sync_data"
    }

    private lateinit var dbHelper: SQLiteOpenHelper

    override fun onCreate(): Boolean {
        dbHelper = object : SQLiteOpenHelper(context, "app_db", null, 1) {
            override fun onCreate(db: SQLiteDatabase) {
                db.execSQL("CREATE TABLE $TABLE_NAME (id INTEGER PRIMARY KEY AUTOINCREMENT, message TEXT)")
            }
            override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
        }
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbHelper.writableDatabase
        val id = db.insert(TABLE_NAME, null, values)
        context?.contentResolver?.notifyChange(uri, null)
        return Uri.withAppendedPath(uri, id.toString())
    }

    override fun query(
        uri: Uri, projection: Array<out String>?, selection: String?,
        selectionArgs: Array<out String>?, sortOrder: String?
    ): Cursor {
        val db = dbHelper.readableDatabase
        return db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder)
    }

    override fun getType(uri: Uri): String? = null
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int = 0
}
