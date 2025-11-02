import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

/**
 * Classe de test pour CalculRacine
 */
class TestCalculRacine {
    
    private CalculRacine calculRacine;
    
    @BeforeEach
    void setUp() {
        calculRacine = new CalculRacine();
    }
    
    // ===== TESTS DES CAS NORMaux =====
    
    @Test
    @DisplayName("Test avec une petite plage de valeurs positives")
    void testPetitePlagePositives() {
        // Arrange
        int a = 1, b = 5;
        
        // Act
        double[] resultats = calculRacine.calculerRacines(a, b);
        
        // Assert
        assertEquals(5, resultats.length, "Le tableau doit contenir 5 éléments");
        
        // Vérification des valeurs avec précision
        assertEquals(1.0, resultats[0], 0.0001, "√1 doit être 1.0");
        assertEquals(1.4142, resultats[1], 0.0001, "√2 doit être environ 1.4142");
        assertEquals(1.7321, resultats[2], 0.0001, "√3 doit être environ 1.7321");
        assertEquals(2.0, resultats[3], 0.0001, "√4 doit être 2.0");
        assertEquals(2.2361, resultats[4], 0.0001, "√5 doit être environ 2.2361");
    }
    
    @Test
    @DisplayName("Test avec zéro comme borne inférieure")
    void testAvecZero() {
        double[] resultats = calculRacine.calculerRacines(0, 2);
        
        assertEquals(3, resultats.length, "Doit contenir 3 éléments (0,1,2)");
        assertEquals(0.0, resultats[0], 0.0001, "√0 doit être 0.0");
        assertEquals(1.0, resultats[1], 0.0001, "√1 doit être 1.0");
        assertEquals(1.4142, resultats[2], 0.0001, "√2 doit être environ 1.4142");
    }
    
    @Test
    @DisplayName("Test avec des carrés parfaits")
    void testCarresParfaits() {
        double[] resultats = calculRacine.calculerRacines(4, 9);
        
        assertEquals(2.0, resultats[0], 0.0001, "√4 doit être 2.0");
        assertEquals(3.0, resultats[5], 0.0001, "√9 doit être 3.0");
    }
    
    // ===== TESTS DES CAS LIMITES =====
    
    @Test
    @DisplayName("Test avec une plage minimale (A et B consécutifs)")
    void testPlageMinimale() {
        double[] resultats = calculRacine.calculerRacines(10, 11);
        
        assertEquals(2, resultats.length, "Doit contenir 2 éléments");
        assertEquals(Math.sqrt(10), resultats[0], 0.0001);
        assertEquals(Math.sqrt(11), resultats[1], 0.0001);
    }
    
    @Test
    @DisplayName("Test avec une grande plage")
    void testGrandePlage() {
        int a = 100, b = 200;
        double[] resultats = calculRacine.calculerRacines(a, b);
        
        assertEquals(101, resultats.length, "Doit contenir 101 éléments");
        assertEquals(10.0, resultats[0], 0.0001, "√100 doit être 10.0");
    }
    
    // ===== TESTS DE GESTION DES EXCEPTIONS =====
    
    @Test
    @DisplayName("Test exception quand A > B")
    void testASuperieurB() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculRacine.calculerRacines(10, 5),
            "Doit lever une exception quand A > B"
        );
        
        assertTrue(exception.getMessage().contains("ne peut pas être supérieur"),
                  "Le message doit indiquer 'ne peut pas être supérieur'");
    }
    
    @Test
    @DisplayName("Test exception quand A = B")
    void testAEgalB() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculRacine.calculerRacines(5, 5),
            "Doit lever une exception quand A = B"
        );
        
        assertTrue(exception.getMessage().contains("ne peut pas être égal"),
                  "Le message doit indiquer 'ne peut pas être égal'");
    }
    
    @Test
    @DisplayName("Test exception quand A est négatif")
    void testANegatif() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculRacine.calculerRacines(-1, 5),
            "Doit lever une exception quand A est négatif"
        );
        
        assertTrue(exception.getMessage().contains("ne peut pas être négatif"),
                  "Le message doit indiquer 'ne peut pas être négatif'");
    }
    
    @Test
    @DisplayName("Test exception avec A très négatif")
    void testATresNegatif() {
        assertThrows(
            IllegalArgumentException.class,
            () -> calculRacine.calculerRacines(-100, 5),
            "Doit lever une exception même avec A très négatif"
        );
    }
    
    // ===== TESTS DE PERFORMANCE =====
    
    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    @DisplayName("Test performance avec très grande plage")
    void testPerformanceGrandePlage() {
        // Test avec une plage de 100,000 éléments
        double[] resultats = calculRacine.calculerRacines(1, 100000);
        
        assertEquals(100000, resultats.length, "Doit contenir 100,000 éléments");
        // Vérification rapide de la cohérence
        assertTrue(resultats[0] == 1.0, "Premier élément doit être 1.0");
        assertTrue(resultats[99999] > 316.0, "Dernier élément doit être > 316");
    }
    
    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Test performance plage moyenne")
    void testPerformancePlageMoyenne() {
        // Test avec une plage de 10,000 éléments
        double[] resultats = calculRacine.calculerRacines(1, 10000);
        
        assertEquals(10000, resultats.length, "Doit contenir 10,000 éléments");
    }
    
    // ===== TESTS DE COMPARAISON ET COHÉRENCE =====
    
    @Test
    @DisplayName("Test comparaison avec Math.sqrt()")
    void testComparaisonAvecMathSqrt() {
        int a = 50, b = 60;
        double[] resultats = calculRacine.calculerRacines(a, b);
        
        // Vérification que chaque valeur correspond à Math.sqrt()
        for (int i = a; i <= b; i++) {
            double valeurAttendue = Math.sqrt(i);
            double valeurCalculee = resultats[i - a];
            assertEquals(valeurAttendue, valeurCalculee, 0.000000001,
                        "La racine de " + i + " doit correspondre exactement à Math.sqrt()");
        }
    }
    
    @Test
    @DisplayName("Test cohérence entre méthode normale et optimisée")
    void testCohérenceMethodes() {
        int a = 1, b = 100;
        
        double[] resultatsNormaux = calculRacine.calculerRacines(a, b);
        double[] resultatsOptimises = calculRacine.calculerRacinesOptimise(a, b);
        
        assertArrayEquals(resultatsNormaux, resultatsOptimises, 0.0000000001,
                         "Les deux méthodes doivent donner exactement les mêmes résultats");
    }
    
    @Test
    @DisplayName("Test ordre des valeurs dans le tableau")
    void testOrdreValeurs() {
        int a = 1, b = 10;
        double[] resultats = calculRacine.calculerRacines(a, b);
        
        // Vérifier que les valeurs sont dans l'ordre croissant
        for (int i = 1; i < resultats.length; i++) {
            assertTrue(resultats[i] > resultats[i - 1],
                      "Les racines carrées doivent être en ordre croissant");
        }
    }
}