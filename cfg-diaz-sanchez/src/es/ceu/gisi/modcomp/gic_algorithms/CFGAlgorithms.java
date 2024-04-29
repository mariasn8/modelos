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
    private Map<Character, List<String>> producciones=new HashMap<Character, List<String>>();
    private Map<String, List<Character>> inverseProd;
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
    public void removeNonTerminal(char nonterminal) throws CFGAlgorithmsException {     //¿¿NO TIENE TEST??
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
        try {

            if (terminales.contains(terminal)){
                terminales.remove(terminal);
            }
                
                
            else{
                throw new CFGAlgorithmsException();
            }
        } catch(CFGAlgorithmsException e){
            
            throw e;
        
        }       //SIN ACABAR
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
            
            if(noterminales.contains(nonterminal)){     //comprueba q el noterminal esta en la lista d no terminales

                if(production.equals("l")) {     //si nonterminal esta en la lista de noterm y la prod es l, se añade a los term
                    terminales.add('l');
                }
                
                for(int i=0; i<production.length();i++){    //recorre todo production
                    if(!noterminales.contains(production.charAt(i)) && !terminales.contains(production.charAt(i))){   //si la letra no esta en la lista d term y noterm, error
                        throw new CFGAlgorithmsException();
                    }
                }
                
                //listaChars.add(nonterminal);
                //inverseProd.put(production, listaChars);
                
                if(producciones.containsKey(nonterminal) && producciones.get(nonterminal).contains(production)){    //si production y nonterminal estan en producciones, error
                    throw new CFGAlgorithmsException();
                } 
                
                if(producciones.get(nonterminal)!=null){    //si ya hay una lista de prods para el NT, se coge esta
                    listaProds=producciones.get(nonterminal);   //añade a la lista la nueva prod
                }

                listaProds.add(production);     //si llega, todas las letras son T o NT. Añade la prod a la lista
                //listaChars.add(nonterminal);
                
                producciones.put(nonterminal, listaProds);  //si llega, se añade production
                //inverseProd.put(production, listaChars)
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
            Collections.sort(listaProds);    //ordena alfabeticamente

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
        
        else{
            return "";
        }

    }



    @Override //M
    public String getGrammar() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        List<Character> orden=new ArrayList<>();
        orden.addAll(noterminales);
        
        Collections.sort(orden);    //para ordenar alfabeticamente la lista de noterm
        //S iria hacia el final (?)
        
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
        if (axioma == ' ') {
            return false;
        }

    try {
        getStartSymbol(); 
        for (char nt : noterminales) {
        List<String> prods = getProductions(nt);
        for (String prod : prods) {
            // Verificar si la producción es válida
            if (prod.length() < 3 || !Character.isUpperCase(prod.charAt(0)) || prod.charAt(1) != ':' || prod.charAt(2) != ':') {
                return false; // La producción no es válida
            }
        }
    }
        return true;
    } catch (CFGAlgorithmsException e) {
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override   //M
    public List<Character> removeLambdaProductions() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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



    @Override   //M
    public boolean isDerivedUsignCYK(String word) throws CFGAlgorithmsException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    } 



    @Override   //M
    public String algorithmCYKStateToString(String word) throws CFGAlgorithmsException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
