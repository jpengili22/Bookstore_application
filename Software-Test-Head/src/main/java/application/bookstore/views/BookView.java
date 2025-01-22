package application.bookstore.views;

import application.bookstore.controllers.BookController;
import application.bookstore.models.Author;
import application.bookstore.models.Book;
import application.bookstore.ui.CreateButton;
import application.bookstore.ui.DeleteButton;
import application.bookstore.ui.EditButton;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class BookView extends View{
    private final BorderPane borderPane = new BorderPane();
    private final TableView<Book> tableView = new TableView<>();
    private final HBox formPane = new HBox();
    private final TextField isbnField = new TextField();
    private final TextField titleField = new TextField();
    private final TextField purchasedPriceField = new TextField();
    private final TextField sellingPriceField = new TextField();
    private final TextField quantityField = new TextField();
    private final ComboBox<Author> authorsComboBox = new ComboBox<>();
    private final Button saveBtn = new CreateButton();
    private final Button deleteBtn = new DeleteButton();
    private final Button editBtn = new EditButton();
    private final TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
    private final TableColumn<Book, String> titleCol = new TableColumn<>("Title");
    private final TableColumn<Book, Float> purchasedPriceCol = new TableColumn<>("Purchased Price");
    private final TableColumn<Book, Float> sellingPriceCol = new TableColumn<>("Selling Price");
    private final TableColumn<Book, Integer> quantityCol = new TableColumn<>("Quantity");
    private final TableColumn<Book, String> authorCol = new TableColumn<>("Author");
    private final Label resultLabel = new Label("");
    private final SearchView searchView = new SearchView("Search for a book");

    public TableView<Book> getTableView() {
        return tableView;
    }

    public TextField getIsbnField() {
        return isbnField;
    }

    public TextField getTitleField() {
        return titleField;
    }

    public TextField getPurchasedPriceField() {
        return purchasedPriceField;
    }

    public TextField getSellingPriceField() {
        return sellingPriceField;
    }

    public ComboBox<Author> getAuthorsComboBox() {
        return authorsComboBox;
    }

    public Button getSaveBtn() {
        return saveBtn;
    }

    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public Button getEditBtn() {
        return editBtn;
    }

    public TableColumn<Book, String> getIsbnCol() {
        return isbnCol;
    }

    public TableColumn<Book, String> getTitleCol() {
        return titleCol;
    }

    public TableColumn<Book, Float> getPurchasedPriceCol() {
        return purchasedPriceCol;
    }

    public TableColumn<Book, Float> getSellingPriceCol() {
        return sellingPriceCol;
    }

    public Label getResultLabel() {
        return resultLabel;
    }

    public SearchView getSearchView() {
        return searchView;
    }

    public TextField getQuantityField() {
        return quantityField;
    }

    public TableColumn<Book, Integer> getQuantityCol() {
        return quantityCol;
    }

    public TableColumn<Book, String> getAuthorCol() {
        return authorCol;
    }

    public BookView() {
        setTableView();
        setForm();
        // inject the controller
        new BookController(this);
    }

    private void setForm() {
        formPane.setPadding(new Insets(20));
        formPane.setSpacing(20);
        formPane.setAlignment(Pos.CENTER);
        //vendosja e fushave per plotesimin e te dhenave te librit
        Label isbnLabel = new Label("ISBN: ", isbnField);
        isbnLabel.setId("isbnLabel");
        isbnLabel.setContentDisplay(ContentDisplay.TOP);

        Label titleLabel = new Label("Title: ", titleField);
        titleLabel.setId("titleLabel");
        titleLabel.setContentDisplay(ContentDisplay.TOP);
        Label purchasedPriceLabel = new Label("Purchased price", purchasedPriceField);
        purchasedPriceLabel.setId("purchasedPriceLabel");
        purchasedPriceLabel.setContentDisplay(ContentDisplay.TOP);
        Label sellingPriceLabel = new Label("Selling price", sellingPriceField);
        sellingPriceLabel.setId("sellingPriceLabel");
        sellingPriceLabel.setContentDisplay(ContentDisplay.TOP);
        Label quantityLabel = new Label("Quantity", quantityField);
        quantityLabel.setId("quantityLabel");
        quantityLabel.setContentDisplay(ContentDisplay.TOP);
        Label authorLabel = new Label("Author", authorsComboBox);
        authorsComboBox.getItems().setAll(Author.getAuthors());
        // set default selected the first author
        if (!Author.getAuthors().isEmpty())
            authorsComboBox.setValue(Author.getAuthors().get(0));
        authorLabel.setContentDisplay(ContentDisplay.TOP);
        saveBtn.setId("saveBookBtn");
        formPane.getChildren().addAll(isbnLabel, titleLabel, purchasedPriceLabel, sellingPriceLabel, quantityLabel,
                                        authorLabel, saveBtn);
    }

    private void setTableView() {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setEditable(true);
        tableView.setItems(FXCollections.observableArrayList(Book.getBooks()));
        //krijimi i tabeles edhe dhenia e vleres per kolonat ne rastin konkret vlerat vijne nga fusha me titull isbn
        //cdo kolone merr vlere nje nga fushat e lists qe kthehet nga  getBooks ne rreshtin 153
        isbnCol.setCellValueFactory(
                new PropertyValueFactory<>("isbn")
        );
        // to edit the value inside the table view
        isbnCol.setCellFactory(TextFieldTableCell.forTableColumn());

        titleCol.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );
        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());

        purchasedPriceCol.setCellValueFactory(
                new PropertyValueFactory<>("purchasedPrice")
        );
        purchasedPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        sellingPriceCol.setCellValueFactory(
                new PropertyValueFactory<>("sellingPrice")
        );
        sellingPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        quantityCol.setCellValueFactory(
                new PropertyValueFactory<>("quantity")
        );
        quantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        authorCol.setCellValueFactory(
                new PropertyValueFactory<>("author")
        );
        authorCol.setCellFactory(ComboBoxTableCell.forTableColumn("Test", "Foo", "Bar"));

        tableView.getColumns().addAll(isbnCol, titleCol, purchasedPriceCol, sellingPriceCol, quantityCol, authorCol);
    }
    @Override
    public Parent getView() {
        resultLabel.setId("resultLabelBook");
        borderPane.setCenter(tableView);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);
        vBox.getChildren().addAll(formPane, resultLabel);
        borderPane.setBottom(vBox);
        return borderPane;
    }
}
