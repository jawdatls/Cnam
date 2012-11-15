package com.cnam.jmoney;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Category
{
	public static final int EXPENSE = 1;
	public static final int INCOME  = 2;
	private SQLiteDatabase db;
	
	public static class T {
		public static final String NAME             = "mny_categories";
		public static final String COL_ID           = "cat_id";
		public static final String COL_NAME         = "cat_name";
		public static final String COL_METHOD       = "cat_method";
		public static final String COL_PARENT       = "cat_parent";
	}
	
	public Category(SQLiteDatabase db) {
		this.db = db;
	}
	
	public void defaut() {
		 long id; 
		// expense
		id = this.add("Food & Drink", Category.EXPENSE, 0);
			 	this.add("Breakfast", Category.EXPENSE, (int)id);
			 	this.add("Lunch", Category.EXPENSE, (int)id);
			 	this.add("Dinner", Category.EXPENSE, (int)id);
			 	this.add("Tea & Drinks", Category.EXPENSE, (int)id);
			 	this.add("Fruits & Snacks", Category.EXPENSE, (int)id);
	 	id = this.add("Household", Category.EXPENSE, 0);
		 		this.add("Daily Use", Category.EXPENSE, (int)id);
		 		this.add("Power & Water bill", Category.EXPENSE, (int)id);
		 		this.add("Rent & Loban", Category.EXPENSE, (int)id);
	 	id = this.add("Automobile", Category.EXPENSE, 0);
			 	this.add("Public Transportation", Category.EXPENSE, (int)id);
			 	this.add("Taxi", Category.EXPENSE, (int)id);
			 	this.add("Car Gas", Category.EXPENSE, (int)id);
			 	this.add("Train & Air Ticket", Category.EXPENSE, (int)id);
	 	id = this.add("Communications", Category.EXPENSE, 0);
			 	this.add("Communication", Category.EXPENSE, (int)id);
			 	this.add("Mobile", Category.EXPENSE, (int)id);
			 	this.add("Internet", Category.EXPENSE, (int)id);
			 	this.add("Cable Tv", Category.EXPENSE, (int)id);
	 	id = this.add("Entertainment", Category.EXPENSE, 0);
			 	this.add("Sports", Category.EXPENSE, (int)id);
			 	this.add("Friends", Category.EXPENSE, (int)id);
			 	this.add("Entertainment", Category.EXPENSE, (int)id);
			 	this.add("Pets", Category.EXPENSE, (int)id);
			 	this.add("Travel", Category.EXPENSE, (int)id);
			 	this.add("Luxury", Category.EXPENSE, (int)id);
	 	id = this.add("Learning", Category.EXPENSE, 0);
			 	this.add("Book & Mag", Category.EXPENSE, (int)id);
			 	this.add("Education", Category.EXPENSE, (int)id);
			 	this.add("Elearning", Category.EXPENSE, (int)id);
	 	id = this.add("Gifts & Social", Category.EXPENSE, 0);
			 	this.add("Gifts & Social", Category.EXPENSE, (int)id);
			 	this.add("Family", Category.EXPENSE, (int)id);
			 	this.add("Donation", Category.EXPENSE, (int)id);
	 	id = this.add("Medicare", Category.EXPENSE, 0);
			 	this.add("Sick & Medicine", Category.EXPENSE, (int)id);
			 	this.add("Medical Insurance", Category.EXPENSE, (int)id);
			 	this.add("Insurance", Category.EXPENSE, (int)id);
			 	this.add("Health Food", Category.EXPENSE, (int)id);
			 	this.add("Beauty Wellness", Category.EXPENSE, (int)id);
	 	id = this.add("Finance&Investment", Category.EXPENSE, 0);
			 	this.add("Bank Fee", Category.EXPENSE, (int)id);
			 	this.add("Investment", Category.EXPENSE, (int)id);
			 	this.add("Installment", Category.EXPENSE, (int)id);
			 	this.add("Tax Expense", Category.EXPENSE, (int)id);
			 	this.add("Fine", Category.EXPENSE, (int)id);
	 	id = this.add("Others", Category.EXPENSE, 0);
			 	this.add("Other", Category.EXPENSE, (int)id);
			 	this.add("Money Lost", Category.EXPENSE, (int)id);
			 	this.add("Bad Debt", Category.EXPENSE, (int)id);
		// income
	 	id = this.add("Salary Income", Category.INCOME, 0);
			 	this.add("Work Income", Category.INCOME, (int)id);
			 	this.add("Interest Income", Category.INCOME, (int)id);
			 	this.add("Parttime Earnings", Category.INCOME, (int)id);
			 	this.add("Bonus Income", Category.INCOME, (int)id);
	 	id = this.add("Other Income", Category.INCOME, 0);
			 	this.add("Gift Income", Category.INCOME, (int)id);
			 	this.add("Investment Income", Category.INCOME, (int)id);
			 	this.add("Other Income", Category.INCOME, (int)id);
		
		 
	}
	public long add(String name, int method, int parent ){
		ContentValues o = new ContentValues();
		o.put(T.COL_NAME, name);
		o.put(T.COL_METHOD, method);
		o.put(T.COL_PARENT, parent);
		return db.insert(T.NAME, null, o);
	}
	public boolean edit(int id,String name, int method, int parent ){
		ContentValues o = new ContentValues();
		o.put(T.COL_NAME, name);
		o.put(T.COL_METHOD, method);
		o.put(T.COL_PARENT, parent);
		return db.update(T.NAME, o,T.COL_ID + "=" + id, null) > 0;
	}
	public boolean delete(long id) 
    {
        boolean res = false;
        Cursor cur = this.get(id);
        if(cur.moveToFirst()){
	        int prnt = Integer.parseInt(cur.getString(cur.getColumnIndex(T.COL_PARENT)));
	        if(prnt == 0){
	        	Cursor all = this.getAllByParent(id);
	        	if (all.moveToFirst()) {
	                do {
	                    this.delete(Integer.parseInt(all.getString(all.getColumnIndex(T.COL_ID))));
	                } while (all.moveToNext());
	            }
	        }
			res = db.delete(T.NAME, T.COL_ID + "=" + id, null) > 0;
        }
		return res;
    }
	public Cursor getAll() 
    {
        return db.query(T.NAME, new String[] {
        		T.COL_ID,T.COL_NAME,T.COL_METHOD,T.COL_PARENT
        		}, 
                null,null,null,null,null);
    }
	public Cursor getAllByParent(long parent) 
    {
        return db.query(T.NAME, new String[] {
        		T.COL_ID,T.COL_NAME,T.COL_METHOD,T.COL_PARENT
        		},
        		T.COL_PARENT + "=" + parent,
                null,null,null,null,null);
    }
	public Cursor get(long id) throws SQLException 
    {
        Cursor mCursor = db.query(T.NAME, new String[] {
        		T.COL_ID,T.COL_NAME,T.COL_METHOD,T.COL_PARENT
                }, 
                T.COL_ID + "=" + id,
                null,null,null,null,null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
	public int getFirstCategoryID() throws SQLException 
    {
        Cursor mCursor = db.query(T.NAME, new String[] {
        		T.COL_ID,T.COL_NAME,T.COL_METHOD,T.COL_PARENT
                },
                null,null,null,null,null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        int id = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(T.COL_ID)));
        return id;
    }
	public int getCategoryIDFromName(String name) throws SQLException 
    {
        Cursor mCursor = db.query(T.NAME, new String[] {
        		T.COL_ID,T.COL_NAME,T.COL_METHOD,T.COL_PARENT
                },
                T.COL_NAME + "= \"" + name +"\"",
                null,null,null,null,null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        int id = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(T.COL_ID)));
        return id;
    }
	public List<String> getAllLabels(int method, int parent){
        List<String> labels = new ArrayList<String>();
 
        // Select All Query
        String selectQuery = "SELECT  * FROM " + T.NAME + " WHERE "+T.COL_METHOD+" = \""+method+"\" AND "+T.COL_PARENT+"=\""+parent+"\" ";
 
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
	
	public List<String> getAllLabelsRoot(int method,int parent){
        List<String> labels = new ArrayList<String>();
 
        // Select All Query
        String selectQuery = "SELECT  * FROM " + T.NAME + " WHERE "+T.COL_METHOD+" = \""+method+"\" AND "+T.COL_PARENT+"=\""+parent+"\" ";
 
        Cursor cursor = this.db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        labels.add("Root");
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
		// CASE WHEN "+T.COL_METHOD+" = "+Category.EXPENSE+" THEN 'Type : Expense' ELSE 'Type : Income' END
        String selectQuery = "SELECT  "+T.COL_ID+" AS _id, (CASE WHEN "+T.COL_PARENT+" > 0 THEN ('__ ' || "+T.COL_NAME+") ELSE "+T.COL_NAME+" END) AS cat_name FROM " + T.NAME+"";
        Cursor cursor = this.db.rawQuery(selectQuery, null);
        // closing connection
        //cursor.close();
        // returning cursor
        return cursor;
    }
}
