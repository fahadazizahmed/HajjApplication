package capitalcryptoworld.capitalworld.com.newhajjapp.Activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import capitalcryptoworld.capitalworld.com.newhajjapp.Controller.RestManager;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.AuthToken;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.ComplainResponse;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.Complaint;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.LoginModel;
import capitalcryptoworld.capitalworld.com.newhajjapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplaintForm extends AppCompatActivity {
    EditText title;
    EditText description;
    LinearLayout submit;
    Spinner spinner;
    String [] paths={"Normal","Medium","High"};
    String complainLevel;
    RestManager restManager;
    SharedPreferences spref;
    static String filename="my personal file";
    public static final String Value = "could not load data";
    String concateStringWithToken;
    String authHeader;
    String token;
    AlertDialog dialogBuilder;
    LoginModel loginModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_complaint_form);
        spref=getSharedPreferences(filename,0);


        //ArrayAdapter<String> adapter= new ArrayAdapter<String>(ComplaintForm.this,android.R.layout.simple_spinner_dropdown_item,paths);
        //spinner =(Spinner)findViewById(R.id.spinner);
        //spinner.setAdapter(adapter);



        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, paths);
        final MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.android_material_design_spinner);
        materialDesignSpinner.setAdapter(arrayAdapter);

        title = (EditText)findViewById(R.id.et_title);
        description = (EditText)findViewById(R.id.et_detail);
        submit = (LinearLayout)findViewById(R.id.send_report);
        restManager = new RestManager();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().toString().equals("")|| description.getText().toString().equals("")){
                    dialogBuilder = new AlertDialog.Builder(ComplaintForm.this).create();
                    View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                    TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                    TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                    TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);

                    msgError.setText("Please fill all the field");

                    dialogBuilder.setView(customView);
                    dialogBuilder.show();
                }
                else{
                    new UserComplain().execute();

                }




            }
        });

        materialDesignSpinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
             complainLevel=materialDesignSpinner.getText().toString();
               // Toast.makeText(ComplaintForm.this,quantity,Toast.LENGTH_SHORT).show();


            }
        });


    }



    public class UserComplain extends AsyncTask<String,Integer,String>
    { ProgressDialog mDialog;
        SharedPreferences.Editor editor;


        protected  void onPreExecute()
        {
            spref=getSharedPreferences(filename, Context.MODE_PRIVATE);//this file only aceess your app
             editor = spref.edit();
            token = spref.getString("Token", Value);
            concateStringWithToken = "Bearer";
            authHeader = concateStringWithToken+" "+token;
           String myName =   spref.getString("username","name");
          String myPasword=  spref.getString("userpass","pass");



           loginModel = new LoginModel(myName,myPasword);

            mDialog = ProgressDialog.show(ComplaintForm.this,"Please wait...", "Complaint Sent ...", true);
        }
        @Override
        protected String doInBackground(String... strings) {
            Complaint complaint = new Complaint(title.getText().toString(),description.getText().toString(),complainLevel,true);
            retrofit2.Call<ComplainResponse> call = restManager.getServices().sendComplain(authHeader,complaint);
            call.enqueue(new Callback<ComplainResponse>() {
                @Override
                public void onResponse(retrofit2.Call<ComplainResponse> call, Response<ComplainResponse> response) {
                    if(response.isSuccessful())
                    {



                        Toast.makeText(ComplaintForm.this,"Your compalain is sent we accomodate you soon!Thanks",Toast.LENGTH_SHORT).show();


                        //




                        Call<AuthToken> calls = restManager.getServices().getToken(loginModel);
                        calls.enqueue(new Callback<AuthToken>() {
                            @Override
                            public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {
                                if (response.isSuccessful()) {
                                    int complain = response.body().getResult().getComplaintId();

                                    int opeId = response.body().getResult().getOperatorId();




                                    editor.putInt("complain",complain);
                                    editor.putInt("opeId",opeId);
                                    editor.commit();
                                    mDialog.dismiss();

                                    Intent intent = new Intent(ComplaintForm.this,RegistrationOption.class);
                                    startActivity(intent);
                                    title.setText("");
                                    description.setText("");




                                }


                                else {
                                    dialogBuilder = new android.support.v7.app.AlertDialog.Builder(ComplaintForm.this).create();
                                    View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                                    TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                                    TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                                    TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                                    msgError.setText("Some problem to register complain");
                                    dialogBuilder.setView(customView);
                                    dialogBuilder.show();
                                    mDialog.dismiss();
                                    //  Toast.makeText(Login.this, "User name or Password is not correct", Toast.LENGTH_LONG).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<AuthToken> call, Throwable t) {


                                dialogBuilder = new android.support.v7.app.AlertDialog.Builder(ComplaintForm.this).create();
                                View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                                TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                                TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                                TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                                msgError.setText("Oops!there ia no internet connection");
                                dialogBuilder.setView(customView);
                                dialogBuilder.show();
                                mDialog.dismiss();
                            }
                        });













                        ///















                    }

                    else

                        {

                                mDialog.dismiss();
                                dialogBuilder = new AlertDialog.Builder(ComplaintForm.this).create();
                                View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                                TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                                TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                                TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                                msgError.setText("There is some problem to send complain");
                                dialogBuilder.setView(customView);
                                dialogBuilder.show();

                        }

                }

                @Override
                public void onFailure(retrofit2.Call<ComplainResponse> call, Throwable t) {
                    mDialog.dismiss();
                    dialogBuilder = new AlertDialog.Builder(ComplaintForm.this).create();
                    View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                    TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                    TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                    TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);

                    msgError.setText("There is no internet connection");

                    dialogBuilder.setView(customView);
                    dialogBuilder.show();

                }
            });

            return null;
        }
        protected void onPostExecute(String result) {

        }

    }

    public void cancel(View view){
        dialogBuilder.dismiss();
    }

























}
