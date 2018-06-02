package capitalcryptoworld.capitalworld.com.newhajjapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import capitalcryptoworld.capitalworld.com.newhajjapp.Controller.RestManager;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.AvailableProvider;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.MyProvider;
import capitalcryptoworld.capitalworld.com.newhajjapp.R;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailableOperator extends AppCompatActivity {
    String [] paths={"Normal","Medium","High"};
    List<String> myProviderList;
    Spinner spinner;
    RestManager restManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_available_operator);
        restManager = new RestManager();
        myProviderList = new ArrayList<>();


        retrofit2.Call<AvailableProvider> call = restManager.getServices().getAllProvider();
        call.enqueue(new Callback<AvailableProvider>() {
            @Override
            public void onResponse(retrofit2.Call<AvailableProvider> call, Response<AvailableProvider> response) {

                AvailableProvider availableProvider =   response.body();
                MyProvider[] myProvider= availableProvider .getResult();
                for(int i = 0;i<myProvider.length;i++){
                    MyProvider myProvider1 = myProvider[i];
                    myProviderList.add(myProvider1.getName());
                }

            }

            @Override
            public void onFailure(retrofit2.Call<AvailableProvider> call, Throwable t) {

            }
        });


        ArrayAdapter<String> adapter= new ArrayAdapter<String>(AvailableOperator.this,android.R.layout.simple_spinner_dropdown_item,myProviderList);
        spinner =(Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(adapter);



















    }
}
