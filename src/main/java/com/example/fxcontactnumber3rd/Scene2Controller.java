package com.example.fxcontactnumber3rd;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Scene2Controller {
    @FXML
    private ImageView imgViewAddToContacts;

    @FXML
    private ImageView imgViewEdit;

    @FXML
    private ImageView imgViewGoBack;
    @FXML
    private ImageView imgDelete;

    @FXML
    private RadioButton radioBtnFemale;

    @FXML
    private RadioButton radioBtnMale;

    @FXML
    private RadioButton radioBtnOthers;

    @FXML
    private TextField txtFieldAddress;

    @FXML
    private TextField txtFieldAge;

    @FXML
    private TextField txtFieldName;

    @FXML
    private TextField txtFieldPhoneNumber;

    @FXML
    private ToggleGroup toggleGroupGender;
    public ContactPerson selectedContactPerson;
    private Scene1Controller scene1Controller;
    private String genderSelect = null;
    private Image imgEdit = new Image(getClass().getResourceAsStream("/com/example/fxcontactnumber3rd/Media/imgEdit.png"));
    private Image imgEditDone = new Image(getClass().getResourceAsStream("/com/example/fxcontactnumber3rd/Media/imgEditDone.png"));
    private PersonHandler personHandler = new PersonHandler();
    private boolean editMode = true;
    private final int MAX_AGE = 150;

    public void setSelectedContactPerson(ContactPerson selectedContactPerson) {
        this.selectedContactPerson = selectedContactPerson;
    }

    public void setScene1Controller(Scene1Controller scene1Controller) {
        this.scene1Controller = scene1Controller;
    }

    @FXML
    void imgViewAddToContactsClicked(MouseEvent event) {
        checkNameFields(event);
    }

    @FXML
    void imgViewEditClicked(MouseEvent event) {
        personHandler.handleEditButtonClick();
    }

    @FXML
    void imgViewGoBackClicked(MouseEvent event) {
        goBackToScene1(event, false);
    }

    @FXML
    void imgDeleteClicked(MouseEvent event) {
        if (Alerts.alertDeleteContactPerson()) {
            personHandler.somethingChanged = false;
            scene1Controller.observableList.remove(selectedContactPerson);
            goBackToScene1(event, false);
        }
    }

    @FXML
    void fieldsPressedEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            Node source = (Node) event.getSource();

            MouseEvent newMouseEvent = new MouseEvent(
                    MouseEvent.MOUSE_CLICKED,
                    0, 0, 0, 0, MouseButton.PRIMARY, 1,
                    false, false, false, false, true, false, false, false, false, false, null
            );

            source.fireEvent(newMouseEvent);
            checkNameFields(newMouseEvent);
        }
    }

    @FXML
    void radioBtnClicked() {
        if (radioBtnMale.isSelected()) {
            genderSelect = "Male";
        } else if (radioBtnFemale.isSelected()) {
            genderSelect = "Female";
        } else if (radioBtnOthers.isSelected()) {
            genderSelect = "Others";
        } else {
            genderSelect = "N/A";
        }
    }

    private void goBackToScene1(MouseEvent event, boolean willAdd) {
        if (event.getSource() instanceof Node) {
            Node source = (Node) event.getSource();
            if (willAdd) {
                personHandler.addAPerson();
            }

            Stage stageToClose = (Stage) source.getScene().getWindow();
            if (personHandler.somethingChanged) {
                if (Alerts.alertUnsavedChanges()) {
                    scene1Controller.refreshTableView();
                    stageToClose.close();
                }
            } else {
                scene1Controller.refreshTableView();
                stageToClose.close();
            }
        } else {
            personHandler.addAPerson();
            Stage thisStage = (Stage) imgViewGoBack.getScene().getWindow();
            thisStage.close();

        }
    }


    private void checkNameFields(MouseEvent event) {
        String name = txtFieldName.getText();
        String number = txtFieldPhoneNumber.getText();
        String age = txtFieldAge.getText();

        if (!name.isEmpty()) {
            if (!number.isEmpty()) {
                if (checkNumberField(number, true)) {
                    if (!age.isEmpty()) {
                        checkAgeField(event, true); // continue to check age input then proceed sa pag add
                    } else {
                        goBackToScene1(event, true); // okay lang kahit walang age, proceed sa pag add
                    }
                } else {
                    txtFieldPhoneNumber.setText(null);
                }
            } else {
                Alerts.alertEmptyNumberField();
            }
        } else {
            Alerts.alertEmptyNameField();
        }
    }

    private boolean checkNumberField(String number, boolean willAdd) {
        if (is11Digits(number)) {
            if (is11DigitsNumerics(number)) {
                return true;
            } else {
                Alerts.alertIncorrectPhoneNumberFormat();
                if (willAdd)
                    txtFieldPhoneNumber.setText(null);
            }
        } else {
            Alerts.alertIncorrectPhoneNumberFormat();
            if (willAdd)
                txtFieldPhoneNumber.setText(null);
        }
        return false;
    }

    private boolean checkAgeField(MouseEvent event, boolean willAdd) {
        if (isNumeric(txtFieldAge.getText())) {
            if (isValidAge(txtFieldAge.getText())) {
                if (willAdd)
                    goBackToScene1(event, true);
                else
                    return true;
            } else {
                Alerts.alertInvalidAge();
                if (willAdd)
                    txtFieldAge.setText(null);
            }
        } else {
            Alerts.alertTxtFieldAgeNotNumber();
            if (willAdd)
                txtFieldAge.setText(null);
        }
        return false;
    }

    private boolean is11Digits(String number) {
        String regexPattern = "\\d{11}";
        return number.matches(regexPattern);
    }

    private boolean is11DigitsNumerics(String number) {
        for (char c : number.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidAge(String str) {
        int age = Integer.parseInt(str);
        return age > 0 && age < MAX_AGE;
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+");
    }

    private Object checkFields(int oneAddTwoAgeThreeGen) {
        switch (oneAddTwoAgeThreeGen) {
            case 1:
                if (!txtFieldAddress.getText().isEmpty())
                    return txtFieldAddress.getText();
                break;
            case 2:
                if (!txtFieldAge.getText().isEmpty())
                    return Integer.valueOf(txtFieldAge.getText());
                break;
            case 3:
                if (!(genderSelect == null))
                    return this.genderSelect;
                break;
        }

        if (oneAddTwoAgeThreeGen == 1 || oneAddTwoAgeThreeGen == 3)
            return "";
        else
            return 0;
    }



    public class PersonHandler {
        private void addAPerson() {
            String name = txtFieldName.getText();
            String number = txtFieldPhoneNumber.getText();
            String address = (String) checkFields(1);
            byte age = ((Integer) checkFields(2)).byteValue();
            String gender = (String) checkFields(3);

            Scene1Controller.AddPerson addPerson = scene1Controller.new AddPerson();

            if (!address.isBlank()) {
                if (!(age == 0)) {
                    if (!gender.isBlank()) {
                        addPerson.addAPerson(age, name, number, address, gender);             // name, number, address, age, gender
                    } else {
                        addPerson.addAPerson(age, true, name, number, address);      // name, number, address, age
                    }
                } else {
                    if (!gender.isBlank()) {
                        addPerson.addAPerson(name, number, address, gender);                  // name, number, address, gender
                    } else {
                        addPerson.addAPerson(name, number, address, true);           // name, number, address
                    }
                }
            } else {
                if (!(age == 0)) {
                    if (!gender.isBlank()) {
                        addPerson.addAPerson(age, false, name, number, gender);      // name, number, age, gender
                    } else {
                        addPerson.addAPerson(age, name, number);                              // name, number, age
                    }
                } else {
                    if (!gender.isBlank()) {
                        addPerson.addAPerson(name, number, gender, false);           // name, number, gender
                    } else {
                        addPerson.addAPerson(name, number);                                   // name, number
                    }
                }
            }

            scene1Controller.btnRefresh(null);
            scene1Controller.initiateTxtFieldSearchEventAndSetTableField();

        }

        public void viewSelectedContactPerson() {
            imgDelete.setVisible(false);
            txtFieldName.getParent().requestFocus();
            imgViewAddToContacts.setVisible(false);

            imgViewEdit.setImage(imgEdit);
            imgViewEdit.setVisible(true);

            txtFieldName.setText(selectedContactPerson.getName());
            txtFieldPhoneNumber.setText(selectedContactPerson.getNumber());
            txtFieldAddress.setText(selectedContactPerson.getAddress());

            if (selectedContactPerson.getAge() != 0)
                txtFieldAge.setText(String.valueOf(selectedContactPerson.getAge()));
            else
                txtFieldAge.setText("");

            if (!selectedContactPerson.getGender().equals("N/A")) {
                if (selectedContactPerson.getGender().equals("Male"))
                    radioBtnMale.setSelected(true);
                else if (selectedContactPerson.getGender().equals("Female"))
                    radioBtnFemale.setSelected(true);
                else if (selectedContactPerson.getGender().equals("Others"))
                    radioBtnOthers.setSelected(true);
            }

            txtFieldName.setEditable(false);
            txtFieldPhoneNumber.setEditable(false);
            txtFieldAddress.setEditable(false);
            txtFieldAge.setEditable(false);
            radioBtnMale.setDisable(true);
            radioBtnFemale.setDisable(true);
            radioBtnOthers.setDisable(true);
        }


        private void editSelectedContactPerson() {
            imgDelete.setVisible(true);
            imgViewEdit.setImage(imgEditDone);

            txtFieldName.setEditable(true);
            txtFieldPhoneNumber.setEditable(true);
            txtFieldAddress.setEditable(true);
            txtFieldAge.setEditable(true);
            radioBtnMale.setDisable(false);
            radioBtnFemale.setDisable(false);
            radioBtnOthers.setDisable(false);

            txtFieldName.setOnKeyReleased(this::changesMade);
            txtFieldPhoneNumber.setOnKeyReleased(this::changesMade);
            txtFieldAddress.setOnKeyReleased(this::changesMade);
            txtFieldAge.setOnKeyReleased(this::changesMade);
            radioBtnMale.setOnMouseClicked(this::changesMade);
            radioBtnFemale.setOnMouseClicked(this::changesMade);
            radioBtnOthers.setOnMouseClicked(this::changesMade);
        }

        private boolean somethingChanged = false;

        private void changesMade(KeyEvent event) {
            somethingChanged = true;
        }

        private void changesMade(MouseEvent event) {
            somethingChanged = true;
        }

        public void handleEditButtonClick() { // may gagawin ako dito, hanggat hindi true yung boolean sa pag edit, hindi babalik sa viewMode
            if (editMode) {
                editSelectedContactPerson();
            } else {
                somethingChanged = false;
                saveChanges();
                viewSelectedContactPerson();
            }
            editMode = !editMode;
        }

        private void saveChanges() {
            selectedContactPerson.setName(txtFieldName.getText());

            if (checkNumberField(txtFieldPhoneNumber.getText(), false))
                selectedContactPerson.setNumber(txtFieldPhoneNumber.getText());
            else
                selectedContactPerson.setNumber(selectedContactPerson.getNumber());


            selectedContactPerson.setAddress(txtFieldAddress.getText());

            if (!txtFieldAge.getText().isEmpty()) {
                if (checkAgeField(null, false))
                    selectedContactPerson.setAge(Byte.parseByte(txtFieldAge.getText()));
                else
                    selectedContactPerson.setAge(selectedContactPerson.getAge());
            } else {
                selectedContactPerson.setAge((byte) 0);
            }

            radioBtnClicked();

            if (!selectedContactPerson.getGender().equals(genderSelect)) {
                selectedContactPerson.setGender(genderSelect);
            }
        }

    }
}