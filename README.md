
## Домашнее задание HW06
    - Починить InMemory и Jdbc тесты

    - Добавить локализацию и jsp:include в meal*.jsp

    Починить работу meals: перенести функциональность MealServlet в контроллеры,
    разнести запросы на update/delete/.. по разным методам
    (можно делать по аналогии с ru.javawebinar.topjava.web.RootController#setUser,
    аннотации на параметры и адаптеры для LocalDate\Time мы введем позже).

Optional

    Добавить еще одну роль к ADMIN (будет 2 роли: ROLE_USER, ROLE_ADMIN), добавить проверку ролей в тесты на User,
    починить тесты Jpa (дублирование)

    Починить тесты на роли Jdbc:
           добавить транзакционность (DataSourceTransactionManager) и доставание ролей в JdbcUserRepositoryImpl