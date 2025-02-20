package tqs.stocksportfolio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class) // Integra Mockito com JUnit 5
class StocksPortfolioTest {

    @Mock
    IStockmarketService market; // Mock do serviço de mercado

    @InjectMocks
    StocksPortfolio portfolio; // Classe sob teste

    @BeforeEach
    void setUp() {
        portfolio = new StocksPortfolio(market);
    }

    @Test
    void getTotalValue() {
        // Configurar o mock com valores de retorno esperados
        when(market.lookUpPrice("EBAY")).thenReturn(4.0);
        when(market.lookUpPrice("MSFT")).thenReturn(1.5);

        // // Mock de ações que **não serão usadas** no teste
        // when(market.lookUpPrice("AAPL")).thenReturn(150.0);
        // when(market.lookUpPrice("GOOGL")).thenReturn(2800.0);
        // when(market.lookUpPrice("AMZN")).thenReturn(3400.0);

        // Adicionar apenas algumas ações ao portfólio
        portfolio.addStock(new Stock("EBAY", 2)); // 2 * 4.0 = 8.0
        portfolio.addStock(new Stock("MSFT", 4)); // 4 * 1.5 = 6.0

        // Executar teste
        double result = portfolio.totalValue();

        // Verificar resultado esperado com Hamcrest
        assertThat(result, is(closeTo(14.0, 0.01))); // closeTo evita problemas de precisão com doubles

        // Verificar se apenas as ações usadas foram consultadas no mock
        verify(market, times(1)).lookUpPrice("EBAY");
        verify(market, times(1)).lookUpPrice("MSFT");

        // // Verificar se ações desnecessárias não foram chamadas
        // verify(market, never()).lookUpPrice("AAPL");
        // verify(market, never()).lookUpPrice("GOOGL");
        // verify(market, never()).lookUpPrice("AMZN");
    }

    @Test
    void testMostValuableStocks() {
        // Mockando preços de mercado
        when(market.lookUpPrice("AAPL")).thenReturn(150.0);
        when(market.lookUpPrice("GOOGL")).thenReturn(2800.0);
        when(market.lookUpPrice("AMZN")).thenReturn(3400.0);
        when(market.lookUpPrice("MSFT")).thenReturn(1.5);
        when(market.lookUpPrice("EBAY")).thenReturn(4.0);

        // Adicionando ações ao portfólio
        portfolio.addStock(new Stock("AAPL", 10));   // 10 * 150.0  = 1500.0
        portfolio.addStock(new Stock("GOOGL", 1));   // 1 * 2800.0  = 2800.0
        portfolio.addStock(new Stock("AMZN", 2));    // 2 * 3400.0  = 6800.0
        portfolio.addStock(new Stock("MSFT", 50));   // 50 * 1.5    = 75.0
        portfolio.addStock(new Stock("EBAY", 100));  // 100 * 4.0   = 400.0

        // Pegando as 3 ações mais valiosas
        List<Stock> topStocks = portfolio.mostValuableStocks(3);

        // Verificações
        assertThat(topStocks, hasSize(3)); // Deve conter 3 ações
        assertThat(topStocks.get(0).getLabel(), is("AMZN")); // 6800.0 - mais valiosa
        assertThat(topStocks.get(1).getLabel(), is("GOOGL")); // 2800.0 - segunda mais valiosa
        assertThat(topStocks.get(2).getLabel(), is("AAPL")); // 1500.0 - terceira mais valiosa

        // Verifica se os preços foram consultados corretamente
        verify(market, atLeastOnce()).lookUpPrice(anyString());
    }

}
