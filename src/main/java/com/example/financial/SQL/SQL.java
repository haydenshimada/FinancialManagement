package com.example.financial.SQL;

import com.example.financial.Type;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SQL {
    private Connection connect = null;
    private PreparedStatement pst = null;
    private List<Type> data;
    private List<Type> History;
    private static int id = 0;

    public SQL() throws SQLException {
        connect = MySQLConnUtils.getMySQlConnection();
    }

    /** trả về list gd theo ngày. */
    public List<Type> getList(int year, int month, int day) throws SQLException {
        data = new ArrayList<>();
        final String findDate = "select type, money, imageSource, buttonColor from data where extract(day from time) = ? and extract(month from time) = ? and extract(year from time) = ?";
        pst = connect.prepareStatement(findDate);
        pst.setInt(1, day);
        pst.setInt(2, month);
        pst.setInt(3, year);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            data.add(new Type(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
        }
        return data;
    }

    /** xóa type. */
    public void deleteType(final String type, final int year, final int month, final int day) throws SQLException {
        final String delType = "delete from data where type = ? and extract(day from time) = ? and extract(month from time) = ? and extract(year from time) = ?";
        pst = connect.prepareStatement(delType);
        pst.setString(1, type);
        pst.setInt(2, day);
        pst.setInt(3, month);
        pst.setInt(4, year);
        pst.executeUpdate();
    }

    /** thêm giao dịch. */
    public void addType(final String type, final int money, final String imageSource, final String buttonColor, int year, int month, int day) throws SQLException {
        final String addType = "insert into data (type, money, imageSource, buttonColor, time) values (?, ?, ?, ?, ?)";

        // chuyển util.date thành sql.date
        Calendar myCal = Calendar.getInstance();
        myCal.set(Calendar.YEAR, year);
        myCal.set(Calendar.MONTH, month - 1);
        myCal.set(Calendar.DAY_OF_MONTH, day);
        java.util.Date d = myCal.getTime();
        java.sql.Date date = new java.sql.Date(d.getTime());

        pst = connect.prepareStatement(addType);
        pst.setString(1, type);
        pst.setInt(2, money);
        pst.setString(3, imageSource);
        pst.setString(4, buttonColor);
        pst.setDate(5, date);
        try {
            pst.executeUpdate();
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }

    /** tính tổng tiền theo tháng. */
    public int sum(int month, int year) throws SQLException {
        final String count = "select money from data where extract(month from time) = ? and extract(year from time) = ?";
        int Sum = 0;
        pst = connect.prepareStatement(count);
        pst.setInt(1, month);
        pst.setInt(2, year);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Sum += rs.getInt(1);
        }
        return Sum;
    }

    /** update data trong type. */
    public void updateType(final String type, final int money) throws SQLException {
        final String update = "update data set money = ? where type = ?";
        pst = connect.prepareStatement(update);
        pst.setInt(1, money);
        pst.setString(2, type);
        pst.executeUpdate();
    }

    /** kiểm tra xem data có null ko. */
    public boolean check(final String type, int year, int month) throws SQLException {
        boolean ok = false;
        final String checkNull = "select money where type = ? and extract(month from time) = ? and extract(year from time) = ?";
        pst = connect.prepareStatement(checkNull);
        pst.setString(1, type);
        pst.setInt(2, month);
        pst.setInt(3, year);
        ResultSet rs = pst.executeQuery();
        ok = rs.next();
        return ok;
    }

    /** TRẢ VỀ LIST GIÁ TRỊ THEO THÁNG NĂM */
    public List<Type> getHistory(int month, int year) throws SQLException {
        final String count = "select money from fm where extract(month from time) = ? and extract(year from time) = ?";
        pst = connect.prepareStatement(count);
        pst.setInt(1, month);
        pst.setInt(2, year);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            History.add(new Type(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
        }
        return History;
    }

    /** thêm lịch sử. */
    public void addHistory(final String type, final int money, final String imageSource, final String buttonColor, int year, int month, int day) throws SQLException {
        final String addType = "insert into history (type, money, imageSource, buttonColor, time) values (?, ?, ?, ?, ?)";

        // chuyển util.date thành sql.date
        Calendar myCal = Calendar.getInstance();
        myCal.set(Calendar.YEAR, year);
        myCal.set(Calendar.MONTH, month - 1);
        myCal.set(Calendar.DAY_OF_MONTH, day);
        java.util.Date d = myCal.getTime();
        java.sql.Date date = new java.sql.Date(d.getTime());

        pst = connect.prepareStatement(addType);
        pst.setString(1, type);
        pst.setInt(2, money);
        pst.setString(3, imageSource);
        pst.setString(4, buttonColor);
        pst.setDate(5, date);
        try {
            pst.executeUpdate();
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }

    /* trả về list gồm type, money và time theo type + time.
    public List<Type> findData(final String type, int month, int year) throws SQLException {
        final String checkData = "select type, money, time where type = ? and extract(month from time) = ? and extract(year from time) = ?";
        pst = connect.prepareStatement(checkData);
        pst.setString(1, type);
        pst.setInt(2, month);
        pst.setInt(3, year);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            data.add(new Type(rs.getString(1), rs.getInt(2), rs.getObject(3, LocalDate.class)));
        }
        return data;
    }
     */

    /* test main
    public static void main (String[] args) throws SQLException, ClassNotFoundException {

        SQL sql = new SQL(); //tạo một kết nối sql mới

        //-------thêm type (food, abc, green).
        String s1 = "food";
        String s2 = "abc";
        String s3 = "Green";
        sql.addType(s1,0, s2, s3);

        //-------xóa type 1.
        sql.deleteType("food");


        //-------thêm type (water, def, Blue).
                String s4 = "water";
        String s5 = "def";
        String s6 = "Blue";
        sql.addType(s4, s5, s6);

        //--------in ra tổng tiền theo tháng
        System.out.println(sql.sum(11, 2002));

        //--------in ra list gd với tham số đầu vào là local date
        List<Type> lt = sql.getList(LocalDate.parse("2002-11-22"));
        for (int i = 0; i < lt.size(); i++) {
            System.out.println(lt.get(i) + "\n");
        }

        List<Type> sqlHis = sql.getHistory(11, 2002);
        for (int i = 0; i < sqlHis.size(); i++) {
            System.out.println(sqlHis.get(i) + "\n");
        }
    }
     */
}
