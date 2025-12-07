import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MiniCompilateur {
    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java MiniCompilateur <fichier_source>");
            System.out.println("Exemple: java MiniCompilateur test.txt");
            return;
        }
        
        String filename = args[0];
         
        try {
            // 1. Lire le code source
            String codeSource = lireFichier(filename);
            System.out.println("Code source lu depuis " + filename + " ===\n");
            System.out.println(codeSource);
            System.out.println("\n" + "=".repeat(50) + "\n");
            
            // 2. Analyse lexicale
            System.out.println(" L'analyse Lexical :");
            AnalyseLexical lexer = new AnalyseLexical(codeSource + "#");
            lexer.Lexer();
            
            System.out.println("\nTokens générés:");
            System.out.println("Type\t\t\tValeur");
            System.out.println("--------------------------------");
            for (Token token : lexer.tokentab) {
                System.out.printf("%-20s %s%n", token.type, token.val);
            }
            System.out.println("\n" + "=".repeat(50) + "\n");
            
            // 3. Analyse syntaxique
            System.out.println(" L'analyse Syntaxique");
            
            // Trouver toutes les instructions while
            ArrayList<String> whileStatements = trouverWhileStatements(codeSource);
            
            if (whileStatements.isEmpty()) {
                System.out.println("Aucune instruction 'while' trouvée dans le code.");
            } else {
                
                for (String whileStmt : whileStatements) {
                    System.out.println("\nAnalyse de: " + whileStmt);
                    WhileParserSimpleComplet parser = new WhileParserSimpleComplet();
                    parser.Z(whileStmt);
                }
            }
            
            System.out.println("\n" + "=".repeat(50));
            System.out.println(" Fin De La Compilation");
            
        } catch (IOException e) {
            System.err.println("Erreur: Impossible de lire le fichier " + filename);
            System.err.println("Message: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erreur lors de la compilation: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static String lireFichier(String filename) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        
        return content.toString();
    }
    
    private static ArrayList<String> trouverWhileStatements(String code) {
        ArrayList<String> statements = new ArrayList<>();
        String[] lines = code.split("\n");
        
        for (String line : lines) {
            String trimmedLine = line.trim();
            
            // Détecter les lignes contenant 'while'
            if (trimmedLine.startsWith("while") || 
                trimmedLine.contains(" while ") || 
                trimmedLine.contains("\twhile") || 
                trimmedLine.contains("while(")) {
                
                // Enlever le point-virgule final s'il existe
                String stmt = trimmedLine;
                if (stmt.endsWith(";")) {
                    stmt = stmt.substring(0, stmt.length() - 1);
                }
                statements.add(stmt);
            }
        }
        
        return statements;
    }
}