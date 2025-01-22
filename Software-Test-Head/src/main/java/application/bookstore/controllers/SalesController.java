package application.bookstore.controllers;

import application.bookstore.models.Book;
import application.bookstore.views.ManagerView;
import application.bookstore.views.SalesView;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SalesController {
    private final SalesView salesView;

    public SalesController(SalesView salesView) {
        this.salesView = salesView;
        //setSaveListener();
    }

    private void setSaveListener() {

        salesView.getAddBtn().setOnAction(e -> {
            String isbn = salesView.getIsbnField().getText();
            boolean exist = false;
            int index = -1;
            int quantity = 0;
            for (Book b : salesView.getTableView().getItems()) {
                index++;
                if (isbn.equals(b.getIsbn())) {
                    quantity = b.getQuantity();
                    exist = true;
                    break;
                }
            }
            if (exist) {
                if (Integer.parseInt(salesView.getQuantityField().getText()) > 0) {
                    quantity = quantity + Integer.parseInt(salesView.getQuantityField().getText());
                    salesView.getTableView().getItems().get(index).setQuantity(quantity);
                    try {
                        deleteFileContent("./src/main/resources/data/books.ser");
                        for (Book b : salesView.getTableView().getItems()) {
                            b.saveInFile();
                        }
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                    salesView.getResultLabel().setText("Quantity updated!");
                    salesView.getResultLabel().setTextFill(Color.DARKGREEN);

                } else {
                    salesView.getResultLabel().setText("Invalid Quantity!");
                    salesView.getResultLabel().setTextFill(Color.DARKRED);
                }
                resetFields();
            } else {
                salesView.getResultLabel().setText("Wrong ISBN!!");
                salesView.getResultLabel().setTextFill(Color.DARKRED);
            }
            salesView.getTableView().refresh();
        });
    }

    private void resetFields() {
        salesView.getIsbnField().setText("");
        salesView.getQuantityField().setText("");
    }

    public static void deleteFileContent(String path) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(path);
        writer.print("");
        writer.close();
    }
}

