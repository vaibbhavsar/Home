package poi.ivyphlox.com.poivender.model;

import android.net.Uri;

public class ImageModelBussiness
{
    private String base64;

    private Uri uri;

    private String url;

    public String getBase64 ()
    {
        return base64;
    }

    public void setBase64 (String base64)
    {
        this.base64 = base64;
    }

    public Uri getUri ()
    {
        return uri;
    }

    public void setUri (Uri uri)
    {
        this.uri = uri;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [base64 = "+base64+", uri = "+uri+", url = "+url+"]";
    }
}

			