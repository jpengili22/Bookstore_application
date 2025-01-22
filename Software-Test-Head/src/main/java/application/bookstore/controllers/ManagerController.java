package application.bookstore.controllers;

import application.bookstore.models.Book;
import application.bookstore.views.ManagerView;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ManagerController {
    private final ManagerView managerView;

    public ManagerController(ManagerView managerView) {
        this.managerView = managerView;
        setSaveListener();
    }

    private void setSaveListener() {

        managerView.getAddBtn().setOnAction(e -> {
            String isbn = managerView.getIsbnField().getText();
            boolean exist = false;
            int index = -1;
            int quantity = 0;
            for (Book b : managerView.getTableView().getItems()) {
                index++;
                if (isbn.equals(b.getIsbn())) {
                    quantity = b.getQuantity();
                    exist = true;
                    break;
                }
            }
            if (exist) {
                if (Integer.parseInt(managerView.getQuantityField().getText()) > 0) {
                    quantity = quantity + Integer.parseInt(managerView.getQuantityField().getText());
                    managerView.getTableView().getItems().get(index).setQuantity(quantity);
                    try {
                        deleteFileContent("./src/main/resources/data/books.ser");
                        for (Book b : managerView.getTableView().getItems()) {
                            b.saveInFile();
                        }
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                    managerView.getResultLabel().setText("Quantity updated!");
                    managerView.getResultLabel().setTextFill(Color.DARKGREEN);

                } else {
                    managerView.getResultLabel().setText("Invalid Quantity!");
                    managerView.getResultLabel().setTextFill(Color.DARKRED);
                }
                resetFields();
            } else {
                managerView.getResultLabel().setText("Wrong ISBN!!");
                managerView.getResultLabel().setTextFill(Color.DARKRED);
            }
            managerView.getTableView().refresh();
        });
    }

    private void resetFields() {
        managerView.getIsbnField().setText("");
        managerView.getQuantityField().setText("");
    }

    public static void deleteFileContent(String path) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(path);
        writer.print("");
        writer.close();
    }
}
