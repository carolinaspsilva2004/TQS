package tqs.booksearch;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LibrarySteps {
    static final Logger log = getLogger(lookup().lookupClass());

    private Library library = new Library();
    private List<Book> searchResults;

    @ParameterType(".*")
    public LocalDate iso8601Date(String date) {
        return LocalDate.parse(date.replace("\"", "")); // Remove aspas antes de converter
    }


    @Given("a biblioteca contém os seguintes livros:")
    public void a_biblioteca_contém_os_seguintes_livros(DataTable table) {
        List<Map<String, String>> books = table.asMaps();
        for (Map<String, String> book : books) {
            library.addBook(new Book(
                book.get("título"),
                book.get("autor"),
                LocalDate.parse(book.get("publicado"))
            ));
        }
    }

    @When("o cliente pesquisa por livros do autor {string}")
    public void o_cliente_pesquisa_por_livros_do_autor(String autor) {
        searchResults = library.findBooksByAuthor(autor);
    }

    @When("o cliente pesquisa por livros publicados entre {iso8601Date} e {iso8601Date}")
    public void o_cliente_pesquisa_por_livros_publicados_entre(LocalDate start, LocalDate end) {
        searchResults = library.findBooks(start, end);
    }

    @Then("os seguintes livros são retornados:")
    public void os_seguintes_livros_são_retornados(DataTable expectedTable) {
        List<String> expectedTitles = expectedTable.asMaps().stream()
            .map(row -> row.get("título"))
            .collect(Collectors.toList());

        List<String> actualTitles = searchResults.stream()
            .map(Book::getTitle)
            .collect(Collectors.toList());

        assertEquals(expectedTitles, actualTitles);
    }
}
