package com.caiyanjia.notes.dao.Impl;

import com.caiyanjia.notes.entity.Note;
import com.caiyanjia.notes.dao.Dao.BaseDAO;
import com.caiyanjia.notes.dao.Dao.noteDao;
import com.caiyanjia.notes.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class noteDaoImpl extends BaseDAO implements noteDao {




	@Override
	public Boolean noteInsert(Connection conn, String label, String author, String content, Date date ,Boolean permission,String user_id) {
		String sqlString = "insert into note(label,author,content,date,permission,user_id ) values(?,?,?,?,?,?)";
				
		if(Update(conn, sqlString, label,author,content,date,permission,user_id) > 0) {
			System.out.println("添加笔记成功");
			return  true;
		}else {
			System.out.println("添加失败");
			return false;
		}
		
	}

	@Override
	public Boolean noteDelete(Connection conn, String label,String user_id) {
		String sqlString = "delete from note  where label = ? and user_id = ?";
		if(Update(conn, sqlString, label,user_id)>0) {
			System.out.println("删除成功");
			return true;
		}else {
			System.out.println("删除失败");
			return  false;
		}
			
		
		
	}

	/**
	 * 查找一条笔记信息
	 * @param conn
	 * @param label
	 * @return note的一条信息
	 */
	@Override
	public Note noteSearch(Connection conn, String label) {
		String sql = "Select `label`,author,`date`,`like`,content,permission from `note` where permission = ? and `label` = ?";

		return getInstance(conn, Note.class,sql,true,label);

	}

	@Override
	public boolean noteUpdate(Connection conn, String content, String label) {
		String sql = "Update note set content = ? where label = ?";
		if(Update(conn,sql,content,label) > 0)
			return true;
		return false;
	}

	@Override
	public List<Note> getAllnote(Connection conn) {
		String sql = "Select label,author,date,`like`,permission,content from note where permission = ?";


		return getForList(conn, Note.class,sql,true);
	}



	@Override
	public List<Note> getIdNote(Connection conn,String user_id) {
		String sql = "select label,author,date,`like`,permission,content from note where user_id = ?";

		return getForList(conn,Note.class,sql,user_id);

	}

	@Override
	public void setGroup(Connection conn, String label, String group) {
		String sql1 = "select id  from `group` where `name` = ?";
		int group_id = getValue(conn,sql1,group);

		String sql = "Update note set group_id = ? where label = ? ";
		if(Update(conn,sql,group_id,label)>0){
			System.out.println("设置分组成功");
		}else{
			System.out.println("设置分组失败");
		}
	}

	@Override
	public Boolean noteUpdateAll(Connection conn, Note note,String oldlabel,String user_id) {
		String sql = "Update note set label = ?,content = ?,permission = ?,author = ?,`date` = ? where label = ? and user_id = ?";
		if(Update(conn,sql,note.getLabel(),note.getContent(), note.isPermission(),note.getAuthor(),note.getDate(),oldlabel,user_id)>0){
			return true;
		}else {
			return  false;
		}
	}

	/**
	 *获得笔记的默认分组
	 */

	public <E> Object getGroup(Connection conn, String label, String user_id){


		String sql = "select `group_id` from note where label = ? and user_id = ?";
		if(getValue(conn,sql,label,user_id)!= null){
			Integer group_id = getValue(conn,sql,label,user_id);
			String sql1 ="select `name` from `group` where id = ?";
			return getValue(conn,sql1,group_id);
		}
		return null;

	}

	public List<Note> getNoteFromGroup(Connection conn, String group_name , String user_id){
		String sql = "select * from note where group_id = ? and user_id = ?";

		String sql1 = "select `id` from `group` where `name` = ?";
		Integer group_id = 0;
		//第一种有名字的
		if(getValue(conn,sql1,group_name)!=null){
			 group_id = getValue(conn,sql1,group_name);
			return getForList(conn,Note.class,sql,group_id,user_id);
		}

		String sql2 = "select id from `group` where `name` = ?";
		Integer root = getValue(conn,sql2,"知识库");


		return getForList(conn,Note.class,sql,root,user_id);
	}


	public Boolean deleteGroup(Connection conn,String oldGroup,String user_id){
		String sql1 = "select id where name = ?";
		int groupId  = getValue(conn,sql1,oldGroup);

		String sql = "Update note set parent_node = ? where id = ? and user_id = ?";
		if(Update(conn,sql,"0",groupId,user_id) >0 ){
			return true;
		}else{
			return false;
		}

	}
//*******************以上是分组************

//********************以下是查询*************
	public List<Note> searchByLabel(Connection conn, String label){
		String sql  = "select * from note where label = ?";
		return getForList(conn,Note.class,sql,label);

	}

	public List<Note> searchByAuthor(Connection conn, String author){
		String sql = "select * from note where author = ?";
		return getForList(conn,Note.class,sql,author);
	}

	@Override
	public void likeIncrease(Connection conn , String label) {
		String sql  = "Update note set `like` = `like` + 1 where label = ?";

		if(Update(conn,sql,label)>0){
			System.out.println("点赞成功");
		}else{
			System.out.println("点赞失败");
		}
	}


	public void likeDecrease(Connection conn,String label){
		String sql = "Update note set `like` = `like` - 1 where label = ?";
		Update(conn,sql,label);
	}




}
