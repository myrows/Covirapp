# Covirapp :chart_with_upwards_trend: :satellite: :camera: :iphone:

Aplicación móvil creada sobre lenguaje Kotlin , esta app del Covid-19 proporciona información de varias fuentes oficiales con datos de interés, consume también de una Api creada en Kotlin con el IDE Intellij IDEA y entres sus funciones , la más destacada es la detección de mascarilla en el rostro facial gracias al aprendizaje automático de AutoML

## ¿Cómo puedo probarla?

Importe en Android Studio la aplicación localizada en Covirap-app

Cuenta de usuario : USUARIO - usuario PASSWORD - 12345678

### El API lo puedes probar a través de este enlace público de Postman

Postman > Import > Link > Paste URL

https://www.getpostman.com/collections/c8abfe0e5f3525b211a6

### ¿Cómo puedo probar el API en IntelliJ IDEA ?

1. Crea una instancia de PostgreSQL en Docker

~~~
docker run --name milky-way -p 5432:5432 -e POSTGRES_DB=milky-way -e POSTGRES_PASSWORD=milkyway -e POSTGRES_USERNAME=postgres -d postgres
~~~

2. Run > Gradle > Tasks > bootRun
3. Fin ¿fácil no?

## Tecnologías usada

* Kotlin - Dagger2 - Coroutines
* Android Studio
* Intellij IDEA - Oauth 2.0
* Patrón MVVM
* Firebase - ML Kit - AutoML
* Cloud Firestore
* Coil
* Android Map SDK
* Librería de terceros para análisis de datos en gráficas
* Consume Apis externas de NovelCOVID, Narrativa, entre otras
* PostgreSQL
* Liquibase
* AWS EC2 y S3

## Features

+ Radar - añadir una circunferencia al marker y mandar notificación si una persona se acerca al área de otra
+ Mejoras en el diseño
+ Implementación de test unitarios y test a nivel de UI
