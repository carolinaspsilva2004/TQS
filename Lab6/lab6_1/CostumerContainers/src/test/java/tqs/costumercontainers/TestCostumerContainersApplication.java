package tqs.costumercontainers;

import org.springframework.boot.SpringApplication;

public class TestCostumerContainersApplication {

    public static void main(String[] args) {
        SpringApplication.from(CostumerContainersApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
