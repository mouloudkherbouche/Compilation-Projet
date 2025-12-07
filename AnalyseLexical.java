
import java.util.ArrayList;

public class AnalyseLexical{

    String input;

    public AnalyseLexical(String input) {
        this.input = input;
    }

    public ArrayList<Token> tokentab = new ArrayList<>();

    public void Lexer() {
        int i = 0;
        char c = input.charAt(i);
            while (c != '#') 
            {   
                if (c != ' ' && c != '\t' && c != '\n') 
                {   //line comment
                    if (c == '/' && i + 1 < input.length() &&  input.charAt(i + 1) == '/') 
                    {
                        i += 2;

                        for (c = input.charAt(i); c != '\n' && c != '#'; c = input.charAt(i)) 
                        {
                            ++i;
                        }

                    }//comment
                    else if (c == '/' && i + 1 < input.length() && input.charAt(i + 1) == '*') 
                    {
                        i += 2;
                        c = input.charAt(i);
                        while (!(c == '*' && input.charAt(i + 1) == '/'))
                        {
                            ++i;
                            c = input.charAt(i);
                            if (c == '#') 
                            {
                                break;
                            }
                        }
                        i += 2;
                        c = input.charAt(i);
                    }
                    else
                    {
                        String id = "";
                        if ((c >= 'a' && c <= 'z') ||( c >= 'A' && c <= 'Z') || c == '$' || c == '_') 
                        {

                            for (id = "";( c >= 'a' && c <= 'z') ||( c >= 'A' && c <= 'Z') ||( c >= '0' && c <= '9') || c == '$' || c == '_'; c = input.charAt(i)) 
                            {
                                id = id + c;
                                ++i;
                            }
                            
                            if (id.equals("if") || id.equals("else") || id.equals("while") || id.equals("do") || id.equals("for") || id.equals("try") || id.equals("catch") || id.equals("switch") || id.equals("case")) 
                            {
                                tokentab.add(new Token(TypeTokens.StructureDeControle, id));
                            }
                            else if (id.equals("true") || id.equals("false")) 
                            {
                                tokentab.add(new Token(TypeTokens.bool, id));
                            } 
                            else if (id.equals("let") || id.equals("const") || id.equals("var")) 
                            {
                                tokentab.add(new Token(TypeTokens.Deaclaration, id));
                            }else
                            {
                                tokentab.add(new Token(TypeTokens.idantificateur, id));
                            }
                        }
                        else if(c == '"') 
                        {    
                            i++; // Skip opening quote
                            c = input.charAt(i);
                            id = "";
                            while (c != '"') 
                            {
                                id += c;
                                i++;
                                c = input.charAt(i);
                            }
                            i++; // Skip closing quote
                            tokentab.add(new Token(TypeTokens.String, id));
                            c = input.charAt(i);
                        } 
                        else if (c == '\'') 
                        {
                            i++; // Skip opening quote
                            c = input.charAt(i);
                            id = "";
                            while (c != '\'') 
                            {
                                id += c;
                                i++;
                                c = input.charAt(i);
                            }
                            i++; // Skip closing quote
                            tokentab.add(new Token(TypeTokens.String, id));
                            c = input.charAt(i);
                        } 
                        else if (c >= '0' && c <= '9') 
                        {

                            
                            for (id = ""; c >= '0' && c <= '9' || c == '.'; c = input.charAt(i)) 
                            {
                                id += c;
                                i++;
                            }
                            tokentab.add(new Token(TypeTokens.number, id));

                        } 
                        else if (c == '=') {

                            if (input.charAt(i + 1) == '=' && i + 1 < input.length()) {
                                tokentab.add(new Token(TypeTokens.Comparaison, "=="));
                                i += 2;
                            } else {
                                tokentab.add(new Token(TypeTokens.Affectation, "="));
                                ++i;
                            }
                            c = input.charAt(i);

                        }
                        else if (c == '!') {

                            if (input.charAt(i + 1) == '=' && i + 1 < input.length()) {
                                tokentab.add(new Token(TypeTokens.Comparaison, "!="));
                                i += 2;
                            } else {
                                System.out.println("Erreur : '!' seul est invalide");
                                ++i;
                            }
                            c = input.charAt(i);

                        }
                        else if (c == '&') 
                        {

                            if (input.charAt(i + 1) == '&' && i + 1 < input.length()) 
                            {
                                tokentab.add(new Token(TypeTokens.Operateur, "&&"));
                                i += 2;
                            } 
                            else
                            {
                                tokentab.add(new Token(TypeTokens.Operateur, "&"));
                                ++i;
                            }
                            c = input.charAt(i);

                        } 
                        else if (c == '|') 
                        {

                            if (input.charAt(i + 1) == '|' && i + 1 < input.length()) 
                            {
                                tokentab.add(new Token(TypeTokens.Operateur, "||"));
                                i += 2;
                            } 
                            else
                            {
                                tokentab.add(new Token(TypeTokens.Operateur, "|"));
                                ++i;
                            }
                            c = input.charAt(i);

                        }
                        else if (c == '<') 
                        {
                            if (input.charAt(i + 1) == '=' && i + 1 < input.length()) 
                            {
                                tokentab.add(new Token(TypeTokens.Comparaison, "<="));
                                i += 2;
                            }
                            else
                            {
                                tokentab.add(new Token(TypeTokens.Comparaison, "<"));
                                ++i;
                            }
                            c = input.charAt(i);

                        }
                        else if (c == '>') 
                        {
                            if (input.charAt(i + 1) == '=' && i + 1 < input.length()) 
                            {
                                tokentab.add(new Token(TypeTokens.Comparaison, ">="));
                                i += 2;
                            } 
                            else
                            {
                                tokentab.add(new Token(TypeTokens.Comparaison, ">"));
                                ++i;
                            }
                            c = input.charAt(i);
                        }
                        else if(c == '+' && i + 1 < input.length() && input.charAt(i + 1) == '+') {
                            tokentab.add(new Token(TypeTokens.IncDec, "++"));
                            i += 2;
                            c = input.charAt(i);}
                            
                             else if (c == '-' && i + 1 < input.length() && input.charAt(i + 1) == '-') {
                             tokentab.add(new Token(TypeTokens.IncDec, "--"));
                            i += 2;
                         c = input.charAt(i);
                        } else if (c != '+' && c != '-' && c != '*' && c != '/' && c != '%') 
                        {
                            if (c != '{' && c != '}' && c != '(' && c != ')' && c != ';' && c != ',' && c!='.') 
                            {
                                System.out.println("Erreur : symbole inconnu '" + c + "'");
                                break;
                            } 
                            else
                            {
                                String tmp = "" + c;
                                tokentab.add(new Token(TypeTokens.Separateur, tmp));
                                ++i;
                                c = input.charAt(i);
                            }
                        } 
                        else 
                        {
                            String tmp = "" + c;
                            tokentab.add(new Token(TypeTokens.Operateur, tmp));
                            ++i;
                            c = input.charAt(i);

                        }
                    }
                }
                else
                {
                    ++i;
                    c = input.charAt(i);
                }
            }

            // Add EOF token
            if (tokentab.isEmpty() || !tokentab.get(tokentab.size() - 1).val.equals("#")) {
                tokentab.add(new Token(TypeTokens.EOF, "#"));
            }

            //Fin de l'analyse lexicale
        }

        
    public static void main(String[] args) {
        // Exemple d'utilisation
        String code = """
            let x = 42;
            const y = "Hello";
            if (x > 10) {
                console.log(y);
            }
            // Commentaire
            function test() {
                return x + 5;
            }
            """;
        
        AnalyseLexical lexer = new AnalyseLexical(code);
        lexer.Lexer();
        
        // Afficher les tokens
        System.out.println("Tokens générés:");
        System.out.println("Type\t\t\tValeur");
        System.out.println("--------------------------------");
        for (Token token : lexer.tokentab) {
            System.out.printf("%-20s %s%n", token.type, token.val);
        }
    }
}
    

