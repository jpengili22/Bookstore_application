package application.bookstore.controllers;

import application.bookstore.models.Bill;
import application.bookstore.models.Book;
import application.bookstore.views.LibrarianView;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.Date;

public class LibrarianController {
    private final LibrarianView librarianView;
    //lidhja ndremjet view dhe controller
    public LibrarianController(LibrarianView librarianView) {
        this.librarianView = librarianView;
        setSaveListener();
    }
//butoni save ben ruajtjen e fatures
    private void setSaveListener() {

        librarianView.getPurchaseBtn().setOnAction(e -> {
            String isbn = librarianView.getIsbnField().getText();
            Book theBook = new Book();
            boolean exist = false;
            int index = -1;
            int quantity = 0;
            for (Book b : librarianView.getTableView().getItems()) {  //kontrollon nese libri ekziston
                index++;
                if (isbn.equals(b.getIsbn())) {
                    theBook = b;
                    exist = true;
                    break;
                }
            }
            if (exist) {        //nese libri ekziston
                if (theBook.availableQuantity(Integer.parseInt(librarianView.getQuantityField().getText()))) {
                    Bill bill = new Bill();
                    bill.setUser(LoginController.getCurrentUser());
                    bill.setTotalAmount(Integer.parseInt(librarianView.getQuantityField().getText()) * theBook.getSellingPrice());
                    quantity = theBook.getQuantity();
                    theBook.setQuantity(Integer.parseInt(librarianView.getQuantityField().getText()));
                    bill.setSoldBook(theBook);
                    bill.setDate(new Date());

                    if (bill.saveInFile()) {
                        try {
                            // RUHET FATURA NE FOLDERIN E SPECIFIKUAR ME POSHTE
                            File fileOut = new File("./src/main/resources/data/invoices/ " + bill.getSoldBook().getTitle() + "_Bill.txt");
                            FileWriter out = new FileWriter(fileOut);
                            PrintWriter printWriter = new PrintWriter(out);
                            printWriter.println("ISBN: " + bill.getSoldBook().getIsbn());
                            printWriter.println("Title: " + bill.getSoldBook().getTitle());
                            printWriter.println("Author: " + bill.getSoldBook().getAuthor());
                            printWriter.println("Price: " + bill.getSoldBook().getSellingPrice());
                            printWriter.println("Quantity: " + bill.getSoldBook().getQuantity());
                            printWriter.println("Total: " + bill.getTotalAmount());
                            printWriter.println("Seller:" + bill.getUser().getUsername());
                            printWriter.println("Date: " + bill.getDate());
                            printWriter.close();
                        } catch (Exception exp) {
                            exp.printStackTrace();
                        }
                        quantity = quantity - bill.getSoldBook().getQuantity();
                        librarianView.getTableView().getItems().get(index).setQuantity(quantity);
                        try {
                            //fshin dhe rishkruan file ku jane te gjitha faturat
                            //me siper gjenerohet file .txt per faturen, kurse ketu ne i ruajme ne file
                            //ne menyre qe ti kemi me vone per statistika
                            deleteFileContent("./src/main/resources/data/books.ser");
                            for(Book b: librarianView.getTableView().getItems()){
                                b.saveInFile();
                            }
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                        librarianView.getResultLabel().setText("Invoice generated successfully!");
                        librarianView.getResultLabel().setTextFill(Color.DARKGREEN);
                    } else {
                        librarianView.getResultLabel().setText("Invoice could not be generated!");
                        librarianView.getResultLabel().setTextFill(Color.DARKRED);
                    }
                } else {
                    librarianView.getResultLabel().setText("Requested quantity is bigger than stock!!");
                    librarianView.getResultLabel().setTextFill(Color.DARKRED);
                }
                resetFields();
            } else {
                librarianView.getResultLabel().setText("Wrong ISBN!!");
                librarianView.getResultLabel().setTextFill(Color.DARKRED);
            }
            librarianView.getTableView().refresh();
        });
    }

    private void resetFields() {
        librarianView.getIsbnField().setText("");
        librarianView.getQuantityField().setText("");
    }

    public static void deleteFileContent(String path) throws FileNotFoundException { //Method to empty the content
        PrintWriter writer = new PrintWriter(path);
        writer.print("");
        writer.close();
    }
}
