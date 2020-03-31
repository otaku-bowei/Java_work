package cn.service;

import cn.entity.Customer;
import cn.entity.Manager;
import cn.factory.MybatisFactory;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

public class CheckIn {

    public static Customer selectCustomer(String c_id) throws IOException {
        SqlSession sqlSession = MybatisFactory.getSqlSession() ;
        Customer customer = sqlSession.selectOne("cn.service.Mapper.selectCustomer" , c_id) ;
        sqlSession.close() ;
        return customer ;
    }

    public static Manager selectManager(String m_id) throws IOException {
        SqlSession sqlSession = MybatisFactory.getSqlSession() ;
        Manager manager = sqlSession.selectOne("cn.service.Mapper.selectManager" , m_id) ;
        sqlSession.close() ;
        return manager ;
    }
}
