import java.net.MalformedURLException;
import java.rmi.*;
import java.util.List;

/**
 * L'interface Centrale représente un système centralisé permettant la gestion
 * des capteurs, l'enregistrement des mesures et l'analyse des tendances climatiques.
 * Elle étend l'interface Remote pour être utilisée dans le contexte des appels
 * distants via RMI (Remote Method Invocation).
 *
 * @author Enzo Soulan
 * @author Yon Beaurain
 * @version 2.0
 * @since 2024-03-01
 */
public interface Centrale extends Remote
{
    /**
     * Ajoute un capteur avec l'intervalle spécifié pour l'enregistrement des mesures.
     *
     * @param intervalle Intervalle de temps entre les mesures du capteur.
     * @throws RemoteException En cas d'erreur lors de l'appel distant.
     * @throws MalformedURLException Si l'URL du capteur est mal formée.
     * @throws NotBoundException Si le capteur n'est pas lié correctement.
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
     * Liste les capteurs enregistrés dans la centrale.
     *
     * @return Chaîne de caractères représentant la liste des capteurs.
     * @throws RemoteException En cas d'erreur lors de l'appel distant.
     */
    public String listerCapteurs() throws RemoteException;

    /**
     * Enregistre les mesures (température et humidité) du capteur spécifié.
     *
     * @param codeUnique Code unique identifiant le capteur.
     * @param temperature Mesure de la température.
     * @param humidite Mesure de l'humidité.
     * @throws RemoteException En cas d'erreur lors de l'appel distant.
     */
    public void enregistrerMesures(String codeUnique, int temperature, int humidite) throws RemoteException;

    /**
     * Obtient la dernière mesure enregistrée pour le capteur spécifié.
     *
     * @param codeUnique Code unique identifiant le capteur.
     * @return Chaîne de caractères représentant la dernière mesure.
     * @throws RemoteException En cas d'erreur lors de l'appel distant.
     */
    public String obtenirDerniereMesure(String codeUnique) throws RemoteException;

    /**
     * Obtient les moyennes et les tendances des mesures pour le capteur spécifié sur une période donnée.
     *
     * @param codeUnique Code unique identifiant le capteur.
     * @param periode Période pour laquelle les moyennes et les tendances sont calculées.
     * @return Chaîne de caractères représentant les moyennes et les tendances.
     * @throws RemoteException En cas d'erreur lors de l'appel distant.
     */
    public String obtenirMoyennesTendances(String codeUnique, String periode) throws RemoteException;

    /**
     * Détermine la tendance climatique basée sur une liste de mesures.
     *
     * @param mesures Liste des mesures à utiliser pour déterminer la tendance.
     * @return Chaîne de caractères représentant la tendance climatique.
     * @throws RemoteException En cas d'erreur lors de l'appel distant.
     */
    public String determinerTendance(List<Integer> mesures) throws RemoteException;
    
    /**
     * Modifie l'intervalle de temps du capteur spécifié.
     *
     * @param codeUnique Code unique identifiant le capteur.
     * @param nouvelIntervalle Nouvel intervalle de temps entre les mesures du capteur.
     * @throws MalformedURLException Si l'URL du capteur est mal formée.
     * @throws RemoteException En cas d'erreur lors de l'appel distant.
     * @throws NotBoundException Si le capteur n'est pas lié correctement.
     */
    public void modifierIntervalleCapteur(String codeUnique, int nouvelIntervalle) throws MalformedURLException, RemoteException, NotBoundException;

    /**
     * Modifie l'intervalle de temps global pour tous les capteurs enregistrés dans la centrale.
     *
     * @param nouvelIntervalle Nouvel intervalle de temps entre les mesures pour tous les capteurs.
     * @throws MalformedURLException Si l'URL du capteur est mal formée.
     * @throws RemoteException En cas d'erreur lors de l'appel distant.
     * @throws NotBoundException Si le capteur n'est pas lié correctement.
     */
    public void modifierIntervalleGlobal(int nouvelIntervalle) throws MalformedURLException, RemoteException, NotBoundException;
    
}
