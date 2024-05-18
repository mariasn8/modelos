package es.ceu.gisi.modcomp.gic_algorithms;

import es.ceu.gisi.modcomp.gic_algorithms.exceptions.CFGAlgorithmsException;
import es.ceu.gisi.modcomp.gic_algorithms.interfaces.*;
import java.util.List;
import java.util.Set;

import java.util.*;


/**
 * Esta clase contiene la implementación de las interfaces que establecen los
 * métodos necesarios para el correcto funcionamiento del
 * proyecto de programación de la asignatura Modelos de Computación.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class CFGAlgorithms implements CFGInterface, WFCFGInterface, CNFInterface, CYKInterface {
    
    private Set<Character> noterminales=new HashSet<>();  //set es una lista q no permite elementos repetidos
    private Set<Character> terminales=new HashSet<>();
    private Character axioma=' ';
    private Map<Character, List<String>> producciones=new HashMap<>();
    private Map<String, List<Character>> inverseProd=new HashMap<>();
    private String [] [] table; 

    

    @Override
    public void addNonTerminal(char nonterminal) throws CFGAlgorithmsException {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try{
            if(Character.isUpperCase(nonterminal)){     //asegura q sea mayus, sino salta la excepcion
                if(noterminales.contains(nonterminal)){   //si ya esta en la lista noterminal, salta la excepcion
                    throw new CFGAlgorithmsException();
                }
                
                if(Character.isLowerCase(nonterminal) || Character.isDigit(nonterminal)){   //si es minus o nº salta la excepcion
                    throw new CFGAlgorithmsException();
                }
                
                noterminales.add(nonterminal);
            }
            
            else{
                throw new CFGAlgorithmsException();
            }
            
        } catch(CFGAlgorithmsException e){
            throw e;
        }
    }



    @Override   //M
    public void removeNonTerminal(char nonterminal) throws CFGAlgorithmsException {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try{
            if(noterminales.contains(nonterminal)){     //si el NT esta en la lista,
                noterminales.remove(nonterminal);       //se borra
                producciones.remove(nonterminal);
            }
            
            else{
                throw new CFGAlgorithmsException();
            }
            
        } catch(CFGAlgorithmsException e){
            throw e;
        }
        
    }



    @Override   //M
    public Set<Character> getNonTerminals() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return noterminales;
    }



    @Override   //G
    public void addTerminal(char terminal) throws CFGAlgorithmsException {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try{
            
            if(Character.isLowerCase(terminal)){
                if (terminales.contains(terminal)){
                    throw new CFGAlgorithmsException();
                }
                if(Character.isUpperCase(terminal) || Character.isDigit(terminal)){   //si es mayuscula salta una excepción
                    throw new CFGAlgorithmsException();
                }
                terminales.add(terminal);
            }
            else{
                throw new CFGAlgorithmsException();
            }
                
        } catch(CFGAlgorithmsException e){
            
            throw e;
        
        }
    }



    @Override   //G
    public void removeTerminal(char terminal) throws CFGAlgorithmsException {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        
        try {
            
            String valor = String.valueOf(terminal);
            
                
            
            if (terminales.contains(terminal)){
                
                terminales.remove(terminal);
                
                
                Iterator <Character> i = noterminales.iterator();
                while (i.hasNext()){
                
                    List <String> lista = getProductions(i.next());
                    
                    
                    if(lista.contains(String.valueOf(terminal))){
                        
                    lista.remove(String.valueOf(terminal));
                    
                    }
                    
                    
                }
                
            }
            
            else{
     
            throw new CFGAlgorithmsException();
                               
            }
            
        } catch(CFGAlgorithmsException e){
            
            throw e;
        
        }
    }



    @Override   //G
    public Set<Character> getTerminals() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        
        return terminales;

    }



    @Override   //G
    public void setStartSymbol(char nonterminal) throws CFGAlgorithmsException {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try{
            if (noterminales.contains(nonterminal)){
                this.axioma = nonterminal;
            }
            
            else{
             throw new CFGAlgorithmsException();
            }
        }
        catch(CFGAlgorithmsException e){
            throw e;
        }
    }


    
    @Override   //G
    public Character getStartSymbol() throws CFGAlgorithmsException {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try{
            if(axioma.equals(' ')){
                throw new CFGAlgorithmsException();
            }
            return axioma;
            
        } catch(CFGAlgorithmsException e){
            throw e;
        }
      
    }
    
    

    @Override   //M
    public void addProduction(char nonterminal, String production) throws CFGAlgorithmsException {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try{
            List<String> listaProds=new ArrayList<>();
            List<Character> listaChars=new ArrayList<>();
            
            if(noterminales.contains(nonterminal)){     //comprueba q el noterminal esta en la lista d no terminales

                if(production.equals("l")) {     //si nonterminal esta en la lista de noterm y la prod es l, se añade a los term
                    terminales.add('l');
                }
                
                for(int i=0; i<production.length();i++){    //recorre todo production
                    if(!noterminales.contains(production.charAt(i)) && !terminales.contains(production.charAt(i))){   //si la letra no esta en la lista d term y noterm, error
                        throw new CFGAlgorithmsException();
                    }
                }
                
                if(producciones.containsKey(nonterminal) && producciones.get(nonterminal).contains(production)){    //si production y nonterminal estan en producciones, error
                    throw new CFGAlgorithmsException();
                }
                
                if(inverseProd.containsKey(production) && inverseProd.get(production).contains(nonterminal)){
                    throw new CFGAlgorithmsException();
                }
                
                if(producciones.get(nonterminal)!=null){    //si ya hay una lista de prods para el NT, se coge esta
                    listaProds=getProductions(nonterminal);   //añade a la lista la nueva prod
                }
                
                if(inverseProd.get(production)!=null){
                    listaChars=inverseProd.get(production);     //añade la prod añadida anteriormente a la lista
                }

                listaProds.add(production);     //si llega, todas las letras son T o NT. Añade la prod a la lista
                listaChars.add(nonterminal);
                
                producciones.put(nonterminal, listaProds);  //si llega, se añade production
                inverseProd.put(production, listaChars);
                terminales.remove('l');     //borra la l de la lista de terminales
            }
            
            else{
                throw new CFGAlgorithmsException();
            }
            
        } catch (CFGAlgorithmsException e){
            throw e;
        }
    }
    
    
    
    @Override   //M 
    public boolean removeProduction(char nonterminal, String production) throws CFGAlgorithmsException {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try{
            List<String> prods=getProductions(nonterminal);     //coge las prods del NT
            
            if(noterminales.contains(nonterminal)){     //comprueba q el NT está en la lista
                if(producciones.containsKey(nonterminal) && prods.contains(production)){      //comprueba q el NT tenga una prod asociada y q production haya sido agregada antes
                    prods.remove(production);   //se borra de la lista de prods
                    producciones.put(nonterminal, prods);   //añade la lista de prods de la q se ha borrado la prod
                    return true;                            //el NT se sobreescribe al ya estar agregado de antes
                }
                
                else{
                    throw new CFGAlgorithmsException();
                }
            }
            
        } catch (CFGAlgorithmsException e){
            throw e;
        }
        return false;
    }



    @Override   //M
    public List<String> getProductions(char nonterminal) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody        
        
        return producciones.get(nonterminal);   //.get() coge el valor de la clave, en A::=SAa coge SAa

    }



    @Override   //M
    public String getProductionsToString(char nonterminal) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        List<String> listaProds=getProductions(nonterminal);    //lista de producciones
        
        if(!producciones.isEmpty()){    //comprueba q hay prods agregadas
            Collections.sort(listaProds);    //ordena alfabeticamente las producciones

            StringBuilder sb=new StringBuilder();
            if(producciones.containsKey(nonterminal)){  
                for(String str:listaProds){
                    sb.append(str).append("|");     

                }
            }

            sb.reverse();       //da la vuelta a la cadena
            sb.deleteCharAt(0);     //borra el 1º elemento, q seria el ultimo | en la cadena original
            sb.reverse();       //vuelve a darle la vuelta a la cadena
            return nonterminal+"::="+sb;
        }
        
        else{   //si no hay prods devuelve vacio
            return "";
        }

    }



    @Override //M
    public String getGrammar() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        List<Character> orden=new ArrayList<>();
        orden.addAll(noterminales);
        
        Collections.sort(orden);    //para ordenar alfabeticamente la lista de noterm
        
        StringBuilder sb=new StringBuilder();   //pasa la lista orden a String
        for(Character ch:orden){
            sb.append(getProductionsToString(ch)).append("\n");     //coge las prods hechas ya String y las pone en lineas diferentes
        }       //.append()añade lo q haya dentro de () al final de sb
        
        sb.reverse();
        sb.deleteCharAt(0);     //borra el ultimo salto de linea para q no de error
        sb.reverse();
        
        String str=sb.toString();
        return str;

    }



    @Override //M
    public void deleteGrammar() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        terminales.removeAll(terminales);
        noterminales.removeAll(noterminales);   //borra todos los elementos de las listas
        axioma=' ';     //pone el axioma en blanco
        producciones.clear();   
    }



    @Override //G
    public boolean isCFG() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        
        try{
            
            Set <Character> noterm=getNonTerminals();
            StringBuilder sb=new StringBuilder();   //pasa el set noterm a string
            for(Character ch: noterm){
                sb.append(ch);
                String variable = ch.toString();
                
                if(variable.length() > 1){      //si hay mas de 1 no terminal, error
                    throw new CFGAlgorithmsException();
                }
            }

            return true;
        } 
        catch (CFGAlgorithmsException e) {
            return false;
        }
    }



    @Override   //G
    public boolean hasUselessProductions() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override   //G
    public List<String> removeUselessProductions() { 
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override   //G
    public List<Character> removeUselessSymbols() { 
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override   //M
    public boolean hasLambdaProductions() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        String gram=getGrammar();  
        
        
        if(gram.contains("l")){    //comprueba si la gramatica contiene la l
            return true;
        }
        
        else{
            return false;       //SIN ACABAR, FALTA ELIMINAR LAS C, Q SON SIMBOLOS INUTILES
        }
    }

    

    @Override   //M
    public List<Character> removeLambdaProductions() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        /*List<Character> listaLambda=new ArrayList<>();
         
        if(inverseProd.containsKey("l")){
            listaLambda=inverseProd.get("l");   //coge el character al q esta asociada l
            inverseProd.remove("l");
        }
        
        return listaLambda;     //NO FUNCIONA BIEN, FALTAN PRODS. ELIMINAR NORMAS Y SIMBOLOS INUTILES DSPS
        */
        
        String listaProds=getGrammar(); //pasarlo a arraylist
        
        List<Character> viejo=new ArrayList<>();   //Set -> lista q no permite elementos repetidos
        List<Character>simbAnul=new ArrayList<>(); //simbolos anulables. coge los NT q tienen una produccion q hace l
        
        //parten la gramatica en distintos trozos, por lineas y producciones
        String[] listaPartida=listaProds.split("\n");   //parte el string por lineas    A::=Aa|a|B (1)    B::=Ba|b (2)  (por ej)
        ArrayList<String> listaArray=new ArrayList<>(Arrays.asList(listaPartida));  //pasa el String a ArrayList
    
        /*String xx=Arrays.toString(listaPartida);
        String[] prodsPartidas=xx.split("::=");     //separa la parte izda de la dcha de la prod
        ArrayList<String> listaProdPartida=new ArrayList<>(Arrays.asList(prodsPartidas));   //lo pasa a ArrayList
        */
        
        while(viejo!=simbAnul){
            viejo=simbAnul;
            for(){  //para todas las producciones que esten en la lista
                if(producciones.containsValue(simbAnul)){   //si el valor de la prod pertenece a los simbAnul
                    simbAnul=simbAnul+inverseProd.get("Aa");
                }
            }
        }
        
        List<Character> nuevasProds=new ArrayList<>();
        
        for(){      //para todas las prods q esten en la lista de prods
            for(int i=1;i<prods.size(); i++){   //¿?
                werf
                
                if(){   //si el lado dcho de la prod no es anulable
                    rdfg
                }
                
                else if(){   //si el lado dcho si es anulable
                    sr
                }
            }
            
            for(){      //
                e5rt
            }
        }
        
        
    }




    @Override   //G
    public boolean hasUnitProductions() { 
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override   //G
    public List<String> removeUnitProductions() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override   //M
    public void transformToWellFormedGrammar() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        
        if(hasUselessProductions()){
            removeUselessProductions();
        }
        
        if(hasLambdaProductions()){
            removeLambdaProductions();      //el metodo interior debería llamar a la gramatica almacenada
        }
        
        if(hasUnitProductions()){
            removeUnitProductions();
        }
        
        removeUselessSymbols();     //más o menos, hay q comprobarlo una vez hechos los metodos
        
    }



    @Override   //G
    public void checkCNFProduction(char nonterminal, String production) throws CFGAlgorithmsException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override   //G
    public boolean isCNF() { 
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override   //G
    public void transformIntoCNF() throws CFGAlgorithmsException { 
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override   //M     hace el algoritmo y dice si la palabra pertenece
    public boolean isDerivedUsignCYK(String word) throws CFGAlgorithmsException {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        
        //transformIntoCNF();       //coger la gramatica ya transformada en CNF
        int n=word.length();    //longitud de la palabra
        table=new String [n+1][n+1];    //crea la tabla con la misma longitud de la palabra, +1 porque deja un espacio para los nº
                        //i     //j
        
        /*for(int i=1; i<n; i++){
            table[i][0]=Integer.toString(i);    //pone nº en la columna 0  (?)
        } */
        
        /*for(int j=1; j<n; j++){
            //for(int a=0; a<n; a++)
            table[0][j]= ;   //pone word en la fila 0  (?) comprobar si van
        } */
        
        word.toCharArray();
        //Empieza el algoritmo
       for(int i=1; i<n; i++){     //i=filas
           List<Character> listaChars=new ArrayList<>();
           
           if(inverseProd.containsKey("a")){
               listaChars=inverseProd.get("a");
           }
           System.out.println(listaChars);
           
           //añadir a la matriz
        } 
        
        //inverseProd=Character, List<String>

        

       
       
       
        /*for(int j=2; j<n; j++){     //j=columnas
            for(int i=1; i<n-j+1; i++){
                casillaActual=null;
                for(int k=1; k<j-1; k++){
                    casillaActual+=fnk;
                }
            }
        } */
        return true;
    } 



    @Override   //M
    public String algorithmCYKStateToString(String word) throws CFGAlgorithmsException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        
        /*String s="";
        int n=word.length();
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                s+=table[i][j];
            }
            s+=" "+"\n";
        }
        return s; */
    }

}
