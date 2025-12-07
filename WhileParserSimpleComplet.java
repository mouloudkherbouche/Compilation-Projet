import java.util.Scanner;

public class WhileParserSimpleComplet {
    
    private int i;
    private String chaine;
    private boolean error;
    
    public void Z(String input) {
        chaine = input + "#";
        i = 0;
        error = false;
        S();
        
        if (chaine.charAt(i) == '#' && error == false) {
            System.out.println("Chaine acceptée");
        } else {
            System.err.println("non accepté");
        }
    }
    
    // S → while ( E ) B
    public void S() {
        // while
        if (chaine.charAt(i) == 'w' && chaine.charAt(i+1) == 'h' && 
            chaine.charAt(i+2) == 'i' && chaine.charAt(i+3) == 'l' && 
            chaine.charAt(i+4) == 'e') {
            i = i + 5;
            
            // espaces
            while (chaine.charAt(i) == ' ') i++;
            
            // (
            if (chaine.charAt(i) == '(') {
                i++;
                while (chaine.charAt(i) == ' ') i++;
                
                // E (expression logique complète)
                E();
                
                while (chaine.charAt(i) == ' ') i++;
                
                // )
                if (chaine.charAt(i) == ')') {
                    i++;
                    while (chaine.charAt(i) == ' ') i++;
                    
                    // B (bloc)
                    B();
                } else {
                    System.err.println("Erreur: ) attendu");
                    error = true;
                }
            } else {
                System.err.println("Erreur: ( attendu");
                error = true;
            }
        } else {
            System.err.println("Erreur: while attendu");
            error = true;
        }
    }
    
    // B → ; | { L }
    public void B() {
        while (chaine.charAt(i) == ' ') i++;
        
        if (chaine.charAt(i) == ';') {
            i++;
        } else if (chaine.charAt(i) == '{') {
            i++;
            while (chaine.charAt(i) == ' ') i++;
            
            L();
            
            while (chaine.charAt(i) == ' ') i++;
            
            if (chaine.charAt(i) == '}') {
                i++;
            } else {
                System.err.println("Erreur: } attendu");
                error = true;
            }
        } else {
            System.err.println("Erreur: ; ou { attendu");
            error = true;
        }
    }
    
    // L → I L | ε
    public void L() {
        while (i < chaine.length() && chaine.charAt(i) == ' ') i++;
        
        // Condition d'arrêt
        if (i >= chaine.length() || chaine.charAt(i) == '}') {
            return;
        }
        I();
        if (i < chaine.length() && chaine.charAt(i) != '}') {
            L();
        }
    }
    
