package ExerciciExceptions;

import java.io.IOException;

/**
 * EXERCICI AVANÇAT D'EXCEPCIONS EN JAVA
 *
 */

// TASCA 1: Excepció Checked Base
// TODO: Crea 'SistemaException' que hereti d'Exception. 
// Ha de tenir un constructor que accepti només un missatge de text, i un altre que accepti missatge i una "Throwable cause" (causa original).
class SistemaException extends Exception{


    SistemaException(String message, Throwable cause){
    super(message,cause);
}
SistemaException(String message){
    super(message);
}
}

// TASCA 2: Sub-excepció Checked
// TODO: Crea 'DadesNoValidesException' que hereti de 'SistemaException'.
// Aquesta excepció ha de ser més rica en informació:
// 1. Afegeix un camp privat de tipus String anomenat 'valorErroni'.
// 2. Crea un constructor que accepti el missatge i el valor que ha causat l'error.
// 3. Implementa un mètode getter per a 'valorErroni'.
class DadesNoValidesException extends SistemaException{
    private String valorErroni;

    DadesNoValidesException(String message, Throwable cause){
        super(message,cause);
    }
    DadesNoValidesException(String message){
        super(message);
    }
    DadesNoValidesException(String message, String valorErroni){
        super(message);
        this.valorErroni=valorErroni;
    }

    public String getValorErroni() {
        return valorErroni;
    }

    public void setValorErroni(String valorErroni) {
        this.valorErroni = valorErroni;
    }
}

// TASCA 3: Excepcions de Base de Dades
// TODO: Crea 'ConnexioFallidaException' que hereti de RuntimeException (Unchecked).
// TODO: Crea 'ConsultaInvalidaException' que hereti de SistemaException (Checked).
class ConexioFallidaException extends RuntimeException{

}
class ConsultaInvalidaException extends SistemaException{

   ConsultaInvalidaException(String missatge) {
       super(missatge);
   }
    ConsultaInvalidaException(String missatge, Throwable cause){
        super(missatge,cause);
    }
}

public class Main {

    public static void main(String[] args) {
        System.out.println("=== INICIANT EL SISTEMA ===");

        // Prova de les Tasques 4 i 5
        try {
            inicialitzarSistema();
        } catch (Exception e) {
            // TODO: Si has fet bé les tasques, aquí s'hauria de capturar una SistemaException.
            System.out.println("Error Crític capturat a main: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println("  -> Causa original oculta: " + e.getCause().getMessage());
            }
        }

        System.out.println("\n=== PROCESSANT DADES DELS USUARIS ===");
        // Prova de la Tasca 6. Crida a "processarUsuari" amb dades invàlides i comprova si genera les excepcions corresponents.
        // TODO: En capturar 'DadesNoValidesException', mostra també el 'valorErroni' fent servir el getter que has creat.

        System.out.println("\n=== CONSULTANT BASE DE DADES ===");
        // Prova de la Tasca 7
        executarConsulta("SELECT * FROM usuaris");
        executarConsulta(""); // Prova de consulta buida per forçar ConsultaInvalidaException

        System.out.println("\n=== FI DEL PROGRAMA ===");
    }

    /**
     * TASCA 4 i 5: Encadenament d'excepcions (Exception Chaining).
     */
    public static void inicialitzarSistema() throws SistemaException{ // TODO: Canvia 'throws Exception' per 'throws SistemaException'
        try {
            llegirFitxerConfiguracio();
        } catch (IOException e) {
            // TASCA 5: Encadenament
            // TODO: Captura la IOException que llança el mètode llegirFitxerConfiguracio.
            // En comptes de llançar la IOException directament, crea una nova 'SistemaException' 
            // amb el missatge "Fallada crítica en arrencar el sistema".
            // MOLT IMPORTANT: Passa l'excepció original (e) com a causa al constructor de SistemaException.
            // Finalment, llança la nova SistemaException.
            SistemaException errorException=new SistemaException("error");
            throw errorException;

        }
    }

    // Aquest mètode simula un error de lectura de fitxer. No s'ha de modificar.
    private static void llegirFitxerConfiguracio() throws IOException {
        // Imprimeix un missatge com "llegint configuració del disc...
        // Llança una excepció de tipus "IOException" per simular que no ha trobat un fitxer al disc
        System.out.println("[Sistema] Llegint configuració del disc...");
        throw new IOException("Fitxer 'config.sys' no trobat al disc dur.");
    }

    /**
     * TASCA 6: Multi-catch (Java 7+) i llançament explícit d'excepcions.
     */
    public static void processarUsuari(String idStr, String nom) throws DadesNoValidesException {
        // Processa usuari. Llança excepcions en els següents casos:
        // 1. L'string idStr s'ha de poder convertir a número. Si no és així, llança NumberFormatException.
        // 2. L'string "nom" no pot estar buit ni tenir només espais.
        // 3. L'string "nom" ha de tenir més de 3 caràcters.
        // TODO: Pels errors de "nom", llança 'DadesNoValidesException' passant-li el nom erroni com a segon argument.
        String n="";
        for (int i = 0; i < idStr.length(); i++) {
            char c=idStr.charAt(i);
            if (c>='0' && c<='9'){
                n=n+c;
        } else {
               throw new NumberFormatException("No es numero");
            }
        }
        int id=Integer.parseInt(n);

        if (nom.isEmpty()){
            throw new DadesNoValidesException("El nombre esta vacio");
        }
        if (nom.length()<=3){
            throw  new DadesNoValidesException("El nombre tiene que tener mas de tres caracteres","8");
        }

    }

    /**
     * TASCA 7: Gestió manual de recursos (Try-Catch-Finally).
     */
    public static void executarConsulta(String query) {
        // TASCA 7: Gestió manual
        // 
        // TODO: 
        // 1. Declara la variable 'ConnexioBaseDades bd' fora del bloc try i inicialitza-la a null.
        // 2. Dins d'un bloc 'try':
        //    a. Instancia l'objecte 'bd'.
        //    b. Crida al mètode connectar().
        //    c. Crida al mètode executar(query).
        // 3. Gestiona les excepcions:
        //    a. Captura 'ConsultaInvalidaException': Imprimeix "Error de sintaxi: " + missatge.
        //    b. Captura 'ConnexioFallidaException': Imprimeix "Error de xarxa: " + missatge.
        //    c. Captura 'Exception' genèrica: Imprimeix "Error inesperat."
        // 4. Afegeix un bloc 'finally' per tancar la connexió de manera segura.
        //    Recorda que close() també pot llançar una excepció!

        System.out.println("[Tasca 7] Intentant executar consulta...");
    }
}

/**
 * Classe auxiliar que simula un recurs extern (com un fitxer o connexió de xarxa).
 */

class ConnexioBaseDades {

    public void connectar() {
        System.out.println("[BD] Connectant al servidor...");
    }

    /**
     * TODO: Afegeix les clàusules 'throws' necessàries.
     */
    public void executar(String query) throws Exception {
        // Simulem dos tipus d'errors:
        // 1. Si la query és buida o nul·la, llança 'ConsultaInvalidaException'.
        // TODO

        // 2. Simulem un error de xarxa si la query conté paraules prohibides com 'DROP'
        // TODO

        System.out.println("[BD] Consulta executada correctament.");
    }

    public void close() throws Exception {
        System.out.println("[BD] Tancant la connexió de manera segura.");
    }
}