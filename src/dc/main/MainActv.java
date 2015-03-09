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
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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
import utils.Methods;
import utils.Tags;

//import app.main.R;


public class MainActv extends Activity implements SensorEventListener {
	
	public static Vibrator vib;

	//REF http://www.anddev.org/other-coding-problems-f5/android-compass-tutorial-t11674.html
	private SensorManager sensorManager;
	private TextView txtRawData;
	private TextView txtDirection;
	private float myAzimuth = 0;
	private float myPitch = 0;
	private float myRoll = 0;

	///////////////////////////////////
	//
	// views
	//
	///////////////////////////////////
	TextView tv_Main;
	
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

     // Real sensor manager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        
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
		
		super.onDestroy();
		
	}//protected void onDestroy()

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
//		Methods.confirm_quit(this, keyCode);
		
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

		sensorManager.unregisterListener(this);
		
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

		sensorManager.registerListener(this, 
				sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_NORMAL);
		
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
		
		///////////////////////////////////
		//
		// views
		//
		///////////////////////////////////
		tv_Main = (TextView) findViewById(R.id.actvMain_TV);
		
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
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

//		// Log
//		String msg_Log;
//		
//		msg_Log = String.format(
//				Locale.JAPAN,
//				"event => %s", event.getClass().getName()
//				);
//		
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		myAzimuth = Math.round(event.values[0]);
        myPitch = Math.round(event.values[1]);
        myRoll = Math.round(event.values[2]);
        
//        String out = String.format(
//        				Locale.JAPAN,
//        				"Azimuth: %.2f\n\nPitch:%.2f\n\nRoll:%.2f\n\n", 
//                myAzimuth, myPitch, myRoll);
        
//        TextView tv_Main = (TextView) findViewById(R.id.actvMain_TV);
        
//        tv_Main.setText(out);
	
        this.printDirection();
        
	}

	private void printDirection() {
	
		String txt = null;
		
		if (myAzimuth < 22)
//				txtDirection.setText("N");
			
			txt = String.format("%s %.2f", "N", this.myAzimuth);
		
		else if (myAzimuth >= 22 && myAzimuth < 67)
//			txtDirection.setText("NE");
			txt = String.format("%s %.2f", "NE", this.myAzimuth);
		
		else if (myAzimuth >= 67 && myAzimuth < 112)
//			txtDirection.setText("E");
			txt = String.format("%s %.2f", "E", this.myAzimuth);
		
		else if (myAzimuth >= 112 && myAzimuth < 157)
//			txtDirection.setText("SE");
			txt = String.format("%s %.2f", "SE", this.myAzimuth);
		
		else if (myAzimuth >= 157 && myAzimuth < 202)
//			txtDirection.setText("S");
			txt = String.format("%s %.2f", "S", this.myAzimuth);
		
		else if (myAzimuth >= 202 && myAzimuth < 247)
//			txtDirection.setText("SW");
			txt = String.format("%s %.2f", "SW", this.myAzimuth);
		
		else if (myAzimuth >= 247 && myAzimuth < 292)
//			txtDirection.setText("W");
			txt = String.format("%s %.2f", "W", this.myAzimuth);
		
		else if (myAzimuth >= 292 && myAzimuth < 337)
//			txtDirection.setText("NW");
			txt = String.format("%s %.2f", "NW", this.myAzimuth);
		
		else if (myAzimuth >= 337)
//			txtDirection.setText("N");
			txt = String.format("%s %.2f", "N", this.myAzimuth);
		
		else
//			txtDirection.setText("");
			txt = String.format("%s %.2f", "ELSE", this.myAzimuth);
			
		///////////////////////////////////
		//
		// display
		//
		///////////////////////////////////
		tv_Main.setText(txt);		
		
	}
	

}//public class MainActv extends Activity
