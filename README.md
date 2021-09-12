# CHALLENGE - CONCURSO DE PREGUNTAS Y RESPUESTAS
Este el proyecto correspondiente al reto tecnico "Concurso de preguntas y respuestas" para Softka University.

## Descripcion
Estre proyecto se realizo en un JavaApplication usando el lenguaje Java. Consta de una serie de ventanas, donde podras:

-Agregar preguntas al banco de preguntas  
-Jugar  
-Ver el historico de las partidas  

Todo el manejo de persistencia de datos se manejo desde el servidor local.

## Instalacion
Para la ejecucion del proyecto se recomienda usar los siguientes programas:
```bash
-NetBeans IDE 12.0
-XAMPP Control Panel v3.3.0
-Servidor Apache (el que viene incluido por defecto en XAMPP)
-phpMyAdmin SQL Dump version 5.1.1 (el que viene incluido por defecto en XAMPP)
```  
Estos programas son los mismos que se usaron para la realizacion del proyecto.
### Base de datos
Como se menciono anteriormente la persistencia de los datos se manejo de manera local (localhost).  

Para usar la base de datos de manera local, adjunto el siguiente link: https://drive.google.com/file/d/11HMcNJBXxqYyQIpegVf6DzXqT-oAFLN0/view?usp=sharing  

Alli podras encontrar un archivo .zip donde estara: un archivo .sql y el script correspondiente a la creacion de la base de datos.  
Si usas el archivo .sql, recuerda primero crear la base de datos con el nombre "examensoftka" y luego importar el archivo .sql

En la base de datos encontraras: 25 preguntas (5 por cada categoria) y algunos datos de partidas ya jugadas (para el funcionamiento del historico).

## Notas
-Todos los jugadores, por defecto, inician la primera ronda con 500 puntos.  
-Se usaron las identidades necesarias para el funcionamiento correcto del juego.  
-Para los datos del jugador, solo se le solicita su nombre, el cual es pedido antes de iniciar la partida.  
-En el historico podras observar el nombre del jugador, la fecha de la partida, saber si se retiro o no, saber si gano o no, hasta que ronda de preguntas llego y cuantos puntos acumulo en la partida.
