## Домашнее задание HW05
     Имплементировать DataJpaUserMealRepositoryImpl
     Разделить реализации Repository по профилям Spring: jdbc, jpa, datajpa
            (общее в профилях можно объединять, например <beans profile="datajpa,jpa">)
     Сделать тесты всех реализаций (jdbc, jpa, datajpa) через наследование (без дублирования),
             общее из UserMealServiceTest и UserServiceTest вынеси в базовый класс.
     Запустить все тесты: mvn test
                          или в IDEA Maven Lifecycle- test (кнопку skipTest отжать)

Optional

     Починить MealServlet / SpringMain (добавить профили)

     Сделать и протестировать в сервисах методы (можно сделать разные варианты, реализация только в DataJpa):
     -  достать по id пользователя вместе с его едой
     -  достать по id еду вместе с пользователем
     Обращения к DB сделать в одной транзакции

     В DataJpaUserMealRepositoryImpl.save попробуйте как упражнение обойтись одним вызовом proxy.save(userMeal)