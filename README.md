Estructura del Proyecto

El proyecto sigue una arquitectura modular con varias capas:

    Capa de Aplicación (Application Layer): Contiene la lógica de negocio principal y los casos de uso.
        use_cases: Contiene los casos de uso para gestionar floristerías, decoraciones, árboles y tickets.

    Capa de Dominio (Domain Layer): Contiene las entidades y lógica de dominio.
        entities: Contiene las clases que representan las entidades del dominio, como Floristeria, Decoracion, Arbol, Ticket.

    Capa de Infraestructura (Infrastructure Layer): Contiene las implementaciones de persistencia y los gateways.
        repositories: Contiene las implementaciones de los repositorios para MongoDB y SQL.
        mappers: Contiene los mappers para convertir entre entidades y DTOs.

    Capa de Interfaz de Usuario (User Interface Layer): Contiene la interfaz de usuario, en este caso, una interfaz de línea de comandos (CLI).
        cli_controller: Contiene el controlador para manejar los comandos de la CLI.

Flujo de Trabajo

    Inicio de la Aplicación:
        La aplicación se inicia desde App.java o Main.java, que configura el entorno y lanza el controlador de la CLI.

    Interacción con la CLI:
        El usuario interactúa con la aplicación a través de comandos de la CLI. Cada comando invoca un caso de uso específico.

    Casos de Uso:
        Los casos de uso son responsables de la lógica de negocio. Por ejemplo, AddDecorationUseCase maneja la lógica para añadir una nueva decoración.

    Persistencia:
        Los casos de uso interactúan con los repositorios para guardar y recuperar datos. Los repositorios utilizan mappers para convertir entre entidades y DTOs.

    Salida:
        La aplicación muestra los resultados en la CLI, permitiendo al usuario ver el inventario, confirmar operaciones, etc.


![Jesus](https://github.com/Mettanoia/FlowerShopSisma/blob/develop/DALL%C2%B7E%202024-07-18%2005.05.58%20-%20A%20depiction%20of%20Robert%20C.%20Martin%2C%20also%20known%20as%20Uncle%20Bob%2C%20illustrated%20as%20a%20saint.%20He%20should%20have%20a%20serene%20expression%2C%20wearing%20a%20robe%20with%20coding%20symbo.webp)
