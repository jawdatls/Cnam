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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class AccountActivity extends Activity {
	
	Button add_btn,delete_btn;
	ListView listview;
	SimpleCursorAdapter adapter;
	Cursor cursor;
	DBHelper db;
	Account account_db;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_layout);
        
        db = new DBHelper(this);
        account_db = new Account(db.getDB());
        
        add_btn = (Button)findViewById(R.id.btn_add_account);
        delete_btn = (Button)findViewById(R.id.btn_delete_account);
        
        add_btn.setText("Add Account");
        delete_btn.setText("Delete");
        
    	cursor = account_db.getAllLabelsCursor();
        
        adapter = new SimpleCursorAdapter(this,
        android.R.layout.simple_list_item_checked, 
        cursor, 
        new String[] { Account.T.COL_NAME },
        new int[] {android.R.id.text1});
        
        listview = (ListView) findViewById(R.id.accounts_list);
        listview.setAdapter(adapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        delete_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                long[] checkedIds = listview.getCheckedItemIds();
                for(long id : checkedIds)
                    account_db.delete(id);
                listview.clearChoices();
                adapter.changeCursor(account_db.getAllLabelsCursor());
            }
        });
        
        add_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//Preparing views
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.account_dialog_layout, null);

                //layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.
                final EditText account_name = (EditText) layout.findViewById(R.id.account_name);
                account_name.setHint("Name");
                final EditText account_exchange = (EditText) layout.findViewById(R.id.account_exchange );
                account_exchange .setHint("Exchange Rate");
                final EditText account_memo = (EditText) layout.findViewById(R.id.account_memo);
                account_memo.setHint("Memo");
                final Spinner account_type = (Spinner) layout.findViewById(R.id.account_type);
                account_type.setPrompt("Type");
                
                List<String> typeList = new ArrayList<String>();
                typeList.add("Cash"); typeList.add("Cash Hko Rmb");
                typeList.add("Credit Card"); typeList.add("Bank Card");
                typeList.add("Value Card"); 
                ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(AccountActivity.this , android.R.layout.simple_spinner_item,typeList);
                typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                account_type.setAdapter(typeAdapter);
                
                //Building dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
                builder.setView(layout);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = account_name.getText().toString();
                        String exchange = account_exchange.getText().toString();
                        String memo = account_memo.getText().toString();
                        int type = Account.CASH;
        				if(account_type.getSelectedItem().toString() == "Cash Hko Rmb"){
        					type = Account.CASH_HKO_RMB;
        				}else
    					if(account_type.getSelectedItem().toString() == "Credit Card"){
        					type = Account.CREDITCARD;
        				}else
    					if(account_type.getSelectedItem().toString() == "Bank Card"){
        					type = Account.BANKCARD;
        				}else
    					if(account_type.getSelectedItem().toString() == "Value Card"){
        					type = Account.VALUECARD;
        				}	
        				
        				long id;
        				id = account_db.add(name, exchange, memo, type);
        				if(id > 0){
        					Toast.makeText(AccountActivity.this,"The Account Has Been Saved", Toast.LENGTH_LONG).show();
        					Intent i = new Intent(getApplicationContext(), AccountActivity.class);
        					startActivity(i);
        				}else{
        					Toast.makeText(AccountActivity.this,"The Account Hasn't Been Saved, Please Try Again", Toast.LENGTH_LONG).show();
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
