
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

//Class for the contacts in the contacts book
public class Contacts implements Serializable {

    private TreeMap<String, String> contacts;// contacts map (key is: name, value is: phone number)

    // constructor for the Contacts, creates a new TreeMap
    public Contacts(){
        this.contacts = new TreeMap();
    }

    // get the contacts as an arrayList of Contact object
    public ArrayList<Contact> getContactList(){
        ArrayList<Contact> contactsList = new ArrayList<Contact>();
        Iterator tableIterator = this.contacts.keySet().iterator();
        while (tableIterator.hasNext()){
            String key = (String)tableIterator.next();
            Contact contact = new Contact(key, contacts.get(key));
            contactsList.add(contact);
        }
        return contactsList;
    }

    // gets a phone number by a given name
    public String getPhoneNumber(String key) {
        return this.contacts.get(key);
    }

    // adds a contact to the contacts map, in the name is already exist than update the phone number
    public void addOrUpdateContact(String name, String phoneNumber){
        contacts.put(name, phoneNumber);
    }

    // remove a contact by a given name
    public void removeContact(String name){
        contacts.remove(name);
    }

    // returns true if contact exist in the contacts map, do it by a given name
    public boolean isContactExist(String name){
        return contacts.containsKey(name);
    }

}
