package com.cnam.jmoney;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

public class Project
{
	private SQLiteDatabase db;
	// table fields
	public static class T {
		public static final String NAME             = "mny_projects";
		public static final String COL_ID           = "prj_id";
		public static final String COL_NAME         = "prj_name";
	}
	public Project(SQLiteDatabase db) {
		this.db = db;
	}
	
	public void defaut() {
		this.add("Default");
		this.add("Holiday");
		this.add("Wedding");
		this.add("Travel");
		this.add("Shopping");
	}
	
	public long add(String name){
		ContentValues o = new ContentValues();
		o.put(T.COL_NAME, name);
		return db.insert(T.NAME, null, o);
	}
	public boolean edit(int id,String name){
		ContentValues o = new ContentValues();
		o.put(T.COL_NAME, name);
		return db.update(T.NAME, o,T.COL_ID + "=" + id, null) > 0;
	}
	public boolean delete(long id) 
    {
        return db.delete(T.NAME, T.COL_ID + "=" + id, null) > 0;
    }
	public Cursor getAll() 
    {
        return db.query(T.NAME, new String[] {
        		T.COL_ID,T.COL_NAME
        		}, 
                null,null,null,null,null);
    }
	public Cursor get(long id) throws SQLException 
    {
        Cursor mCursor =db.query(T.NAME, new String[] {
        		T.COL_ID,T.COL_NAME
                }, 
                T.COL_ID + "=" + id,
                null,null,null,null,null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
	
	public int getProjectIDFromName(String name) throws SQLException 
    {
        Cursor mCursor = db.query(T.NAME, new String[] {
        		T.COL_ID,T.COL_NAME
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
