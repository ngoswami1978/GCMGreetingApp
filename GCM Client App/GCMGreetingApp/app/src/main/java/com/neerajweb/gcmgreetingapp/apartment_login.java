package com.neerajweb.gcmgreetingapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.app.ProgressDialog;
import com.neerajweb.gcmgreetingapp.dao.OwnerDAO;
import com.neerajweb.gcmgreetingapp.dao.SettingsService;
import java.util.Random;
import com.neerajweb.gcmgreetingapp.helper.AlertDialogManager;
import com.neerajweb.gcmgreetingapp.helper.ConnectionDetector;

/**
 * Created by Admin on 20/08/2015.
 */

public class apartment_login extends Activity  implements View.OnClickListener {
    public static final String TAG = "LoginActivity";
    Button bSignIn;
    EditText etUsername, etpwd;
    TextView tvregister, tvforget, id, name, flat, age;
    Boolean blnchkRemember;
    Boolean isLogin;
    Boolean remeberCredentials = true  ;
    private SharedPreferences preferences;
    String strReminder;
    private OwnerDAO mOwnerDao;
    SettingsService settingservice = new SettingsService();
    LinearLayout linearLayout;
    int images[];

    //ListView object for displaying data from database
    ListView listItems;

    //Message TextView object for displaying data
    TextView shMsg;

    // Connection detector
    ConnectionDetector cd;

