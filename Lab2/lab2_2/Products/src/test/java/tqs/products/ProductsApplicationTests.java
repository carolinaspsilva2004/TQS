package tqs.products;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductsApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
    //test para a main
	void main() {
		ProductsApplication.main(new String[] {});
	}
}
