
package utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;










// Apache
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import dc.items.LogItem;
import dc.listeners.dialog.DL;
import dc.main.CompassActv;
import dc.main.LogActv;
import dc.main.R;
import dc.main.ShowLogActv;

// REF=> http://commons.apache.org/net/download_net.cgi
//REF=> http://www.searchman.info/tips/2640.html

//import org.apache.commons.net.ftp.FTPReply;

public class Methods {
	
	public static void confirm_quit(Activity actv, int keyCode) {
		
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			
			AlertDialog.Builder dialog=new AlertDialog.Builder(actv);
			
	        dialog.setTitle(actv.getString(R.string.generic_tv_confirm));
	        dialog.setMessage(actv.getString(R.string.generic_tv_quit_app));
	        
	        dialog.setPositiveButton(
	        				actv.getString(R.string.generic_bt_ok),
	        				new DL(actv, dialog, 0));
	        
	        dialog.setNegativeButton(
	        				actv.getString(R.string.generic_bt_cancel),
	        				new DL(actv, dialog, 1));
	        
	        dialog.create();
	        dialog.show();
			
		}//if (keyCode==KeyEvent.KEYCODE_BACK)
		
	}//public static void confirm_quit(Activity actv, int keyCode)

	public static boolean 
	backup_DB
	(Activity actv) {
		/****************************
		 * 1. Prep => File names
		 * 2. Prep => Files
		 * 2-2. Folder exists?
		 * 
		 * 2-3. Dst folder => Files within the limit?
		 * 3. Copy
			****************************/
		boolean res;
		
		////////////////////////////////

		// prep: paths

		////////////////////////////////
		String time_label = Methods.get_TimeLabel(Methods.getMillSeconds_now());
//		String time_label = Methods.get_TimeLabel(STD.getMillSeconds_now());
		
		String fpath_Src = StringUtils.join(
					new String[]{
							actv.getDatabasePath(CONS.DB.dbName).getPath()},
//							CONS.fname_db},
					File.separator);
		
		String dpath_Dst = StringUtils.join(
					new String[]{
							CONS.DB.dPath_dbFile_backup,
							CONS.DB.fname_DB_Backup_Trunk},
//							CONS.dpath_db_backup,
//							CONS.fname_db_backup_trunk},
					File.separator);
		
		String fpath_Dst = dpath_Dst + "_"
				+ time_label
//				+ MainActv.fileName_db_backup_ext;
				+ CONS.DB.fname_DB_Backup_ext;
//		+ CONS.fname_db_backup_ext;
//				+ MainActv.fname_db_backup_trunk;

		// Log
		String msg_log = "db_Src = " + fpath_Src
						+ " / "
						+ "db_Dst = " + fpath_Dst;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_log);
		
		/****************************
		 * 2. Prep => Files
			****************************/
		File f_Src = new File(fpath_Src);
		File f_Dst = new File(fpath_Dst);
		
		/****************************
		 * 2-2. Folder exists?
			****************************/
		File db_Backup = new File(CONS.DB.dPath_dbFile_backup);
//		File db_backup = new File(CONS.dpath_db_backup);
		
		if (!db_Backup.exists()) {
			
			try {
				
				res = db_Backup.mkdirs();
//				boolean res = db_Backup.mkdir();
				
				if (res == true) {
					
					// Log
					Log.d("Methods.java" + "["
							+ Thread.currentThread().getStackTrace()[2].getLineNumber()
							+ "]", "Folder created: " + db_Backup.getAbsolutePath());
					
				} else {

					// Log
					Log.e("Methods.java" + "["
							+ Thread.currentThread().getStackTrace()[2].getLineNumber()
							+ "]", "Folder not created: " + db_Backup.getAbsolutePath());
					
				}
			} catch (Exception e) {
				
				// Log
				Log.e("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Create folder => Failed");
				
				return false;
				
			}
			
		} else {//if (!db_backup.exists())
			
			// Log
			Log.i("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Folder exists: ");
			
		}//if (!db_backup.exists())
		
		/*********************************
		 * 2-3. Dst folder => Files within the limit?
		 *********************************/
		File[] files_dst_folder = new File(CONS.DB.dPath_dbFile_backup).listFiles();
//		File[] files_dst_folder = new File(CONS.dpath_db_backup).listFiles();
		
		if (files_dst_folder != null) {
			
			int num_of_files = files_dst_folder.length;
			
			// Log
			Log.i("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "num of backup files = " + num_of_files);
			
		}
		
		
		/****************************
		 * 3. Copy
			****************************/
		try {
			FileChannel iChannel = new FileInputStream(f_Src).getChannel();
			FileChannel oChannel = new FileOutputStream(f_Dst).getChannel();
			iChannel.transferTo(0, iChannel.size(), oChannel);
			iChannel.close();
			oChannel.close();
			
			
			
			// Log
			Log.i("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "DB file copied");
			
			// debug
			Toast.makeText(actv, "DB backup => Done", Toast.LENGTH_LONG).show();

		} catch (FileNotFoundException e) {
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
			return false;
			
		} catch (IOException e) {
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
			return false;
			
		}//try

		////////////////////////////////

		// save date

		////////////////////////////////
		res = DBUtils._backup_DB_SaveDate(actv);
		
		// Log
		String msg_Log = "save date => " + res;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// return

		////////////////////////////////
		return true;
		
	}//backup_DB

//	/****************************************
//	 *	getMillSeconds_now()
//	 * 
//	 * <Caller> 
//	 * 1. ButtonOnClickListener # case main_bt_start
//	 * 
//	 * <Desc> 1. <Params> 1.
//	 * 
//	 * <Return> 1.
//	 * 
//	 * <Steps> 1.
//	 ****************************************/
//	public static long getMillSeconds_now() {
//		
//		Calendar cal = Calendar.getInstance();
//		
//		return cal.getTime().getTime();
//		
//	}//private long getMillSeconds_now(int year, int month, int date)

	/******************************
		@return format => "yyyyMMdd_HHmmss"
	 ******************************/
	public static String get_TimeLabel(long millSec) {
		
		 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.JAPAN);
		 
		return sdf1.format(new Date(millSec));
		
	}//public static String get_TimeLabel(long millSec)

	public static List<String> 
	get_FileList(File dpath) {
		
		
		////////////////////////////////

		// Directory exists?

		////////////////////////////////
				
		if (!dpath.exists()) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Dir doesn't exist");
			
			return null;
			
		} else {//if (!dpath.exists() == condition)
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Dir exists: " + dpath.getAbsolutePath());
			
		}//if (!dpath.exists() == condition)

		////////////////////////////////

		// Get: File list

		////////////////////////////////
		
		List<String> list_Dir = new ArrayList<String>();
		
		File[] files_list = dpath.listFiles();
		
		if (files_list == null) {
			
			// Log
			String msg_log = "listFiles() => returned null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return null;
			
		}

		////////////////////////////////

		// Sort list

		////////////////////////////////
		
		Methods.sort_list_files(files_list);
		
		for (File f : files_list) {
			
			list_Dir.add(f.getName());
			
		}//for (File f : files_list)
		
		/*********************************
		 * 3. Return
		 *********************************/
		return list_Dir;
		
	}//get_FileList

	public static void 
	sort_list_files(File[] files) {
		// REF=> http://android-coding.blogspot.jp/2011/10/sort-file-list-in-order-by-implementing.html
		/****************************
		 * 1. Prep => Comparator
		 * 2. Sort
			****************************/
		
		/****************************
		 * 1. Prep => Comparator
			****************************/
		Comparator<? super File> filecomparator = new Comparator<File>(){
			
			public int compare(File file1, File file2) {
				/****************************
				 * 1. Prep => Directory
				 * 2. Calculate
				 * 3. Return
					****************************/
				
				int pad1=0;
				int pad2=0;
				
				if(file1.isDirectory())pad1=-65536;
				if(file2.isDirectory())pad2=-65536;
				
				int res = pad2-pad1+file1.getName().compareToIgnoreCase(file2.getName());
				
				return res;
			} 
		 };//Comparator<? super File> filecomparator = new Comparator<File>()
		 
		/****************************
		 * 2. Sort
			****************************/
		Arrays.sort(files, filecomparator);

	}//public static void sort_list_files(File[] files)

	public static String 
	get_Pref_String
	(Activity actv, String pref_name,
			String pref_key, String defValue) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(
						pref_name, Context.MODE_PRIVATE);

		/****************************
		 * Return
			****************************/
		return prefs.getString(pref_key, defValue);

	}//public static String get_Pref_String

	public static boolean
	set_Pref_String
	(Activity actv, String pName, String pKey, String value) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(pName, Context.MODE_PRIVATE);
		
		/****************************
		 * 2. Get editor
		 ****************************/
		SharedPreferences.Editor editor = prefs.edit();
		
		/****************************
		 * 3. Set value
		 ****************************/
		editor.putString(pKey, value);
		
		try {
			
			editor.commit();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Excption: " + e.toString());
			
			return false;
			
		}
		
	}//public static boolean setPref_long(Activity actv, String pref_name, String pref_key, long value)

	public static int 
	get_Pref_Int
	(Activity actv, String pref_name, String pref_key, int defValue) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(pref_name, Context.MODE_PRIVATE);

		/****************************
		 * Return
			****************************/
		return prefs.getInt(pref_key, defValue);

	}//public static boolean set_pref(String pref_name, String value)

	/******************************
		@return true => pref set
	 ******************************/
	public static boolean 
	set_Pref_Int
	(Activity actv, 
			String pref_name, String pref_key, int value) {
		SharedPreferences prefs = 
				actv.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
	
		/****************************
		 * 2. Get editor
			****************************/
		SharedPreferences.Editor editor = prefs.edit();
	
		/****************************
		 * 3. Set value
			****************************/
		editor.putInt(pref_key, value);
		
		try {
			editor.commit();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Excption: " + e.toString());
			
			return false;
		}
	
	}//set_Pref_Int
	
