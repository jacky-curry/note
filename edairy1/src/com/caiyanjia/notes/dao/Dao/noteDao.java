package com.caiyanjia.notes.dao.Dao;

import com.caiyanjia.notes.bean.Note;
import com.caiyanjia.notes.controller.noteDate;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public interface noteDao {
	/**
	 * 
	 * @Title: noteInsert
	 * @Description: 笔记的添加
	 * @param label	笔记的
	 * @param author 作者user的name
	 * @param content	笔记内容
	 * @author author	
	 * @date 2021-03-28 11:16:43
	 */
	public void noteInsert(Connection conn, String label, String author, String content, Date date,Boolean promisstion,String user_id);
	
	public void noteDelete(Connection conn, String label,String user_id);
	/**
	 * 
	 * @Title: noteSearch
	 * @Description: 查找笔记，并将其数据返回
	 * @param conn
	 * @param label
	 * @return
	 * @author author
	 * @date 2021-03-28 11:25:45
	 */
	public Note noteSearch(Connection conn, String label);
	/**
	 * 
	 * @Title: noteUpdate
	 * @Description: 修改笔记内容
	 * @param conn
	 * @param content	修改后的内容传入
	 * @param label	
	 * @return	返回修改后的note对象
	 * @author author
	 * @date 2021-03-28 11:24:00
	 */
	public boolean noteUpdate(Connection conn,String content,String label);

	/**
	 *返回所有的笔记数据
	 * @param conn
	 * @return
	 */
	public List<Note> getAllnote(Connection conn);


	/**
	 * 返回当前id号的所有笔记
	 */

	public List<Note> getIdNote(Connection conn,String user_id);

	/**
	 *
	 * @param conn
	 * @param label
	 * @param group
	 */
	public void setGroup(Connection conn,String label,String group);


	public void noteUpdateAll(Connection conn,Note note,String oldlabel,String user_id);


	/**
	 * 用于删除note中group
	 * @param conn
	 * @param oldGroup
	 * @param user_id
	 */
	public void deleteGroup(Connection conn,String oldGroup,String user_id);

	/**
	 * 通过label进行查询
	 *
	 * @param conn
	 * @param label
	 * @return
	 */
	public List<Note> searchByLabel(Connection conn, String label);


	public List<Note> searchByAuthor(Connection conn, String author);

	public void likeIncrease(Connection conn , String label);

	public void likeDecrease(Connection conn,String label);

}