    // Alert dialog manager
    AlertDialogManager alert = new AlertDialogManager();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.apartment_login);

            cd = new ConnectionDetector(getApplicationContext());

            // Check for internet connection
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present
                alert.showAlertDialog(apartment_login.this, "Internet Connection Error",
                        "Please connect to working Internet connection", false);
                // stop executing code by return
                return;
            }

            /**
             * Adding our layout to parent class frame layout.
             */
            //getLayoutInflater().inflate(R.layout.apartment_login, frameLayout);

            linearLayout = (LinearLayout) findViewById(R.id.LoginActivity);
            images = new int[]  {R.drawable.apartment3, R.drawable.apartment4, R.drawable.apartment5, R.drawable.apartment9};
            linearLayout.setBackgroundResource(images[getRandomNumber()]);


                // Create our sqlite database object
                this.mOwnerDao = new OwnerDAO(this);

                preferences =  SettingsService.SettingsService(this.getApplicationContext());
                blnchkRemember = true;
                isLogin = false;

                try {
                    strReminder = settingservice.GetSetting(this, "RemeberCredentials", String.valueOf(""));
                }
                catch (Exception ex) {
                    Log.d(TAG, "Error in decrypt method : " + ex.getMessage());
                    strReminder="";
                }
                if (strReminder==""){strReminder="0";}
                remeberCredentials = Boolean.valueOf(strReminder);
                blnchkRemember = remeberCredentials;

                hideControls(true);

                // Create your application here
                bSignIn = (Button) findViewById(R.id.bSignIn);
                tvregister = (TextView) findViewById(R.id.tvregister);
                tvforget = (TextView) findViewById(R.id.tvforget);

                etUsername = (EditText) findViewById(R.id.etUsername);
                this.etUsername.addTextChangedListener(new TextWatcher() {
                    public void afterTextChanged(Editable s) {
                        Validation.hasText(etUsername);
                    }
                    public void beforeTextChanged(CharSequence s, int start, int count, int after){}
                    public void onTextChanged(CharSequence s, int start, int before, int count){}
                });

                etpwd = (EditText) findViewById(R.id.etpwd);
                this.etpwd.addTextChangedListener(new TextWatcher() {
                    public void afterTextChanged(Editable s) {
                        Validation.hasText(etpwd);
                    }
                    public void beforeTextChanged(CharSequence s, int start, int count, int after){}
                    public void onTextChanged(CharSequence s, int start, int before, int count){}
                });

                if (remeberCredentials == true) {
                    String savedusername = settingservice.GetSetting(this, "username", String.valueOf(""));
                    String savedpassword = settingservice.GetSetting(this, "password", String.valueOf(""));

                    if (savedusername != null) {
                        etUsername.setText(savedusername.toString());
                        etpwd.setText(savedpassword.toString());
                    }
                }

                CheckBox checkbox = (CheckBox) findViewById(R.id.checkbox_remember_me);
                checkbox.setChecked(blnchkRemember);

                //Gets TextView object instances
                shMsg = (TextView) findViewById(R.id.shMsg);

                //Gets ListView object instance
                listItems = (ListView) findViewById(R.id.listItems);

                //Add ItemClick event handler to ListView instance
                listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //setTitle(parent.getItemAtPosition(position).toString());

                        //Gets TextView object instance from record_view layout
                        TextView shId = (TextView) view.findViewById(R.id.Id_row);
                        TextView shName = (TextView) view.findViewById(R.id.Name_row);
                        TextView shFlatNo = (TextView) view.findViewById(R.id.Flat_row);
                        TextView shAge = (TextView) view.findViewById(R.id.Age_row);

                        //Displays messages for CRUD operations
                        shMsg.setText(shId.getText());

                        //Intent intent = new Intent(apartment_login.this, drawable_main_activity.class);
                        //startActivity(intent);
                        //var myIntent = new Intent (this, typeof(drawable_main_activity));

                        /*
                             var myIntent = new Intent (this, typeof(WelcomeActivity));

                            //Assuming you're using a ListActivity as the basis for your listview you can use the following code to bind the ItemClick event of the listview.
                            //Within the listview you can then finish the current activity and start the next one,passing in the ID or any other property of the selected item.
                            // Start the second activity and finish this activity.
                            // NOTICE: Finishing the activity removes it from the backstack.
                            // The user will not be able to navigate back to the list!

                            myIntent.PutExtra ("LOGIN_ID", shId.Text);
                            myIntent.PutExtra ("LOGIN_USERNAME", settingservice.LoginUserName);
                            myIntent.PutExtra ("LOGIN_PASSWORD", settingservice.LoginPassword);

                            StartActivityForResult (myIntent, 0);
                            Finish();
                        */

                    }
                });

                //Sets Database class message property to shMsg TextView instance
                shMsg.setText(mOwnerDao.sqldb_message);

                bSignIn.setOnClickListener(this);
                tvregister.setOnClickListener(this);
                tvforget.setOnClickListener(this);

                checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked()) {
                            Toast.makeText(apartment_login.this, "Selected", Toast.LENGTH_LONG).show();
                            blnchkRemember = true;
                        } else {
                            Toast.makeText(apartment_login.this, "Not Selected", Toast.LENGTH_LONG).show();
                            blnchkRemember = false;
                        }
                    }
                });
        } catch (Exception e) {
            Log.d(TAG, "Error in apartment_login method : " + e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    protected void onResume()
    {
        super.onResume();
       if(linearLayout != null){
           linearLayout.setBackgroundResource(images[getRandomNumber()]);
        }
    }

    private int getRandomNumber() {
        //Note that general syntax is Random().nextInt(n)
        //It results in range 0-4
        //So it should be equal to number of images in images[] array
        return new Random().nextInt(4);
    }

    private void hideControls(Boolean blnHd) {
        try {
            id = (TextView) findViewById(R.id.id);
            name = (TextView) findViewById(R.id.name);
            flat = (TextView) findViewById(R.id.flat);
            age = (TextView) findViewById(R.id.age);

            if (blnHd == true) {
                id.setVisibility(View.GONE);
                name.setVisibility(View.GONE);
                flat.setVisibility(View.GONE);
                age.setVisibility(View.GONE);
            }
            if (blnHd == false) {
                id.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                flat.setVisibility(View.VISIBLE);
                age.setVisibility(View.VISIBLE);
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.tvregister:
                    Intent intent = new Intent(apartment_login.this, apartment_registeration_activity.class);
                    startActivity(intent);
                    break;

                case R.id.bSignIn:
                    if (checkValidation())
                        submitForm();
                    else
                        Toast.makeText(apartment_login.this, "Please enter login name and password", Toast.LENGTH_LONG).show();
                    break;

                case R.id.tvforget:
                    break;
            }

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void submitForm() {
        // Submit your form here. your form is valid
        //Toast.makeText(this, "Submitting form...", Toast.LENGTH_LONG).show();
        try
        {
            isLogin=false;
            new fetchUserDataInBackground().execute(etUsername.getText().toString(), etpwd.getText().toString());
            //Toast.makeText(this, "Hi------" + isLogin.toString(), Toast.LENGTH_LONG).show();
        }catch (Exception e) {
            Log.d(TAG, "Error in submitForm method in apartment_login activity : " + e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean ValidateSqluserpwd(String mUname, String mPwd)
    {
        try
        {
            isLogin=false;
            settingservice.setLoginUserName(String.valueOf(""));
            settingservice.setLoginPassword(String.valueOf(""));

            /* One methid to extract
            List<owner_model> lstOwners= mOwnerDao.getOwnerbyUnamePwd(mUname,mPwd);

            for (owner_model info : lstOwners) {
                String ownerDetails = "Name    "+ info.getName() + "    Pwd    " + info.getPassword();
                Log.d("Owner information ", ownerDetails);
            }*/

            Cursor sqldb_cursor = mOwnerDao.fetchOwnerbyUnamePwd(mUname,mPwd);

            if (sqldb_cursor != null && sqldb_cursor.getCount()>=1)
            {
                // The desired columns to be bound
                String[] columns = new String[] {"_id","NAME","Flat_name","AGE" };

                // the XML defined views which the data will be bound to
                int[] to = new int[] {
                        R.id.Id_row,
                        R.id.Name_row,
                        R.id.Flat_row,
                        R.id.Age_row
                };

                // create the adapter using the cursor pointing to the desired data as well as the layout information
                shMsg.setText(mOwnerDao.sqldb_message); // Welcome Message to the User is Observed here.
                SimpleCursorAdapter sqldb_adapter = new SimpleCursorAdapter (this, R.layout.record_view, sqldb_cursor, columns, to);
                listItems.setAdapter(sqldb_adapter);

                //apartment_login Success
                isLogin=true;
                return isLogin;
            }
            else
            {
                //apartment_login failed
                isLogin=false;
                listItems.setAdapter(null);
                shMsg.setText(mOwnerDao.sqldb_message);
                hideControls(true);
            }
        }
        catch (Exception e)
        {
            //apartment_login failed
            isLogin=false;
            Log.d(TAG, "Error in ValidateSqluserpwd : " + e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return isLogin;
    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText(etpwd )) ret = false;
        if (!Validation.hasText(etUsername)) ret = false;
        return ret;
    }

    // Class to fetch data asynchronous
    private class fetchUserDataInBackground extends AsyncTask<String, Void, String> {
        ProgressDialog progress;
        String result = null;

        public fetchUserDataInBackground() {
            progress = new ProgressDialog(apartment_login.this);
            progress.setIndeterminate(true);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setTitle("Please wait...");
            progress.setMessage("Checking apartment_login info...");
            progress.setCancelable(true);
            progress.show();
        }

        @Override
        protected String doInBackground(final String... params) {
            //Added sleep so that you can see messages from onPreExecute
            //and after that Inside doInBackground clearly.
            try{
               Thread.sleep(5000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }

            // perform Long time consuming operation
            Log.d(TAG, "Just started doing stuff in background");
            //New thread is created because this function can't update UI Thread.
            runOnUiThread(new Thread() {
                public void run() {
                    isLogin = ValidateSqluserpwd(params[0].toString(), params[1].toString());
                }
            } );
            publishProgress(); // It calls onProgressUpdate Method.
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            //Log.d(TAG, "onProgressUpdate");
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progress.dismiss();

            if (isLogin==false)
            {
                hideControls(true);
                Toast.makeText(apartment_login.this, "apartment_login failed pls try again...", Toast.LENGTH_LONG).show();
            }
            else
            {
                if (blnchkRemember == true)
                {
                    try{
                        settingservice.AddOrUpdateSetting(apartment_login.this, "RemeberCredentials", "true");
                        settingservice.AddOrUpdateSetting(apartment_login.this, "username", etUsername.getText());
                        settingservice.AddOrUpdateSetting(apartment_login.this, "password", etpwd.getText());
                    }
                    catch (Exception e) {
                        Log.d(TAG, "Error in onPostExecute method in onPostExecute : " + e.getMessage());
                    }
                }
                else if(blnchkRemember == false)
                {
                    try {
                        settingservice.AddOrUpdateSetting(apartment_login.this, "RemeberCredentials", "false");
                        settingservice.AddOrUpdateSetting(apartment_login.this, "username", String.valueOf(""));
                        settingservice.AddOrUpdateSetting(apartment_login.this, "password", String.valueOf(""));
                    } catch (Exception e) {
                        Log.d(TAG, "Error in onPostExecute method in onPostExecute : " + e.getMessage());
                    }
                }
                hideControls(false);
                etUsername.setText(String.valueOf(""));
                etpwd.setText(String.valueOf(""));
                etUsername.setHint("Username *");
                etpwd.setHint("Password *");
                etUsername.setError(null);
                etpwd.setError(null);
            }
        }
    }
}