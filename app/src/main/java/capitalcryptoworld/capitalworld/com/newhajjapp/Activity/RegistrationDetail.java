package capitalcryptoworld.capitalworld.com.newhajjapp.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;


import java.util.Calendar;
import java.util.Random;


import capitalcryptoworld.capitalworld.com.newhajjapp.Controller.RestManager;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.HajjRegisterResponse;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.RegisteHajjUser;
import capitalcryptoworld.capitalworld.com.newhajjapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationDetail extends AppCompatActivity {
    EditText province,dateOfBirth,gender,phone,address,cnicPicture,passportPicture;
    Button registerHajj;
    String userProvince,userdateOfBirht,userGender,userPhone,userAddress,ImageDecode,ImageDecodes,passPic,cnicPic;
    RestManager restManager;
    SharedPreferences spref;
    static String filename="my personal file";
    public static final String Value = "could not load data";
    RegisteHajjUser registeHajjUser;
    String  token,concateStringWithToken,authHeader;
    ImageView imageViewDate,imageViewCnic,imageViewPassport;
    DatePicker datePicker;
    Calendar calendar;
    int year, month, day;
    private static int IMG_RESULT = 1;
    private static int IMG_RESULT1 = 2;

    int compressionRatio = 2;
    Bitmap photo;
    Bitmap photos;
    byte[] imgByte;
    RadioButton radioButton1;
    RadioButton radioButton2;

    File finalFile;
    private Uri fileUri;
    String selectedGender;
    String date;
    SharedPreferences.Editor editor;
    AlertDialog dialogBuilder;
    String imagePath;
    private File imageFile;
    Intent intent;




    RadioGroup radioGroup;
    int ides;



















    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration_detail);
        configVar();
        restManager = new RestManager();
        //Select Date

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        imageViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(999);

            }
        });
        ///////////

        //Select Cnic Image
        imageViewCnic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random generator = new Random();
                int n = 10000;
                int nu = generator.nextInt(n);
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), nu + ".png");
                startActivityForResult(intent, 0);
            }
        });
