package com.hnv.db.nso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.hnv.api.def.DefAPI;
import com.hnv.api.def.DefJS;
import com.hnv.api.main.API;
import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.common.tool.ToolData;
import com.hnv.common.tool.ToolJSON;
import com.hnv.data.json.JSONArray;
import com.hnv.data.json.JSONObject;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.aut.TaAutUser;
import com.hnv.db.aut.vi.ViAutUserDyn;
import com.hnv.db.aut.vi.ViAutUserMember;
import com.hnv.def.DefDBExt;

/**
 * TaNsoGroupMember by H&V SAS
 */
@Entity
@Table(name = DefDBExt.TA_NSO_GROUP_MEMBER )
public class TaNsoGroupMember extends EntityAbstract<TaNsoGroupMember> {

	private static final long serialVersionUID = 1L;
	public static final int		STAT_NEW 			= 0;
	public static final int		STAT_ACTIVE 		= 1;
	public static final int		STAT_REVIEW 		= 5;
	public static final int		STAT_DELETED 		= 10;
	public static final int		STAT_DENY 			= 11;

	public static final int TYP_OWNER 			= 0;
	public static final int TYP_MANAGER 		= 1;
	public static final int TYP_REPORTER 		= 2;
	public static final int TYP_MEMBER_LEV_01 	= 10;//--doctor
	public static final int TYP_MEMBER_LEV_02 	= 20;//--pharma
	public static final int TYP_MEMBER_LEV_03 	= 30;//--nurse	
	public static final int TYP_MEMBER_OTHER 	= 100; //--patient for doctor meeting

	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_I_NSO_GROUP                       =	"I_Nso_Group";
	public static final String	COL_I_AUT_USER                        =	"I_Aut_User";
	public static final String	COL_I_STATUS                          =	"I_Status";
	public static final String	COL_I_TYPE                            =	"I_Type";
	public static final String	COL_D_DATE_01                         =	"D_Date_01";
	public static final String	COL_D_DATE_02                         =	"D_Date_02";

	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_I_NSO_GROUP                       =	"I_Nso_Group";
	public static final String	ATT_I_AUT_USER                        =	"I_Aut_User";
	public static final String	ATT_I_STATUS                          =	"I_Status";
	public static final String	ATT_I_TYPE                            =	"I_Type";
	public static final String	ATT_D_DATE_01                         =	"D_Date_01";
	public static final String	ATT_D_DATE_02                         =	"D_Date_02";
	public static final String	ATT_O_MEMBER                          =	"O_Member";

	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static	final boolean				API_CACHE 	= false;
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaNsoGroupMember> 	DAO ;
	static{
		DAO = new EntityDAO<TaNsoGroupMember>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaNsoGroupMember.class,RIGHTS);
	}

	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;

	@Column(name=COL_I_NSO_GROUP, nullable = true)
	private	Integer         I_Nso_Group;

	@Column(name=COL_I_AUT_USER, nullable = true)
	private	Integer         I_Aut_User;

	@Column(name=COL_I_STATUS, nullable = true)
	private	Integer         I_Status;

	@Column(name=COL_I_TYPE, nullable = true)
	private	Integer         I_Type;

	@Column(name=COL_D_DATE_01, nullable = true)
	private	Date            D_Date_01;

	@Column(name=COL_D_DATE_02, nullable = true)
	private	Date            D_Date_02;

	//-----------------------Transient Variables-------------------------
	@Transient
	private	ViAutUserMember    O_Member			= null;
	

	//---------------------Constructeurs-----------------------
	public TaNsoGroupMember(){}
	public TaNsoGroupMember(int grpId, int uId){
		this.I_Aut_User 	= uId;
		this.I_Nso_Group 	= grpId;
	}

	public TaNsoGroupMember(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		//doInitDBFlag();
	}
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaNsoGroupMember ent) {
		if (ent == this) return;
		this.I_Nso_Group            = ent.I_Nso_Group;
		this.I_Aut_User             = ent.I_Aut_User;
		this.I_Status               = ent.I_Status;
		this.I_Type                 = ent.I_Type;
		this.D_Date_01              = ent.D_Date_01;
		this.D_Date_02              = ent.D_Date_02;

		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;

		ok = (I_ID == ((TaNsoGroupMember)o).I_ID);
		if (!ok) return ok;


		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}

	public static List<TaNsoGroupMember> reqNsoGroupIDByUser(Integer userId) throws Exception {
		List<TaNsoGroupMember> list = new ArrayList<TaNsoGroupMember>();
		list = TaNsoGroupMember.DAO.reqList(Restrictions.eq(TaNsoGroupMember.ATT_I_AUT_USER, userId));
		return list;
	}

	//----------------------------------------------------------------------------------------

	private static void doGroupWorkSaveMember(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {	
		Integer 				groupId	= ToolData.reqInt		(json, "groupId", null);	
		JSONArray				member 	= ToolData.reqJsonArr	(json, "members", new JSONArray()) ;

		if(groupId == null) {
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		TaNsoGroup group = TaNsoGroup.DAO.reqEntityByRef(groupId);
		if( group == null || ! group.req(TaNsoGroup.ATT_I_PER_MANAGER).equals(user.reqPerManagerId())) {
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		if(member.size() == 0)	return;

		List<TaNsoGroupMember> mems = TaNsoGroupMember.DAO.reqList(Restrictions.eq(TaNsoGroupMember.ATT_I_NSO_GROUP		, groupId));

		Map<Integer, TaNsoGroupMember> mapExist = new HashMap<Integer, TaNsoGroupMember>();
		for(TaNsoGroupMember m: mems) {
			mapExist.put((Integer) m.req(TaNsoGroupMember.ATT_I_AUT_USER), m);
		}

		List<TaNsoGroupMember> lstRE 	= new ArrayList<TaNsoGroupMember>();
		List<TaNsoGroupMember> lstMOD 	= new ArrayList<TaNsoGroupMember>();
		List<TaNsoGroupMember> lstDEL 	= new ArrayList<TaNsoGroupMember>();

		for(int i = 0; i < member.size(); i++) {
			JSONObject mem = (JSONObject) member.get(i);
			Map<String, Object> attr 	= API.reqMapParamsByClass(mem, TaNsoGroupMember.class);

			Integer uId 				= (Integer) attr.get(TaNsoGroupMember.ATT_I_AUT_USER);
			TaNsoGroupMember re 		= new TaNsoGroupMember(attr);

			if(mapExist.containsKey(uId)) {
				TaNsoGroupMember memExist = mapExist.get(uId);
				memExist.reqSet(TaNsoGroupMember.ATT_I_TYPE	, attr.get(TaNsoGroupMember.ATT_I_TYPE));
				memExist.reqSet(TaNsoGroupMember.ATT_D_DATE_02, new Date());
				lstMOD.add(mapExist.get(uId));
				mapExist.remove(uId);
				continue;
			}

			lstRE.add(re);
		}

		if(lstRE.size() > 0)	TaNsoGroupMember.DAO.doPersist(lstRE);
		if(lstMOD.size() > 0) 	TaNsoGroupMember.DAO.doMerge(lstMOD);

		if(!mapExist.isEmpty()) {
			for (Map.Entry<Integer, TaNsoGroupMember> entry : mapExist.entrySet()) {
				lstDEL.add(entry.getValue());
			}
			TaNsoGroupMember.DAO.doRemove(lstDEL);
		}

		API.doResponse(response	, ToolJSON.reqJSonString(
				// filter,
				DefJS.SESS_STAT	, 1, 
				DefJS.SV_CODE	, DefAPI.SV_CODE_API_YES
				));
	}

	//---------------------------------------------------------------------------------------------------------------------------------------------
	public static List<TaNsoGroupMember> reqListByGroupId(Integer grpID) throws Exception {
		if (grpID==null )					return null;

		List	<TaNsoGroupMember>  	lstObj 			= TaNsoGroupMember.DAO.reqList(Order.asc(TaNsoGroupMember.ATT_I_ID),	Restrictions.eq(TaNsoGroupMember.ATT_I_NSO_GROUP	 , grpID));		
		return lstObj;

	}
	

	//---------------------------------------------------------------------------------------------------------------------------------------------
	public static List<TaNsoGroupMember> reqListNew (Integer grpID,  JSONArray lstJson) throws Exception {
		if (lstJson == null || lstJson.size() == 0)
			return null;

		List<TaNsoGroupMember> list = new ArrayList<TaNsoGroupMember>();
		for (Object o : lstJson) {
			TaNsoGroupMember inf = new TaNsoGroupMember(
					API.reqMapParamsByClass((JSONObject) o, TaNsoGroupMember.class));
			
			Integer stat = inf.reqInt(TaNsoGroupMember.ATT_I_STATUS);
			
			if(stat == null) inf.reqSet(TaNsoGroupMember.ATT_I_STATUS, TaNsoGroupMember.STAT_ACTIVE);
			
			inf.reqSet(TaNsoGroupMember.ATT_I_NSO_GROUP, grpID);
			inf.reqSet(TaNsoGroupMember.ATT_I_ID, null);
			list.add(inf);
		}
		TaNsoGroupMember.DAO.doPersist(list);
		return list;
	}	

	public static List<TaNsoGroupMember> reqListNew (Integer grpID,  Set<Integer> lstId) throws Exception {
		if (lstId==null || lstId.size()==0)	return null;

		List<TaNsoGroupMember> list = new ArrayList<TaNsoGroupMember>();
		for (Integer uId: lstId){
			TaNsoGroupMember inf	= new TaNsoGroupMember(grpID, uId);	
			list.add(inf);					
		}			
		TaNsoGroupMember.DAO.doPersist(list);
		return list;
	}

	public static List<TaNsoGroupMember> reqListMod(Integer grpID,   JSONArray lstJson) throws Exception {
		if (lstJson==null || lstJson.size()==0)	return null;
		if (grpID==null )						return null;

		List		<TaNsoGroupMember> 		lstMod 			= new ArrayList<TaNsoGroupMember > 	();
		List		<Map<String, Object>> 	lstModVal 		= new ArrayList<Map<String, Object>>();
		List		<TaNsoGroupMember> 		lstNew 			= new ArrayList<TaNsoGroupMember > 	();
		Collection	<TaNsoGroupMember> 		lstDel 			= null;
		Collection	<TaNsoGroupMember>  	lstObj 			= TaNsoGroupMember.DAO.reqList(	Restrictions.eq(TaNsoGroupMember.ATT_I_NSO_GROUP	 , grpID));		

		HashMap		<Integer,TaNsoGroupMember> 	map 		= new HashMap<Integer,TaNsoGroupMember>();


		if (lstObj!=null){
			for(TaNsoGroupMember d:lstObj){
				Integer id = (Integer) d.req(TaNsoGroupMember.ATT_I_ID);			
				d.reqSet(TaNsoGroupMember.ATT_I_NSO_GROUP	 , grpID);
				map.put(id, d);			 
			}
		}

		for(int i = 0; i < lstJson.size(); i++) {
			JSONObject 			o 		= (JSONObject) lstJson.get(i);
			if(o == null) continue;
			Map<String, Object> attr 	= API.reqMapParamsByClass(o, TaNsoGroupMember.class);


			Integer 			id		= (Integer) attr.get(TaNsoGroupMember.ATT_I_ID);

			if (id!=null && id>0 && map.containsKey(id)){
				attr.remove(TaNsoGroupMember.ATT_I_NSO_GROUP);

				lstMod		.add	(map.get(id));					
				lstModVal	.add	(attr);
				map			.remove	(id);
			}else{
				TaNsoGroupMember poO	= new TaNsoGroupMember(API.reqMapParamsByClass(o, TaNsoGroupMember.class));		
				poO.reqSet(TaNsoGroupMember.ATT_I_NSO_GROUP	, grpID);
				poO.reqSet(TaNsoGroupMember.ATT_I_ID		, null);
				poO.reqSet(TaNsoGroupMember.ATT_D_DATE_01	, new Date());

				lstNew		.add	(poO);
			}
		}

		if (map.size()>0){
			lstDel = map.values();				
		}

		Session sess = TaNsoGroupMember.DAO.reqSessionCurrent();
		try {
			TaNsoGroupMember.DAO.doMerge			(sess, lstMod, lstModVal);
			TaNsoGroupMember.DAO.doPersist			(sess, lstNew);
			TaNsoGroupMember.DAO.doRemove			(sess, lstDel);
			TaNsoGroupMember.DAO.doSessionCommit	(sess);
			lstMod.addAll(lstNew);
			return lstMod;
		}catch(Exception e){
			e.printStackTrace();
			TaNsoGroupMember.DAO.doSessionRollback(sess);
		}
		return null;
	}


	public static List<TaNsoGroupMember> reqListMod (Integer grpID,  Set<Integer> lstId) throws Exception {
		if (lstId==null || lstId.size()==0)	return null;
		if (grpID==null )					return null;

		List		<TaNsoGroupMember> 		lstMod 			= new ArrayList<TaNsoGroupMember > 	();
		List		<Map<String, Object>> 	lstModVal 		= new ArrayList<Map<String, Object>>();
		List		<TaNsoGroupMember> 		lstNew 			= new ArrayList<TaNsoGroupMember > 	();
		Collection	<TaNsoGroupMember> 		lstDel 			= null;
		Collection	<TaNsoGroupMember>  	lstObj 			= TaNsoGroupMember.DAO.reqList(	Restrictions.eq(TaNsoGroupMember.ATT_I_NSO_GROUP	, grpID)); 

		HashMap		<Integer,TaNsoGroupMember> 	map 		= new HashMap<Integer,TaNsoGroupMember>();


		if (lstObj!=null){
			for(TaNsoGroupMember d:lstObj){
				Integer id = (Integer) d.req(TaNsoGroupMember.ATT_I_ID);			
				d.reqSet(TaNsoGroupMember.ATT_I_NSO_GROUP	, grpID);
				map.put(id, d);			 
			}
		}

		for(Integer catId : lstId) {
			if(catId == null) continue;
			JSONObject o = new JSONObject();
			o.put("catId", catId);

			Map<String, Object> attr 	= API.reqMapParamsByClass(o, TaNsoGroupMember.class);

			Integer 			id		= (Integer) attr.get(TaNsoGroupMember.ATT_I_ID);

			if (id!=null  && id>0 && map.containsKey(id)){
				attr.remove(TaNsoGroupMember.ATT_I_NSO_GROUP);

				lstMod		.add	(map.get(id));					
				lstModVal	.add	(attr);
				map			.remove	(id);
			}else{
				TaNsoGroupMember poO	= new TaNsoGroupMember(API.reqMapParamsByClass(o, TaNsoGroupMember.class));		
				poO.reqSet(TaNsoGroupMember.ATT_I_NSO_GROUP	, grpID);
				poO.reqSet(TaNsoGroupMember.ATT_I_ID		, null);

				lstNew		.add	(poO);
			}
		}

		if (map.size()>0){
			lstDel = map.values();				
		}

		Session sess = TaNsoGroupMember.DAO.reqSessionCurrent();
		try {
			TaNsoGroupMember.DAO.doMerge			(sess, lstMod, lstModVal);
			TaNsoGroupMember.DAO.doPersist		(sess, lstNew);
			TaNsoGroupMember.DAO.doRemove		(sess, lstDel);
			TaNsoGroupMember.DAO.doSessionCommit	(sess);
			lstMod.addAll(lstNew);
			return lstMod;
		}catch(Exception e){
			e.printStackTrace();
			TaNsoGroupMember.DAO.doSessionRollback(sess);
		}
		return null;
	}

	public static void doListDel(Session sess, Integer grpID) throws Exception {
		Collection	<TaNsoGroupMember>  		lstObj 			= TaNsoGroupMember.DAO.reqList(	sess,	Restrictions.eq(TaNsoGroupMember.ATT_I_NSO_GROUP	, grpID)); 

		TaNsoGroupMember.DAO.doRemove		(sess, lstObj);
	}
}
