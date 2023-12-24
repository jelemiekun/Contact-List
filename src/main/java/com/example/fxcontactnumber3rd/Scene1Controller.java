package com.example.fxcontactnumber3rd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Scene1Controller {
    private FXMLLoader loader;
    private Parent root;
    private Scene scene;
    private Stage stage;

    private Label label;

    @FXML
    private TextField txtFieldSearch;

    @FXML
    private ImageView btnAdd;
    public Image icon = new Image(getClass().getResourceAsStream("/com/example/fxcontactnumber3rd/Media/icon.png"));

    @FXML
    private TableColumn<ContactPerson, String> colName;

    @FXML
    private TableColumn<ContactPerson, String> colNumber;
    private FilteredList filteredList;

    @FXML
    public TableView<ContactPerson> tableView;
    public ObservableList<ContactPerson> observableList = FXCollections.observableArrayList();

    @FXML
    private void txtFieldSearchPressedEnter(KeyEvent event) {
        if (!observableList.isEmpty()) {
            if (event.getCode() == KeyCode.ENTER)
                searchAPerson(event);
        }
    }

    public void setTables() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        tableView.setItems(observableList);

        tableViewLabelEmpty();

        filteredList = new FilteredList<>(observableList, p -> true);
        SortedList<ContactPerson> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);


    }

    public void initiateTxtFieldSearchEventAndSetTableField() {
        if (!observableList.isEmpty())
            txtFieldSearch.setOnKeyReleased(this::searchAPerson);
    }

    private void searchAPerson(KeyEvent event) {
        if (!txtFieldSearch.getText().isEmpty()) {
            String searchText = txtFieldSearch.getText().trim().toLowerCase();

            filteredList.setPredicate(contactPerson -> {
                ContactPerson person = (ContactPerson) contactPerson;

                return person.getName().toLowerCase().contains(searchText);
            });

            if (filteredList.isEmpty())
                tableViewLabelSearchNotFound();

            if (event.getCode() == KeyCode.ENTER) {
                Alerts.alertContactNotFound();
                refreshTableView();
            }
        } else {
            refreshTableView();
        }
    }

    private void tableViewLabelSearchNotFound() {
        label = new Label("Contact Person not found.");
        label.setLineSpacing(10);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setPrefHeight(50);
        tableView.setPlaceholder(label);
    }

    private void tableViewLabelEmpty() {
        label = new Label("Your contact list is empty.\nClick the add button to add a contact person.");
        label.setLineSpacing(10);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setPrefHeight(50);
        tableView.setPlaceholder(label);
    }


    @FXML
    void btnAdd(MouseEvent event) {
        openScene2(event, null, false);
    }

    @FXML
    void btnRefresh(MouseEvent event) {
        refreshTableView();
        txtFieldSearch.setText("");

        if (observableList.isEmpty())
            tableViewLabelEmpty();
    }

    private void openScene2(MouseEvent event, ContactPerson selectedContactPerson, boolean willEdit) {
        try {
            loader = new FXMLLoader(getClass().getResource("/com/example/fxcontactnumber3rd/Scene2.fxml"));
            root = loader.load();

            Scene2Controller scene2Controller = loader.getController();
            scene2Controller.setScene1Controller(this);

            scene = new Scene(root);
            stage = new Stage();

            if (willEdit) {
                scene2Controller.setSelectedContactPerson(selectedContactPerson);
                Scene2Controller.PersonHandler personHandler = scene2Controller.new PersonHandler();
                personHandler.viewSelectedContactPerson();
                stage.setTitle("Edit " + selectedContactPerson.getName());
            } else {
                stage.setTitle("Add a Contact Person");
            }

            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.setResizable(false);

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickedRow(MouseEvent event) {
        ContactPerson selectedPerson = tableView.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 1) {
            clearClickedRow();
        } else if (event.getClickCount() == 2) {
            if (selectedPerson != null) {
                openScene2(event, selectedPerson, true);
                clearClickedRow();
            }
        }
        // ayaw netong nasa baba haha edi wag
        /*if (selectedPerson != null) {
            if (event.getClickCount() == 2) {
                openScene2(event, selectedPerson, true);
                clearClickedRow();
            }
        } else {
            if (event.getClickCount() == 1 || event.getClickCount() == 2)
                clearClickedRow();
        }*/
    }

    private void clearClickedRow() {
        tableView.getSelectionModel().clearSelection();
    }

    public void refreshTableView() {
        filteredList.setPredicate(p -> true);
        tableView.refresh();
    }

    private void addToObservableList(ContactPerson contactPerson) {
        observableList.add(contactPerson);
    }

    public class AddPerson {
        public void addAPerson(String... strings) {
            ContactPerson contactPerson = null;

            if (strings.length == 2)
                contactPerson = new ContactPerson(strings[0], strings[1]);                                          // name, number
            else if (strings.length == 4)
                contactPerson = new ContactPerson(strings[0], strings[1], strings[2], strings[3]);                  // name, number, address, gender

            addToObservableList(contactPerson);
        }
        public void addAPerson(byte age, String... strings) {
            ContactPerson contactPerson = null;

            if (strings.length == 4) {
                contactPerson = new ContactPerson(strings[0], strings[1], strings[2], age, strings[3]);            // name, number, address, age, gender
            } else if (strings.length == 2) {
                contactPerson = new ContactPerson(strings[0], strings[1], age);                                    // name, number, age
            }
            addToObservableList(contactPerson);
        }

        public void addAPerson(byte age, boolean isAddress,String... strings) {
            ContactPerson contactPerson = null;

            if (strings.length == 3) {
                if (isAddress) {
                    contactPerson = new ContactPerson(strings[0], strings[1], strings[2], age);                     // name, number, address, age
                } else {
                    contactPerson = new ContactPerson(strings[0], strings[1], age, strings[2]);                     // name, number, age, gender
                }
            }
            addToObservableList(contactPerson);
        }

        public void addAPerson(String name, String number, String addressOrGender, boolean isAddress) {
            ContactPerson contactPerson = null;

            if (isAddress)
                contactPerson = new ContactPerson(name, number, addressOrGender, isAddress);                        // name, number, address
            else
                contactPerson = new ContactPerson(name, number, addressOrGender, isAddress);                        // name, number, gender

            addToObservableList(contactPerson);
        }
    }
}
