package com.hnv.db.aut.vi;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.common.tool.ToolSet;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.per.vi.ViPerPersonDyn;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.def.DefDBExt;

@Entity
@Table(name = DefDBExt.TA_AUT_USER )
public class ViAutUserDyn extends EntityAbstract<ViAutUserDyn> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	public static final int 	TYPE_01_ADM_ALL		= 100;
	public static final int 	TYPE_01_SUP_ADM		= 1;
	public static final int 	TYPE_01_ADM			= 2;
	public static final int 	TYPE_01_AGENT		= 3;
//	
//	public static final int 	TYPE_01_MENTOR     	= 6;
//	public static final int 	TYPE_01_CLIENT		= 4;
//	
//	public static final int 	TYPE_01_VISITOR		= 10;
	

	public static final int 	TYPE_02_CAND		= 100;
	public static final int 	TYPE_02_RECR		= 200;
	
	
	public static final int		STAT_INACTIVE 	= 0;
	public static final int		STAT_ACTIVE 	= 1;
	public static final int		STAT_REVIEW 	= 5;
	public static final int		STAT_DELETED 	= 10;

	// ---------------------------List of Column from
	// DB-----------------------------
	public static final String COL_I_ID 			= "I_ID";
	public static final String COL_T_LOGIN_01		= "T_Login_01";
	public static final String COL_T_LOGIN_02		= "T_Login_02";
	
	public static final String COL_T_PASS_01		= "T_Pass_01";
	public static final String COL_T_PASS_02		= "T_Pass_02";
	
	public static final String COL_I_STATUS 		= "I_Status";
	
	public static final String COL_I_TYPE_01 		= "I_Type_01";
	public static final String COL_I_TYPE_02 		= "I_Type_02";
	
	public static final String COL_T_INFO_01 		= "T_Info_01"; //email
	public static final String COL_T_INFO_02 		= "T_Info_02"; //tel
	public static final String COL_T_INFO_03 		= "T_Info_03";
	public static final String COL_T_INFO_04 		= "T_Info_04";
	public static final String COL_T_INFO_05 		= "T_Info_05";
	
	public static final String COL_D_DATE_01 		= "D_Date_01"; //date new
	public static final String COL_D_DATE_02 		= "D_Date_02"; //date mod
	public static final String COL_D_DATE_03 		= "D_Date_03"; //date validation
	
	public static final String COL_I_AUT_USER_01 	= "I_Aut_User_01"; //user new
	public static final String COL_I_AUT_USER_02 	= "I_Aut_User_02"; //user mod
	public static final String COL_I_AUT_USER_03 	= "I_Aut_User_03"; //supervisor
	
	public static final String COL_I_PER_MANAGER 	= "I_Per_Manager";
	public static final String COL_I_PER_PERSON 	= "I_Per_Person";

	// ---------------------------List of ATTR of class-----------------------------
	public static final String ATT_I_ID 			= "I_ID";
	public static final String ATT_T_LOGIN_01		= "T_Login_01";
	public static final String ATT_T_LOGIN_02		= "T_Login_02";
	
	public static final String ATT_T_PASS_01		= "T_Pass_01";
	public static final String ATT_T_PASS_02		= "T_Pass_02";
	
	public static final String ATT_I_STATUS 		= "I_Status";
	
	public static final String ATT_I_TYPE_01 		= "I_Type_01";
	public static final String ATT_I_TYPE_02 		= "I_Type_02";
	
	public static final String ATT_D_DATE_01 		= "D_Date_01";
	public static final String ATT_D_DATE_02 		= "D_Date_02";
	public static final String ATT_D_DATE_03 		= "D_Date_03";
	public static final String ATT_T_INFO_01 		= "T_Info_01";
	public static final String ATT_T_INFO_02 		= "T_Info_02";
	public static final String ATT_T_INFO_03 		= "T_Info_03";
	public static final String ATT_T_INFO_04 		= "T_Info_04";
	public static final String ATT_T_INFO_05 		= "T_Info_05";
	public static final String ATT_I_AUT_USER_01 	= "I_Aut_User_01";
	public static final String ATT_I_AUT_USER_02 	= "I_Aut_User_02";
	public static final String ATT_I_AUT_USER_03 	= "I_Aut_User_03";
	public static final String ATT_I_PER_MANAGER 	= "I_Per_Manager";
	public static final String ATT_I_PER_PERSON 	= "I_Per_Person";

	public static final String ATT_O_ROLES 			= "O_Roles";
	public static final String ATT_O_RIGHTS 		= "O_Rights";
	
	public static final String ATT_O_PER_MANAGER 	= "O_Per_Manager";
	public static final String ATT_O_PER_PERSON     = "O_Per_Person";
	
	public static final String ATT_O_DOCUMENTS 		= "O_Documents";
	public static final String ATT_O_AVATAR	        = "O_Avatar";
