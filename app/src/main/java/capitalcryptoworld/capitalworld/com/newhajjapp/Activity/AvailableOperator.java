package capitalcryptoworld.capitalworld.com.newhajjapp.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import capitalcryptoworld.capitalworld.com.newhajjapp.Adapter.OperatorAdapter;
import capitalcryptoworld.capitalworld.com.newhajjapp.Controller.RestManager;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.Assign;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.AssignOperator;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.AvailableProvider;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.ComplainUser;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.MyProvider;
import capitalcryptoworld.capitalworld.com.newhajjapp.R;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailableOperator extends AppCompatActivity implements OperatorAdapter.FlowerClickListener {
    String [] paths;
    List<String> myProviderList;
    Spinner spinner;
    RestManager restManager;
    TextView descrription;
    int b;
    ProgressBar progressBar;


    private RecyclerView mRecyclerView;
    ComplainUser allAccomodation;
    Button button;
    Assign assign;
    AlertDialog dialogBuilder;


    private OperatorAdapter mAccomodationDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_available_operator);
        progressBar = (ProgressBar)findViewById(R.id.pBar);
        restManager = new RestManager();
        myProviderList = new ArrayList<>();
        button = (Button)findViewById(R.id.btn_signs);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //  Toast.makeText(AvailableOperator.this,"",Toast.LENGTH_SHORT).show();
                new UserAssign().execute();

            }
        });
        configViews();



        retrofit2.Call<AvailableProvider> call = restManager.getServices().getAllProvider();
        call.enqueue(new Callback<AvailableProvider>() {
            @Override
            public void onResponse(retrofit2.Call<AvailableProvider> call, Response<AvailableProvider> response) {

                AvailableProvider availableProvider =   response.body();
                MyProvider[] myProvider= availableProvider .getResult();
                for(int i = 0;i<myProvider.length;i++){
                    MyProvider myProvider1 = myProvider[i];
                 //   myProviderList.add(myProvider1.getName());
                    mAccomodationDetailAdapter.addFlower( myProvider1);
                    //paths[i] = myProvider1.getName();
                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(retrofit2.Call<AvailableProvider> call, Throwable t) {

            }
        });


        descrription = (TextView)findViewById(R.id.des);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
       allAccomodation = (ComplainUser) intent.getSerializableExtra("value");
        descrription.setText("The is the complain about the "+ allAccomodation.getTitle()+" the brief detail of the problem is "+ allAccomodation.getDescription()+" and the priority of the complain is "+allAccomodation.getPriority());



















    }

    private void configViews() {
        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mAccomodationDetailAdapter = new OperatorAdapter(AvailableOperator.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));

        mRecyclerView.setAdapter(mAccomodationDetailAdapter);

    }

    @Override
    public void onClick(int position) {
       // Toast.makeText(AvailableOperator.this,position+"",Toast.LENGTH_SHORT).show();

        MyProvider selectedAccomodation=mAccomodationDetailAdapter.getAccomodation(position);
        b = selectedAccomodation.getId();

        Toast.makeText(AvailableOperator.this,"you choose"+" "+selectedAccomodation.getName(),Toast.LENGTH_SHORT).show();
button.setVisibility(View.VISIBLE);

    }



    public class UserAssign extends AsyncTask<String,Integer,String>
    { ProgressDialog mDialog;


        protected  void onPreExecute()
        {
            int a = allAccomodation.getId();
             assign = new Assign(a,b);
             Log.d("ddd",a+""+b+assign+"");


            mDialog = ProgressDialog.show(AvailableOperator.this,"Please wait...", "Complaint Assign to operator ...", true);
        }
        @Override
        protected String doInBackground(String... strings) {
            retrofit2.Call<AssignOperator> call = restManager.getServices().isAssign(assign);
            call.enqueue(new Callback<AssignOperator>() {
                @Override
                public void onResponse(retrofit2.Call<AssignOperator> call, Response<AssignOperator> response) {
                    if(response.isSuccessful()) {
                        mDialog.dismiss();
                        Toast.makeText(AvailableOperator.this, "Complain assign Sucessfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AvailableOperator.this,AdminDashboard.class);
                    startActivity(intent);

                    }
                    else

                    {

                        mDialog.dismiss();
                        dialogBuilder = new AlertDialog.Builder(AvailableOperator.this).create();
                        View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                        TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                        TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                        TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                        msgError.setText("There is some problem to Assign");
                        dialogBuilder.setView(customView);
                        dialogBuilder.show();

                    }


                }

                @Override
                public void onFailure(retrofit2.Call<AssignOperator> call, Throwable t) {
                    mDialog.dismiss();
                    dialogBuilder = new AlertDialog.Builder(AvailableOperator.this).create();
                    View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                    TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                    TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                    TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                    msgError.setText("There is no internet connection");
                    dialogBuilder.setView(customView);
                    dialogBuilder.show();


                }
            });

                  /*  else

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
*/
            return null;
        }
        protected void onPostExecute(String result) {

        }

    }



    public void cancel(View view){
        dialogBuilder.dismiss();
    }














}
