package pl.akademiakodu.library.dao;

import pl.akademiakodu.library.domain.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
        String addBook = "INSERT INTO Books (title, author, pages)"
                + "VALUES ('"
                + book.getTitle() +"','"
                + book.getAuthor() +"',"
                + book.getPages() +");";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(addBook);
        } catch (SQLException e){
            System.out.println("Nie udało się dodać książki");
        }
    }

    /*
      DELETE FROM table_name
      WHERE some_column=some_value;
    */

    @Override
    public void removeBook(Book book) {
//        String removeBook = "DELETE FROM Books WHERE "
//                + ;
    }

    @Override
    public List<Book> getAllBooks() {
        return null;
    }


    public static void main(String[] args) {
        BookDaoSqlite bookDaoSqlite = new BookDaoSqlite();
        bookDaoSqlite.addBook(new Book("Bolek i Lolek", "Jepetto", 130));
    }
}
