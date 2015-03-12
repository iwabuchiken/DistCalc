package dc.listeners.button;

import java.io.File;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import dc.main.R;
import dc.utils.CONS;
import dc.utils.Methods;
import dc.utils.Methods_dlg;
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
import android.widget.TextView;

public class BO_CL implements OnClickListener {
	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;

	TextView tv_A2C;
	TextView tv_B2A;
	TextView tv_B2C;
	
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

		case ACTV_MAIN_IB_CALC:
			
			case_ACTV_MAIN_IB_CALC();
			
			break;
			
		case ACTV_MAIN_IB_GET_A2C:
			
			case_ACTV_MAIN_IB_GET_A2C();
			
			break;
			
		case ACTV_MAIN_IB_GET_B2A:
			
			case_ACTV_MAIN_IB_GET_B2A();
			
			break;
			
		case ACTV_MAIN_IB_GET_B2C:
			
			case_ACTV_MAIN_IB_GET_B2C();
			
			break;
			
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
	case_ACTV_MAIN_IB_CALC() {
		// TODO Auto-generated method stub

		///////////////////////////////////
		//
		// calc: degrees: validate
		//
		///////////////////////////////////
		// validate
		String token = "Degree: %s --> Not ready";
		int colorId = R.color.red;
		
		if (CONS.MainActv.deg_A2C == -1) {
//			if (tv_A2C == null) {
			
			String message = String.format(
						Locale.JAPAN,
						token, "A to C");
//			int colorId = R.color.red;
//			int colorId = actv.getResources().getColor(R.color.blue1);
//			int colorId = actv.getResources().getColor(R.color.red);
			
			Methods_dlg.dlg_ShowMessage(actv, message, colorId);
			
			return;
			
		} else if (CONS.MainActv.deg_B2A == -1) {
//		} else if (tv_B2A == null) {
				
//				String message = "Degree: B to A --> Not ready";
//				int colorId = R.color.red;

			String message = String.format(
					Locale.JAPAN,
					token, "B to A");

			Methods_dlg.dlg_ShowMessage(actv, message, colorId);
			
			return;
				
		} else if (CONS.MainActv.deg_B2C == -1) {
//		} else if (tv_B2C == null) {
			
//			String message = "Degree: B to C --> Not ready";
//			int colorId = R.color.red;

			String message = String.format(
					Locale.JAPAN,
					token, "B to C");

			Methods_dlg.dlg_ShowMessage(actv, message, colorId);
			
			return;
			
		} else if (CONS.MainActv.locA_Lat == null
					|| CONS.MainActv.locA_Longi == null
					|| CONS.MainActv.locB_Lat == null
					|| CONS.MainActv.locB_Longi == null
					) {
			
			String message = "Loc data --> Not ready";
//			int colorId = actv.getResources().getColor(R.color.red);
			
			Methods_dlg.dlg_ShowMessage(actv, message, colorId);
			
			return;
			
		}
		
		///////////////////////////////////
		//
		// calc: degrees
		//
		///////////////////////////////////
//		double betha_2 = CONS.MainActv.deg_B2A;
		
		double alpha_1 = CONS.MainActv.deg_A2C + 180 - CONS.MainActv.deg_B2A;
//		double alpha_1 = CONS.MainActv.deg_A2C - 180 + betha_2;
		
		double betha_1 = (double) CONS.MainActv.deg_B2A - CONS.MainActv.deg_B2C;
//		double betha_1 = (double) CONS.MainActv.deg_B2C - CONS.MainActv.deg_B2A;
		
		// Log
		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"A2C = %f / B2A = %f / B2C = %f *** "
				+ "alpha_1 = %f / betha_1 = %f", 
				CONS.MainActv.deg_A2C, CONS.MainActv.deg_B2A, CONS.MainActv.deg_B2C,
				alpha_1, betha_1
				);
		
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		///////////////////////////////////
		//
		// calc: distance: base
		//
		///////////////////////////////////
		double dist_Base = Methods.getDistanceFromLatLonInKm(
					CONS.MainActv.locA_Longi, 
					CONS.MainActv.locA_Lat, 
					
					CONS.MainActv.locB_Longi, 
					CONS.MainActv.locB_Lat);
		
		// convert km to m
		dist_Base *= 1000;
		
		// Log
		msg_Log = String.format(
				Locale.JAPAN,
				"A.longi=%f, A.lat=%f / B.longi=%f, B.lat=%f / dist=%f",CONS.MainActv.locA_Longi, 
							CONS.MainActv.locA_Lat, 
							
							CONS.MainActv.locB_Longi, 
							CONS.MainActv.locB_Lat, 
							dist_Base
				);
		
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
//		double AC = (1/Math.cos(alpha_1)) * ();
		
		
		///////////////////////////////////
		//
		// disp: degrees
		//
		///////////////////////////////////
		TextView tv_Calc = (TextView) actv.findViewById(R.id.actvMain_TV_Msg);
		
