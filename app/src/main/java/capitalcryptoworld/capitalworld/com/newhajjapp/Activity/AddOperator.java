package capitalcryptoworld.capitalworld.com.newhajjapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import capitalcryptoworld.capitalworld.com.newhajjapp.Controller.RestManager;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.Register;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.RegisterResponse;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.User;
import capitalcryptoworld.capitalworld.com.newhajjapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddOperator extends AppCompatActivity {
    EditText name,surName,userNames,email,password,phnNumber;
    Button start;
    RestManager restManager;
    TextView goLogin;
    AlertDialog dialogBuilder;
    String userName,userSurName,myUserName,userEmail,userPhnNumber,userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_operator);
        configView();
        restManager = new RestManager();
      
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = name.getText().toString();
                userSurName = surName.getText().toString();
                userEmail = email.getText().toString();
                userPhnNumber = phnNumber.getText().toString();
                userPassword = password.getText().toString();
                myUserName = userNames.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String phonePattern = "\\d{4}-\\d{7}";
                if(userName.equals("") || userSurName.equals("")|| userEmail.equals("") || userPhnNumber.equals("")|| userPassword.equals("") || myUserName.equals("")){
                    dialogBuilder = new AlertDialog.Builder(AddOperator.this).create();
                    View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                    TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                    TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                    TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);

                    msgError.setText("Please fill all the field");

                    dialogBuilder.setView(customView);
                    dialogBuilder.show();
                }
                else if (!(userEmail.matches(emailPattern)))
                {
                    dialogBuilder = new AlertDialog.Builder(AddOperator.this).create();
                    View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                    TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                    TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                    TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);

                    msgError.setText("Unvalid Email Address");

                    dialogBuilder.setView(customView);
                    dialogBuilder.show();
                }
                else if(!(userPhnNumber.matches(phonePattern))){
                    dialogBuilder = new AlertDialog.Builder(AddOperator.this).create();
                    View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                    TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                    TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                    TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);

                    msgError.setText("Unvalid Phone Number write like 0323-4227475");

                    dialogBuilder.setView(customView);
                    dialogBuilder.show();

                }



                else  {


                    new UserRegister().execute();

                }



            }
        });
    }

    private void configView() {
        name = (EditText)findViewById(R.id.et_name);
        surName = (EditText)findViewById(R.id.et_surname);
        email = (EditText)findViewById(R.id.et_email);
        phnNumber = (EditText)findViewById(R.id.et_phnnumber);
        password =(EditText)findViewById(R.id.et_password);
        userNames =(EditText)findViewById(R.id.et_userName);


        start = (Button)findViewById(R.id.btn_sign);

    }




    public class UserRegister extends AsyncTask<String,Integer,String>
    {
        private ProgressDialog mDialog;

        User user = new User(userName,userSurName,myUserName,userEmail,userPhnNumber,userPassword,true);
        Register register = new Register(user, new String[]{"Operator"});

        protected  void onPreExecute()
        {

            mDialog = ProgressDialog.show(AddOperator.this,"Please wait...", "AddOperator is in process ...", true);
        }
        @Override
        protected String doInBackground(String... strings) {
            Call<RegisterResponse> call = restManager.getServices().sendUser(register);
            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(AddOperator.this,"Operator Add Sucessfully",Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                        Intent intent = new Intent(AddOperator.this,AdminDashboard.class);
                        startActivity(intent);
                        // Intent intent = new Intent(AddOperator.this,Log.class);
                        // startActivity(intent);


                    }

                    else{
                        //  Log.d("Error response",response.body().toString());
                        // Toast.makeText(AddOperator.this, "Email or username already exist", Toast.LENGTH_LONG).show();

                        dialogBuilder = new AlertDialog.Builder(AddOperator.this).create();
                        View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                        TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                        TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                        TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                        msgError.setText("Email or username alaready exist");
                        dialogBuilder.setView(customView);
                        dialogBuilder.show();
                        mDialog.dismiss();
                    }



                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    // Log.d("Internet response",t.getMessage());

                    dialogBuilder = new AlertDialog.Builder(AddOperator.this).create();
                    View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                    TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                    TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                    TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                    msgError.setText("There is no internet connection");
                    dialogBuilder.setView(customView);
                    dialogBuilder.show();
                    mDialog.dismiss();


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
