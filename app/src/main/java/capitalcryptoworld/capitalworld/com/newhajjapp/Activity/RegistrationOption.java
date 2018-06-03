package capitalcryptoworld.capitalworld.com.newhajjapp.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import capitalcryptoworld.capitalworld.com.newhajjapp.R;


public class RegistrationOption extends AppCompatActivity {
LinearLayout registerHajj;
LinearLayout accomodationPackage;
    SharedPreferences spref;
    static String filename="my personal file";
    public static final String Value = "could not load data";
    TextView textView;
    LinearLayout complainCervice;
    AlertDialog dialogBuilder;
    LinearLayout linearLayout5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration_option);
        textView = (TextView)findViewById(R.id.acc_text);
        complainCervice = (LinearLayout)findViewById(R.id.btn_complain);
        registerHajj = (LinearLayout)findViewById(R.id.reg_hajj);
        linearLayout5 = (LinearLayout)findViewById(R.id.logout) ;
        linearLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getSharedPreferences(RegistrationOption.filename, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(RegistrationOption.this,Login.class);
                startActivity(intent);
            }
        });


        spref=getSharedPreferences(filename, Context.MODE_PRIVATE);
       String accid= spref.getString("Accomodation_Id","43");
        Toast.makeText(RegistrationOption.this,accid+"",Toast.LENGTH_SHORT).show();



        ////////////

        registerHajj.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Addition for Hajj Registration Check

                spref=getSharedPreferences(filename, Context.MODE_PRIVATE);
                Boolean HajjRegistrationCheck = spref.getBoolean("HajjRegistration",false);
                //int key = spref.getInt("key",10);

                if(HajjRegistrationCheck==true)
                {

                    dialogBuilder = new AlertDialog.Builder(RegistrationOption.this).create();
                    View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                    TextView msgError = (TextView) customView.findViewById(R.id.tx_msg1);
                    TextView msgError1 = (TextView) customView.findViewById(R.id.tx_msg2);
                    TextView errorDialog = (TextView) customView.findViewById(R.id.error_msg);
                    msgError.setText("You Already Register for Hajj");
                    dialogBuilder.setView(customView);
                    dialogBuilder.show();
                }
                else {
                    Intent intent = new Intent(RegistrationOption.this,RegistrationDetail.class);
                    startActivity(intent);
                }

            }
        });





        /////////////////



              accomodationPackage = (LinearLayout)findViewById(R.id.accomd_detail);
               accomodationPackage.setOnClickListener(new View.OnClickListener()
               {
                   @Override
                   public void onClick(View v)
                   {
                       spref=getSharedPreferences(filename, Context.MODE_PRIVATE);
                       //int key = spref.getInt("key",10);
                       Boolean HajjRegistrationCheck = spref.getBoolean("HajjRegistration",false);
                       if(HajjRegistrationCheck == true) {
                           Intent intent = new Intent(RegistrationOption.this, ListAccomodation.class);
                           startActivity(intent);
                       }
                       else {
                           dialogBuilder = new AlertDialog.Builder(RegistrationOption.this).create();
                           View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                           TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                           TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                           TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                           msgError.setText("Please Register for Hajj before");
                           dialogBuilder.setView(customView);
                           dialogBuilder.show();


                       }

                   }
               });



        /////////////





        complainCervice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spref=getSharedPreferences(filename, Context.MODE_PRIVATE);
                Boolean HajjRegistrationCheck = spref.getBoolean("HajjRegistration",false);
                //int key = spref.getInt("key",10);
                if(HajjRegistrationCheck==true) {
                    Intent intent = new Intent(RegistrationOption.this, ComplaintForm.class);
                    startActivity(intent);
                }
                else {
                    dialogBuilder = new AlertDialog.Builder(RegistrationOption.this).create();
                    View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                    TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                    TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                    TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                    msgError.setText("Please Register for Hajj before");
                    dialogBuilder.setView(customView);
                    dialogBuilder.show();


                }
            }
        });







































       Log.d("Ac+id",accid+"");



    }
    public void cancel(View view){
        dialogBuilder.dismiss();
    }
}
