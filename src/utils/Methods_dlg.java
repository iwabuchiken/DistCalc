package utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;

import utils.Tags.DialogTags;
import dc.adapters.Adp_ListItems;
import dc.items.ListItem;
import dc.listeners.dialog.DB_OCL;
import dc.listeners.dialog.DB_OTL;
import dc.listeners.dialog.DOI_CL;
import dc.main.R;
import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Methods_dlg {

	public static void 
	dlg_Db_Actv
	(Activity actv) {
		/****************************
		 * 1. Dialog
		 * 2. Prep => List
		 * 3. Adapter
		 * 4. Set adapter
		 * 
		 * 5. Set listener to list
		 * 6. Show dialog
			****************************/
		Dialog dlg1 = Methods_dlg.dlg_Template_Cancel(
									actv, R.layout.dlg_tmpl_list_cancel, 
									R.string.dlg_db_admin_title, 
									R.id.dlg_tmpl_list_cancel_bt_cancel, 
//									R.id.dlg_db_admin_bt_cancel, 
									Tags.DialogTags.GENERIC_DISMISS);
		
		//test
		// Log
		String msg_Log = "actv => " + actv.getClass().getName();
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		/****************************
		 * 2. Prep => List
			****************************/
		List<ListItem> list = _dlg_Db_Actv__Get_ItemList(actv);
//		String[] choices = {
//
//				////////////////////////////////
//
//				// DB
//
//				////////////////////////////////
//				actv.getString(R.string.dlg_db_admin_item_backup_db),
//				actv.getString(R.string.dlg_db_admin_item_refresh_db),
//				
//				actv.getString(R.string.dlg_db_admin_item_upload_db),
//				
//				////////////////////////////////
//
//				// drop/create tables
//
//				////////////////////////////////
//				actv.getString(R.string.dlg_db_admin_item_drop_table_ifm11),
//				actv.getString(R.string.dlg_db_admin_item_create_table_ifm11),
//				
//				actv.getString(
//							R.string.dlg_db_admin_item_drop_table_refresh_log),
//				actv.getString(
//							R.string.dlg_db_admin_item_create_table_refresh_log),
//				
//				actv.getString(
//						R.string.dlg_db_admin_item_create_table_memo_patterns),
//				actv.getString(
//						R.string.dlg_db_admin_item_drop_table_memo_patterns),
//
//				////////////////////////////////
//
//				// others
//
//				////////////////////////////////
//				actv.getString(R.string.dlg_db_admin_item_restore_db),
//				
//				actv.getString(R.string.dlg_db_admin_item_import_db_file),
//				
//				actv.getString(R.string.dlg_db_admin_item_import_patterns),
//				
//				CONS.DB.Sqls._20140817_154650_addCol_IFM11_UpdatedAt_TITLE
////				actv.getString(R.string.dlg_db_admin_item_exec_sql)
////					+ "/"
////					+ actv.getString(R.string.dlg_db_admin_exec_sql_add_col)
//							,
//				
//					};
		
//		List<String> list = new ArrayList<String>();
//		
//		for (String item : choices) {
//			
//			list.add(item);
//			
//		}
		
		/****************************
		 * 3. Adapter
			****************************/
		Adp_ListItems adapter = new Adp_ListItems(
				actv,
//				R.layout.dlg_db_admin,
//				android.R.layout.simple_list_item_1,
				R.layout.list_row_simple_1,
				list
				);
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//				actv,
////				R.layout.dlg_db_admin,
////				android.R.layout.simple_list_item_1,
//				R.layout.list_row_simple_1,
//				list
//				);

		/****************************
		 * 4. Set adapter
			****************************/
		ListView lv = (ListView) dlg1.findViewById(R.id.dlg_tmpl_list_cancel_lv);
//		ListView lv = (ListView) dlg.findViewById(R.id.dlg_db_admin_lv);
		
		lv.setAdapter(adapter);
		
		/****************************
		 * 5. Set listener to list
			****************************/
		lv.setTag(Tags.DialogItemTags.DLG_DB_ADMIN_LV);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg1));
		
		/****************************
		 * 6. Show dialog
			****************************/
		dlg1.show();
		
		
	}//public static void dlg_db_activity(Activity actv)

	private static List<ListItem> 
	_dlg_Db_Actv__Get_ItemList
	(Activity actv) {
		// TODO Auto-generated method stub
		
		List<ListItem> list = new ArrayList<ListItem>();
		
		list.add(new ListItem.Builder()
					.setText(actv.getString(
								R.string.dlg_db_admin_item_backup_db))
					.setIconID(R.drawable.menu_icon_admin_32x32_blue)
					.setTextColor_ID(R.color.blue1)
					.build());

		list.add(new ListItem.Builder()
					.setText(actv.getString(
							R.string.dlg_db_admin_item_refresh_db))
							.setIconID(R.drawable.menu_icon_admin_32x32_brown)
							.setTextColor_ID(R.color.black)
							.build());
		
		list.add(new ListItem.Builder()
					.setText(actv.getString(
							R.string.dlg_db_admin_item_upload_db))
							.setIconID(R.drawable.menu_icon_admin_32x32_purple)
							.setTextColor_ID(R.color.purple4)
							.build());
		
		list.add(new ListItem.Builder()
					.setText(actv.getString(
							R.string.dlg_db_admin_item_operations))
							.setIconID(R.drawable.menu_icon_admin_32x32_yellow)
							.setTextColor_ID(R.color.yellow_dark)
							.build());
		
		list.add(new ListItem.Builder()
						.setText(actv.getString(
								R.string.dlg_db_admin_item_restore_db))
								.setIconID(R.drawable.menu_icon_admin_32x32_green)
								.setTextColor_ID(R.color.green4)
								.build());
		
		return list;
		
//		////////////////////////////////
//
//		// DB
//
//		////////////////////////////////
//		actv.getString(R.string.dlg_db_admin_item_backup_db),
//		actv.getString(R.string.dlg_db_admin_item_refresh_db),
//		
//		actv.getString(R.string.dlg_db_admin_item_upload_db),
//		
//		////////////////////////////////
//
//		// drop/create tables
//
//		////////////////////////////////
//		actv.getString(R.string.dlg_db_admin_item_drop_table_ifm11),
//		actv.getString(R.string.dlg_db_admin_item_create_table_ifm11),
//		
//		actv.getString(
//					R.string.dlg_db_admin_item_drop_table_refresh_log),
//		actv.getString(
//					R.string.dlg_db_admin_item_create_table_refresh_log),
//		
//		actv.getString(
//				R.string.dlg_db_admin_item_create_table_memo_patterns),
//		actv.getString(
//				R.string.dlg_db_admin_item_drop_table_memo_patterns),
//
//		////////////////////////////////
//
//		// others
//
//		////////////////////////////////
//		actv.getString(R.string.dlg_db_admin_item_restore_db),
//		
//		actv.getString(R.string.dlg_db_admin_item_import_db_file),
//		
//		actv.getString(R.string.dlg_db_admin_item_import_patterns),
//		
//		CONS.DB.Sqls._20140817_154650_addCol_IFM11_UpdatedAt_TITLE
////		actv.getString(R.string.dlg_db_admin_item_exec_sql)
////			+ "/"
////			+ actv.getString(R.string.dlg_db_admin_exec_sql_add_col)
//					,
//		
//			};

//		return null;
		
	}//_dlg_Db_Actv__Get_ItemList

	public static
	Dialog dlg_Template_Cancel
	(Activity actv, int layoutId, int titleStringId,
			int cancelButtonId, Tags.DialogTags cancelTag) {
		/****************************
		* Steps
		* 1. Set up
		* 2. Add listeners => OnTouch
		* 3. Add listeners => OnClick
		****************************/
		
		// 
		Dialog dlg = new Dialog(actv);
		
		//
		dlg.setContentView(layoutId);
		
		// Title
		dlg.setTitle(titleStringId);
		
		/****************************
		* 2. Add listeners => OnTouch
		****************************/
		//
		Button btn_cancel = (Button) dlg.findViewById(cancelButtonId);
		
		//
		btn_cancel.setTag(cancelTag);
		
		//
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg));
		
		/****************************
		* 3. Add listeners => OnClick
		****************************/
		//
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg));
		
		//
		//dlg.show();
		
		return dlg;
	
	}//public static Dialog dlg_template_okCancel()
	
	public static Dialog 
	dlg_Template_Cancel_SecondDialog
	(Activity actv, Dialog dlg1,
			int layoutId, int titleStringId,
			int cancelButtonId, Tags.DialogTags cancelTag) {
		/****************************
		 * Steps
		 * 1. Set up
		 * 2. Add listeners => OnTouch
		 * 3. Add listeners => OnClick
		 ****************************/
		
		// 
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(layoutId);
		
		// Title
		dlg2.setTitle(titleStringId);
		
		/****************************
		 * 2. Add listeners => OnTouch
		 ****************************/
		//
		Button btn_cancel = (Button) dlg2.findViewById(cancelButtonId);
		
		//
		btn_cancel.setTag(cancelTag);
		
		//
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		/****************************
		 * 3. Add listeners => OnClick
		 ****************************/
		//
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		//
		//dlg.show();
		
		return dlg2;
		
	}//public static Dialog dlg_template_okCancel()

	public static Dialog 
	dlg_Template_OkCancel_SecondDialog
	(Activity actv, Dialog dlg1,
			int layoutId, int titleStringId,
			
			int okButtonId, Tags.DialogTags okTag,
			int cancelButtonId, Tags.DialogTags cancelTag) {
		/****************************
		 * Steps
		 * 1. Set up
		 * 2. Add listeners => OnTouch
		 * 3. Add listeners => OnClick
		 ****************************/
		
		// 
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(layoutId);
		
		// Title
		dlg2.setTitle(titleStringId);
		
		////////////////////////////////
		
		// button: cancel
		
		////////////////////////////////
		Button btn_Ok = (Button) dlg2.findViewById(okButtonId);
		
		btn_Ok.setTag(okTag);
		
		btn_Ok.setOnTouchListener(new DB_OTL(actv, dlg1, dlg2));
		
		btn_Ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		////////////////////////////////

		// button: cancel

		////////////////////////////////
		Button btn_cancel = (Button) dlg2.findViewById(cancelButtonId);
		
		btn_cancel.setTag(cancelTag);
		
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg1, dlg2));
		
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		//
		//dlg.show();
		
		return dlg2;
		
	}//public static Dialog dlg_template_okCancel()
	
	public static Dialog 
	dlg_Template_Cancel_SecondDialog
	(Activity actv, Dialog dlg1,
			int layoutId, String title,
			int cancelButtonId, Tags.DialogTags cancelTag) {
		/****************************
		 * Steps
		 * 1. Set up
		 * 2. Add listeners => OnTouch
		 * 3. Add listeners => OnClick
		 ****************************/
		
		// 
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(layoutId);
		
		// Title
		dlg2.setTitle(title);
		
		/****************************
		 * 2. Add listeners => OnTouch
		 ****************************/
		//
		Button btn_cancel = (Button) dlg2.findViewById(cancelButtonId);
		
		//
		btn_cancel.setTag(cancelTag);
		
		//
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		/****************************
		 * 3. Add listeners => OnClick
		 ****************************/
		//
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		//
		//dlg.show();
		
		return dlg2;
		
	}//public static Dialog dlg_template_okCancel()
	
	public static void 
	conf_DropTable
	(Activity actv, Dialog dlg1, String tname) {
		// TODO Auto-generated method stub
		
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(R.layout.dlg_tmpl_confirm_simple);
		
		// Title
		dlg2.setTitle(R.string.generic_tv_confirm);
		
		////////////////////////////////

		// set: message

		////////////////////////////////
		TextView tv_Message = (TextView) dlg2.findViewById(
								R.id.dlg_tmpl_confirm_simple_tv_message);
		
		tv_Message.setText(actv.getString(
					R.string.dlg_db_admin_item_drop_table));

		////////////////////////////////
		
		// set: item name
		
		////////////////////////////////
		TextView tv_ItemName = (TextView) dlg2.findViewById(
				R.id.dlg_tmpl_confirm_simple_tv_item_name);
		
		tv_ItemName.setText(tname);
		
		////////////////////////////////

		// button: cancel

		////////////////////////////////
		Button btn_Cancel = (Button) dlg2.findViewById(
						R.id.dlg_tmpl_confirm_simple_btn_cancel);
		
		//
//		btn_Cancel.setTag(Tags.DialogTags.dlg_generic_dismiss_second_dialog);
		btn_Cancel.setTag(Tags.DialogTags.GENERIC_DISMISS_SECOND_DIALOG);
		
		//
		btn_Cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		btn_Cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, tname));
		
		////////////////////////////////
		
		// button: ok
		
		////////////////////////////////
		Button btn_Ok = (Button) dlg2.findViewById(
				R.id.dlg_tmpl_confirm_simple_btn_ok);
		
		//
		btn_Ok.setTag(Tags.DialogTags.DROP_TABLE_OK);
		
		//
		btn_Ok.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		btn_Ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, tname));
		
		////////////////////////////////

		// show

		////////////////////////////////
		dlg2.show();
		
	}//conf_DropTable
	
	public static void
	dlg_ShowMessage(Activity actv, String message) {
		
		Dialog dlg = Methods_dlg.dlg_Template_Cancel(
				actv, R.layout.dlg_tmpl_toast_ok, 
				R.string.generic_tv_confirm, 
				R.id.dlg_tmpl_toast_ok_bt_cancel, 
//				R.id.dlg_db_admin_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS);
		
		TextView tv_Message = 
				(TextView) dlg.findViewById(R.id.dlg_tmpl_toast_ok_tv_message);
		
		tv_Message.setText(message);
		
		dlg.show();
		
	}
	
	public static void
	dlg_ShowMessage
	(Activity actv, String message, int colorId) {
		
		Dialog dlg = Methods_dlg.dlg_Template_Cancel(
				actv, R.layout.dlg_tmpl_toast_ok, 
				R.string.generic_tv_confirm, 
				R.id.dlg_tmpl_toast_ok_bt_cancel, 
//				R.id.dlg_db_admin_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS);
		
		TextView tv_Message = 
				(TextView) dlg.findViewById(R.id.dlg_tmpl_toast_ok_tv_message);
		
		tv_Message.setText(message);
		
		////////////////////////////////

		// background

		////////////////////////////////
		tv_Message.setBackgroundColor(actv.getResources().getColor(colorId));
		
		dlg.show();
		
	}//dlg_ShowMessage

	public static void
	dlg_ShowMessage_SecondDialog
	(Activity actv, String message, Dialog dlg1) {
		
		Dialog dlg2 = Methods_dlg.dlg_Template_Cancel_SecondDialog(
				actv, dlg1,
				R.layout.dlg_tmpl_toast_ok, 
				R.string.generic_tv_confirm, 
				
				R.id.dlg_tmpl_toast_ok_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS_SECOND_DIALOG);
		
		TextView tv_Message = 
				(TextView) dlg2.findViewById(R.id.dlg_tmpl_toast_ok_tv_message);
		
		tv_Message.setText(message);
		
		dlg2.show();
		
	}

	
	
	

//	/******************************
//		@return 
//		1. Cursor returned null => parameter dlg<br>
//		2. Cursor has no entry => parameter dlg<br>
//		
//	 ******************************/
//	public static Dialog 
//	_dlg_AddMemo_Set_Gridview
//	(Activity actv, Dialog dlg) {
//		////////////////////////////////
//
//		// setup: db, view
//
//		////////////////////////////////
//		GridView gv = (GridView) dlg.findViewById(R.id.dlg_add_memos_gv);
//		
//		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
//		
//		SQLiteDatabase rdb = dbu.getReadableDatabase();
//
//		////////////////////////////////
//
//		// Table exists?
//
//		////////////////////////////////
//		String tableName = CONS.DB.tname_MemoPatterns;
//		
//		boolean res = dbu.tableExists(rdb, tableName);
//		
//		if (res == true) {
//			
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Table exists: " + tableName);
//			
//			rdb.close();
//			
////			return;
//			
//		} else {//if (res == false)
//			////////////////////////////////
//
//			// no table => creating one
//
//			////////////////////////////////
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Table doesn't exist: " + tableName);
//			
//			rdb.close();
//			
//			SQLiteDatabase wdb = dbu.getWritableDatabase();
//			
//			res = dbu.createTable(
//							wdb, 
//							tableName, 
//							CONS.DB.col_names_MemoPatterns, 
//							CONS.DB.col_types_MemoPatterns);
//			
//			if (res == true) {
//				// Log
//				Log.d("Methods.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", "Table created: " + tableName);
//				
//				wdb.close();
//				
//			} else {//if (res == true)
//				// Log
////				Log.d("Methods.java"
////						+ "["
////						+ Thread.currentThread().getStackTrace()[2]
////								.getLineNumber() + "]", "Create table failed: " + tableName);
//				
//				String msg = "Create table failed: " + tableName;
//				Methods_dlg.dlg_ShowMessage(actv, msg);
//				
//				
//				wdb.close();
//				
//				return dlg;
//				
//			}//if (res == true)
//
//			
//		}//if (res == false)
//		
//		
//		////////////////////////////////
//
//		// Get cursor
//
//		////////////////////////////////
//		rdb = dbu.getReadableDatabase();
//		
////		String sql = "SELECT * FROM " + tableName + " ORDER BY word ASC";
////		
////		Cursor c = rdb.rawQuery(sql, null);
////		
////		actv.startManagingCursor(c);
//		
//		// "word"
//		String orderBy = CONS.DB.col_names_MemoPatterns_full[3] + " ASC"; 
//		
//		Cursor c = rdb.query(
//						CONS.DB.tname_MemoPatterns,
//						CONS.DB.col_names_MemoPatterns_full,
//		//				CONS.DB.col_types_refresh_log_full,
//						null, null,		// selection, args 
//						null, 			// group by
//						null, 		// having
//						orderBy);
//
//		actv.startManagingCursor(c);
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
//			rdb.close();
//			
//			return dlg;
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
//			rdb.close();
//			
//			return dlg;
//			
//		}
//
//		////////////////////////////////
//
//		// cursor: move to first
//
//		////////////////////////////////
//		c.moveToFirst();
//		
//		////////////////////////////////
//
//		// Get list
//
//		////////////////////////////////
////		CONS.IMageActv.patternList = new ArrayList<String>();
////		List<String> patternList = new ArrayList<String>();
//
//		List<WordPattern> patternList = new ArrayList<WordPattern>();
//		
//		WordPattern wp = null;
//		
//		if (c.getCount() > 0) {
//			
//			for (int i = 0; i < c.getCount(); i++) {
//				
////				CONS.IMageActv.patternList.add(c.getString(3));	// 3 => "word"
////				patternList.add(c.getString(3));	// 3 => "word"
////				patternList.add(c.getString(1));
//		
//				wp = new WordPattern.Builder()
//				.setDb_Id(c.getLong(0))
//				.setCreated_at(c.getString(1))
//				.setModified_at(c.getString(2))
//				.setWord(c.getString(3))
//				.build();
//	
//				patternList.add(wp);
//
//				c.moveToNext();
//				
//			}//for (int i = 0; i < patternList.size(); i++)
//			
//		} else {//if (c.getCount() > 0)
//			
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "!c.getCount() > 0");
//			
//		}//if (c.getCount() > 0)
//		
//		
////		Collections.sort(CONS.IMageActv.patternList);
//////		Collections.sort(patternList);
////
////		// Log
////		String msg_Log = "CONS.IMageActv.patternList.size() => " 
////						+ CONS.IMageActv.patternList.size();
//////		String msg_Log = "patternList => " + patternList.size();
////		Log.d("Methods_dlg.java" + "["
////				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////				+ "]", msg_Log);
//
//		////////////////////////////////
//
//		// Adapter
//
//		////////////////////////////////
////		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
////		CONS.IMageActv.adp_ImageActv_GridView = new ArrayAdapter<String>(
////										actv,
////										R.layout.add_memo_grid_view,
////										CONS.IMageActv.patternList
//////										patternList
////										);
//		
//		Adp_WordPatterns adapter = new Adp_WordPatterns(
//				actv,
//				R.layout.add_memo_grid_view,
//				patternList
//				);
//
//		
////		gv.setAdapter(CONS.IMageActv.adp_ImageActv_GridView);
//		gv.setAdapter(adapter);
//
////		// Log
////		String msg_Log = "adapter.getCount() => " 
////					+ CONS.IMageActv.adp_ImageActv_GridView.getCount();
////		Log.d("Methods_dlg.java" + "["
////				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////				+ "]", msg_Log);
//		
//		////////////////////////////////
//
//		// Set listener
//
//		////////////////////////////////
//////		gv.setTag(DialogTags.dlg_add_memos_gv);
//		gv.setTag(Tags.DialogItemTags.DLG_ADD_MEMOS_GV);
////		
//		gv.setOnItemClickListener(new DOI_CL(actv, dlg));
////		gv.setOnItemClickListener(new DialogOnItemClickListener(actv, dlg));
////		
////		
////		// Log
////		Log.d("Methods.java" + "["
////				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////				+ "]", "GridView setup => Done");
//		
//		/*----------------------------
//		 * 8. Close db
//			----------------------------*/
//		rdb.close();
//		
//
//		////////////////////////////////
//
//		// return
//
//		////////////////////////////////
//		// Log
//		String msg_Log = "gridview => set";
//		Log.d("Methods_dlg.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
////		// Log
////		msg_Log = "patternList => " + CONS.IMageActv.patternList.size();
//////		msg_Log = "patternList => " + patternList.size();
////		Log.d("Methods_dlg.java" + "["
////				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////				+ "]", msg_Log);
//
//		// Log
//		msg_Log = "gv.getChildCount() => " + gv.getChildCount();
//		Log.d("Methods_dlg.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		return dlg;
//		
//	}//public static Dialog dlg_addMemo(Activity actv, long file_id, String tableName)

	public static Dialog 
	dlg_Template_Cancel
	(Activity actv,
			int layoutId, String title, 
			int cancelButtonId, DialogTags cancelTag) {
		// TODO Auto-generated method stub
		
		Dialog dlg = new Dialog(actv);
		
		//
		dlg.setContentView(layoutId);
		
		// Title
		dlg.setTitle(title);
		
		/****************************
		* 2. Add listeners => OnTouch
		****************************/
		//
		Button btn_cancel = (Button) dlg.findViewById(cancelButtonId);
		
		//
		btn_cancel.setTag(cancelTag);
		
		//
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg));
		
		/****************************
		* 3. Add listeners => OnClick
		****************************/
		//
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg));
		
		//
		//dlg.show();
		
		return dlg;		
	}//dlg_Template_Cancel

	public static void 
	conf_Delete_Folder
	(Activity actv, Dialog dlg1,
			String folderName, String dlg1_Choice) {
		// TODO Auto-generated method stub
		
		Dialog dlg2 = new Dialog(actv);

		// layout
//		dlg2.setContentView(R.layout.dlg_tmpl_confirm_simple);
		dlg2.setContentView(R.layout.dlg_tmpl_confirm_simple);
		
		// Title
		dlg2.setTitle(R.string.generic_tv_confirm);

		////////////////////////////////

		// Set: Message

		////////////////////////////////
		TextView tv_Message = (TextView) dlg2.findViewById(
//							R.id.dlg_tmpl_confirm_simple_tv_message);
							R.id.dlg_tmpl_confirm_simple_tv_message);
//							R.id.dlg_tmpl_confirm_simple_cb_tv_message);
		
//		tv_Message.setText(actv.getString(R.string.dlg_conf_delete_bm_tv_message));
		tv_Message.setText(actv.getString(
								R.string.dlg_actvmain_lv_delete_confirm_message));

		////////////////////////////////

		// Set: Folder name

		////////////////////////////////
		TextView tv_ItemName = (TextView) dlg2.findViewById(
							R.id.dlg_tmpl_confirm_simple_tv_item_name);
//							R.id.dlg_tmpl_confirm_simple_cb_tv_item_name);

		tv_ItemName.setText(folderName);

		////////////////////////////////

		// Add listeners => OnTouch

		////////////////////////////////
		Button btn_ok = (Button) dlg2.findViewById(
								R.id.dlg_tmpl_confirm_simple_btn_ok);
		
		Button btn_cancel = (Button) dlg2.findViewById(
								R.id.dlg_tmpl_confirm_simple_btn_cancel);
		
		//
		btn_ok.setTag(Tags.DialogTags.DLG_DELETE_FOLDER_CONF_OK);
		btn_cancel.setTag(Tags.DialogTags.GENERIC_DISMISS_SECOND_DIALOG);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg2));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		/****************************
		 * 4. Add listeners => OnClick
			****************************/
		//
		btn_ok.setOnClickListener(
					new DB_OCL(actv, dlg1, dlg2, folderName));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		/****************************
		 * 5. Show dialog
			****************************/
		dlg2.show();
		
	}//conf_DeleteFolder

	public static void 
	dlg_MoveFiles
	(Activity actv) {
		// TODO Auto-generated method stub

		////////////////////////////////

		// dialog

		////////////////////////////////
		Dialog dlg1 = Methods_dlg.dlg_Template_Cancel(
				actv, R.layout.dlg_tmpl_list_cancel, 
				R.string.dlg_move_files_title, 
				R.id.dlg_tmpl_list_cancel_bt_cancel, 
//				R.id.dlg_db_admin_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS);

		////////////////////////////////

		// Prep => List

		////////////////////////////////
		String[] choices = {
		
			actv.getString(R.string.dlg_move_files_item_folder),
			actv.getString(R.string.dlg_move_files_item_remote),
		
		};
		
		List<String> list = new ArrayList<String>();
		
		for (String item : choices) {
		
			list.add(item);
		
		}
		
		////////////////////////////////

		// Adapter

		////////////////////////////////
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				
					actv,
					R.layout.list_row_simple_1,
					list
					
		);
		
		/****************************
		* 4. Set adapter
		****************************/
		ListView lv = (ListView) dlg1.findViewById(R.id.dlg_tmpl_list_cancel_lv);
		
		lv.setAdapter(adapter);
		
		/****************************
		* 5. Set listener to list
		****************************/
//		lv.setTag(Tags.DialogItemTags.dlg_move_files);
		lv.setTag(Tags.DialogItemTags.DLG_MOVE_FILES);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg1));
		
		/****************************
		* 6. Show dialog
		****************************/
		dlg1.show();
		
	}

	public static void 
	conf_MoveFiles__Folder
	(Activity actv, 
		Dialog dlg1, Dialog dlg2, String choice) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// dialog

		////////////////////////////////
		Dialog dlg3 = 
				Methods_dlg.dlg_Tmpl_OkCancel_ThirdDialog(
						actv, 
						R.layout.dlg_tmpl_confirm_simple, 
						R.string.generic_tv_confirm, 
						
						R.id.dlg_tmpl_confirm_simple_btn_ok, 
						R.id.dlg_tmpl_confirm_simple_btn_cancel, 
						
						Tags.DialogTags.DLG_CONF_MOVE_FILES_FOLDER_OK, 
						Tags.DialogTags.GENERIC_DISMISS_THIRD_DIALOG, 
						
						dlg1, dlg2);
		
		////////////////////////////////

		// view: message

		////////////////////////////////
		TextView tv_Msg = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_tv_message);
		
		tv_Msg.setText(actv.getString(
								R.string.dlg_move_files_confirm_move_to_folder_msg));
		
		////////////////////////////////

		// view: item name

		////////////////////////////////
		TextView tv_ItemName = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_tv_item_name);
