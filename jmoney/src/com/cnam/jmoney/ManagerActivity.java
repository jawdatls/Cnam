package com.cnam.jmoney;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ManagerActivity extends Activity {
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_layout);
        
        /**
         * Creating all buttons instances
         * */
        // Dashboard Expense button
        Button btn_category = (Button) findViewById(R.id.btn_category);
        btn_category.setText("Categories");
        
        // Dashboard Income button
        Button btn_account = (Button) findViewById(R.id.btn_account);
        btn_account.setText("Accounts");
        
        // Dashboard manager button
        Button btn_project = (Button) findViewById(R.id.btn_project);
        btn_project.setText("Projects");
        
        // Dashboard report button
        Button btn_setting = (Button) findViewById(R.id.btn_setting);
        btn_setting.setText("Settings");
        
        /**
         * Handling all button click events
         * */
        
        // Listening to News Feed button click
        btn_category.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(), CategoryActivity.class);
				startActivity(i);
			}
		});
        
       // Listening Friends button click
        btn_account.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(), AccountActivity.class);
				startActivity(i);
			}
		});
        
        // Listening Messages button click
        btn_project.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(),ProjectActivity.class);
				startActivity(i);
			}
		});
        
        // Listening to Places button click
        btn_setting.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(), SettingActivity.class);
				startActivity(i);
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
        	Intent i = new Intent(getApplicationContext(), JMoneyActivity.class);
			startActivity(i);
        	
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

