package capitalcryptoworld.capitalworld.com.newhajjapp.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import capitalcryptoworld.capitalworld.com.newhajjapp.Controller.RestManager;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.GetOperatorDetail;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.OperatorDetails;
import capitalcryptoworld.capitalworld.com.newhajjapp.R;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignOperatorRecord extends AppCompatActivity {
    SharedPreferences spref;
    static String filename = "my personal file";
    public static final String Value = "could not load data";
    int complainId;
    RestManager restManager;
    TextView textView;
    TextView textView1,textView2;
    capitalcryptoworld.capitalworld.com.newhajjapp.Model.Status status1;
    android.app.AlertDialog dialogBuilder;
    Button button;
    String authHeader;
    LinearLayout linearLayout1,linearLayout2,linearLayout3;
    TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_assign_operator_record);
        new GetComplainStatus().execute();
        textView = (TextView) findViewById(R.id.tx_title);
        textView1 = (TextView) findViewById(R.id.tx_des);
        textView2 = (TextView) findViewById(R.id.etx_dsig);
        textView3 = (TextView) findViewById(R.id.no);
        linearLayout1 = (LinearLayout)findViewById(R.id.l1);
        linearLayout2 = (LinearLayout)findViewById(R.id.l2);
        linearLayout3 = (LinearLayout)findViewById(R.id.l3);
        button = (Button) findViewById(R.id.btn_sign);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0323"));
                if (ActivityCompat.checkSelfPermission(AssignOperatorRecord.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });
        new GetComplainStatus().execute();

    }


    //

    public class GetComplainStatus extends AsyncTask<String, Integer, String> {
        private ProgressDialog mDialog;


        protected void onPreExecute() {
            restManager = new RestManager();


            spref = getSharedPreferences(filename, Context.MODE_PRIVATE);//this file only aceess your app
            String token = spref.getString("Token", Value);
            String concateStringWithToken = "Bearer";
            authHeader = concateStringWithToken + " " + token;


            mDialog = ProgressDialog.show(AssignOperatorRecord.this, "Please wait...", "Get operator detail Detail ...", true);
        }

        @Override
        protected String doInBackground(String... strings) {

            retrofit2.Call<OperatorDetails> call = restManager.getServices().getope(authHeader);
            call.enqueue(new Callback<OperatorDetails>() {
                @Override
                public void onResponse(retrofit2.Call<OperatorDetails> call, Response<OperatorDetails> response) {
                    if (response.isSuccessful()) {
                        mDialog.dismiss();
                       GetOperatorDetail getOperatorDetail =  response.body().getResult();
                       linearLayout1.setVisibility(View.VISIBLE);
                       linearLayout2.setVisibility(View.VISIBLE);
                       linearLayout3.setVisibility(View.VISIBLE);
                       button.setVisibility(View.VISIBLE);
                       textView.setText(getOperatorDetail.getName());
                       textView1.setText(getOperatorDetail.getMobile());
                        textView2.setText(getOperatorDetail.getDesignation());


                    } else {

                        mDialog.dismiss();
                        textView3.setVisibility(View.VISIBLE);


                    }
                }

                @Override
                public void onFailure(retrofit2.Call<OperatorDetails> call, Throwable t) {
                    mDialog.dismiss();
                    textView3.setVisibility(View.VISIBLE);

                }


            });


            return null;
        }

        protected void onPostExecute(String result) {

        }

    }

    public void cancel(View view) {
        dialogBuilder.dismiss();
    }


}