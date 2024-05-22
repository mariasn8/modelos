package es.ceu.gisi.modcomp.gic_algorithms;

import es.ceu.gisi.modcomp.gic_algorithms.exceptions.CFGAlgorithmsException;
import es.ceu.gisi.modcomp.gic_algorithms.interfaces.*;
import java.util.List;
import java.util.Set;
import java.util.logging.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


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
                
                //recorrer prods del NT y si el NT esta en ellas borrarlo
                for(Character ch:noterminales){
                    List<String> listaNoterm = getProductions(ch);      //prods de un no terminal

                    Iterator<String> listaIt = listaNoterm.iterator();
                    while (listaIt.hasNext()) {
                        String prod = listaIt.next();       //String de la parte dcha de una prod, en A::=ABa sería el ABa
                        
                        if (prod.equals(String.valueOf(nonterminal))) {      //para borrar una letra sola
                            listaIt.remove();  
                        
                        } else {
                            char [] prodDividida=prod.toCharArray();    //pasa la parte dcha de la prod a un array de chars
                            for (char c : prodDividida) {   //recorre el array y si alguna letra coincide, la borra
                                if (c == nonterminal) {
                                    listaIt.remove();
                                    break;      //si borra, para y vuelve al while
                                }
                            }
                        }
                    }
                
                }
            }
            
            else{
                throw new CFGAlgorithmsException();
            }
            
        }catch(CFGAlgorithmsException e){
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
                
       try {
        if (terminales.contains(terminal)) {
            terminales.remove(terminal);

            Iterator<Character> i = noterminales.iterator();
            while (i.hasNext()) {
                Character noTerminal = i.next();    //pasa al siguiente elemento
                List<String> lista = getProductions(noTerminal);
                // hay que utilizar el iterador ya que no esta permitido en java borrar en una lista mientras está iterando.
                Iterator<String> listIterator = lista.iterator();
                while (listIterator.hasNext()) {
                    String produccion = listIterator.next();

                    if (produccion.equals(String.valueOf(terminal))) {      //para una letra sola
                        listIterator.remove();
                    } else {    //para un conjunto de letras que contenga a terminal
                        char[] charArray = produccion.toCharArray();
                        for (char c : charArray) {
                            if (c == terminal) {
                                listIterator.remove();
                                break;      //si borra, para y vuelve al while
                            }
                        }
                    }
                }
            }
        } else {
            throw new CFGAlgorithmsException();
        }
    } catch (CFGAlgorithmsException e) {
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
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
      
        ArrayList<Character> listanoterminales = new ArrayList<>(noterminales);
         
        Character noterminal= null;
        List<String> producciones;
        
        int j;
        int i;
        for (i=0;i<noterminales.size();i++) {

        
            noterminal=listanoterminales.get(i);
            producciones = getProductions(noterminal);
        
            
            if(producciones!=null){

                ArrayList<String>produccionesord = new ArrayList<>(producciones);

                for(j=0;j<produccionesord.size();j++){
                    
                    
                    
                    if(produccionesord.get(j).equals(noterminal.toString())){

                      
                       return true;
                    }
                }
            }
        } 
        return false;
    }



    @Override   //G
    public List<String> removeUselessProductions() { 
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody }
        
        ArrayList<Character> listanoterminales = new ArrayList<>(noterminales);
        List<String> listafinal = new ArrayList<>();
        Character noterminal= null;

        List<String> producciones;

        int j;
        int i;
        for (i=0;i<noterminales.size();i++) {

            noterminal=listanoterminales.get(i);
            producciones = getProductions(noterminal);

            if(producciones!=null){

                ArrayList<String>produccionesord = new ArrayList<>(producciones);

                for(j=0;j<produccionesord.size();j++){  

                    if(produccionesord.get(j).equals(noterminal.toString())){

                        try {
                            if(listafinal.size()<1){
                                listafinal.add(producciones.get(j)+"::="+noterminal.toString());
                            }
                            else{
                                listafinal.add("|"+noterminal.toString());
                            }
                            
                            removeProduction(noterminal,producciones.get(j));
                        } catch (CFGAlgorithmsException ex) {
                            Logger.getLogger(CFGAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            }
        } 
        return listafinal;
    }



    @Override   //G  q use los los algortimos 1 y2 
    public List<Character> removeUselessSymbols() { 
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override   //M
    public boolean hasLambdaProductions() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        String gram=getGrammar();  
        
        return gram.contains("l"); //comprueba si la gramatica contiene la l
        //SIN ACABAR, FALTA ELIMINAR LAS C, Q SON SIMBOLOS INUTILES
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
        
        /*while(viejo!=simbAnul){
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
        } */
        return viejo;
        
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
        try{
            //transformIntoCNF();       //coger la gramatica ya transformada en CNF
            int n=word.length();    //longitud de la palabra
            table=new String [n+1][n];    //crea la tabla con la misma longitud de la palabra, +1 porque deja un espacio para word
                            //j     //i
                        //j=filas   i=columnas

            //pone las letras de word en la 1ª fila de la tabla
            for(int i=0; i<n; i++){
                char letrasWord=word.charAt(i);     //pasa word de string a char
                table[0][i]=Character.toString(letrasWord);
            }
            
            for(int i=0; i<n; i++){     //para recorrer word
                if(Character.isUpperCase(word.charAt(i))){      //si el caracter de la posicion i de word es mayus, eroro
                    throw new CFGAlgorithmsException();
                }
            }

            //Empieza el algoritmo
            for(int i=1; i<n+1; i++){     //i=filas     n+1 para que se ejecute n veces, ya q i empieza a 1
                List<Character> listaChars=new ArrayList<>();

                if(inverseProd.containsKey(table[0][i-1])){
                    listaChars=inverseProd.get(table[0][i-1]);      //coge las letras q la producen
                }

                StringBuilder sb=new StringBuilder();
                for(Character ch: listaChars){
                    sb.append(ch).append(", ");
                }

                if(sb.isEmpty()){
                    throw new CFGAlgorithmsException();
                }

                sb.reverse();
                sb.deleteCharAt(0).deleteCharAt(0);  //borra el ultimo ", "
                sb.reverse(); 
                //sb.append("|\t");

                String str=sb.toString();
                //System.out.print(str+"\n");

                table [1][i-1]=str;      //pone str en la 1ª fila de la tabla
            }

            //2ª parte del algoritmo
            for(int j=2; j<=n; j++){     //avanza de fila       <= porque si no falta por ejecutarse 1 vez
                for(int i=1; i<=n-j+1; i++){     //avanza en las columnas de la fila
                    table[j][i-1]="";       //casilla actual, vacía

                    for(int k=1; k<=j-1; k++){

                        String [] arriba=table[k][i-1].split(", ");   //al separarlo por ", " ya se divide para el array
                        String [] diag=table[j-k][i-1+k].split(", ");

                        for (String a : arriba) {   //recorre los elementos de la casilla de arriba
                            for (String d : diag) { //recorre los de la casilla en diagonal arriba a la dcha
                                String combo = a + d; //combina la 1ª letra de la 1ª casilla con las de la diagonal

                                if(inverseProd.containsKey(combo)){
                                    //table[j][i-1]+=inverseProd.get(combo);
                                    List <Character>lista=inverseProd.get(combo);

                                    //StringBuilder sb=new StringBuilder();
                                    for(Character ch: lista){       //para todo character de lista:
                                        //sb.append(ch).append(", ");
                                        if(!table[j][i-1].contains(ch.toString())){ //si la casilla no contiene el character que hay en la lista,
                                            table[j][i-1]+=ch+", ";                 //se añade a la tabla
                                        }
                                    }   
                                }
                            }
                        }

                        if(table[j][i-1].endsWith(", ") && table[j][i-1].length()<4){   //borra el ultimo ", " cndo solo hay q poner una letra en la tabla
                        table[j][i-1]=table[j][i-1].substring(0, table[j][i-1].length()-2); 
                        }       //el substring muestra todos los elementos del String menos los 2 ultimos (", ") 
                    }
                    if(table[j][i-1].isBlank()){
                            table[j][i-1]="0";      //PARA EL CONJUNTO VACIO VAMOS A USAR EL 0
                    }

                    if(table[j][i-1].endsWith(", ")){   //borra el ultimo ", " de todo el string
                        table[j][i-1]=table[j][i-1].substring(0, table[j][i-1].length()-2); 
                    }
                }
            }

            /*for (int j=0; j<table.length; j++) {    //j=filas
                for (int i=0; i<table[j].length; i++) {     //i=columnas
                    System.out.print(table[j][i] + " | ");

                }
                System.out.println();

            } */
            algorithmCYKStateToString(word);
            return table[n][0].contains(Character.toString(axioma));      //devuelve true o false
        
        }catch(CFGAlgorithmsException e){
            throw e;
        }

    } 



    @Override   //M
    public String algorithmCYKStateToString(String word) throws CFGAlgorithmsException {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        
        String x="";
        for (int j=0; j<table.length; j++) {    //j=filas
            for (int i=0; i<table[j].length; i++) {     //i=columnas
                //System.out.print(table[j][i] + " | ");
                x+=table[j][i]+" | ";
            }
            //System.out.println();
            x=x.substring(0, x.length()-3)+"\n";    //borra el ulitmo " | "
        }
        
        System.out.print(x);
        System.out.println();
        return x;
    }
    
}
     
