package com.example.fxcontactnumber3rd;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alerts {
    private static Alert alert;
    private static ButtonType result;

    public static void alertTxtFieldAgeNotNumber() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Age field can't contain a symbols or letters.");
        alert.setContentText("Please put a number only.");
        alert.show();
    }

    public static void alertInvalidAge() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid age.");
        alert.setContentText("Please put a valid age.");
        alert.show();
    }

    public static void alertEmptyNameField() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Name field cannot be empty.");
        alert.setContentText("Please put a name in the field.");
        alert.show();
    }

    public static void alertEmptyNumberField() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Phone Number field cannot be empty.");
        alert.setContentText("Please put a number in the field.");
        alert.show();
    }

    public static void alertIncorrectPhoneNumberFormat() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Incorrect Number Format.");
        alert.setContentText("Please put a valid number for \"00000000000\". \nExample: 09208905313");
        alert.show();
    }

    public static boolean alertUnsavedChanges() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Unsaved Changes");
        alert.setHeaderText("You have unsaved changes.");
        alert.setContentText("Exit without saving changes?");

        result = alert.showAndWait().orElse(ButtonType.CANCEL);
        return result == ButtonType.OK;
    }

    public static boolean alertDeleteContactPerson() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("Delete this contact person?");
        alert.setContentText("Press OK to delete. \nThis action cannot be undone.");

        result = alert.showAndWait().orElse(ButtonType.CANCEL);
        return result == ButtonType.OK;
    }

    public static void alertContactNotFound() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Not found");
        alert.setHeaderText("Contact person not found.");
        alert.setContentText("Can't find who you're looking for");
        alert.show();
    }
}
