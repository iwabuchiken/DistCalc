package dc.listeners.dialog;


import utils.CONS;
import utils.Tags;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DOI_CL implements OnItemClickListener {

	//
	Activity actv;
	Dialog d1;
	Dialog d2;
	
	//
	Vibrator vib;
	private String file_Name;
	
	
	//
//	Methods.DialogTags dlgTag = null;

	public DOI_CL(Activity actv, Dialog dlg) {
		// 
		this.actv = actv;
		this.d1 = dlg;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg)

	public DOI_CL(Activity actv, Dialog dlg, Dialog dlg2) {
		// 
		this.actv = actv;
		this.d1 = dlg;
		this.d2 = dlg2;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg)


	public DOI_CL
	(Activity actv, Dialog dlg1, String file_Name) {
		// TODO Auto-generated constructor stub
		
		this.actv = actv;
		this.d1 = dlg1;
		
		this.file_Name	= file_Name;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

	}

	public DOI_CL
	(Activity actv) {
		// TODO Auto-generated constructor stub
	}

	//	@Override
	public void 
	onItemClick
	(AdapterView<?> parent, View v, int position, long id) {
		/*----------------------------
		 * Steps
		 * 1. Get tag
		 * 2. Vibrate
		 * 3. Switching
			----------------------------*/
		
		Tags.DialogItemTags tag = (Tags.DialogItemTags) parent.getTag();
//		
		vib.vibrate(CONS.Admin.vibLength_click);
		
		String item;

//		String item = (String) parent.getItemAtPosition(position);
		
		/*----------------------------
		 * 3. Switching
			----------------------------*/
		switch (tag) {
		
			
		default:
			break;
		}//switch (tag)
		
	}//public void onItemClick(AdapterView<?> parent, View v, int position, long id)

}//public class DialogOnItemClickListener implements OnItemClickListener
