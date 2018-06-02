package capitalcryptoworld.capitalworld.com.newhajjapp.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import capitalcryptoworld.capitalworld.com.newhajjapp.Adapter.AccomodationDetailAdapter;
import capitalcryptoworld.capitalworld.com.newhajjapp.Controller.RestManager;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.AccomodationList;

import capitalcryptoworld.capitalworld.com.newhajjapp.Model.AllAccomodation;
import capitalcryptoworld.capitalworld.com.newhajjapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAccomodation extends AppCompatActivity implements AccomodationDetailAdapter.FlowerClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    AlertDialog dialogBuilder;
    private RestManager mManager;
    private AccomodationDetailAdapter mAccomodationDetailAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    int [] a = {R.drawable.na,R.drawable.nb,R.drawable.qa,R.drawable.qb,R.drawable.p1,R.drawable.p2,R.drawable.pa,R.drawable.r};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        configViews();
        mManager=new RestManager();
        new GetAccomodation().execute();

    }



    public class GetAccomodation extends AsyncTask<String,Integer,String>
    {
        private ProgressDialog mDialog;


        protected  void onPreExecute()
        {

            mDialog = ProgressDialog.show(ListAccomodation.this,"Please wait...", "Get All Accomodation ...", true);
        }
        @Override
        protected String doInBackground(String... strings) {
            Call<AccomodationList> call = mManager.getServices().getAllaccomodation();
            call.enqueue(new Callback<AccomodationList>() {
                @Override
                public void onResponse(Call<AccomodationList> call, Response<AccomodationList> response) {
                    if(response.isSuccessful()){
                        AccomodationList flowerlist= response.body();
                        AllAccomodation[] allAccomodations= flowerlist.getResult();
                        for(int i = 0;i<allAccomodations.length;i++){
                            AllAccomodation allAccomodation = allAccomodations[i];
                            mAccomodationDetailAdapter.addFlower(allAccomodation);

                        }
                        mDialog.dismiss();

                    }
                    else{
                        mDialog.dismiss();
                        dialogBuilder = new AlertDialog.Builder(ListAccomodation.this).create();
                        View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                        TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                        TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                        TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                        msgError.setText("We cannot get list of Accomodation");
                        dialogBuilder.setView(customView);
                        dialogBuilder.show();
                        mDialog.dismiss();

                    }
                }


                @Override
                public void onFailure(Call<AccomodationList> call, Throwable t) {
                    mDialog.dismiss();
                    dialogBuilder = new AlertDialog.Builder(ListAccomodation.this).create();
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






















    private void configViews() {
        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mAccomodationDetailAdapter = new AccomodationDetailAdapter(this,a);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));

        mRecyclerView.setAdapter( mAccomodationDetailAdapter);

    }

    @Override
    public void onClick(int position) {
      //  Toast.makeText(this,position+"",Toast.LENGTH_LONG).show();
         AllAccomodation selectedAccomodation=mAccomodationDetailAdapter.getAccomodation(position);
         Intent intent = new Intent(ListAccomodation.this,DetailAccomodationActivity.class);
         Bundle bundle = new Bundle();
          bundle.putSerializable("value",selectedAccomodation);
       bundle.putInt("fer",position);




          intent.putExtras(bundle);


          //



        startActivity(intent);




















    }


    @Override
    public void onRefresh() {
        Toast.makeText(this,"OnRefresh",Toast.LENGTH_LONG).show();//how you hide after downloading we do in asynctask
    }
}
