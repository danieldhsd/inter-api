# inter-api

## Para executar o projeto primeiro baixe o repositorio para sua maquina.

## Na pasta em que foi baixaido o repositorio execute:

mvn clean install -> para instalar o projeto com o maven

## Para executar os testes execute os comandos abaixo:

mvn test -> para executar os testes &nbsp;


mvn test -Dtest=DigitoUnicoServiceTeste -> Executa os testes da classe DigitoUnicoServiceTeste &nbsp;


mvn test -Dtest=DigitoUnicoCacheTeste  -> Executa os testes da classe DigitoUnicoCacheTeste &nbsp;


## Para rodar o projeto execute o comando abaixo:

java -jar target/inter-api-0.0.1-SNAPSHOT.jar &nbsp;


ou &nbsp;


mvn spring-boot:run
