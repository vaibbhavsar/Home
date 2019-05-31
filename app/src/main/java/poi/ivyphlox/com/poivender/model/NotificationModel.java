package poi.ivyphlox.com.poivender.model;

public class NotificationModel
{
    private String receipient_mobile;

    private String _id;

    private String sender_mobile;

    private String sharable_contact;

    public String getReceipient_mobile ()
    {
        return receipient_mobile;
    }

    public void setReceipient_mobile (String receipient_mobile)
    {
        this.receipient_mobile = receipient_mobile;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getSender_mobile ()
    {
        return sender_mobile;
    }

    public void setSender_mobile (String sender_mobile)
    {
        this.sender_mobile = sender_mobile;
    }

    public String getSharable_contact ()
    {
        return sharable_contact;
    }

    public void setSharable_contact (String sharable_contact)
    {
        this.sharable_contact = sharable_contact;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [receipient_mobile = "+receipient_mobile+", _id = "+_id+", sender_mobile = "+sender_mobile+", sharable_contact = "+sharable_contact+"]";
    }
}

			