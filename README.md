# Covirapp :chart_with_upwards_trend: :satellite: :camera: :iphone:

Aplicación móvil creada sobre lenguaje Kotlin , esta app del Covid-19 proporciona información de varias fuentes oficiales con datos de interés, consume también de una Api creada en Kotlin con el IDE Intellij IDEA y entres sus funciones , la más destacada es la detección de mascarilla en el rostro facial gracias al aprendizaje automático de AutoML

<a href="https://ibb.co/9VMmMWJ"><img src="https://i.ibb.co/0Fwpwrk/detector-Mask.png" alt="detector-Mask" border="0" width="200"></a>
<a href="https://ibb.co/kgNWrpS"><img src="https://i.ibb.co/PZqfbR6/Paises-Data.png" alt="Paises-Data" border="0" width="200"></a>
<a href="https://ibb.co/TPXpvn4"><img src="https://i.ibb.co/RQnZvM3/Paises-Listado.png" alt="Paises-Listado" border="0" width="200"></a>
<a href="https://ibb.co/XVyCbfr"><img src="https://i.ibb.co/vDsjHC6/Radar.png" alt="Radar" border="0" width="200"></a>
<a href="https://ibb.co/6HtSMyD"><img src="https://i.ibb.co/v3dWFcL/users-Listado.png" alt="users-Listado" border="0" width="200"></a>

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
