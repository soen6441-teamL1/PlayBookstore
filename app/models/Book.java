package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book extends Model {

    @Id
    @Constraints.Required
    public Integer id;

    @Constraints.Required
    public String title;

    @Constraints.Min(0)
    @Constraints.Required
    public Integer price;

    @Constraints.Required
    public String author;

    public Book() {
    }

    public Book(Integer id, String title, Integer price, String author) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.author = author;
    }

    public static Finder<Integer, Book> find = new Finder<>(Book.class);


}
