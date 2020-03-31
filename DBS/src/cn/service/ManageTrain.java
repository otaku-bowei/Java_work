package cn.service;

import cn.dao.QueryDAO;
import cn.entity.Train;
import cn.factory.MybatisFactory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class ManageTrain {
    //添加车次
    public static void addTrain(int train_no , String startTime , String origin , String distance,double money) throws IOException {
        SqlSession sqlSession = MybatisFactory.getSqlSession() ;
        QueryDAO dao = sqlSession.getMapper(QueryDAO.class) ;
        dao.addTrain(train_no , startTime , origin , distance);//添加车次应该自动添加车票出售，触发器做不了
        sqlSession.close();
        publishTickets(train_no ,startTime ,origin , distance ,money);//顺序进行模拟触发器
    }

    //车票生成
    public static void publishTickets(int train_no , String startTime , String origin , String distance,
                                      double money) throws IOException {
        SqlSession sqlSession = MybatisFactory.getSqlSession() ;
        QueryDAO dao = sqlSession.getMapper(QueryDAO.class) ;
        for(int i=0 ; i<6 ; i++ ){
            dao.publishTrain(UUID.randomUUID().toString().replace("-", ""),
                    train_no,startTime,origin,distance,(i%2)+1,(i%3)+1,money);//两节车厢每节三个座位
        }
        sqlSession.close();
    }

    //所有车次展示
    public static List<Train> allTrain() throws IOException {
        SqlSession sqlSession = MybatisFactory.getSqlSession() ;
        QueryDAO dao = sqlSession.getMapper(QueryDAO.class) ;
        List<Train> trains = dao.allTrain();
        sqlSession.close();
        return trains ;
    }
}
