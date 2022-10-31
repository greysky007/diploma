# Дипломный проект по профессии «Тестировщик»

Дипломный проект — автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

[Перечень сценариев для автоматизации](docs/Plan.md)
## Начало работы

Клонировать репозиторий `git clone https://github.com/greysky007/diploma.git`

### Prerequisites
Установить на ПК:
1. JAVA JDK 11
2. IntelliJ IDEA
3. GIT
4. Docker


## Запуск тестов
1. Запустить контейнеры с mysql, postgresql, nodejs командой: docker-compose up
2. Запустить SUT командой:

Для работы с БД MySQL:
`java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar`

Для работы с БД Postresql:
`java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar
`

3. Запустить тесты командой:

Для тестирования с БД MySQL:
`gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app`

Для тестирования с БД Postgresql:
`gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app`

### Создание отчетов в Allure
Для формирования отчета и открытия в браузере использовать команду
 `gradlew allureServe` 

