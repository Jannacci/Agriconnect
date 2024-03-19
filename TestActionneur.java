import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;

public class TestActionneur {
    public static void main(String args[]) throws RemoteException {
        try {
            Centrale centrale = (Centrale) Naming.lookup("rmi://localhost/Centrale");
            
            // Déclenchement manuel de l'arrosage pour une durée de 5 minutes
            System.out.println("Début de l'arrosage manuel...");
            centrale.declencherArrosageManuel(null, 5);
            System.out.println("Arrosage terminé.");

            // Obtention de l'état de l'arrosage
            boolean etatArrosage = centrale.obtenirEtatArrosage();
            if (etatArrosage) {
                System.out.println("L'arrosage est actuellement en cours.");
            } else {
                System.out.println("L'arrosage n'est pas en cours.");
            }

        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
