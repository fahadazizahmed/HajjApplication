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
    TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration_option);
        textView = (TextView)findViewById(R.id.acc_text);
        textView1 = (TextView)findViewById(R.id.tx_com);

        spref=getSharedPreferences(filename, Context.MODE_PRIVATE);
        int acc = spref.getInt("accomodation",21);
        int comp =  spref.getInt("complain",34);

        if(acc == 0){
            textView.setText("Select Accomodation  package");
        }
        else {
            textView.setText("See the detail of your package");
        }
        if(comp == 0){
            textView1.setText("Register your Complain");
        }
        else {
            textView1.setText("Check your register complain");
        }












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






        ////////////

        registerHajj.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                spref=getSharedPreferences(filename, Context.MODE_PRIVATE);
                boolean register = spref.getBoolean("register",false);

                if(register == true)
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
                       boolean register = spref.getBoolean("register",false);
                       int acc = spref.getInt("accomodation",21);
                       //if(register == true) {
                       //    Intent intent = new Intent(RegistrationOption.this, ListAccomodation.class);
                       //    startActivity(intent);
                      // }
                     if (register == false){
                           dialogBuilder = new AlertDialog.Builder(RegistrationOption.this).create();
                           View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                           TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                           TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                           TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                           msgError.setText("Please Register for Hajj before");
                           dialogBuilder.setView(customView);
                           dialogBuilder.show();

                       }
                      else if(register == true && acc == 0){
                         Intent intent = new Intent(RegistrationOption.this, ListAccomodation.class);
                         startActivity(intent);
                       }


                       else {


                         Intent intent = new Intent(RegistrationOption.this, YourAccomodation.class);
                         startActivity(intent);















                       }

                   }
               });



        /////////////





        complainCervice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spref=getSharedPreferences(filename, Context.MODE_PRIVATE);
                boolean register = spref.getBoolean("register",false);
               int comp =  spref.getInt("complain",34);
                if(register == false) {


                    dialogBuilder = new AlertDialog.Builder(RegistrationOption.this).create();
                    View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                    TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                    TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                    TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);
                    msgError.setText("Please Register for Hajj before");
                    dialogBuilder.setView(customView);
                    dialogBuilder.show();
                }


                else if(register == true && comp == 0){
                    Intent intent = new Intent(RegistrationOption.this, ComplaintForm.class);
                    startActivity(intent);
                }


                else {


                    Intent intent = new Intent(RegistrationOption.this, RegisterComplain.class);
                    startActivity(intent);

                }








            }
        });











































    }
    public void cancel(View view){
        dialogBuilder.dismiss();
    }
}