//		dlg_tmpl_confirm_simple_tv_message
		
		tv_ItemName.setText(choice);
		
		////////////////////////////////

		// show

		////////////////////////////////
		dlg3.show();
		
	}//conf_MoveFiles__Folder
	
	public static void 
	conf_MoveFiles__Folder_Top
	(Activity actv, 
			Dialog dlg1, Dialog dlg2) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// dialog
		
		////////////////////////////////
		Dialog dlg3 = 
				Methods_dlg.dlg_Tmpl_OkCancel_ThirdDialog(
						actv, 
						R.layout.dlg_tmpl_confirm_simple, 
						R.string.generic_tv_confirm, 
						
						R.id.dlg_tmpl_confirm_simple_btn_ok, 
						R.id.dlg_tmpl_confirm_simple_btn_cancel, 
						
						Tags.DialogTags.DLG_CONF_MOVE_FILES_FOLDER_TOP_OK, 
						Tags.DialogTags.GENERIC_DISMISS_THIRD_DIALOG, 
						
						dlg1, dlg2);
		
		////////////////////////////////
		
		// view: message
		
		////////////////////////////////
		TextView tv_Msg = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_tv_message);
		
		tv_Msg.setText(actv.getString(
				R.string.dlg_move_files_confirm_move_to_flolder_top_msg));
		
		////////////////////////////////
		
		// view: item name
		
		////////////////////////////////
		TextView tv_ItemName = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_tv_item_name);
