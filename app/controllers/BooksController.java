package controllers;

import com.google.inject.Inject;
import models.Book;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.books.list;
import views.html.books.create;
import views.html.books.details;
import views.html.books.edit;

import java.util.List;
import java.util.Set;

public class BooksController extends Controller {

    private FormFactory formFactory;

    @Inject
    public BooksController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    // HTTP GET all books
    public Result getBooks(){
        List<Book> books = Book.find.all();
        return ok(list.render(books));
    }

    // HTTP GET specific book
    public Result getBook(Integer id){
        Book book = Book.find.byId(id);
        if(book == null)
            return notFound();

        return ok(details.render(book));
    }

    // HTTP GET create book view
    public Result getCreate(){
        Form<Book> bookForm = formFactory.form(Book.class);
        return ok(create.render(bookForm));
    }

    // HTTP POST create new book
    public Result postCreate(){
        // Map request to book form object
        Form<Book> bookForm = formFactory.form(Book.class).bindFromRequest();
        // Extract book from book form
        Book book = bookForm.get();

        // Save into database
        book.save();

        // Redirect took book list
        return redirect(routes.BooksController.getBooks());
    }

    // HTTP GET retrieve edit view
    public Result getEdit(Integer id){

        Book book = Book.find.byId(id);

        if(book == null)
            return notFound("Coult not find book with id " + id);

        Form<Book> bookForm = formFactory.form(Book.class).fill(book);
        return ok(edit.render(bookForm));
    }

    // HTTP PUT update book
    public Result putEdit(){
        Form<Book> bookForm = formFactory.form(Book.class).bindFromRequest();
        Book book = bookForm.get();

        Book oldBook = Book.find.byId(book.id);

        if(oldBook == null)
            return notFound();

        oldBook.title = book.title;
        oldBook.author = book.author;
        oldBook.price = book.price;
        oldBook.update();

        return redirect(routes.BooksController.getBooks());
    }

    // HTTP DELETE
    public Result delete(Integer id){

        Book book = Book.find.byId(id);

        if(book == null)
            return notFound();

        book.delete();
        return redirect(routes.BooksController.getBooks());
    }


}
