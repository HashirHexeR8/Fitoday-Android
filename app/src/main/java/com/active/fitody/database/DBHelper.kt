package com.fiction.securetype.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DBContract.UserWeightInfoTable.CREATE_TABLE_QUERY)
        db.execSQL(DBContract.SkipRopeActivityRecordTable.CREATE_TABLE_QUERY)
        db.execSQL(DBContract.BodyProportionRecordTable.CREATE_TABLE_QUERY)
        db.execSQL(DBContract.UserEntryTable.CREATE_TABLE_QUERY)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Fitody.db"
    }
}