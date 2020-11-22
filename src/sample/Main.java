package sample;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application {

    private TableView table = new TableView();
    private ContactManager cm = new ContactManager(10);
    public static void main(String[] args) {
        launch(args);
    }

    public void init() {
        cm.add("Simon", "Bermudez","123","321",new Address("123 Street", "none", "Toronto", "M4J3E1", "Ontario", "Canada"), "simon@simon.com", "Notes Here", new MyDate(24,07,1997));
        cm.add("Fatih", "Camgoz","123","321",new Address("123 Street", "none", "Toronto", "M4J3E1", "Ontario", "Canada"), "fatih@fatih.com", "Notes Here", new MyDate(19,11,1997));
    }

    public void updateTable() {
        table.getItems().clear();
        Contact[] contacts = cm.allContacts();

        for (Contact c : contacts) {
            table.getItems().add(c);
        }
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Address Book");
        stage.setWidth(1500);
        stage.setHeight(1000);

        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn phoneCol = new TableColumn("Phone");
        TableColumn homePhoneCol = new TableColumn("Home");
        homePhoneCol.setCellValueFactory(new PropertyValueFactory<>("homePhone"));
        TableColumn workPhoneCol = new TableColumn("Work");
        workPhoneCol.setCellValueFactory(new PropertyValueFactory<>("workPhone"));
        phoneCol.getColumns().addAll(homePhoneCol, workPhoneCol);

        TableColumn homeAddressCol = new TableColumn("Home Address");
        homeAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn emailCol = new TableColumn("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn birthdayCol = new TableColumn("Birthday");
        birthdayCol.setCellValueFactory(new PropertyValueFactory<>("birthday"));

        TableColumn notesCol = new TableColumn("Notes");
        notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));


        table.getColumns().addAll(firstNameCol, lastNameCol, phoneCol, homeAddressCol, emailCol, birthdayCol, notesCol);

        updateTable();

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        vbox.setMinWidth(1000);

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        TextField homePhoneField = new TextField();
        homePhoneField.setPromptText("Home Phone");

        TextField workPhoneField = new TextField();
        workPhoneField.setPromptText("Work Phone");

        TextField streetInfo1Field = new TextField();
        streetInfo1Field.setPromptText("Street Info 1");

        TextField streetInfo2Field = new TextField();
        streetInfo2Field.setPromptText("Street Info 2");

        TextField cityField = new TextField();
        cityField.setPromptText("City");

        TextField postalCodeField = new TextField();
        postalCodeField.setPromptText("Postal Code");

        TextField provinceField = new TextField();
        provinceField.setPromptText("Province");

        TextField countryField = new TextField();
        countryField.setPromptText("Country");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField notesField = new TextField();
        notesField.setPromptText("Notes");

        TextField dayOfBirthField = new TextField();
        dayOfBirthField.setPromptText("Day of Birth");

        TextField monthOfBirthField = new TextField();
        monthOfBirthField.setPromptText("Month Of Birth");

        TextField yearOfBirthField = new TextField();
        yearOfBirthField.setPromptText("Year Of Birth");

        Button submitBtn = new Button("Submit");

        submitBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                cm.addOrEdit(firstNameField.getText(),
                        lastNameField.getText(),
                        homePhoneField.getText(),
                        workPhoneField.getText(),
                        new Address(
                                streetInfo1Field.getText(),
                                streetInfo2Field.getText(),
                                cityField.getText(),
                                postalCodeField.getText(),
                                provinceField.getText(),
                                countryField.getText()),
                        emailField.getText(),
                        notesField.getText(),
                        new MyDate(
                                Integer.parseInt(dayOfBirthField.getText()),
                                Integer.parseInt(monthOfBirthField.getText()),
                                Integer.parseInt(yearOfBirthField.getText())
                ));

                firstNameField.setText("");
                lastNameField.setText("");
                homePhoneField.setText("");
                workPhoneField.setText("");
                streetInfo1Field.setText("");
                streetInfo2Field.setText("");
                cityField.setText("");
                postalCodeField.setText("");
                provinceField.setText("");
                countryField.setText("");
                emailField.setText("");
                notesField.setText("");
                dayOfBirthField.setText("");
                monthOfBirthField.setText("");
                yearOfBirthField.setText("");
                
                updateTable();
            }
        });

        table.setRowFactory(new Callback<TableView<Contact>, TableRow<Contact>>() {
            @Override
            public TableRow<Contact> call(TableView<Contact> tableView) {
                final TableRow<Contact> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem removeItem = new MenuItem("Remove");
                removeItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        cm.deleteContact(row.getItem());
                        updateTable();
                    }
                });

                final MenuItem editItem = new MenuItem("Edit");
                editItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Contact contact = row.getItem();
                        System.out.println(contact.toString());
                        firstNameField.setText(contact.getFirstName());
                        lastNameField.setText(contact.getLastName());
                        homePhoneField.setText(contact.getHomePhone());
                        workPhoneField.setText(contact.getWorkPhone());
                        streetInfo1Field.setText(contact.getAddressObject().streetInfo1);
                        streetInfo2Field.setText(contact.getAddressObject().streetInfo2);
                        cityField.setText(contact.getAddressObject().city);
                        postalCodeField.setText(contact.getAddressObject().postalCode);
                        provinceField.setText(contact.getAddressObject().province);
                        countryField.setText(contact.getAddressObject().country);
                        emailField.setText(contact.getEmail());
                        notesField.setText(contact.getNotes());
                        dayOfBirthField.setText(Integer.toString(contact.getBirthdayObject().getDay()));
                        monthOfBirthField.setText(Integer.toString(contact.getBirthdayObject().getMonth()));
                        yearOfBirthField.setText(Integer.toString(contact.getBirthdayObject().getYear()));
                    }
                });

                contextMenu.getItems().addAll(removeItem, editItem);
                // Set context menu on row, but use a binding to make it only show for non-empty rows:
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu)null)
                                .otherwise(contextMenu)
                );
                return row ;
            }
        });

        final HBox hbox1 = new HBox();
        final HBox hbox2 = new HBox();
        final HBox hbox3 = new HBox();
        final HBox hbox4 = new HBox();


        hbox1.getChildren().addAll(firstNameField, lastNameField, homePhoneField, workPhoneField);
        hbox2.getChildren().addAll(streetInfo1Field, streetInfo2Field, cityField, postalCodeField);
        hbox3.getChildren().addAll(provinceField, countryField, emailField, notesField);
        hbox4.getChildren().addAll(dayOfBirthField, monthOfBirthField, yearOfBirthField, submitBtn);
        vbox.getChildren().addAll(hbox1, hbox2, hbox3, hbox4);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
}