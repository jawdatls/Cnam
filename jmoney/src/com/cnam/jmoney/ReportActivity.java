package com.cnam.jmoney;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.cnam.jmoney.R;

public class ReportActivity extends Activity {
	private TextView empty_th, expense_th, income_th; 
	private TextView total_th,total_expense_td,total_income_td;
	private TextView year_th,year_expense_td,year_income_td;
	private TextView month_th,month_expense_td,month_income_td;
	private TextView week_th,week_expense_td,week_income_td;
	private TextView day_th,day_expense_td,day_income_td;
	private TextView res_th,res_td;
	private ListView listview;
	private DBHelper db;
	private Operation operation_db;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_layout);
        
        this.db = new DBHelper(this);
        this.operation_db = new Operation(db.getDB());
        
        this.empty_th   = (TextView) findViewById(R.id.empty_th);
        this.expense_th = (TextView) findViewById(R.id.expense_th);
        this.income_th  = (TextView) findViewById(R.id.income_th);
        
        this.total_th   = (TextView) findViewById(R.id.total_th);
        this.total_expense_td   = (TextView) findViewById(R.id.total_expense_td);
        this.total_income_td   = (TextView) findViewById(R.id.total_income_td);
        
        this.year_th   = (TextView) findViewById(R.id.year_th);
        this.year_expense_td   = (TextView) findViewById(R.id.year_expense_td);
        this.year_income_td   = (TextView) findViewById(R.id.year_income_td);
        
        this.month_th   = (TextView) findViewById(R.id.month_th);
        this.month_expense_td   = (TextView) findViewById(R.id.month_expense_td);
        this.month_income_td   = (TextView) findViewById(R.id.month_income_td);
        
        this.week_th   = (TextView) findViewById(R.id.week_th);
        this.week_expense_td   = (TextView) findViewById(R.id.week_expense_td);
        this.week_income_td   = (TextView) findViewById(R.id.week_income_td);
        
        this.day_th   = (TextView) findViewById(R.id.day_th);
        this.day_expense_td   = (TextView) findViewById(R.id.day_expense_td);
        this.day_income_td   = (TextView) findViewById(R.id.day_income_td);
        
        this.res_th = (TextView) findViewById(R.id.res_th);
        this.res_td = (TextView) findViewById(R.id.res_td);
        
        this.listview = (ListView) findViewById(android.R.id.list);
        
        empty_th.setText("Type"); expense_th.setText("Expense");income_th.setText("Income");
        
        total_th.setText("Total");
        total_expense_td.setText(operation_db.getMoneyTotal(Category.EXPENSE)); 
        total_income_td.setText(operation_db.getMoneyTotal(Category.INCOME));
        
        year_th.setText("Yearly"); 
        year_expense_td.setText(operation_db.getMoneyOfYear(Category.EXPENSE)); 
        year_income_td.setText(operation_db.getMoneyOfYear(Category.INCOME));
        
        month_th.setText("Monthly"); 
        month_expense_td.setText(operation_db.getMoneyOfMonth(Category.EXPENSE)); 
        month_income_td.setText(operation_db.getMoneyOfMonth(Category.INCOME));
        
        week_th.setText("Weekly"); 
        week_expense_td.setText(operation_db.getMoneyOfWeek(Category.EXPENSE)); 
        week_income_td.setText(operation_db.getMoneyOfWeek(Category.INCOME));
        
        day_th.setText("Daily"); 
        day_expense_td.setText(operation_db.getMoneyOfToday(Category.EXPENSE)); 
        day_income_td.setText(operation_db.getMoneyOfToday(Category.INCOME));
        
        double result = operation_db.getResult();
        Setting setting = new Setting(db.getDB());
		Currency currency = new Currency(db.getDB());
        res_th.setText("Result");
        res_td.setText(""+result+currency.getSign(setting.getValueByKey("currency")));
        if((result) > 0){
        	res_td.setTextColor(Color.parseColor("#529c52"));
        }else
        if(result < 0){
        	res_td.setTextColor(Color.parseColor("#ff4a4a"));
        }else{
        	res_td.setTextColor(Color.parseColor("#2f89df"));
        }	
        
        String[] columns = new String[] {"money","category","subcategory","account","project","method","date" };
        int[] to = new int[] {R.id.money, R.id.category, R.id.subcategory, R.id.account, R.id.project,R.id.method,R.id.date};
        Cursor list = operation_db.getAllOperation();
        SimpleCursorAdapter mAdap = new SimpleCursorAdapter(this, R.layout.item_list, list, columns, to);
        listview.setAdapter(mAdap);
        
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