//		dlg_tmpl_confirm_simple_tv_message
		
		tv_ItemName.setText(CONS.Paths.dname_Base);
		
		////////////////////////////////
		
		// show
		
		////////////////////////////////
		dlg3.show();
		
	}//conf_MoveFiles__Folder

	public static
	Dialog dlg_Tmpl_OkCancel_ThirdDialog
	(Activity actv, 
		int layoutId, int titleStringId,
		
		int okButtonId, int cancelButtonId,
		Tags.DialogTags okTag, Tags.DialogTags cancelTag,
		
		Dialog dlg1, Dialog dlg2) {
		/****************************
		 * Steps
		 * 1. Set up
		 * 2. Add listeners => OnTouch
		 * 3. Add listeners => OnClick
		 ****************************/
		
		// 
		Dialog dlg3 = new Dialog(actv);
		
		//
		dlg3.setContentView(layoutId);
		
		// Title
		dlg3.setTitle(titleStringId);
		
		/****************************
		 * 2. Add listeners => OnTouch
		 ****************************/
		//
		Button btn_ok = (Button) dlg3.findViewById(okButtonId);
		Button btn_cancel = (Button) dlg3.findViewById(cancelButtonId);
		
		//
		btn_ok.setTag(okTag);
		btn_cancel.setTag(cancelTag);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg3));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg3));
		
		/****************************
		 * 3. Add listeners => OnClick
		 ****************************/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, dlg3));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, dlg3));
		
		
		return dlg3;
		
	}//public static Dialog dlg_template_okCancel_SecondDialog()

	public static Dialog 
	dlg_Tmpl_OkCancel
	(Activity actv, 
		int layoutId, int titleStringId,
		int okButtonId, int cancelButtonId, 
		DialogTags okTag, DialogTags cancelTag) {
		/*----------------------------
		* Steps
		* 1. Set up
		* 2. Add listeners => OnTouch
		* 3. Add listeners => OnClick
		----------------------------*/
		
		// 
		Dialog dlg = new Dialog(actv);
		
		//
		dlg.setContentView(layoutId);
		
		// Title
		dlg.setTitle(titleStringId);
		
		/*----------------------------
		* 2. Add listeners => OnTouch
		----------------------------*/
		//
		Button btn_ok = (Button) dlg.findViewById(okButtonId);
		Button btn_cancel = (Button) dlg.findViewById(cancelButtonId);
		
		//
		btn_ok.setTag(okTag);
		btn_cancel.setTag(cancelTag);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg));
		
		/*----------------------------
		* 3. Add listeners => OnClick
		----------------------------*/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg));
		
		//
		//dlg.show();
		
		return dlg;
	
	}//public static Dialog dlg_template_okCancel()

	public static void 
	dlg_patterns
	(Activity actv) {
		/*----------------------------
		 * memo
			----------------------------*/
		Dialog dlg1 = Methods_dlg.dlg_Template_Cancel(
				actv, 
				R.layout.dlg_tmpl_cancel_lv, 
				R.string.dlg_memo_patterns_title, 
				
				R.id.dlg_tmpl_cancel_lv_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS);
//				.dlg_template_cancel(
//						actv, R.layout.dlg_admin_patterns, 
//						R.string.dlg_memo_patterns_title, 
//						R.id.dlg_admin_patterns_bt_cancel, 
//						Tags.DialogTags.dlg_generic_dismiss);
//		Dialog dlg = Methods_dlg.dlg_template_cancel(
//				actv, R.layout.dlg_admin_patterns, 
//				R.string.dlg_memo_patterns_title, 
//				R.id.dlg_admin_patterns_bt_cancel, 
//				Tags.DialogTags.dlg_generic_dismiss);
		
		/*----------------------------
		 * 2. Prep => List
			----------------------------*/
		String[] choices = {
				actv.getString(R.string.generic_tv_register),
				actv.getString(R.string.generic_tv_edit),
				actv.getString(R.string.generic_tv_delete)
		};
		
		List<String> list = new ArrayList<String>();
		
		for (String item : choices) {
			
			list.add(item);
			
		}
		
		/*----------------------------
		 * 3. Adapter
			----------------------------*/
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				actv,
//				R.layout.dlg_db_admin,
				R.layout.list_row_simple_1,
//				android.R.layout.simple_list_item_1,
				list
				);

		/*----------------------------
		 * 4. Set adapter
			----------------------------*/
		ListView lv = (ListView) dlg1.findViewById(R.id.dlg_tmpl_cancel_lv_lv);
		
		lv.setAdapter(adapter);

		/*----------------------------
		 * 5. Set listener to list
			----------------------------*/
