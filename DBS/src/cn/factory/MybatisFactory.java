package cn.factory;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisFactory {
    public static SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "batis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory ;
    }

    public static SqlSession getSqlSession() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory() ;
        return sqlSessionFactory.openSession(true) ;
    }
}
