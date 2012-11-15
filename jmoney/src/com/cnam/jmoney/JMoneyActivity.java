package com.cnam.jmoney;

import com.cnam.jmoney.ExpenseActivity;
import com.cnam.jmoney.IncomeActivity;
import com.cnam.jmoney.ManagerActivity;
import com.cnam.jmoney.ReportActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cnam.jmoney.R;

public class JMoneyActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jmoney_layout);
        
        /**
         * Creating all buttons instances
         * */
        // Dashboard Expense button
        Button btn_expense= (Button) findViewById(R.id.btn_expense);
        btn_expense.setText("Expense");
        
        // Dashboard Income button
        Button btn_income = (Button) findViewById(R.id.btn_income);
        btn_income.setText("Income");
        
        // Dashboard manager button
        Button btn_manager = (Button) findViewById(R.id.btn_manager);
        btn_manager.setText("Manager");
        
        // Dashboard report button
        Button btn_report = (Button) findViewById(R.id.btn_report);
        btn_report.setText("Report");
        
        /**
         * Handling all button click events
         * */
        
        // Listening to News Feed button click
        btn_expense.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(), ExpenseActivity.class);
				startActivity(i);
			}
		});
        
       // Listening Friends button click
        btn_income.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(), IncomeActivity.class);
				startActivity(i);
			}
		});
        
        // Listening Messages button click
        btn_manager.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(), ManagerActivity.class);
				startActivity(i);
			}
		});
        
        // Listening to Places button click
        btn_report.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(), ReportActivity.class);
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
    	home.setClickable(false);
    	image.setClickable(false);
    	home.setVisibility(Button.INVISIBLE);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	Intent intent = new Intent(Intent.ACTION_MAIN);
        	intent.addCategory(Intent.CATEGORY_HOME);
        	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	startActivity(intent);
        	
        	finish();
            System.exit(0);
        	
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}