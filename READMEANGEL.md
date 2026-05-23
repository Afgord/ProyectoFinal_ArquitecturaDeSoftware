# UNO Spin - Guia para correr el proyecto

Este repositorio tiene tres partes grandes que tienen que estar activas al mismo tiempo para jugar:

- **Broker** (mensajeria entre nodos)
- **DominioSuscriptor** (logica del juego en el servidor)
- **Uno_Spin_Java** (cliente con interfaz grafica)

Si falta alguna, el cliente muestra error de conexion o se queda esperando.

## Requisitos

Necesitas tener instalado:

- Java (el proyecto compila con release 25 segun los pom)
- Maven en el PATH
- Tres terminales libres como minimo (una por servicio, y otra por cada jugador)

Todo lo siguiente asume que estas en la raiz del repo, la carpeta `ProyectoFinal_ArquitecturaDeSoftware`.

## Compilar 

Abre una terminal y compila cada bloque. No es estrictamente necesario compilar los tres si solo vas a probar el cliente, pero la primera vez conviene hacerlo todo para evitar errores de dependencias.

**Broker:**

```
cd Broker
mvn install
```

**Dominio:**

```
cd DominioSuscriptor
mvn install
```

**Cliente:**

```
cd Uno_Spin_Java
mvn install
```

Si algo falla, revisa que Maven termine sin errores antes de seguir.

---

## Orden para ejecutar 

Siempre en este orden:

1. Broker
2. Dominio (TraductorEventos)
3. Clientes (EventoTraductor)

Si abres clientes antes que el broker o el dominio, van a fallar.

---

## Paso 1: Levantar el Broker

Terminal 1:

```
cd Broker\BrokerApp
mvn exec:java
```

Deberias ver algo como que escucha en el puerto **5001**. Deja esta terminal abierta.

---

## Paso 2: Levantar el Dominio

Terminal 2:

```
cd DominioSuscriptor\TraductorEventos
mvn exec:java
```

Deberias ver que escucha en el puerto **5000**. Tambien deja esta terminal abierta.

Si aqui sale "Address already in use", algun proceso viejo sigue usando el puerto. Cierra terminales anteriores o mata el proceso que tenga el 5000 y vuelve a intentar.

---

## Paso 3: Levantar los clientes (jugadores)

Cada jugador es una ventana aparte. El numero del jugador se pasa como argumento: 1, 2, 3 o 4.

Terminal 3 (jugador 1):

```
cd Uno_Spin_Java\EventoTraductor
mvn exec:java "-Dexec.args=1"
```

Terminal 4 (jugador 2):

```
cd Uno_Spin_Java\EventoTraductor
mvn exec:java "-Dexec.args=2"
```

Y asi sucesivamente para 3 y 4.

En PowerShell las comillas en `"-Dexec.args=1"` son necesarias. Sin ellas el comando suele fallar.

Puertos que usa cada cliente para escuchar respuestas del broker:

## Que deberia pasar en pantalla

1. Cada cliente se conecta y aparece el **lobby** con los jugadores conectados.
2. Segun cuantos esten en sala:
  - **1 jugador:** espera en el lobby.
  - **2 o 3 jugadores:** aparece el boton "Iniciar partida". Todos deben pulsarlo para arrancar.
  - **4 jugadores:** la partida inicia sola al conectarse el cuarto.
3. Cuando la partida arranca, el lobby se cierra y se abre el **tablero**.

## Probar con 3 jugadores 

1. Broker y Dominio corriendo.
2. Abre tres terminales con `-Dexec.args=1`, `-Dexec.args=2` y `-Dexec.args=3`.
3. Espera a ver los tres nombres en el lobby.
4. Los tres pulsan "Iniciar partida".
5. Deberian entrar al tablero al mismo tiempo.

## Reiniciar entre pruebas

El dominio guarda el estado de la partida en memoria. Si ya jugaste una ronda y vuelves a conectar clientes, a veces se salta el lobby o entras directo al tablero porque la partida quedo en curso.

Para probar de cero:

1. Cierra los clientes.
2. Detén el Dominio (Ctrl+C en su terminal).
3. Vuelve a ejecutar `mvn exec:java` en `TraductorEventos`.
4. El Broker puede quedarse abierto si quieres, pero si hay raro comportamiento reinicialo tambien.

## Errores 

**"No se pudo conectar al Dominio" o al Broker**

Verifica que los pasos 1 y 2 esten corriendo.

- Revisa que nadie mas este usando los puertos 5000 y 5001.

**"Tiempo de espera agotado al unirse al lobby"**

- El dominio o el broker no estan activos, o el dominio quedo en un estado raro. Reinicia el TraductorEventos.

**"Address already in use"**

- Hay otra instancia del mismo servicio. Cierra terminales viejas o libera el puerto.

**El lobby no se ve pero la consola dice "Partida iniciada"**

- Casi siempre es dominio sin reiniciar despues de una partida anterior. Reinicia TraductorEventos y vuelve a abrir clientes.



