package ModifiedContactApp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


public class ContactsAppController {
    @FXML
    private Button uploadButton;
    @FXML
    private ImageView imageView;
    @FXML
    private GridPane gridPane;
    @FXML
    private ListView<Contacts> contactsListView;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addButton;
    @FXML
    private Button saveButton;

    @FXML
    private TextField emailTextField;



    public final ObservableList<Contacts> contacts =
            FXCollections.observableArrayList();

    class sortByLast implements Comparator<Contacts> {
        public int compare(Contacts i1, Contacts i2) {
            return i1.getLast().compareTo(i2.getLast());
        }
    }
    public void initialize(){
        contacts.add(new Contacts("James","Harden","(917)462-7397",
                "jamesharden73@gmail.com","ModifiedContactApp/images/a03.jpg"));
        contacts.add(new Contacts("Jeniffer","Lawrence","(347)414-1917",
                "jenniferlawrence@gmail.com","ModifiedContactApp/images/a01.jpg"));
        contacts.add(new Contacts("Kanye","West","(347)863-9999",
                "kanyewest@gmail.com","ModifiedContactApp/images/kanyeWest.jpg"));
        contacts.add(new Contacts("Homer","Simpson","(347)863-9999",
                "homersimpson@gmail.com","ModifiedContactApp/images/simp1.jpg"));
        contacts.add(new Contacts("Kevin","Hart","(347)863-9999",
                "kevinhart@gmail.com","ModifiedContactApp/images/a02.jpeg"));

        sortByLast lastNameSort = new sortByLast();
        Collections.sort(contacts, lastNameSort);
        contactsListView.setItems(contacts);


        contactsListView.getSelectionModel().selectedItemProperty().
                addListener(
                        new ChangeListener<Contacts>() {
                            @Override
                            public void changed(ObservableValue<? extends Contacts> ov,
                                                Contacts oldValue, Contacts newValue) {
                                imageView.setImage(null);
                                firstNameTextField.setText(newValue.getFirst());
                                lastNameTextField.setText(newValue.getLast());
                                phoneNumberTextField.setText(newValue.getPhoneNumber());
                                emailTextField.setText(newValue.getEmail());
                                //Image image = new Image(getClass().getResource("za.jpg").toURI().toString());
                                imageView.setImage(new Image(newValue.getImagePath()));
//                                try {
//                                    imageView.setImage(new Image(getClass().getResource(newValue.getImagePath()).toURI().toString()));
//                                } catch (URISyntaxException e) {
//                                    e.printStackTrace();
//                                }
                            }
                        }
                );

        contactsListView.setCellFactory(
                new Callback<ListView<Contacts>, ListCell<Contacts>>() {
                    @Override
                    public ListCell<Contacts> call(ListView<Contacts> param) {
                        return new ImageTextCell();
                    }
                }
        );

    }
    private String pathSaver = null;

    public void onEditButtonPressed(javafx.event.ActionEvent actionEvent) {
        addButton.setDisable(false);
        saveButton.setDisable(true);
        final int selectedIdx = contactsListView.getSelectionModel().getSelectedIndex();
        if(selectedIdx != -1)
        {
            Contacts editsMade = contactsListView.getSelectionModel().getSelectedItem();
            editsMade.setFirst(firstNameTextField.getText());
            editsMade.setLast(lastNameTextField.getText());
            editsMade.setEmail(emailTextField.getText());
            editsMade.setPhoneNumber(phoneNumberTextField.getText());
            editsMade.setImagePath(pathSaver);
            contacts.remove(selectedIdx);
            contacts.add(editsMade);
            sortByLast lastNameSort = new sortByLast();
            Collections.sort(contacts, lastNameSort);
            contactsListView.setItems(contacts);
            pathSaver = null;
        }
    }

    public void onDeleteButtonPressed(javafx.event.ActionEvent actionEvent) {

        final int selectedIdx = contactsListView.getSelectionModel().getSelectedIndex();
        if(selectedIdx != -1)
        {
            Contacts itemToRemove = contactsListView.getSelectionModel().getSelectedItem();
            final int newSelectedIdx;
                    if(selectedIdx == contactsListView.getItems().size() - 1) {
                        newSelectedIdx = selectedIdx - 1;
                    }
                    else {
                       newSelectedIdx =  selectedIdx;
                    }
            contactsListView.getItems().remove(selectedIdx);
            contactsListView.getSelectionModel().select(newSelectedIdx);
            //removes the player for the array
            System.out.println("selectIdx: " + selectedIdx);
            System.out.println("item: " + itemToRemove);
        }
        addButton.setDisable(false);
        saveButton.setDisable(true);
    }
    public void onAddButtonPressed(javafx.event.ActionEvent actionEvent) {
        firstNameTextField.clear();
        lastNameTextField.clear();
        phoneNumberTextField.clear();
        emailTextField.clear();
        imageView.setImage(null);
        addButton.setDisable(true);
        saveButton.setDisable(false);
        editButton.setDisable(true);
    }

    public void onSaveButtonPressed(javafx.event.ActionEvent actionEvent){
        Contacts newContact = new Contacts(null,null,null,null,null);
        newContact.setFirst(firstNameTextField.getText());
        newContact.setLast(lastNameTextField.getText());
        newContact.setPhoneNumber(phoneNumberTextField.getText());
        newContact.setEmail(emailTextField.getText());
        newContact.setImagePath(pathSaver);
        if((newContact.getFirst().isEmpty()==false  || newContact.getLast().isEmpty() == false) &&
                (newContact.getPhoneNumber().isEmpty() == false || newContact.getEmail().isEmpty() == false)){
            contacts.add(newContact);
        }
        sortByLast lastNameSort = new sortByLast();
        Collections.sort(contacts, lastNameSort);
        addButton.setDisable(false);
        saveButton.setDisable(true);
        editButton.setDisable(false);
        pathSaver = null;
    }



    public void onUploadButtonPressed(javafx.event.ActionEvent actionEvent){
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        String path = null;

        if(selectedFile != null)
        {
             path= "file:///"+selectedFile.getPath();
             pathSaver=path;
             imageView.setImage(new Image(path));
        }
    }

}
