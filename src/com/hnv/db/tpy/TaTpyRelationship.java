package com.hnv.db.tpy;

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

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.hnv.api.main.API;
import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.data.json.JSONArray;
import com.hnv.data.json.JSONObject;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.def.DefDBExt;		

/**
* TaTpyRelationship by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_TPY_RELATIONSHIP )
public class TaTpyRelationship extends EntityAbstract<TaTpyRelationship> {

	private static final long serialVersionUID = 1L;
	
	public static final	int		STAT_DESACTIVE			= 0;
	public static final	int		STAT_ACTIVE				= 1;
	
	public static final	int		LEV_ADMIN				= 0;
	public static final	int		LEV_BA					= 10;
	public static final	int		LEV_DEV					= 20;
	public static final	int		LEV_TEST				= 30;
	public static final	int		LEV_MEMBER				= 40;
	public static final	int		LEV_GUEST				= 50;

	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_I_ENTITY_TYPE_01                  =	"I_Entity_Type_01";
	public static final String	COL_I_ENTITY_TYPE_02                  =	"I_Entity_Type_02";
	public static final String	COL_I_ENTITY_ID_01                    =	"I_Entity_ID_01";
	public static final String	COL_I_ENTITY_ID_02                    =	"I_Entity_ID_02";
	
	public static final String	COL_D_DATE_01                        =	"D_Date_01";//"D_Date_New";
	public static final String	COL_D_DATE_02                        =	"D_Date_02";//"D_Date_Mod";
	public static final String	COL_D_DATE_03                      	 =	"D_Date_03";//"D_Date_Begin";
	public static final String	COL_D_DATE_04                        =	"D_Date_04";//"D_Date_End";
	public static final String	COL_I_TYPE                            =	"I_Type";
	public static final String	COL_I_STATUS                          =	"I_Status";
	public static final String	COL_I_LEVEL                           =	"I_Level";
	public static final String	COL_T_COMMENT                         =	"T_Comment";

	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_I_ENTITY_TYPE_01                  =	"I_Entity_Type_01";
	public static final String	ATT_I_ENTITY_TYPE_02                  =	"I_Entity_Type_02";
	public static final String	ATT_I_ENTITY_ID_01                    =	"I_Entity_ID_01";
	public static final String	ATT_I_ENTITY_ID_02                    =	"I_Entity_ID_02";
	
	public static final String	ATT_D_DATE_01                        =	"D_Date_01";
	public static final String	ATT_D_DATE_02                        =	"D_Date_02";
	public static final String	ATT_D_DATE_03                     	 =	"D_Date_03";
	public static final String	ATT_D_DATE_04                        =	"D_Date_04";
	public static final String	ATT_I_TYPE                            =	"I_Type";
	public static final String	ATT_I_STATUS                          =	"I_Status";
	public static final String	ATT_I_LEVEL                           =	"I_Level";
	public static final String	ATT_T_COMMENT                         =	"T_Comment";

	public static final String	ATT_O_ENTITY_01                       =	"O_Entity_01";
	public static final String	ATT_O_ENTITY_02                       =	"O_Entity_02";

	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 

	public		static 	final EntityDAO<TaTpyRelationship> 	DAO;
	static{
		DAO = new EntityDAO<TaTpyRelationship>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaTpyRelationship.class,RIGHTS);
	}

	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_I_ENTITY_TYPE_01, nullable = false)
	private	Integer         I_Entity_Type_01;

	@Column(name=COL_I_ENTITY_TYPE_02, nullable = false)
	private	Integer         I_Entity_Type_02;

	@Column(name=COL_I_ENTITY_ID_01, nullable = false)
	private	Integer         I_Entity_ID_01;

	@Column(name=COL_I_ENTITY_ID_02, nullable = false)
	private	Integer         I_Entity_ID_02;

	@Column(name=COL_D_DATE_01, nullable = true)
	private	Date            D_Date_01;
   
	@Column(name=COL_D_DATE_02, nullable = true)
	private	Date            D_Date_02;
   
	@Column(name=COL_D_DATE_03, nullable = true)
	private	Date            D_Date_03;
 
	@Column(name=COL_D_DATE_04, nullable = true)
	private	Date            D_Date_04;
   
	@Column(name=COL_I_TYPE, nullable = true)
	private	Integer         I_Type;
       
	@Column(name=COL_I_STATUS, nullable = true)
	private	Integer         I_Status;
     
	@Column(name=COL_I_LEVEL, nullable = true)
	private	Integer         I_Level;
      
	@Column(name=COL_T_COMMENT, nullable = true)
	private	String          T_Comment;
    
	@Transient 
	private Object O_Entity_01;
	
	@Transient 
	private Object O_Entity_02;
    
	//-----------------------Transient Variables-------------------------


	//---------------------Constructeurs-----------------------
	private TaTpyRelationship(){}

	public TaTpyRelationship(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		doInitDBFlag();
	}
	
	public TaTpyRelationship(Integer I_Entity_Type_01, Integer I_Entity_Type_02, Integer I_Entity_ID_01, Integer I_Entity_ID_02) throws Exception {
		this.reqSetAttr(
			ATT_I_ENTITY_TYPE_01, I_Entity_Type_01,
			ATT_I_ENTITY_TYPE_02, I_Entity_Type_02,
			ATT_I_ENTITY_ID_01, I_Entity_ID_01,
			ATT_I_ENTITY_ID_02, I_Entity_ID_02
		);
		doInitDBFlag();
	}
	public TaTpyRelationship(Integer I_Entity_Type_01, Integer I_Entity_Type_02, Integer I_Entity_ID_01, Integer I_Entity_ID_02, Date D_Date_New, Date D_Date_Mod, Date D_Date_Begin, Date D_Date_End, Integer I_Type, Integer I_Status, Integer I_Level, String T_Comment) throws Exception {
		this.reqSetAttr(
			ATT_I_ENTITY_TYPE_01       , I_Entity_Type_01,
			ATT_I_ENTITY_TYPE_02       , I_Entity_Type_02,
			ATT_I_ENTITY_ID_01         , I_Entity_ID_01,
			ATT_I_ENTITY_ID_02         , I_Entity_ID_02,
			ATT_D_DATE_01             , D_Date_New,
			ATT_D_DATE_02             , D_Date_Mod,
			ATT_D_DATE_03           , D_Date_Begin,
			ATT_D_DATE_04             , D_Date_End,
			ATT_I_TYPE                 , I_Type,
			ATT_I_STATUS               , I_Status,
			ATT_I_LEVEL                , I_Level,
			ATT_T_COMMENT              , T_Comment
		);
		doInitDBFlag();
	}
	
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaTpyRelationship ent) {
		if (ent == this) return;
		this.I_Entity_Type_01       = ent.I_Entity_Type_01;
		this.I_Entity_Type_02       = ent.I_Entity_Type_02;
		this.I_Entity_ID_01         = ent.I_Entity_ID_01;
		this.I_Entity_ID_02         = ent.I_Entity_ID_02;
		this.D_Date_01             	= ent.D_Date_01;
		this.D_Date_02             	= ent.D_Date_02;
		this.D_Date_03           	= ent.D_Date_03;
		this.D_Date_04             	= ent.D_Date_04;
		this.I_Type                 = ent.I_Type;
		this.I_Status               = ent.I_Status;
		this.I_Level                = ent.I_Level;
		this.T_Comment              = ent.T_Comment;



		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((TaTpyRelationship)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}

	//---------------------------------------------------------------------------------------
	public static List<TaTpyRelationship> reqList (int entTyp01,  Set<Integer> entId01, int entTyp02, Integer entId02, Criterion...cris) throws Exception{
		Criterion cri = Restrictions.and(
				Restrictions.eq(TaTpyRelationship.ATT_I_ENTITY_TYPE_01	, entTyp01), 
				Restrictions.eq(TaTpyRelationship.ATT_I_ENTITY_TYPE_02	, entTyp02),
				Restrictions.eq(TaTpyRelationship.ATT_I_ENTITY_ID_02	, entId02));
		
		if (cris!=null && cris.length>0) {
			for (Criterion c: cris) {
				cri = Restrictions.and (cri, c);
			}
		}
		List<TaTpyRelationship> list 		= TaTpyRelationship.DAO.reqList_In(TaTpyRelationship.ATT_I_ENTITY_ID_01, entId01, cri);
		return list;
	}
	
	public static List<TaTpyRelationship> reqListMod(int entTyp01, Set<Integer> entId01, int entTyp02, Integer entId02,  JSONArray lstJson) throws Exception {
		if (lstJson==null)	return null;
		if (entId01==null && entId02==null)		return null;

		List		<TaTpyRelationship> 		lstMod 			= new ArrayList<TaTpyRelationship > 	();
		List		<Map<String, Object>> 		lstModVal 		= new ArrayList<Map<String, Object>>();
		List		<TaTpyRelationship> 		lstNew 			= new ArrayList<TaTpyRelationship > 	();
		Collection	<TaTpyRelationship> 		lstDel 			= null;
		Collection	<TaTpyRelationship>  		lstObj 			= TaTpyRelationship.reqList(entTyp01, entId01, entTyp02, entId02);	

		HashMap		<Integer,TaTpyRelationship> 	map 		= new HashMap<Integer,TaTpyRelationship>();


		if (lstObj!=null){
			for(TaTpyRelationship d:lstObj){
				Integer id = (Integer) d.req(TaTpyRelationship.ATT_I_ENTITY_ID_01);			
				map.put(id, d);			 
			}
		}

		for(int i = 0; i < lstJson.size(); i++) {
			JSONObject 			o 		= (JSONObject) lstJson.get(i);
			if(o == null) continue;
			Map<String, Object> attr 	= API.reqMapParamsByClass(o, TaTpyRelationship.class);
			attr.put(TaTpyRelationship.ATT_I_ENTITY_TYPE_01, entTyp01);
			attr.put(TaTpyRelationship.ATT_I_ENTITY_TYPE_02, entTyp02);
			
			if (entId02!=null) attr.put(TaTpyRelationship.ATT_I_ENTITY_ID_02, entId02);
			
			Integer 			idPrj		= (Integer) attr.get(TaTpyRelationship.ATT_I_ENTITY_ID_01);

			if (idPrj!=null && map.containsKey(idPrj)){
				Integer lev  =  (Integer) attr.get(TaTpyRelationship.ATT_I_LEVEL);
				if (lev<0) continue;
				
				lstMod		.add	(map.get(idPrj));					
				lstModVal	.add	(attr);
				map			.remove	(idPrj);
			}else{
				//---check entityId not null
				Integer id01 =  (Integer) attr.get(TaTpyRelationship.ATT_I_ENTITY_ID_01);
				Integer id02 =  (Integer) attr.get(TaTpyRelationship.ATT_I_ENTITY_ID_02);
				Integer lev  =  (Integer) attr.get(TaTpyRelationship.ATT_I_LEVEL);
				
				if (id01==null || id02==null || lev==null || lev<0) continue;
				
				TaTpyRelationship poO	= new TaTpyRelationship(attr);		
				poO.reqSet(TaTpyRelationship.ATT_I_ID, null);

				lstNew		.add	(poO);
			}
		}

		if (map.size()>0){
			lstDel = map.values();				
		}

		Session sess = TaTpyRelationship.DAO.reqSessionCurrent();
		try {
			TaTpyRelationship.DAO.doMerge			(sess, lstMod, lstModVal);
			TaTpyRelationship.DAO.doPersist			(sess, lstNew);
			TaTpyRelationship.DAO.doRemove			(sess, lstDel);
			TaTpyRelationship.DAO.doSessionCommit	(sess);
			lstMod.addAll(lstNew);
			return lstMod;
		}catch(Exception e){
			e.printStackTrace();
			TaTpyRelationship.DAO.doSessionRollback(sess);
		}
		return null;
	}
	
	public static List<TaTpyRelationship> reqList (int entTyp01, Integer entId01, int entTyp02, Integer entId02, Criterion...cris) throws Exception{
		Criterion cri = Restrictions.and(
				Restrictions.eq(TaTpyRelationship.ATT_I_ENTITY_TYPE_01	, entTyp01), 
				Restrictions.eq(TaTpyRelationship.ATT_I_ENTITY_TYPE_02	, entTyp02));
		
		if (entId01!=null) cri = Restrictions.and (cri, Restrictions.eq(TaTpyRelationship.ATT_I_ENTITY_ID_01	, entId01));
		if (entId02!=null) cri = Restrictions.and (cri, Restrictions.eq(TaTpyRelationship.ATT_I_ENTITY_ID_02	, entId02));
		
		if (cris!=null) {
			for (Criterion c: cris) {
				cri = Restrictions.and (cri, c);
			}
		}
		List<TaTpyRelationship> list 		= TaTpyRelationship.DAO.reqList(cri);
		return list;
	}
	
	
	public static List<TaTpyRelationship> reqListMod(int entTyp01, Integer entId01, int entTyp02, Integer entId02,  JSONArray lstJson, List<TaTpyRelationship> lstNewReturn, List<TaTpyRelationship> lstModReturn, List<TaTpyRelationship> lstDelReturn) throws Exception {
		if (lstJson==null)	return null;
		if (entId01==null && entId02==null)		return null;

		List		<TaTpyRelationship> 		lstMod 			= new ArrayList<TaTpyRelationship > 	();
		List		<Map<String, Object>> 		lstModVal 		= new ArrayList<Map<String, Object>>();
		List		<TaTpyRelationship> 		lstNew 			= new ArrayList<TaTpyRelationship > 	();
		Collection	<TaTpyRelationship> 		lstDel 			= null;
		Collection	<TaTpyRelationship>  		lstObj 			= TaTpyRelationship.reqList(entTyp01, entId01, entTyp02, entId02);	

		HashMap		<Integer,TaTpyRelationship> 	map 		= new HashMap<Integer,TaTpyRelationship>();


		if (lstObj!=null){
			for(TaTpyRelationship d:lstObj){
				Integer id = (Integer) d.req(TaTpyRelationship.ATT_I_ID);			
				map.put(id, d);			 
			}
		}

		for(int i = 0; i < lstJson.size(); i++) {
			JSONObject 			o 		= (JSONObject) lstJson.get(i);
			if(o == null) continue;
			Map<String, Object> attr 	= API.reqMapParamsByClass(o, TaTpyRelationship.class);
			attr.put(TaTpyRelationship.ATT_I_ENTITY_TYPE_01, entTyp01);
			attr.put(TaTpyRelationship.ATT_I_ENTITY_TYPE_02, entTyp02);
			
			if (entId01!=null) attr.put(TaTpyRelationship.ATT_I_ENTITY_ID_01, entId01);
			if (entId02!=null) attr.put(TaTpyRelationship.ATT_I_ENTITY_ID_02, entId02);
			
			Integer 			id		= (Integer) attr.get(TaTpyRelationship.ATT_I_ID);

			if (id!=null && map.containsKey(id)){
				lstMod		.add	(map.get(id));					
				lstModVal	.add	(attr);
				map			.remove	(id);
			}else{
				//---check entityId not null
				Integer id01 =  (Integer) attr.get(TaTpyRelationship.ATT_I_ENTITY_ID_01);
				Integer id02 =  (Integer) attr.get(TaTpyRelationship.ATT_I_ENTITY_ID_02);
				
				if (id01==null || id02==null) continue;
				
				TaTpyRelationship poO	= new TaTpyRelationship(attr);		
				poO.reqSet(TaTpyRelationship.ATT_I_ID, null);

				lstNew		.add	(poO);
			}
		}

		if (map.size()>0){
			lstDel = map.values();				
		}

		Session sess = TaTpyRelationship.DAO.reqSessionCurrent();
		try {
			List<TaTpyRelationship> lstMM = TaTpyRelationship.DAO.reqMerge (sess, lstMod, lstModVal);
			TaTpyRelationship.DAO.doPersist			(sess, lstNew);
			TaTpyRelationship.DAO.doRemove			(sess, lstDel);
			TaTpyRelationship.DAO.doSessionCommit	(sess);
			
			lstMod.addAll(lstNew);
			
			if (lstNewReturn!=null) {
				lstNewReturn.clear();
				lstNewReturn.addAll(lstNew);
			}
			if (lstDelReturn!=null) {
				lstDelReturn.clear();
				if (lstDel!= null) {
					lstDelReturn.addAll(lstDel);
				}
			}
			if (lstModReturn!=null) {
				lstModReturn.clear();
				if (lstMM!= null) {
					lstModReturn.addAll(lstMM);
				}
			}
			return lstMod;
		}catch(Exception e){
			e.printStackTrace();
			TaTpyRelationship.DAO.doSessionRollback(sess);
		}
		return null;
	}
	
	public static void doListDel(Session sess, Integer entType, Integer entID) throws Exception {
		Collection	<TaTpyRelationship>  		relas			= TaTpyRelationship.DAO.reqList(sess,	Restrictions.eq(TaTpyRelationship.ATT_I_ENTITY_TYPE_01, entType), 
																										Restrictions.eq(TaTpyRelationship.ATT_I_ENTITY_ID_01  , entID));		
		TaTpyRelationship.DAO.doRemove(sess, relas);
	}
}
