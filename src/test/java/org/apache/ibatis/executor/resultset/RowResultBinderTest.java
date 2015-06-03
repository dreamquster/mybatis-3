package org.apache.ibatis.executor.resultset;

import org.apache.ibatis.BaseDataTest;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.domain.blog.Reader;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by root on 15-6-1.
 */
public class RowResultBinderTest extends BaseDataTest {

    protected static DataSource ds;
    private final Configuration config;


    public RowResultBinderTest() {
        config = new Configuration();
        config.setLazyLoadingEnabled(true);
        config.setUseGeneratedKeys(false);
        config.setMultipleResultSetsEnabled(true);
        config.setUseColumnLabel(true);
        config.setDefaultStatementTimeout(5000);
        config.setDefaultFetchSize(100);
    }

    @BeforeClass
    public static void setUp() throws IOException, SQLException {
        ds = createBlogDataSource();
    }


    @Test
    public void bindColumnToUserDefineCase() throws SQLException {
        Executor executor = new SimpleExecutor(config, new JdbcTransaction(ds, null, false));
        try {
            MappedStatement selectStatement = new MappedStatement.Builder(config,
                    "selectAuthorAutoMap",
                    new StaticSqlSource(config,"SELECT * FROM reader ORDER BY I_USER_ID"),
                    SqlCommandType.SELECT).resultMaps(
                    new ArrayList<ResultMap>() {{//anonymous list
                            add(new ResultMap.Builder(config, "defaultResultMap", Reader.class,
                                    new ArrayList<ResultMapping>()).build());
            }}).fetchSize(100).build();
            List<Reader> readers = executor.query(selectStatement, null, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
            assertEquals(2, readers.size());
            Reader reader = readers.get(0);
            // id,readername
            // (1, "Karl Max");
            assertEquals(1, reader.getId());
            assertEquals("Karl Max", reader.getReadername());
        } finally {
            executor.rollback(true);
            executor.close(false);
        }
    }

}
