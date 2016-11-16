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
            System.out.println("Nie udalo sie wykonac SQL " + e.getMessage());
        }
    }


    @Override
    public void addBook(Book book) {

    }

    @Override
    public void removeBook(Book book) {

    }

    @Override
    public List<Book> getAllBooks() {
        return null;
    }


    public static void main(String[] args) {
        BookDaoSqlite bookDaoSqlite = new BookDaoSqlite();
    }
}
