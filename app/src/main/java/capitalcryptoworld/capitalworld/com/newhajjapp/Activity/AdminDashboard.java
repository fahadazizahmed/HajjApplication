package capitalcryptoworld.capitalworld.com.newhajjapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import capitalcryptoworld.capitalworld.com.newhajjapp.R;


public class AdminDashboard extends AppCompatActivity {
    LinearLayout linearLayout;
    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    LinearLayout linearLayout3;
    LinearLayout linearLayout4;
    SharedPreferences spref;
    static String filename="my personal file";
    public static final String Value = "could not load data";
    LinearLayout linearLayout5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_dashboard);
        linearLayout = (LinearLayout)findViewById(R.id.c_ope);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this,AddOperator.class);
                startActivity(intent);
            }
        });

        linearLayout1 = (LinearLayout)findViewById(R.id.r_com);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this,AdminShowComplainList.class);
                startActivity(intent);
            }
        });
        linearLayout2 = (LinearLayout)findViewById(R.id.a_op);
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this,AvailableOperator.class);
                startActivity(intent);
            }
        });
        linearLayout3 = (LinearLayout)findViewById(R.id.s_user);
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this,AllSelectedHaji.class);
                startActivity(intent);
            }
        });
        linearLayout4 = (LinearLayout)findViewById(R.id.draw);
        linearLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this,AdminSelectPeople.class);
                startActivity(intent);
            }
        });
        linearLayout5 = (LinearLayout)findViewById(R.id.logout);
        linearLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getSharedPreferences(AdminDashboard.filename, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(AdminDashboard.this,Login.class);
                startActivity(intent);
            }
        });
    }
}
