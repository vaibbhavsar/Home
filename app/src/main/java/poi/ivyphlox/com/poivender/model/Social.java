package poi.ivyphlox.com.poivender.model;

public class Social
{
    private String twitter;

    private String facebook;

    private String linkedin;

    private String instagram;

    public String getTwitter ()
    {
        return twitter;
    }

    public void setTwitter (String twitter)
    {
        this.twitter = twitter;
    }

    public String getFacebook ()
    {
        return facebook;
    }

    public void setFacebook (String facebook)
    {
        this.facebook = facebook;
    }

    public String getLinkedin ()
    {
        return linkedin;
    }

    public void setLinkedin (String linkedin)
    {
        this.linkedin = linkedin;
    }

    public String getInstagram ()
    {
        return instagram;
    }

    public void setInstagram (String instagram)
    {
        this.instagram = instagram;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [twitter = "+twitter+", facebook = "+facebook+", linkedin = "+linkedin+", instagram = "+instagram+"]";
    }
}