//		lv.setTag(Tags.DialogItemTags.dlg_admin_patterns_lv);
		lv.setTag(Tags.DialogItemTags.DLG_ADMIN_PATTERNS_LV);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg1));
//		lv.setOnItemClickListener(new DialogOnItemClickListener(actv, dlg));
		
		/*----------------------------
		 * 6. Show dialog
			----------------------------*/
		dlg1.show();
		
	}//public static void dlg_patterns(Activity actv)

	public static void 
	conf_Exec_Sql
	(Activity actv, 
		Dialog dlg1, String sql_Type) {
		// TODO Auto-generated method stub
		
//		////////////////////////////////
//
//		// dispatch
//
//		////////////////////////////////
//		if (sql_Type.equals(CONS.DB.Sqls._20140817_154650_addCol_IFM11_UpdatedAt_TITLE)) {
//			
//			_conf_Exec_Sql__AddCol_IFM11(actv, dlg1);
////			// Log
////			String msg_Log = "sql type => " + sql_Type;
////			Log.d("Methods_dlg.java" + "["
////					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////					+ "]", msg_Log);
//			
//		} else {
//			
//			String msg = "Unknown sql operation => " + sql_Type;
//			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
//			
//			return;
//
//		}
		
	}//conf_Exec_Sql

	/******************************
		@param duration => millseconds
	 ******************************/
	public static void
	dlg_ShowMessage_Duration
	(Activity actv, String message, int duration) {

		////////////////////////////////

		// validate

		////////////////////////////////
		String actv_Name = actv.getClass().getName();
		
		if (actv_Name.equals(CONS.Admin.actvName_TNActv)) {
			
			if (CONS.Admin.isRunning_TNActv == false) {
				
				// Log
				String msg_Log = "TNActv => not running";
				Log.i("Methods_dlg.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				return;
				
			}
			
		}
		
		////////////////////////////////

		// build dlg

		////////////////////////////////
		final Dialog dlg = Methods_dlg.dlg_Template_Cancel(
				actv, R.layout.dlg_tmpl_toast_ok, 
				R.string.generic_tv_confirm, 
				R.id.dlg_tmpl_toast_ok_bt_cancel, 
//				R.id.dlg_db_admin_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS);
		
		TextView tv_Message = 
				(TextView) dlg.findViewById(R.id.dlg_tmpl_toast_ok_tv_message);
		
		tv_Message.setText(message);
		
		////////////////////////////////

		// show

		////////////////////////////////
		dlg.show();
		
		//REF http://xjaphx.wordpress.com/2011/07/13/auto-close-dialog-after-a-specific-time/
		final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                dlg.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, duration); // after 2 second (or 2000 miliseconds), the task will be active.
		
	}

	/******************************
		@param duration => millseconds
	 ******************************/
	public static void
	dlg_ShowMessage_Duration
	(Activity actv, String message, int colorID, int duration) {
		
		final Dialog dlg = Methods_dlg.dlg_Template_Cancel(
				actv, R.layout.dlg_tmpl_toast_ok, 
				R.string.generic_tv_confirm, 
				R.id.dlg_tmpl_toast_ok_bt_cancel, 
//				R.id.dlg_db_admin_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS);
		
		TextView tv_Message = 
				(TextView) dlg.findViewById(R.id.dlg_tmpl_toast_ok_tv_message);
		
		tv_Message.setText(message);
		
		////////////////////////////////

		// background

		////////////////////////////////
//		tv_Message.setBackgroundColor(colorID);
		tv_Message.setBackgroundColor(actv.getResources().getColor(colorID));

		////////////////////////////////
		
		// show
		
		////////////////////////////////
		dlg.show();
		
		//REF http://xjaphx.wordpress.com/2011/07/13/auto-close-dialog-after-a-specific-time/
		final Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				dlg.dismiss(); // when the task active then close the dialog
				t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
			}
		}, duration); // after 2 second (or 2000 miliseconds), the task will be active.
		
	}

	public static void 
	conf_Upload_DB
	(Activity actv, Dialog d1) {
		// TODO Auto-generated method stub

//		Dialog dlg2 = Methods_dlg.dlg_Template_Cancel_SecondDialog(
		Dialog d2 = Methods_dlg.dlg_Template_OkCancel_SecondDialog(
				actv, d1,
				R.layout.dlg_tmpl_confirm_simple, 
				R.string.generic_tv_confirm, 
				
				R.id.dlg_tmpl_confirm_simple_btn_ok, 
				Tags.DialogTags.UPLOAD_DB_FILE_OK,
				
				R.id.dlg_tmpl_confirm_simple_btn_cancel, 
				Tags.DialogTags.GENERIC_DISMISS_SECOND_DIALOG
				);
		
		////////////////////////////////

		// set: message

		////////////////////////////////
		TextView tv_Message = 
				(TextView) d2.findViewById(R.id.dlg_tmpl_confirm_simple_tv_message);
		
		tv_Message.setText(actv.getString(R.string.dlg_upload_image_confirm));
		
		////////////////////////////////
		
		// set: item name
		
		////////////////////////////////
		TextView tv_ItemName = 
				(TextView) d2.findViewById(R.id.dlg_tmpl_confirm_simple_tv_item_name);
		
		// "DB"
		tv_ItemName.setText(actv.getString(R.string.menuMain_DB));

		////////////////////////////////

		// show

		////////////////////////////////
		d2.show();

	}//conf_Upload_DB

	public static void 
	dlg_OptMenu_MainActv_Others
	(Activity actv) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// vars

		////////////////////////////////
		String msg_Log;
		
		
		////////////////////////////////

		// dlg

		////////////////////////////////
		Dialog d1 = Methods_dlg.dlg_Template_Cancel(
						actv,
						R.layout.dlg_tmpl_cancel_lv,
						R.string.menuMain_Admin,
						
						R.id.dlg_tmpl_cancel_lv_bt_cancel,
						Tags.DialogTags.GENERIC_DISMISS);
		
		/****************************
		* 2. Prep => List
		****************************/
