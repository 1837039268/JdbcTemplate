package cn.itcast.jdbctemplate;

import cn.itcast.datasource.utils.JDBCUtils;
import cn.itcast.domain.User;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class JdbcTemplateDemo2 {

    //Junit单元测试,可以让方法独立执行
    //1.获取JdbcTemplate对象
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 1.修改id=1的 age 为 35
     */
    @Test
    public void test1() {
        //2.定义sql
        String sql = "update sys_user set age=35 where id=?";
        //3.执行sql
        int count = template.update(sql, 1);
        System.out.println(count);
    }

    /**
     * 2.添加一条记录
     */
    @Test
    public void test2() {
        String sql = "insert into sys_user(id,name,age) values(?,?,?)";
        int count = template.update(sql, null, "小强", 22);
        System.out.println(count);
    }

    /**
     * 3.删除刚才添加的记录
     */
    @Test
    public void test3() {
        String sql = "delete from sys_user where name=?";
        int count = template.update(sql, "小强");
        System.out.println(count);
    }

    /**
     * 4.查询id=1的记录,将其封装为map集合
     * 注意:这个方法查询的结果集长度只能是1
     */
    @Test
    public void test4() {
        String sql = "select * from sys_user where id=?";
        Map<String, Object> map = template.queryForMap(sql, 1);
        System.out.println(map);
        //{id=1, name=武松, age=35, sex=1, create_time=2019-11-06 09:51:12.0, del_flag=0}
    }

    /**
     * 5.查询所有记录,将其封装为list
     * 注意:将每一条记录封装为一个map集合,再将map集合装载到list集合中
     */
    @Test
    public void test5() {
        String sql = "select * from sys_user";
        List<Map<String, Object>> list = template.queryForList(sql);
        for (Map<String, Object> strMap : list) {
            System.out.println(strMap);
        }
    }

    /**
     * 6.查询所有记录,将其封装为user对象的list集合(常用)
     */
    @Test
    public void test6() {
        String sql = "select * from sys_user";
        List<User> list = template.query(sql, new BeanPropertyRowMapper<>(User.class));
        for (User user : list) {
            System.out.println(user);
        }
//        List<User> list = template.query(sql, new RowMapper<User>() {
//            @Override
//            public User mapRow(ResultSet rs, int i) throws SQLException {
//                User user = new User();
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                int age = rs.getInt("age");
//                String sex = rs.getString("sex");
//                String createtime = rs.getString("create_time");
//                String delflag = rs.getString("del_flag");
//
//                user.setId(id);
//                user.setName(name);
//                user.setAge(age);
//                user.setSex(sex);
//                user.setCreateTime(createtime);
//                user.setDelFlag(delflag);
//                return user;
//            }
//        });
//        for (User user : list) {
//            System.out.println(user);
//        }
    }

    /**
     * 7.查询总记录数
     */
    @Test
    public void test7() {
        String sql = "select count(id) from sys_user";
        Long total = template.queryForObject(sql, Long.class);
        System.out.println(total);
    }

    /**
     * 8.分页
     */
    @Test
    public void test8() {
        //1:0-9  2:10-19
        int page = 1;
        int pageSize = 3;
        int beginRow = (page - 1) * pageSize;
        String sql = "select * from sys_user limit ?,?";
        List<User> list = template.query(sql, new BeanPropertyRowMapper<>(User.class), beginRow, pageSize);
        for (User user : list) {
            System.out.println(user);
        }
    }

}
