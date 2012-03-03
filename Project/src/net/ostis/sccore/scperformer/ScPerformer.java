package net.ostis.sccore.scperformer;

import net.ostis.sccore.scelements.ScNode;
import net.ostis.sccore.scelements.ScNodeImpl;
import net.ostis.sccore.scfactory.ScFactory;
import net.ostis.sccore.scfactory.ScFactoryImpl;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.AutoIndexer;
import org.neo4j.graphdb.index.ReadableIndex;
import org.neo4j.kernel.AbstractGraphDatabase;

/**
 * User: yaskoam
 * Date: 03.03.12
 * Time: 13:51
 */
public class ScPerformer {
    private AbstractGraphDatabase dataBase;
    private Transaction transaction;

    /**
     * Construct object with the data base path.
     * @param basePath path
     */
    public ScPerformer(String basePath) {
        dataBase = DataBaseConnector.getDataBaseInstance(basePath);
        ScFactoryImpl factory = ScFactoryImpl.getInstance();
        factory.setDataBase(dataBase);
    }

    /**
     * Method that return sc factory object for further working
     * @return sc factory object
     */
    public ScFactory getScFactory() {
        return ScFactoryImpl.getInstance();
    }

    /**
     * Method start data base transaction for execute different operation.
     *
     */
    public void beginExecution() {
        transaction = dataBase.beginTx();
    }

    /**
     * Method that finish transaction.
     * 
     */
    public void finishExecution() {
        transaction.success();
        transaction.finish();
    }

    /**
     * Method that find sc node by name in memory.
     * @param nodeName name of node
     * @return founded node
     */
    public ScNode findScNodeByName(String nodeName) {
        AutoIndexer<Node> autoIndexer = dataBase.index().getNodeAutoIndexer();
        ReadableIndex<Node> index = autoIndexer.getAutoIndex();
        Node node = index.get(ScNode.SC_NODE_NAME_PROPERTY, nodeName).getSingle();
        return new ScNodeImpl(node);
    }
}




















