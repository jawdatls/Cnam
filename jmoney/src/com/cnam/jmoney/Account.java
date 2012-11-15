package com.cnam.jmoney;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Account
{
	private SQLiteDatabase db;
	// account type
	public static final int CASH = 1;
	public static final int CASH_HKO_RMB = 2;
	public static final int CREDITCARD = 3;
	public static final int BANKCARD = 4;
	public static final int VALUECARD = 5;
	public static class T {
		public static final String NAME             = "mny_accounts";
		public static final String COL_ID           = "acc_id";
		public static final String COL_NAME         = "acc_name";
		public static final String COL_EXCHANGERATE = "acc_exchange_rate";
		public static final String COL_MEMO         = "acc_memo";
		public static final String COL_TYPE         = "acc_type";
	}
	public Account(SQLiteDatabase db) {
		this.db = db;
	}
	
	public void defaut() {
		this.add("Cash", "1/1", "", CASH);
		this.add("Cash Hko Rmb", "1/2", "", CASH_HKO_RMB);
		this.add("Credit Card", "3/4", "", CREDITCARD);
		this.add("Bank Card", "2/1", "", BANKCARD);
		this.add("Value Card", "3/2", "", VALUECARD);
	}
	
	public long add(String name, String exchangerate, String memo, int type){
		ContentValues o = new ContentValues();
		o.put(T.COL_NAME, name);
		o.put(T.COL_EXCHANGERATE, exchangerate);
		o.put(T.COL_MEMO, memo);
		o.put(T.COL_TYPE, type);
		return db.insert(T.NAME, null, o);
	}
	public boolean edit(int id,String name, String exchangerate, String memo, int type){
		ContentValues o = new ContentValues();
		o.put(T.COL_NAME, name);
		o.put(T.COL_EXCHANGERATE, exchangerate);
		o.put(T.COL_MEMO, memo);
		o.put(T.COL_TYPE, type);
		return db.update(T.NAME, o,T.COL_ID + "=" + id, null) > 0;
	}
	public boolean delete(long id) 
    {
        return db.delete(T.NAME, T.COL_ID + "=" + id, null) > 0;
    }
	public Cursor getAll() 
    {
        return db.query(T.NAME, new String[] {
        		T.COL_ID,T.COL_NAME,T.COL_EXCHANGERATE,T.COL_MEMO,T.COL_TYPE
        		}, 
                null,null,null,null,null);
    }
	public Cursor get(long id) throws SQLException 
    {
        Cursor mCursor =db.query(T.NAME, new String[] {
        		T.COL_ID,T.COL_NAME,T.COL_EXCHANGERATE,T.COL_MEMO,T.COL_TYPE
                }, 
                T.COL_ID + "=" + id,
                null,null,null,null,null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
	
	public int getAccountIDFromName(String name) throws SQLException 
    {
        Cursor mCursor = db.query(T.NAME, new String[] {
        		T.COL_ID,T.COL_NAME,T.COL_EXCHANGERATE,T.COL_MEMO,T.COL_TYPE
                },
                T.COL_NAME + "= \"" + name +"\"",
                null,null,null,null,null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        int id = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(T.COL_ID)));
        return id;
    }
	
	public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();
 
        // Select All Query
        String selectQuery = "SELECT  * FROM " + T.NAME;
 
        Cursor cursor = this.db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
 
        // closing connection
        cursor.close();
 
        // returning lables
        return labels;
    }
	
	public Cursor getAllLabelsCursor(){
		// Select All Query
        String selectQuery = "SELECT  "+T.COL_ID+" AS _id, "+T.COL_NAME+" FROM " + T.NAME;
        Cursor cursor = this.db.rawQuery(selectQuery, null);
        // closing connection
        //cursor.close();
        // returning cursor
        return cursor;
	}
}
