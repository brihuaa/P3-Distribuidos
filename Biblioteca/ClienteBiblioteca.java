import Biblioteca.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class ClienteBiblioteca {
    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            GestionBiblioteca biblioteca = GestionBibliotecaHelper.narrow(ncRef.resolve_str("GestionBiblioteca"));

            biblioteca.agregarLibro("1984", "Orwell");
            biblioteca.agregarLibro("El Principito", "Saint-Exupéry");

            System.out.println("Libro encontrado: " + biblioteca.obtenerLibro("1984"));
            System.out.println("Buscar por autor: " + biblioteca.buscarPorAutor("Orwell"));

            biblioteca.eliminarLibro("1984");
            System.out.println("Libro tras eliminación: " + biblioteca.obtenerLibro("1984"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
