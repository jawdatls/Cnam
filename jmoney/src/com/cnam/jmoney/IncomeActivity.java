package com.cnam.jmoney;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cnam.jmoney.R;

public class IncomeActivity extends Activity {
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_layout);
        
     // fileds
        final EditText money,remark;
        final DatePicker date;
    	final Spinner category, subcategory,account,project;
    	final Button submit;
    	
        // spinner list   	
    	List<String> categoryList,subcategoryList,accountList,projectList;
    	
    	// spinner adapter
    	ArrayAdapter<String> categoryAdapter,subcategoryAdapter,accountAdapter,projectAdapter;
    	
        // object db
    	DBHelper  db = new DBHelper(this);
    	final Operation operation_db = new Operation(db.getDB());
    	Category category_db = new Category(db.getDB());
    	Category subcategory_db = new Category(db.getDB());
    	Account account_db = new Account(db.getDB());
    	Project project_db = new Project(db.getDB());
    	
    	final Category subcategory_db2 = new Category(db.getDB());
    	final Account account_db2 = new Account(db.getDB());
    	final Project project_db2 = new Project(db.getDB());
        
        // relier le view
        money = (EditText) findViewById(R.id.money);
        date = (DatePicker)findViewById(R.id.date);
        remark = (EditText)findViewById(R.id.remark);
        category = (Spinner)findViewById(R.id.category);
        subcategory = (Spinner)findViewById(R.id.subcategory);
        account = (Spinner)findViewById(R.id.account);
        project = (Spinner)findViewById(R.id.project);
        submit = (Button)findViewById(R.id.submit);
        
        // Hint + Text
        submit.setText("Save");
        money.setHint("Money");
        remark.setHint("Remark");
        category.setPrompt("Category");
        subcategory.setPrompt("Sub Category");
        account.setPrompt("Account");
        project.setPrompt("Project");
        
        // build category list
        categoryList = category_db.getAllLabels(Category.INCOME, 0);
        categoryAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_item,categoryList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(categoryAdapter);
        
        // build sub category list
        subcategoryList = subcategory_db.getAllLabels(Category.INCOME, subcategory_db.getFirstCategoryID());
        subcategoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,subcategoryList);
        subcategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subcategory.setAdapter(subcategoryAdapter);
        
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				// build sub category list
				List<String> subcategoryList2;
				subcategoryList2 = subcategory_db2.getAllLabels(Category.INCOME, subcategory_db2.getCategoryIDFromName(parent.getItemAtPosition(pos).toString()));
				ArrayAdapter<String> subcategoryAdapter2 = new ArrayAdapter<String>(IncomeActivity.this, android.R.layout.simple_spinner_item,subcategoryList2);
		        subcategoryAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        subcategory.setAdapter(subcategoryAdapter2);
			}
			public void onNothingSelected(AdapterView<?> parent) {
			    // Do nothing, just another required interface callback
			}
        });
        
        // build account list
        accountList = account_db.getAllLabels();
        accountAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_item,accountList);
        accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        account.setAdapter(accountAdapter);
       
        // build project list
        projectList = project_db.getAllLabels();
        projectAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_item,projectList);
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        project.setAdapter(projectAdapter);
        
    	submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				long id;
				int c = subcategory_db2.getCategoryIDFromName(category.getSelectedItem().toString());
				int sc = subcategory_db2.getCategoryIDFromName(subcategory.getSelectedItem().toString());
				int a = account_db2.getAccountIDFromName(account.getSelectedItem().toString());
				int p = project_db2.getProjectIDFromName(project.getSelectedItem().toString());
				// date
				String _date = date.getYear()+"-"+(date.getMonth()+1)+"-"+date.getDayOfMonth()+" 00:00:00";
				id = operation_db.add(
						money.getText().toString(),
						_date,
						c,
						sc,
						a,
						p,
						remark.getText().toString(),
						Operation.INCOME
						);
				if(id > 0){
					Toast.makeText(IncomeActivity.this,"Your Income Has Been Saved", Toast.LENGTH_LONG).show();
					Intent i = new Intent(getApplicationContext(), JMoneyActivity.class);
					startActivity(i);
				}else{
					Toast.makeText(IncomeActivity.this,"Your Income Hasn't Been Saved, Please Try Again", Toast.LENGTH_LONG).show();
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
        	Intent i = new Intent(getApplicationContext(), JMoneyActivity.class);
			startActivity(i);
        	
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
