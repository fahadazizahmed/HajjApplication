package capitalcryptoworld.capitalworld.com.newhajjapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

                retrofit2.Call<HajiSelect> call = restManager.getServices().isHajiSelect(a);
                call.enqueue(new Callback<HajiSelect>() {
                    @Override
                    public void onResponse(retrofit2.Call<HajiSelect> call, Response<HajiSelect> response) {

                        if(response.isSuccessful()){
                            Log.d("Responsexxx",response.body().isSuccess()+"");
                            Toast.makeText(AdminSelectPeople.this,"List of people select for hajj",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdminSelectPeople.this,AllSelectedHaji.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(AdminSelectPeople.this,"Draw is already done",Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFailure(retrofit2.Call<HajiSelect> call, Throwable t) {
                        Toast.makeText(AdminSelectPeople.this,"No internet connection",Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });
    }
}
