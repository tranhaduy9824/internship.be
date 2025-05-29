package com.hnv.db.nso.vi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.aut.TaAutUser;
import com.hnv.db.nso.TaNsoGroupMember;
import com.hnv.db.per.TaPerPerson;
import com.hnv.def.DefDBExt;

@Entity
public class ViNsoGroupMember extends EntityAbstract<ViNsoGroupMember>{
	
	private static final long serialVersionUID = 1L;
	
	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_I_NSO_GROUP                           =	"I_Group";
	public static final String	COL_I_STATUS                          =	"I_Status";
	public static final String	COL_I_TYPE                            =	"I_Type";
	public static final String	COL_D_DATE_01                         =	"D_Date_01";
	public static final String	COL_D_DATE_02                         =	"D_Date_02";
	public static final String	COL_I_AUT_USER                        =	"I_Aut_User";
	public static final String	COL_T_LOGIN_01                           =	"T_Login";
	public static final String	COL_T_NAME_01                         =	"T_Name_01";
	public static final String	COL_T_NAME_02                         =	"T_Name_02";
	public static final String	COL_T_NAME_03                         =	"T_Name_03";
//	public static final String	COL_T_AVATAR                     	  =	"T_Avatar";
	
	//---------------------------List of Att-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_I_GROUP                           =	"I_Group";
	public static final String	ATT_I_STATUS                          =	"I_Status";
	public static final String	ATT_I_TYPE                            =	"I_Type";
	public static final String	ATT_D_DATE_01                         =	"D_Date_01";
	public static final String	ATT_D_DATE_02                         =	"D_Date_02";
	public static final String	ATT_I_AUT_USER                        =	"I_Aut_User";
	public static final String	ATT_T_LOGIN                           =	"T_Login";
	public static final String	ATT_T_NAME_01                         =	"T_Name_01";
	public static final String	ATT_T_NAME_02                         =	"T_Name_02";
	public static final String	ATT_T_NAME_03                         =	"T_Name_03";
//	public static final String	ATT_T_AVATAR                     	  =	"T_Avatar";
	
	
	private 	static 	final boolean[] 			RIGHTS		= {true, false, false, false, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static	final boolean				API_CACHE 	= false;
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<ViNsoGroupMember> 	DAO;
	
	static{
		DAO = new EntityDAO<ViNsoGroupMember>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), ViNsoGroupMember.class,RIGHTS);	
	}

	@Id
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_I_NSO_GROUP, nullable = true)
	private	Integer         I_Group;
   
	@Column(name=COL_I_STATUS, nullable = true)
	private	Integer         I_Status;
     
	@Column(name=COL_I_TYPE, nullable = true)
	private	Integer         I_Type;
       
//	@Column(name=COL_D_DATE_01, nullable = true)
//	private	Date            D_Date_01;
//    
//	@Column(name=COL_D_DATE_02, nullable = true)
//	private	Date            D_Date_02;
	@Column(name=COL_I_AUT_USER, nullable = true)
	private	Integer         I_Aut_User;
	
	@Column(name=COL_T_LOGIN_01, nullable = false)
	private	String          T_Login;
	
	@Column(name=COL_T_NAME_01, nullable = true)
	private	String          T_Name_01;

	@Column(name=COL_T_NAME_02, nullable = true)
	private	String          T_Name_02;

	@Column(name=COL_T_NAME_03, nullable = true)
	private	String          T_Name_03;
	
//	@Column(name=COL_T_AVATAR, nullable = true)
//	private	String          T_Avatar;
	
	public ViNsoGroupMember() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Serializable reqRef() {
		return this.I_ID;
	}
	
	@Override
	public void doMergeWith(ViNsoGroupMember arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		ok = (I_ID == ((ViNsoGroupMember)o).I_ID);
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;
	}
	
	private static final String SQL_REQ_LIST_SELECT = "SELECT"
			+ " g."   + ViNsoGroupMember.COL_I_ID		+ " AS " +COL_I_ID   	+ ","
			+ " g."   + TaNsoGroupMember.COL_I_NSO_GROUP	+ " AS " +COL_I_NSO_GROUP   + ","
			+ " g."   + TaNsoGroupMember.COL_I_STATUS 	+ " AS " +COL_I_STATUS  + ","
			+ " g."   + TaNsoGroupMember.COL_I_TYPE 	+ " AS " +COL_I_TYPE 	+ ","
			+ " g."   + TaNsoGroupMember.COL_I_AUT_USER	+ " AS " +COL_I_AUT_USER+ ","
			+ " u."   + TaAutUser.COL_T_LOGIN_01	 		+ " AS " +COL_T_LOGIN_01 	+ ","
			+ " p."   + TaPerPerson.COL_T_NAME_01	 	+ " AS " +COL_T_NAME_01 + ","
			+ " p."   + TaPerPerson.COL_T_NAME_02	 	+ " AS " +COL_T_NAME_02 + ","
			+ " p."   + TaPerPerson.COL_T_NAME_03	 	+ " AS " +COL_T_NAME_03;
