package net.ostis.sccore.scperformer;

import java.util.Map;
import java.util.HashMap;

import org.neo4j.kernel.AbstractGraphDatabase;
import org.neo4j.kernel.Config;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.server.WrappingNeoServerBootstrapper;

import net.ostis.sccore.scelements.ScNode;
import net.ostis.sccore.scelements.ScNodeImpl;
import net.ostis.sccore.types.ScElementTypes;
import org.neo4j.graphdb.Node;

import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;


/**
 * Class that provide getting database object.
 * @author yaskoam
 */
public class DataBaseConnector {

    private static Index<Node> elementTypes = null;
    
    /**
     * Method that get neo4j data base object for working with that data base.
     * @param basePath path to location of data base
     * @return data base object
     */
    public static AbstractGraphDatabase getDataBaseInstance(String basePath) {
        /* set configuration of base */
        Map<String, String> config = new HashMap<String, String>();
        /* set auto indexing for searching nodes by node name*/
        config.put(Config.NODE_AUTO_INDEXING, "true");
        
        config.put(Config.NODE_KEYS_INDEXABLE, ScNode.SC_NODE_NAME_PROPERTY);
        AbstractGraphDatabase dataBase = new EmbeddedGraphDatabase(basePath, config);
        registerShutDownHook(dataBase);
        
        /* start server */
        WrappingNeoServerBootstrapper server = new WrappingNeoServerBootstrapper(dataBase);
        server.start();

        /* create index for types      */
       //createIndex(dataBase);
        
        return dataBase;
    }

 /**
 *  Method that create index for types.
 * 
 * @return created index of types
 */
    public synchronized static void createIndex(AbstractGraphDatabase dataBase) {        
        IndexManager index = dataBase.index();                        
        for(ScElementTypes type : ScElementTypes.values())
        {
              Node node = index.forNodes( ScNode.Sc_ELEMENT_TYPE ).get(ScNode.Sc_ELEMENT_TYPE, type.toString()).getSingle();              
              if (node == null) {
                  node = dataBase.createNode();
                  node.setProperty(ScNode.SC_NODE_NAME_PROPERTY, type.toString());
              }       
              index.forNodes( ScNode.Sc_ELEMENT_TYPE ).add(node, ScNode.Sc_ELEMENT_TYPE, type.toString());
        }           
                
    }
    

    private static void registerShutDownHook(final AbstractGraphDatabase db) {

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                db.shutdown();
            }
        });
    }
}
