package cn.dao;

import cn.entity.Ticket;
import cn.entity.Train;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/*
**注解开发sql，是单纯的sql语句和java代码的拼接
* 动态sql开发，是sql和java的融合，通过提供者提供sql类和DAO结合，将DAO接口和真正执行的sql语句映射
 */
public interface QueryDAO {
    /*
    **用户
     */
    //根据出发时间和起点终点搜索需要购买的车票
    @Select("select * from ticket where start_time >= #{startTime} and " +
            "origin=#{origin} and distance =#{distance} ")
    public List<Ticket> buyTicket(@Param("startTime") String startTime , @Param("origin") String origin ,
                                  @Param("distance") String distance);
    //根据用户搜索用户所有的车票
    @Select("select * from ticket where holder = #{c_id}")
    public ArrayList<Ticket> customerTicket (@Param("c_id") String c_id) ;
    //根据车次查询车票
    @Select("select * from ticket where train_no = #{id}")
    public ArrayList<Ticket> queryTrain_c(@Param("id") int id) ;
    //购票
    @Insert("insert into order(o_id , ticket_no , o_Timestamp) values (#{o_id} , #{ticket_no} , #{o_Timestamp})")
    public void buy(@Param("o_id") String o_id , @Param("ticket_no") String ticket_no , @Param("o_Timestamp") Timestamp o_Timestamp) ;
    @Update("upTimestamp ticket set holder = #{c_id} where ticket_no = #{ticket_no}")
    public void ticketBought(@Param("c_id") String c_id , @Param("ticket_no") String ticket_no);
    //退票
    @Insert("insert into repay(o_id , ticket_no , r_Timestamp) values (#{o_id} , #{ticket_no} , #{r_Timestamp})")
    public void repay(@Param("o_id") String o_id , @Param("ticket_no") String ticket_no , @Param("r_Timestamp") Timestamp r_Timestamp);
    @Update("upTimestamp ticket set holder = null where ticket_no = #{ticket_no}")
    public void ticketRepayed(@Param("ticket_no") String ticket_no);



    /*
    **管理员
     */
    //所有车次
    @Select("select * from train")
    public List<Train> allTrain();
    //查询车次信息
    @Select("select * from train where train_no = #{no}")
    public List<Train> queryTrain_m(@Param("no") int train_no) ;
    //修改目的地
    @Update("upTimestamp train set distance = #{distance}")
    public void changeDistance(@Param("distance") String diatance) ;
    //修改出发时间
    @Update("upTimestamp train set start_time = #{new_time}")
    public void changeStartTime(@Param("new_time") Timestamp newTime) ;

    //添加车次
    @Insert("INSERT INTO train(train_no,start_time,origin,distance) VALUES (${train_no},\'${start_time}\'," +
            "\'${origin}\',\'${distance}\')")
    public void addTrain(@Param("train_no") int trainNo, @Param("start_time") String startTime,
                                 //时间cuo读出用timestamp，插入用string
                                 @Param("origin") String origin, @Param("distance") String distance);
    //生产车票
    @Insert("INSERT INTO ticket(ticket_no, train_no, start_time, origin, distance, section, seat, money) " +
            "VALUES (\'${ticket}\', ${train}, \'${startTime}\', \'${origin}\', \'${distance}\', ${section}," +
            " ${seat}, ${money})")
    public void publishTrain(@Param("ticket") String ticket,@Param("train") int train,@Param("startTime") String time,
                             @Param("origin") String origin,@Param("distance") String distance,@Param("section") int section,
                             @Param("seat") int seat,@Param("money") double money);

}
