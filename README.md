Домашнее задание HW07

     Добавить тесты контроллеров:
           UserMealControllerTest для mealList.jsp и ResourceControllerTest для style.css (status и ContentType)

     Реализовать UserMealRestController и протестировать его через UserMealRestControllerTest

     В параметрах getBetween принимать LocalDateTime (конвертировать через Spring, см. ссылку ниже), 
       а передавать в тестах в формате ISO_LOCAL_DATE_TIME (например '2011-12-03T10:15:30').
     Следите чтобы url в тестах совпадал с параметрами в методе контроллера.
     Можно добавить логирование <logger name="org.springframework.web" level="debug"/> для проверки маршрутизации.


Optional

     Заменить @DATETIMEFORMAT на свой LocalDateTime конвертор или форматтер.

     Протестировать UserMealRestController через SoapUi или curl.