//		String[] choices = {
//					actv.getString(R.string.dlg_actvmain_lv_delete),
//					};
		
		List<ListItem> list = new ArrayList<ListItem>();
//		List<String> list = new ArrayList<String>();
		
		list.add(new ListItem.Builder()
						.setText(actv.getString(
									R.string.dlg_actv_main_other_see_log))
						.setIconID(R.drawable.menu_icon_admin_32x32_blue)
						.setTextColor_ID(R.color.blue1)
						.build());
		
		list.add(new ListItem.Builder()
					.setText(actv.getString(
							R.string.dlg_actv_main_other_Import_From10))
							.setIconID(R.drawable.menu_icon_admin_32x32_brown)
							.setTextColor_ID(R.color.black)
							.build());
		
		/****************************
		* 3. Adapter
		****************************/
		Adp_ListItems adapter = new Adp_ListItems(
							actv,
							//R.layout.dlg_db_admin,
							R.layout.list_row_simple_iv_1,
							//android.R.layout.simple_list_item_1,
							list
		);
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//							actv,
//							//R.layout.dlg_db_admin,
//							R.layout.list_row_simple_1,
//							//android.R.layout.simple_list_item_1,
//							list
//		);
		
		/****************************
		* 4. Set adapter
		****************************/
		ListView lv = (ListView) d1.findViewById(R.id.dlg_tmpl_cancel_lv_lv);
		
		lv.setAdapter(adapter);
		
		////////////////////////////////

		// Set listener to list

		////////////////////////////////
		lv.setTag(Tags.DialogItemTags.ACTV_MAIN_OPTMENU_OTHERS);
		
		lv.setOnItemClickListener(new DOI_CL(actv, d1));
		
		/***************************************
		* Modify the list view height
		***************************************/
