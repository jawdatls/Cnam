package com.cnam.jmoney;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class CategoryActivity extends Activity {
	
	Button add_btn,delete_btn;
	ListView listview;
	SimpleCursorAdapter adapter;
	Cursor cursor;
	DBHelper db;
	Category category_db;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_layout);
        
        db = new DBHelper(this);
        category_db = new Category(db.getDB());
        
        add_btn = (Button)findViewById(R.id.btn_add_category);
        delete_btn = (Button)findViewById(R.id.btn_delete_category);
        
        add_btn.setText("Add Category");
        delete_btn.setText("Delete");
        
        cursor = category_db.getAllLabelsCursor();
        
        adapter = new SimpleCursorAdapter(this,
        android.R.layout.simple_list_item_checked, 
        cursor, 
        new String[] { Category.T.COL_NAME }, // "name" is the column in your database that I describe below
        new int[] {android.R.id.text1});
        
        listview = (ListView) findViewById(R.id.categories_list);
        listview.setAdapter(adapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        
        delete_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                long[] checkedIds = listview.getCheckedItemIds();
                for(long id : checkedIds)
                    category_db.delete(id);
                listview.clearChoices();
                adapter.changeCursor(category_db.getAllLabelsCursor());
            }
        });
        
        add_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//Preparing views
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.category_dialog_layout, null);

                //layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.
                final EditText category_name = (EditText) layout.findViewById(R.id.category_name);
                category_name.setHint("Name");
                final Spinner category_method = (Spinner) layout.findViewById(R.id.category_method);
                category_method.setPrompt("Method");
                final Spinner category_parent = (Spinner) layout.findViewById(R.id.category_parent);
                category_parent.setPrompt("Parent");
                
                List<String> methodList = new ArrayList<String>();
                methodList.add("Expense"); methodList.add("Income");
                ArrayAdapter<String> methodAdapter = new ArrayAdapter<String>(CategoryActivity.this , android.R.layout.simple_spinner_item,methodList);
                methodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                category_method.setAdapter(methodAdapter);
                
                // build category list
                List<String> categoryList = category_db.getAllLabelsRoot(Category.EXPENSE,0);
                ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(CategoryActivity.this , android.R.layout.simple_spinner_item,categoryList);
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                category_parent.setAdapter(categoryAdapter);
                
                category_method.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        				// build sub category list
        				List<String> subcategoryList2;
        				int method = Category.EXPENSE;
        				if(parent.getItemAtPosition(pos).toString() == "Income"){
        					method = Category.INCOME;
        				}
        				subcategoryList2 = category_db.getAllLabelsRoot(method,0);
        				ArrayAdapter<String> subcategoryAdapter2 = new ArrayAdapter<String>(CategoryActivity.this, android.R.layout.simple_spinner_item,subcategoryList2);
        		        subcategoryAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		        category_parent.setAdapter(subcategoryAdapter2);
        			}
        			public void onNothingSelected(AdapterView<?> parent) {
        			    // Do nothing, just another required interface callback
        			}
                });

                //Building dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(CategoryActivity.this);
                builder.setView(layout);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = category_name.getText().toString();
                        int method = Category.EXPENSE;
        				if(category_method.getSelectedItem().toString() == "Income"){
        					method = Category.INCOME;
        				} 
        				String c = category_parent.getSelectedItem().toString();
        				int parent = 0; 
        				if(c != "Root"){
        					parent = category_db.getCategoryIDFromName(category_parent.getSelectedItem().toString());
        				}
        				long id;
        				id = category_db.add(name, method, parent);
        				if(id > 0){
        					Toast.makeText(CategoryActivity.this,"The Category Has Been Saved", Toast.LENGTH_LONG).show();
        					Intent i = new Intent(getApplicationContext(), CategoryActivity.class);
        					startActivity(i);
        				}else{
        					Toast.makeText(CategoryActivity.this,"The Category Hasn't Been Saved, Please Try Again", Toast.LENGTH_LONG).show();
        				}
                    	dialog.dismiss();
                        //save info where you want it
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                //AlertDialog dialog = builder.create();
                builder.show();
                
                
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
