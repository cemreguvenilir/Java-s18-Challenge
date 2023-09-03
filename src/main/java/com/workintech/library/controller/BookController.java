package com.workintech.library.controller;

import com.workintech.library.entity.Author;
import com.workintech.library.entity.Book;
import com.workintech.library.entity.Category;
import com.workintech.library.mapping.BookResponse;
import com.workintech.library.service.AuthorService;
import com.workintech.library.service.BookService;
import com.workintech.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private BookService bookService;
    private CategoryService categoryService;
    private AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, CategoryService categoryService, AuthorService authorService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.authorService = authorService;
    }

    @GetMapping("/")
    public List<BookResponse> findAll(){
       List<Book> books = bookService.findAll();
       List<BookResponse> responses = new ArrayList<>();
       for(Book book: books){
           responses.add(new BookResponse(book.getId(), book.getName(), book.getCategory().getName()));
       }
       return responses;
    }

    @GetMapping("/{id}")
    public BookResponse findById(@PathVariable int id){
        Book book = bookService.findById(id);
        return new BookResponse(book.getId(), book.getName(), book.getCategory().getName());
    }

    @PostMapping("/{categoryId}")
    public BookResponse save(@PathVariable int categoryId, @RequestBody Book book){
        Category category = categoryService.findById(categoryId);
        if(category != null){
            book.setCategory(category);
            bookService.save(book);
            return new BookResponse(book.getId(), book.getName(), category.getName());
        }
        return null;
    }
    @PostMapping("/saveByAuthor/{categoryId}/{authorId}")
    public BookResponse saveByAuthor(@PathVariable int categoryId, @PathVariable int authorId, @RequestBody Book book){
        Category category = categoryService.findById(categoryId);
        if(category != null){
            book.setCategory(category);
            Author author = authorService.findById(authorId);
            if(author != null){
                book.setAuthor(author);
                Book book1 = bookService.save(book);
                return new BookResponse(book1.getId(), book1.getName(),
                        book1.getCategory().getName(),
                        book1.getAuthor().getFirstName(),book1.getAuthor().getLastName());
            }
            return null;
        }
        return null;
    }

    @PutMapping("/{id}")
    public BookResponse update(@RequestBody Book book, @PathVariable int id){
        Book book1 = bookService.findById(id);
        if(book1 != null){
            book.setAuthor(book1.getAuthor());
            book.setCategory(book1.getCategory());
            book.setId(id);
            bookService.save(book);
            return new BookResponse(book.getId(), book.getName(), book.getCategory().getName());

        }
        return null;
    }

    @DeleteMapping("/{id}")
    public BookResponse delete(@PathVariable int id){
        Book book = bookService.findById(id);
        if(book != null){
            bookService.delete(book);
            return new BookResponse(book.getId(), book.getName(), book.getCategory().getName());
        }
        return null;
    }


}
