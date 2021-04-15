package com.caiyanjia.notes.dao.Impl;

import com.caiyanjia.notes.dao.Dao.BaseDAO;
import com.caiyanjia.notes.entity.thumbUp;

import java.sql.Connection;

public class thumbUpDao extends BaseDAO {
    //点赞后存入对应的user_id 和 note
    public Boolean thumbUp(Connection conn,String user_id,String note){
        String sql = "insert into thumbup(user_id,note_name) values(?,?)";
        if(Update(conn,sql,user_id,note)>0){
            return true;
        }else{
            return false;
        }
    }

    //取消点赞
    public Boolean cancelThumbUp(Connection conn,String user_id,String note){
        String sql = "delete from thumbup where user_id = ? and note_name = ?";
        if(Update(conn,sql,user_id,note)>0){
            return true;
        }else{
            return false;
        }
    }



    //传入user_id和note在thumbup查找，如果找到的话就返回true：表示已经点赞，否则，就还没点赞
    public Boolean ifThumbUp(Connection conn,String user_id,String note){
        String sql = "select * from thumbup where user_id = ? and note_name = ?";
        if(getInstance(conn, thumbUp.class,sql,user_id,note)!=null){
            return true;
        }else {
            return false;
        }


    }



}
