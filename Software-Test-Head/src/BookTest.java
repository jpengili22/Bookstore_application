package application.bookstore.models;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookTest {

  @Test
  void getPurchasedPrice() {
    Book book = new Book();
    book.setPurchasedPrice(10);
    assertEquals(10, book.getPurchasedPrice());
  }

  @Test
  void testAvailableQuantity() {
    Book book = new Book();
    book.setQuantity(1);

    assertTrue(book.availableQuantity(1));
  }

  @Test
  void testSaveInFile() {
    assertTrue((new Book()).saveInFile());
  }

  @Test
  void testDeleteFromFile() {
    assertFalse((new Book()).deleteFromFile());
  }

  @Test
  void testGettersAndSetters() {
    Book actualBook = new Book();
    Author author = new Author("test", "test");

    actualBook.setAuthor(author);
    actualBook.setIsbn("Isbn");
    actualBook.setPurchasedPrice(10.0f);
    actualBook.setQuantity(1);
    actualBook.setSellingPrice(10.0f);
    actualBook.setTitle("Dr");
    Author actualAuthor = actualBook.getAuthor();
    String actualIsbn = actualBook.getIsbn();
    float actualPurchasedPrice = actualBook.getPurchasedPrice();
    Integer actualQuantity = actualBook.getQuantity();
    float actualSellingPrice = actualBook.getSellingPrice();
    String actualTitle = actualBook.getTitle();
    boolean actualIsValidResult = actualBook.isValid();

    assertEquals("Dr", actualTitle);
    assertEquals("Isbn", actualIsbn);
    assertEquals(1, actualQuantity.intValue());
    assertEquals(10.0f, actualPurchasedPrice);
    assertEquals(10.0f, actualSellingPrice);
    assertTrue(actualIsValidResult);
    assertSame(author, actualAuthor);
  }

}
