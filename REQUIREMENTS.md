# Точки пополнения

Реализовать приложение для отображения точек пополнения на карте (можно
использовать сторонние библиотеки RxJava, Dagger, Retrofit, Glide, Gson)
Приложение представляет из себя два экрана:
1. Экран с картой и списком точек пополнения. Переключение между этими
режимами можно сделать, например, с помощью ViewPager и Tabs наверху
2. Экран с деталями точки

## Экран с картой и списком

### Карта

GoogleMap. На карте кнопки для изменения масштаба и определения текущего
местоположения.
Радиус поиска ограничиваем размерами карты на экране (от центра до крайнего верхнего
края). 
Полученные данные необходимо кэшировать в базе данных (можно использовать ORM,
предпочтение OrmLite). Время актуальности кэша – 10 минут.
По нажатию на пин – снизу экрана отображается вью с базовой информацией по точке:
иконка, имя, адрес (приветствуется использование BottomSheet)
По нажатию на эту вью – открывать экран деталей по точке.

### Список

Отобразить полученные точки в виде вертикального списка. В каждом элементе
отобразить базовую информацию (по аналогии со вью внизу карты), то есть иконка, имя,
адрес.
Важно отобразить статус «просмотренности» данной точки (можно изменить фон или
показать какой-нибудь индикатор). Статус «просмотренности» будет меняться на экране
деталей и хранится исключительно локально на устройстве.
По нажатию на элемент списка – открывать экран деталей по точке. (transition animation с
иконкой приветствуется)

## Детали точки

Отобразить любую имеющуюся информацию по дпереданной точке и партнере. Можно
расставить элементы на свой вкус, приветствуется соблюдение гайдлайнов Material Design
(для вдохновения: https://www.uplabs.com/android)
При заходе на экран требуется локально изменить статус «просмотренности» данной
точки пополнения. При возвращении с этого экрана, нужно обновить представление
данного элемента в списке (убрать индикатор «не просмотрено»)