//	public static boolean
//	restore_DB(Activity actv) {
//    	
//    	// Log
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Starting: restore_DB()");
//
//		/*********************************
//		 * Get the absolute path of the latest backup file
//		 *********************************/
//		// Get the most recently-created db file
//		String src_dir = CONS.DB.dPath_dbFile_backup;
////		String src_dir = "/mnt/sdcard-ext/IFM9_backup";
//		
//		File f_dir = new File(src_dir);
//		
//		File[] src_dir_files = f_dir.listFiles();
//		
//		// If no files in the src dir, quit the method
//		if (src_dir_files.length < 1) {
//			
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread()
//						.getStackTrace()[2].getLineNumber()
//					+ "]", "No files in the dir: " + src_dir);
//			
//			return false;
//			
//		}//if (src_dir_files.length == condition)
//		
//		// Latest file
//		File f_src_latest = src_dir_files[0];
//		
//		
//		for (File file : src_dir_files) {
//			
//			if (f_src_latest.lastModified() < file.lastModified()) {
//						
//				f_src_latest = file;
//				
//			}//if (variable == condition)
//			
//		}//for (File file : src_dir_files)
//		
//		// Show the path of the latest file
//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "f_src_latest=" + f_src_latest.getAbsolutePath());
//		
//		/*********************************
//		 * Restore file
//		 *********************************/
//		String src = f_src_latest.getAbsolutePath();
//		String dst = StringUtils.join(
//				new String[]{
//						//REF http://stackoverflow.com/questions/9810430/get-database-path answered Jan 23 at 11:24
//						actv.getDatabasePath(CONS.DB.dbName).getPath()
//				},
////						actv.getFilesDir().getPath() , 
////						CONS.DB.dbName},
//				File.separator);
//		
//		boolean res = Methods.restore_DB(
//							actv, 
//							CONS.DB.dbName, 
//							src, dst);
//		
//		// Log
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "res=" + res);
//		
//		////////////////////////////////
//
//		// return
//
//		////////////////////////////////
//		return res;
//		
//	}//private void restore_DB()
//
//	/*********************************
//	 * @return true => File copied(i.e. restored)<br>
//	 * 			false => Copying failed
//	 *********************************/
//	public static boolean
//	restore_DB
//	(Activity actv, String dbName, 
//			String src, String dst) {
//		/*********************************
//		 * 1. Setup db
//		 * 2. Setup: File paths
//		 * 3. Setup: File objects
//		 * 4. Copy file
//		 * 
//		 *********************************/
//		// Setup db
//		DBUtils dbu = new DBUtils(actv, dbName);
//		
//		SQLiteDatabase wdb = dbu.getWritableDatabase();
//	
//		wdb.close();
//	
//		/*********************************
//		 * 2. Setup: File paths
//	
//		/*********************************
//		 * 3. Setup: File objects
//		 *********************************/
//	
//		/*********************************
//		 * 4. Copy file
//		 *********************************/
//		FileChannel iChannel = null;
//		FileChannel oChannel = null;
//		
//		try {
//			iChannel = new FileInputStream(src).getChannel();
//			oChannel = new FileOutputStream(dst).getChannel();
//			iChannel.transferTo(0, iChannel.size(), oChannel);
//			
//			iChannel.close();
//			oChannel.close();
//			
//			// Log
//			Log.d("ThumbnailActivity.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "File copied: " + src);
//			
//			// debug
//			Toast.makeText(actv, "DB restoration => Done", Toast.LENGTH_LONG).show();
//			
//			return true;
//	
//		} catch (FileNotFoundException e) {
//			// Log
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception: " + e.toString());
//			if (iChannel != null) {
//				
//				try {
//					
//					iChannel.close();
//					
//				} catch (IOException e1) {
//					
//					// Log
//					Log.e("Methods.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", "Exception: " + e.toString());
//	
//				}
//				
//			}
//			
//			if (iChannel != null) {
//				
//				try {
//					
//					iChannel.close();
//					
//				} catch (IOException e1) {
//					
//					// Log
//					Log.e("Methods.java" + "["
//							+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//							+ "]", "Exception: " + e.toString());
//					
//				}
//				
//			}
//			
//			if (oChannel != null) {
//				
//				try {
//					oChannel.close();
//				} catch (IOException e1) {
//					
//					// Log
//					Log.e("Methods.java" + "["
//							+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//							+ "]", "Exception: " + e.toString());
//					
//				}
//				
//			}
//	
//			return false;
//			
//		} catch (IOException e) {
//			// Log
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception: " + e.toString());
//			
//			if (iChannel != null) {
//				
//				try {
//					
//					iChannel.close();
//					
//				} catch (IOException e1) {
//					
//					// Log
//					Log.e("Methods.java" + "["
//							+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//							+ "]", "Exception: " + e.toString());
//					
//				}
//				
//			}
//			
//			if (oChannel != null) {
//				
//				try {
//					oChannel.close();
//				} catch (IOException e1) {
//					
//					// Log
//					Log.e("Methods.java" + "["
//							+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//							+ "]", "Exception: " + e.toString());
//					
//				}
//				
//			}
//	
//			
//			return false;
//			
//		}//try
//		
//	}//restore_DB
//
	/****************************************
	 *	refreshMainDB(Activity actv)
	 * 
	 *  @return -1 => Can't create a table<br>
	 *  		-2 => Can't build cursor<br>
	 *  		-3 => No entry<br>
	 *  		-4 => Can't build TI list<br>
	 *  		0~	Number of items added
	 ****************************************/
