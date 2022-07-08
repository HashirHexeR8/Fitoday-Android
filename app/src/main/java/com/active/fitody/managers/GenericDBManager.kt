package com.active.fitody.managers

import android.content.ContentValues
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.active.fitody.FitodyApplication
import com.active.fitody.database.DatabaseQueryResultSingleRow
import com.fiction.securetype.database.DBHelper

class GenericDBManager {

    private var dbContext: DBHelper? = null
    private var dbInstance: SQLiteDatabase? = null

    init {
        dbContext = DBHelper(FitodyApplication.context())
        dbInstance = dbContext?.writableDatabase
    }

    companion object {

        fun dbContext() : GenericDBManager {
            return GenericDBManager()
        }
    }

    fun insert(tableName: String, contentValues: ContentValues)
    {
        dbInstance?.beginTransaction()
        try {
            val rows = dbInstance?.insert(tableName, null, contentValues)
            dbInstance?.setTransactionSuccessful()
        } catch (exception: SQLException) {
            exception.printStackTrace()
            throw exception
        } finally {
            dbInstance?.endTransaction()
        }
    }

    fun insertOrUpdate(tableName: String, contentValues: ContentValues)
    {
        dbInstance?.beginTransaction()
        try {
            val rows = dbInstance?.insertWithOnConflict(tableName, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE)
            dbInstance?.setTransactionSuccessful()
        } catch (exception: SQLException) {
            exception.printStackTrace()
            throw exception
        } finally {
            dbInstance?.endTransaction()
        }
    }

    fun select(selectQuery: String): ArrayList<DatabaseQueryResultSingleRow> {
        val dataMap: ArrayList<DatabaseQueryResultSingleRow> = ArrayList()
        val cursor = dbInstance?.rawQuery(selectQuery, null)
        while(cursor?.moveToNext() == true) {
            var row = DatabaseQueryResultSingleRow()
            for (column in cursor.columnNames) {
                var data: Any
                val columnIndex = cursor.getColumnIndex(column)
                val dataType = cursor.getType(columnIndex)
                when (dataType) {
                    Cursor.FIELD_TYPE_NULL -> data = ""
                    Cursor.FIELD_TYPE_INTEGER -> data = cursor.getLong(columnIndex)
                    Cursor.FIELD_TYPE_FLOAT -> data = cursor.getFloat(columnIndex)
                    Cursor.FIELD_TYPE_STRING -> data = cursor.getString(columnIndex)
                    else -> data = ""
                }
                row[column] = data
            }
            dataMap.add(row)
        }

        return dataMap
    }
}