// Select Passport image
        imageViewPassport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random generator = new Random();
                int n = 10000;
                int nu = generator.nextInt(n);
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), nu + ".png");
                startActivityForResult(intent, 1);
            }
        });







        ////////












        registerHajj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spref = getSharedPreferences(filename, Context.MODE_PRIVATE);//this file only aceess your app
                token = spref.getString("Token", Value);
                ides = spref.getInt("Id", 210);
                Log.d("Id is", ides + "");

                userProvince = province.getText().toString();
                userdateOfBirht = dateOfBirth.getText().toString();
                userPhone = phone.getText().toString();
                userAddress = address.getText().toString();

                passPic = imageToString();
                cnicPic = imageToString();

                    token = spref.getString("Token", Value);
                    ides = spref.getInt("Id", 21);
                    Log.d("Cnic", cnicPic);
                    Log.d("passportx", passPic);
                    Log.d("Gender", selectedGender);
                    Log.d("new Token", token);


                    Log.d("address", userAddress);
                    Log.d("userPhoneId is", userPhone + "");
                    Log.d("userPhoneId is", userProvince + "");
                   registeHajjUser = new RegisteHajjUser(ides,selectedGender,userAddress,userPhone,userProvince,cnicPic,passPic);
                    Log.d("user",registeHajjUser+"");
                new HajjUserRegister().execute();

            }
        });
    }

    void configVar() {
        province = (EditText)findViewById(R.id.et_province);
        dateOfBirth = (EditText)findViewById(R.id.et_dob);
        phone = (EditText)findViewById(R.id.et_phnNumber);
        address = (EditText)findViewById(R.id.et_address);
        cnicPicture = (EditText)findViewById(R.id.et_cnic);
        passportPicture = (EditText)findViewById(R.id.et_passPicture);
        registerHajj = (Button)findViewById(R.id.btn_registerHajj);
        imageViewDate = (ImageView)findViewById(R.id.img_date);
        imageViewCnic = (ImageView)findViewById(R.id.img_cnic);
        imageViewPassport = (ImageView)findViewById(R.id.img_passport);

        radioGroup=(RadioGroup) findViewById(R.id.radio_group);






        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rd_male){
                    selectedGender = "Male";
                    Toast.makeText(RegistrationDetail.this,"male",Toast.LENGTH_SHORT).show();
                }
                else if(checkedId == R.id.rd_female){
                   selectedGender = "Female";
                }
            }
        });


















    }




    //////////

    public class HajjUserRegister extends AsyncTask<String,Integer,String>
    {


        private ProgressDialog mDialog;
        final SharedPreferences.Editor editor = spref.edit();






        protected  void onPreExecute()
        {

            concateStringWithToken = "Bearer";
            authHeader = concateStringWithToken+" "+token;
            Log.d("AuthHeader",authHeader);

            mDialog = ProgressDialog.show(RegistrationDetail.this,"Please wait...", "Registration is in process ...", true);

        }
        @Override
        protected String doInBackground(String... strings) {

            Call<HajjRegisterResponse> call = restManager.getServices().registerHajjUser(authHeader,registeHajjUser);
            call.enqueue(new Callback<HajjRegisterResponse>() {
                @Override
                public void onResponse(Call<HajjRegisterResponse> call, Response<HajjRegisterResponse> response) {
                    if(response.isSuccessful()){
                       // HajjRegisterResponse hajjRegisterResponse = response.body();
                       // Log.d("sucess respnse",hajjRegisterResponse.isSuccess()+"");
                       Toast.makeText(RegistrationDetail.this,"You register successfully for hajj draw",Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                        editor.putInt("key",5);
                        editor.commit();

                       Intent intent = new Intent(RegistrationDetail.this,RegistrationOption.class);
                       startActivity(intent);

                    }


                    else{

                        dialogBuilder = new AlertDialog.Builder(RegistrationDetail.this).create();
                        View customView = getLayoutInflater().inflate(R.layout.activity_password_change_dialogue, null);
                        TextView msgError  = (TextView) customView.findViewById(R.id.tx_msg1);
                        TextView msgError1  = (TextView) customView.findViewById(R.id.tx_msg2);
                        TextView errorDialog = (TextView)customView.findViewById(R.id.error_msg);

                        msgError.setText("User Alreday Exist");

                        dialogBuilder.setView(customView);
                        dialogBuilder.show();

                        mDialog.dismiss();
                    }



                }

                @Override
                public void onFailure(Call<HajjRegisterResponse> call, Throwable t) {
                    Log.d("Internet response",t.getMessage());
                    dialogBuilder = new AlertDialog.Builder(RegistrationDetail.this).create();
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






    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        date = day+"/"+month+"/"+year;
        Log.d("todya date",date);

        dateOfBirth.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));




    }



    ///////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");


            Uri tempUri = getImageUri(RegistrationDetail.this, photo);


            finalFile = new File(getRealPathFromURI(tempUri));
            cnicPicture.setText(finalFile+"");



        }
       else if (requestCode == 1 && resultCode == RESULT_OK) {
            photos = (Bitmap) data.getExtras().get("data");
            Log.d("lassi",photos +"" );


            Uri tempUri = getImageUri(RegistrationDetail.this, photos);
            Log.d("lassi1",tempUri +"" );


            finalFile = new File(getRealPathFromURI(tempUri));
            passportPicture.setText(finalFile+"");
            Log.d("lassi2",finalFile+"" );



        }
    }

















/////////////

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, compressionRatio, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);

        return Uri.parse(path);
    }



    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        Log.d("lassi3",idx+"" );
        return cursor.getString(idx);
    }

    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        imgByte = byteArrayOutputStream.toByteArray();//this is the real image you
        Log.d("im",imgByte+"");
        return Base64.encodeToString(imgByte,Base64.DEFAULT);

    }






    public void cancel(View view){
        dialogBuilder.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegistrationDetail.this,Registration.class);
        startActivity(intent);
    }
}
