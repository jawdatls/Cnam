package com.cnam.jmoney;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

// Define the version and database file name
private static final String DB_NAME = "jmoney_db.db";
private static final int DB_VERSION = 1;
    
// Use a static class to defined the data structure
// This will come in very handy if you using Agile 
// As your development model
private static class _Account {
	private static final String NAME             = "mny_accounts";
	private static final String COL_ID           = "acc_id";
	private static final String COL_NAME         = "acc_name";
	private static final String COL_EXCHANGERATE = "acc_exchange_rate";
	private static final String COL_MEMO         = "acc_memo";
	private static final String COL_TYPE         = "acc_type";
}
private static class _Category {
	private static final String NAME             = "mny_categories";
	private static final String COL_ID           = "cat_id";
	private static final String COL_NAME         = "cat_name";
	private static final String COL_METHOD       = "cat_method";
	private static final String COL_PARENT       = "cat_parent";
}
private static class _Currency {
	private static final String NAME             = "mny_currencies";
	private static final String COL_ID           = "cur_id";
	private static final String COL_NAME         = "cur_name";
	private static final String COL_SIGN         = "cur_sign";
}
private static class _Operation {
	private static final String NAME             = "mny_operations";
	private static final String COL_ID           = "opr_id";
	private static final String COL_VALUE        = "opr_value";
	private static final String COL_DATE         = "opr_date";
	private static final String COL_CATEGORY     = "opr_category";
	private static final String COL_SUBCATEGORY  = "opr_subcategory";
	private static final String COL_ACCOUNT      = "opr_account";
	private static final String COL_PROJECT      = "opr_project";
	private static final String COL_REMARK       = "opr_remark";
	private static final String COL_METHOD       = "opr_method";
}
private static class _Project {
	private static final String NAME             = "mny_projects";
	private static final String COL_ID           = "prj_id";
	private static final String COL_NAME         = "prj_name";
}
private static class _Setting {
	private static final String NAME             = "mny_settings";
	private static final String COL_ID           = "set_id";
	private static final String COL_KEY          = "set_key";
	private static final String COL_VALUE        = "set_value";
}
private SQLiteDatabase db;

// Constructor to simplify Business logic access to the repository 
public DBHelper(Context context) {
	super(context, DB_NAME, null, DB_VERSION);
    // Android will look for the database defined by DB_NAME
    // And if not found will invoke your onCreate method
	this.db = this.getWritableDatabase();
}

@Override
public void onCreate(SQLiteDatabase db) {
            
    // Android has created the database identified by DB_NAME
    // The new database is passed to you vai the db arg
    // Now it is up to you to create the Schema.
    // This schema creates a very simple user table, in order
    // Store user login credentials
	/// Account
	db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
					_Account.NAME,_Account.COL_ID, _Account.COL_NAME,_Account.COL_EXCHANGERATE,_Account.COL_MEMO,_Account.COL_TYPE));
	/// Category
	db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s INTEGER, %s INTEGER)",
			_Category.NAME,_Category.COL_ID, _Category.COL_NAME,_Category.COL_METHOD,_Category.COL_PARENT));
	/// Currency
	db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT)",
			_Currency.NAME,_Currency.COL_ID, _Currency.COL_NAME,_Currency.COL_SIGN));
	/// Operation
	db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s DATETIME, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s TEXT, %s INTEGER)",
			_Operation.NAME,_Operation.COL_ID,_Operation.COL_VALUE,_Operation.COL_DATE,_Operation.COL_CATEGORY,_Operation.COL_SUBCATEGORY,_Operation.COL_ACCOUNT,_Operation.COL_PROJECT,_Operation.COL_REMARK,_Operation.COL_METHOD));
	/// Project
	db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT)",
			_Project.NAME,_Project.COL_ID, _Project.COL_NAME));
	/// Setting
	db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT)",
			_Setting.NAME,_Setting.COL_ID, _Setting.COL_KEY,_Setting.COL_VALUE));
	
	Category cat = new Category(db);
	cat.defaut();
	
	Account acc = new Account(db);
	acc.defaut();
	
	Project pro = new Project(db);
	pro.defaut();
	
	Currency cur = new Currency(db);
	cur.defaut();
	
	Setting set = new Setting(db);
	set.defaut();

}

//Upgrading database
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // Drop older table if existed
    db.execSQL("DROP TABLE IF EXISTS " + _Account  .NAME);
    db.execSQL("DROP TABLE IF EXISTS " + _Category .NAME);
    db.execSQL("DROP TABLE IF EXISTS " + _Currency .NAME);
    db.execSQL("DROP TABLE IF EXISTS " + _Operation.NAME);
    db.execSQL("DROP TABLE IF EXISTS " + _Project  .NAME);
    db.execSQL("DROP TABLE IF EXISTS " + _Setting  .NAME);

    // Create tables again
    onCreate(db);
}

public SQLiteDatabase getDB(){
	return this.db;
}



}

