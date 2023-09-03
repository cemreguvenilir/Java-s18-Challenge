package com.workintech.library.controller;

import com.workintech.library.entity.Author;
import com.workintech.library.entity.Book;
import com.workintech.library.entity.Category;
import com.workintech.library.mapping.AuthorResponse;
import com.workintech.library.mapping.BookResponse;
import com.workintech.library.service.AuthorService;
import com.workintech.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private AuthorService authorService;
    private BookService bookService;

    @Autowired
    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping("/")
    public List<AuthorResponse> findAll(){
        List<Author> authors = authorService.findAll();
        List<AuthorResponse> authorResponses = new ArrayList<>();
        for(Author author: authors){
            List<BookResponse> bookResponses = new ArrayList<>();
            for(Book book: author.getBookList()){
                bookResponses.add(new BookResponse(book.getId(), book.getName(), book.getCategory().getName()));
            }
            authorResponses.add(new AuthorResponse(author.getId(), author.getFirstName(), author.getLastName(),bookResponses));
        }
        return authorResponses;
    }

    @GetMapping("/{id}")
    public AuthorResponse findById(@PathVariable int id){
        Author author = authorService.findById(id);
        return new AuthorResponse(author.getId(), author.getFirstName(), author.getLastName(), null);
    }

    @PostMapping("/")
    public AuthorResponse save(@RequestBody Author author){
        Author author1 = authorService.save(author);
        return new AuthorResponse(author1.getId(), author1.getFirstName(), author1.getLastName(), null);
    }
    @PostMapping("/{bookId}")
    public AuthorResponse saveByBook(@RequestBody Author author, @PathVariable int bookId){
        Book book = bookService.findById(bookId);
       author.addBook(book);
       book.setAuthor(author);
       authorService.save(author);
       return new AuthorResponse(author.getId(), author.getFirstName(), author.getLastName(), null);
    }

    @PutMapping("/{id}")
    public Author update(@RequestBody Author author, @PathVariable int id){
        Author author1 = authorService.findById(id);
        if(author1 != null){

            author.setId(id);
            return authorService.save(author);
        }
        return null;
    }
    @DeleteMapping("/{id}")
    public AuthorResponse delete(@PathVariable int id){
        Author author1 = authorService.findById(id);
        if(author1 != null){
            authorService.delete(author1);
            return new AuthorResponse(author1.getId(), author1.getFirstName(), author1.getLastName(), null);
        }
        return null;
    }


}
