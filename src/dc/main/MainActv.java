package dc.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import org.apache.commons.lang.StringUtils;

import dc.listeners.others.STL;
import dc.utils.CONS;
import dc.utils.Methods;
import dc.utils.Tags;

//import app.main.R;


public class MainActv extends Activity implements LocationListener {
	
	public static Vibrator vib;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	/*----------------------------
		 * 1. super
		 * 2. Set content
		 * 2-2. Set title
		 * 3. Initialize => vib
		 * 
		 *  4. Set list
		 *  5. Set listener => Image buttons
		 *  6. Set path label
		 *  
		 *  7. Initialize preferences
		 *  
		 *  8. Refresh DB
			----------------------------*/
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actv_main);
        
        /*----------------------------
		 * 2-2. Set title
			----------------------------*/
		this.setTitle(this.getClass().getName());
        
        vib = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);

//		/*********************************
//		 * Debugs
//		 *********************************/
//		do_debug();
        
    }//public void onCreate(Bundle savedInstanceState)

    private void do_debug() {
    	
	}

	@Override
	protected void onDestroy() {
		/*----------------------------
		 * 1. Reconfirm if the user means to quit
		 * 2. super
		 * 3. Clear => prefs_main
		 * 4. Clear => list_root_dir
			----------------------------*/
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onDestroy()");
		
		///////////////////////////////////
		//
		// location manager
		//
		///////////////////////////////////
		if (CONS.MainActv.locationManager_ != null) {
			
			CONS.MainActv.locationManager_.removeUpdates(this);
			
		}
		
		CONS.MainActv.locationObtained = false;
		
		// Log
		String msg_Log = "Location manager => updates removed";
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		
		
		super.onDestroy();
		
	}//protected void onDestroy()

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		Methods.confirm_quit(this, keyCode);
		
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.menu_main, menu);
//		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		
		case R.id.menuMain_Admin://------------------------
			
			Methods.start_Activity_LogActv(this);
			
			break;
		
		case R.id.menuMain_DB://------------------------
			
			break;
			
		case R.id.menuMain_Prefs://------------------------
			
			break;
			
		default://------------------------
			break;

		}//switch (item.getItemId())

		
		return super.onOptionsItemSelected(item);
		
	}//public boolean onOptionsItemSelected(MenuItem item)

	@Override
	protected void onPause() {
		
		super.onPause();

		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onPause()");

//		// Log
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "prefs_main: " + Methods.get_currentPath_from_prefs(this));
		
		
	}

	@Override
	protected void onResume() {
		/*********************************
		 * 1. super
		 * 2. Set enables
		 *********************************/
		super.onResume();

		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onResume()");

		
		/*********************************
		 * 2. Set enables
		 *********************************/
//		ImageButton ib_up = (ImageButton) findViewById(R.id.v1_bt_up);
//		
//		String curDirPath = Methods.get_currentPath_from_prefs(this);
//		
//		if (curDirPath.equals(dname_base)) {
//			
//			ib_up.setEnabled(false);
//			
//			ib_up.setImageResource(R.drawable.ifm8_up_disenabled);
//			
//		} else {//if (this.currentDirPath == this.dpath_base)
//		
//			ib_up.setEnabled(true);
//
//			
//			ib_up.setImageResource(R.drawable.ifm8_up);
//		
//		}//if (this.currentDirPath == this.dpath_base)
		
	}//protected void onResume()

	@Override
	protected void onStart() {

		super.onStart();
		
		///////////////////////////////////
		//
		// listeners
		//
		///////////////////////////////////
		_onStart_SetListeners();
		
		/***************************************
		 * Prepare: data
		 ***************************************/
		prep_Data();
		
	}//protected void onStart()

	private void 
	_onStart_SetListeners() {
		// TODO Auto-generated method stub
		
		///////////////////////////////////
		//
		// LL: swipe
		//
		///////////////////////////////////
		LinearLayout ll_Swipe = (LinearLayout) findViewById(R.id.actvMain_LL_Swipe);
		
		ll_Swipe.setTag(Tags.SwipeTags.ACTV_MAIN_LL_SWIPE);
		
		ll_Swipe.setOnTouchListener(new STL(this));
		
	}//_onStart_SetListeners

	@Override
	public void 
	onLocationChanged(Location loc) {
		// TODO Auto-generated method stub
		
		CONS.MainActv.longitude = loc.getLongitude();
		
		CONS.MainActv.latitude = loc.getLatitude();
		
		// Log
		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"longi=%f / lat=%f", CONS.MainActv.longitude, CONS.MainActv.latitude
				);
		
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		if (CONS.MainActv.locationObtained == false
				&& CONS.MainActv.longitude != null
				&& CONS.MainActv.latitude != null) {
			
			CONS.MainActv.locationObtained = true;
			
		} else {//if (CONS.MainActv.locationObtained == false
			
			
			//REF http://alvinalexander.com/programming/printf-format-cheat-sheet
			String val_Longi = String.format("%3.9f", CONS.MainActv.longitude);
			String val_Lat = String.format("%3.9f", CONS.MainActv.latitude);
			
			////////////////////////////////

			// monitor distance

			////////////////////////////////
			
		}//if (CONS.MainActv.locationObtained == false

	}//onLocationChanged

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	private void prep_Data() {
		// TODO Auto-generated method stub
		CONS.MainActv.locationManager_ =
				(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		
		Criteria criteria = _setup_SetCriteria();
		
		String provider = CONS.MainActv.locationManager_.getBestProvider(criteria, true);
		
		CONS.MainActv.locationProvider_ =
				CONS.MainActv.locationManager_.getProvider(LocationManager.GPS_PROVIDER);
		
		CONS.MainActv.locationManager_
				.requestLocationUpdates(
						CONS.MainActv.locationProvider_.getName(),
						0, 0, this);

	}//private void prepareData()

	private Criteria _setup_SetCriteria() {
		// TODO Auto-generated method stub
		Criteria criteria = new Criteria();

		//Accuracy繧呈欠螳�
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
//		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		
		//PowerRequirement繧呈欠螳�
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		
		//SpeedRequired繧呈欠螳�
		criteria.setSpeedRequired(false);
		
		//AltitudeRequired繧呈欠螳�
		criteria.setAltitudeRequired(false);
		
		//BearingRequired繧呈欠螳�
		criteria.setBearingRequired(false);
		
		//CostAllowed繧呈欠螳�
		criteria.setCostAllowed(false);

		return criteria;
		
	}//private void _setup_SetCriteria()


}//public class MainActv extends Activity
