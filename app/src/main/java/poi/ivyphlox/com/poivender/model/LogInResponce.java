package poi.ivyphlox.com.poivender.model;

/**
 * Created by vaibhavbhavsar on 10/03/19.
 */

public class LogInResponce {

    private String image;

    private String latitude;

    private String name;

    private String is_POI;

    private String active;

    private String _id;

    private String mobile_number;

    private String longitude;

    public String getAddressTitle() {
        return addressTitle;
    }

    public void setAddressTitle(String addressTitle) {
        this.addressTitle = addressTitle;
    }

    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }

    private String addressTitle;
    private String addressDetails;

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getLatitude ()
    {
        return latitude;
    }

    public void setLatitude (String latitude)
    {
        this.latitude = latitude;
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

    public String getActive ()
    {
        return active;
    }

    public void setActive (String active)
    {
        this.active = active;
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
        return "ClassPojo [image = "+image+", latitude = "+latitude+", name = "+name+", is_POI = "+is_POI+", active = "+active+", _id = "+_id+", mobile_number = "+mobile_number+", longitude = "+longitude+"]";
    }

}
