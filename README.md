# ProyectoFinal_ArquitecturaDeSoftware

Arquitectura orientada a eventos — UNO Spin (Equipo 4).

## Requisitos

- JDK 25
- Maven 3.9+

## Compilar todo

```powershell
cd Broker; mvn install -DskipTests
cd ..\DominioSuscriptor; mvn install -DskipTests
cd ..\Uno_Spin_Java; mvn install -DskipTests
```

## Cómo ejecutar (orden obligatorio)

Abre **3 terminales** y déjalas corriendo:

### 1. Dominio Suscriptor (puerto 5000)

```powershell
cd DominioSuscriptor\TraductorEventos
mvn exec:java
```

### 2. Broker (puerto 5001)

```powershell
cd Broker\BrokerApp
mvn exec:java
```

### 3. Cliente con interfaz gráfica

```powershell
cd Uno_Spin_Java\EventoTraductor
mvn exec:java
```

El cliente **requiere** Dominio y Broker activos. Si no hay conexión, muestra un diálogo de error y no abre el tablero. Al conectar, sincroniza el estado real enviando `EventoUnirsePartida` al Dominio.

Jugador 2, 3 o 4 (otra terminal cada uno):

```powershell
mvn exec:java "-Dexec.args=2"
mvn exec:java "-Dexec.args=3"
mvn exec:java "-Dexec.args=4"
```

## Puertos

| Proceso            | Puerto |
|--------------------|--------|
| Dominio Suscriptor | 5000   |
| Broker             | 5001   |
| Jugador N          | 5001+N |

## Red en LAN

Por defecto usa `127.0.0.1`. Para jugar en varias máquinas, pasa la IP del host del broker:

```powershell
mvn exec:java "-Duno.host=192.168.1.50"
```

Usa la **misma IP** en Dominio, Broker y todos los clientes.

## Simulador sin GUI (pruebas)

```powershell
cd Broker\BrokerApp
mvn exec:java "-Dexec.mainClass=org.broker.SimuladorCliente"
```

Cambia `ID_CLIENTE` dentro de `SimuladorCliente.java` para probar otro jugador.
