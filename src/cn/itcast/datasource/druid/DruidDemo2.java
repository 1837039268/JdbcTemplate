package cn.itcast.datasource.druid;

import cn.itcast.datasource.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 使用新的工具类
 */
public class DruidDemo2 {

    public static void main(String[] args) {

        /**
         * 完成添加的操作,给sys_user添加一条记录
         */
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            //1.获取连接
            conn = JDBCUtils.getConnecton();
            //2.定义sql
            String sql = "insert into sys_user values(null,?,?,?,?,?)";
            //3.获取pstmt对象
            pstmt = conn.prepareStatement(sql);
            //4.给?赋值
            pstmt.setString(1, "小龙");
            pstmt.setInt(2, 23);
            pstmt.setString(3, "1");
            pstmt.setString(4, "2000-10-10 10:00:00");
            pstmt.setString(5, "0");
            //5.执行sql语句
            int count = pstmt.executeUpdate();
            System.out.println(count);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //6.释放资源
            JDBCUtils.close(null, pstmt, conn);
        }
    }

}
