package application.bookstore.controllers;

import application.bookstore.models.Author;
import application.bookstore.models.Book;
import application.bookstore.views.BookView;
import javafx.scene.paint.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookController {
    private final BookView bookView;
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    public BookController(BookView bookView) {
        this.bookView = bookView;
        setSaveListener();
    }

    private void setSaveListener() {
        bookView.getSaveBtn().setOnAction(e -> executor.submit(() -> {
            String isbn = bookView.getIsbnField().getText();
            String title = bookView.getTitleField().getText();
            float purchasedPrice = Float.parseFloat(bookView.getPurchasedPriceField().getText());
            float sellingPrice = Float.parseFloat(bookView.getSellingPriceField().getText());
            Integer quantity = Integer.parseInt(bookView.getQuantityField().getText());
            Author author = bookView.getAuthorsComboBox().getValue();
            Book book = new Book(isbn, title, purchasedPrice, sellingPrice, quantity, author);
            if (book.saveInFile()) {
                bookView.getTableView().getItems().add(book);
                bookView.getResultLabel().setText("Book created successfully");
                bookView.getResultLabel().setTextFill(Color.DARKGREEN);
                resetFields();
            } else {
                bookView.getResultLabel().setText("Book creation failed");
                bookView.getResultLabel().setTextFill(Color.DARKRED);
            }
        }));
    }

    private void resetFields() {
        bookView.getIsbnField().setText("");
        bookView.getTitleField().setText("");
        bookView.getPurchasedPriceField().setText("");
        bookView.getSellingPriceField().setText("");
    }

    public void shutdown() {
        executor.shutdown();
        System.out.println("BookController threads shut down.");
    }
}
