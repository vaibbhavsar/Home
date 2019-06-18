package poi.ivyphlox.com.poivender.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class BussinessProfileModel implements Parcelable
{
    protected BussinessProfileModel(Parcel in) {
        mobile_number = in.readString();
        website = in.readString();
        detailsAddress = in.readString();
        bussinessCat = in.readString();
        bussinessName = in.readString();
        lon = in.readString();
        bussinessMobile = in.readString();
        City = in.readString();
        profileImage = in.readString();
        closedTime = in.readString();
        ImageList = in.createStringArrayList();
        Area = in.readString();
        openTime = in.readString();
        lat = in.readString();
    }

    public BussinessProfileModel(){

    }

    public static final Creator<BussinessProfileModel> CREATOR = new Creator<BussinessProfileModel>() {
        @Override
        public BussinessProfileModel createFromParcel(Parcel in) {
            return new BussinessProfileModel(in);
        }

        @Override
        public BussinessProfileModel[] newArray(int size) {
            return new BussinessProfileModel[size];
        }
    };

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    private String mobile_number;
    private String website;

    private String detailsAddress;

    private String bussinessCat;

    private String bussinessName;

    private String lon;

    private String bussinessMobile;

    private String City;

    private String profileImage;

    private String closedTime;

    private List<String> ImageList;

    private String Area;

    private String openTime;

    private String lat;

    public String getWebsite ()
    {
        return website;
    }

    public void setWebsite (String website)
    {
        this.website = website;
    }

    public String getDetailsAddress ()
    {
        return detailsAddress;
    }

    public void setDetailsAddress (String detailsAddress)
    {
        this.detailsAddress = detailsAddress;
    }

    public String getBussinessCat ()
    {
        return bussinessCat;
    }

    public void setBussinessCat (String bussinessCat)
    {
        this.bussinessCat = bussinessCat;
    }

    public String getBussinessName ()
    {
        return bussinessName;
    }

    public void setBussinessName (String bussinessName)
    {
        this.bussinessName = bussinessName;
    }

    public String getLon ()
    {
        return lon;
    }

    public void setLon (String lon)
    {
        this.lon = lon;
    }

    public String getBussinessMobile ()
    {
        return bussinessMobile;
    }

    public void setBussinessMobile (String bussinessMobile)
    {
        this.bussinessMobile = bussinessMobile;
    }

    public String getCity ()
    {
        return City;
    }

    public void setCity (String City)
    {
        this.City = City;
    }

    public String getProfileImage ()
    {
        return profileImage;
    }

    public void setProfileImage (String profileImage)
    {
        this.profileImage = profileImage;
    }

    public String getClosedTime ()
    {
        return closedTime;
    }

    public void setClosedTime (String closedTime)
    {
        this.closedTime = closedTime;
    }

    public List<String> getImageList ()
    {
        return ImageList;
    }

    public void setImageList (List<String> ImageList)
    {
        this.ImageList = ImageList;
    }

    public String getArea ()
    {
        return Area;
    }

    public void setArea (String Area)
    {
        this.Area = Area;
    }

    public String getOpenTime ()
    {
        return openTime;
    }

    public void setOpenTime (String openTime)
    {
        this.openTime = openTime;
    }

    public String getLat ()
    {
        return lat;
    }

    public void setLat (String lat)
    {
        this.lat = lat;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [website = "+website+"," +
                " detailsAddress = "+detailsAddress+"," +
                " bussinessCat = "+bussinessCat+"," +
                " bussinessName = "+bussinessName+"," +
                " lon = "+lon+"," +
                " bussinessMobile = "+bussinessMobile+"," +
                " City = "+City+"," +
                " profileImage = "+profileImage+"," +
                " closedTime = "+closedTime+"," +
                " ImageList = "+ImageList+"," +
                " Area = "+Area+"," +
                " openTime = "+openTime+"," +
                " lat = "+lat+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mobile_number);
        parcel.writeString(website);
        parcel.writeString(detailsAddress);
        parcel.writeString(bussinessCat);
        parcel.writeString(bussinessName);
        parcel.writeString(lon);
        parcel.writeString(bussinessMobile);
        parcel.writeString(City);
        parcel.writeString(profileImage);
        parcel.writeString(closedTime);
        parcel.writeStringList(ImageList);
        parcel.writeString(Area);
        parcel.writeString(openTime);
        parcel.writeString(lat);
    }
}