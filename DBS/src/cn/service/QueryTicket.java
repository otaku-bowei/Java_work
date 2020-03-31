package cn.service;

import cn.dao.QueryDAO;
import cn.entity.Customer;
import cn.entity.Ticket;
import cn.factory.DateFormat;
import cn.factory.MybatisFactory;
import org.apache.ibatis.session.SqlSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class QueryTicket {
    //用户购票
    public static List<Ticket> showTicket(Date startTime , String origin , String distance) throws IOException {
        SqlSession sqlSession = MybatisFactory.getSqlSession() ;
        QueryDAO queryDAO = sqlSession.getMapper(QueryDAO.class) ;
        List<Ticket> tickets = queryDAO.buyTicket(DateFormat.DateToString(startTime) , origin ,distance) ;
        sqlSession.close();
        System.out.println(tickets.size());
        return tickets ;
    }

    //购票订单处理
    public static void buyTicket(Customer customer , Ticket ticket) throws IOException {
        SqlSession sqlSession = MybatisFactory.getSqlSession() ;
        QueryDAO queryDAO = sqlSession.getMapper(QueryDAO.class) ;
        //生成订单
        queryDAO.buy(customer.getC_id() , ticket.getTicket_no() , new Timestamp(System.currentTimeMillis()));
        //修改车票信息所属
        queryDAO.ticketBought(customer.getC_id() , ticket.getTicket_no());
        sqlSession.close();
    }

    //退票订单处理
    public static void repayTicket(Customer customer , Ticket ticket) throws IOException {
        SqlSession sqlSession = MybatisFactory.getSqlSession() ;
        QueryDAO queryDAO = sqlSession.getMapper(QueryDAO.class) ;
        //生成退订订单
        queryDAO.repay(customer.getC_id() , ticket.getTicket_no() , new Timestamp(System.currentTimeMillis()));
        //修改车票信息所属
        queryDAO.ticketRepayed(ticket.getTicket_no());
        sqlSession.close();
    }

    //退票找票
    public static ArrayList<Ticket> queryTickets_c(Customer customer) throws IOException {
        SqlSession sqlSession = MybatisFactory.getSqlSession() ;
        QueryDAO queryDAO = sqlSession.getMapper(QueryDAO.class) ;
        ArrayList<Ticket> tickets = queryDAO.customerTicket(customer.getC_id()) ;
        sqlSession.close();
        return tickets ;
    }
}
