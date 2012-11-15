package com.cnam.jmoney;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ProjectActivity extends Activity {
	Button add_btn,delete_btn;
	ListView listview;
	SimpleCursorAdapter adapter;
	Cursor cursor;
	DBHelper db;
	Project project_db;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_layout);
        
        db = new DBHelper(this);
        project_db = new Project(db.getDB());
        
        add_btn = (Button)findViewById(R.id.btn_add_project);
        delete_btn = (Button)findViewById(R.id.btn_delete_project);
        
        add_btn.setText("Add Project");
        delete_btn.setText("Delete");
        
    	cursor = project_db.getAllLabelsCursor();
        
        adapter = new SimpleCursorAdapter(this,
        android.R.layout.simple_list_item_checked, 
        cursor, 
        new String[] { Project.T.COL_NAME },
        new int[] {android.R.id.text1});
        
        listview = (ListView) findViewById(R.id.projects_list);
        listview.setAdapter(adapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        delete_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                long[] checkedIds = listview.getCheckedItemIds();
                for(long id : checkedIds)
                    project_db.delete(id);
                listview.clearChoices();
                adapter.changeCursor(project_db.getAllLabelsCursor());
            }
        });
        
        add_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//Preparing views
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.project_dialog_layout, null);

                //layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.
                final EditText project_name = (EditText) layout.findViewById(R.id.project_name);
                project_name.setHint("Name");
                
                
                //Building dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ProjectActivity.this);
                builder.setView(layout);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = project_name.getText().toString();
                        
        				long id;
        				id = project_db.add(name);
        				if(id > 0){
        					Toast.makeText(ProjectActivity.this,"The Account Has Been Saved", Toast.LENGTH_LONG).show();
        					Intent i = new Intent(getApplicationContext(), ProjectActivity.class);
        					startActivity(i);
        				}else{
        					Toast.makeText(ProjectActivity.this,"The Account Hasn't Been Saved, Please Try Again", Toast.LENGTH_LONG).show();
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
