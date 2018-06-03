package capitalcryptoworld.capitalworld.com.newhajjapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import capitalcryptoworld.capitalworld.com.newhajjapp.Controller.RestManager;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.HajiSelect;
import capitalcryptoworld.capitalworld.com.newhajjapp.R;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminSelectPeople extends AppCompatActivity {
    EditText editText ;
    Button button;
    RestManager restManager;
    String a;
    AlertDialog dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_select_people);
        editText = (EditText)findViewById(R.id.et_people);



        button = (Button)findViewById(R.id.btn_people);
        restManager = new RestManager();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = editText.getText().toString();
                if(a.equals("")){
                    dialogBuilder = new AlertDialog.Builder(AdminSelectPeople.this).create();
                    View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                    TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                    TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                    TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                    msgError.setText("Enter people for draw");
                    dialogBuilder.setView(customView);
                    dialogBuilder.show();
                }
                else {

new DrawPeople().execute();
                }

            }
        });
    }

    //

    public class DrawPeople extends AsyncTask<String,Integer,String>
    { ProgressDialog mDialog;


        protected  void onPreExecute()
        {



            mDialog = ProgressDialog.show(AdminSelectPeople.this,"Please wait...", "User draw in process ...", true);
        }
        @Override
        protected String doInBackground(String... strings) {
/////////////////

            retrofit2.Call<HajiSelect> call = restManager.getServices().isHajiSelect(a);
            call.enqueue(new Callback<HajiSelect>() {
                @Override
                public void onResponse(retrofit2.Call<HajiSelect> call, Response<HajiSelect> response) {

                    if (response.isSuccessful()) {
                        Log.d("Responsexxx", response.body().isSuccess() + "");
                        mDialog.dismiss();
                        Toast.makeText(AdminSelectPeople.this, "Sucessfully register people for hajj", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminSelectPeople.this, AllSelectedHaji.class);
                        startActivity(intent);
                    } else {
                        mDialog.dismiss();
                        dialogBuilder = new AlertDialog.Builder(AdminSelectPeople.this).create();
                        View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                        TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                        TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                        TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                        msgError.setText("Drwa is already done");
                        dialogBuilder.setView(customView);
                        dialogBuilder.show();
                    }


                }

                @Override
                public void onFailure(retrofit2.Call<HajiSelect> call, Throwable t) {
                    mDialog.dismiss();
                    dialogBuilder = new AlertDialog.Builder(AdminSelectPeople.this).create();
                    View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                    TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                    TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                    TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                    msgError.setText("There is no internet connection");
                    dialogBuilder.setView(customView);
                    dialogBuilder.show();

                }
            });




















            ////////////////


            return null;
        }
        protected void onPostExecute(String result) {

        }

    }















    //






















    public void cancel(View view){
        dialogBuilder.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AdminSelectPeople.this,AdminDashboard.class);
        startActivity(intent);
    }
}
