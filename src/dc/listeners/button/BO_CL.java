package dc.listeners.button;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import dc.utils.CONS;
import dc.utils.Methods;
import dc.utils.Tags;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class BO_CL implements OnClickListener {
	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;

	//
	Vibrator vib;
	
	//
	int position;
	
	public BO_CL(Activity actv, int position) {
		//
		this.actv = actv;
		this.position = position;
		
		//
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
		
		
	}//public ButtonOnClickListener(Activity actv, int position)

	public BO_CL(Activity actv) {
		
		this.actv = actv;
		
		//
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

	}

	//	@Override
	public void onClick(View v) {
//		//
		Tags.ButtonTags tag = (Tags.ButtonTags) v.getTag();
//
		CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
		
		//
		switch (tag) {

		case ib_up:
			
			case_ACTV_MAIN_IB_UP();
			
			break;


		case ACTV_TN_IB_BACK://-----------------------------------------------------------------------------
		case ACTV_SHOWLOG_IB_BACK://-----------------------------------------------------------------------------
		case image_activity_back://-----------------------------------------------------------------------------
			
			case_ACTV_TN_IB_BACK();
			
			break;
			
//		case ACTV_TN_IB_TOP://-----------------------------------------------------------------------------
//			
//			case_ACTV_TN_IB_TOP();
//			
//			break;
//			
		case ACTV_SHOWLOG_IB_TOP://-----------------------------------------------------------------------------
			
			case_ACTV_SHOWLOG_IB_TOP();
			
			break;
			
		case ACTV_LOG_IB_TOP://-----------------------------------------------------------------------------
			
			case_ACTV_LOG_IB_TOP();
			
			break;
			
//		case ACTV_TN_IB_BOTTOM://-----------------------------------------------------------------------------
//			
//			case_ACTV_TN_IB_BOTTOM();
//			
//			break;
			
		case ACTV_SHOWLOG_IB_BOTTOM://-----------------------------------------------------------------------------
			
			case_ACTV_SHOWLOG_IB_BOTTOM();
			
			break;
			
		case ACTV_LOG_IB_BOTTOM://-----------------------------------------------------------------------------
			
			case_ACTV_LOG_IB_BOTTOM();
			
			break;
			
//		case ACTV_TN_IB_DOWN://-----------------------------------------------------------------------------
//			
//			case_ACTV_TN_IB_DOWN();
//			
//			break;
			
		case ACTV_SHOWLOG_IB_DOWN://-----------------------------------------------------------------------------
			
			case_ACTV_SHOWLOG_IB_DOWN();
			
			break;
			
		case ACTV_LOG_IB_DOWN://-----------------------------------------------------------------------------
			
			case_ACTV_LOG_IB_DOWN();
			
			break;
			
//		case ACTV_TN_IB_UP://-----------------------------------------------------------------------------
//			
//			case_ACTV_TN_IB_UP();
//			
//			break;
			
		case ACTV_SHOWLOG_IB_UP://-----------------------------------------------------------------------------
			
			case_ACTV_SHOWLOG_IB_UP();
			
			break;
			
		case ACTV_LOG_IB_UP://-----------------------------------------------------------------------------
			
			case_ACTV_LOG_IB_UP();
			
			break;
			
//		case thumb_activity_ib_bottom: //----------------------------------------------
//			
//			int numOfGroups = TNActv.tiList.size() / lv.getChildCount();
//			
//			int indexOfLastChild = lv.getChildCount() * numOfGroups;
//			
//			lv.setSelection(indexOfLastChild);
//
//			break;// case thumb_activity_ib_bottom 
			
//		case thumb_activity_ib_top://--------------------------------------------
//			
//			vib.vibrate(CONS.vibLength_click);
//			
//			lv.setSelection(0);
//			
//			break;// thumb_activity_ib_top

//		case image_activity_prev://----------------------------------------------------
//			
//			image_activity_prev();
//			
//			
//			break;// case image_activity_prev
//			
//		case image_activity_next://----------------------------------------------------
//
//			image_activity_next();
//			
//			break;// case image_activity_next
			
		default:
			break;
		}//switch (tag)
		
	}//public void onClick(View v)

	private void 
	case_ACTV_SHOWLOG_IB_TOP() {
		// TODO Auto-generated method stub
		
//		/******************************
//			validate: list
//		 ******************************/
//		if (CONS.ShowLogActv.list_ShowLog_Files == null) {
//			
////			DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
//			
//			CONS.ShowLogActv.list_ShowLog_Files = 
//							Methods.get_LogItem_List(actv);
//			
//		}
		
		ListView lv = ((ListActivity) actv).getListView();
		
		lv.setSelection(0);
		
	}//case_ACTV_TN_IB_TOP
	
	private void 
	case_ACTV_LOG_IB_TOP() {
		// TODO Auto-generated method stub
		
//		/******************************
//			validate: list
//		 ******************************/
//		if (CONS.ShowLogActv.list_ShowLog_Files == null) {
//			
////			DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
//			
//			CONS.ShowLogActv.list_ShowLog_Files = 
//							Methods.get_LogItem_List(actv);
//			
//		}
		
		ListView lv = ((ListActivity) actv).getListView();
		
		lv.setSelection(0);
		
	}//case_ACTV_TN_IB_TOP
	
	
	private void 
	case_ACTV_SHOWLOG_IB_BOTTOM() {
		// TODO Auto-generated method stub
		
		/******************************
			validate: list
		 ******************************/
		if (CONS.ShowLogActv.list_ShowLog_Files == null) {
			
			CONS.ShowLogActv.list_ShowLog_Files = 
							Methods.get_LogItem_List(actv);
			
		}
			
		ListView lv = ((ListActivity) actv).getListView();
		
		int numOfGroups = 
					CONS.ShowLogActv.list_ShowLog_Files.size() / lv.getChildCount();
		
		int indexOfLastChild = lv.getChildCount() * numOfGroups;
		
		lv.setSelection(indexOfLastChild);
		
	}//case_ACTV_TN_IB_TOP
	
	private void 
	case_ACTV_LOG_IB_BOTTOM() {
		// TODO Auto-generated method stub
		
		/******************************
			validate: list
		 ******************************/
		if (CONS.LogActv.list_LogFiles == null) {
			
			CONS.LogActv.list_LogFiles = 
					Methods.get_LogFileNames_List(actv);
			
		}
		
		ListView lv = ((ListActivity) actv).getListView();
		
		int numOfGroups = 
				CONS.LogActv.list_LogFiles.size() / lv.getChildCount();
		
		int indexOfLastChild = lv.getChildCount() * numOfGroups;
		
		lv.setSelection(indexOfLastChild);
		
	}//case_ACTV_LOG_IB_BOTTOM
	
	
	private void 
	case_ACTV_SHOWLOG_IB_DOWN() {
		// TODO Auto-generated method stub
		
		/******************************
			validate: list
		 ******************************/
		if (CONS.ShowLogActv.list_ShowLog_Files == null) {
			
			CONS.ShowLogActv.list_ShowLog_Files = 
							Methods.get_LogItem_List(actv);
			
		}
		
		ListView lv = ((ListActivity) actv).getListView();
		
		int new_Position = lv.getLastVisiblePosition();
		
		if((new_Position + lv.getChildCount()) 
				> CONS.ShowLogActv.list_ShowLog_Files.size()) {
			
			new_Position = 
					CONS.ShowLogActv.list_ShowLog_Files.size() - lv.getChildCount();
			
		}
		
		lv.setSelection(new_Position);
		
	}//case_ACTV_TN_IB_TOP
	
	private void 
	case_ACTV_LOG_IB_DOWN() {
		// TODO Auto-generated method stub
		
		/******************************
			validate: list
		 ******************************/
		if (CONS.LogActv.list_LogFiles == null) {
			
			CONS.LogActv.list_LogFiles = 
					Methods.get_LogFileNames_List(actv);
			
		}
		
		ListView lv = ((ListActivity) actv).getListView();
		
		int new_Position = lv.getLastVisiblePosition();
		
		if((new_Position + lv.getChildCount()) 
				> CONS.LogActv.list_LogFiles.size()) {
			
			new_Position = 
					CONS.LogActv.list_LogFiles.size() - lv.getChildCount();
			
		}
		
		lv.setSelection(new_Position);
		
	}//case_ACTV_TN_IB_TOP
	
	
	private void 
	case_ACTV_SHOWLOG_IB_UP() {
		// TODO Auto-generated method stub
		
//		/******************************
//			validate: list
//		 ******************************/
//		if (CONS.ShowLogActv.list_ShowLog_Files == null) {
//			
//			CONS.ShowLogActv.list_ShowLog_Files = 
//							Methods.get_LogItem_List(actv);
//			
//		}		
		
		ListView lv = ((ListActivity) actv).getListView();
		
		int lastPos = lv.getLastVisiblePosition();
		
		int childCount = lv.getChildCount();
		
		int new_Position;
		
		if (lastPos - (childCount * 2) + 2 > 0) {
			
			new_Position = lastPos - (childCount * 2) + 2;
			
		} else {
			
			new_Position = 0;
			
		}
		
		lv.setSelection(new_Position);		
		
	}//case_ACTV_TN_IB_TOP
	
	private void 
	case_ACTV_LOG_IB_UP() {
		// TODO Auto-generated method stub
		
//		/******************************
//			validate: list
//		 ******************************/
//		if (CONS.ShowLogActv.list_ShowLog_Files == null) {
//			
//			CONS.ShowLogActv.list_ShowLog_Files = 
//							Methods.get_LogItem_List(actv);
//			
//		}		
		
		ListView lv = ((ListActivity) actv).getListView();
		
		int lastPos = lv.getLastVisiblePosition();
		
		int childCount = lv.getChildCount();
		
		int new_Position;
		
		if (lastPos - (childCount * 2) + 2 > 0) {
			
			new_Position = lastPos - (childCount * 2) + 2;
			
		} else {
			
			new_Position = 0;
			
		}
		
		lv.setSelection(new_Position);		
		
	}//case_ACTV_TN_IB_TOP
	
	
	private void 
	case_ACTV_TN_IB_BACK() {
		// TODO Auto-generated method stub
		
		actv.finish();
		
		actv.overridePendingTransition(0, 0);
		
	}

	private void case_ACTV_MAIN_IB_UP() {
		// TODO Auto-generated method stub
		
//		Methods.go_Up_Dir(actv);
		
//		String msg_Toa = "UP";
//		Toast.makeText(actv, msg_Toa, Toast.LENGTH_SHORT).show();
		
	}

}//public class ButtonOnClickListener implements OnClickListener
