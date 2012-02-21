/* $This file is distributed under the terms of the license in /doc/license.txt$ */
package edu.cornell.mannlib.vitro.webapp.utils.dataGetter;

import java.util.Map;

import javax.servlet.ServletContext;

import com.hp.hpl.jena.rdf.model.Model;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;

/**
 * A class to get data based on configurations in RDF. These are useful for pages bug not specific to pages.
 * Consider using this interface when you need data based on RDF configuration.  
 * 
 * The main difference between this and PageDataGetter is that these are configured not based on page URI 
 * but on DataGetter URI.  This allows a configuration of a DataGetter to be used in multiple situations. 
 * The DataGetter is not passed information about what page it might be associated with.
 * 
 * Using this interface is preferred over PageDataGetter because then the DataGetter can be associated with 
 * things other than pages. 
 * 
 * Constructors:  Objects that implement this interface will be constructed by reflection with the following 
 * constructor signatures in the following order:
 *  DataGetter( Model displayModel, String dataGetterURI )
 *  DataGetter()
 *  
 */
public interface DataGetter {

    /**
     * Get data. Throwing an Exception is acceptable.
     *  
     * @param context - servlet context to get state from
     * @param vreq - request to get state from
     * @param valueMap - any values already generated by data getters or the controller.
     * @return data to add to valueMap.  Should not be null.
     */
    Map<String,Object> getData(ServletContext context, VitroRequest vreq, Map<String, Object> valueMap );       

}