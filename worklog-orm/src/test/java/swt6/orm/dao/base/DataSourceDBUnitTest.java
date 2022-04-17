package swt6.orm.dao.base;

import org.apache.derby.jdbc.ClientDataSource;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import swt6.client.ConsoleClient;
import swt6.util.JpaUtil;

import javax.sql.DataSource;

public class DataSourceDBUnitTest extends DataSourceBasedDBTestCase {
    @Override
    protected DataSource getDataSource() {
        JpaUtil.getTransactedTestEntityManager();
        JpaUtil.commit();
//        ClientDataSource dataSource = new ClientDataSource();
        EmbeddedDataSource dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("memory:WorkLogDb");

        return dataSource;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("dbunit/dataSet.xml"));
    }

    @Override
    protected DatabaseOperation getSetUpOperation() {
        JpaUtil.getTransactedEntityManager();
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        JpaUtil.closeEntityManagerFactory();
        return DatabaseOperation.DELETE_ALL;
    }
}
