import Biblioteca.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;
import java.util.*;

class GestionBibliotecaImpl extends GestionBibliotecaPOA {
    private ORB orb;
    private Map<String, String> libros = new HashMap<>();

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    public String obtenerLibro(String titulo) {
        return libros.getOrDefault(titulo, "No encontrado");
    }

    public void agregarLibro(String titulo, String autor) {
        libros.put(titulo, autor);
    }

    public void eliminarLibro(String titulo) {
        libros.remove(titulo);
    }

    public String buscarPorAutor(String autor) {
        for (Map.Entry<String, String> libro : libros.entrySet()) {
            if (libro.getValue().equals(autor)) {
                return libro.getKey();
            }
        }
        return "No encontrado";
    }
}

public class ServidorBiblioteca {
    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            GestionBibliotecaImpl bibliotecaImpl = new GestionBibliotecaImpl();
            bibliotecaImpl.setORB(orb);

            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(bibliotecaImpl);
            GestionBiblioteca href = GestionBibliotecaHelper.narrow(ref);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            ncRef.rebind(ncRef.to_name("GestionBiblioteca"), href);

            System.out.println("Servidor listo...");
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
