package tqs.booksearch; 

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private List<Book> store = new ArrayList<>();

    public void addBook(Book book) {
        store.add(book);
    }

    public List<Book> findBooksByAuthor(String author) {
        return store.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public List<Book> findBooks(LocalDate start, LocalDate end) {
        return store.stream()
                .filter(book -> book.getPublished().isAfter(start) && book.getPublished().isBefore(end))
                .collect(Collectors.toList());
    }
}
