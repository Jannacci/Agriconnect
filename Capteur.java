import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * La classe Capteur représente un capteur météorologique qui mesure la température
 * et l'humidité à intervalles réguliers et envoie ces mesures à une Centrale météorologique
 * via RMI (Remote Method Invocation).
 *
 * @author Enzo Soulan
 * @author Yon Beaurain
 * @version 2.0
 * @since 2024-03-01
 */
public class Capteur {
    private Centrale centrale; // Centrale météorologique à laquelle le capteur est connecté
    private String codeUnique; // Code unique associé au capteur
    private Double latitude; // Latitude géographique du capteur
    private Double longitude; // Longitude géographique du capteur
    private Timer timer; // Timer pour effectuer les mesures à intervalles réguliers
    private static Random random = new Random(); // Générateur de nombres aléatoires
    
    /**
     * Constructeur de la classe Capteur.
     *
     * @throws RemoteException si une erreur liée à la communication distante survient
     * @throws NotBoundException si la centrale n'est pas liée correctement
     * @throws MalformedURLException si l'URL de la centrale est mal formée
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
     * @throws MalformedURLException si l'URL de la centrale est mal formée
     * @throws RemoteException si une erreur liée à la communication distante survient
     * @throws NotBoundException si la centrale n'est pas liée correctement
     */
    public void resetTimer(int nouvelIntervalle) throws MalformedURLException, RemoteException, NotBoundException {
        timer.cancel();                 // Annulation de la tâche actuelle
        timer = new Timer();            // Création d'un nouveau Timer et planification de la tâche avec le nouvel intervalle
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
     * @throws MalformedURLException si l'URL de la centrale est mal formée
     * @throws RemoteException si une erreur liée à la communication distante survient
     * @throws NotBoundException si la centrale n'est pas liée correctement
     */
    public void demarrer(int intervalle) throws MalformedURLException, RemoteException, NotBoundException {
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
     * @param ID Identifiant associé à l'arrêt du capteur.
     * @throws RemoteException si une erreur liée à la communication distante survient
     */
    public void arreter(String ID) throws RemoteException {
        timer.cancel();
    }
}
