package com.cnam.jmoney;


import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

public class Operation
{
	private SQLiteDatabase db;
	// method type
	public static final int EXPENSE = 1;
	public static final int INCOME = 2;
	// table fields
	public static class T {
		public static final String NAME             = "mny_operations";
		public static final String COL_ID           = "opr_id";
		public static final String COL_VALUE        = "opr_value";
		public static final String COL_DATE         = "opr_date";
		public static final String COL_CATEGORY     = "opr_category";
		public static final String COL_SUBCATEGORY  = "opr_subcategory";
		public static final String COL_ACCOUNT      = "opr_account";
		public static final String COL_PROJECT      = "opr_project";
		public static final String COL_REMARK       = "opr_remark";
		public static final String COL_METHOD       = "opr_method";
	}
	
	public Operation(SQLiteDatabase db){
		this.db = db;
	}
	
	public long add(String value,String date,int category, int subcategory, int account, int project, String remark, int method ){
		ContentValues o = new ContentValues();
		o.put(T.COL_VALUE, value);
		o.put(T.COL_DATE, date);
		o.put(T.COL_CATEGORY, category);
		o.put(T.COL_SUBCATEGORY, subcategory);
		o.put(T.COL_ACCOUNT, account);
		o.put(T.COL_PROJECT, project);
		o.put(T.COL_REMARK, remark);
		o.put(T.COL_METHOD, method);
		return db.insert(T.NAME, null, o);
	}
	public boolean edit(int id,String value,String date,String category, String subcategory, String account, String project, String remark, String method ){
		ContentValues o = new ContentValues();
		o.put(T.COL_VALUE, value);
		o.put(T.COL_DATE, date);
		o.put(T.COL_CATEGORY, category);
		o.put(T.COL_SUBCATEGORY, subcategory);
		o.put(T.COL_ACCOUNT, account);
		o.put(T.COL_PROJECT, project);
		o.put(T.COL_REMARK, remark);
		o.put(T.COL_METHOD, method);
		return db.update(T.NAME, o,T.COL_ID + "=" + id, null) > 0;
	}
	public boolean delete(long id) 
    {
        return db.delete(T.NAME, T.COL_ID + "=" + id, null) > 0;
    }
	public Cursor getAll() 
    {
        return db.query(T.NAME, new String[] {
        		T.COL_ID,T.COL_VALUE,T.COL_DATE,T.COL_CATEGORY,T.COL_SUBCATEGORY,T.COL_ACCOUNT,T.COL_PROJECT,T.COL_REMARK,T.COL_METHOD
        		}, 
                null,null,null,null,null);
    }
	public Cursor get(long id) throws SQLException 
    {
        Cursor mCursor =db.query(T.NAME, new String[] {
        		T.COL_ID,T.COL_VALUE,T.COL_DATE,T.COL_CATEGORY,T.COL_SUBCATEGORY,T.COL_ACCOUNT,T.COL_PROJECT,T.COL_REMARK,T.COL_METHOD
                }, 
                T.COL_ID + "=" + id,
                null,null,null,null,null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
	
	public String getMoneyOfToday(int method){
		String res = "0";
		
		String selectQuery = "SELECT ifnull(SUM(cast("+T.COL_VALUE+" AS REAL)),0) AS total FROM " + T.NAME + " WHERE "+T.COL_METHOD+" = "+method+" ";
		selectQuery += " AND "+T.COL_DATE+" BETWEEN datetime('now', 'start of day') AND datetime('now', 'localtime')";
		
		Cursor cursor = this.db.rawQuery(selectQuery, null);
		
		if (cursor.moveToFirst()) {
			res = cursor.getString(cursor.getColumnIndex("total"));
        }
		
		Setting setting = new Setting(this.db);
		Currency currency = new Currency(this.db);
		
		return res+currency.getSign(setting.getValueByKey("currency"));
	}
	
	public String getMoneyOfWeek(int method){
		String res = "0";
		
		String selectQuery = "SELECT ifnull(SUM(cast("+T.COL_VALUE+" AS REAL)),0) AS total FROM " + T.NAME + " WHERE "+T.COL_METHOD+" = "+method+" ";
		selectQuery += " AND "+T.COL_DATE+" BETWEEN datetime('now', '-6 days') AND datetime('now', 'localtime')";
		
		Cursor cursor = this.db.rawQuery(selectQuery, null);
		
		if (cursor.moveToFirst()) {
			res = cursor.getString(cursor.getColumnIndex("total"));
        }
		
		Setting setting = new Setting(this.db);
		Currency currency = new Currency(this.db);
		
		return res+currency.getSign(setting.getValueByKey("currency"));
	}
	
	public String getMoneyOfMonth(int method){
		String res = "0";
		
		String selectQuery = "SELECT ifnull(SUM(cast("+T.COL_VALUE+" AS REAL)),0) AS total FROM " + T.NAME + " WHERE "+T.COL_METHOD+" = "+method+" ";
		selectQuery += " AND "+T.COL_DATE+" BETWEEN datetime('now', 'start of month') AND datetime('now', 'localtime')";
		
		Cursor cursor = this.db.rawQuery(selectQuery, null);
		
		if (cursor.moveToFirst()) {
			res = cursor.getString(cursor.getColumnIndex("total"));
        }
		
		Setting setting = new Setting(this.db);
		Currency currency = new Currency(this.db);
		
		return res+currency.getSign(setting.getValueByKey("currency"));
	}
	
	public String getMoneyOfYear(int method){
		String res = "0";
		
		String selectQuery = "SELECT ifnull(SUM(cast("+T.COL_VALUE+" AS REAL)),0) AS total FROM " + T.NAME + " WHERE "+T.COL_METHOD+" = "+method+" ";
		selectQuery += " AND "+T.COL_DATE+" BETWEEN datetime('now', 'start of year') AND datetime('now', 'localtime')";
		
		Cursor cursor = this.db.rawQuery(selectQuery, null);
		
		if (cursor.moveToFirst()) {
			res = cursor.getString(cursor.getColumnIndex("total"));
        }
		
		Setting setting = new Setting(this.db);
		Currency currency = new Currency(this.db);
		
		return res+currency.getSign(setting.getValueByKey("currency"));
	}
	
	public String getMoneyTotal(int method){
		String res = "0";
		
		String selectQuery = "SELECT ifnull(SUM(cast("+T.COL_VALUE+" AS REAL)),0) AS total FROM " + T.NAME + " WHERE "+T.COL_METHOD+" = "+method+" ";
		
		Cursor cursor = this.db.rawQuery(selectQuery, null);
		
		if (cursor.moveToFirst()) {
			res = cursor.getString(cursor.getColumnIndex("total"));
        }
		
		Setting setting = new Setting(this.db);
		Currency currency = new Currency(this.db);
		
		return res+currency.getSign(setting.getValueByKey("currency"));
	}
	
	public double getResult(){
		double res = 0;
		
		String sql_e = "SELECT ifnull(SUM(cast("+T.COL_VALUE+" AS REAL)),0) AS total FROM " + T.NAME + " WHERE "+T.COL_METHOD+" = "+Category.EXPENSE+" ";
		Cursor expense = this.db.rawQuery(sql_e, null);
		
		String sql_i = "SELECT ifnull(SUM(cast("+T.COL_VALUE+" AS REAL)),0) AS total FROM " + T.NAME + " WHERE "+T.COL_METHOD+" = "+Category.INCOME+" ";
		Cursor income = this.db.rawQuery(sql_i, null);
		
		if (expense.moveToFirst() && income.moveToFirst()) {
			res = Double.parseDouble(income.getString(income.getColumnIndex("total"))) - Double.parseDouble(expense.getString(expense.getColumnIndex("total")));
        }
		
		return res;
	}
	
	public Cursor getAllOperation(){
		Cursor cursor;
		
		Setting setting = new Setting(this.db);
		Currency currency = new Currency(this.db);
		
		String sql = "";
		sql += " SELECT o."+T.COL_ID+" AS _id, (\"Money : \"||o."+T.COL_VALUE+" || \""+currency.getSign(setting.getValueByKey("currency"))+"\") AS money, (\"Category : \"||c."+Category.T.COL_NAME+") AS category ";
		sql += ", (\"Sub Category : \"||sc."+Category.T.COL_NAME+") AS subcategory, (\"Account : \"||a."+Account.T.COL_NAME+") AS account ";
		sql += ", (\"Project : \"||p."+Project.T.COL_NAME+") AS project";
		sql += ", CASE WHEN "+T.COL_METHOD+" = "+Category.EXPENSE+" THEN 'Type : Expense' ELSE 'Type : Income' END AS method ";
		sql += ", (\"Date : \" || strftime(\"%d-%m-%Y\","+T.COL_DATE+")) AS date ";
		sql += " FROM ";
		sql += T.NAME+" AS o INNER JOIN "+Category.T.NAME+" AS c ON (o."+T.COL_CATEGORY+" = c."+Category.T.COL_ID+") ";
		sql += " INNER JOIN "+Category.T.NAME+" AS sc ON (o."+T.COL_SUBCATEGORY+" = sc."+Category.T.COL_ID+" )";
		sql += " INNER JOIN "+Account.T.NAME+" AS a ON (o."+T.COL_ACCOUNT+" = a."+Account.T.COL_ID+") ";
		sql += " INNER JOIN "+Project.T.NAME+" AS p ON (o."+T.COL_PROJECT+" = p."+Project.T.COL_ID+")";
		sql += " ORDER BY "+T.COL_ID+" DESC LIMIT 0,100";
		
		cursor = this.db.rawQuery(sql, null);
		
		return cursor;
	}
}

