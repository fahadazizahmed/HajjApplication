package capitalcryptoworld.capitalworld.com.newhajjapp.Model;

/**
 * Created by Fahad Aziz on 29/05/2018.
 */

public class HajiResult {
    private String creatorUserId;

    private String phone;

    private String passportPic;

    private String address;

    private String cnicPic;

    private String dob;

    private String province;

    private String gender;

    private String isDeleted;

    public String getCreatorUserId ()
    {
        return creatorUserId;
    }

    public void setCreatorUserId (String creatorUserId)
    {
        this.creatorUserId = creatorUserId;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getPassportPic ()
    {
        return passportPic;
    }

    public void setPassportPic (String passportPic)
    {
        this.passportPic = passportPic;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getCnicPic ()
    {
        return cnicPic;
    }

    public void setCnicPic (String cnicPic)
    {
        this.cnicPic = cnicPic;
    }

    public String getDob ()
    {
        return dob;
    }

    public void setDob (String dob)
    {
        this.dob = dob;
    }

    public String getProvince ()
    {
        return province;
    }

    public void setProvince (String province)
    {
        this.province = province;
    }

    public String getGender ()
    {
        return gender;
    }

    public void setGender (String gender)
    {
        this.gender = gender;
    }

    public String getIsDeleted ()
    {
        return isDeleted;
    }

    public void setIsDeleted (String isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [creatorUserId = "+creatorUserId+", phone = "+phone+", passportPic = "+passportPic+", address = "+address+", cnicPic = "+cnicPic+", dob = "+dob+", province = "+province+", gender = "+gender+", isDeleted = "+isDeleted+"]";
    }
}
