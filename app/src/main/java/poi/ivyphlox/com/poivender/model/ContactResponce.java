package poi.ivyphlox.com.poivender.model;

public class ContactResponce
{
    private String name;

    private String is_POI;

    private String id;

    private String mobile_number;

    private String isPOI;

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

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getMobile_number ()
    {
        return mobile_number;
    }

    public void setMobile_number (String mobile_number)
    {
        this.mobile_number = mobile_number;
    }

    public String getIsPOI ()
    {
        return isPOI;
    }

    public void setIsPOI (String isPOI)
    {
        this.isPOI = isPOI;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", is_POI = "+is_POI+", id = "+id+", mobile_number = "+mobile_number+", isPOI = "+isPOI+"]";
    }
}