//		lv.setLayoutParams(
//				new LinearLayout.LayoutParams(
//						300,	//	Width
//						LayoutParams.WRAP_CONTENT	//	Height
//				));
		
		/***************************************
		* Modify: Button layout
		***************************************/
		LinearLayout llButton =
					(LinearLayout) d1.findViewById(R.id.dlg_tmpl_cancel_lv_ll_filepath);
//		(LinearLayout) dlg1.findViewById(R.id.actv_imp_ll_filepath);
		
		LinearLayout.LayoutParams params =
				new LinearLayout.LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT);
		
		params.gravity = Gravity.CENTER_HORIZONTAL;
		
		llButton.setLayoutParams(params);

		////////////////////////////////

		// get: screen size

		////////////////////////////////
		//REF size http://stackoverflow.com/questions/19155559/how-to-get-android-device-screen-size answered Oct 3 '13 at 10:00
		DisplayMetrics displayMetrics = actv.getResources()
                			.getDisplayMetrics();
		
		int w = displayMetrics.widthPixels;
		
//		// Log
//		String msg_Log = "w => " + w;
//		Log.d("Methods_dlg.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		int dialog_Width = w * CONS.Admin.ratio_Dialog_to_Screen_W / 100;
		
//		// Log
//		msg_Log = "dialog_Width => " + dialog_Width;
//		Log.d("Methods_dlg.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		////////////////////////////////

		// linear layot: main

		////////////////////////////////
		LinearLayout ll_Main = (LinearLayout) d1.findViewById(R.id.dlg_tmpl_cancel_lv_ll_main);
		
		// Log
		msg_Log = "ll_Main => created";
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		//REF parent layout http://stackoverflow.com/questions/4631966/set-relativelayout-layout-params-programmatically-throws-classcastexception answered Jan 8 '11 at 5:42
//		08-21 11:30:45.434: E/AndroidRuntime(20722): java.lang.ClassCastException: android.widget.LinearLayout$LayoutParams
//		08-21 11:30:45.434: E/AndroidRuntime(20722): 	at android.widget.FrameLayout.onLayout(FrameLayout.java:293)
//		08-21 11:30:45.434: E/AndroidRuntime(20722): 	at android.view.View.layout(View.java:7184)

		FrameLayout.LayoutParams params2 =
				new FrameLayout.LayoutParams(
//						LinearLayout.LayoutParams params2 =
//						new LinearLayout.LayoutParams(
						dialog_Width,
//						400,
//						200,
//						LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
		
		// Log
		msg_Log = "setting params...";
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		ll_Main.setLayoutParams(params2);
		
		// Log
		msg_Log = "params => set";
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		/****************************
		* 6. Show dialog
		****************************/
		d1.show();
		
		
	}//dlg_OptMenu_MainActv_Others

	public static void 
	conf_DropCreate_TablePatterns
	(Activity actv, Dialog d1, Dialog d2) {
		// TODO Auto-generated method stub
		
		Dialog d3 = new Dialog(actv);
		
		//
		d3.setContentView(R.layout.dlg_tmpl_confirm_simple);
		
		// Title
		d3.setTitle(R.string.generic_tv_confirm);
		
		////////////////////////////////

		// set: message

		////////////////////////////////
		TextView tv_Message = (TextView) d3.findViewById(
								R.id.dlg_tmpl_confirm_simple_tv_message);
		
		tv_Message.setText(actv.getString(
					R.string.commons_lbl_drop_create_table) + " ?");

		////////////////////////////////
		
		// set: item name
		
		////////////////////////////////
		TextView tv_ItemName = (TextView) d3.findViewById(
				R.id.dlg_tmpl_confirm_simple_tv_item_name);
		
		tv_ItemName.setText(CONS.DB.tname_MemoPatterns);
		
		////////////////////////////////

		// button: cancel

		////////////////////////////////
		Button btn_Cancel = (Button) d3.findViewById(
						R.id.dlg_tmpl_confirm_simple_btn_cancel);
		
		//
//		btn_Cancel.setTag(Tags.DialogTags.dlg_generic_dismiss_second_dialog);
		btn_Cancel.setTag(Tags.DialogTags.GENERIC_DISMISS_THIRD_DIALOG);
		
		//
		btn_Cancel.setOnTouchListener(new DB_OTL(actv, d1, d2, d3));
		
		btn_Cancel.setOnClickListener(new DB_OCL(actv, d1, d2, d3));
		
		////////////////////////////////
		
		// button: ok
		
		////////////////////////////////
		Button btn_Ok = (Button) d3.findViewById(
				R.id.dlg_tmpl_confirm_simple_btn_ok);
		
		//
		btn_Ok.setTag(Tags.DialogTags.DROP_CREATE_TABLE_PATTERNS_OK);
		
		//
		btn_Ok.setOnTouchListener(new DB_OTL(actv, d1, d2, d3));
		
		btn_Ok.setOnClickListener(new DB_OCL(actv, d1, d2, d3));
		
		////////////////////////////////

		// show

		////////////////////////////////
		d3.show();		
		
	}//conf_DropCreate_TablePatterns

	public static void 
	conf_DropCreate_Table_Admin
	(Activity actv, Dialog d1, Dialog d2) {
		// TODO Auto-generated method stub
		
		Dialog d3 = new Dialog(actv);
		
		//
		d3.setContentView(R.layout.dlg_tmpl_confirm_simple);
		
		// Title
		d3.setTitle(R.string.generic_tv_confirm);
		
		////////////////////////////////
		
		// set: message
		
		////////////////////////////////
		TextView tv_Message = (TextView) d3.findViewById(
				R.id.dlg_tmpl_confirm_simple_tv_message);
		
		tv_Message.setText(actv.getString(
				R.string.commons_lbl_drop_create_table) + " ?");
		
		////////////////////////////////
		
		// set: item name
		
		////////////////////////////////
		TextView tv_ItemName = (TextView) d3.findViewById(
				R.id.dlg_tmpl_confirm_simple_tv_item_name);
		
		tv_ItemName.setText(CONS.DB.tname_Admin);
		
		////////////////////////////////
		
		// button: cancel
		
		////////////////////////////////
		Button btn_Cancel = (Button) d3.findViewById(
				R.id.dlg_tmpl_confirm_simple_btn_cancel);
		
		//
//		btn_Cancel.setTag(Tags.DialogTags.dlg_generic_dismiss_second_dialog);
		btn_Cancel.setTag(Tags.DialogTags.GENERIC_DISMISS_THIRD_DIALOG);
		
		//
		btn_Cancel.setOnTouchListener(new DB_OTL(actv, d1, d2, d3));
		
		btn_Cancel.setOnClickListener(new DB_OCL(actv, d1, d2, d3));
		
		////////////////////////////////
		
		// button: ok
		
		////////////////////////////////
		Button btn_Ok = (Button) d3.findViewById(
				R.id.dlg_tmpl_confirm_simple_btn_ok);
		
		//
		btn_Ok.setTag(Tags.DialogTags.DROP_CREATE_DROP_TABLE_ADMIN_OK);
		
		//
		btn_Ok.setOnTouchListener(new DB_OTL(actv, d1, d2, d3));
		
		btn_Ok.setOnClickListener(new DB_OCL(actv, d1, d2, d3));
		
		////////////////////////////////
		
		// show
		
		////////////////////////////////
		d3.show();		
		
	}//conf_DropCreate_Table_Admin

	public static void
	dlg_ShowMessage_ThirdDialog
	(Activity actv, 
			String message, Dialog dlg1, Dialog dlg2, int colorID) {
		
		Dialog dlg3 = Methods_dlg.dlg_Template_Cancel_ThirdDialog(
				actv, dlg1, dlg2,
				R.layout.dlg_tmpl_toast_ok, 
				R.string.generic_tv_confirm, 
				
				R.id.dlg_tmpl_toast_ok_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS_THIRD_DIALOG);
		
		TextView tv_Message = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_toast_ok_tv_message);
		
		tv_Message.setBackgroundColor(actv.getResources().getColor(colorID));
		
		tv_Message.setTextColor(Color.WHITE);
		
		tv_Message.setText(message);
		
		dlg3.show();
		
	}

	public static Dialog 
	dlg_Template_Cancel_ThirdDialog
	(Activity actv, Dialog dlg1, Dialog dlg2,
			int layoutId, int titleStringId,
			int cancelButtonId, Tags.DialogTags cancelTag) {
		/****************************
		 * Steps
		 * 1. Set up
		 * 2. Add listeners => OnTouch
		 * 3. Add listeners => OnClick
		 ****************************/
		
		// 
		Dialog dlg3 = new Dialog(actv);
		
		//
		dlg3.setContentView(layoutId);
		
		// Title
		dlg3.setTitle(titleStringId);
		
		/****************************
		 * 2. Add listeners => OnTouch
		 ****************************/
		//
		Button btn_cancel = (Button) dlg3.findViewById(cancelButtonId);
		
		//
		btn_cancel.setTag(cancelTag);
		
		//
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg1, dlg2, dlg3));
		
		/****************************
		 * 3. Add listeners => OnClick
		 ****************************/
		//
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, dlg3));
		
		//
		//dlg.show();
		
		return dlg3;
		
	}//public static Dialog dlg_template_okCancel()

	public static void 
	dlg_LABS_main
	(Activity actv) {
		// TODO Auto-generated method stub

		////////////////////////////////

		// vars

		////////////////////////////////
		String msg_Log;
		
		
		////////////////////////////////

		// dlg

		////////////////////////////////
		Dialog d1 = Methods_dlg.dlg_Template_Cancel(
						actv,
						R.layout.dlg_tmpl_cancel_lv,
						R.string.opt_Menu_LABS_title,
						
						R.id.dlg_tmpl_cancel_lv_bt_cancel,
						Tags.DialogTags.GENERIC_DISMISS);
		
		/****************************
		* 2. Prep => List
		****************************/
		List<ListItem> list = new ArrayList<ListItem>();
		
		list.add(new ListItem.Builder()
						.setText(actv.getString(
									R.string.opt_Menu_LABS__Change_RGB))
						.setIconID(R.drawable.menu_icon_admin_32x32_blue)
						.setTextColor_ID(R.color.blue1)
						.build());
		
		list.add(new ListItem.Builder()
					.setText(actv.getString(
							R.string.opt_Menu_LABS__RedColor_zero))
							.setIconID(R.drawable.menu_icon_admin_32x32_brown)
							.setTextColor_ID(R.color.black)
							.build());
		
		list.add(new ListItem.Builder()
					.setText(actv.getString(
							R.string.opt_Menu_LABS__BlueColor_zero))
							.setIconID(R.drawable.menu_icon_admin_32x32_purple)
							.setTextColor_ID(R.color.purple4)
							.build());
		
		list.add(new ListItem.Builder()
					.setText(actv.getString(
							R.string.opt_Menu_LABS__GreenColor_zero))
							.setIconID(R.drawable.menu_icon_admin_32x32_yellow)
							.setTextColor_ID(R.color.yellow_dark)
							.build());
		
		list.add(new ListItem.Builder()
					.setText(actv.getString(
							R.string.opt_Menu_LABS__Rotate_Image))
							.setIconID(R.drawable.menu_icon_admin_32x32_brown)
							.setTextColor_ID(R.color.black)
							.build());
		
		list.add(new ListItem.Builder()
					.setText(actv.getString(
							R.string.opt_Menu_LABS__Canvas))
							.setIconID(R.drawable.menu_icon_admin_32x32_blue)
							.setTextColor_ID(R.color.blue1)
							.build());
		
		/****************************
		* 3. Adapter
		****************************/
		Adp_ListItems adapter = new Adp_ListItems(
							actv,
							R.layout.list_row_simple_iv_1,
							list
		);
		
		/****************************
		* 4. Set adapter
		****************************/
		ListView lv = (ListView) d1.findViewById(R.id.dlg_tmpl_cancel_lv_lv);
		
		lv.setAdapter(adapter);
		
		////////////////////////////////

		// Set listener to list

		////////////////////////////////
		lv.setTag(Tags.DialogItemTags.ACTV_IMAGE_OPTMENU_LABS);
		
		lv.setOnItemClickListener(new DOI_CL(actv, d1));
		
		/***************************************
		* Modify the list view height
		***************************************/
//		lv.setLayoutParams(
//				new LinearLayout.LayoutParams(
//						300,	//	Width
//						LayoutParams.WRAP_CONTENT	//	Height
//				));
		
		/***************************************
		* Modify: Button layout
		***************************************/
		LinearLayout llButton =
					(LinearLayout) d1.findViewById(R.id.dlg_tmpl_cancel_lv_ll_filepath);
		
		LinearLayout.LayoutParams params =
				new LinearLayout.LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT);
		
		params.gravity = Gravity.CENTER_HORIZONTAL;
		
		llButton.setLayoutParams(params);

		////////////////////////////////

		// get: screen size

		////////////////////////////////
		//REF size http://stackoverflow.com/questions/19155559/how-to-get-android-device-screen-size answered Oct 3 '13 at 10:00
		DisplayMetrics displayMetrics = actv.getResources()
                			.getDisplayMetrics();
		
		int w = displayMetrics.widthPixels;
		
		int dialog_Width = w * CONS.Admin.ratio_Dialog_to_Screen_W / 100;
		
		////////////////////////////////

		// linear layot: main

		////////////////////////////////
		LinearLayout ll_Main = (LinearLayout) d1.findViewById(R.id.dlg_tmpl_cancel_lv_ll_main);
		
		// Log
		msg_Log = "ll_Main => created";
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		//REF parent layout http://stackoverflow.com/questions/4631966/set-relativelayout-layout-params-programmatically-throws-classcastexception answered Jan 8 '11 at 5:42
//		08-21 11:30:45.434: E/AndroidRuntime(20722): java.lang.ClassCastException: android.widget.LinearLayout$LayoutParams
//		08-21 11:30:45.434: E/AndroidRuntime(20722): 	at android.widget.FrameLayout.onLayout(FrameLayout.java:293)
//		08-21 11:30:45.434: E/AndroidRuntime(20722): 	at android.view.View.layout(View.java:7184)

		FrameLayout.LayoutParams params2 =
				new FrameLayout.LayoutParams(
//						LinearLayout.LayoutParams params2 =
//						new LinearLayout.LayoutParams(
						dialog_Width,
//						400,
//						200,
//						LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
		
		ll_Main.setLayoutParams(params2);
		
		/****************************
		* 6. Show dialog
		****************************/
		d1.show();

	}//dlg_LABS_main

	public static void
	dlg_ShowMessage_SecondDialog
	(Activity actv, String message, Dialog dlg1, int colorID) {
		
		Dialog d2 = Methods_dlg.dlg_Template_Cancel_SecondDialog(
				actv, dlg1,
				R.layout.dlg_tmpl_toast_ok, 
				R.string.generic_tv_confirm, 
				
				R.id.dlg_tmpl_toast_ok_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS_SECOND_DIALOG);
		
		TextView tv_Message = 
				(TextView) d2.findViewById(R.id.dlg_tmpl_toast_ok_tv_message);
		
		tv_Message.setText(message);
		
		tv_Message.setBackgroundColor(actv.getResources().getColor(colorID));
		
		tv_Message.setTextColor(Color.WHITE);
		
		d2.show();
		
	}

	public static void 
	dlg_ACTV_CANVAS_Ops
	(Activity actv) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// dlg

		////////////////////////////////
		Dialog d1 = Methods_dlg.dlg_Template_Cancel(
						actv,
						R.layout.dlg_tmpl_cancel_lv,
						R.string.menu_actv_canvas_Ops,
						
						R.id.dlg_tmpl_cancel_lv_bt_cancel,
						Tags.DialogTags.GENERIC_DISMISS);
		
		/****************************
		* 2. Prep => List
		****************************/
		List<ListItem> list = new ArrayList<ListItem>();
		
		list.add(new ListItem.Builder()
						.setText(actv.getString(
									R.string.menu_actv_canvas_Ops__GetRGB))
						.setIconID(R.drawable.menu_icon_admin_32x32_blue)
						.setTextColor_ID(R.color.blue1)
						.build());
		
		/****************************
		* 3. Adapter
		****************************/
		Adp_ListItems adapter = new Adp_ListItems(
							actv,
							R.layout.list_row_simple_iv_1,
							list
		);
		
		/****************************
		* 4. Set adapter
		****************************/
		ListView lv = (ListView) d1.findViewById(R.id.dlg_tmpl_cancel_lv_lv);
		
		lv.setAdapter(adapter);
		
		////////////////////////////////

		// Set listener to list

		////////////////////////////////
		lv.setTag(Tags.DialogItemTags.ACTV_CANVAS_OPS);
		
		lv.setOnItemClickListener(new DOI_CL(actv, d1));
		
		/***************************************
		* Modify the list view height
		***************************************/
