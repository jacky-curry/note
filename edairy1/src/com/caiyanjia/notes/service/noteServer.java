package com.caiyanjia.notes.service;

import com.caiyanjia.notes.dao.Impl.groupDaoImpl;
import com.caiyanjia.notes.dao.Impl.noteDaoImpl;
import com.caiyanjia.notes.entity.Group;
import com.caiyanjia.notes.entity.Note;
import com.caiyanjia.notes.entity.thumbUp;
import com.caiyanjia.notes.util.JDBCUtils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class noteServer extends noteDaoImpl {
    groupDaoImpl groupdaoimpl = new groupDaoImpl();
    //点赞事件的处理
    public int likeChange(int choice,String label){
        Connection connection = JDBCUtils.getConnection();
        if(choice == 1){
            likeIncrease(connection,label);
            return 1;
        }else{
            likeDecrease(connection,label);
            return 0;
        }
    }

    //笔记的删除
    public Boolean deleteNote(Connection conn, String label,String user_id){
       return noteDelete(conn,label,user_id);
    }
    //笔记分组的删除
    public Boolean deleteGroup(Connection conn,String name,String user_id){
        //将note表中的分组设置为默认分组


        //删除group表中的分组
        if(groupdaoimpl.deleteGroup(conn,name,user_id)) {
            return true;
        }else{
            return false;
        }
    }

    //笔记分组名称的修改
    public Boolean updateGroupName(Connection conn,String oldName,String newName){
        return groupdaoimpl.changName(conn,oldName,newName);
    }

    //创建根分组
    public void insert(Connection conn,String root,String user_id){
        String sql = "insert `group`(`name`,user_id) values(?,?)";
        this.Update(conn,sql,root,user_id);
    }

    public Integer getroot(Connection conn,String root){
        String sql = "select * from `group` where `name` = ?";
        return getValue(conn,sql,root);
    }




    //查找同一个user_id的笔记分组名称或者笔记分组下的笔记
    //1:表示查分组名称   2: 表示查分组中的笔记

    /**
     *
     * @param conn
     * @param user_id
     * @param
     * @param choice    1：查找user_id下的分组名  2：查找根分组中笔记  3：查找其他分组中的笔记
     * @param <T>
     * @return
     */
    public <T> List<T> searchNoteFromGroup(Connection conn,String user_id , String group_name,int choice){
        if(choice == 1){
            return  (List<T>)groupdaoimpl.searchGroup(conn,user_id);
        }
        if(choice == 2){
            return (List<T>) getNoteFromGroup(conn,null,user_id);
        }
        if(choice == 3){
            return (List<T>) getNoteFromGroup(conn,group_name,user_id);
        }
        return null;
    }

    //创建分组
    public Boolean createGroup(Connection conn, Group group){
        return groupdaoimpl.insertGroup(conn,group);
    }

    //将更改后的note对象保存到数据库中
    public Boolean changNote(Connection conn,Note note,String oldLabel,String user_id){
        return noteUpdateAll(conn,note,oldLabel,user_id);
    }


    public Boolean newNote(Connection conn, String label, String author, String content, Date date , Boolean permission, String user_id){
        return noteInsert(conn,label,author,content,new Date(),permission,user_id);
    }

    static public List<Note> getNoteById(Connection conn,String user_id) {
        String sql = "select label,author,date,`like`,permission,content from note where user_id = ?";

        return getForList(conn,Note.class,sql,user_id);

    }

   static public List<Note> getNoteGroupByForm(Connection conn, String group_name , String user_id){
        String sql1 = "select `id` from `group` where `name` = ?";
        int group_id = getValue(conn,sql1,group_name);

        System.out.println(group_id);

        String sql = "select * from note where group_id = ? and user_id = ?";

        return getForList(conn,Note.class,sql,group_id,user_id);
    }

    //查询user_id下的分组名称
    public List<String> searchGroup(Connection conn, String user_id) {
        String sql = "select `name` from `group` where user_id = ?";

        List<String> list = new ArrayList<>();
        for(Group p: Objects.requireNonNull(getForList(conn, Group.class, sql, user_id))){
            list.add(p.getName());
        }


        return list;
    }

    //*************点赞操作**********

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
