package capitalcryptoworld.capitalworld.com.newhajjapp.Model;

import java.util.Date;

/**
 * Created by Fahad Aziz on 19/05/2018.
 */

public class RegisteHajjUser {





    public RegisteHajjUser(int id,String gender, String address, String phone, String province, String cnicPic, String passportPic) {
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.province = province;
        this.cnicPic = cnicPic;
        this.passportPic = passportPic;
        this.id = id;
        this.dob = dob;
    }

            String  gender;
            String address;
            String phone;
            Date dob;
            String province;
            String cnicPic;
            String passportPic;
            int id;


}

