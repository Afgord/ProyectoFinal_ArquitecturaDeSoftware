# Instrucciones de ejecución — CU1 Configurar Partida

## Descripción general

El **CU1 - Configurar Partida** permite que el jugador anfitrión defina los parámetros iniciales de una partida de UNO Spin:

- Rango mínimo y máximo de cartas numéricas.
- Número de cartas comodín.
- Número de cartas de acción.
- Tiempo máximo para mostrar las manos de los jugadores.

La solicitud se envía mediante la arquitectura orientada a eventos del proyecto:

```text
Publisher → Broker → DominioSuscriptor → Broker → Publisher
```

El dominio valida la configuración y responde con un evento de aceptación o rechazo.

---

## Requisitos previos

- Apache NetBeans con soporte para proyectos Maven.
- JDK configurado en NetBeans.
- El repositorio descargado localmente.
- Los siguientes proyectos deben compilar correctamente:
  - `Broker`
  - `DominioSuscriptor`
  - `Uno_Spin_Java`

---

## Configuración de red para ejecución local

La prueba del CU1 está configurada para ejecutarse en una misma computadora usando:

```text
127.0.0.1
```

### Puertos utilizados

| Nodo | Puerto |
|---|---:|
| Broker | 5001 |
| DominioSuscriptor | 5000 |
| Publisher / Jugador anfitrión | 5002 |

---

## Compilación de los proyectos

Antes de ejecutar el caso de uso, realizar **Clean and Build** en NetBeans sobre los siguientes proyectos:

1. `Broker`
2. `DominioSuscriptor`
3. `Uno_Spin_Java`

Cada compilación debe finalizar con:

```text
BUILD SUCCESS
```

---

## Orden de ejecución

Para probar el CU1 se deben iniciar tres procesos en el siguiente orden.

### 1. Ejecutar el Broker

Proyecto:

```text
Broker / BrokerApp
```

Clase principal:

```text
org.broker.MainBroker
```

Resultado esperado en consola:

```text
=== [BROKER] Escuchando en puerto: 5001 ===
[ServidorTCP] Escuchando en puerto 5001
```

---

### 2. Ejecutar el DominioSuscriptor

Proyecto:

```text
DominioSuscriptor / TraductorEventos
```

Clase principal:

```text
org.traductor.MainTraductor
```

Resultado esperado en consola:

```text
=== [SISTEMA] Iniciando Servidor en 127.0.0.1 ===
=== [TRADUCTOR] Escuchando en puerto: 5000 ===
[ServidorTCP] Escuchando en puerto 5000
```

---

### 3. Ejecutar el CU1 Configurar Partida

Proyecto:

```text
Uno_Spin_Java / EventoTraductor
```

Clase principal:

```text
com.mycompany.eventotraductor.EjecutadorConfigurarPartida
```

Al ejecutarlo, se abrirá la ventana:

```text
UNO Spin - Configurar partida
```

---

## Prueba del flujo principal

1. Seleccionar el rango de cartas numéricas.
2. Definir el número de cartas comodín.
3. Definir el número de cartas de acción.
4. Definir el tiempo máximo para mostrar cartas.
5. Presionar el botón:

```text
Guardar configuración
```

### Resultado esperado

- El formulario enviará el evento `EventoConfigurarPartida`.
- El Broker lo reenviará al `DominioSuscriptor`.
- El dominio validará la configuración.
- Se generará una respuesta de éxito.
- En pantalla se mostrará el mensaje:

```text
Partida configurada correctamente.
Estado: Disponible.
Otros jugadores ya pueden solicitar unirse.
```

---

## Evidencia esperada en consola

### Publisher

```text
[Dispatcher] Contexto agregado a la cola.
[ClienteTCP] Bytes transmitidos correctamente.
[ServidorTCP] Contexto agregado a la cola.
[Receptor] Bytes recibidos desde el mecanismo de entrada.
```

### Broker

```text
[ServidorTCP] Contexto agregado a la cola.
[Receptor] Bytes recibidos desde el mecanismo de entrada.
[Dispatcher] Contexto agregado a la cola.
[ClienteTCP] Bytes transmitidos correctamente.
```

### DominioSuscriptor

```text
[Fachada] Procesando configuración de partida...
[Fachada] Partida configurada correctamente.
[ClienteTCP] Bytes transmitidos correctamente.
```

---

## Nota sobre mensajes de conexión rechazada

Durante la prueba puede aparecer en el Broker un mensaje similar a:

```text
[ClienteTCP] Error al transmitir bytes: Connection refused: connect
```

Esto ocurre porque el Broker intenta notificar a otros jugadores registrados en el directorio, pero en la prueba del CU1 solo se ejecuta el jugador anfitrión. Este mensaje no impide que el caso de uso funcione correctamente.

---

## Flujo alterno validado

El dominio también valida configuraciones inválidas. Durante las pruebas se confirmó que, si se intenta enviar una configuración no permitida, el `DominioSuscriptor` responde con un evento de rechazo y el Publisher muestra el mensaje de error correspondiente.

Ejemplo de mensaje:

```text
No se pudo configurar la partida.

Motivo: El número de comodines debe estar entre 1 y 8.
```

---

## Rama del repositorio

El caso de uso individual se encuentra implementado en la rama:

```text
feature/cu1-configurar-partida
```