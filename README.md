``` mermaid
classDiagram
    direction TB
    
    class Floristeria {
        +String nombre
        +List arboles
        +List flores
        +List decoraciones
        +double valorTotalStock()
        +void agregarArbol(Arbol a)
        +void agregarFlor(Flor f)
        +void agregarDecoracion(Decoracion d)
        +void retirarArbol(Arbol a)
        +void retirarFlor(Flor f)
        +void retirarDecoracion(Decoracion d)
        +void mostrarStock()
        +void mostrarValorTotal()
        +void crearTicket(Ticket t)
        +void mostrarComprasAntiguas()
        +double totalDineroGanado()
    }
    
    class Producto {
        +double precio
    }
    
    class Arbol {
        +double altura
    }
    
    class Flor {
        +String color
    }
    
    class Decoracion {
        +String tipoMaterial
    }
    
    class ConexionSQL {
        +static ConexionSQL instancia
        +static ConexionSQL obtenerInstancia()
        +void conectar()
        +void desconectar()
    }
    
    class FloristeriaDAO {
        <<interface>>
        +void agregarArbol(Arbol a)
        +void agregarFlor(Flor f)
        +void agregarDecoracion(Decoracion d)
        +void retirarArbol(Arbol a)
        +void retirarFlor(Flor f)
        +void retirarDecoracion(Decoracion d)
        +List obtenerArboles()
        +List obtenerFlores()
        +List obtenerDecoraciones()
        +double obtenerValorTotalStock()
        +void crearTicket(Ticket t)
        +List obtenerComprasAntiguas()
        +double obtenerTotalDineroGanado()
    }
    
    class FloristeriaDAOSQL {
        +void agregarArbol(Arbol a)
        +void agregarFlor(Flor f)
        +void agregarDecoracion(Decoracion d)
        +void retirarArbol(Arbol a)
        +void retirarFlor(Flor f)
        +void retirarDecoracion(Decoracion d)
        +List obtenerArboles()
        +List obtenerFlores()
        +List obtenerDecoraciones()
        +double obtenerValorTotalStock()
        +void crearTicket(Ticket t)
        +List obtenerComprasAntiguas()
        +double obtenerTotalDineroGanado()
    }
    
    class PasarelaPago {
        +void procesarPago(Ticket t, Callback callback)
    }
    
    class Callback {
        +void ejecutar()
    }
    
    class Ticket {
        +String ticketID
        +List compra
    }
    
    class Cliente {
        +String dni
        +String nombre
        +List ticketIDs
    }
    
    Producto <|-- Arbol
    Producto <|-- Flor
    Producto <|-- Decoracion
    Floristeria -- Producto
    Floristeria -- Ticket
    FloristeriaDAO --|> Floristeria
    FloristeriaDAO --|> FloristeriaDAOSQL
    ConexionSQL -- FloristeriaDAOSQL
    PasarelaPago -- Ticket
    Ticket -- FloristeriaDAO
    Cliente -- Ticket
