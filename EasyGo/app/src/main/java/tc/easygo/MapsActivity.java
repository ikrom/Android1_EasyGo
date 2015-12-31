package tc.easygo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class MapsActivity extends AppCompatActivity {

	private Spinner spinner1;
	private Button btnSubmit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);

		addListenerOnButton();
		//addListenerOnSpinnerItemSelection();

	}

	//add items into spinner dynamically


	/*public void addListenerOnSpinnerItemSelection(){
		
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}*/

	//get the selected dropdown list value
	public void addListenerOnButton() {

		spinner1 = (Spinner) findViewById(R.id.spinner1);

		btnSubmit = (Button) findViewById(R.id.btnSubmit);

		btnSubmit.setOnClickListener(new OnClickListener() {
			//String vari  = spinner1.getSelectedItem().toString();
			@Override
			public void onClick(View v) {

				if(spinner1.getSelectedItemPosition()==0){
					// Creates an Intent that will load a map of San Francisco
					Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr=-8.0370701,114.0731465");
					Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
					mapIntent.setPackage("com.google.android.apps.maps");
					startActivity(mapIntent);

				}
				if(spinner1.getSelectedItemPosition()==1){
					Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr=-8.5635465,113.9239892");
					Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
					mapIntent.setPackage("com.google.android.apps.maps");
					startActivity(mapIntent);

				}if(spinner1.getSelectedItemPosition()==2){
					Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr=-8.7253682,114.3604669");
					Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
					mapIntent.setPackage("com.google.android.apps.maps");
					startActivity(mapIntent);

				}if(spinner1.getSelectedItemPosition()==3){
					Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr=-8.6695804,114.4508232");
					Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
					mapIntent.setPackage("com.google.android.apps.maps");
					startActivity(mapIntent);

				}if(spinner1.getSelectedItemPosition()==4){
					Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr=-8.2179929,114.3760107");
					Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
					mapIntent.setPackage("com.google.android.apps.maps");
					startActivity(mapIntent);

				}
				/*Toast.makeText(MyAndroidAppActivity.this,
						"OnClickListener : " + 
						"\nSpinner 1 : " + spinner1.getSelectedItem() ,
						Toast.LENGTH_SHORT).show();*/
			}

		});

	}

}