//	public static int 
//	refresh_MainDB
//	(Activity actv) {
//		////////////////////////////////
//
//		// Set up DB(writable)
//		// Execute query for image files
//		// build: TI list from cursor
//		// Insert data into db
//		// close: db
//
//		////////////////////////////////
//		////////////////////////////////
//
//		// vars
//
//		////////////////////////////////
//		boolean res;
//		
//		////////////////////////////////
//
//		// Set up DB(writable)
//
//		////////////////////////////////
//		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
//		
//		SQLiteDatabase wdb = dbu.getWritableDatabase();
//
//		////////////////////////////////
//
//		// Table exists?
//		// If no, then create one
//		//	1. baseDirName
//		//	2. backupTableName
//
//		////////////////////////////////
//		res = Methods._refresh_MainDB__SetupTable(wdb, dbu);
////		boolean res = refreshMainDB_1_set_up_table(wdb, dbu);
//
//		if (res == false) {
//			
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Can't  create table");
//			
//			wdb.close();
//			
//			return -1;
//			
//		}//if (res == false)
//		
////		//debug
////		wdb.close();
////		
////		return -1;
//		
//		
//		////////////////////////////////
//
//		// Execute query for image files
//
//		////////////////////////////////
//		Cursor c = _refresh_MainDB__ExecQuery(actv, wdb, dbu);
//		
//		/******************************
//			validate: null
//		 ******************************/
//		if (c == null) {
//			
//			// Log
//			String msg_Log = "can't build cursor";
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			return -2;
//			
//		}
//
//		/******************************
//			validate: any entry?
//		 ******************************/
//		if (c.getCount() < 1) {
//			
//			// Log
//			String msg_Log = "No entry";
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			return -3;
//			
//		}
//		
//		////////////////////////////////
//
//		// build: TI list from cursor
//
//		////////////////////////////////
//		List<TI> list_TI = Methods._refresh_MainDB__Build_TIList(actv, c);
//
//		/******************************
//			validate: null
//		 ******************************/
//		if (list_TI == null) {
//			
//			// Log
//			String msg_Log = "list_TI => Null";
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			return -4;
//			
//		}
//		
//		////////////////////////////////
//
//		// close: db
//
//		////////////////////////////////
//		wdb.close();
//		
////		////////////////////////////////
////
////		// test
//		/*
//		 * - SDCard => reinserted to the device
//		 * - seems: last update of each file => reset to the current date
//		 * - hence, need a work to fix the TI table
//		 */
////
////		////////////////////////////////
////		List<TI> list_New = Methods
////						._refresh_MainDB__RecoveryFrom_SDCard_Reset(actv, list_TI);
//		
//		////////////////////////////////
//
//		// Insert data into db
//
//		////////////////////////////////
////		int numOfItemsAdded = _refresh_MainDB__InsertData_TIs(actv, list_New);
//		int numOfItemsAdded = _refresh_MainDB__InsertData_TIs(actv, list_TI);
////		int numOfItemsAdded = _refresh_MainDB__InsertData_Image(actv, wdb, dbu, c);
//		
//		// Log
//		String msg_Log = "numOfItemsAdded => " + numOfItemsAdded;
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//			
//		////////////////////////////////
//
//		// Insert: refresh date
//		//		=> only if there is/are new entry/entries
//
//		////////////////////////////////
//		res = Methods._refresh_MainDB__InsertData_RefreshDate(
////										actv, numOfItemsAdded, list_New);
//										actv, numOfItemsAdded, list_TI);
//
//		// Log
//		msg_Log = "insert refresh date => " + res;
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
////		return 0;
//		return numOfItemsAdded;
//		
//	}//public static int refreshMainDB(Activity actv)

