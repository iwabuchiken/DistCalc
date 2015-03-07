package utils;



import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
//import android.view
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/****************************************
 * Copy & pasted from => C:\WORKS\WORKSPACES_ANDROID\ShoppingList\src\shoppinglist\main\DBUtils.java
 ****************************************/
public class DBUtils extends SQLiteOpenHelper{

	/*****************************************************************
	 * Class fields
	 *****************************************************************/
	 // DB name
	String dbName = null;
//	static String dbName = null;
	
	// Activity
	Activity activity;
	
	//
	Context context;

	/*********************************
	 * DB
	 *********************************/
	// Database
	SQLiteDatabase db = null;

	/*****************************************************************
	 * Constructor
	 *****************************************************************/
	public DBUtils(Context context, String dbName) {
		super(context, dbName, null, 1);
		
		// Initialize activity
		this.activity = (Activity) context;
		
		this.context = context;
		
		this.dbName = dbName;
		
	}//public DBUtils(Context context)

	/*******************************************************
	 * Methods
	 *******************************************************/
	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}//public void onCreate(SQLiteDatabase db)

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}

	/****************************************
	 * createTable_generic()
	 * 
	 * <Caller> 
	 * 1. 
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public boolean createTable
	(SQLiteDatabase db, String tableName, 
			String[] columns, String[] types) {
		/*----------------------------
		 * Steps
		 * 1. Table exists?
		 * 2. Build sql
		 * 3. Exec sql
			----------------------------*/
		
		//
