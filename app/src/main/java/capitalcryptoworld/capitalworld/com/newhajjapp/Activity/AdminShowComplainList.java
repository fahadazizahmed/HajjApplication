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

import capitalcryptoworld.capitalworld.com.newhajjapp.Adapter.AdapterAdminShowComplain;
import capitalcryptoworld.capitalworld.com.newhajjapp.Controller.RestManager;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.ComplainUser;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.UnAssignedComplain;
import capitalcryptoworld.capitalworld.com.newhajjapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminShowComplainList extends AppCompatActivity  implements AdapterAdminShowComplain.FlowerClickListener, SwipeRefreshLayout.OnRefreshListener {

private RecyclerView mRecyclerView;
        AlertDialog dialogBuilder;
private RestManager mManager;
private AdapterAdminShowComplain mAccomodationDetailAdapter;
private SwipeRefreshLayout swipeRefreshLayout;

@Override
protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_show_complain_list);

        configViews();
        mManager=new RestManager();
        new GetComplain().execute();

        }



public class GetComplain extends AsyncTask<String,Integer,String>
{
    private ProgressDialog mDialog;


    protected  void onPreExecute()
    {

        mDialog = ProgressDialog.show(AdminShowComplainList.this,"Please wait...", "Get All Complain ...", true);
    }
    @Override
    protected String doInBackground(String... strings) {
        Call<UnAssignedComplain> call = mManager.getServices().getCompalinList();
        call.enqueue(new Callback<UnAssignedComplain>() {
            @Override
            public void onResponse(Call<UnAssignedComplain> call, Response<UnAssignedComplain> response) {
                if(response.isSuccessful()){
                    UnAssignedComplain flowerlist= response.body();
                    ComplainUser[] ComplainUsers= flowerlist.getResult();
                    for(int i = 0;i<ComplainUsers.length;i++){
                        ComplainUser ComplainUser = ComplainUsers[i];
                        mAccomodationDetailAdapter.addFlower(ComplainUser);

                    }
                    mDialog.dismiss();

                }
                else{
                    mDialog.dismiss();
                    dialogBuilder = new AlertDialog.Builder(AdminShowComplainList.this).create();
                    View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                    TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                    TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                    TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                    msgError.setText("Sorry! We cannot get list of Complain");
                    dialogBuilder.setView(customView);
                    dialogBuilder.show();
                    mDialog.dismiss();

                }
            }


            @Override
            public void onFailure(Call<UnAssignedComplain> call, Throwable t) {
                mDialog.dismiss();
                dialogBuilder = new AlertDialog.Builder(AdminShowComplainList.this).create();
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
        mAccomodationDetailAdapter = new AdapterAdminShowComplain(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));

        mRecyclerView.setAdapter(mAccomodationDetailAdapter);

    }

    @Override
    public void onClick(int position) {
        //  Toast.makeText(this,position+"",Toast.LENGTH_LONG).show();
        ComplainUser selectedAccomodation=mAccomodationDetailAdapter.getAccomodation(position);
        Intent intent = new Intent(AdminShowComplainList.this,AvailableOperator.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("value",selectedAccomodation);
        intent.putExtras(bundle);
        startActivity(intent);




















    }


    @Override
    public void onRefresh() {
        Toast.makeText(this,"OnRefresh",Toast.LENGTH_LONG).show();//how you hide after downloading we do in asynctask
    }
}

