package application.bookstore.controllers;

import application.bookstore.models.Book;
import application.bookstore.views.SalesView;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SalesController {
    private final SalesView salesView;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    public SalesController(SalesView salesView) {
        this.salesView = salesView;
        setSaveListener();
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
                try {
                    int newQuantity = Integer.parseInt(salesView.getQuantityField().getText());
                    if (newQuantity > 0) {
                        quantity += newQuantity;
                        salesView.getTableView().getItems().get(index).setQuantity(quantity);


                        executor.submit(() -> updateBookData(salesView));

                        salesView.getResultLabel().setText("Quantity updated!");
                        salesView.getResultLabel().setTextFill(Color.DARKGREEN);
                    } else {
                        salesView.getResultLabel().setText("Invalid Quantity!");
                        salesView.getResultLabel().setTextFill(Color.DARKRED);
                    }
                } catch (NumberFormatException ex) {
                    salesView.getResultLabel().setText("Invalid Quantity Format!");
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

    private void updateBookData(SalesView salesView) {
        try {
            // Clear the file content
            deleteFileContent("./src/main/resources/data/books.ser");


            for (Book b : salesView.getTableView().getItems()) {
                b.saveInFile();
            }
            System.out.println("Book data updated successfully.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFileContent(String path) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(path);
        writer.print("");
        writer.close();
    }

    public void shutdown() {
        executor.shutdown();
        System.out.println("SalesController threads shut down.");
    }
}
