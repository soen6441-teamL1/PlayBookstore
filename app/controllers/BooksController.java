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
import views.html.errors._404;

import java.util.List;

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

        if(book == null){
            return notFound(_404.render());
            //return notFound();
        }

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

        if(bookForm.hasErrors()){
            flash("danger", "Please correct the form below.");
            return badRequest(create.render(bookForm));
        }

        // Extract book from book form
        Book book = bookForm.get();

        // Save into database
        book.save();

        flash("success", "Book saved successfully.");

        // Redirect took book list
        return redirect(routes.BooksController.getBooks());
    }

    // HTTP GET retrieve edit view
    public Result getEdit(Integer id){

        Book book = Book.find.byId(id);

        if(book == null){
            //return notFound("Could not find book with id " + id);
            return notFound(_404.render());
        }


        Form<Book> bookForm = formFactory.form(Book.class).fill(book);
        return ok(edit.render(bookForm));
    }

    // HTTP PUT update book
    public Result putEdit(){
        Form<Book> bookForm = formFactory.form(Book.class).bindFromRequest();

        if(bookForm.hasErrors()){
            flash("danger", "please correct the form below.");
            return badRequest(edit.render(bookForm));
        }

        Book book = bookForm.get();

        Book oldBook = Book.find.byId(book.id);

        if(oldBook == null){
            flash("danger", "Book not found");
            return notFound();
        }

        oldBook.title = book.title;
        oldBook.author = book.author;
        oldBook.price = book.price;
        oldBook.update();

        flash("success", "Book saved successfully.");

        // Front-end js will take care of redirect
        //return redirect(routes.BooksController.getBooks());
        return ok();
    }

    // HTTP DELETE
    public Result delete(Integer id){

        Book book = Book.find.byId(id);

        if(book == null){
            flash("danger", "Book not found");
            return notFound();
        }


        book.delete();
        flash("success", "Book deleted");

        // Frontend js will take care of redirect
        return ok();
        //return redirect(routes.BooksController.getBooks());
    }


}
