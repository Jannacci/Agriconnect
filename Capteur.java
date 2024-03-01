import java.rmi.*;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.net.*;

/**
 * La classe Capteur représente un capteur météorologique qui mesure la température
 * et l'humidité à intervalles réguliers et envoie ces mesures à une Centrale météorologique
 * via RMI (Remote Method Invocation).
 *
 * @author Enzo Soulan
 * @author Yon Beaurain
 * @version 1.0
 * @since 2024-02-09
 */
public class Capteur {
    private Centrale centrale;                      // Centrale météorologique à laquelle le capteur est connecté.
    private String codeUnique;                      // Code unique associé au capteur.
    private Double latitude;                        // Latitude géographique du capteur.
    private Double longitude;                       // Longitude géographique du capteur.
    private Timer timer;                            // Timer pour effectuer les mesures à intervalles réguliers.
    private static Random random = new Random();    // Générateur de nombres aléatoires.

    /**
     * Constructeur de la classe Capteur.
     *
     * @throws RemoteException En cas d'erreur lors de l'appel distant.
     * @throws NotBoundException Si le capteur n'est pas lié correctement.
     * @throws MalformedURLException Si l'URL de ma centrale est mal formée.
     */
    public Capteur() throws RemoteException, NotBoundException, MalformedURLException {
        this.centrale = (Centrale) Naming.lookup("rmi://localhost/Centrale");
        this.codeUnique = generateCodeUnique();
        this.latitude = generateRandomCoordinate();
        this. longitude = generateRandomCoordinate();
        this.timer = new Timer();
    }

    /**
     * Récupère le code unique du capteur.
     *
     * @return Le code unique du capteur.
     */
    public String getCodeUnique() {
        return codeUnique;
    }

    /**
     * Récupère les coordonnées GPS du capteur.
     *
     * @return Les coordonnées GPS sous la forme d'une chaîne de caractères.
     */
    public String getCoordonneesGPS() {
        return latitude + ", " + longitude;
    }

    /**
     * Réinitialise le timer du capteur avec un nouvel intervalle.
     *
     * @param nouvelIntervalle Le nouvel intervalle entre deux mesures en millisecondes.
     * @throws RemoteException En cas d'erreur lors de l'appel distant.
     * @throws NotBoundException Si le capteur n'est pas lié correctement.
     * @throws MalformedURLException Si l'URL de ma centrale est mal formée.
     */
    public void resetTimer(int nouvelIntervalle) throws RemoteException, NotBoundException, MalformedURLException {
        timer.cancel();
        timer = new Timer();
        demarrer(nouvelIntervalle);
    }

    /**
     * Génère un nombre aléatoire dans la plage spécifiée [min, max].
     * Cette méthode est utilisée pour générer la température et l'humidité.
     *
     * @param min La valeur minimale de la plage.
     * @param max La valeur maximale de la plage.
     * @return Un nombre aléatoire dans la plage spécifiée.
     */
    private static int generateRandomValue(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    /**
     * Génère un code unique basé sur le temps actuel en millisecondes.
     * Cette méthode est utilisée pour attribuer un code unique à chaque capteur. 
     *
     * @return Le code unique généré.
     */
    private static String generateCodeUnique() {
        return Long.toString(System.currentTimeMillis());
    }

    /**
     * Génère une coordonnée géographique aléatoire dans la plage [-90, 90] pour la latitude
     * ou [-180, 180] pour la longitude.
     * Cette méthode est utilisée pour attribuer des coordonnées aléatoires à chaque capteur. 
     *
     * @return Une coordonnée géographique aléatoire.
     */
    private static double generateRandomCoordinate() {
        return -90 + (180 * random.nextDouble());
    }

    /**
     * Démarre le timer du capteur pour effectuer les mesures à intervalles réguliers.
     *
     * @param intervalle L'intervalle entre deux mesures en millisecondes.
     * @throws RemoteException En cas d'erreur lors de l'appel distant.
     * @throws NotBoundException Si le capteur n'est pas lié correctement.
     * @throws MalformedURLException Si l'URL de ma centrale est mal formée.
     */
    public void demarrer(int intervalle) throws RemoteException, NotBoundException, MalformedURLException {
        TimerTask task = new TimerTask() {
            public void run() {
                int temperature = generateRandomValue(20, 30);
                int humidite = generateRandomValue(40, 60);
                try {
                    centrale.enregistrerMesures(codeUnique, temperature, humidite);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(task, 0, intervalle);
    }

    /**
     * Arrête le timer du capteur.
     *
     * @throws RemoteException En cas d'erreur lors de l'appel distant.
     */
    public void arreter() throws RemoteException {
        timer.cancel();
    }

    /**
     * Méthode principale de lancement, permettant aux capteurs d'interagir avec
     * le serveur central à travers des commandes spécifiques.
     *
     * @param args Les arguments de la ligne de commande (non utilisés dans cette application)
     */
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Connexion à la centrale distante via RMI
            Centrale centrale = (Centrale) Naming.lookup("rmi://localhost/Centrale");
            
            // On ajoute 5 capteurs avec des intervalles de mesures de 5000 ms
            for(int i = 0; i < 5; i++) {
                centrale.ajouterCapteur(5000);
            }

            // Boucle principale pour le menu interactif
            while (true) {
                System.out.println("\n╔═══════════════════════════╗");
                System.out.println("║          Capteur          ║");
                System.out.println("╚═══════════════════════════╝");
                System.out.println("\n1. Retirer un capteur");
                System.out.println("0. Quitter");
                System.out.print("\nVotre choix : ");
                int choix = scanner.nextInt();
                scanner.nextLine();
                switch (choix) {
                    case 1: // Retirer un capteur
                        System.out.print("Entrez le code du capteur : ");
                        String codeUniqueRetireString = scanner.nextLine();
                        centrale.retirerCapteur(codeUniqueRetireString);
                        break;
                    case 0: // Quitter l'application
                        System.out.println("Au revoir !");
                        return;
                    default: // Choix invalide
                        System.out.println("Choix invalide. Veuillez faire un choix valide.");
                }
                }
            } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
