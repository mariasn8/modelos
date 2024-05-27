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
        
        List<String> produccion;
        
        int j;
        int i;
        for (i=0;i<noterminales.size();i++) {
        
            noterminal=listanoterminales.get(i);
            produccion = getProductions(noterminal);

            if(produccion!=null){

                ArrayList<String>produccionesord = new ArrayList<>(produccion);

                for(j=0;j<produccionesord.size();j++){
                    
                    if(produccionesord.get(j).equals(noterminal.toString())){
                       
                       
                       if(listafinal.size()<1){
                       listafinal.add(produccion.get(j)+"::="+noterminal.toString());
                       }
                       else{
                       listafinal.add("|"+noterminal.toString());
                       }
                       
                        try {
                            removeProduction(noterminal,produccion.get(j));
                        } catch (CFGAlgorithmsException ex) {
                            
                        } 
                    }
                }
            }
            
        } 
      return listafinal;
    }
    //metodo implementado por el alumno
    public List<Character> eliminarTerminalesInutiles(){
    
    int contadorB;
        ArrayList<Character>terminalesEliminados = new ArrayList<>();
        ArrayList<Character>listaNoTerminales = new ArrayList<>(noterminales);
        ArrayList<Character>listaTerminales = new ArrayList<>(terminales);
        for( int i=0;i<terminales.size();i++){
            contadorB = 0;
            
            Character terminal=listaTerminales.get(i);
            
            
            for (int j=0;j<noterminales.size();j++){
                Character noterminal = listaNoTerminales.get(j);
                //System.out.println(terminal+ " is in "+noterminal+"?");
                
                if(getProductions(noterminal)!= null){
               
                    List<String> produccion = new ArrayList<>(getProductions(noterminal));
                    //System.out.println(produccion);
                    for(int z =0; z<produccion.size();z++){
                       String oneProd = produccion.get(z);
                       //System.out.println(oneProd);

                        for(int y = 0; y<oneProd.length();y++){

                            Character comparador = oneProd.charAt(y);
                            //System.out.println(comparador+" is equal "+terminal+"??");
                            if(comparador.equals(terminal)){
                                contadorB++;

                            }
                            

                        }

                    }
                }
                
            }
            if(contadorB == 0){
                terminalesEliminados.add(terminal);
                terminales.remove(terminal);
            }
        
        }
        return terminalesEliminados;
        
    }
    
    //Siempre se puede terminar
    public List<Character> UcanFinish(){
    int contador;
    ArrayList<Character>NoterminalesEliminados = new ArrayList<>();
    ArrayList<Character>listaNoTerminales = new ArrayList<>(noterminales);
    
        for(int i = 0; i<noterminales.size();i++){
        contador = 0;
    
        Character noterminal = listaNoTerminales.get(i); 
        //System.out.println(" - "+noterminal+" ----------------------------------------");    
            if(getProductions(noterminal)!= null){
                
                List<String> produccion = new ArrayList<>(getProductions(noterminal));
                for(int z =0; z<produccion.size();z++){
                    String oneProd = produccion.get(z);
                    //System.out.println(oneProd);
                    if(oneProd.length()==1 && !noterminales.contains(oneProd.charAt(0))){
                        //System.out.println("STOP "+oneProd.charAt(0));
                        contador++;
                        
                    }
                    else if(oneProd.length()==1 && noterminales.contains(oneProd.charAt(0)) && !noterminales.equals(oneProd.charAt(0))){
                        //System.out.println("STOP "+oneProd.charAt(0));
                        contador++;
                        
                    }
                    else{
                        if(oneProd.length()==1){
                        //System.out.println("CONTINUE "+oneProd.charAt(0));
                        }
                        else{
                            for(int y = 0; y<oneProd.length();y++){
                                Character comparador = oneProd.charAt(y);
                                
                                if(!comparador.equals(noterminal)&& noterminales.contains(comparador)){
                                    //System.out.println("STOP");
                                    contador++;
                                    
                                }
                                else{
                                   // System.out.println("CONTINUE " + comparador);
                                }  
                            }   
                        }
                    }
                }
            }
            if(contador==0){
            //System.out.println("BORRAR");
            try{
                removeNonTerminal(noterminal);
                
                }
            catch (CFGAlgorithmsException e) {
                       
                }
            }  
        }
        //System.out.println(noterminales);
        return NoterminalesEliminados;
    }
   

    @Override   //G 
    public List<Character> removeUselessSymbols() {


        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        ArrayList<Character> NoterminalesEliminados2 = new ArrayList<>();
        eliminarTerminalesInutiles();
        UcanFinish();
        List<Character> listafinal = new ArrayList<>();


        //itera en cada noterminal
        for (int i = 0; i < noterminales.size(); i++) {

            ArrayList<Character> listanoterminales = new ArrayList<>(noterminales);


            Character noterminal2 = listanoterminales.get(i);

            if (getProductions(noterminal2) == null) {
                try {
                    removeNonTerminal(noterminal2);
                    break;
                } catch (CFGAlgorithmsException e) {

                }
            }
            List<String> produccion2 = new ArrayList<>(getProductions(noterminal2));

            for (int j = 0; j < noterminales.size(); j++) {
                Character noterminal = listanoterminales.get(j);
                if (getProductions(noterminal) == null) {
                    try {
                        removeNonTerminal(noterminal);
                        break;

                    } catch (CFGAlgorithmsException e) {

                    }
                }
                List<String> produccion = new ArrayList<>(getProductions(noterminal));


                //eliminar casos normales
                if (!noterminal.equals(noterminal2)) {

                    //System.out.println(noterminal+" "+ produccion);
                    //System.out.println(noterminal2+" "+ produccion2);

                    if (produccion == null) {


                        //System.out.println(produccion);
                        //System.out.println(produccion2);

                        if (produccion.equals(produccion2)) {

                            if (noterminal.equals(axioma)) {

                                try {
                                    removeNonTerminal(noterminal2);
                                } catch (CFGAlgorithmsException e) {
                                }
                            } else if (noterminal2.equals(axioma)) {

                                try {
                                    removeNonTerminal(noterminal);
                                } catch (CFGAlgorithmsException e) {

                                    try {
                                        removeNonTerminal(noterminal2);
                                    } catch (CFGAlgorithmsException ex) {

                                    }
                                }
                            } else if (noterminal2.equals(axioma)) {

                                try {
                                    removeNonTerminal(noterminal);
                                } catch (CFGAlgorithmsException e) {

                                }
                            }
                        } else {

                            try {
                                removeNonTerminal(noterminal);
                            } catch (CFGAlgorithmsException e) {

                            }
                        }

                    }
                }
            }
        }

        return NoterminalesEliminados2;

    }


    @Override   //M
    public boolean hasLambdaProductions() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        removeUselessSymbols();
        String gram=getGrammar();
        
        return gram.contains("l"); //comprueba si la gramatica contiene la l
    }

    

    @Override   //M
    public List<Character> removeLambdaProductions() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        /*List<Character> listaLambda=new ArrayList<>();
         
        if(inverseProd.containsKey("l")){
            listaLambda=inverseProd.get("l");   //coge el character al q esta asociada l
            inverseProd.remove("l");
        }
        
        return listaLambda;
        */
                        
        List<Character> viejo=new ArrayList<>();   //Set -> lista q no permite elementos repetidos
        List<Character>simbAnul=new ArrayList<>(); //simbolos anulables. coge los NT q tienen una produccion q hace l
        
        //mete los NT en simbAnul q generan l directamente
        for(Character noterm: producciones.keySet()){      //keySet devuelve un set con todas las claves del hashmap
            List<String> prodsNoterm=producciones.get(noterm);  //coge la parte dcha de la prod
            for(String prod: prodsNoterm){      //va recorriendo las partes dchas de la prod
                if(prod.equals("l")){
                    simbAnul.add(noterm);
                }
            }
        }
        
        //1ª parte      los NT q pden derivar en lambda
        while(!viejo.equals(simbAnul)){
            //viejo.addAll(simbAnul);
            viejo=new ArrayList<>(simbAnul);
            for(Character noterm: producciones.keySet()){  //recorrer las prods y si la parte dcha es producida por alguno de simbAnul
                List<String> prodsNoterm=producciones.get(noterm);  //meter la parte izda en simbAnul tbm
                for(String prod: prodsNoterm){
                    
                    for(int i=0; i<prod.length(); i++){     //recorre prod
                        char x=prod.charAt(i);
                        if(simbAnul.contains(x) && !simbAnul.contains(noterm)){
                            simbAnul.add(noterm);
                        }
                    }
                }
            }
        }
        
        //System.out.println(simbAnul);
        List<String> prodsComb=new ArrayList<>();
        
        //2ªparte del algoritmo
        for(Character noterm: producciones.keySet()){  //recorrer las prods y si la parte dcha es producida por alguno de simbAnul
            List<String> prodsNoterm=producciones.get(noterm);  //meter la parte izda en simbAnul tbm
            for(String prod: prodsNoterm){

                List<String> combinar=combos(prod, simbAnul);
                for(String str: combinar){
                    prodsComb.add(noterm, str);
                }
            }
        }
        List<String>lambda=new ArrayList<>();
        lambda.add("l");
        
        if(simbAnul.contains(axioma) && !getProductions(axioma).contains("l")){      //añade S::=l si no existe
            producciones.put(axioma, lambda);
        }
        
        return simbAnul;

        
    }

    private List<String> combos(String prod, List<Character> simbAnul){
        List<String> lista=new ArrayList<>();
        if(prod.length()==1){
            if(simbAnul.contains(prod.charAt(0))){  //si la letra de la prod esta en simbAnul, se cambia por l
                lista.add("l");
            }
            else{
                lista.add(prod);    //sino, se deja igual
            }
        }
        else{
            for(int i=0; i<prod.length(); i++){    //recorre prod
                char x=prod.charAt(i);
                if(simbAnul.contains(x)){
                    String prod2=prod.substring(0, i)+prod.substring(i+1);
                    lista.add(prod2);
                }
            }
        }
        return lista;
    }




    @Override   //G
    public boolean hasUnitProductions() { 
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        ArrayList<Character> listanoterminales = new ArrayList<>(noterminales);
        for (int i=0;i<noterminales.size();i++) {
         
            Character noterminal= null;
            List<String> produccion = new ArrayList<>();
          
            noterminal =listanoterminales.get(i);
            produccion = getProductions(noterminal);

                //System.out.println(noterminal + " " + produccion);
                
            if(produccion != null){

                for(int j=0;j<produccion.size();j++){

                    int contador=0;

                    //System.out.println(produccion.get(j));
                    String produccionIndividual = produccion.get(j);

                    if(produccionIndividual.length() == 1){

                       if(noterminales.contains(produccionIndividual.charAt(0))){

                           return true;

                       }

                    }
                    if(produccionIndividual.length() > 1){
                        for(int k = 0; k<produccionIndividual.length();k++){

                            if(noterminales.contains(produccionIndividual.charAt(k))){

                                contador ++;
                            }
                        }

                    }
                    if(contador == 1){

                        return true;

                    }
                }
            }
        }
       
         return false;
        
    }



    @Override   //G
    public List<String> removeUnitProductions() {
                //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    Character noterminal = null;
    
    List<String> produccion = new ArrayList<>();
    
    ArrayList<Character> listanoterminales = new ArrayList<>(noterminales);
    
    for (int i = 0; i < noterminales.size(); i++) {

            noterminal = listanoterminales.get(i);
            produccion = getProductions(noterminal);

            //System.out.println(noterminal + " " + produccion);

            if (produccion != null) {

                for (int j = 0; j < produccion.size(); j++) {

                    String produccionIndividual = produccion.get(j);
                    
                    if (produccionIndividual.length() == 1) {

                        if (noterminales.contains(produccionIndividual.charAt(0))) {
                   
                            try {

                                removeProduction(noterminal, produccionIndividual);
                                
                            } catch (CFGAlgorithmsException e) {
                               
                            }

                            
                            Character unitaria = produccionIndividual.charAt(0);
                            
                            List<String> produccionesUnitarias = getProductions(unitaria);
                            
                            if (produccionesUnitarias != null) {
                             for (String prod : produccionesUnitarias) {
                                    
                                    try {
                                        
                                        addProduction(noterminal, prod);
                                        
                                    } catch (CFGAlgorithmsException e) {
                                        
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return produccion;
    }



    @Override   //M
    public void transformToWellFormedGrammar() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        
        if(hasUselessProductions()){
            removeUselessProductions();
        }
        
        if(hasLambdaProductions()){
            removeLambdaProductions();
        }
        
        if(hasUnitProductions()){
            removeUnitProductions();
        }
        
        removeUselessSymbols();     //más o menos, hay q comprobarlo una vez hechos los metodos
        
    }

    @Override   //G
    public void checkCNFProduction(char nonterminal, String production) throws CFGAlgorithmsException {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try{
            ArrayList<Character> listanoterminales = new ArrayList<>(noterminales);
            ArrayList<Character> listaterminales = new ArrayList<>(terminales);
            
            Character simboloinicial = getStartSymbol();
            String minuscula = production.toLowerCase();
            String mayuscula = production.toUpperCase();
            if(noterminales.contains(nonterminal)){
                if(production.length() == 1 && production.equals(minuscula)){
                    if(!terminales.contains(production)) {
                        if (nonterminal != simboloinicial) {
                            //System.out.println("no pasa");
                            throw new CFGAlgorithmsException();
                        }
                        //System.out.println("pasa");
                    }
                }
                else if(production.length() == 2 && production.equals(mayuscula)){

                    //System.out.println("pasa");
                }
                else{
                    //System.out.println("no pasa");
                    throw new CFGAlgorithmsException();

                }
            }
            else{
                //System.out.println("no pasa");
                throw new CFGAlgorithmsException();
            }
        }catch (CFGAlgorithmsException e){
            throw e;    
        }
        
        
    }

    @Override   //G
    public boolean isCNF() { 
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
         ArrayList<Character> listanoterminales = new ArrayList<>(noterminales);
         
         Character noterminal;
        
                        
                     
         for(int i = 0; i<noterminales.size();i++){
            noterminal = listanoterminales.get(i);
            ArrayList<String> produccion = new ArrayList<>(getProductions(noterminal));
            
                for(int j=0;j<getProductions(noterminal).size();j++){
                    System.out.println(noterminal + "  "+produccion.get(j));
                    
                    try{
                        checkCNFProduction(noterminal,produccion.get(j));
                    }catch(CFGAlgorithmsException e){
                        return false;
                    }
            }
         }
 
        return true; 
    
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

                        if(table[j][i-1].endsWith(", ") && k>1){    //borra el ultimo ", " cndo las letras van en distintos String
                        table[j][i-1]=table[j][i-1].substring(0, table[j][i-1].length()-2); 
                        }       //el substring muestra todos los elementos del String menos los 2 ultimos (", ")
                    }
                    if(table[j][i-1].isBlank()){
                            table[j][i-1]="0";      // 0 para el conjunto vacio
                    }

                    if(table[j][i-1].endsWith(", ")){   //borra el ultimo ", " cndo se ponen varias letras a la vez en la tabla
                        table[j][i-1]=table[j][i-1].substring(0, table[j][i-1].length()-2);
                    }
                }
            }
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
            for (int i=0; i<table[j].length && table[j][i]!=null; i++) {     //i=columnas
                
                x+=table[j][i]+" | ";
            }
            
            x=x.substring(0, x.length()-3)+"\n";    //borra el ulitmo " | "
        }
        
        System.out.print(x);
        System.out.println();
        return x;
    }
    
}
     