//		lv.setLayoutParams(
//				new LinearLayout.LayoutParams(
//						300,	//	Width
//						LayoutParams.WRAP_CONTENT	//	Height
//				));
		
		/***************************************
		* Modify: Button layout
		***************************************/
		LinearLayout llButton =
					(LinearLayout) d1.findViewById(R.id.dlg_tmpl_cancel_lv_ll_filepath);
		
		LinearLayout.LayoutParams params =
				new LinearLayout.LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT);
		
		params.gravity = Gravity.CENTER_HORIZONTAL;
		
		llButton.setLayoutParams(params);

		////////////////////////////////

		// get: screen size

		////////////////////////////////
		//REF size http://stackoverflow.com/questions/19155559/how-to-get-android-device-screen-size answered Oct 3 '13 at 10:00
		DisplayMetrics displayMetrics = actv.getResources()
                			.getDisplayMetrics();
		
		int w = displayMetrics.widthPixels;
		
		int dialog_Width = w * CONS.Admin.ratio_Dialog_to_Screen_W / 100;
		
		////////////////////////////////

		// linear layot: main

		////////////////////////////////
		LinearLayout ll_Main = 
				(LinearLayout) d1.findViewById(R.id.dlg_tmpl_cancel_lv_ll_main);
		
		//REF parent layout http://stackoverflow.com/questions/4631966/set-relativelayout-layout-params-programmatically-throws-classcastexception answered Jan 8 '11 at 5:42