//	private static List<TI> 
//	_refresh_MainDB__RecoveryFrom_SDCard_Reset
//	(Activity actv, List<TI> list_TI) {
//
//		////////////////////////////////
//
//		// steps
//		/*
//		 * 	1. build TI list (all files => number is: 4501 or something)
//		 * 	2. filter the list => only those starting with "2014-11"
//		 * 							(currently, 12 of them)
//		 * 	3. Insert those filtered ones
//		 */
//
//		////////////////////////////////
//		
//		////////////////////////////////
//
//		// setup
//
//		////////////////////////////////
//		List<TI> list_New = new ArrayList<TI>();
//		
//		int size = list_TI.size();
//		
//		// Log
//		String msg_Log = "list_TI.size() => " + size;
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//
//		////////////////////////////////
//
//		// filter list
//
//		////////////////////////////////
//		for (int i = 0; i < size; i++) {
//			
//			if (list_TI.get(i).getFile_name().startsWith("2014-11")) {
//				
//				list_New.add(list_TI.get(i));
//				
//			}
//		}
//		
//		// Log
//		msg_Log = "list_New.size() => " + list_New.size();
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
////		int limit = 30;
//		
//		for (int i = 0; i < list_New.size(); i++) {
////			for (int i = size - limit; i < size; i++) {
//			
//			// Log
//			msg_Log = "ti(new) => " + list_New.get(i).getFile_name();
////			msg_Log = "ti => " + list_TI.get(i).getFile_name();
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//		}
//		
//		////////////////////////////////
//
//		// return
//
//		////////////////////////////////
//		return list_New;
//
//	}//_refresh_MainDB__RecoveryFrom_SDCard_Reset
//	
//
//	private static boolean 
//	_refresh_MainDB__InsertData_RefreshDate
//	(Activity actv, 
//			int numOfItemsAdded, List<TI> list_TI) {
//		
//		////////////////////////////////
//
//		// prep: last refresh date
//
//		////////////////////////////////
//		long lastRefreshed = -1;
//		
//		String label = null;
//		
//		for (TI ti : list_TI) {
//			
//			if (Methods.conv_TimeLabel_to_MillSec(ti.getDate_added())
//					> lastRefreshed) {
////				if (ti.getDate_added() > lastRefreshed) {
//				
//				lastRefreshed = Methods.conv_TimeLabel_to_MillSec(ti.getDate_added());
////				lastRefreshed = ti.getDate_added();
//				
//			}
//			
//		}
//
//		if (lastRefreshed == -1) {
//			
//			// In seconds. 
//			label = Methods.conv_MillSec_to_TimeLabel(STD.getMillSeconds_now());
//			
//		} else {
//			
//			// Converting sec to mill sec
//			label = Methods.conv_MillSec_to_TimeLabel(lastRefreshed);
////			label = Methods.conv_MillSec_to_TimeLabel(lastRefreshed * 1000);
//			
//		}
//		
//		////////////////////////////////
//
//		// save data
//
//		////////////////////////////////
//		return DBUtils.insert_Data_RefreshDate(actv, label, numOfItemsAdded);
//		
////		return false;
//		
//	}//_refresh_MainDB__InsertData_RefreshDate
//	
//
//	private static int 
//	_refresh_MainDB__InsertData_TIs
//	(Activity actv, List<TI> list_TI) {
//		
//		
//		boolean res;
//		
//		int counter = 0;
//		
//		for (TI ti : list_TI) {
//			
//			res = DBUtils.insert_Data_TI(actv, ti);
//			
//			if (res == true) {
//				
//				counter += 1;
//				
//			}
//			
//		}
//		
//		return counter;
//		
//	}//_refresh_MainDB__InsertData_TIs
//
//	/******************************
//		@return Ti list => the below fields remain null<br>
//				1. created_at<br>
//				2. modified_at<br>
//				==> these fields are to be filled later<br>
//					when inserting the list into DB
//	 ******************************/
//	private static List<TI> 
//	_refresh_MainDB__Build_TIList
//	(Activity actv, Cursor c) {
//		
//		
//		List<TI> list_TI = new ArrayList<TI>();
//		
//		while(c.moveToNext()) {
//			
//			TI ti = new TI.Builder()
//						.setFileId(c.getLong(0))
////						.setCreated_at(time)
////						.setModified_at(time)
//						
//						.setFile_name(c.getString(2))
//						
//						.setDate_added(
//								Methods.conv_MillSec_to_TimeLabel(c.getLong(3) * 1000))
////								c.getLong(3))
//						.setDate_modified(
//								Methods.conv_MillSec_to_TimeLabel(c.getLong(4) * 1000))
////								c.getLong(4))
//						
//						.setTable_name(CONS.DB.tname_IFM11)
//						.setFile_path(CONS.Paths.dpath_Storage_Camera)
//						.build();
//			
//			list_TI.add(ti);
//			
//		}
//		
////		// Log
////		String msg_Log = "list_TI.size => " + list_TI.size();
////		Log.d("Methods.java" + "["
////				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////				+ "]", msg_Log);
//		
//		return list_TI;
//		
//	}//_refresh_MainDB__Build_TIList
//
//	private static int 
//	_refresh_MainDB__InsertData_Image
//	(Activity actv, SQLiteDatabase wdb, DBUtils dbu, Cursor c) {
//		/*----------------------------
//		 * 4. Insert data into db
//			----------------------------*/
////		int numOfItemsAdded = Methods.insertDataIntoDB(actv, MainActv.dirName_base, c);
//		int numOfItemsAdded = 0;
//			
////		int numOfItemsAdded = -1;
//		
//		/*----------------------------
//		 * 5. Update table "refresh_log"
//			----------------------------*/
//		c.moveToPrevious();
//		
//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "c.getLong(3) => " + c.getLong(3));
//		
//
//		return numOfItemsAdded;
//		
//	}//private static int refreshMainDB_3_insert_data(Cursor c)
//
//	/******************************
//		@return false => Table doesn't exist; can't create one
//	 ******************************/
//	private static boolean 
//	_refresh_MainDB__SetupTable
//	(SQLiteDatabase wdb, DBUtils dbu) {
//		/*----------------------------
//		 * 2-1.1. baseDirName
//			----------------------------*/
//		String tableName = CONS.DB.tname_IFM11;
//		boolean result = dbu.tableExists(wdb, tableName);
//		
//		// If the table doesn't exist, create one
//		if (result == false) {
//
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Table doesn't exist: " + tableName);
//			
//			result = dbu.createTable(
//							wdb, 
//							tableName, 
//							CONS.DB.col_names_IFM11, 
//							CONS.DB.col_types_IFM11);
//			
//			if (result == false) {
//
//				Log.d("Methods.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", "Can't create a table: "+ tableName);
//				
//				return false;
//				
//			} else {//if (result == false)
//				
//				Log.d("Methods.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", "Table created: "+ tableName);
//				
//				return true;
//				
//			}//if (result == false)
//
//		} else {//if (result == false)
//			
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Table exists: "+ tableName);
//
//			return true;
//			
//		}//if (result == false)
//	}//private static boolean refreshMainDB_1_set_up_table(SQLiteDatabase wdb, DBUtils dbu)
//
//	/******************************
//		@return null => 1. Can't prepare the table 'refresh log'<br>
//						2. Cursor => null<br>
//						3. Cursor => count < 1<br>
//	 ******************************/
//	private static Cursor 
//	_refresh_MainDB__ExecQuery
//	(Activity actv, SQLiteDatabase wdb, DBUtils dbu) {
//		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//        
//        // Log
//		String msg_Log = "uri.path => " + uri.getPath()
//					+ " / "
//					+ "uri.encodedPath =>" + uri.getEncodedPath()
//					+ " / "
//					+ "uri.getHost =>" + uri.getHost()
//					;
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//        
//		String[] proj = CONS.DB.proj;
//
//		////////////////////////////////
//
//		// setup: table: refresh log
//
//		////////////////////////////////
//		boolean res = Methods._refresh_MainDB__Setup_RefreshLog(actv, wdb, dbu);
//		
//		if (res == false) {
//			
//			// Log
//			msg_Log = "Setup can't be done => refresh_log  table";
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			return null;
//			
//		} else {
//
//			// Log
//			msg_Log = "setup done => rehresh log";
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//		}
//		
////		boolean result = dbu.tableExists(wdb, CONS.DB.tna.tableName_refreshLog);
////		
//		////////////////////////////////
//
//		// get: last refreshed date
//
//		////////////////////////////////
////		long lastRefreshedDate = 0;		// Initial value => 0
//		String lastRefreshedDate = 
//				Methods._refresh_MainDB__Get_LastRefreshed(actv, wdb, dbu);
//		
//		long last_Refreshed;
//		
//		/******************************
//			validate: gotten data?
//		 ******************************/
//		if (lastRefreshedDate == null) {
//			
//			last_Refreshed = 0;
//			
//		} else {
//			
//			last_Refreshed = Methods.conv_TimeLabel_to_MillSec(lastRefreshedDate);
//			
//		}
//		
//		// Log
//		msg_Log = String.format(Locale.JAPAN,
//						"last_Refreshed => %d (%s)", 
//						last_Refreshed, 
//						Methods.conv_MillSec_to_TimeLabel(last_Refreshed));
////		msg_Log = "lastRefreshedDate => " + lastRefreshedDate
////				+ ;
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		////////////////////////////////
//
//		// modify: refreshed date
//		//		=> convert to seconds
//
//		////////////////////////////////
//		last_Refreshed = last_Refreshed / 1000;
//		
//		msg_Log = String.format(Locale.JAPAN,
//						"last_Refreshed(converted) => %d (%s)", 
//						last_Refreshed, 
//						Methods.conv_MillSec_to_TimeLabel(last_Refreshed));
//		//msg_Log = "lastRefreshedDate => " + lastRefreshedDate
//		//		+ ;
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		////////////////////////////////
//
//		// Execute query
//
//		////////////////////////////////
//		// REF=> http://blog.csdn.net/uoyevoli/article/details/4970860
//		Cursor c = actv.managedQuery(
//						uri, 
//						proj,
//						MediaStore.Images.Media.DATE_ADDED + " > ?",
//						new String[] {String.valueOf(last_Refreshed)},
////						new String[] {String.valueOf(lastRefreshedDate)},
//						null);
//
//		////////////////////////////////
//
//		// validate
//
//		////////////////////////////////
//		/******************************
//			null
//		 ******************************/
//		if (c == null) {
//			
//			// Log
//			msg_Log = "cursor => null";
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			return null;
//			
//		/******************************
//		no entry
//		 ******************************/
//		} else if (c.getCount() < 1) {
//			
//			// Log
//			msg_Log = "EXTERNAL_CONTENT_URI => no entry";
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			return null;
//			
//		}
//		
//		// Log
//		msg_Log = "cursor: count => " + c.getCount();
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
////		// Log
////		Log.d("Methods.java" + "["
////				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////				+ "]", "Last refreshed (in sec): " + String.valueOf(lastRefreshedDate / 1000));
////
////        actv.startManagingCursor(c);
////        
////        // Log
////		Log.d("Methods.java" + "["
////				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////				+ "]", "c.getCount() => " + c.getCount());
////
////		return c;
//		
//        return c;
//        
//	}//_refresh_MainDB__ExecQuery
//
//	/******************************
//		@return null => 1. Can't prepare the table 'refresh log'<br>
//						2. Cursor => null<br>
//						3. Cursor => count < 1<br>
//	 ******************************/
//	private static Cursor 
//	_refresh_MainDB__ExecQuery__Period
//	(Activity actv,
//		SQLiteDatabase wdb, DBUtils dbu, long start, long end) {
//		
//		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//		
//		String[] proj = CONS.DB.proj;
//		
//		String msg_Log;
//		
//		////////////////////////////////
//		
//		// setup: table: refresh log
//		
//		////////////////////////////////
//		boolean res = Methods._refresh_MainDB__Setup_RefreshLog(actv, wdb, dbu);
//		
//		if (res == false) {
//			
//			// Log
//			msg_Log = "Setup can't be done => refresh_log  table";
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			return null;
//			
//		} else {
//			
//			// Log
//			msg_Log = "setup done => rehresh log";
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//		}
//		
//		long last_Refreshed = end;
//		
//		////////////////////////////////
//		
//		// Execute query
//		
//		////////////////////////////////
//		
//		// Log
//		msg_Log = String.format(
//					Locale.JAPAN,
//					"start => %d(%s), end => %d(%s)", 
//					start,
//					Methods.conv_MillSec_to_TimeLabel(start),
//					end,
//					Methods.conv_MillSec_to_TimeLabel(end));
//		
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		// REF=> http://blog.csdn.net/uoyevoli/article/details/4970860
//		Cursor c = actv.managedQuery(
//				uri, 
//				proj,
//				MediaStore.Images.Media.DATE_ADDED + " > ?",
////					+ " AND "
////					+ MediaStore.Images.Media.DATE_ADDED + " < ?",
//				new String[] {
//						
////						String.valueOf(start / 1000),
//						String.valueOf(end / 1000),
//						
//				},
////						new String[] {String.valueOf(lastRefreshedDate)},
//				null);
//		
//		/******************************
//			debug
//		 ******************************/
//		String lastRefreshedDate = 
//				Methods._refresh_MainDB__Get_LastRefreshed(actv, wdb, dbu);
//
//		last_Refreshed = Methods.conv_TimeLabel_to_MillSec(lastRefreshedDate);
//		
//		// Log
//		msg_Log = String.format(
//				Locale.JAPAN,
//				"start => %d(%s), end => %d(%s), last_Refreshed => %d(%s)", 
//				start,
//				Methods.conv_MillSec_to_TimeLabel(start),
//				end,
//				Methods.conv_MillSec_to_TimeLabel(end),
//				last_Refreshed,
//				Methods.conv_MillSec_to_TimeLabel(last_Refreshed));
//				
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		c = actv.managedQuery(
//				uri, 
//				proj,
//				MediaStore.Images.Media.DATE_ADDED + " > ?",
////					+ " AND "
////					+ MediaStore.Images.Media.DATE_ADDED + " < ?",
//				new String[] {
//						
////						String.valueOf(start / 1000),
//						String.valueOf(last_Refreshed / 1000),
//						
//				},
////						new String[] {String.valueOf(lastRefreshedDate)},
//				null);
//		
//		////////////////////////////////
//		
//		// validate
//		
//		////////////////////////////////
//		/******************************
//			null
//		 ******************************/
//		if (c == null) {
//			
//			// Log
//			msg_Log = "cursor => null";
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			return null;
//			
//			/******************************
//		no entry
//			 ******************************/
//		} else if (c.getCount() < 1) {
//			
//			// Log
//			msg_Log = "EXTERNAL_CONTENT_URI => no entry";
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			return null;
//			
//		}
//		
//		// Log
//		msg_Log = "cursor: count => " + c.getCount();
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
////		// Log
////		Log.d("Methods.java" + "["
////				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////				+ "]", "Last refreshed (in sec): " + String.valueOf(lastRefreshedDate / 1000));
////
////        actv.startManagingCursor(c);
////        
////        // Log
////		Log.d("Methods.java" + "["
////				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////				+ "]", "c.getCount() => " + c.getCount());
////
////		return c;
//		
//		return c;
//		
//	}//_refresh_MainDB__ExecQuery__Period
//	
//	/******************************
//	 * Data is stored in TEXT type. The method returns a String<br>
//	 * 
//		@return null => 1. query returned null<br>
//						2. query found no entry<br>
//	 ******************************/
//	private static String 
//	_refresh_MainDB__Get_LastRefreshed
//	(Activity actv, SQLiteDatabase wdb, DBUtils dbu) {
//		
//		
////		long lastRefreshedDate = 0;
//		
//		String orderBy = android.provider.BaseColumns._ID + " DESC";
//		
//		Cursor c = wdb.query(
//				CONS.DB.tname_RefreshLog,
//				CONS.DB.col_names_refresh_log_full,
////				CONS.DB.col_types_refresh_log_full,
//				null, null,		// selection, args 
//				null, 			// group by
//				null, 		// having
//				orderBy);
//
//		/******************************
//			validate: null
//		 ******************************/
//		if (c == null) {
//
//			// Log
//			String msg_Log = "query => null";
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			return null;
//			
//		}
//		
//		/******************************
//			validate: any entry?
//		 ******************************/
//		if (c.getCount() < 1) {
//
//			// Log
//			String msg_Log = "entry => < 1";
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			return null;
//			
//		}
//		
//		////////////////////////////////
//
//		// get: data
//
//		////////////////////////////////
//		c.moveToFirst();
//		
//		String lastRefreshed = c.getString(3);
//		
//		return lastRefreshed;
////		return Methods.conv_TimeLabel_to_MillSec(lastRefreshed);
//		
////		return 0;
//		
//	}//_refresh_MainDB__Get_LastRefreshed
//
//	private static boolean 
//	_refresh_MainDB__Setup_RefreshLog
//	(Activity actv, SQLiteDatabase wdb, DBUtils dbu) {
//		
//		
//		boolean result = dbu.tableExists(wdb, CONS.DB.tname_RefreshLog);
//		
//		if (result != false) {
//			
//			// Log
//			String msg_Log = "table exists => " + CONS.DB.tname_RefreshLog;
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			return true;
//			
//		}
//		
//		////////////////////////////////
//
//		// create: table
//
//		////////////////////////////////
//		result = dbu.createTable(
//					wdb, 
//					CONS.DB.tname_RefreshLog, 
//					CONS.DB.col_names_refresh_log, 
//					CONS.DB.col_types_refresh_log);
//
//		if (result == true) {
//			// Log
//			Log.d("Methods.java"
//				+ "["
//				+ Thread.currentThread().getStackTrace()[2]
//				.getLineNumber() + "]", "Table created => " + CONS.DB.tname_RefreshLog);
//			
//			return true;
//			
//		} else {//if (result == true)
//			
//			// Log
//			Log.d("Methods.java"
//				+ "["
//				+ Thread.currentThread().getStackTrace()[2]
//				.getLineNumber() + "]", 
//				"Create table failed: " + CONS.DB.tname_RefreshLog);
//		
//			return false;
//			
//		}//if (result == true)
//		
//	}//_refresh_MainDB__Setup_RefreshLog

	public static void 
	drop_Table
	(Activity actv, 
			Dialog dlg1, Dialog dlg2, String tname) {
		
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		boolean res = dbu.dropTable(actv, tname);

		////////////////////////////////

		// report

		////////////////////////////////
		if (res == true) {
			
			dlg2.dismiss();
			dlg1.dismiss();
			
			String msg = "Table dropped => " + tname;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
		} else {

			dlg2.dismiss();
			
			String msg = "Can't drop table => " + tname;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
		}
		
	}//drop_Table

	public static String
	conv_MillSec_to_TimeLabel(long millSec)
	{
		//REF http://stackoverflow.com/questions/7953725/how-to-convert-milliseconds-to-date-format-in-android answered Oct 31 '11 at 12:59
		String dateFormat = CONS.Admin.format_Date;
//		String dateFormat = "yyyy/MM/dd hh:mm:ss.SSS";
		
		DateFormat formatter = new SimpleDateFormat(dateFormat, Locale.JAPAN);
//		DateFormat formatter = new SimpleDateFormat(dateFormat);

		// Create a calendar object that will convert the date and time value in milliseconds to date. 
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTimeInMillis(millSec);
		
		return formatter.format(calendar.getTime());
		
	}//conv_MillSec_to_TimeLabel(long millSec)

	public static long
	conv_TimeLabel_to_MillSec(String timeLabel)
//	conv_MillSec_to_TimeLabel(long millSec)
	{
//		String input = "Sat Feb 17 2012";
		Date date;
		try {
			date = new SimpleDateFormat(
						CONS.Admin.format_Date, Locale.JAPAN).parse(timeLabel);
			
			return date.getTime();
//			long milliseconds = date.getTime();
			
		} catch (ParseException e) {
			
//			e.printStackTrace();
			// Log
			String msg_Log = "Exception: " + e.toString();
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return -1;
			
		}
		
//		Locale.ENGLISH).parse(input);
		
//		Date date = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH).parse(input);
//		long milliseconds = date.getTime();
		
	}//conv_TimeLabel_to_MillSec(String timeLabel)

	private static boolean 
	updateRefreshLog
	(Activity actv, 
			SQLiteDatabase wdb, DBUtils dbu, 
			long lastItemDate, int numOfItemsAdded) {
		////////////////////////////////

		// validate: Table exists?

		////////////////////////////////
		String tableName = CONS.DB.tname_RefreshLog;
		
		if(!dbu.tableExists(wdb, tableName)) {
		
			Log.d("Methods.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Table doesn't exitst: " + tableName);
		
			/*----------------------------
			* 2. If no, create one
			----------------------------*/
			if(dbu.createTable(
					wdb, tableName, 
					CONS.DB.col_names_refresh_log, 
					CONS.DB.col_types_refresh_log)) {
				
				//toastAndLog(actv, "Table created: " + tableName, Toast.LENGTH_LONG);
				
				// Log
				Log.d("Methods.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
				.getLineNumber() + "]", "Table created: " + tableName);
			
			} else {//if
				/*----------------------------
				* 2-2. Create table failed => Return
				----------------------------*/
				// Log
				Log.d("Methods.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
				.getLineNumber() + "]", "Create table failed: " + tableName);
				
				
				return false;
			
			}//if
		
		} else {//if(dbu.tableExists(wdb, ImageFileManager8Activity.refreshLogTableName))
		
			// Log
			Log.d("Methods.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Table exitsts: " + tableName);
		
		
		}//if(dbu.tableExists(wdb, ImageFileManager8Activity.refreshLogTableName))
		
		////////////////////////////////

		// Insert data

		////////////////////////////////
		try {
			
			return dbu.insert_Data_RefreshDate(wdb, numOfItemsAdded);
			
//			return true;
			
		} catch (Exception e) {
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Insert data failed");
			
			return false;
		}
		
	}//private static boolean updateRefreshLog(SQLiteDatabase wdb, long lastItemDate)

	public static String
	conv_CurrentPath_to_TableName(String currentPath)
	{
		String full = currentPath;
//		String full = CONS.Paths.dpath_Storage_Sdcard + CONS.Paths.dname_Base;
		
		////////////////////////////////

		// Get: raw strings

		////////////////////////////////
		String head = CONS.Paths.dpath_Storage_Sdcard;
		
		int len = head.length();
		
		// Log
		String msg_Log = "full => " + full;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		String target = null;
		
		try {
			
			target = full.substring(len + 1);
			
		} catch (Exception e) {
			// TODO: handle exception
			// Log
			msg_Log = "Exception. Returning the base table name => "
					+ CONS.DB.tname_IFM11;
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			e.printStackTrace();
			
			return CONS.DB.tname_IFM11;
			
		}

//		// Log
//		String msg_log = "full = " + full
//						+ " // "
//						+ "target = " + target;
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_log);

		////////////////////////////////

		// Split: target

		////////////////////////////////
//		// Log
//		String msg_log = "File.separator = " + File.separator;
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_log);
		
		String[] tokens = target.split(File.separator);
		
		////////////////////////////////

		// Build: table name

		////////////////////////////////
		if (tokens == null) {
			
			// Log
			String msg_log = "Split => returned null";
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return null;
			
		} else if (tokens.length == 1) {
			
			return target;
			
		} else {

			return StringUtils.join(tokens, CONS.DB.jointString_TableName);
			
		}
		
	}//conv_CurrentPath_to_TableName(String currentPath)

	public static String 
	conv_CurrentPathMove_to_TableName
	(String choice) {
		
		
		String[] tokens = choice.split(File.separator);
		
		/******************************
			validate: null
		 ******************************/
		if (tokens == null) {
			
			// Log
			String msg_Log = "tokens => null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return choice;
			
		}
		
		////////////////////////////////

		// size => 1

		////////////////////////////////
		if (tokens.length == 1) {
			
			return tokens[0];
			
		}

		////////////////////////////////

		// size > 1

		////////////////////////////////
		return StringUtils.join(tokens, CONS.DB.jointString_TableName);
		
	}//conv_CurrentPathMove_to_TableName

	public static boolean 
	file_Exists
	(Activity actv, String fpath) {
		
		
		File f = new File(fpath);
		
		return f.exists();
		
	}//file_Exists

