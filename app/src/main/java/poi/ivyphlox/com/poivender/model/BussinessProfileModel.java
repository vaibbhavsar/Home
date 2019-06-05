package poi.ivyphlox.com.poivender.model;

import java.util.List;

public class BussinessProfileModel
{
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
}