
// a class of single contact
public class Contact {
    private String name;
    private  String phoneNumber;

    // constructor for the the Contact
    public Contact(String name, String phoneNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    //get the name of the contact
    public String getName() {
        return name;
    }

    // get the phone number of the contact
    public String getPhoneNumber() {
        return phoneNumber;
    }

    //set a new phone number to the contact
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
