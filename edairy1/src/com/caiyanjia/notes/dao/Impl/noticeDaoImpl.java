package com.caiyanjia.notes.dao.Impl;

import com.caiyanjia.notes.bean.Notice;
import com.caiyanjia.notes.dao.Dao.BaseDAO;
import com.caiyanjia.notes.dao.Dao.noticeDao;

import java.sql.Connection;
import java.util.Objects;

public class noticeDaoImpl extends BaseDAO implements noticeDao {

    @Override
    public void insert(Connection conn, String notice) {
        String sql = "insert into `notice`(notice) values(?) ";
        if(Update(conn,sql,notice)>0){
            System.out.println("插入公告成功");
        }else{
            System.out.println("插入公告失败");
        }
    }

    @Override
    public String getNotice(Connection conn) {
        String sql  = "select * from `notice`";
        if(getInstance(conn, Notice.class, sql) != null){
            System.out.println("读取成功");
            return Objects.requireNonNull(getInstance(conn, Notice.class, sql)).getNotice();
        }else{
            System.out.println("读取失败");
            return null;
        }
    }
}