//		if (!tableExists(db, tableName)) {
		if (tableExists(db, tableName)) {
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists => " + tableName);
			
			return false;
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		/*----------------------------
		 * 2. Build sql
			----------------------------*/
		//
		StringBuilder sb = new StringBuilder();
		
		sb.append("CREATE TABLE " + tableName + " (");
		sb.append(android.provider.BaseColumns._ID +
							" INTEGER PRIMARY KEY AUTOINCREMENT, ");
		
		// created_at, modified_at
		sb.append("created_at TEXT, modified_at TEXT, ");
		
		int i = 0;
		for (i = 0; i < columns.length - 1; i++) {
			sb.append(columns[i] + " " + types[i] + ", ");
		}//for (int i = 0; i < columns.length - 1; i++)
		
		sb.append(columns[i] + " " + types[i]);
		
		sb.append(");");
		
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "sql => " + sb.toString());
		
		/*----------------------------
		 * 3. Exec sql
			----------------------------*/
		//
		try {
//			db.execSQL(sql);
			db.execSQL(sb.toString());
			
			// Log
			Log.d(this.getClass().getName() + 
					"["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table created => " + tableName);
			
			
			return true;
		} catch (SQLException e) {
			// Log
			Log.e(this.getClass().getName() + 
					"[" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
					"Exception => " + e.toString());
			
			return false;
		}//try
		
	}//public boolean createTable(SQLiteDatabase db, String tableName)

	/******************************
		createTable()
		
		@param columns, types => use non-full version
		@return true => Table created or exists
	 ******************************/
	public boolean createTable
	(Activity actv, String tableName, String[] columns, String[] types)
	{
		/*----------------------------
		 * Steps
		 * 1. Table exists?
		 * 2. Build sql
		 * 3. Exec sql
			----------------------------*/
//		DBUtils dbu = new DBUtils(actv, dbName);
		
		//
		SQLiteDatabase wdb = this.getWritableDatabase();
//		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		//
		//if (!tableExists(db, tableName)) {
		if (tableExists(wdb, tableName)) {
			// Log
			Log.i("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists => " + tableName);
			
			// debug
			String msg_Toast = "Table exists => " + tableName;
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
			
			return true;
//			return false;
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		/*----------------------------
		 * 2. Build sql
			----------------------------*/
		//
		StringBuilder sb = new StringBuilder();
		
		sb.append("CREATE TABLE " + tableName + " (");
		sb.append(android.provider.BaseColumns._ID +
							" INTEGER PRIMARY KEY AUTOINCREMENT, ");
		
		// created_at, modified_at
		sb.append("created_at TEXT, modified_at TEXT, ");
//		sb.append("created_at INTEGER, modified_at INTEGER, ");
		
		int i = 0;
		for (i = 0; i < columns.length - 1; i++) {
			sb.append(columns[i] + " " + types[i] + ", ");
		}//for (int i = 0; i < columns.length - 1; i++)
		
		sb.append(columns[i] + " " + types[i]);
		
		sb.append(");");
		
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "sql => " + sb.toString());
		
		/*----------------------------
		 * 3. Exec sql
			----------------------------*/
		//
		try {
		//	db.execSQL(sql);
			wdb.execSQL(sb.toString());
			
			// Log
			Log.d(this.getClass().getName() + 
					"["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table created => " + tableName);
			
			wdb.close();
			
			return true;
			
		} catch (SQLException e) {
			
			// Log
			Log.e(this.getClass().getName() + 
					"[" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
					"Exception => " + e.toString());
			
			wdb.close();
			
			return false;
			
		}//try

	}//public boolean createTable(SQLiteDatabase db, String tableName)

	/******************************
		createTable()<br>
		1. Columns "created_at" and "modified_at" => auto-inserted
		
		@param columns, types => use non-full version
		@return 
			-1	Table exists<br>
			-2	SQLException<br>
			1	Table created<br>
	 ******************************/
	public static int createTable_static
	(Activity actv, 
			String tableName, String[] columns, String[] types)
	{
		/*----------------------------
		 * Steps
		 * 1. Table exists?
		 * 2. Build sql
		 * 3. Exec sql
			----------------------------*/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		//
//		SQLiteDatabase wdb = this.getWritableDatabase();
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		//
		//if (!tableExists(db, tableName)) {
		if (DBUtils.tableExists(actv, CONS.DB.dbName, tableName)) {
//			if (tableExists(wdb, tableName)) {
			// Log
			Log.i("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists => " + tableName);
			
			// debug
			String msg_Toast = "Table exists => " + tableName;
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
			
			return -1;
//			return false;
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		/*----------------------------
		 * 2. Build sql
			----------------------------*/
		//
		StringBuilder sb = new StringBuilder();
		
		sb.append("CREATE TABLE " + tableName + " (");
		sb.append(android.provider.BaseColumns._ID +
				" INTEGER PRIMARY KEY AUTOINCREMENT, ");
		
		// created_at, modified_at
		sb.append("created_at TEXT, modified_at TEXT, ");
//		sb.append("created_at INTEGER, modified_at INTEGER, ");
		
		int i = 0;
		for (i = 0; i < columns.length - 1; i++) {
			sb.append(columns[i] + " " + types[i] + ", ");
		}//for (int i = 0; i < columns.length - 1; i++)
		
		sb.append(columns[i] + " " + types[i]);
		
		sb.append(");");
		
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "sql => " + sb.toString());
		
		/*----------------------------
		 * 3. Exec sql
			----------------------------*/
		//
		try {
			//	db.execSQL(sql);
			wdb.execSQL(sb.toString());
			
			// Log
			Log.d(actv.getClass().getName() + 
					"["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table created => " + tableName);
			
			wdb.close();
			
			return 1;
			
		} catch (SQLException e) {
			
			// Log
			Log.e(actv.getClass().getName() + 
					"[" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
					"Exception => " + e.toString());
			
			wdb.close();
			
			return -2;
			
		}//try
		
	}//public boolean createTable(SQLiteDatabase db, String tableName)
	
	public boolean tableExists(SQLiteDatabase db, String tableName) {
		// The table exists?
		Cursor cursor = db.rawQuery(
									"SELECT * FROM sqlite_master WHERE tbl_name = '" + 
									tableName + "'", null);
		
		((Activity) context).startManagingCursor(cursor);
//		actv.startManagingCursor(cursor);
		
		// Judge
		if (cursor.getCount() > 0) {
			return true;
		} else {//if (cursor.getCount() > 0)
			return false;
		}//if (cursor.getCount() > 0)
	}//public boolean tableExists(String tableName)
	
	public static boolean 
	tableExists
	(Activity actv, String dbName, String tableName) {
		// The table exists?
		DBUtils dbu = new DBUtils(actv, dbName);
		
		//
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		Cursor cursor = rdb.rawQuery(
				"SELECT * FROM sqlite_master WHERE tbl_name = '" + 
						tableName + "'", null);
		
		actv.startManagingCursor(cursor);
//		actv.startManagingCursor(cursor);
		
		// Judge
		if (cursor.getCount() > 0) {
		
			rdb.close();
			return true;
			
		} else {//if (cursor.getCount() > 0)
			
			rdb.close();
			return false;
			
		}//if (cursor.getCount() > 0)
		
	}//public boolean tableExists(String tableName)

	public boolean dropTable(Activity actv, SQLiteDatabase db, String tableName) {
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Starting: dropTable()");
		
		/*------------------------------
		 * The table exists?
		 *------------------------------*/
		// The table exists?
		boolean tempBool = tableExists(db, tableName);
		
		if (tempBool == true) {
		
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: " + tableName);

		} else {//if (tempBool == true)
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + tableName);

			return false;
		}//if (tempBool == true)

		/*------------------------------
		 * Drop the table
		 *------------------------------*/
		// Define the sql
        String sql 
             = "DROP TABLE " + tableName;
        
        // Execute
        try {
			db.execSQL(sql);
			
			// Vacuum
			db.execSQL("VACUUM");
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "The table dropped => " + tableName);
			
			// Return
			return true;
			
		} catch (SQLException e) {
			// TODO ?��?��?��?��?��?��?��?��?��?��?��ꂽ catch ?��u?��?��?��b?��N
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "DROP TABLE => failed (table=" + tableName + "): " + e.toString());
			
			// debug
			Toast.makeText(actv, 
						"DROP TABLE => failed(table=" + tableName, 
						3000).show();
			
			// Return
			return false;
		}//try

	}//public boolean dropTable(String tableName) 

	public boolean drop_table(Activity actv, SQLiteDatabase db, String tableName) {
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Starting: dropTable()");
		
		/*------------------------------
		 * The table exists?
		 *------------------------------*/
		// The table exists?
		boolean tempBool = tableExists(db, tableName);
		
		if (tempBool == true) {
		
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: " + tableName);

		} else {//if (tempBool == true)
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + tableName);

			return false;
		}//if (tempBool == true)

		/*------------------------------
		 * Drop the table
		 *------------------------------*/
		// Define the sql
        String sql 
             = "DROP TABLE " + tableName;
        
        // Execute
        try {
			db.execSQL(sql);
			
			// Vacuum
			db.execSQL("VACUUM");
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "The table dropped => " + tableName);
			
			// Return
			return true;
			
		} catch (SQLException e) {
			// TODO ?��?��?��?��?��?��?��?��?��?��?��ꂽ catch ?��u?��?��?��b?��N
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "DROP TABLE => failed (table=" + tableName + "): " + e.toString());
			
			// debug
			Toast.makeText(actv, 
						"DROP TABLE => failed(table=" + tableName, 
						3000).show();
			
			// Return
			return false;
		}//try

	}//public boolean dropTable(String tableName) 

	public boolean insertData(SQLiteDatabase db, String tableName, 
												String[] columnNames, String[] values) {
		
////		String sql = "SELECT * FROM TABLE " + DBUtils.table_name_memo_patterns;
//		String sql = "SELECT * FROM " + DBUtils.table_name_memo_patterns;
//		
//		Cursor c = db.rawQuery(sql, null);
//		
//		
//		
//		// Log
//		Log.d("DBUtils.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "c.getCount() => " + c.getCount() + " / " +
//				"c.getColumnCount() => " + c.getColumnCount());
//		
//		c.close();
		
		
		/*----------------------------
		* 1. Insert data
		----------------------------*/
		try {
			// Start transaction
			db.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();
			
			// Put values
			for (int i = 0; i < columnNames.length; i++) {
				val.put(columnNames[i], values[i]);
			}//for (int i = 0; i < columnNames.length; i++)
			
			// Insert data
			db.insert(tableName, null, val);
			
			// Set as successful
			db.setTransactionSuccessful();
			
			// End transaction
			db.endTransaction();
			
			// Log
//			Log.d("DBUtils.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + 
//				" / " + columnNames[3] + " => " + values[3] + ")");
			
			return true;
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Exception! => " + e.toString());
			
			return false;
		}//try
		
//		//debug
//		return false;
		
	}//public insertData(String tableName, String[] columnNames, String[] values)

	/******************************
		public boolean dropTable
		
		@return
			false	=> Table can't be dropped or doesn't exist
	 ******************************/
	public boolean dropTable
	(Activity actv, String tableName) {
		/***************************************
		 * Setup: DB
		 ***************************************/
//		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = this.getWritableDatabase();
		
		/*------------------------------
		 * The table exists?
		 *------------------------------*/
		// The table exists?
		boolean tempBool = tableExists(wdb, tableName);
		
		if (tempBool == true) {
		
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: " + tableName);

		} else {//if (tempBool == true)
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + tableName);
			
			// debug
			String msg_Toast = "Table doesn't exist: " + tableName;
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();

			return false;
		}//if (tempBool == true)

		/*------------------------------
		 * Drop the table
		 *------------------------------*/
		// Define the sql
        String sql 
             = "DROP TABLE " + tableName;
        
        // Execute
        try {
			wdb.execSQL(sql);
			
			// Vacuum
			wdb.execSQL("VACUUM");
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "The table dropped => " + tableName);
			
			// debug
			String msg_Toast = "The table dropped => " + tableName;
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
			
			wdb.close();
			
			// Return
			return true;
			
		} catch (SQLException e) {
			// TODO ?��?��?��?��?��?��?��?��?��?��?��ꂽ catch ?��u?��?��?��b?��N
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "DROP TABLE => failed (table=" + tableName + "): " + e.toString());
			
			// debug
			Toast.makeText(actv, 
						"DROP TABLE => failed(table=" + tableName, 
						Toast.LENGTH_LONG).show();
			
			wdb.close();
			
			// Return
			return false;
		}//try

	}//public boolean dropTable(String tableName) 

	/******************************
		public boolean dropTable
		
		@return
			-1	Table doesn't exist<br>
			-2	SQLException<br>
			1	Table dropped<br>
	 ******************************/
	public static int dropTable_2
	(Activity actv, String tableName) {
		/***************************************
		 * Setup: DB
		 ***************************************/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		/*------------------------------
		 * The table exists?
		 *------------------------------*/
		// The table exists?
		boolean tempBool = DBUtils.tableExists(actv, CONS.DB.dbName, tableName);
		
		if (tempBool == true) {
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: " + tableName);
			
		} else {//if (tempBool == true)
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + tableName);
			
			// debug
			String msg_Toast = "Table doesn't exist: " + tableName;
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
			return -1;
			
		}//if (tempBool == true)
		
		/*------------------------------
		 * Drop the table
		 *------------------------------*/
		// Define the sql
		String sql 
		= "DROP TABLE " + tableName;
		
		// Execute
		try {
			wdb.execSQL(sql);
			
			// Vacuum
			wdb.execSQL("VACUUM");
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "The table dropped => " + tableName);
			
			// debug
			String msg_Toast = "The table dropped => " + tableName;
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
			
			wdb.close();
			
			// Return
			return 1;
			
		} catch (SQLException e) {
			// TODO ?��?��?��?��?��?��?��?��?��?��?��ꂽ catch ?��u?��?��?��b?��N
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "DROP TABLE => failed (table=" + tableName + "): " + e.toString());
			
			// debug
			Toast.makeText(actv, 
					"DROP TABLE => failed(table=" + tableName, 
					Toast.LENGTH_LONG).show();
			
			wdb.close();
			
			// Return
			return -2;
		}//try
		
	}//public boolean dropTable(String tableName) 
	
	public boolean 
	insertData(SQLiteDatabase db, String tableName, 
											String[] columnNames, long[] values) {
		/*----------------------------
		* 1. Insert data
		----------------------------*/
		try {
			// Start transaction
			db.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();
			
			// Put values
			for (int i = 0; i < columnNames.length; i++) {
				val.put(columnNames[i], values[i]);
			}//for (int i = 0; i < columnNames.length; i++)
			
			// Insert data
			db.insert(tableName, null, val);
			
			// Set as successful
			db.setTransactionSuccessful();
			
			// End transaction
			db.endTransaction();
			
			// Log
			Log.d("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + "), and others");
			
			return true;
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Exception! => " + e.toString());
			
			return false;
		}//try
	}//public insertData(String tableName, String[] columnNames, String[] values)
	
	/******************************
		@return false => 1. Insertion failed<br>
						2. Exception
	 ******************************/
	public boolean 
	insert_Data_RefreshDate
	(SQLiteDatabase db, int numOfData) {
		/*----------------------------
		 * 1. Insert data
		----------------------------*/
		////////////////////////////////

		// prep: content values

		////////////////////////////////
		// ContentValues
		ContentValues val = new ContentValues();
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"last_refreshed", "num_of_items_added"
		
		// Put values
		val.put("created_at",
				Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
		val.put("modified_at",
				Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
		val.put("last_refreshed",
				Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
		val.put("num_of_items_added", numOfData);
		
		try {
			// Start transaction
			db.beginTransaction();
			
//			// ContentValues
//			ContentValues val = new ContentValues();
//			
//			// Put values
//			for (int i = 0; i < columnNames.length; i++) {
//				val.put(columnNames[i], values[i]);
//			}//for (int i = 0; i < columnNames.length; i++)
			
			// Insert data
			long res = db.insert(CONS.DB.tname_RefreshLog, null, val);
			
			if (res == -1) {
				
				// Log
				String msg_Log = "insertion => failed";
				Log.e("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				db.endTransaction();
				
				return false;
				
			} else {
				
				// Log
				String msg_Log = "insertion => done";
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);

			}
			
			// Set as successful
			db.setTransactionSuccessful();
			
			// End transaction
			db.endTransaction();
			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + "), and others");
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception! => " + e.toString());
			
			return false;
			
		}//try
		
	}//insertData_RefreshDate
	
	/******************************
		@return false => 1. Insertion failed<br>
						2. Exception
	 ******************************/
	public static boolean 
	insert_Data_generic
	(Activity actv, String tname, ContentValues val) {
		/*----------------------------
		 * 1. Insert data
		----------------------------*/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		

		////////////////////////////////

		// insert

		////////////////////////////////
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// Insert data
			long res = wdb.insert(tname, null, val);
			
			if (res == -1) {
				
				// Log
				String msg_Log = "insertion => failed";
				Log.e("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				wdb.endTransaction();
		
				wdb.close();
				
				return false;
				
			} else {
				
				// Log
				String msg_Log = "insertion => done";
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
			wdb.close();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return false;
			
		}//try
		
	}//insertData_RefreshDate
	
	public boolean deleteData(Activity actv, SQLiteDatabase db, String tableName, long file_id) {
		/*----------------------------
		 * Steps
		 * 1. Item exists in db?
		 * 2. If yes, delete it
			----------------------------*/
		/*----------------------------
		 * 1. Item exists in db?
			----------------------------*/
		boolean result = isInDB_long(db, tableName, file_id);
		
		if (result == false) {		// Result is false ==> Meaning the target data doesn't exist
											//							in db; Hence, not executing delete op
			
			// debug
			Toast.makeText(actv, 
					"Data doesn't exist in db: " + String.valueOf(file_id), 
					2000).show();
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"Data doesn't exist in db => Delete the data (file_id = " + String.valueOf(file_id) + ")");
			
			return false;
			
		} else {//if (result == false)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"Data exists in db" + String.valueOf(file_id) + ")");
			
		}//if (result == false)
		
		
		String sql = 
						"DELETE FROM " + tableName + 
						" WHERE file_id = '" + String.valueOf(file_id) + "'";
		
		try {
			db.execSQL(sql);
			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Data deleted => file id = " + String.valueOf(file_id));
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Sql executed: " + sql);
			
			return true;
			
		} catch (SQLException e) {
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			return false;
			
		}//try
		
	}//public boolean deleteData(SQLiteDatabase db, String tableName, long file_id)

	public boolean deleteData_ai(Activity actv,
						SQLiteDatabase db, String tableName, long db_id) {
		/*----------------------------
		 * Steps
		 * 1. Item exists in db?
		 * 2. If yes, delete it
			----------------------------*/
		/*----------------------------
		 * 1. Item exists in db?
			----------------------------*/
		boolean result = DBUtils.isInDB_long_ai(db, tableName, db_id);
		
		if (result == false) {		// Result is false ==> Meaning the target data doesn't exist
											//							in db; Hence, not executing delete op
			
			// debug
			Toast.makeText(actv, 
					"Data doesn't exist in db: " + String.valueOf(db_id), 
					2000).show();
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"Data doesn't exist in db => Delete the data (db_id = " + String.valueOf(db_id) + ")");
			
			return false;
			
		} else {//if (result == false)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"Data exists in db" + String.valueOf(db_id) + ")");
			
		}//if (result == false)
		
		
		String sql = 
						"DELETE FROM " + tableName
						+ " WHERE " + android.provider.BaseColumns._ID
						+ " = "
						+ String.valueOf(db_id);
		
		try {
			db.execSQL(sql);
			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Data deleted => file id = " + String.valueOf(db_id));
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Sql executed: " + sql);
			
			return true;
			
		} catch (SQLException e) {
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "sql=" + sql);
			
			return false;
			
		}//try
		
	}//public boolean deleteData_ai(Activity actv, SQLiteDatabase db, String tableName, long db_id)

	/****************************************
	 *
	 * 
	 * <Caller> 
	 * 1. deleteData(Activity actv, SQLiteDatabase db, String tableName, long file_id)
	 * 
	 * <Desc> 
	 * 1. REF=> http://stackoverflow.com/questions/3369888/android-sqlite-insert-unique
	 * 
	 * <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static boolean 
	isInDB_long
	(SQLiteDatabase db, String tableName, long file_id) {
		
		String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE file_id = '" +
						String.valueOf(file_id) + "'";
		
		long result = DatabaseUtils.longForQuery(db, sql, null);
		
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "result => " + String.valueOf(result));
		
		if (result > 0) {

			return true;
			
		} else {//if (result > 0)
			
			return false;
			
		}//if (result > 0)
		
//		return false;
		
	}//public boolean isInDB_long(SQLiteDatabase db, String tableName, long file_id)

	public static boolean isInDB_long_ai(
						SQLiteDatabase db, String tableName, long db_id) {
		
		String sql = "SELECT COUNT(*) FROM " + tableName
					+ " WHERE " + android.provider.BaseColumns._ID + " = "
					+ String.valueOf(db_id);
		
		long result = DatabaseUtils.longForQuery(db, sql, null);
		
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "result => " + String.valueOf(result));
		
		if (result > 0) {

			return true;
			
		} else {//if (result > 0)
			
			return false;
			
		}//if (result > 0)
		
//		return false;
		
	}//public static boolean isInDB_long_ai

	public boolean insert_data_refresh_history(SQLiteDatabase wdb,
			String tableName, long[] data) {
		/*----------------------------
		* 1. Insert data
		----------------------------*/
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();
			
