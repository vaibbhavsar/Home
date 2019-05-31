package poi.ivyphlox.com.poivender.model;

public class ContactModelProfilePOJO
{
    private String code;

    private String contact;

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public String getContact ()
    {
        return contact;
    }

    public void setContact (String contact)
    {
        this.contact = contact;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [code = "+code+", contact = "+contact+"]";
    }
}