    // I → ident OP= E ; | ident = E ; | ident ; | ident++ ; | ident-- ; | ++ident ; | --ident ;
    public void I() {
        while (chaine.charAt(i) == ' ') i++;
        
        // variable (identificateur)
        if ((chaine.charAt(i) >= 'a' && chaine.charAt(i) <= 'z') || 
            (chaine.charAt(i) >= 'A' && chaine.charAt(i) <= 'Z')) {
            
            // lire variable
            while ((chaine.charAt(i) >= 'a' && chaine.charAt(i) <= 'z') || 
                   (chaine.charAt(i) >= 'A' && chaine.charAt(i) <= 'Z') ||
                   (chaine.charAt(i) >= '0' && chaine.charAt(i) <= '9') ||
                   chaine.charAt(i) == '_') {
                i++;
            }
            
            while (chaine.charAt(i) == ' ') i++;
            
            // ++ ou -- après (post-incrément/décrément)
            if (chaine.charAt(i) == '+' && chaine.charAt(i+1) == '+') {
                i = i + 2;
                while (chaine.charAt(i) == ' ') i++;
                
                if (chaine.charAt(i) == ';') {
                    i++;
                } else {
                    System.err.println("Erreur: ; attendu après ++");
                    error = true;
                }
                return;
            }
            
            if (chaine.charAt(i) == '-' && chaine.charAt(i+1) == '-') {
                i = i + 2;
                while (chaine.charAt(i) == ' ') i++;
                
                if (chaine.charAt(i) == ';') {
                    i++;
                } else {
                    System.err.println("Erreur: ; attendu après --");
                    error = true;
                }
                return;
            }
            
            // Opérateurs d'affectation composés
            if (chaine.charAt(i) == '+' && chaine.charAt(i+1) == '=') {
                i = i + 2;
                while (chaine.charAt(i) == ' ') i++;
                
                E();
                
                while (chaine.charAt(i) == ' ') i++;
                
                if (chaine.charAt(i) == ';') {
                    i++;
                } else {
                    System.err.println("Erreur: ; attendu");
                    error = true;
                }
                return;
            }
            
            if (chaine.charAt(i) == '-' && chaine.charAt(i+1) == '=') {
                i = i + 2;
                while (chaine.charAt(i) == ' ') i++;
                
                E();
                
                while (chaine.charAt(i) == ' ') i++;
                
                if (chaine.charAt(i) == ';') {
                    i++;
                } else {
                    System.err.println("Erreur: ; attendu");
                    error = true;
                }
                return;
            }
            
            if (chaine.charAt(i) == '*' && chaine.charAt(i+1) == '=') {
                i = i + 2;
                while (chaine.charAt(i) == ' ') i++;
                
                E();
                
                while (chaine.charAt(i) == ' ') i++;
                
                if (chaine.charAt(i) == ';') {
                    i++;
                } else {
                    System.err.println("Erreur: ; attendu");
                    error = true;
                }
                return;
            }
            
            if (chaine.charAt(i) == '/' && chaine.charAt(i+1) == '=') {
                i = i + 2;
                while (chaine.charAt(i) == ' ') i++;
                
                E();
                
                while (chaine.charAt(i) == ' ') i++;
                
                if (chaine.charAt(i) == ';') {
                    i++;
                } else {
                    System.err.println("Erreur: ; attendu");
                    error = true;
                }
                return;
            }
            
            // = simple
            if (chaine.charAt(i) == '=') {
                i++;
                while (chaine.charAt(i) == ' ') i++;
                
                E();
                
                while (chaine.charAt(i) == ' ') i++;
                
                if (chaine.charAt(i) == ';') {
                    i++;
                } else {
                    System.err.println("Erreur: ; attendu");
                    error = true;
                }
                return;
            } 
            // juste variable;
            else if (chaine.charAt(i) == ';') {
                i++;
                return;
            } else {
                System.err.println("Erreur: =, +=, -=, *=, /=, ++, -- ou ; attendu");
                error = true;
                return;
            }
        } 
        // ++ident ou --ident (pré-incrément/décrément)
        else if (chaine.charAt(i) == '+' && chaine.charAt(i+1) == '+') {
            i = i + 2;
            while (chaine.charAt(i) == ' ') i++;
            
            // variable après ++
            if ((chaine.charAt(i) >= 'a' && chaine.charAt(i) <= 'z') || 
                (chaine.charAt(i) >= 'A' && chaine.charAt(i) <= 'Z')) {
                
                while ((chaine.charAt(i) >= 'a' && chaine.charAt(i) <= 'z') || 
                       (chaine.charAt(i) >= 'A' && chaine.charAt(i) <= 'Z') ||
                       (chaine.charAt(i) >= '0' && chaine.charAt(i) <= '9') ||
                       chaine.charAt(i) == '_') {
                    i++;
                }
                
                while (chaine.charAt(i) == ' ') i++;
                
                if (chaine.charAt(i) == ';') {
                    i++;
                } else {
                    System.err.println("Erreur: ; attendu");
                    error = true;
                }
            } else {
                System.err.println("Erreur: variable attendue après ++");
                error = true;
            }
            return;
        }
        else if (chaine.charAt(i) == '-' && chaine.charAt(i+1) == '-') {
            i = i + 2;
            while (chaine.charAt(i) == ' ') i++;
            
            // variable après --
            if ((chaine.charAt(i) >= 'a' && chaine.charAt(i) <= 'z') || 
                (chaine.charAt(i) >= 'A' && chaine.charAt(i) <= 'Z')) {
                
                while ((chaine.charAt(i) >= 'a' && chaine.charAt(i) <= 'z') || 
                       (chaine.charAt(i) >= 'A' && chaine.charAt(i) <= 'Z') ||
                       (chaine.charAt(i) >= '0' && chaine.charAt(i) <= '9') ||
                       chaine.charAt(i) == '_') {
                    i++;
                }
                
                while (chaine.charAt(i) == ' ') i++;
                
                if (chaine.charAt(i) == ';') {
                    i++;
                } else {
                    System.err.println("Erreur: ; attendu");
                    error = true;
                }
            } else {
                System.err.println("Erreur: variable attendue après --");
                error = true;
            }
            return;
        }
        else {
            System.err.println("Erreur: instruction attendue");
            error = true;
        }
    }
    
    // E → E2 (|| E2)*
    public void E() {
        E2();
        
        while (chaine.charAt(i) == ' ') i++;
        
        while (chaine.charAt(i) == '|' && chaine.charAt(i+1) == '|') {
            i = i + 2;
            while (chaine.charAt(i) == ' ') i++;
            E2();
            while (chaine.charAt(i) == ' ') i++;
        }
    }
    
    // E2 → E3 (&& E3)*
    public void E2() {
        E3();
        
        while (chaine.charAt(i) == ' ') i++;
        
        while (chaine.charAt(i) == '&' && chaine.charAt(i+1) == '&') {
            i = i + 2;
            while (chaine.charAt(i) == ' ') i++;
            E3();
            while (chaine.charAt(i) == ' ') i++;
        }
    }
    
