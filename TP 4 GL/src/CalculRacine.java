import java.util.Arrays;

/**
 * Classe permettant de calculer les racines carrées des nombres dans une plage donnée
 */
public class CalculRacine {
    
    /**
     * Calcule les racines carrées des nombres entre A et B (inclus)
     * @param a borne inférieure (incluse)
     * @param b borne supérieure (incluse)
     * @return tableau des racines carrées des nombres de A à B
     * @throws IllegalArgumentException si A >= B ou si A est négatif
     */
    public double[] calculerRacines(int a, int b) {
        // Validation des paramètres
        validerParametres(a, b);
        
        // Calcul du nombre d'éléments
        int taille = b - a + 1;
        double[] racines = new double[taille];
        
        // Calcul des racines carrées
        for (int i = 0; i < taille; i++) {
            int nombre = a + i;
            racines[i] = Math.sqrt(nombre);
        }
        
        return racines;
    }
    
    /**
     * Valide les paramètres d'entrée
     * @param a borne inférieure
     * @param b borne supérieure
     * @throws IllegalArgumentException si les paramètres sont invalides
     */
    private void validerParametres(int a, int b) {
        if (a > b) {
            throw new IllegalArgumentException("A (" + a + ") ne peut pas être supérieur à B (" + b + ")");
        }
        
        if (a == b) {
            throw new IllegalArgumentException("A (" + a + ") ne peut pas être égal à B (" + b + ")");
        }
        
        if (a < 0) {
            throw new IllegalArgumentException("A (" + a + ") ne peut pas être négatif");
        }
    }
    
    /**
     * Version optimisée pour les grandes plages de valeurs
     */
    public double[] calculerRacinesOptimise(int a, int b) {
        validerParametres(a, b);
        
        int taille = b - a + 1;
        double[] racines = new double[taille];
        
        // Calcul direct sans variable intermédiaire
        for (int i = 0; i < taille; i++) {
            racines[i] = Math.sqrt(a + i);
        }
        
        return racines;
    }
    
    /**
     * Affiche les racines carrées calculées (méthode utilitaire)
     */
    public void afficherRacines(int a, int b) {
        double[] racines = calculerRacines(a, b);
        System.out.println("Racines carrées de " + a + " à " + b + ":");
        for (int i = 0; i < racines.length; i++) {
            System.out.printf("√%d = %.4f\n", (a + i), racines[i]);
        }
    }
}