/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.ceu.gisi.modcomp.gic_algorithms;

import es.ceu.gisi.modcomp.gic_algorithms.exceptions.CFGAlgorithmsException;
import java.util.List;

/**
 *
 * @author Maria
 */
public class prueba {
    public static void main(String []args) throws CFGAlgorithmsException{
        //CFGAlgorithms gica= new CFGAlgorithms();
        getProduc();
    }

    private static List<String> getProduc() throws CFGAlgorithmsException {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody       
        CFGAlgorithms gica= new CFGAlgorithms();
        /*List<String> aaaa= gica.getProd();
        System.out.println(aaaa);
        return aaaa; */
        gica.addNonTerminal('S');
        gica.addNonTerminal('A');
        
        gica.addTerminal('a');
        
        gica.addProduction('A', "aA");
        gica.addProduction('A', "a");
        
        List<String> aaa=gica.getProductions('A');
        System.out.println(aaa);
        return aaa;
    } 
    
}
