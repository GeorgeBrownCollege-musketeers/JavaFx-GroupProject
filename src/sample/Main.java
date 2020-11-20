package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Address Book");
        stage.setWidth(960);
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

        Contact[] contacts = cm.allContacts();

        for (Contact c : contacts) {
            table.getItems().add(c);
        }

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
}