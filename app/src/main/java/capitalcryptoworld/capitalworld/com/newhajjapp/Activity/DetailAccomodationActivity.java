package capitalcryptoworld.capitalworld.com.newhajjapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import capitalcryptoworld.capitalworld.com.newhajjapp.Controller.RestManager;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.AccomodationList;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.AllAccomodation;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.AssignedAccomodation;
import capitalcryptoworld.capitalworld.com.newhajjapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAccomodationActivity extends AppCompatActivity {
    TextView name,phn,price,available,location;
    SharedPreferences spref;
    static String filename="my personal file";
    public static final String Value = "could not load data";
    Button selectPackage;
    int type;
    RestManager restManager;
    AlertDialog dialogBuilder;
    String token;
    String authHeader;
    String concateStringWithToken;
    ImageView imageView;
    int [] a = {R.drawable.na,R.drawable.nb,R.drawable.qa,R.drawable.qb,R.drawable.p1,R.drawable.p2,R.drawable.pa,R.drawable.r};


     SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail_accomodation);
        imageView = (ImageView)findViewById(R.id.img);
        spref=getSharedPreferences(filename,0);
         editor = spref.edit();

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        AllAccomodation allAccomodation = (AllAccomodation) intent.getSerializableExtra("value");
       int v =bundle.getInt("fer",23);
        name = (TextView)findViewById(R.id.tx_name);
        phn = (TextView)findViewById(R.id.tx_contact);
        price = (TextView)findViewById(R.id.tx_price);
        location = (TextView)findViewById(R.id.tx_location);
        available = (TextView)findViewById(R.id.tx_availabe);
        selectPackage = (Button)findViewById(R.id.btn_sign);
        restManager = new RestManager();

        int ids = allAccomodation.getId();

        name.setText(allAccomodation.getName().toString());
        phn.setText(allAccomodation.getContact()+"");
        price.setText(allAccomodation.getPrice());
        available.setText(allAccomodation.getAvailable()+"");
        location.setText(allAccomodation.getLocation());



        imageView.setImageResource(a[v]);
        Toast.makeText(DetailAccomodationActivity.this,ids+"",Toast.LENGTH_SHORT).show();
        editor.putInt("AccomodationId",ids);
        editor.commit();

        spref=getSharedPreferences(filename, Context.MODE_PRIVATE);//this file only aceess your app
        token = spref.getString("Token", Value);

        type=spref.getInt("AccomodationId",23);

        Log.d("show id",type+"");
        selectPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AccomdateNow().execute();

               // Toast.makeText(DetailAccomodationActivity.this,type+"detail",Toast.LENGTH_SHORT).show();

            }
        });



    }

    public void cancel(View view)
    {
        dialogBuilder.dismiss();
    }


    public class AccomdateNow extends AsyncTask<String,Integer,String>
        {
            private ProgressDialog mDialog;






            protected  void onPreExecute()
            {
                concateStringWithToken = "Bearer";
                authHeader = concateStringWithToken+" "+token;


                mDialog = ProgressDialog.show(DetailAccomodationActivity.this,"Please wait...", "Accomodation Assign is in process ...", true);
            }
            @Override
            protected String doInBackground(String... strings) {
                Call<AssignedAccomodation> call = restManager.getServices().AccomodationAssigned(authHeader,type);
                call.enqueue(new Callback<AssignedAccomodation>() {
                    @Override
                    public void onResponse(Call<AssignedAccomodation> call, Response<AssignedAccomodation> response) {
                        if(response.isSuccessful()){

                            mDialog.dismiss();
                            editor.putInt("accomodation",type);
                            editor.commit();


                           // editor.commit();
                            // Toast.makeText(DetailAccomodationActivity.this,"Accomodation is assigned to you",Toast.LENGTH_SHORT).show();


                         Intent intent = new Intent(DetailAccomodationActivity.this,RegistrationOption.class);
                         startActivity(intent);
                    }
                    else {
                            dialogBuilder = new AlertDialog.Builder(DetailAccomodationActivity.this).create();
                            View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                            TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                            TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                            TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                            msgError.setText("Oops!Accomdation is not assigned");
                            dialogBuilder.setView(customView);
                            dialogBuilder.show();
                            mDialog.dismiss();

                        }


                    }

                    @Override
                    public void onFailure(Call<AssignedAccomodation> call, Throwable t) {
                        dialogBuilder = new AlertDialog.Builder(DetailAccomodationActivity.this).create();
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

                return null;
            }
            protected void onPostExecute(String result) {

            }

        }






}