//			+ " d."   + TaTpyDocument.COL_T_PATH_01	 	+ " AS " +COL_T_AVATAR;
	
	private static final String SQL_REQ_LIST_FROM	= " FROM "	+ DefDBExt.TA_NSO_GROUP_MEMBER	+ " g";
	
	private static final String SQL_REQ_LIST_COUNT 	= "SELECT count(DISTINCT(u."+ ViNsoGroupMember.COL_T_LOGIN_01	+"))";
	
	private static final String SQL_REQ_LIST_GROUP 	= "SELECT * "
														+ "FROM "+ DefDBExt.TA_NSO_GROUP_MEMBER + " "
														+ "WHERE " + TaNsoGroupMember.COL_I_AUT_USER +" = %d";
	private static final String SQL_REQ_LIST_JOIN	= " INNER JOIN "+DefDBExt.TA_AUT_USER + " u"
			+ " ON g." + TaNsoGroupMember.COL_I_AUT_USER + " = u." + TaAutUser.COL_I_ID
			+ " INNER JOIN "+DefDBExt.TA_PER_PERSON + " p"
			+ " ON u." + TaAutUser.COL_I_PER_PERSON+ " = p." + TaPerPerson.COL_I_ID;
//			+ " INNER JOIN "+com.hnv.tool.db.tpy_cfg.config.DefDBExt.TA_TPY_DOCUMENT + " d"
//			+ " ON u." + TaAutUser.COL_I_PER_PERSON + " = d." +TaTpyDocument.COL_I_PARENT_ID;
	private static final String SQL_REQ_WHERE		= " WHERE g."+ TaNsoGroupMember.COL_I_AUT_USER +"!= %d";
	private static final String SQL_REQ_GROUP_BY	= " GROUP BY " + ViNsoGroupMember.COL_T_LOGIN_01	;
	
	private static String reqMemberListSql(Integer userId) {
		String sql = SQL_REQ_LIST_SELECT;
		sql += SQL_REQ_LIST_FROM;
		sql += SQL_REQ_LIST_JOIN;
//		sql += String.format(SQL_REQ_WHERE, userId);
//		sql += SQL_REQ_GROUP_BY;
		return sql;
	}
	
	public static List<ViNsoGroupMember> reqMemberList(Integer userId) throws Exception {
		List<ViNsoGroupMember> 	list 	= new ArrayList<ViNsoGroupMember>();		
		String sql = reqMemberListSql(userId);		
		list = ViNsoGroupMember.DAO.reqList(sql);
			
		return list;
	}

	private static String reqMemberListCountSql(Integer userId) {
		String sql = SQL_REQ_LIST_COUNT;
		sql += SQL_REQ_LIST_FROM;
		sql += SQL_REQ_LIST_JOIN;
//		sql += String.format(SQL_REQ_WHERE, userId);
//		sql += SQL_REQ_GROUP_BY;
		return sql;
	}
	
	public static Integer reqMemberListCount(Integer userId) throws Exception {
		String sql = reqMemberListCountSql(userId);
		int count = ViNsoGroupMember.DAO.reqCount(sql).intValue();
		return count;
	}
	
	private static String reqIDByUserSql(Integer userId) {
		String sql = String.format(SQL_REQ_LIST_GROUP, userId);
		return sql;
	}
	
	public static List<ViNsoGroupMember> reqIDByUser(Integer userId) throws Exception {
		List<ViNsoGroupMember> list = new ArrayList<ViNsoGroupMember>();
		String sql = reqIDByUserSql(userId);
		list = ViNsoGroupMember.DAO.reqList(sql);
		return list;
	}
}
