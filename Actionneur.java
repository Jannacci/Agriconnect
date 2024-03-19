import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

/**
 * La classe actionneur
 *
 * @author Enzo Soulan
 * @author Yon Beaurain
 * @author Emma Guillemet
 * @author Matteo Léger
 * @version 3.0
 * @since 2024-03-15
 */
public class Actionneur extends UnicastRemoteObject {
    private Centrale centrale;                      // Centrale météorologique à laquelle l'actionneur est connecté.
    private String codeUnique;                      // Code unique associé au capteur.
    private Double latitude;                        // Latitude géographique du capteur.
    private Double longitude;                       // Longitude géographique du capteur.
    private static Random random = new Random();    // Générateur de nombres aléatoires.

    public Actionneur(String codeUnique, double latitude, double longitude) throws RemoteException {
        this.codeUnique = generateCodeUnique();
        this.latitude = generateRandomCoordinate();
        this.longitude = generateRandomCoordinate();
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
     * Génère un code unique basé sur le temps actuel en millisecondes.
     * Cette méthode est utilisée pour attribuer un code unique à chaque capteur. 
     *
     * @return Le code unique généré.
     */
    private static String generateCodeUnique() {
        return Long.toString(System.currentTimeMillis());
    }

    /**
     * Getter
     * @return String ou Double
     */
    public String getCodeUnique() {
        return codeUnique;
    }

    public Double getCodeLongitude() {
        return longitude;
    }

    public Double getCodeLatitude() {
        return latitude;
    }
}
