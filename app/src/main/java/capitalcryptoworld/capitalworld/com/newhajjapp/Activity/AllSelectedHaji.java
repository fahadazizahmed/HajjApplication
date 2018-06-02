package capitalcryptoworld.capitalworld.com.newhajjapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import capitalcryptoworld.capitalworld.com.newhajjapp.Adapter.HajiAdapter;
import capitalcryptoworld.capitalworld.com.newhajjapp.Controller.RestManager;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.AccomodationList;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.HajiList;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.HajiResult;
import capitalcryptoworld.capitalworld.com.newhajjapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllSelectedHaji extends AppCompatActivity implements HajiAdapter.FlowerClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    AlertDialog dialogBuilder;
    private RestManager mManager;
    private HajiAdapter mHajiAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_all_selected_haji);

        configViews();
        mManager=new RestManager();
        new GetAccomodation().execute();

    }



    public class GetAccomodation extends AsyncTask<String,Integer,String>
    {
        private ProgressDialog mDialog;


        protected  void onPreExecute()
        {

            mDialog = ProgressDialog.show(AllSelectedHaji.this,"Please wait...", "Get All Selected Haji ...", true);
        }
        @Override
        protected String doInBackground(String... strings) {
            Call<HajiList> call = mManager.getServices().getAllHaji();
            call.enqueue(new Callback<HajiList>() {
                @Override
                public void onResponse(Call<HajiList> call, Response<HajiList> response) {
                    if(response.isSuccessful()){
                        HajiList flowerlist= response.body();
                        HajiResult[] allAccomodations= flowerlist.getResult();
                        for(int i = 0;i<allAccomodations.length;i++){
                            HajiResult allAccomodation = allAccomodations[i];
                            mHajiAdapter.addFlower(allAccomodation);

                        }
                        mDialog.dismiss();

                    }
                    else{
                        mDialog.dismiss();
                        dialogBuilder = new AlertDialog.Builder(AllSelectedHaji.this).create();
                        View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                        TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                        TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                        TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                        msgError.setText("We are facing problem to get the list of haji");
                        dialogBuilder.setView(customView);
                        dialogBuilder.show();
                        mDialog.dismiss();

                    }
                }


                @Override
                public void onFailure(Call<HajiList> call, Throwable t) {
                    mDialog.dismiss();
                    dialogBuilder = new AlertDialog.Builder(AllSelectedHaji.this).create();
                    View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                    TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                    TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                    TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                    msgError.setText("We are facing problem to get the list of haji");
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
        mHajiAdapter = new HajiAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));

        mRecyclerView.setAdapter( mHajiAdapter);

    }

    @Override
    public void onClick(int position) {
        //  Toast.makeText(this,position+"",Toast.LENGTH_LONG).show();


















    }


    @Override
    public void onRefresh() {
        Toast.makeText(this,"OnRefresh",Toast.LENGTH_LONG).show();//how you hide after downloading we do in asynctask
    }
}
