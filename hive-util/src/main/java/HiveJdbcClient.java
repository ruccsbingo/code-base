import java.sql.*;


/**
 * Created by zhangbing on 15/11/25.
 */
public class HiveJdbcClient {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    /**
        * @param args
        * @throws SQLException
        */
    public static void main(String[] args) throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(1);
        }
        //replace "hive" here with the name of the user the queries should run as
        Connection con = DriverManager.getConnection("jdbc:hive2://10.111.1.14:10000/working_dw", "", "");
        Statement stmt = con.createStatement();
//        String tableName = "fact_document_v3";
        String tableName = "dict_user_active_channel";

        // show tables
        String sql = "show tables '" + tableName + "'";
        System.out.println("Running: " + sql);
        ResultSet res = stmt.executeQuery(sql);
        if (res.next()) {
            System.out.println(res.getString(1));
        }
        // describe table
        sql = "describe " + tableName;
        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res.getString(1) + "\t" + res.getString(2));
        }

        sql = "show partitions dict_user_active_channel";
        sql = "select * from dict_user_active_channel where  dbname in (\"10.101.1.192_3308\") and fromid > \"m\" and fromid < \"n\" limit 10 ";
//        sql = "select * from dim_user_info where p_day = \"2016-04-10\" limit 10";
//        sql = "select fromid, user_userid, device_name, age, gender, region, depth " +
//                "from dict_user_active_channel, dim_user_info " +
//                "where user_userid = user_id " +
//                "and p_day = \"2016-04-10\" " +
//                "and dbname in (\"10.101.1.192_3308\", \"10.101.1.193_3306\", \"account1.yidian.com_3308\",  \"account2.yidian.com_3308\") " +
//                "and fromid > \"m\" and fromid < \"n\" order by fromid, gender, region, depth, age, device_name limit 10" ;
        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res.getString(1) + "\t");
        }

//        sql = "show partitions fact_document_v3 partition(p_day='2016-01-06', p_hour = '12')";
//        res = stmt.executeQuery(sql);
//        while (res.next()) {
//            System.out.println(res.getString(1));
//        }
    }
}