		String message = String.format(
				Locale.JAPAN,
				"a1=%.2f / b1=%.2f /\n L=%.3f", 
				alpha_1, betha_1, dist_Base
//				"a1=%.2f / b1=%.2f", 
//				alpha_1, betha_1
				);
		
		tv_Calc.setText(message);
		

	}//case_ACTV_MAIN_IB_CALC()
	

	private void 
	case_ACTV_MAIN_IB_GET_A2C() {
		// TODO Auto-generated method stub
		
		///////////////////////////////////
		//
		// view
		//
		///////////////////////////////////
		tv_A2C = (TextView) actv.findViewById(R.id.actvMain_TV_A2C_Val);
		
		///////////////////////////////////
		//
		// validate
		//
		///////////////////////////////////
		Double longi = CONS.MainActv.longitude;
		Double lat = CONS.MainActv.latitude;
		
		if (longi == null || lat == null) {
			
			tv_A2C.setText("No loc data");
			
			return;
			
		}
		
		///////////////////////////////////
		//
		// display: degree
		//
		///////////////////////////////////
		CONS.MainActv.deg_A2C = CONS.MainActv.degree;
		
		tv_A2C.setText(String.valueOf(CONS.MainActv.deg_A2C));
//		tv_A2C.setText(String.valueOf(CONS.MainActv.degree));

		
		///////////////////////////////////
		//
		// loc
		//
		///////////////////////////////////
//		if (CONS.MainActv.locA_Longi == null) {
//			
//			CONS.MainActv.locA_Longi = new Double();
//			
//		}
		
		CONS.MainActv.locA_Longi = Double.valueOf(CONS.MainActv.longitude);
		CONS.MainActv.locA_Lat = Double.valueOf(CONS.MainActv.latitude);
		
	}//case_ACTV_MAIN_IB_GET_A2C

	private void 

	case_ACTV_MAIN_IB_GET_B2A() {
		// TODO Auto-generated method stub
		
		///////////////////////////////////
		//
		// view
		//
		///////////////////////////////////
		tv_B2A = (TextView) actv.findViewById(R.id.actvMain_TV_B2A_Val);
		
		///////////////////////////////////
		//
		// validate
		//
		///////////////////////////////////
		Double longi = CONS.MainActv.longitude;
		Double lat = CONS.MainActv.latitude;
		
		if (longi == null || lat == null) {
			
			tv_B2A.setText("No loc data");
			
//		} else if (CONS.MainActv.degree == null) {
//			
//			tv_B2A.setText("No loc data");
		}
		
		///////////////////////////////////
		//
		// display: degree
		//
		///////////////////////////////////
		CONS.MainActv.deg_B2A = CONS.MainActv.degree;
		
		tv_B2A.setText(String.valueOf(CONS.MainActv.deg_B2A));
//		tv_B2A.setText(String.valueOf(CONS.MainActv.degree));
		
	}//case_ACTV_MAIN_IB_GET_B2A
	
	
	private void 
	case_ACTV_MAIN_IB_GET_B2C() {
		// TODO Auto-generated method stub
		
		///////////////////////////////////
		//
		// view
		//
		///////////////////////////////////
		tv_B2C = (TextView) actv.findViewById(R.id.actvMain_TV_B2C_Val);
		
		///////////////////////////////////
		//
		// validate
		//
		///////////////////////////////////
		Double longi = CONS.MainActv.longitude;
		Double lat = CONS.MainActv.latitude;
		
		if (longi == null || lat == null) {
			
			tv_B2C.setText("No loc data");
			
//		} else if (CONS.MainActv.degree == null) {
//			
//			tv_B2C.setText("No loc data");
		}
		
		///////////////////////////////////
		//
		// display: degree
		//
		///////////////////////////////////
		CONS.MainActv.deg_B2C = CONS.MainActv.degree;
		
		tv_B2C.setText(String.valueOf(CONS.MainActv.deg_B2C));

//		tv_B2C.setText(String.valueOf(CONS.MainActv.degree));

		///////////////////////////////////
		//
		// loc
		//
		///////////////////////////////////
		CONS.MainActv.locB_Longi = Double.valueOf(CONS.MainActv.longitude);
		CONS.MainActv.locB_Lat = Double.valueOf(CONS.MainActv.latitude);

	}//case_ACTV_MAIN_IB_GET_B2C
	
	
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
