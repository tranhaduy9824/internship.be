package com.hnv.db.aut;

//import java.lang.String;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.common.tool.ToolSet;
import com.hnv.data.json.JSONObject;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.aut.vi.ViAutAuthUserDyn;
import com.hnv.db.aut.vi.ViAutUserDyn;
import com.hnv.db.aut.vi.ViAutUserMember;
import com.hnv.db.nso.TaNsoGroup;
import com.hnv.db.nso.TaNsoGroupMember;
import com.hnv.db.per.TaPerPerson;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.def.DefDBExt;

@Entity
@Table(name = DefDBExt.TA_AUT_USER )
public class TaAutUser extends EntityAbstract<TaAutUser> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int  ENT_TYP				= DefDBExt.ID_TA_AUT_USER;

	public static final int 	TYPE_01_SUP_ADM		= 1;
	public static final int 	TYPE_01_ADM			= 2;
	
	public static final int 	TYPE_01_DOCTOR		= 20;
	public static final int 	TYPE_01_AGENT		= 30;
	public static final int 	TYPE_01_CLIENT		= 40;
	
	public static final int 	TYPE_02_RECRUITER	= 100;
	public static final int 	TYPE_02_CANDIDATE	= 200;
	

	public static final int		STAT_INACTIVE 		= 0;
	public static final int		STAT_ACTIVE 		= 1;
	public static final int		STAT_ACTIVE_HIDDEN	= 3;
	public static final int		STAT_LOCK 			= 5;
	public static final int		STAT_DELETED 		= 10;

	public static final int		STAT_NOT_EXIST 		= 29;
	public static final int		STAT_NOT_LOGIN 		= 30;
	public static final int		STAT_LOGINED 		= 31;

	// ---------------------------List of Column from
	// DB-----------------------------
	public static final String COL_I_ID 			= "I_ID";
	public static final String COL_T_LOGIN_01		= "T_Login_01";
	public static final String COL_T_LOGIN_02		= "T_Login_02"; //GG
	public static final String COL_T_LOGIN_03		= "T_Login_03"; //FB

	public static final String COL_T_PASS_01		= "T_Pass_01";
	public static final String COL_T_PASS_02		= "T_Pass_02";
	public static final String COL_T_PASS_03		= "T_Pass_03";

	public static final String COL_I_STATUS 		= "I_Status";

	public static final String COL_I_TYPE_01 		= "I_Type_01";
	public static final String COL_I_TYPE_02 		= "I_Type_02";

	public static final String COL_T_INFO_01 		= "T_Info_01"; //email
	public static final String COL_T_INFO_02 		= "T_Info_02"; //tel
	public static final String COL_T_INFO_03 		= "T_Info_03";
	public static final String COL_T_INFO_04 		= "T_Info_04";
	public static final String COL_T_INFO_05 		= "T_Info_05"; //times

	public static final String COL_T_INFO_06 		= "T_Info_06";
	public static final String COL_T_INFO_07 		= "T_Info_07";
	public static final String COL_T_INFO_08 		= "T_Info_08";
	public static final String COL_T_INFO_09 		= "T_Info_09";
	public static final String COL_T_INFO_10 		= "T_Info_10"; //hashcode

	public static final String COL_D_DATE_01 		= "D_Date_01"; //date new
	public static final String COL_D_DATE_02 		= "D_Date_02"; //date mod
	public static final String COL_D_DATE_03 		= "D_Date_03"; //date bg validation
	public static final String COL_D_DATE_04 		= "D_Date_04"; //date end validation

	public static final String COL_I_AUT_USER_01 	= "I_Aut_User_01"; //user new
	public static final String COL_I_AUT_USER_02 	= "I_Aut_User_02"; //user mod
	public static final String COL_I_AUT_USER_03 	= "I_Aut_User_03"; //supervisor

	public static final String COL_I_PER_MANAGER 	= "I_Per_Manager";
	public static final String COL_I_PER_PERSON 	= "I_Per_Person";

	// ---------------------------List of ATTR of class-----------------------------
	public static final String ATT_I_ID 			= "I_ID";
	public static final String ATT_T_LOGIN_01		= "T_Login_01";
	public static final String ATT_T_LOGIN_02		= "T_Login_02";
	public static final String ATT_T_LOGIN_03		= "T_Login_03"; //FB


	public static final String ATT_T_PASS_01		= "T_Pass_01";
	public static final String ATT_T_PASS_02		= "T_Pass_02";
	public static final String ATT_T_PASS_03		= "T_Pass_03";


	public static final String ATT_I_STATUS 		= "I_Status";

	public static final String ATT_I_TYPE_01 		= "I_Type_01";
	public static final String ATT_I_TYPE_02 		= "I_Type_02";

	public static final String ATT_D_DATE_01 		= "D_Date_01";
	public static final String ATT_D_DATE_02 		= "D_Date_02";
	public static final String ATT_D_DATE_03 		= "D_Date_03";
	public static final String ATT_D_DATE_04 		= "D_Date_04";

	public static final String ATT_T_INFO_01 		= "T_Info_01";
	public static final String ATT_T_INFO_02 		= "T_Info_02";
	public static final String ATT_T_INFO_03 		= "T_Info_03";
	public static final String ATT_T_INFO_04 		= "T_Info_04";
	public static final String ATT_T_INFO_05 		= "T_Info_05";

	public static final String ATT_T_INFO_06 		= "T_Info_06";
	public static final String ATT_T_INFO_07 		= "T_Info_07";
	public static final String ATT_T_INFO_08 		= "T_Info_08";
	public static final String ATT_T_INFO_09 		= "T_Info_09";
	public static final String ATT_T_INFO_10 		= "T_Info_10"; //hashcode

	public static final String ATT_I_AUT_USER_01 	= "I_Aut_User_01";
	public static final String ATT_I_AUT_USER_02 	= "I_Aut_User_02";
	public static final String ATT_I_AUT_USER_03 	= "I_Aut_User_03";
	public static final String ATT_I_PER_MANAGER 	= "I_Per_Manager";
	public static final String ATT_I_PER_PERSON 	= "I_Per_Person";

	public static final String ATT_O_AUTH 			= "O_Auth";
	public static final String ATT_O_AUTHS 			= "O_Auths";
	public static final String ATT_O_RIGHTS 		= "O_Rights";

	public static final String ATT_O_PER_MANAGER 	= "O_Per_Manager";
	public static final String ATT_O_PER_PERSON     = "O_Per_Person";

	public static final String ATT_O_DOCUMENTS 		= "O_Documents";
	public static final String ATT_O_AVATAR	        = "O_Avatar";
	//	public static final String ATT_O_MANAGER        = "O_Manager";
	//	public static final String ATT_O_SUPERIOR	    = "O_Superior";

	public static final String ATT_O_GROUPS	        = "O_Groups";
	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static	final boolean				API_CACHE 	= true;
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaAutUser> 	DAO;
	static{
		DAO = new EntityDAO<TaAutUser>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaAutUser.class,RIGHTS,  HISTORY, DefDBExt.TA_AUT_USER, DefDBExt.ID_TA_AUT_USER);
	}

	//-----------------------Class Attributes-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;

	@Column(name=COL_T_LOGIN_01, nullable = false)
	private	String          T_Login_01;

	@Column(name=COL_T_LOGIN_02, nullable = false)
	private	String          T_Login_02;

	@Column(name=COL_T_LOGIN_03, nullable = false)
	private	String          T_Login_03;

	@Column(name=COL_T_PASS_01, nullable = false)
	private	String          T_Pass_01;

	@Column(name=COL_T_PASS_02, nullable = false)
	private	String          T_Pass_02;

	@Column(name=COL_T_PASS_03, nullable = false)
	private	String          T_Pass_03;

	@Column(name=COL_I_STATUS, nullable = true)
	private	Integer         I_Status;

	@Column(name=COL_I_TYPE_01, nullable = true)
	private	Integer         I_Type_01;

	@Column(name=COL_I_TYPE_02, nullable = true)
	private	Integer         I_Type_02;

	@Column(name=COL_D_DATE_01, nullable = true)
	private	Date            D_Date_01;

	@Column(name=COL_D_DATE_02, nullable = true)
	private	Date            D_Date_02;

	@Column(name=COL_D_DATE_03, nullable = true)
	private	Date            D_Date_03;

	@Column(name=COL_D_DATE_04, nullable = true)
	private	Date            D_Date_04;

	@Column(name=COL_T_INFO_01, nullable = true)
	private	String            T_Info_01;

	@Column(name=COL_T_INFO_02, nullable = true)
	private	String            T_Info_02;

	@Column(name=COL_T_INFO_03, nullable = true)
	private	String            T_Info_03;

	@Column(name=COL_T_INFO_04, nullable = true)
	private	String            T_Info_04;

	@Column(name=COL_T_INFO_05, nullable = true)
	private	String            T_Info_05;

	@Column(name=COL_T_INFO_06, nullable = true)
	private	String            T_Info_06;

	@Column(name=COL_T_INFO_07, nullable = true)
	private	String            T_Info_07;

	@Column(name=COL_T_INFO_08, nullable = true)
	private	String            T_Info_08;

	@Column(name=COL_T_INFO_09, nullable = true)
	private	String            T_Info_09;

	@Column(name=COL_T_INFO_10, nullable = true)
	private	String            T_Info_10;

	@Column(name=COL_I_AUT_USER_01, nullable = true)
	private	Integer            I_Aut_User_01;

	@Column(name=COL_I_AUT_USER_02, nullable = true)
	private	Integer            I_Aut_User_02;

	@Column(name=COL_I_AUT_USER_03, nullable = true)
	private	Integer            I_Aut_User_03;

	@Column(name=COL_I_PER_MANAGER, nullable = true)
	private	Integer            I_Per_Manager;

	@Column(name=COL_I_PER_PERSON, nullable = true)
	private	Integer            I_Per_Person;
	//-----------------------Transient Variables-------------------------
	@Transient
	private	List<ViAutAuthUserDyn>  	O_Auths;

	@Transient
	private	HashSet<Integer> 			O_Rights;

	@Transient
	private	HashSet<Integer> 			O_Roles;

	@Transient
	private	List<TaTpyDocument> 		O_Documents;

	@Transient
	private TaTpyDocument 	            O_Avatar;

	@Transient
	private	TaPerPerson					O_Per_Manager;

	@Transient
	private	TaPerPerson					O_Per_Person;

	@Transient
	private	List<TaAutHistory>			O_History_Conn;

	@Transient
	private ViAutUserDyn 	            O_Aut_Superior;
	
	@Transient
	private Object						O_Groups;

	//-----------------------------------------------------------------------
	private TaAutUser(){}

	public TaAutUser(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		//doInitDBFlag();
	}

	@Override
	public void doMergeWith(TaAutUser arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Serializable reqRef() {
		// TODO Auto-generated method stub
		return this.I_ID;
	}


	public Integer reqPerManagerId() {
		// TODO Auto-generated method stub
		return this.I_Per_Manager;
	}

	public Integer reqId() {
		// TODO Auto-generated method stub
		return this.I_ID;
	}

	public static TaAutUser reqUser(String login){
		if (login==null || login.length()==0) return null;		
		try {
			login 		= login.toLowerCase();
			List<TaAutUser> lU = TaAutUser.DAO.reqList(Restrictions.or(Restrictions.ilike(TaAutUser.ATT_T_LOGIN_01, login), Restrictions.ilike(TaAutUser.ATT_T_LOGIN_02, login)));
			if (lU!=null && lU.size()>0){						
				return lU.get(0);									
			}	
		} catch (Exception e) {			
			e.printStackTrace();
		}

		return null;
	}


	public static TaAutUser reqUserInfo(Integer id) throws Exception{
		if (id==null || id<=0) return null;

		TaAutUser user = TaAutUser.DAO.reqEntityByRef(id);

		if (user!=null) {
			user.reqSet(TaAutUser.ATT_T_PASS_01, null);
			user.reqSet(TaAutUser.ATT_T_PASS_02, null);
			user.reqSet(TaAutUser.ATT_D_DATE_01, null);
			user.reqSet(TaAutUser.ATT_D_DATE_02, null);
			user.reqSet(TaAutUser.ATT_D_DATE_03, null);
			user.reqSet(TaAutUser.ATT_I_STATUS, null);
		}


		return user;
	}


	public void doBuilAuths(boolean forced) throws Exception {       
		if (this.O_Auths!=null && !forced) return;

		Criterion 			cri 	= Restrictions.eq(TaAutAuthUser.ATT_I_AUT_USER, this.I_ID);
		List<TaAutAuthUser> auths 	= TaAutAuthUser.DAO.reqList(cri);

		this.O_Auths 	= new ArrayList<ViAutAuthUserDyn>();
		this.O_Rights 	= new HashSet<>();
		this.O_Roles 	= new HashSet<Integer>();

		Date now		= new Date();
		for	(TaAutAuthUser a : auths) {
			ViAutAuthUserDyn v = new ViAutAuthUserDyn(a);
			this.O_Auths.add(v);

			Integer 		st = a.reqInt(TaAutAuthUser.ATT_I_STATUS);
			if (st.equals(TaAutRole.STAT_OFF)) continue;

			Date 			d1 = a.reqDate(TaAutAuthUser.ATT_D_DATE_01);
			Date 			d2 = a.reqDate(TaAutAuthUser.ATT_D_DATE_02);
			if (d1!=null && d1.compareTo(now)>0) continue;
			if (d2!=null && d2.compareTo(now)<0) continue;

			Integer			r 	= a.reqInt(TaAutAuthUser.ATT_I_AUT_ROLE);
			String 			rs 	= a.reqStr(TaAutAuthUser.ATT_T_AUT_RIGHT);
			Set<Integer> 	s 	= ToolSet.reqSetInt(rs);
			this.O_Rights.addAll(s);
			this.O_Roles.add(r);
		}
	}

	public void doBuilAuth(boolean forced, TaAutAuthUser auth, int roleId) throws Exception {       
		if(auth == null) {
			auth 	= TaAutAuthUser.DAO.reqEntityByValues(TaAutAuthUser.ATT_I_AUT_USER, this.I_ID, TaAutAuthUser.ATT_I_AUT_ROLE, roleId);
		}

		if(auth == null) return;

		String 			rs 	= auth.reqStr(TaAutAuthUser.ATT_T_AUT_RIGHT);
		Set<Integer> 	s 	= ToolSet.reqSetInt(rs);

		if (this.O_Rights==null) this.O_Rights 		= new HashSet<>();
		this.O_Rights.addAll(s);
	}

	public boolean canHaveRights (Integer... rights) {
		if (rights!=null) {
			for (Integer r : rights)
				if (!O_Rights.contains(r)) return false;
		}
		return true;
	}

	public boolean canHaveRights (Set<Integer> rights) {
		if (rights!=null) {
			for (Integer r : rights)
				if (!O_Rights.contains(r)) return false;
		}
		return true;
	}

	public boolean canHaveOneRight (Integer... rights) {
		if (rights!=null) {
			for (Integer r : rights)
				if (O_Rights.contains(r)) return true;
		}
		return false;
	}
	
	public boolean canHaveOneRight (Set<Integer> rights) {
		if (rights!=null) {
			for (Integer r : rights)
				if (O_Rights.contains(r)) return true;
		}
		return false;
	}

	public boolean canHaveRoles (Integer... roles) {
		if (roles!=null) {
			for (Integer r : roles)
				if (!O_Roles.contains(r)) return false;
		}
		return true;
	}

	public boolean canHaveRoles (Set<Integer> roles) {
		if (roles!=null) {
			for (Integer r : roles)
				if (!O_Roles.contains(r)) return false;
		}
		return true;
	}

	public boolean canHaveOneRole (Integer... roles) {
		if (roles!=null) {
			for (Integer r : roles)
				if (O_Roles.contains(r)) return true;
		}
		return false;
	}

	public boolean canHaveLogin(String login) {
		return this.T_Login_01.equals(login) || this.T_Login_02.equals(login);
	}

	public boolean canBeSuperAdmin() {
		return this.I_Type_01.equals(TYPE_01_SUP_ADM);
	}

	public boolean canBeAdmin() {
		return this.I_Type_01.equals(TYPE_01_ADM);
	}
	//	public boolean canBeClient() {
	//		return this.I_Type_01.equals(TYPE_01_CLIENT) ||  this.I_Type_01.equals(TYPE_01_VISITOR);
	//	}
	//--------------------------------------------------------------------------------------------------------------------------------------------------------

	public void doBuildDocuments(boolean forced) throws Exception {
		if (this.O_Documents != null && !forced) return;
		this.O_Documents = TaTpyDocument.reqTpyDocuments(ENT_TYP, I_ID, null, null);
	}


	public void doBuildManager(boolean forced) throws Exception{
		if (this.O_Per_Manager != null && !forced) return;
		this.O_Per_Manager = TaPerPerson.DAO.reqEntityByValue(TaPerPerson.ATT_I_ID, this.I_Per_Manager);
	}

	public void doBuildPerson(boolean forced) throws Exception{
		if (this.O_Per_Person != null && !forced) return;
		this.O_Per_Person = TaPerPerson.DAO.reqEntityByValue(TaPerPerson.ATT_I_ID, this.I_Per_Person);
	}

	public void doBuildHistoryConnection(boolean forced) throws Exception{
		if (this.O_History_Conn != null && !forced) return;

		Criterion cri = Restrictions.and(Restrictions.eq(TaAutHistory.ATT_I_AUT_USER, this.I_ID),
				Restrictions.eq(TaAutHistory.ATT_I_TYPE, TaAutHistory.TYPE_CONN));
		this.O_History_Conn = TaAutHistory.DAO.reqList(0 , 10, Order.desc(ATT_I_ID), cri);
	}

	public void doBuildSuperior(boolean forced) throws Exception{
		if (this.O_Aut_Superior != null && !forced) return;
		this.O_Aut_Superior = ViAutUserDyn.DAO.reqEntityByValue(TaAutUser.ATT_I_ID, this.I_Aut_User_03);
	}

	public void doBuildAvatar(boolean forced) throws Exception {
		if (this.O_Avatar != null && !forced) return;
		
		TaTpyDocument.reqBuildAvatar (this, DefDBExt.ID_TA_AUT_USER, ATT_O_AVATAR);
	}
	
	public void doBuildGroupWork (boolean forced, Integer wLev) throws Exception {
		if (this.O_Groups != null && !forced) return;
		
		List<TaNsoGroupMember> 	lst 		= TaNsoGroupMember.DAO.reqList(Restrictions.eq(TaNsoGroupMember.ATT_I_AUT_USER, this.I_ID), Restrictions.le(TaNsoGroupMember.ATT_I_TYPE, wLev));
		Set<Integer> 			gIds		= ToolSet.reqSetInt(lst, TaNsoGroupMember.ATT_I_NSO_GROUP);
		this.O_Groups						= TaNsoGroup.DAO.reqList_In (TaNsoGroup.ATT_I_ID, gIds, Restrictions.eq(TaNsoGroup.ATT_I_TYPE_01, TaNsoGroup.TYP_01_WORK));
	}

	public void doBuilAvatarPath()  throws Exception{
		if (this.O_Documents == null) return;

		for (TaTpyDocument doc : O_Documents){
			Integer typ01 = doc.reqInt(TaTpyDocument.ATT_I_TYPE_01);
			Integer typ02 = doc.reqInt(TaTpyDocument.ATT_I_TYPE_02);
			if (typ01!=null && typ02!=null 
					&& typ01.equals(TaTpyDocument.TYPE_01_FILE_MEDIA)  
					&& typ02.equals(TaTpyDocument.TYPE_02_FILE_IMG_AVATAR)){

				JSONObject 	ava 	= new JSONObject();
				String 		imgPrv 	= doc.reqStr(TaTpyDocument.ATT_T_INFO_05);
				String 		imgRaw 	= doc.reqStr(TaTpyDocument.ATT_T_INFO_03);
				if (imgPrv==null) imgPrv = imgRaw;
				ava.put("urlPrev"	, imgPrv);
				ava.put("url"		, imgRaw);
				this.T_Info_09 = ava.toJSONString();
				return;
			}
		}
	}

	//-----------------------------------------------------------------------------------------------------------------------------
	public static void doBuildAvatarForList(List<TaAutUser>  list) throws Exception {
		if (list==null || list.size()==0) return;
		TaTpyDocument.reqBuildAvatar(list, DefDBExt.ID_TA_PRJ_PROJECT, ATT_O_AVATAR);
	}
	//-----------------------------------------------------------------------------------------------------------------------------
	//	public static void doBuildAvatar(List<TaAutUser> usersLst) throws Exception {
	//		Set<Integer> ids = ToolSet.reqSetInt(usersLst, TaAutUser.ATT_I_ID);
	//		if(!ids.isEmpty()) {
	//			Map<Integer, TaTpyDocument> mapAva = new HashMap<Integer, TaTpyDocument>();
	//			
	//			List<TaTpyDocument> avas = TaTpyDocument.reqTpyDocuments (DefDBExt.ID_TA_AUT_USER, ids, TaTpyDocument.TYPE_01_FILE_MEDIA, TaTpyDocument.TYPE_02_FILE_IMG_AVATAR, null, null);
	//
	//			if(avas != null && avas.size() > 0) {
	//				for(TaTpyDocument a : avas) {
	//					mapAva.put((Integer) a.req(TaTpyDocument.ATT_I_ENTITY_ID), a);
	//				}
	//				for(TaAutUser u : usersLst) {
	//					if(mapAva.containsKey(u.reqId())){
	//						u.reqSet(TaAutUser.ATT_O_AVATAR, mapAva.get(u.reqId()));
	//					}
	//				}
	//			}
	//		}
	//	}

	private static String ATT_O_USER = "O_User";
	public static List<ViAutUserMember> reqBuildUserMember(List lst, String colUserNameToGet, String colUserObjToSet) throws Exception {
		if (colUserObjToSet==null) colUserObjToSet = ATT_O_USER;
		if (lst==null) return null;

		Set<Integer> ids = ToolSet.reqSetInt(lst, colUserNameToGet);
		if(ids.size()>0) {
			Map<Integer, ViAutUserMember> map = new HashMap<Integer, ViAutUserMember>();

			//			List<ViAutUserMember> users = ViAutUserMember.DAO.reqList_In (ViAutUserMember.ATT_I_ID, ids, Restrictions.eq(ViAutUserMember.ATT_I_STATUS, TaAutUser.STAT_ACTIVE));
			List<ViAutUserMember> users = ViAutUserMember.DAO.reqList_In (ViAutUserMember.ATT_I_ID, ids);

			if(users != null && users.size() > 0) {
				for(ViAutUserMember a : users) {
					map.put((Integer) a.reqId(), a);
				}

				for(Object g : lst) {
					EntityAbstract ent = ((EntityAbstract)g);
					int id = ent.reqInt(colUserNameToGet);
					ent.reqSet(colUserObjToSet, map.get(id));
				}
			}
			return users;
		}
		return null;
	}

	public static List<ViAutUserMember> reqBuildUserMember(List lst, String colUserNameToGet) throws Exception {
		if (lst==null) return null;

		Set<Integer> ids = ToolSet.reqSetInt(lst, colUserNameToGet);
		if(ids.size()>0) {
			Map<Integer, ViAutUserMember> map = new HashMap<Integer, ViAutUserMember>();

			List<ViAutUserMember> users = ViAutUserMember.DAO.reqList_In (ViAutUserMember.ATT_I_ID, ids, Restrictions.eq(ViAutUserMember.ATT_I_STATUS, TaAutUser.STAT_ACTIVE));

			if(users != null && users.size() > 0) {
				for(ViAutUserMember a : users) {
					map.put((Integer) a.reqId(), a);
				}
			}
			return users;
		}
		return null;
	}
	//-----------------------------------------------------------------------------------------------------------------------------
	public static List<TaAutUser> reqListUsrFilter(int begin, int countRow, String orderCol, String orderDir, List<String> searchKey, Integer manId, Integer supID, Set<Integer> status,  Integer typ01, Integer typ02, boolean forChat) throws Exception {
		List<TaAutUser> result = null;

		Criterion cri = reqCri(searchKey, manId, supID,  status, typ01, typ02, forChat);

		Order order = null;
		if (orderCol!=null && orderDir!=null )
			if 		(orderDir.equals("DESC")) order = Order.desc(orderCol);
			else if (orderDir.equals("ASC"))  order = Order.asc(orderCol);

		if (order==null) order = Order.asc(TaAutUser.ATT_T_LOGIN_01);

		if (order!=null)
			result = TaAutUser.DAO.reqList(begin, countRow, order, cri);
		else 
			result = TaAutUser.DAO.reqList(begin, countRow, cri);

		return result;
	}


	private static Criterion reqRestriction(List<String> searchKey) {	
		if (searchKey==null) return null;
		Criterion cri = null;
		for (String s: searchKey){			
			if (cri ==null) 
				cri = Restrictions.or(
						Restrictions.ilike(TaAutUser.ATT_T_LOGIN_01, s), 
						Restrictions.ilike(TaAutUser.ATT_T_LOGIN_02, s),
						Restrictions.ilike(TaAutUser.ATT_T_LOGIN_03, s)
						);
			else {
				cri = Restrictions.and(cri, Restrictions.or(
						Restrictions.ilike(TaAutUser.ATT_T_LOGIN_01, s), 
						Restrictions.ilike(TaAutUser.ATT_T_LOGIN_02, s),
						Restrictions.ilike(TaAutUser.ATT_T_LOGIN_03, s)
						)
						);
			}
		}

		return cri;
	}


	public static Number reqCountUsrFilter(List<String> searchKey, Integer manId,  Integer supID, Set<Integer> status,  Integer typ01, Integer typ02, boolean forChat) throws Exception {
		Criterion cri = reqCri(searchKey, manId,  supID, status, typ01, typ02, forChat);

		return TaAutUser.DAO.reqCount(cri);
	}

	private static Criterion reqCri(List<String> searchKey, Integer manId, Integer supID, Set<Integer> status, Integer typ01, Integer typ02, boolean forChat) throws Exception{
		Criterion cri = Restrictions.gt(TaAutUser.ATT_I_ID, 0); 

		if (manId!=null) {
			if(forChat) {
				cri = Restrictions.and( 
						//						Restrictions.or(
						//								Restrictions.ne(TaAutUser.ATT_I_TYPE_02		, TYPE_02_CANDIDATE),
						//								Restrictions.ne(TaAutUser.ATT_I_TYPE_02		, TYPE_02_RECRUITER)
						//						),
						Restrictions.eq(TaAutUser.ATT_I_PER_MANAGER	, manId));
			} else {
				cri = Restrictions.and(Restrictions.gt(TaAutUser.ATT_I_ID, 3), Restrictions.eq(TaAutUser.ATT_I_PER_MANAGER, manId));
			}
		}

		if(status != null && status.size()>0)
			cri = Restrictions.and(cri, Restrictions.in(TaAutUser.ATT_I_STATUS, status));

		Criterion criKey =   reqRestriction (searchKey);
		if (criKey !=null)
			cri = Restrictions.and(cri, criKey);

		if(supID != null) {
			cri = Restrictions.and(cri, Restrictions.eq(TaAutUser.ATT_I_AUT_USER_01, supID));
		}

		if (typ01!=null){
			cri = Restrictions.and(cri, Restrictions.eq(TaAutUser.ATT_I_TYPE_01, typ01));
		}
		if (typ02!=null){
			cri = Restrictions.and(cri, Restrictions.eq(TaAutUser.ATT_I_TYPE_02, typ02));
		}
		return cri;
	}

	public void doHidePwd () throws Exception{
		this.reqSet(TaAutUser.ATT_T_PASS_01, null);
		this.reqSet(TaAutUser.ATT_T_PASS_02, null);
		this.reqSet(TaAutUser.ATT_T_PASS_03, null);
	}

	public static void doRemove (TaAutUser u) throws Exception{
		List<TaAutHistory> lst  = TaAutHistory.DAO.reqList(Restrictions.eq(TaAutHistory.ATT_I_AUT_USER, u.I_ID));
		TaAutHistory.DAO.doRemove(lst);
		TaAutUser.DAO.doRemove(u);
	}


}