//	public static final String ATT_O_MANAGER        = "O_Manager";
//	public static final String ATT_O_SUPERIOR	    = "O_Superior";
	

	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, false, false, false, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 

	public		static 	final EntityDAO<ViAutUserDyn> 	DAO;
	static{
		DAO = new EntityDAO<ViAutUserDyn>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), ViAutUserDyn.class, RIGHTS);
	}

	//-----------------------Class Attributes-------------------------
	@Id
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;

	@Column(name=COL_T_LOGIN_01, nullable = false)
	private	String          T_Login_01;

	@Column(name=COL_T_LOGIN_02, nullable = false)
	private	String          T_Login_02;

	@Column(name=COL_I_STATUS, nullable = true)
	private	Integer         I_Status;

	@Column(name=COL_I_TYPE_01, nullable = true)
	private	Integer         I_Type_01;

	@Column(name=COL_I_TYPE_02, nullable = true)
	private	Integer         I_Type_02;
	

	@Column(name=COL_T_INFO_01, nullable = true)
	private	String            T_Info_01;

	@Column(name=COL_T_INFO_02, nullable = true)
	private	String            T_Info_02;
	
	@Column(name=COL_D_DATE_01, nullable = true)
	private	Date              D_Date_01;

	@Column(name=COL_T_INFO_03, nullable = true)
	private	String            T_Info_03;
//
//	@Column(name=COL_T_INFO_04, nullable = true)
//	private	String            T_Info_04;
//
//	@Column(name=COL_T_INFO_05, nullable = true)
//	private	String            T_Info_05;

	@Column(name=COL_I_AUT_USER_03, nullable = true)
	private	Integer            I_Aut_User_03;

	@Column(name=COL_I_PER_MANAGER, nullable = true)
	private	Integer            I_Per_Manager;

	@Column(name=COL_I_PER_PERSON, nullable = true)
	private	Integer            I_Per_Person;
	//-----------------------Transient Variables-------------------------
	@Transient
	private TaTpyDocument 	            O_Avatar;
	
//	@Transient
//	private	List<TaTpyDocument> 		O_Documents;
//
//	@Transient
//	private	TaPerPerson					O_Per_Manager;
//	
	@Transient
	private	ViPerPersonDyn				O_Per_Person;

	//-----------------------------------------------------------------------
	private ViAutUserDyn(){}

	@Override
	public void doMergeWith(ViAutUserDyn arg0) {
	}


	public Integer reqPerManagerId() {
		return this.I_Per_Manager;
	}

	public boolean canBeSuperAdmin() {
		return this.I_Type_01.equals(TYPE_01_SUP_ADM);
	}
	
	public boolean canBeAdmin() {
		return this.I_Type_01.equals(TYPE_01_ADM);
	}

	@Override
	public Serializable reqRef() {
		return I_ID;
	}
	
	public void doBuildAvatar(boolean forced) throws Exception {
		if (this.O_Avatar != null && !forced) return;

		List<TaTpyDocument> lst = TaTpyDocument.DAO.reqList(
				Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_TYPE	, DefDBExt.ID_TA_AUT_USER),
				Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_ID	, this.I_Per_Person),
				Restrictions.eq(TaTpyDocument.ATT_I_TYPE_01		, TaTpyDocument.TYPE_01_FILE_MEDIA),
				Restrictions.eq(TaTpyDocument.ATT_I_TYPE_02		, TaTpyDocument.TYPE_02_FILE_IMG_AVATAR)
				);
		if (lst!=null && lst.size()>0) {
			this.O_Avatar = lst.get(0);
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------------------
	public static void doBuildAvatarForList(List<ViAutUserDyn>  list) throws Exception {
		if (list==null || list.size()==0) return;
		TaTpyDocument.reqBuildAvatar(list, DefDBExt.ID_TA_AUT_USER, ATT_O_AVATAR);
	}
		
	public static void doBuildPer(List<ViAutUserDyn> usersLst) throws Exception {
		Set<Integer> ids = ToolSet.reqSetInt(usersLst, ViAutUserDyn.ATT_I_PER_PERSON);
		if(!ids.isEmpty()) {
			Map<Integer, ViPerPersonDyn> mapPer = new HashMap<>();
			Set<Integer> stats = new HashSet<>();
			stats.add(ViPerPersonDyn.STAT_01_ACTIVE_LV_01);
			stats.add(ViPerPersonDyn.STAT_01_ACTIVE_LV_02);
			stats.add(ViPerPersonDyn.STAT_01_ACTIVE_LV_03);
			
			List<ViPerPersonDyn> pers = ViPerPersonDyn.DAO.reqList_In(ViPerPersonDyn.ATT_I_ID, ids, 
					Restrictions.and(
							Restrictions.in(ViPerPersonDyn.ATT_I_STATUS_01, stats)));
			
			if(pers == null || pers.isEmpty()) return;

			for(ViPerPersonDyn p : pers) {
				mapPer.put((Integer) p.req(ViPerPersonDyn.ATT_I_ID), p);
			}
			for(ViAutUserDyn u : usersLst) {
				if(mapPer.containsKey(u.req(ATT_I_PER_PERSON))){
					u.reqSet(ViAutUserDyn.ATT_O_PER_PERSON, mapPer.get(u.req(ATT_I_PER_PERSON)));
				}
			}
		}
	}
	public static Number reqCountUserFilter(Criterion cri) throws Exception {
		return ViAutUserDyn.DAO.reqCount(cri);
	}

}
