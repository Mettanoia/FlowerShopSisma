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
        +FlowerDTO getFlower()
        +void saveFlower(FlowerDTO)
    }
    class TreeRepository {
        +TreeDTO getTree()
        +void saveTree(TreeDTO)
    }
    class DecorationRepository {
        +DecorationDTO getDecoration()
        +void saveDecoration(DecorationDTO)
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
