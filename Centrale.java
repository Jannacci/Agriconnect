import java.net.MalformedURLException;
import java.rmi.*;

/**
 * L'interface Centrale représente un système centralisé permettant la gestion
 * des capteurs, l'enregistrement des mesures et l'analyse des tendances climatiques.
 * Elle étend l'interface Remote pour être utilisée dans le contexte des appels
 * distants via RMI (Remote Method Invocation).
 *
 * @author Enzo Soulan
 * @author Yon Beaurain
 * @author Emma Guillemet
 * @author Matteo Léger
 * @version 1.0
 * @since 2024-02-09
 */
public interface Centrale extends Remote
{
    /**
     * Ajoute un capteur avec l'intervalle spécifié pour l'enregistrement des mesures.
     *
     * @param intervalle Intervalle de temps entre les mesures du capteur.
     * @throws RemoteException En cas d'erreur lors de l'appel distant.
     * @throws NotBoundException Si le capteur n'est pas lié correctement.
     * @throws MalformedURLException Si l'URL du capteur est mal formée.
     */
    public void ajouterCapteur(int intervalle) throws RemoteException, MalformedURLException, NotBoundException;
    
    /**
     * Retire le capteur avec le code unique spécifié.
     *
     * @param codeUnique Code unique identifiant le capteur à retirer.
     * @throws RemoteException En cas d'erreur lors de l'appel distant.
     */
    public void retirerCapteur(String codeUnique) throws RemoteException;

    /**
     * Enregistre les mesures (température et humidité) du capteur spécifié.
     *
     * @param codeUnique Code unique identifiant le capteur.
     * @param temperature Mesure de la température.
     * @param humidite Mesure de l'humidité.
     * @throws RemoteException En cas d'erreur lors de l'appel distant.
     */
    public void enregistrerMesures(String codeUnique, int temperature, int humidite) throws RemoteException;
}