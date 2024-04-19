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
    private Character axioma;
    private Map<Character, List<String>> produccion;
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
        
        }
    }



    @Override   //G
    public Set<Character> getTerminals() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return terminales;
    }



    @Override   //G
    public void setStartSymbol(char nonterminal) throws CFGAlgorithmsException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override   //G
    public Character getStartSymbol() throws CFGAlgorithmsException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override   //M
    public void addProduction(char nonterminal, String production) throws CFGAlgorithmsException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override   //M
    public boolean removeProduction(char nonterminal, String production) throws CFGAlgorithmsException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override   //M
    public List<String> getProductions(char nonterminal) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override   //G
    public String getProductionsToString(char nonterminal) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override
    public String getGrammar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override
    public void deleteGrammar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override
    public boolean isCFG() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override
    public boolean hasUselessProductions() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override
    public List<String> removeUselessProductions() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override
    public List<Character> removeUselessSymbols() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override
    public boolean hasLambdaProductions() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override
    public List<Character> removeLambdaProductions() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override
    public boolean hasUnitProductions() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override
    public List<String> removeUnitProductions() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override
    public void transformToWellFormedGrammar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override
    public void checkCNFProduction(char nonterminal, String production) throws CFGAlgorithmsException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override
    public boolean isCNF() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override
    public void transformIntoCNF() throws CFGAlgorithmsException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override
    public boolean isDerivedUsignCYK(String word) throws CFGAlgorithmsException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override
    public String algorithmCYKStateToString(String word) throws CFGAlgorithmsException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
