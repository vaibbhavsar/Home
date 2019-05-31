package poi.ivyphlox.com.poivender.model;

public class Mobiles {
    private String other;

    private String office;

    private String home;

    public String getOther ()
    {
        return other;
    }

    public void setOther (String other)
    {
        this.other = other;
    }

    public String getOffice ()
    {
        return office;
    }

    public void setOffice (String office)
    {
        this.office = office;
    }

    public String getHome ()
    {
        return home;
    }

    public void setHome (String home)
    {
        this.home = home;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [other = "+other+", office = "+office+", home = "+home+"]";
    }
}

			