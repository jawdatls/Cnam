package com.cnam.jmoney;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

public class Currency
{
	private SQLiteDatabase db;
	// table fields
	public static class T {
		public static final String NAME             = "mny_currencies";
		public static final String COL_ID           = "cur_id";
		public static final String COL_NAME         = "cur_name";
		public static final String COL_SIGN         = "cur_sign";
	}
	
	public Currency(SQLiteDatabase db) {
		this.db = db;
	}
	
	public void defaut() {
		this.add("Lebanese", "LBP");
		this.add("Dollar", "$");
		this.add("Euro", "€");
	}
	
	public long add(String name, String sign){
		ContentValues o = new ContentValues();
		o.put(T.COL_NAME, name);
		o.put(T.COL_SIGN, sign);
		return db.insert(T.NAME, null, o);
	}
	public boolean edit(int id,String name, String sign){
		ContentValues o = new ContentValues();
		o.put(T.COL_NAME, name);
		o.put(T.COL_SIGN, sign);
		return db.update(T.NAME, o,T.COL_ID + "=" + id, null) > 0;
	}
	public boolean delete(long id) 
    {
        return db.delete(T.COL_NAME, T.COL_ID + "=" + id, null) > 0;
    }
	public Cursor getAll() 
    {
        return db.query(T.COL_NAME, new String[] {
        		T.COL_ID,T.COL_NAME,T.COL_SIGN
        		}, 
                null,null,null,null,null);
    }
	public Cursor get(long id) throws SQLException 
    {
        Cursor mCursor =db.query(T.COL_NAME, new String[] {
        		T.COL_ID,T.COL_NAME,T.COL_SIGN
                }, 
                T.COL_ID + "=" + id,
                null,null,null,null,null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
	
	public String getSign(String name){
		String res = "";
		
		String selectQuery = "SELECT "+T.COL_SIGN+" FROM "+T.NAME+" WHERE "+T.COL_NAME+" = \""+name+"\"";
		Cursor cursor = this.db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			res = cursor.getString(cursor.getColumnIndex(T.COL_SIGN));
        }
		
		return res;
	}
}
