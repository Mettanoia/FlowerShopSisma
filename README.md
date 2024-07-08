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


UML

``` mermaid
classDiagram
    class Flower {
    }
    class Tree {
    }
    class Decoration {
    }
    class Ticket {
    }

    class FlowerDTO {
    }
    class TreeDTO {
    }
    class DecorationDTO {
    }
    class TicketDTO {
    }

    class FlowerRepository {
        +FlowerDTO getFlower(Long)
        +void saveFlower(FlowerDTO)
        +void deleteFlower(FlowerDTO)
    }
    class TreeRepository {
        +TreeDTO getTree()
        +void saveTree(TreeDTO)
        +void deleteTree(TreeDTO)
    }
    class DecorationRepository {
        +DecorationDTO getDecoration()
        +void saveDecoration(DecorationDTO)
        +void deleteDecoration(DecorationDTO)
    }
    class TicketRepository {
        +TicketDTO getTicket()
        +void saveTicket(TicketDTO)
    }

    class CreateFlowerShop {
        +execute()
    }
    class AddFlower {
        +execute(Flower)
    }
    class AddTree {
        +execute(Tree)
    }
    class AddDecoration {
        +execute(Decoration)
    }
    class AddTicket {
        +execute(Ticket)
    }
    class DeleteFlower {
        +execute(Flower)
    }
    class DeleteTree {
        +execute(Tree)
    }
    class DeleteDecoration {
        +execute(Decoration)
    }
    class DeleteTicket {
        +execute(Ticket)
    }
    class PrintStock {
        +execute()
    }
    class PrintPurchaseHistory {
        +execute()
    }
    class PrintBenefits {
        +execute()
    }

    class CliController {
        +CreateFlowerShop createFlowerShop
        +AddFlower addFlower
        +AddTree addTree
        +AddDecoration addDecoration
        +AddTicket addTicket
        +DeleteFlower deleteFlower
        +DeleteTree deleteTree
        +DeleteDecoration deleteDecoration
        +DeleteTicket deleteTicket
        +PrintStock printStock
        +PrintPurchaseHistory printPurchaseHistory
        +PrintBenefits printBenefits
    }

    FlowerDTO --> FlowerRepository
    TreeDTO --> TreeRepository
    DecorationDTO --> DecorationRepository
    TicketDTO --> TicketRepository

    FlowerRepository <|.. AddFlower
    TreeRepository <|.. AddTree
    DecorationRepository <|.. AddDecoration
    TicketRepository <|.. AddTicket

    FlowerRepository <|.. DeleteFlower
    TreeRepository <|.. DeleteTree
    DecorationRepository <|.. DeleteDecoration
    TicketRepository <|.. DeleteTicket

    AddFlower --> Flower
    AddTree --> Tree
    AddDecoration --> Decoration
    AddTicket --> Ticket
    DeleteFlower --> Flower
    DeleteTree --> Tree
    DeleteDecoration --> Decoration
    DeleteTicket --> Ticket

    AddFlower --|> CliController
    AddTree --|> CliController
    AddDecoration --|> CliController
    AddTicket --|> CliController
    DeleteFlower --|> CliController
    DeleteTree --|> CliController
    DeleteDecoration --|> CliController
    DeleteTicket --|> CliController
    CreateFlowerShop --|> CliController
    PrintStock --|> CliController
    PrintPurchaseHistory --|> CliController
    PrintBenefits --|> CliController

    class FlowerMapper {
        +FlowerDTO toDTO(Flower)
        +Flower fromDTO(FlowerDTO)
    }
    class TreeMapper {
        +TreeDTO toDTO(Tree)
        +Tree fromDTO(TreeDTO)
    }
    class DecorationMapper {
        +DecorationDTO toDTO(Decoration)
        +Decoration fromDTO(DecorationDTO)
    }
    class TicketMapper {
        +TicketDTO toDTO(Ticket)
        +Ticket fromDTO(TicketDTO)
    }

    Flower --|> FlowerMapper
    Tree --|> TreeMapper
    Decoration --|> DecorationMapper
    Ticket --|> TicketMapper

    FlowerDTO --|> FlowerMapper
    TreeDTO --|> TreeMapper
    DecorationDTO --|> DecorationMapper
    TicketDTO --|> TicketMapper
