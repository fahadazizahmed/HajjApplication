package capitalcryptoworld.capitalworld.com.newhajjapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import capitalcryptoworld.capitalworld.com.newhajjapp.Controller.RestManager;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.GetAccomp;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.YourSelectAccomodation;
import capitalcryptoworld.capitalworld.com.newhajjapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourAccomodation extends AppCompatActivity {

    android.app.AlertDialog dialogBuilder;
    private RestManager mManager;
    GetAccomp getAccomp1;
    TextView name,price,location,feature,contact;

    SharedPreferences spref;
    static String filename="my personal file";
    public static final String Value = "could not load data";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_your_accomodation);

        configViews();
        mManager=new RestManager();
        new GetYourAccomodation().execute();

    }

    private void configViews() {
        name = (TextView)findViewById(R.id.tx_name);
        feature = (TextView)findViewById(R.id.tx_feature);
        location = (TextView)findViewById(R.id.tx_location);
        price = (TextView)findViewById(R.id.tx_price);
        contact = (TextView) findViewById(R.id.tx_contact);
    }


    public class GetYourAccomodation extends AsyncTask<String,Integer,String>
    {
        private ProgressDialog mDialog;
        int AccommodationId;


        protected  void onPreExecute()
        {
            spref=getSharedPreferences(filename, Context.MODE_PRIVATE);//this file only aceess your app
            AccommodationId = spref.getInt("accomodation",9);

            mDialog = ProgressDialog.show(YourAccomodation.this,"Please wait...", "Get Your Accomodation Detail ...", true);
        }
        @Override
        protected String doInBackground(String... strings) {

            Call<YourSelectAccomodation> call = mManager.getServices().getyourAccomodation(AccommodationId);
            call.enqueue(new Callback<YourSelectAccomodation>() {
                @Override
                public void onResponse(Call<YourSelectAccomodation> call, Response<YourSelectAccomodation> response) {
                    if (response.isSuccessful()) {


                        GetAccomp[] getAccomp = response.body().getResult();
                        for (int i = 0; i < getAccomp.length; i++) {
                            getAccomp1 = getAccomp[0];
                        }
                        mDialog.dismiss();

                        name.setText(getAccomp1.getName());
                        location.setText(getAccomp1.getLocation());
                        feature.setText(getAccomp1.getFeatures());
                        contact.setText(getAccomp1.getContact());
                        price.setText(getAccomp1.getPrice());












                    } else {


                            mDialog.dismiss();
                            dialogBuilder = new android.app.AlertDialog.Builder(YourAccomodation.this).create();
                            View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                            TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                            TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                            TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                            msgError.setText("We cannot get your Accomodation");
                            dialogBuilder.setView(customView);
                            dialogBuilder.show();
                            mDialog.dismiss();


                    }
                }

                @Override
                public void onFailure(Call<YourSelectAccomodation> call, Throwable t) {
                    mDialog.dismiss();
                    dialogBuilder = new android.app.AlertDialog.Builder(YourAccomodation.this).create();
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
