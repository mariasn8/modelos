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
            }
            else{
                throw new CFGAlgorithmsException();
            }
            
        } catch(CFGAlgorithmsException e){
            throw e;
        }
        //SIN ACABAR
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
        
        /*Character [] a= new Character[];
        if(axioma = new Character){
            throw new CFGAlgorithmsException();
        } */
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
                
                if(production.equals("l")){     //si nonterminal esta en la lista de noterm y la prod es l, se añade
                    listaProds.add(production);
                }
                
                for(int i=0; i<production.length();i++){    //recorre todo production
                    if(!noterminales.contains(production.charAt(i)) && !terminales.contains(production.charAt(i))){   //si la letra no esta en la lista d term y noterm, error
                        throw new CFGAlgorithmsException();
                    }
                }
                
                if(listaProds.contains(production)){    //si la prod ya esta en la lista, error
                    throw new CFGAlgorithmsException();
                } 

                listaProds.add(production);     //si llega, todas las letras son T o NT
                producciones.put(nonterminal, listaProds);  //
                
            }
            else{
                throw new CFGAlgorithmsException();
            }
        } catch(CFGAlgorithmsException e) {
            throw e;
        }
    }
    
    
    
    @Override   //M 
    public boolean removeProduction(char nonterminal, String production) throws CFGAlgorithmsException {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try{
            if(noterminales.contains(nonterminal)){     //comprueba q el NT está en la lista
                if(producciones.containsKey(nonterminal)){      //comprueba q el NT tiene asociada una prod
                    producciones.remove(nonterminal, production);
                    return true;    //NO FUNCIONA BIEN
                }
            }
            else{
                throw new CFGAlgorithmsException();
            }
        } catch (CFGAlgorithmsException e){
            throw e;
        }
        return false;
    }



    @Override   //M
    public List<String> getProductions(char nonterminal) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody        
        List<String> prods=new ArrayList<>();
        if(noterminales.contains(nonterminal)){
                prods=producciones.get(nonterminal);      //.get() coge el valor de la clave
            }
        return prods;
    }



    @Override   //M
    public String getProductionsToString(char nonterminal) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        List<String> prods=new ArrayList<>();      //lista de producciones
        if(noterminales.contains(nonterminal)){
                prods=producciones.get(nonterminal);      //guarda las prods del noterm, .get() coge el valor de la clave
        }
        Collections.sort(prods);    //ordena alfabeticamente
        
        StringBuilder sb=new StringBuilder();
        if(producciones.equals(nonterminal)){
            for(String str:prods){
                sb.append(str).append("|");
            }
        }
        
        return nonterminal+"::="+prods;
    }



    @Override //M
    public String getGrammar() {    //FALTA COMPROBAR SI VA
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        List<Character> orden=new ArrayList<>();
        orden.addAll(noterminales);
        Collections.sort(orden);    //para ordenar alfabeticamente la lista de noterm
        //S iria hacia el final (?)
        
        StringBuilder sb=new StringBuilder();   //pasa la lista orden a String
        for(Character ch:orden){
            sb.append(ch).append("::=").append(getProductionsToString(ch)).append("\n");     //separa cada letra, pone ::= despues y las pone en lineas diferentes
        }       //.append()añade lo q haya dentro de () al final de sb
        String str=sb.toString();
        return str;

    }


//aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    @Override //M
    public void deleteGrammar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override //G
    public boolean isCFG() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
               if (axioma == ' ') {
            return false;
        }

    
    
    try {
        getStartSymbol(); 
        for (char nt : noterminales) {
            List<String> prods = getProductions(nt); 
            for (String prod : prods) {
                String productionString = getProductionsToString(nt);
                if (!productionString.startsWith(nt + "::=")) {
                    return false; 
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