//	/******************************
//		@return
//			false => 1. No db file<br>
//	 ******************************/
//	public static boolean 
//	import_DB
//	(Activity actv, Dialog dlg1) {
//		
//		
//		////////////////////////////////
//
//		// setup: src, dst
//
//		////////////////////////////////
//		// IFM10
//		String src_dir = CONS.DB.dPath_dbFile_backup_IFM10;
////		String src_dir = CONS.DB.dPath_dbFile_backup;
//		
//		File f_dir = new File(src_dir);
//		
//		File[] src_dir_files = f_dir.listFiles();
//		
//		// If no files in the src dir, quit the method
//		if (src_dir_files.length < 1) {
//			
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread()
//						.getStackTrace()[2].getLineNumber()
//					+ "]", "No files in the dir: " + src_dir);
//			
//			return false;
//			
//		}//if (src_dir_files.length == condition)
//		
//		// Latest file
//		File f_src_latest = src_dir_files[0];
//		
//		
//		for (File file : src_dir_files) {
//			
//			if (f_src_latest.lastModified() < file.lastModified()) {
//						
//				f_src_latest = file;
//				
//			}//if (variable == condition)
//			
//		}//for (File file : src_dir_files)
//		
//		// Show the path of the latest file
//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "f_src_latest=" + f_src_latest.getAbsolutePath());
//		
//		////////////////////////////////
//
//		// Restore file
//
//		////////////////////////////////
//		String src = f_src_latest.getAbsolutePath();
//		
//		String dst = StringUtils.join(
//				new String[]{
//						//REF http://stackoverflow.com/questions/9810430/get-database-path answered Jan 23 at 11:24
//						actv.getDatabasePath(CONS.DB.dbName).getPath()
//				},
////						actv.getFilesDir().getPath() , 
////						CONS.DB.dbName},
//				File.separator);
//		
//		// Log
//		String msg_Log = "db path => " 
//					+ actv.getDatabasePath(CONS.DB.dbName).getPath();
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		////////////////////////////////
//
//		// build: db file path (dst)
//
//		////////////////////////////////
//		String tmp_str = Methods.get_Dirname(actv, dst);
//		
//		String dst_New = StringUtils.join(
//					new String[]{
//							
//							tmp_str,
//							CONS.DB.dbName_IFM10
//							
//					}, 
//					File.separator);
//		
//		// Log
//		msg_Log = String.format(Locale.JAPAN,
//							"src = %s // dst = %s", 
//							src, dst_New);
//		
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		////////////////////////////////
//
//		// import (using restoration-related method)
//
//		////////////////////////////////
//		boolean res = Methods.restore_DB(
//							actv, 
//							CONS.DB.dbName, 
//							src, dst_New);
//		
//		// Log
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "res=" + res);
//		
//		////////////////////////////////
//
//		// dismiss: dlg
//
//		////////////////////////////////
//		dlg1.dismiss();
//		
//		////////////////////////////////
//
//		// return
//
//		////////////////////////////////
//		return res;
//
////		return false;
//		
//	}//import_DB
//
//	/******************************
//		@return
//			false => 1. No db file<br>
//	 ******************************/
//	public static boolean 
//	import_DB
//	(Activity actv, String fpath_DB) {
//		
//		////////////////////////////////
//		
//		// Restore file
//		
//		////////////////////////////////
//		String src = fpath_DB;
//		
//		String dst = actv.getDatabasePath(CONS.DB.dbName).getPath();
//		
//		// Log
//		String msg_Log = "db path => " 
//				+ actv.getDatabasePath(CONS.DB.dbName).getPath();
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		////////////////////////////////
//		
//		// build: db file path (dst)
//		
//		////////////////////////////////
//		String tmp_str = Methods.get_Dirname(actv, dst);
//		
//		String dst_New = StringUtils.join(
//				new String[]{
//						
//						tmp_str,
//						CONS.DB.dbName_Previous
//						
//				}, 
//				File.separator);
//		
//		// Log
//		msg_Log = String.format(Locale.JAPAN,
//				"src = %s // dst = %s", 
//				src, dst_New);
//		
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		////////////////////////////////
//		
//		// import (using restoration-related method)
//		
//		////////////////////////////////
////		boolean res = true;
//		boolean res = Methods.restore_DB(
//				actv, 
//				CONS.DB.dbName, 
//				src, dst_New);
//		
//		// Log
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "res=" + res);
//		
//		////////////////////////////////
//		
//		// return
//		
//		////////////////////////////////
//		return res;
//		
////		return false;
//		
//	}//import_DB
	
	public static String 
	get_Dirname
	(Activity actv, String target) {

		String[] tokens = target.split(File.separator);
	
		////////////////////////////////
		
		// tokens => null
		
		////////////////////////////////
		if (tokens == null) {
			
			// Log
			String msg_Log = "tokens => null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return target;
			
		}
		
		////////////////////////////////

		// tokens => 1

		////////////////////////////////
		if (tokens.length == 1) {
			
			return target;
			
		}
		
		////////////////////////////////

		// tokens > 1

		////////////////////////////////
		String[] tokens_New = Arrays.copyOfRange(tokens, 0, tokens.length - 1);
		
		return StringUtils.join(tokens_New, File.separator);
	
	}//get_Dirname

	/****************************
	 * deleteDirectory(File target)()
	 * 
	 * 1. REF=> http://www.rgagnon.com/javadetails/java-0483.html
		****************************/
	public static boolean 
	deleteDirectory
	(File target) {
		
		if(target.exists()) {
			//
			File[] files = target.listFiles();
			
			//
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					
					deleteDirectory(files[i]);
					
				} else {//if (files[i].isDirectory())
					
					String path = files[i].getAbsolutePath();
					
					files[i].delete();
					
					// Log
					Log.d("Methods.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", "Removed => " + path);
					
					
				}//if (files[i].isDirectory())
				
			}//for (int i = 0; i < files.length; i++)
			
		}//if(target.exists())
		
		return (target.delete());
	}//public static boolean deleteDirectory(File target)

	public static boolean drop_Table
	(Activity actv, String tname) {
		

		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		return dbu.dropTable(actv, tname);
		
	}

	/******************************
		@return null => 1. dpath_Target ==> Dir doesn't exist<br>
						2. listFiles ==> returned null
	 ******************************/
	public static List<String> get_DirList(String dpath_Target) {
		/*********************************
		 * 1. Directory exists?
		 * 2. Build list
		 * 2-1. Sort list
		 * 
		 * 3. Return
		 *********************************/
		File dir_Target = new File(dpath_Target);
		
		////////////////////////////////
		
		// Directory exists?
		
		////////////////////////////////
		
		if (!dir_Target.exists()) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Dir doesn't exist");
			
			return null;
			
		} else {//if (!dpath.exists() == condition)
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Dir exists: " + dir_Target.getAbsolutePath());
			
		}//if (!dpath.exists() == condition)
		
		////////////////////////////////
		
		// Get: Dir list (Directories only)
		
		////////////////////////////////
		List<String> list_Dir = new ArrayList<String>();
		
		File[] files_list = dir_Target.listFiles(new FileFilter(){
	
			@Override
			public boolean accept(File f) {
				
				return f.isDirectory();
				
			}
			
		});
		
		if (files_list == null) {
			
			// Log
			String msg_log = "listFiles() => returned null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return null;
			
		}
		
		////////////////////////////////
		
		// Sort list
		
		////////////////////////////////
		
		Methods.sort_list_files(files_list);
		
		for (File f : files_list) {
			
			list_Dir.add(f.getName());
			
		}//for (File f : files_list)
		
		/*********************************
		 * 3. Return
		 *********************************/
		return list_Dir;
		
	}//public static List<String> get_file_list(File dpath)

	public static CharSequence conv_CurrentPath_to_DisplayPath(String path) {
		
		
		String head = CONS.Paths.dpath_Storage_Sdcard;
		
		int len = head.length();
		
		// Log
		String msg_Log = "head => " + head;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
//		String target = null;
		
		try {
			
			return head.substring(len + 1);
//			target = head.substring(len + 1);
			
		} catch (Exception e) {
			// TODO: handle exception
			// Log
			msg_Log = "Exception. Returning the base table name => "
					+ CONS.DB.tname_IFM11;
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			e.printStackTrace();
			
			return CONS.DB.tname_IFM11;
			
		}

		
//		return path.substring(len + 1);
		
	}

	public static String 
	conv_CurrentPathMove_to_CurrentPathMove_New
	(String curPath_Move) {
		
		
		String[] tokens = curPath_Move.split(File.separator);
		
		////////////////////////////////

		// tokens == 1

		////////////////////////////////
		if (tokens == null) {
			
			return curPath_Move;
			
		} else if (tokens.length == 1) {
			
			return curPath_Move;
			
		}
		
		////////////////////////////////

		// tokens > 1

		////////////////////////////////
		int len = tokens.length;
		
		String[] tokens_New = Arrays.copyOfRange(tokens, 0, len - 1);
//		String[] tokens_New = Arrays.copyOfRange(tokens, 0, len - 2);
		
		return StringUtils.join(tokens_New, File.separator);
		
	}//conv_CurrentPathMove_to_CurrentPathMove_New

	public static void 
	exec_Sql
	(Activity actv, 
		Dialog dlg1, Dialog dlg2,
		String sql_Type) {
		
		////////////////////////////////

		// dispatch

		////////////////////////////////
		// Log
		String msg_Log = "sql_Type => " + sql_Type;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
//		if (sql_Type.equals(
//				CONS.DB.Sqls._20140817_154650_addCol_IFM11_UpdatedAt_TITLE)) {
//			
//			Methods._exec_Sql__AddCol_IFM11(actv, dlg1, dlg2, sql_Type);
//			
//		} else {
//
//			// Log
//			msg_Log = "Unknown case => " + sql_Type;
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//		}
		
		
		
	}//exec_Sql

	//REF http://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-timeouts answered Oct 24 '10 at 16:28
	public static boolean isOnline(Activity actv) {
	    ConnectivityManager cm =
	        (ConnectivityManager) actv.getSystemService(Context.CONNECTIVITY_SERVICE);
	    
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	    	
	        return true;
	        
	    }
	    
	    return false;
	    
	}

	public static void 
	write_Log
	(Activity actv, String message,
			String fileName, int lineNumber) {
		
		
		////////////////////////////////

		// file

		////////////////////////////////
		File fpath_Log = new File(CONS.DB.dPath_Log, CONS.DB.fname_Log);
		
		////////////////////////////////

		// file exists?

		////////////////////////////////
		if (!fpath_Log.exists()) {
			
			try {
				
				fpath_Log.createNewFile();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
				String msg = "Can't create a log file";
				Methods_dlg.dlg_ShowMessage_Duration(actv, msg, R.color.gold2);
				
				return;
				
			}
		} else {
			
			// Log
			String msg_Log = "log file => exists";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		////////////////////////////////

		// validate: size

		////////////////////////////////
		long len = fpath_Log.length();
		
		if (len > CONS.DB.logFile_MaxSize) {
		
			fpath_Log.renameTo(new File(
						fpath_Log.getParent(), 
						CONS.DB.fname_Log_Trunk
						+ "_"
//						+ Methods.get_TimeLabel(STD.getMillSeconds_now())
						+ Methods.get_TimeLabel(fpath_Log.lastModified())
						+ CONS.DB.fname_Log_ext
						));
			
			// new log.txt
			try {
				
				fpath_Log = new File(fpath_Log.getParent(), CONS.DB.fname_Log);
//				File f = new File(fpath_Log.getParent(), CONS.DB.fname_Log);
				
				fpath_Log.createNewFile();
				
				// Log
				String msg_Log = "new log.txt => created";
				Log.d("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				// Log
				String msg_Log = "log.txt => can't create!";
				Log.e("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				e.printStackTrace();
				
				return;
				
			}
			
		}//if (len > CONS.DB.logFile_MaxSize)
		
		////////////////////////////////

		// write

		////////////////////////////////
		try {
			
			//REF append http://stackoverflow.com/questions/8544771/how-to-write-data-with-fileoutputstream-without-losing-old-data answered Dec 17 '11 at 12:37
			FileOutputStream fos = new FileOutputStream(fpath_Log, true);
//			FileOutputStream fos = new FileOutputStream(fpath_Log);
			
			String text = String.format(Locale.JAPAN,
							"[%s] [%s : %d] %s\n", 
							Methods.conv_MillSec_to_TimeLabel(
											Methods.getMillSeconds_now()),
							fileName, lineNumber,
							message
						);
			
			//REF getBytes() http://www.adakoda.com/android/000240.html
			fos.write(text.getBytes());
//			fos.write(message.getBytes());
			
//			fos.write("\n".getBytes());
			
			fos.close();
			
			// Log
			String msg_Log = "log => written";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
//			FileChannel oChannel = new FileOutputStream(fpath_Log).getChannel();
//			
//			oChannel.wri
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}//write_Log

	public static List<String> 
	get_LogLines
	(Activity actv, String fpath_LogFile) {
		
		
		int count_Lines = 0;
		int count_Read = 0;
		
		List<String> list = new ArrayList<String>();
		
//		File f = new File(fpath_LogFile);
		
		try {
			
//			fis = new FileInputStream(fpath_Log);

			//REF BufferedReader http://stackoverflow.com/questions/7537833/filereader-for-text-file-in-android answered Sep 24 '11 at 8:29
			BufferedReader br = new BufferedReader(
						new InputStreamReader(new FileInputStream(fpath_LogFile)));
//			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			String line = null;
			
			line = br.readLine();
					
			while(line != null) {
				
				list.add(line);
				
				count_Lines += 1;
				count_Read += 1;
				
				line = br.readLine();
				
			}
			
			////////////////////////////////

			// close

			////////////////////////////////
			br.close();
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
			String msg = "FileNotFoundException";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			return null;
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			count_Lines += 1;
			
		}

		// Log
		String msg_Log = String.format(
							Locale.JAPAN,
							"count_Lines => %d / count_Read => %d", 
							count_Lines, count_Read);
		
		Log.d("ShowLogActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		
		return list;
		
	}//get_LogLines

	/******************************
		@return
			null => 1. Log file => doesn't exist<br>
			//REF http://stackoverflow.com/questions/2290757/how-can-you-escape-the-character-in-javadoc answered Dec 11 '11 at 11:11<br>
			2. {@literal List<String>} list => null<br>
			3. list_LogItem => null<br>
	 ******************************/
	public static List<LogItem> 
	get_LogItem_List
	(Activity actv) {
		
		
		String msg_Log;
		
		////////////////////////////////

		// validate: files exists

		////////////////////////////////
		File fpath_Log = new File(
				CONS.DB.dPath_Log,
				CONS.ShowLogActv.fname_Target_LogFile);
		
		if (!fpath_Log.exists()) {
			
			String msg = "Log file => doesn't exist";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			return null;
			
		}
		
		////////////////////////////////

		// read file

		List<String> list = 
						Methods.get_LogLines(actv, fpath_Log.getAbsolutePath());
		
		/******************************
			validate
		 ******************************/
		if (list == null) {
			
			return null;
			
		} else {
			
			////////////////////////////////
			
			// list => reverse
			
			////////////////////////////////
			Collections.reverse(list);
			
			////////////////////////////////

			// add all

			////////////////////////////////
			CONS.ShowLogActv.list_RawLines.addAll(list);
			
		}

		////////////////////////////////

		// build: LogItem list

		////////////////////////////////
		List<LogItem> list_LogItem = 
				Methods.conv_LogLinesList_to_LogItemList(
									actv, CONS.ShowLogActv.list_RawLines);

		/******************************
			validate
		 ******************************/
		if (list_LogItem == null) {
			
			// Log
			msg_Log = "list_LogItem => null";
			Log.e("ShowLogActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return null;
			
		} else {

			// Log
			msg_Log = "list_LogItem => not null"
						+ "(" + list_LogItem.size() + ")";
			Log.d("ShowLogActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
//			CONS.ShowLogActv.list_ShowLog_Files.addAll(list_LogItem);
			
			return list_LogItem;
			
		}
		
	}//get_LogItem_List

	public static String 
	get_DB_path
	(Activity actv) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// Get the absolute path of the latest backup file

		////////////////////////////////
		// Get the most recently-created db file
		String src_dir = CONS.DB.dPath_dbFile_backup;
		
		File f_dir = new File(src_dir);
		
		File[] src_dir_files = f_dir.listFiles();
		
		// If no files in the src dir, quit the method
		if (src_dir_files.length < 1) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread()
						.getStackTrace()[2].getLineNumber()
					+ "]", "No files in the dir: " + src_dir);
			
			return null;
			
		}//if (src_dir_files.length == condition)
		
		// Latest file
		File f_src_latest = src_dir_files[0];
		
		
		for (File file : src_dir_files) {
			
			if (f_src_latest.lastModified() < file.lastModified()) {
						
				f_src_latest = file;
				
			}//if (variable == condition)
			
		}//for (File file : src_dir_files)
		
		// Show the path of the latest file
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "f_src_latest=" + f_src_latest.getAbsolutePath());
		
		/*********************************
		 * Restore file
		 *********************************/
		return f_src_latest.getAbsolutePath();
				
	}//get_DB_path

	public static boolean 
	is_SpecialChars
	(Activity actv, String w) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// build: list

		////////////////////////////////
		List<String> list_Specials = new ArrayList<String>();

		for (String chr : CONS.Admin.special_Chars) {
			
			list_Specials.add(chr);
			
		}
		
		////////////////////////////////

		// judge

		////////////////////////////////
		return list_Specials.contains(w) ? true : false;
		
//		return false;
		
	}//if(Methods.is_SpecialChars(actv, w))

	public static List<String> 
	get_LogFileNames_List
	(Activity actv) {
		// TODO Auto-generated method stub
		
		File dir_Log = new File(CONS.DB.dPath_Log);
		
		/******************************
			validate: exists
		 ******************************/
		if (!dir_Log.exists()) {

			boolean res = dir_Log.mkdirs();
			
			if (res == true) {
				
				// Log
				String msg_Log = "Log dir => created: " + dir_Log.getAbsolutePath();
				Log.d("LogActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			} else {

				// Log
				String msg_Log = "Log dir => not created: " + dir_Log.getAbsolutePath();
				Log.e("LogActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				String msg = "Log dir doesn't exist\nCan't be created";
				
				// Log
				Log.d("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg);
//				Methods_dlg.dlg_ShowMessage(this, msg, R.color.red);
				
				return null;
				
			}
			
		}
		
		////////////////////////////////

		// get: files list

		////////////////////////////////
		String[] list_LogFiles = dir_Log.list();
		
		/******************************
			validate: any log files
		 ******************************/
		if (list_LogFiles == null || list_LogFiles.length < 1) {
			
			String msg = "Log files => doesn't exist";
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg);

			return null;
			
		}
		
		////////////////////////////////

		// build: list

		////////////////////////////////
		List<String> list = new ArrayList<String>();
		
		for (String name : list_LogFiles) {
			
			list.add(name);
			
		}
		
		////////////////////////////////

		// modify list

		////////////////////////////////
		if (list.contains(CONS.DB.fname_Log)) {
			
			list.remove(CONS.DB.fname_Log);
			
			list.add(0, CONS.DB.fname_Log);
			
		}
		
		
		return list;
		
	}//get_LogFileNames_List

	/****************************************
	 *	getMillSeconds_now()
	 * 
	 * <Caller> 
	 * 1. ButtonOnClickListener # case main_bt_start
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static long getMillSeconds_now() {
		
		Calendar cal = Calendar.getInstance();
		
		return cal.getTime().getTime();
		
	}//private long getMillSeconds_now(int year, int month, int date)

	public static List<LogItem> 
	conv_LogLinesList_to_LogItemList
	(Activity actv, List<String> list_RawLines) {
		
		String msg_Log;
		
		List<LogItem> list_LogItems = new ArrayList<LogItem>();
		
		String reg = "\\[(.+?)\\] \\[(.+?)\\] (.+)";
//		String reg = "\\[(.+)\\] \\[(.+)\\] (.+)";
		
		Pattern p = Pattern.compile(reg);
		
		Matcher m = null;
		
		LogItem loi = null;
		
		for (String string : list_RawLines) {
			
			m = p.matcher(string);
			
			if (m.find()) {

				loi = _build_LogItem_from_Matcher(actv, m);
				
				if (loi != null) {
					
					list_LogItems.add(loi);
					
				}
				
			}//if (m.find())
			
		}//for (String string : list_RawLines)
		
		/******************************
			validate
		 ******************************/
		if (list_LogItems.size() < 1) {
			
			// Log
			msg_Log = "list_LogItems.size() => " + list_LogItems.size();
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
		}
		
		return list_LogItems;
		
	}//conv_LogLinesList_to_LogItemList

	/******************************
		@return
			null => Matcher.groupCount() != 3
	 ******************************/
	private static LogItem 
	_build_LogItem_from_Matcher
	(Activity actv, Matcher m) {
		
	
		////////////////////////////////
	
		// validate
	
		////////////////////////////////
		if (m.groupCount() != 3) {
			
			return null;
			
		}
		
		////////////////////////////////
	
		// prep: data
	
		////////////////////////////////
		String[] tokens_TimeLabel = m.group(1).split(" ");
		
		String[] tokens_FileInfo = m.group(2).split(" : ");
		
		String text = m.group(3);
		
		String date = tokens_TimeLabel[0];
		
		String time = tokens_TimeLabel[1].split("\\.")[0];
		
		String fileName = tokens_FileInfo[0];
		
		String line = tokens_FileInfo[1];
		
		////////////////////////////////
	
		// LogItem
	
		////////////////////////////////
		LogItem loi = new LogItem.Builder()
					
					.setDate(date)
					.setTime(time)
					.setMethod(fileName)
					.setLine(Integer.parseInt(line))
					.setText(text)
					.build();
		
		return loi;
		
	}//_build_LogItem_from_Matcher

	public static void 
	start_Activity_ShowLogActv
	(Activity actv, String itemName) {
		
		
		// Log
		String msg_Log = "itemName => " + itemName;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		Intent i = new Intent();
		
		i.setClass(actv, ShowLogActv.class);

		i.putExtra(CONS.Intent.iKey_LogActv_LogFileName, itemName);
		
		i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		
		actv.startActivity(i);
		
		
	}//start_Activity_LogActv

	public static void 
	start_Activity_LogActv
	(Activity actv) {
		
		Intent i = new Intent();
		
		i.setClass(actv, LogActv.class);
		
		i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		
		actv.startActivity(i);
		
		
	}//start_Activity_LogActv
	
	public static void 
	start_Activity_CompassActv
	(Activity actv) {
		
		Intent i = new Intent();
		
		i.setClass(actv, CompassActv.class);
		
		i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		
		actv.startActivity(i);
		
	}//start_Activity_LogActv
	
}//public class Methods
