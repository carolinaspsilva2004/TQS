---comando para correr testes unit:
mvn test

---comando para correr os testes de integração:
mvn failsafe:integration-test

---comando para correr só os testes de frontend
mvn clean verify -Dtest=**/seleniumTests/*