package com.cnam.jmoney;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

public class Setting
{
	private SQLiteDatabase db;
	// table fields
	public static class T {
		public static final String NAME             = "mny_settings";
		public static final String COL_ID           = "set_id";
		public static final String COL_KEY          = "set_key";
		public static final String COL_VALUE        = "set_value";
	}
	public Setting(SQLiteDatabase db) {
		this.db = db;
	}
	
	public void defaut() {
		this.add("currency", "Lebanese");
	}
	
	public long add(String key, String value){
		ContentValues o = new ContentValues();
		o.put(T.COL_KEY, key);
		o.put(T.COL_VALUE, value);
		return db.insert(T.NAME, null, o);
	}
	public boolean edit(String key, String value){
		ContentValues o = new ContentValues();
		o.put(T.COL_VALUE, value);
		return db.update(T.NAME, o,T.COL_KEY + "=\"" + key+"\"", null) > 0;
	}
	public boolean delete(long id) 
    {
        return db.delete(T.NAME, T.COL_ID + "=" + id, null) > 0;
    }
	public Cursor getAll() 
    {
        return db.query(T.NAME, new String[] {
        		T.COL_ID,T.COL_KEY,T.COL_VALUE
        		}, 
                null,null,null,null,null);
    }
	public Cursor get(long id) throws SQLException 
    {
        Cursor mCursor =db.query(T.NAME, new String[] {
        		T.COL_ID,T.COL_KEY,T.COL_VALUE
                }, 
                T.COL_ID + "=" + id,
                null,null,null,null,null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
	public String getValueByKey(String key) throws SQLException 
    {
        String value = "";
		Cursor mCursor =db.query(T.NAME, new String[] {
        		T.COL_ID,T.COL_KEY,T.COL_VALUE
                }, 
                T.COL_KEY + "= \"" + key+"\"",
                null,null,null,null,null);
        if (mCursor != null) {
            mCursor.moveToFirst();
            value = mCursor.getString(mCursor.getColumnIndex(T.COL_VALUE));
        }
        return value;
    }
	
	public List<String> getAllCurrencyLabels(){
        List<String> labels = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Currency.T.NAME;
 
        Cursor cursor = this.db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(cursor.getColumnIndex(Currency.T.COL_NAME)));
            } while (cursor.moveToNext());
        }
 
        // closing connection
        cursor.close();
 
        // returning lables
        return labels;
    }
}
