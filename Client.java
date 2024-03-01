import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * La classe Client est un programme client utilisé pour interagir avec le système AgriConnect
 * via des appels distants RMI (Remote Method Invocation).
 * Il permet à l'utilisateur d'effectuer diverses opérations liées aux capteurs agricoles.
 *
 * @author Enzo Soulan
 * @author Yon Beaurain
 * @version 2.0
 * @since 2024-03-01
 */
public class Client {
    
    /**
     * Méthode principale du client AgriConnect, permettant à l'utilisateur d'interagir avec
     * le serveur central à travers des commandes spécifiques.
     *
     * @param args Les arguments de la ligne de commande (non utilisés dans cette application)
     */
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Connexion à la centrale distante via RMI
            Centrale centrale = (Centrale) Naming.lookup("rmi://localhost/Centrale");

            // Boucle principale pour le menu interactif
            while (true) {
                System.out.println("\n╔═══════════════════════════════╗");
                System.out.println("║          AgriConnect          ║");
                System.out.println("╚═══════════════════════════════╝");
                System.out.println("\n1. Ajouter un capteur");
                System.out.println("2. Retirer un capteur");
                System.out.println("3. Lister les capteurs");
                System.out.println("4. Dernière mesure d'un capteur");
                System.out.println("5. Moyennes et tendances d'un capteur pour la dernière heure");
                System.out.println("6. Moyennes et tendances d'un capteur pour la dernière journée");
                System.out.println("7. Modifier l'intervalle de mesures d'un capteur");
                System.out.println("8. Modifier l'intervalle de mesures de plusieurs capteurs");
                System.out.println("0. Quitter");
                System.out.print("\nVotre choix : ");

                int choix = scanner.nextInt();
                scanner.nextLine();

                // Switch case pour traiter le choix du client
                switch (choix) {
                    case 1: // Ajouter un capteur
                        System.out.print("Entrez l'intervalle de mesure du capteur (ms) : ");
                        int intervalle = scanner.nextInt();
                        centrale.ajouterCapteur(intervalle);
                        break;
                    case 2: // Retirer un capteur
                        System.out.print("Entrez le code du capteur : ");
                        String codeUniqueRetireString = scanner.nextLine();
                        centrale.retirerCapteur(codeUniqueRetireString);
                        break;
                    case 3: // Lister les capteurs
                        System.out.println(centrale.listerCapteurs());
                        break;
                    case 4: // Obtenir la dernière mesure d'un capteur
                        System.out.print("Entrez le code du capteur : ");
                        String codeDerniereMesure = scanner.nextLine();
                        System.out.println(centrale.obtenirDerniereMesure(codeDerniereMesure));
                        break;
                    case 5: // Obtenir les moyennes et les tendances d'un capteur pour la dernière heure
                        System.out.print("Entrez le code du capteur : ");
                        String codeUniqueMoyennesTendancesHeure = scanner.nextLine();
                        System.out.println(centrale.obtenirMoyennesTendances(codeUniqueMoyennesTendancesHeure, "heure"));
                        break;
                    case 6: // Obtenir les moyennes et les tendances d'un capteur pour la dernière journée
                        System.out.print("Entrez le code du capteur : ");
                        String codeUniqueMoyennesTendancesJournee = scanner.nextLine();
                        System.out.println(centrale.obtenirMoyennesTendances(codeUniqueMoyennesTendancesJournee, "journée"));
                        break;
                    case 7: // Modifier l'invervalle de mesures d'un capteur
                        System.out.println("Entrez le code du capteur : ");
                        String codeIntervalleCapteur = scanner.nextLine();
                        System.out.println("Entrez l'intervalle du capteur (ms) : ");
                        intervalle = scanner.nextInt();
                        centrale.modifierIntervalleCapteur(codeIntervalleCapteur, intervalle);
                        break;
                    case 8: // Modifier l'intervalle de mesures de plusieurs capteurs
                        System.out.println("Entrez l'intervalle global (ms) : ");
                        intervalle = scanner.nextInt();
                        centrale.modifierIntervalleGlobal(intervalle);
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
        } finally { // Fermer le scanner dans le bloc finally pour s'assurer qu'il est fermé, même en cas d'exception
            scanner.close();
        }
    }
}
