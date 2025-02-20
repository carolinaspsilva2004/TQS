package tqs.stocksportfolio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class) // Integração com Mockito no JUnit 5
class StocksPortfolioTest {

    @Mock // tag para criar um mock que será injetado na classe sob teste
    IStockmarketService market; // Mock do serviço de mercado

    @InjectMocks
    StocksPortfolio portfolio; // Classe sob teste

    @BeforeEach // esta tag permite que o método seja executado antes de cada teste
    void setUp() {
        portfolio = new StocksPortfolio(market);
    }

    @Test
    void getTotalValue() {
        // Configurar expectativas do mock (valores fictícios)
        when(market.lookUpPrice("EBAY")).thenReturn(4.0);
        when(market.lookUpPrice("MSFT")).thenReturn(1.5);

        // Adicionar ações ao portfólio
        portfolio.addStock(new Stock("EBAY", 2)); // 2 * 4.0 = 8.0
        portfolio.addStock(new Stock("MSFT", 4)); // 4 * 1.5 = 6.0

        // Executar teste
        double result = portfolio.totalValue();

        // Verificar resultado esperado
        assertEquals(14.0, result, 0.01);

        // Verificar uso do mock (chamado exatamente duas vezes)
        verify(market, times(2)).lookUpPrice(anyString());
    }
}
