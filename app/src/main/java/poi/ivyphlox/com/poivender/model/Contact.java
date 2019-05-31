package poi.ivyphlox.com.poivender.model;

public class Contact {

    int id;
    String name;
    String phone_number;
    boolean isSelected;
    String email;
    String status;

    public Contact() {
    }

    public Contact(int id, String name, String _phone_number, String email, String _status) {
        this.id = id;
        this.name = name;
        this.phone_number = _phone_number;
        this.email = email;
        this.status = _status;
    }

    public Contact(String name, String _phone_number, String _status) {
        this.name = name;
        this.phone_number = _phone_number;
        this.status = _status;
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return this.phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public String get_status() {
        return status;
    }

    public void set_status(String _status) {
        this.status = _status;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}  