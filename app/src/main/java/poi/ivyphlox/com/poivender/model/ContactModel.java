package poi.ivyphlox.com.poivender.model;

/**
 * Created by vaibhavbhavsar on 08/03/19.
 */

public class ContactModel {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String name;
    private String phone;
    private String email;

    @Override
    public String toString() {
        System.out.print("name :"+name+"phone"+phone);
        return super.toString();

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
