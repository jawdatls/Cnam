package com.cnam.jmoney;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends Activity {
	Button save_btn;
	TextView currency_label;
	Spinner currency_value;
	Cursor cursor;
	DBHelper db;
	Setting setting_db;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        
        db = new DBHelper(this);
        setting_db = new Setting(db.getDB());
        
        save_btn = (Button)findViewById(R.id.btn_save_setting);
        currency_label = (TextView)findViewById(R.id.setting_currency_text_view);
        currency_value = (Spinner)findViewById(R.id.setting_currency);
        
        save_btn.setText("Save");
        currency_label.setText("Currency");
        
        List<String> currencyList = setting_db.getAllCurrencyLabels();
        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(SettingActivity.this , android.R.layout.simple_spinner_item,currencyList);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currency_value.setAdapter(currencyAdapter);
        
        currency_value.setSelection(currencyList.indexOf(setting_db.getValueByKey("currency")));
        
        save_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	String curency_setting = currency_value.getSelectedItem().toString();
            	boolean res;
            	res = setting_db.edit("currency", curency_setting);
            	if(res == true){
					Toast.makeText(SettingActivity.this,"The Setting Has Been Saved", Toast.LENGTH_LONG).show();
					Intent i = new Intent(getApplicationContext(), SettingActivity.class);
					startActivity(i);
				}else{
					Toast.makeText(SettingActivity.this,"The Setting Hasn't Been Saved, Please Try Again", Toast.LENGTH_LONG).show();
				}
            }
        });
        this.home();
    }
    
    private void home(){
    	Button home;
    	ImageView image;
    	home = (Button)findViewById(R.id.home_btn);
    	image = (ImageView)findViewById(R.id.home_btn_img);
    	home.setText("Home");
    	image.setContentDescription("JMoney");
    	home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), JMoneyActivity.class);
				startActivity(i);
			}
		});
    	image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), JMoneyActivity.class);
				startActivity(i);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	Intent i = new Intent(getApplicationContext(), ManagerActivity.class);
			startActivity(i);
        	
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
