import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * Cette classe représente l'implémentation d'une centrale météorologique utilisant RMI (Remote Method Invocation).
 * Elle utilise la bibliothèque RMI pour permettre la communication avec les capteurs météorologiques distants.
 *
 * @author Enzo Soulan
 * @author Yon Beaurain
 * @version 1.0
 * @since 2024-02-09
 */
public class  CentraleImpl extends UnicastRemoteObject implements Centrale {
    private HashMap<String, Capteur> capteurs; // Collection HashMap stockant les capteurs associés à leurs codes uniques.
    
    /**
     * Constructeur par défaut de la classe CentraleImpl.
     *
     * @throws RemoteException En cas d'erreur lors de la communication distante.
     */
    public CentraleImpl() throws RemoteException { 
        super();
        capteurs = new HashMap<String, Capteur>();
    }
    
    /**
     * Ajoute un nouveau capteur à la centrale avec l'intervalle spécifié.
     *
     * @param intervalle Intervalle de mesure du capteur.
     * @throws RemoteException En cas d'erreur lors de la communication distante.
     * @throws MalformedURLException En cas d'URL mal formée lors de la communication avec le capteur.
     * @throws NotBoundException Lorsque le capteur distant n'est pas lié correctement.
     */
    public synchronized void ajouterCapteur(int intervalle) throws RemoteException, MalformedURLException, NotBoundException {
        Capteur capteur = new Capteur();
        
        capteurs.put(capteur.getCodeUnique(), capteur);
        System.out.println("\nCapteur " + capteur.getCodeUnique() + " de coordonnées " + capteurs.get(capteur.getCodeUnique()).getCoordonneesGPS() + " et d'intervalle " + intervalle + " ajouté.");
        try {
            capteur.demarrer(intervalle);
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            e.printStackTrace();
        }        
    }

    /**
     * Retire le capteur associé au code unique spécifié.
     *
     * @param codeUnique Code unique du capteur à arrêter et retirer.
     * @throws RemoteException En cas d'erreur lors de la communication distante.
     */
    public synchronized void retirerCapteur(String codeUnique) throws RemoteException {
        capteurs.get(codeUnique).arreter();
        capteurs.remove(codeUnique);
        System.out.println("\nCapteur " + codeUnique + " retiré.");

    }

    /**
     * Enregistre les mesures de température et d'humidité pour un capteur spécifié.
     *
     * @param codeUnique Code unique du capteur.
     * @param temperature Température enregistrée.
     * @param humidite Humidité enregistrée.
     * @throws RemoteException En cas d'erreur lors de la communication distante.
     */
    public void enregistrerMesures(String codeUnique, int temperature, int humidite) throws RemoteException {
        LocalDateTime heureActuelle = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String mesure = heureActuelle.format(formatter) + " " + temperature + " " + humidite;
        String nomFichier = codeUnique + "_mesures.txt";
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier, true))) {
            writer.write(mesure);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        System.out.println("\nLe capteur " + codeUnique + " a enregistré de nouvelles mesures :\n- Température = " + temperature + " ;\n- Humidité = " + humidite + " ;");
    }

    /**
     * Méthode principale pour lancer la centrale météorologique.
     *
     * @param args Les arguments de la ligne de commande (non utilisés dans cette application).
     * @throws RemoteException En cas d'erreur lors de la communication distante.
     * @throws AlreadyBoundException En cas de liaison déjà existante.
     */
    public static void main(String args[]) throws RemoteException, AlreadyBoundException
    {   
        try
        {
            CentraleImpl centrale = new CentraleImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("Centrale", centrale);
        }
        catch(RemoteException re) 
        {
            re.printStackTrace();
        }
    }
}