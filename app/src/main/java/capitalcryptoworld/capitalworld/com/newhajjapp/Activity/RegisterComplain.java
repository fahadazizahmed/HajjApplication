package capitalcryptoworld.capitalworld.com.newhajjapp.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import capitalcryptoworld.capitalworld.com.newhajjapp.Controller.RestManager;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.ComplainStatus;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.Register;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.Status;
import capitalcryptoworld.capitalworld.com.newhajjapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterComplain extends AppCompatActivity {
    SharedPreferences spref;
    static String filename="my personal file";
    public static final String Value = "could not load data";
    int complainId;
    RestManager restManager;
    TextView textView;
    TextView textView1;
    capitalcryptoworld.capitalworld.com.newhajjapp.Model.Status status1;
    AlertDialog dialogBuilder;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_complain);
        new GetComplainStatus().execute();
        textView = (TextView)findViewById(R.id.tx_title);
        textView1 = (TextView)findViewById(R.id.tx_des);
        button = (Button)findViewById(R.id.btn_sign) ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterComplain.this,AssignOperatorRecord.class);
                startActivity(intent);
            }
        });
        new GetComplainStatus().execute();

    }


   //

    public class GetComplainStatus extends AsyncTask<String,Integer,String>
    {
        private ProgressDialog mDialog;



        protected  void onPreExecute()
        {
            restManager = new RestManager();

            spref=getSharedPreferences(filename, Context.MODE_PRIVATE);//this file only aceess your app

            complainId = spref.getInt("complain",9);

            mDialog = ProgressDialog.show(RegisterComplain.this,"Please wait...", "Get Complain Detail ...", true);
        }
        @Override
        protected String doInBackground(String... strings) {
            Call<ComplainStatus> complainStatusCall  = restManager.getServices().getComplainStatus(complainId);
            complainStatusCall.enqueue(new Callback<ComplainStatus>() {
                @Override
                public void onResponse(Call<ComplainStatus> call, Response<ComplainStatus> response) {
                    if(response.isSuccessful()){
                      capitalcryptoworld.capitalworld.com.newhajjapp.Model.Status[] status =  response.body().getResult();

                      for(int i=0;i<status.length;i++){
                         status1 = status[0];
                      }
                        textView.setText(status1.getTitle());
                        textView1.setText(status1.getDescription());
                        mDialog.dismiss();

                    }
                    else {
                        mDialog.dismiss();
                        dialogBuilder = new android.app.AlertDialog.Builder(RegisterComplain.this).create();
                        View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                        TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                        TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                        TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                        msgError.setText("We cannot get your Complain");
                        dialogBuilder.setView(customView);
                        dialogBuilder.show();
                        mDialog.dismiss();

                    }

                }

                @Override
                public void onFailure(Call<ComplainStatus> call, Throwable t) {
                    mDialog.dismiss();
                    dialogBuilder = new android.app.AlertDialog.Builder(RegisterComplain.this).create();
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
























    ///












}
