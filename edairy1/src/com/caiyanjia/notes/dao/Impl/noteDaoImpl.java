package com.caiyanjia.notes.dao.Impl;

import com.caiyanjia.notes.bean.Note;
import com.caiyanjia.notes.dao.Dao.BaseDAO;
import com.caiyanjia.notes.dao.Dao.noteDao;
import javafx.scene.control.TreeItem;
import org.omg.CORBA.NO_IMPLEMENT;

import javax.swing.*;
import javax.xml.soap.Node;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class noteDaoImpl extends BaseDAO implements noteDao {




	@Override
	public void noteInsert(Connection conn, String label, String author, String content, Date date ,Boolean permission,String user_id) {
		String sqlString = "insert into note(label,author,content,date,permission,user_id) values(?,?,?,?,?,?)";
				
		if(Update(conn, sqlString, label,author,content,date,permission,user_id) > 0) {
			System.out.println("添加笔记成功");
		}else {
			System.out.println("添加失败");
		}
		
	}

	@Override
	public void noteDelete(Connection conn, String label,String user_id) {
		String sqlString = "delete from note  where label = ? and user_id = ?";
		if(Update(conn, sqlString, label,user_id)>0) {
			System.out.println("删除成功");
		}else {
			System.out.println("删除失败");
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
		String sql = "Update note set parent_node = ? where label = ? ";
		if(Update(conn,sql,group,label)>0){
			System.out.println("设置分组成功");
		}else{
			System.out.println("设置分组失败");
		}
	}

	@Override
	public void noteUpdateAll(Connection conn, Note note,String oldlabel,String user_id) {
		String sql = "Update note set label = ?,content = ?,permission = ?,author = ?,`date` = ? where label = ? and user_id = ?";
		Update(conn,sql,note.getLabel(),note.getContent(), note.isPermission(),note.getAuthor(),note.getDate(),oldlabel,user_id);
	}

	/**
	 *获得笔记的默认分组
	 */

	public String getGroup(Connection conn,String label,String user_id){
		String sql = "select `parent_node` from note where label = ? and user_id = ?";
		return getValue(conn,sql,label,user_id);

	}

	public List<Note> getNoteFromGroup(Connection conn, String group , String user_id){
		String sql = "select * from note where parent_node = ? and user_id = ?";

		List<Note> noteList = getForList(conn,Note.class,sql,group,user_id);
//		List<String> stringList = new ArrayList<>();
//		assert noteList != null;
//			for (int i = 0; i < noteList.size(); i++) {
//				stringList.set(i,noteList.get(i).getLabel());
//			}

		return noteList;
	}


	public void deleteGroup(Connection conn,String oldGroup,String user_id){
		String sql = "Update note set parent_node = ? where parent_node = ? and user_id = ?";
		Update(conn,sql,"0",oldGroup,user_id);


	}


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
