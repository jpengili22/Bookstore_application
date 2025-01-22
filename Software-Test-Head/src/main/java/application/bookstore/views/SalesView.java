package application.bookstore.views;

import application.bookstore.controllers.ManagerController;
import application.bookstore.controllers.SalesController;
import application.bookstore.models.Book;
import application.bookstore.models.Book;
import application.bookstore.ui.*;
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

public class SalesView extends View {
    private final BorderPane borderPane = new BorderPane();
    private final TableView<Book> tableView = new TableView<>();
    private final HBox formPane = new HBox();
    private final TextField isbnField = new TextField();
    private final TextField quantityField = new TextField();
    private final Button addBtn = new AddButton();
    private final Button saveBtn = new CreateButton();
    private final Button deleteBtn = new DeleteButton();
    private final Button editBtn = new EditButton();
    private final TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
    private final TableColumn<Book, String> titleCol = new TableColumn<>("Title");
    private final TableColumn<Book, Float> sellingPriceCol = new TableColumn<>("Selling Price");
    private final TableColumn<Book, Integer> quantityCol = new TableColumn<>("Quantity");
    private final TableColumn<Book, String> authorCol = new TableColumn<>("Author");
    private final TableColumn<Book, Integer> totalAmountCol = new TableColumn<>("TotalAmount");
    private final TableColumn<Book, String> userCol = new TableColumn<>("User");
    private final TableColumn<Book, String> dateCol = new TableColumn<>("Date");
    private final Label resultLabel = new Label("");
    private final SearchView searchView = new SearchView("Search for a book");
    private Book newBook = new Book();


    public SalesView() {
        setTableView();
        setForm();
        // inject the controller
        new SalesController(this);
    }

    @Override
    public Parent getView() {
        borderPane.setCenter(tableView);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);
        vBox.getChildren().addAll(formPane, resultLabel);
        borderPane.setBottom(vBox);
        return borderPane;
    }

    private void setForm() {
        formPane.setPadding(new Insets(20));
        formPane.setSpacing(20);
        formPane.setAlignment(Pos.CENTER);
        Label isbnLabel = new Label("ISBN: ", isbnField);
        isbnLabel.setContentDisplay(ContentDisplay.TOP);
        Label quantityLabel = new Label("Quantity", quantityField);
        quantityLabel.setContentDisplay(ContentDisplay.TOP);
        formPane.getChildren().addAll(isbnLabel, quantityLabel, addBtn);
    }

    private void setTableView() {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setEditable(false);
        tableView.setItems(FXCollections.observableArrayList(Book.getBooks()));

        isbnCol.setCellValueFactory(
                new PropertyValueFactory<>("isbn")
        );
        // to edit the value inside the table view
        isbnCol.setCellFactory(TextFieldTableCell.forTableColumn());

        titleCol.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );
        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());

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

        tableView.getColumns().addAll(isbnCol, titleCol, sellingPriceCol, quantityCol, authorCol);
    }

    public TableView<Book> getTableView() {
        return tableView;
    }

    public TextField getIsbnField() {
        return isbnField;
    }

    public Button getAddBtn() {
        return addBtn;
    }

    public Label getResultLabel() {
        return resultLabel;
    }

    public SearchView searchByAuthor = new SearchView("Author");

    public TextField getQuantityField() {
        return quantityField;
    }


}
