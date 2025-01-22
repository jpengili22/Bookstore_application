package application.bookstore.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;

class AuthorTest {

  /**
   * testing search method by creating a temporary Array for testing and comparing
   * it with the search result from the actual method
   */
  @Test
  void testGetSearchResults() {
    ArrayList<Author> test = new ArrayList<>();
    Author newAuthor = new Author("Ismail", "Kadare");
    test.add(newAuthor);
    ArrayList<Author> actualSearchResults1 = Author.getSearchResults("Ismail Kadare");
    if(actualSearchResults1.isEmpty())
    {
      newAuthor.saveInFile();
    }
    ArrayList<Author> actualSearchResults = Author.getSearchResults("Ismail Kadare");
    assertEquals(test.toString(), actualSearchResults.toString());
    System.out.println(actualSearchResults);
  }

  @Test
  void testGetSearchResults2() {
    ArrayList<Author> actualSearchResults = Author.getSearchResults("Search Text");
    assertTrue(actualSearchResults.isEmpty());
  }


  @Test
  void testSetFirstName() {
    (new Author("test", "test")).setFirstName("test");
  }

  @Test
  void testSetLastName() {
    (new Author("test", "test")).setLastName("test");
  }

  @Test
  void testGetFullName() {
    assertEquals("test test", (new Author("test", "test")).getFullName());
  }

  @Test
  void testNewAuthor() {
    // Arrange and Act
    Author actualAuthor = new Author("test", "test");

    // Assert
    assertEquals("test", actualAuthor.getLastName());
    assertEquals("test", actualAuthor.getFirstName());
  }

  @Test
  void testSaveInFile() {
    Author test = new Author("testSave", "LastNameTest");
    assertTrue(test.saveInFile());
    ArrayList<Author> actualSearchResults = Author.getSearchResults("testSave LastNameTest");
    assertTrue(!actualSearchResults.isEmpty());
    assertFalse((new Author("test", "")).saveInFile());
  }

  @Test
  void testSaveInFile2() {
    assertFalse((new Author("", "test")).saveInFile());
  }

  @Test
  void testSaveInFile3() {
    assertFalse((new Author("test", "")).saveInFile());
  }

  /**
   * test isvalid method to check if an Author is valid also included some cases
   * when it is not
   */
  @Test
  void testIsValid() {
    assertTrue((new Author("Ismail", "Kadare")).isValid());
    assertFalse((new Author("", "test")).isValid());
    assertFalse((new Author("test", "")).isValid());
  }

  /**
   * test delete method
   */

  @Test
  void testDeleteFromFile2() {
    Author newAuthor = new Author("testDelete", "testDelete");
    newAuthor.saveInFile();
    newAuthor.deleteFromFile();
    ArrayList<Author> actualSearchResults = Author.getSearchResults("testDelete testDelete");
    assertTrue(actualSearchResults.isEmpty());
  }

  /**
   * Testing the getAuthors function if the size of actualAuthors is >0 it will
   * finish the test successfully if the method doesn't return a size >0 it means
   * that there are no users thus the test will fail.
   */
  @Test
  void testGetAuthors() {
    ArrayList<Author> actualAuthors = Author.getAuthors();
    assertTrue(!actualAuthors.isEmpty());
  }

  /**
   * testing getters and setters
   */
  @Test
  void testGettersAndSetters() {
    Author author = new Author("test", "last");
    String actualToStringResult = author.toString();
    String actualFirstName = author.getFirstName();
    assertEquals("last", author.getLastName());
    assertEquals("test last", actualToStringResult);
    assertEquals("test", actualFirstName);
  }

}