    // E3 → R ((< | > | <= | >= | == | !=) R)*
    public void E3() {
        R();
        
        while (chaine.charAt(i) == ' ') i++;
        
        while (true) {
            if (chaine.charAt(i) == '<') {
                if (chaine.charAt(i+1) == '=') {
                    i = i + 2;
                } else {
                    i++;
                }
                while (chaine.charAt(i) == ' ') i++;
                R();
            }
            else if (chaine.charAt(i) == '>') {
                if (chaine.charAt(i+1) == '=') {
                    i = i + 2;
                } else {
                    i++;
                }
                while (chaine.charAt(i) == ' ') i++;
                R();
            }
            else if (chaine.charAt(i) == '=' && chaine.charAt(i+1) == '=') {
                i = i + 2;
                while (chaine.charAt(i) == ' ') i++;
                R();
            }
            else if (chaine.charAt(i) == '!' && chaine.charAt(i+1) == '=') {
                i = i + 2;
                while (chaine.charAt(i) == ' ') i++;
                R();
            }
            else {
                break;
            }
            
            while (chaine.charAt(i) == ' ') i++;
        }
    }
    
    // R → T ((+ | -) T)*
    public void R() {
        T();
        
        while (chaine.charAt(i) == ' ') i++;
        
        while (chaine.charAt(i) == '+' || chaine.charAt(i) == '-') {
            i++;
            while (chaine.charAt(i) == ' ') i++;
            T();
            while (chaine.charAt(i) == ' ') i++;
        }
    }
    
    // T → F ((* | /) F)*
    public void T() {
        F();
        
        while (chaine.charAt(i) == ' ') i++;
        
        while (chaine.charAt(i) == '*' || chaine.charAt(i) == '/') {
            i++;
            while (chaine.charAt(i) == ' ') i++;
            F();
            while (chaine.charAt(i) == ' ') i++;
        }
    }
    
    // F → (E) | ident | nombre
    public void F() {
        while (chaine.charAt(i) == ' ') i++;
        
        // (E)
        if (chaine.charAt(i) == '(') {
            i++;
            while (chaine.charAt(i) == ' ') i++;
            
            E();
            
            while (chaine.charAt(i) == ' ') i++;
            
            if (chaine.charAt(i) == ')') {
                i++;
            } else {
                System.err.println("Erreur: ) attendu");
                error = true;
            }
        }
        // variable
        else if ((chaine.charAt(i) >= 'a' && chaine.charAt(i) <= 'z') || 
                 (chaine.charAt(i) >= 'A' && chaine.charAt(i) <= 'Z')) {
            
            while ((chaine.charAt(i) >= 'a' && chaine.charAt(i) <= 'z') || 
                   (chaine.charAt(i) >= 'A' && chaine.charAt(i) <= 'Z') ||
                   (chaine.charAt(i) >= '0' && chaine.charAt(i) <= '9') ||
                   chaine.charAt(i) == '_') {
                i++;
            }
        }
        // nombre (entier ou flottant)
        else if (chaine.charAt(i) >= '0' && chaine.charAt(i) <= '9') {
            while (chaine.charAt(i) >= '0' && chaine.charAt(i) <= '9') {
                i++;
            }
            
            // partie décimale
            if (chaine.charAt(i) == '.') {
                i++;
                if (chaine.charAt(i) >= '0' && chaine.charAt(i) <= '9') {
                    while (chaine.charAt(i) >= '0' && chaine.charAt(i) <= '9') {
                        i++;
                    }
                } else {
                    System.err.println("Erreur: chiffre attendu après .");
                    error = true;
                }
            }
        }
        else {
            System.err.println("Erreur: facteur attendu (variable, nombre ou (expression))");
            error = true;
        }
    }
    
    public static void main(String[] args) {
        if (args.length > 0) {
            // Mode fichier
            try {
                String filename = args[0];
                String content = new String(java.nio.file.Files.readAllBytes(
                    java.nio.file.Paths.get(filename)));
                
                String[] lines = content.split("\n");
                WhileParserSimpleComplet parse;
                
                for (String line : lines) {
                    String trimmed = line.trim();
                    if (!trimmed.isEmpty() && !trimmed.startsWith("//")) {
                        if (trimmed.startsWith("while") || trimmed.contains("while(")) {
                            parse = new WhileParserSimpleComplet();
                            System.out.println("\n=== Analyse de: " + trimmed + " ===");
                            parse.Z(trimmed);
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Erreur: " + e.getMessage());
            }
        } else {
            // Mode interactif
            Scanner sc = new Scanner(System.in);
            
            System.out.println("=== Analyseur While avec expressions complexes ===");
            System.out.println("Exemples acceptés:");
            System.out.println("  while (x < 10 && y > 5) { x++; }");
            System.out.println("  while ((a + b) * 2 < 50 || c == 0) { c += 2; }");
            System.out.println("  while (i <= 100) { sum += i * i; i++; }");
            System.out.println("\nÉcrire une instruction while (ou 'exit' pour quitter):");
            
            while (true) {
                System.out.print("\n> ");
                String input = sc.nextLine();
                
                if (input.equalsIgnoreCase("exit")) break;
                
                WhileParserSimpleComplet parse = new WhileParserSimpleComplet();
                parse.Z(input);
            }
            
            sc.close();
        }
    }
}