//			// Put values
//			for (int i = 0; i < columnNames.length; i++) {
//				val.put(columnNames[i], values[i]);
//			}//for (int i = 0; i < columnNames.length; i++)
			
//			"last_refreshed", "num_of_items_added"
			
			val.put("last_refreshed", data[0]);
			
			val.put("num_of_items_added", data[1]);
			
			// Insert data
			wdb.insert(tableName, null, val);
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
			// Log
//			Log.d("DBUtils.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + 
//				" / " + columnNames[3] + " => " + values[3] + ")");
			
			return true;
			
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Exception! => " + e.toString());
			
			return false;
			
		}//try
		
	}//public boolean insert_data_refresh_history

	/******************************
		@return true => update successful
	 ******************************/
	public static boolean
	update_Data_AI
	(Activity actv, String dbName, String tableName,
			long db_id, String col_name, String value) {
		/*********************************
		 * memo
		 *********************************/
		DBUtils dbu = new DBUtils(actv, dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		
//		String sql = "UPDATE " + CONS.tname_main + " SET " + 
	
		String sql = "UPDATE " + tableName + " SET " + 
//				"last_viewed_at='" + STD.getMillSeconds_now() + "' " +

				col_name + " = '" + value + "' "
				+ " WHERE " + android.provider.BaseColumns._ID + " = '"
				+ db_id + "'";
		
		
		//			"file_id", 		"file_path", "file_name", "date_added", "date_modified"
		//static String[] cols = 
		//{"file_id", 		"file_path", "file_name", "date_added",
		//"date_modified", "memos", "tags"};
		
		
		try {
			
			wdb.execSQL(sql);
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "sql => Done: " + sql);
			
			//Methods.toastAndLog(actv, "Data updated", 2000);
			
			wdb.close();
			
			return true;
			
			
		} catch (SQLException e) {
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString() + " / " + "sql: " + sql);
			
			wdb.close();
			
			return false;
		}
		
	}//public static boolean update_data_ai()

	
	public void updateData_aiLength(Activity actv, String table_name,
			long db_id, int length) {
		
		DBUtils dbu = new DBUtils(actv, dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();

		
		String sql = "UPDATE " + table_name + " SET " + 
				"length" + " = " + length + " "
				+ " WHERE " + android.provider.BaseColumns._ID + " = '"
				+ db_id + "'";
				
		// Log
		Log.d("DBUtils.java" + "["
		+ Thread.currentThread().getStackTrace()[2].getLineNumber()
		+ "]", "sql=" + sql);

		try {
			
			wdb.execSQL(sql);
			
			// Log
			Log.d("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Exec sql => Done");
			
		} catch (SQLException e) {

			// Log
			Log.e("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Exception => " + e.toString());

		}//try
		
		// Close
		wdb.close();
		
	}//public void updateData_aiLength

	/******************************
		@return
			false<br>
			1. table doesn't exist<br>
			2. SQLException<br>
	 ******************************/
	public boolean
	updateData_generic
	(Activity actv, String tableName, 
		long dbId, String colName, String colValue) {

		////////////////////////////////

		// validate: table exists

		////////////////////////////////
		if (!DBUtils.tableExists(actv, CONS.DB.dbName, tableName)) {
			// Log
			Log.i("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exists => " + tableName);
			
			return false;
			
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		/***************************************
		 * Setup: DB
		 ***************************************/
		SQLiteDatabase wdb = this.getWritableDatabase();
		
		/***************************************
		 * Build SQL
		 ***************************************/
		String sql = "UPDATE " + tableName + " SET "
//				+ colName + "='" + colValue + "', "
				+ colName + "='" + colValue + "'"
				+ " WHERE " + android.provider.BaseColumns._ID + " = '" + dbId + "'";
				
		/***************************************
		 * Exec: Query
		 ***************************************/
		try {
			
			wdb.execSQL(sql);
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "sql => Done: " + sql);
			
		//	Methods.toastAndLog(actv, "Data updated", 2000);

			wdb.close();
			
			return true;
			
			
		} catch (SQLException e) {
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString() + " / " + "sql: " + sql);
			
			wdb.close();
			
			return false;
		}

	}//updateData_generic()

	/******************************
		@return
			false<br>
			1. table doesn't exist<br>
			2. SQLException<br>
	 ******************************/
	public static boolean
	updateData_generic_static
	(Activity actv, String tableName, 
			long dbId, String colName, String colValue) {
		
		////////////////////////////////
		
		// validate: table exists
		
		////////////////////////////////
		if (!DBUtils.tableExists(actv, CONS.DB.dbName, tableName)) {
			// Log
			Log.i("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exists => " + tableName);
			
			return false;
			
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		/***************************************
		 * Setup: DB
		 ***************************************/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		ContentValues val = new ContentValues();
		
		val.put(colName, colValue);
		
		String where = android.provider.BaseColumns._ID
						+ " = ?";
		
		String[] args = new String[]{String.valueOf(dbId)};
		
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// Insert data
			long res = wdb.update(tableName, val, where, args);
			
			if (res < 1) {
				// Log
				String msg_Log = String.format(
									"insertion => failed (result = %d): table = %s"
									, res, tableName);

//				Methods_dlg.dlg_ShowMessage(actv, msg_Log, R.color.red);
				
				wdb.endTransaction();
		
				wdb.close();
				
				return false;
				
			} else {
				
				// Log
				String msg_Log = "insertion => done: " + tableName;
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
			wdb.close();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return false;
			
		}//try		

		
		
//		/***************************************
//		 * Build SQL
//		 ***************************************/
//		String sql = "UPDATE " + tableName + " SET "
//			//				+ colName + "='" + colValue + "', "
//			+ colName + "='" + colValue + "'"
//			+ " WHERE " + android.provider.BaseColumns._ID + " = '" + dbId + "'";
//		
//		/***************************************
//		 * Exec: Query
//		 ***************************************/
//		try {
//			
//			wdb.execSQL(sql);
//			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "sql => Done: " + sql);
//			
//			//	Methods.toastAndLog(actv, "Data updated", 2000);
//			
//			wdb.close();
//			
//			return true;
//			
//			
//		} catch (SQLException e) {
//			// Log
//			Log.e("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception => " + e.toString() + " / " + "sql: " + sql);
//			
//			wdb.close();
//			
//			return false;
//		}
		
	}//updateData_generic()
	
	/******************************
		@return
			false<br>
			1. table doesn't exist<br>
			2. SQLException<br>
	 ******************************/
	public static boolean
	updateData_generic_static
	(Activity actv, String tableName, 
		String colName, String colValue,
		String where, String[] args) {
		
		////////////////////////////////
		
		// validate: table exists
		
		////////////////////////////////
		if (!DBUtils.tableExists(actv, CONS.DB.dbName, tableName)) {
			// Log
			Log.i("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exists => " + tableName);
			
			return false;
			
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		/***************************************
		 * Setup: DB
		 ***************************************/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		ContentValues val = new ContentValues();
		
		val.put(colName, colValue);
		
//		String where = android.provider.BaseColumns._ID
//				+ " = ?";
//		
//		String[] args = new String[]{String.valueOf(dbId)};
		
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// Insert data
			long res = wdb.update(tableName, val, where, args);
			
			if (res < 1) {
				// Log
				String msg_Log = String.format(
						"insertion => failed (result = %d): table = %s"
						, res, tableName);
				
//				Methods_dlg.dlg_ShowMessage(actv, msg_Log, R.color.red);
				
				wdb.endTransaction();
				
				wdb.close();
				
				return false;
				
			} else {
				
				// Log
				String msg_Log = "insertion => done: " + tableName;
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
			wdb.close();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return false;
			
		}//try		
		
		
		
//		/***************************************
//		 * Build SQL
//		 ***************************************/
//		String sql = "UPDATE " + tableName + " SET "
//			//				+ colName + "='" + colValue + "', "
//			+ colName + "='" + colValue + "'"
//			+ " WHERE " + android.provider.BaseColumns._ID + " = '" + dbId + "'";
//		
//		/***************************************
//		 * Exec: Query
//		 ***************************************/
//		try {
//			
//			wdb.execSQL(sql);
//			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "sql => Done: " + sql);
//			
//			//	Methods.toastAndLog(actv, "Data updated", 2000);
//			
//			wdb.close();
//			
//			return true;
//			
//			
//		} catch (SQLException e) {
//			// Log
//			Log.e("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception => " + e.toString() + " / " + "sql: " + sql);
//			
//			wdb.close();
//			
//			return false;
//		}
		
	}//updateData_generic()
	
	/******************************
		@return
			false<br>
			1. table doesn't exist<br>
			2. SQLException<br>
	 ******************************/
	public static boolean
	updateData_generic_static
	(Activity actv, String tableName, 
			ContentValues val,
			String where, String[] args) {
		
		////////////////////////////////
		
		// validate: table exists
		
		////////////////////////////////
		if (!DBUtils.tableExists(actv, CONS.DB.dbName, tableName)) {
			// Log
			Log.i("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exists => " + tableName);
			
			return false;
			
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		/***************************************
		 * Setup: DB
		 ***************************************/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
//		ContentValues val = new ContentValues();
//		
//		val.put(colName, colValue);
		
//		String where = android.provider.BaseColumns._ID
//				+ " = ?";
//		
//		String[] args = new String[]{String.valueOf(dbId)};
		
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// Insert data
			long res = wdb.update(tableName, val, where, args);
			
			if (res < 1) {
				// Log
				String msg_Log = String.format(
						"insertion => failed (result = %d): table = %s"
						, res, tableName);
				
//				Methods_dlg.dlg_ShowMessage(actv, msg_Log, R.color.red);
				
				wdb.endTransaction();
				
				wdb.close();
				
				return false;
				
			} else {
				
				// Log
				String msg_Log = "insertion => done: " + tableName;
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
			wdb.close();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return false;
			
		}//try		
		
		
		
//		/***************************************
//		 * Build SQL
//		 ***************************************/
//		String sql = "UPDATE " + tableName + " SET "
//			//				+ colName + "='" + colValue + "', "
//			+ colName + "='" + colValue + "'"
//			+ " WHERE " + android.provider.BaseColumns._ID + " = '" + dbId + "'";
//		
//		/***************************************
//		 * Exec: Query
//		 ***************************************/
//		try {
//			
//			wdb.execSQL(sql);
//			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "sql => Done: " + sql);
//			
//			//	Methods.toastAndLog(actv, "Data updated", 2000);
//			
//			wdb.close();
//			
//			return true;
//			
//			
//		} catch (SQLException e) {
//			// Log
//			Log.e("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception => " + e.toString() + " / " + "sql: " + sql);
//			
//			wdb.close();
//			
//			return false;
//		}
		
	}//updateData_generic()
	
	public int
	getNumOfEntries(Activity actv, String table_name) {
		/*********************************
		 * memo
		 *********************************/
//		DBUtils dbu = new DBUtils(actv, CONS.dbName);
		
		SQLiteDatabase rdb = this.getReadableDatabase();

		String sql = "SELECT * FROM " + table_name;
		
		Cursor c = null;
		
		try {
			
			c = rdb.rawQuery(sql, null);
			
		} catch (Exception e) {
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			rdb.close();
			
			return -1;
		}
		
		int num_of_entries = c.getCount();
		
		rdb.close();

		return num_of_entries;
		
	}//public int getNumOfEntries(Activity actv, String table_name)

	public int
	getNumOfEntries_BM(Activity actv, String table_name, long aiDbId) {
		/*********************************
		 * memo
		 *********************************/
//		DBUtils dbu = new DBUtils(actv, CONS.dbName);
		
		SQLiteDatabase rdb = this.getReadableDatabase();

		String sql = "SELECT * FROM " + table_name
					+ " WHERE "
					+ "ai_id = "
					+ aiDbId;
		
		Cursor c = null;
		
		try {
			
			c = rdb.rawQuery(sql, null);
			
		} catch (Exception e) {
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			rdb.close();
			
			return -1;
		}
		
		int num_of_entries = c.getCount();
		
		rdb.close();

		return num_of_entries;
		
	}//public int getNumOfEntries_BM(Activity actv, String table_name, long aiDbId)

	/****************************************
	 *		insertDataIntoDB()
	 * 
	 * <Caller> 
	 * 1. private static boolean refreshMainDB_3_insert_data(Activity actv, Cursor c)
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	private static int 
	insertDataIntoDB
	(Activity actv, String tableName, Cursor c) {
		/*----------------------------
		 * Steps
		 * 0. Set up db
		 * 1. Move to first
		 * 2. Set variables
		 * 3. Obtain data
		 * 4. Insert data
		 * 5. Close db
		 * 6. Return => counter
			----------------------------*/
		/*----------------------------
		 * 0. Set up db
			----------------------------*/
//		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
//		
//		SQLiteDatabase wdb = dbu.getWritableDatabase();
//		
//		/*----------------------------
//		 * 1. Move to first
//			----------------------------*/
//		c.moveToFirst();
//
//		/*----------------------------
//		 * 2. Set variables
//			----------------------------*/
//		int counter = 0;
//		int counter_failed = 0;
//		
//		/*----------------------------
//		 * 3. Obtain data
//			----------------------------*/
//		for (int i = 0; i < c.getCount(); i++) {
//
//			String[] values = {
//					String.valueOf(c.getLong(0)),
//					c.getString(1),
//					c.getString(2),
//					String.valueOf(c.getLong(3)),
////					String.valueOf(c.getLong(4))
//					String.valueOf(c.getLong(4)),
//					MainActv.tname_main,
//			};
//
//			/*----------------------------
//			 * 4. Insert data
//			 * 		1. Insert data to tableName
//			 * 		2. Record result
//			 * 		3. Insert data to backupTableName
//			 * 		4. Record result
//				----------------------------*/
//			boolean blResult = 
//						dbu.insertData(wdb, tableName, CONS.cols_for_insert_data, values);
//				
//			if (blResult == false) {
//				// Log
//				Log.d("Methods.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", "i => " + i + "/" + "c.getLong(0) => " + c.getLong(0));
//				
//				counter_failed += 1;
//				
//			} else {//if (blResult == false)
//				counter += 1;
//			}
//
//			//
//			c.moveToNext();
//			
//			if (i % 100 == 0) {
//				// Log
//				Log.d("Methods.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", "Done up to: " + i);
//				
//			}//if (i % 100 == 0)
//			
//		}//for (int i = 0; i < c.getCount(); i++)
//		
//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "All data inserted: " + counter);
//		
//		/*----------------------------
//		 * 5. Close db
//			----------------------------*/
//		wdb.close();
//		
//		/*----------------------------
//		 * 6. Return => counter
//			----------------------------*/
//		//debug
//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "counter_failed(sum): " + counter_failed);
//		
//		return counter;
		return 0;
		
	}//private static int insertDataIntoDB(Activity actv, Cursor c)

	
	
	
	
//	private static ContentValues 
//	_insert_Data_Patterns__ContentValues
//	(String pattern) {
//		// TODO Auto-generated method stub
//		ContentValues val = new ContentValues();
//		
////		android.provider.BaseColumns._ID,		// 0
////		"created_at", "modified_at",			// 1,2
////		"word",									// 3
////		"table_name"							// 4		
//		
//		val.put(
//				CONS.DB.col_names_MemoPatterns_full[2],		// modified_at 
//				Methods.conv_MillSec_to_TimeLabel(
//						STD.getMillSeconds_now()));
//		
//		val.put(
//				CONS.DB.col_names_MemoPatterns_full[3],		// word
//				pattern);
//		
//		return val;
//		
//	}//_insert_Data_Patterns__ContentValues

	/******************************
		@return -1 => Query exception<br>
				-2 => Query returned null<br>
				-3 => Query returned no entry<br>
	 ******************************/
	public static int 
	get_NumOfEntries_TI
	(Activity actv, String tname) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		//
		SQLiteDatabase rdb = dbu.getReadableDatabase();

//		////////////////////////////////
//		
//		// setup: cols
//		
//		////////////////////////////////
//		String[] cols = null;
//		
//		if (tname.equals(CONS.DB.tname_IFM11)) {
			
		String[] cols = CONS.DB.col_names_IFM11_full;
					
//		} else if (tname.equals(CONS.DB.tname_MemoPatterns)) {
//
//			cols = CONS.DB.col_names_MemoPatterns_full;
//			
//		} else if (tname.equals(CONS.DB.tname_RefreshLog)) {
//			
//			cols = CONS.DB.col_names_refresh_log_full;
//			
//		}
		
		////////////////////////////////

		// Query

		////////////////////////////////
		Cursor c = null;
		
		actv.startManagingCursor(c);
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"file_id", "file_path", "file_name",	// 3,4,5
//		"date_added", "date_modified",			// 6,7
//		"memos", "tags",						// 8,9
//		"last_viewed_at",						// 10
//		"table_name"							// 11
		
		String where = CONS.DB.col_names_IFM11_full[11]
						+ " = ?";
		
		String[] args = new String[]{
				
				tname
				
		};
		
		try {
			
			c = rdb.query(
					
					CONS.DB.tname_IFM11,			// 1
					CONS.DB.col_names_IFM11_full,	// 2
					where, args,		// 3,4
					null, null,		// 5,6
					null,			// 7
					null);
			
		} catch (Exception e) {

			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", e.toString());
			
			rdb.close();
			
			return -1;
			
		}//try
		
		/***************************************
		 * Validate
		 * 	Cursor => Null?
		 * 	Entry => 0?
		 ***************************************/
		if (c == null) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Query failed");
			
			rdb.close();
			
			return -2;
			
		} else if (c.getCount() < 1) {//if (c == null)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "No entry in the table");
			
			rdb.close();
			
			return -3;
			
		}//if (c == null)		
		
		////////////////////////////////

		// prep: return

		////////////////////////////////
		int numOfItems = c.getCount();
		
		////////////////////////////////

		// close

		////////////////////////////////
		rdb.close();
		
		
		return numOfItems;
		
	}//get_NumOfEntries_TI

	/******************************
		@return
			-1 => Unknown sql type<br>
			-2 => Exception<br>
			1 => Sql done<br>
	 ******************************/
	public static int 
	exec_Sql
	(Activity actv, String sql_Type) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// setup

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();		
		
		String sql = null;

		////////////////////////////////

		// dispatch

		////////////////////////////////
		if (sql_Type.equals(
				CONS.DB.Sqls._20140817_154650_addCol_IFM11_UpdatedAt_TITLE)) {
			
			sql = CONS.DB.Sqls._20140817_154650_addCol_IFM11_UpdatedAt_SQL;
			
		} else {

			wdb.close();
			
			// Log
			String msg_Log = "Unknown sql type => " + sql_Type;
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return -1;
			
		}
		
		try {
			
			wdb.execSQL(sql);
			
			// Log
			String msg_Log = "sql done => " + sql;
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} catch (Exception e) {
			// TODO: handle exception

			// Log
			String msg_Log = "Exception => " + e.toString();
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			wdb.close();
			
			return -2;
			
		}
		
		
		////////////////////////////////

		// close

		////////////////////////////////
		wdb.close();
		
		return 1;
		
	}//exec_Sql

	public static boolean 
	_backup_DB_SaveDate
	(Activity actv) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// validate: any entry

		////////////////////////////////
		int count = DBUtils.count_Entry(
								actv, 
								CONS.DB.tname_Admin, 
								CONS.DB.col_names_Admin);
		
		/******************************
			validate
		 ******************************/
		if (count < 0 && count != -4) {
			
			// Log
			String msg_Log = String.format("table => not ready: %s (count = %d)",
									CONS.DB.tname_Admin,
									count);
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return false;
			
		}
		
		////////////////////////////////

		// save date

		////////////////////////////////
		boolean res;
		
		if (count >= 1) {

//			android.provider.BaseColumns._ID,		// 0
//			"created_at", "modified_at",			// 1,2
//			"name",									// 3
//			"val",									// 4			
			
			String where = CONS.DB.col_names_Admin_full[3] + " = ?";
			
			String[] args = new String[]{
					
						CONS.DB.admin_LastBackup
						
			};

			String now = Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now());
			
			ContentValues val = new ContentValues();
			
			val.put(
					CONS.DB.col_names_Admin_full[2],
					now);

			val.put(
					CONS.DB.col_names_Admin_full[4],
					now);

			res = DBUtils.updateData_generic_static(
							actv, CONS.DB.tname_Admin, 
							
							val, where, args);
			
		} else {

			ContentValues val = new ContentValues();
			
			val.put(
					CONS.DB.col_names_Admin_full[1],
					Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
			
			val.put(
					CONS.DB.col_names_Admin_full[2],
					Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
			
			val.put(
					CONS.DB.col_names_Admin_full[3],
					CONS.DB.admin_LastBackup);
			
			val.put(
					CONS.DB.col_names_Admin_full[4],
					Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
			
			res = DBUtils.insert_Data_generic(actv, CONS.DB.tname_Admin, val);
			
		}
		
		return res;
		
	}//backup_DB_SaveDate

	/******************************
		@return
			-1	table doesn't exist<br>
			-2	query exception<br>
			-3	query => returned null<br>
			-4	entry => less than 1<br>
	 ******************************/
	private static int 
	count_Entry
	(Activity actv, String tname, String[] cols) {
		// TODO Auto-generated method stub
		////////////////////////////////
		
		// validate: table exists
		
		////////////////////////////////
		if (!DBUtils.tableExists(actv, CONS.DB.dbName, tname)) {
			// Log
			Log.i("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exists => " + tname);
			
			return -1;
			
		}//if (!tableExists(SQLiteDatabase db, String tableName))

		////////////////////////////////

		// prep: db

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		////////////////////////////////
		
		// Query
		
		////////////////////////////////
		Cursor c = null;
		
		try {
			
			c = rdb.query(
					
					tname,			// 1
					cols,	// 2
					null, null,		// 3,4
//					where, args,		// 3,4
					null, null,		// 5,6
					null,			// 7
					null);
			
		} catch (Exception e) {

			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", e.toString());
			
			rdb.close();
			
			return -2;
			
		}//try
		
		/***************************************
		 * Validate
		 * 	Cursor => Null?
		 * 	Entry => 0?
		 ***************************************/
		if (c == null) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Query failed");
			
			rdb.close();
			
			return -3;
			
		} else if (c.getCount() < 1) {//if (c == null)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "No entry in the table");
			
			rdb.close();
			
			return -4;
			
		}//if (c == null)

		////////////////////////////////

		// return

		////////////////////////////////
		int count = c.getCount();
		
		rdb.close();
		
		return count;
		
	}//count_Entry

	
	/******************************
		@return
			null<br>
			1. No DB file<br>
			2. No such table<br>
			3. query => Exception<br>
			4. cursor => returned null<br>
			5. cursor => no entry<br>
	 ******************************/
	public static String 
	find_LastBK
	(Activity actv) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// validate: DB file exists?

		////////////////////////////////
		File dpath_DBFile = actv.getDatabasePath(CONS.DB.dbName);

		if (!dpath_DBFile.exists()) {
			
			String msg = "No DB file: " + CONS.DB.dbName;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			return null;
			
		}
		
		////////////////////////////////

		// DB

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		////////////////////////////////
		
		// validate: table exists?
		
		////////////////////////////////
		String tname = CONS.DB.tname_Admin;
		
		boolean res = dbu.tableExists(rdb, tname);

		if (res == false) {
			
			String msg = "No such table: " + tname;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			rdb.close();
			
			return null;
			
		}

		////////////////////////////////
		
		// Query
		
		////////////////////////////////
		Cursor c = null;
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"name",									// 3
//		"val",									// 4
		
		String where = CONS.DB.col_names_Admin_full[3] + " = ?";
		String[] args = new String[]{
				
							CONS.DB.admin_LastBackup
						};
		
		String orderBy = CONS.DB.col_names_Admin_full[0] + " ASC";
		
		try {
			
			c = rdb.query(
					
					tname,			// 1
					CONS.DB.col_names_Admin_full,	// 2
					where, args,		// 3,4
					null, null,		// 5,6
					orderBy,			// 7
					null);
			
		} catch (Exception e) {

			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", e.toString());
			
			rdb.close();
			
			return null;
			
		}//try
		
		/***************************************
		 * Validate
		 * 	Cursor => Null?
		 * 	Entry => 0?
		 ***************************************/
		if (c == null) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Query failed");
			
			rdb.close();
			
			return null;
			
		} else if (c.getCount() < 1) {//if (c == null)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "No entry in the table");
			
			rdb.close();
			
			return null;
			
		}//if (c == null)

		////////////////////////////////

		// value

		////////////////////////////////
		c.moveToFirst();
		
		String val = c.getString(4);

		rdb.close();
		
		return val;
		
	}//find_LastBK

}//public class DBUtils

