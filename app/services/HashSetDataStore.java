package services;

import models.Book;

import java.util.HashSet;
import java.util.Set;

public class HashSetDataStore {

    private static HashSet<Book> books;

    public static Set<Book> getBooks(){
        return books;
    }

    public static Book findById(Integer id){
        return books.stream().filter(b -> b.id.equals(id)).findFirst().orElse(null);
    }

    public static void add(Book book){
        books.add(book);
    }


    public static void set(Book book){
        books.add(book);
    }

    public static boolean remove(Book book){
        return books.remove(book);
    }


    static {
        books = new HashSet<>();
        books.add(new Book(1, "C++", 20, "Joe"));
        books.add(new Book(2, "Java", 30, "Sally"));
        books.add(new Book(3, "C#", 10, "Rachel"));
        books.add(new Book(4, "Scala", 35, "Alex"));
    }
}
