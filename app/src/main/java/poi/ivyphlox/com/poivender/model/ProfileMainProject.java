package poi.ivyphlox.com.poivender.model;

/**
 * Created by vaibhavbhavsar on 18/04/19.
 */

public class ProfileMainProject {
    private String whatsapp;

    private String image;

    private String addressTitle;

    private Social social;

    private String latitude;

    private String wechat;

    private String active;

    private String otp;

    private String addressDetails;

    private String DOB;

    private Mobiles mobiles;

    private String name;

    private String is_POI;

    private String _id;

    private String mobile_number;

    private String email;

    private String longitude;

    public String getWhatsapp ()
    {
        return whatsapp;
    }

    public void setWhatsapp (String whatsapp)
    {
        this.whatsapp = whatsapp;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getAddressTitle ()
    {
        return addressTitle;
    }

    public void setAddressTitle (String addressTitle)
    {
        this.addressTitle = addressTitle;
    }

    public Social getSocial ()
    {
        return social;
    }

    public void setSocial (Social social)
    {
        this.social = social;
    }

    public String getLatitude ()
    {
        return latitude;
    }

    public void setLatitude (String latitude)
    {
        this.latitude = latitude;
    }

    public String getWechat ()
    {
        return wechat;
    }

    public void setWechat (String wechat)
    {
        this.wechat = wechat;
    }

    public String getActive ()
    {
        return active;
    }

    public void setActive (String active)
    {
        this.active = active;
    }

    public String getOtp ()
    {
        return otp;
    }

    public void setOtp (String otp)
    {
        this.otp = otp;
    }

    public String getAddressDetails ()
    {
        return addressDetails;
    }

    public void setAddressDetails (String addressDetails)
    {
        this.addressDetails = addressDetails;
    }

    public String getDOB ()
    {
        return DOB;
    }

    public void setDOB (String DOB)
    {
        this.DOB = DOB;
    }

    public Mobiles getMobiles ()
    {
        return mobiles;
    }

    public void setMobiles (Mobiles mobiles)
    {
        this.mobiles = mobiles;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getIs_POI ()
    {
        return is_POI;
    }

    public void setIs_POI (String is_POI)
    {
        this.is_POI = is_POI;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getMobile_number ()
    {
        return mobile_number;
    }

    public void setMobile_number (String mobile_number)
    {
        this.mobile_number = mobile_number;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getLongitude ()
    {
        return longitude;
    }

    public void setLongitude (String longitude)
    {
        this.longitude = longitude;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [whatsapp = "+whatsapp+", image = "+image+", addressTitle = "+addressTitle+", social = "+social+", latitude = "+latitude+", wechat = "+wechat+", active = "+active+", otp = "+otp+", addressDetails = "+addressDetails+", DOB = "+DOB+", mobiles = "+mobiles+", name = "+name+", is_POI = "+is_POI+", _id = "+_id+", mobile_number = "+mobile_number+", email = "+email+", longitude = "+longitude+"]";
    }
}
