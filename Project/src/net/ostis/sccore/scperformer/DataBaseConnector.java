package net.ostis.sccore.scperformer;

import java.util.Map;
import java.util.HashMap;
import net.ostis.sccore.scelements.ScNode;
import org.neo4j.kernel.AbstractGraphDatabase;
import org.neo4j.kernel.Config;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.server.WrappingNeoServerBootstrapper;

/**
 * User: yaskoam
 * Date: 03.03.12
 * Time: 14:23
 */
public class DataBaseConnector {

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

        return dataBase;
    }

    private static void registerShutDownHook(final AbstractGraphDatabase db) {

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                db.shutdown();
            }
        } );
    }
}
