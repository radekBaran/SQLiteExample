package pl.akademiakodu.library.dao;

import pl.akademiakodu.library.domain.Book;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import static javafx.scene.input.KeyCode.L;

public class BookDaoSqlite implements BookDao {

    private Connection connection;


    public BookDaoSqlite() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:library.db");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Books ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "title TEXT, "
                + "author TEXT, "
                + "pages INTEGER "
                + ");";


        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Nie udało się wykonać SQL " + e.getMessage());
        }
    }

    /*
     INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country)
    VALUES ('Cardinal','Tom B. Erichsen','Skagen 21','Stavanger','4006','Norway');
    */

    @Override
    public void addBook(Book book) {

        // REFLEKSJA @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        String simpleClassName = book.getClass().getSimpleName();
        System.out.println(simpleClassName);
        Field[] fields = book.getClass().getDeclaredFields();


        StringBuilder atributeString = new StringBuilder(" (");

        for(Field field : fields) {
            if ( !field.getName().equals("id") ){
                atributeString.append(field.getName());
                atributeString.append(",");
            }
            System.out.println(field.getName());
            System.out.println(field.getType());
        }
        atributeString.deleteCharAt(atributeString.length() - 1);
        atributeString.append(")");
        System.out.println(atributeString);

        String addBook = "INSERT INTO " + simpleClassName + "s " + atributeString
                + " VALUES ('"
                + book.getTitle() + "','"
                + book.getAuthor() + "',"
                + book.getPages() + ")";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(addBook);
        } catch (SQLException e) {
            System.out.println("Nie udało się dodać książki");
        }
    }

    /*
      DELETE FROM table_name
      WHERE some_column=some_value;
    */

    @Override
    public void removeBook(Book book) {
        String removeBook = "DELETE FROM Books WHERE title ='"
                + book.getTitle() + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(removeBook);
        } catch (SQLException e) {
            System.out.println("Nie udało się usunąć książki o tytule " + book.getTitle());
        }
    }


    @Override
    public List<Book> getAllBooks() {

        return null;
    }


    public static void main(String[] args) {
        BookDaoSqlite bookDaoSqlite = new BookDaoSqlite();

        bookDaoSqlite.addBook(new Book("W pustyni i w puszczy", "Sienkiewicz", 400));


        String title = "W pustyni i w pusczzy";
        String title2 = "Bolek i Lolek";
        bookDaoSqlite.removeBook(new Book(title2));
    }
}
