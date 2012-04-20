package net.ostis.sccore.scperformer;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.kernel.AbstractGraphDatabase;
import org.neo4j.kernel.Config;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import net.ostis.sccore.scelements.ScNodeImpl;
import net.ostis.sccore.types.ScElementTypes;
import org.neo4j.server.WrappingNeoServerBootstrapper;

/**
 * Class that provide getting database object.
 *
 * @author yaskoam
 */
public class DataBaseManager {

    private static String dataBasePath = "";
    private Index<Node> typesIndex;
    private AbstractGraphDatabase dataBase;
    private static DataBaseManager dataBaseManager;

    /**
     * Constructor for data base manager.
     *
     * @param basePath basePath path to location of data base
     */
    private DataBaseManager(String basePath) {
        // set configuration of base
        Map<String, String> config = new HashMap<String, String>();

        // set auto indexing for searching nodes by node name
        config.put(Config.NODE_AUTO_INDEXING, "true");
        config.put(Config.NODE_KEYS_INDEXABLE, ScNodeImpl.SC_NODE_NAME_PROPERTY);

        dataBase = new EmbeddedGraphDatabase(basePath, config);
        registerShutDownHook(dataBase);

        // create Index for types
        IndexManager indexManager = dataBase.index();
        typesIndex = indexManager.forNodes(ScElementTypes.ELEMENT_TYPE_PROPERTY);

        // create type nodes
        createStandartTypeNodes(dataBase);

        // start server
        WrappingNeoServerBootstrapper server = new WrappingNeoServerBootstrapper(dataBase);
        server.start();
    }

    /**
     * Get data base manager instance.
     *
     * @param basePath basePath path to location of data base
     * @return data base manager object
     */
    public static synchronized DataBaseManager getDataBaseManagerInstance(String path) {
        dataBasePath = path;

        if (dataBaseManager == null) {
            dataBaseManager = new DataBaseManager(dataBasePath);
        }

        return dataBaseManager;
    }

    /**
     * Get data base manager instance.
     *
     * @return data base manager object or null if it doesn't exist
     */
    public static synchronized DataBaseManager getDataBaseManagerInstance() {
        return dataBaseManager;
    }

    /**
     * Method that get neo4j data base object for working with that data base.
     *
     * @return data base object
     */
    public AbstractGraphDatabase getDataBase() {
        return dataBase;
    }

    /**
     * Method that get type node.
     * <p> If type node doesn't exist, method create them. </p>
     *
     * @param type name of type
     * @return type node
     */
    public Node getTypeNode(String type) {
        Node typeNode = typesIndex.get(ScElementTypes.ELEMENT_TYPE_PROPERTY, type).getSingle();

        if (typeNode == null) {
            typeNode = dataBase.createNode();
            typeNode.setProperty(ScElementTypes.ELEMENT_TYPE_PROPERTY, type);
        }

        typesIndex.add(typeNode, ScElementTypes.ELEMENT_TYPE_PROPERTY, type);
        return typeNode;
    }

    /**
     * Method that create index for types.
     *
     * @return created index of types
     */
    private void createStandartTypeNodes(AbstractGraphDatabase dataBase) {
        Transaction transaction = dataBase.beginTx();

        try {
            for (String type : ScElementTypes.getTypesList()) {
                this.getTypeNode(type);
            }
            transaction.success();
        }
        catch (Exception ex) {
            transaction.failure();
        }
        finally {
            transaction.finish();
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
