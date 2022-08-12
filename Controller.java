import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.*;
import java.io.*;

// the Controller class for the Phone Book app
public class Controller {

    private Contacts contacts;
    private ObservableList observableList;

    @FXML
    private TableView<?> contentTable;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> phoneCol;

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private TextField searchBar;

    public void initialize() {
        this.contacts = new Contacts();
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        displayTable();
    }

    // display the table with all the contacts in the user interface
    private void displayTable(){
        this.observableList = FXCollections.observableArrayList(contacts.getContactList());
        this.contentTable.setItems(this.observableList);
    }

    // add new contact or update the phone number if the name already exist
    @FXML
    void addOrUpdateContact(ActionEvent event) {
        String phoneNumber = this.phone.getText();
        String contactName = this.name.getText();
        if (this.isValidPhoneNumber(phoneNumber) && this.isValidName(contactName)){
            this.contacts.addOrUpdateContact(contactName, phoneNumber);
            displayTable();
        }
        else {
            JOptionPane.showMessageDialog(null,"Empty or invalid phone number or name\n" +
                    "Phone numbers: should be at length of 10 and contain only numbers\n" +
                    "Names: should only be alphabetic and spaces");
        }
        this.phone.setText("");
        this.name.setText("");
    }

    // returns true if the name contains only alphabetic and spaces
    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z ]*");
    }

    // returns true if the phone number contains only number and in length of 10
    private boolean isValidPhoneNumber(String phoneNumber){
        return phoneNumber.matches("^[0-9]{10}");
    }


    //Load a Phone Book from the file at the same dir, if couldn't - massage the user
    @FXML
    void loadPhoneBook(ActionEvent event) {
        try {
            FileInputStream fileInputStream = new FileInputStream("phoneBookFile.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            this.contacts = (Contacts) objectInputStream.readObject();
            displayTable();
        }catch (IOException e){
            JOptionPane.showMessageDialog(null,"The file could be opened.\n" +
                    "The file may be missing or damaged");
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            JOptionPane.showMessageDialog(null,"The file could be opened for some reason");
            e.printStackTrace();
        }

    }

    // Save a Phone Book to a file at the same dir. For future loading - if couldn't - massage the user
    @FXML
    void savePhoneBook(ActionEvent event) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("phoneBookFile.dat");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.contacts);
            fileOutputStream.close();
            objectOutputStream.close();
            JOptionPane.showMessageDialog(null,"The file saved successfully");
        }catch (IOException e){
            JOptionPane.showMessageDialog(null,"The file could not be saved");
            e.printStackTrace();
        }
    }
    // remove a contact from contacts and also from the display
    @FXML
    void removeContact(ActionEvent event) {
        Contact contact = (Contact) this.contentTable.getSelectionModel().getSelectedItem();
        this.contacts.removeContact(contact.getName());
        displayTable();
    }

    // search a Phone Number By given Name
    @FXML
    void searchPhoneNumberByName(ActionEvent event) {
        String name = this.searchBar.getText();
        if (!isValidName(name)){
            JOptionPane.showMessageDialog(null,"Invalid name, only alphabetic are allowed");
            this.searchBar.setText("");
            return;
        }
        if (contacts.isContactExist(name)){
            String phoneNumber = contacts.getPhoneNumber(name);
            Contact contact = new Contact(name, phoneNumber);
            this.observableList = FXCollections.observableArrayList(contact);
            this.contentTable.setItems(this.observableList);

        }else {
            this.contentTable.setItems(null);
        }
    }

    // clear the search and display all contacts again
    @FXML
    void clearSearch(ActionEvent event) {
        displayTable();
    }

}