//		08-21 11:30:45.434: E/AndroidRuntime(20722): java.lang.ClassCastException: android.widget.LinearLayout$LayoutParams
//		08-21 11:30:45.434: E/AndroidRuntime(20722): 	at android.widget.FrameLayout.onLayout(FrameLayout.java:293)
//		08-21 11:30:45.434: E/AndroidRuntime(20722): 	at android.view.View.layout(View.java:7184)

		FrameLayout.LayoutParams params2 =
				new FrameLayout.LayoutParams(
						dialog_Width,
						LayoutParams.WRAP_CONTENT);
		
		ll_Main.setLayoutParams(params2);
		
		/****************************
		* 6. Show dialog
		****************************/
		d1.show();
		
	}//dlg_ACTV_CANVAS_Ops

	public static void 
	dlg_filter_ShowLogList
	(Activity actv) {
		// TODO Auto-generated method stub
		boolean res;
		
		////////////////////////////////

		// get dialog

		////////////////////////////////
		Dialog d = _filter_ShowList__GetDialog(actv);

		////////////////////////////////

		// gridview

		////////////////////////////////
//		res = _filter_ShowList__GridView(actv, d);

		////////////////////////////////

		// set previous string

		////////////////////////////////
		res = _filter_ShowList__SetString(actv, d);
		
		////////////////////////////////

		// show

		////////////////////////////////
		d.show();
		
	}//filter_ShowList

	/******************************
		@return
			false => pref val is null<br>
	 ******************************/
	private static boolean 
	_filter_ShowList__SetString
	(Activity actv, Dialog d) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
	
		// get pref
	
		////////////////////////////////
		String pref_FilterString = Methods.get_Pref_String(
						actv, 
						CONS.Pref.pname_MainActv, 
						CONS.Pref.pkey_ShowListActv_Filter_String, 
						null);
		
		if (pref_FilterString == null) {
			
			// Log
			String msg_Log = "pref_FilterString => null";
			Log.d("Methods_dlg.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return false;
			
		}
		
		////////////////////////////////
	
		// set: text
	
		////////////////////////////////
		EditText et = (EditText) d.findViewById(R.id.dlg_filter_showlist_et_content);
		
		et.setText(pref_FilterString);
		
		////////////////////////////////
	
		// selection
	
		////////////////////////////////
		et.setSelection(pref_FilterString.length());
		
		return true;
		
	}//_filter_ShowList__SetString
	
	private static Dialog 
	_filter_ShowList__GetDialog
	(Activity actv) {
		// TODO Auto-generated method stub
	
		////////////////////////////////
	
		// setup dialog
	
		////////////////////////////////
		Dialog dlg = new Dialog(actv);
		
		//
		dlg.setContentView(R.layout.dlg_filter_showlog);
		
		// Title
		dlg.setTitle(actv.getString(R.string.menu_showlist_filter));
		
		////////////////////////////////
	
		// Buttons
	
		////////////////////////////////
		ImageButton bt_OK	= (ImageButton) dlg.findViewById(R.id.dlg_filter_showlist_bt_ok);
	//	Button bt_OK	= (Button) dlg.findViewById(R.id.dlg_filter_showlist_bt_ok);
		ImageButton bt_Cancel =
				(ImageButton) dlg.findViewById(R.id.dlg_filter_showlist_bt_cancel);
	//	Button bt_Cancel =
	//			(Button) dlg.findViewById(R.id.dlg_filter_showlist_bt_cancel);
		ImageButton bt_Clear	=
				(ImageButton) dlg.findViewById(R.id.dlg_filter_showlist_bt_clear);
		ImageButton bt_Reset =
				(ImageButton) dlg.findViewById(R.id.dlg_filter_showlist_bt_reset);
		
		
		////////////////////////////////
	
		// Listeners
	
		////////////////////////////////
	//	bt_OK.setTag(Tags.DialogTags.dlg_Filter_Timeline_OK);
		bt_OK.setTag(Tags.DialogTags.DLG_FILTER_SHOWLIST_OK);
		bt_Clear.setTag(Tags.DialogTags.DLG_FILTER_SHOWLIST_CLEAR);
		bt_Reset.setTag(Tags.DialogTags.DLG_FILTER_SHOWLIST_RESET);
		
		bt_Cancel.setTag(Tags.DialogTags.GENERIC_DISMISS);
		
		// On touch
		bt_OK.setOnTouchListener(new DB_OTL(actv));
		bt_Clear.setOnTouchListener(new DB_OTL(actv));
		bt_Reset.setOnTouchListener(new DB_OTL(actv));
		
		bt_Cancel.setOnTouchListener(new DB_OTL(actv));
		
		// On click
		bt_OK.setOnClickListener(new DB_OCL(actv, dlg));
		bt_Clear.setOnClickListener(new DB_OCL(actv, dlg));
		bt_Reset.setOnClickListener(new DB_OCL(actv, dlg));
		
		bt_Cancel.setOnClickListener(new DB_OCL(actv, dlg));
		
		return dlg;
		
	}//_filter_ShowList__GetDialog

}//public class Methods_dialog
