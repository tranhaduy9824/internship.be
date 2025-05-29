package com.hnv.db.tpy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
import org.hibernate.criterion.Restrictions;

import com.hnv.api.main.API;
import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.data.json.JSONArray;
import com.hnv.data.json.JSONObject;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.def.DefDBExt;

/**
* ReNsoEntityTpyCategory by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_TPY_CATEGORY_ENTITY )
public class TaTpyCategoryEntity extends EntityAbstract<TaTpyCategoryEntity> {

	private static final long serialVersionUID = 1L;

	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_I_ENTITY_TYPE                     =	"I_Entity_Type";
	public static final String	COL_I_ENTITY_ID                       =	"I_Entity_ID";
	public static final String	COL_I_TPY_CATEGORY                    =	"I_Tpy_Category";



	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_I_ENTITY_TYPE                     =	"I_Entity_Type";
	public static final String	ATT_I_ENTITY_ID                       =	"I_Entity_ID";
	public static final String	ATT_I_TPY_CATEGORY                    =	"I_Tpy_Category";
	
	public static final String	ATT_I_COUNT                    	      =	"I_County";

	

	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaTpyCategoryEntity> 	DAO;
	static{
		DAO = new EntityDAO<TaTpyCategoryEntity>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN) , TaTpyCategoryEntity.class,	RIGHTS);

	}

	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
	
	@Column(name=COL_I_ENTITY_TYPE, nullable = false)
	private	Integer         I_Entity_Type;

	@Column(name=COL_I_ENTITY_ID, nullable = false)
	private	Integer         I_Entity_ID;
  
	@Column(name=COL_I_TPY_CATEGORY, nullable = false)
	private	Integer         I_Tpy_Category;


	
	//-----------------------Transient Variables-------------------------
	@Transient
	private	Integer  	I_Count;

	//---------------------Constructeurs-----------------------
	private TaTpyCategoryEntity(){}

	public TaTpyCategoryEntity(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		doInitDBFlag();
	}
	
	public TaTpyCategoryEntity(Integer I_Entity_Type, Integer I_Entity_ID, Integer I_Tpy_Category) throws Exception {
		this.reqSetAttr(
			ATT_I_ENTITY_TYPE, I_Entity_Type,
			ATT_I_ENTITY_ID  , I_Entity_ID,
			ATT_I_TPY_CATEGORY, I_Tpy_Category
		);
		doInitDBFlag();
	}
	
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this;

	}

	@Override
	public void doMergeWith(TaTpyCategoryEntity ent) {
		if (ent == this) return;
		this.I_ID                 = ent.I_ID;
		this.I_Entity_Type        = ent.I_Entity_Type;
		this.I_Entity_ID          = ent.I_Entity_ID;
		this.I_Tpy_Category       = ent.I_Tpy_Category;


		//---------------------Merge Transient Variables if exist-----------------------
		this.I_Count       		  = ent.I_Count;
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_Entity_Type == ((TaTpyCategoryEntity)o).I_Entity_Type);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_Entity_Type;

	}
	
	//---------------------------------------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------------------------------
	public static List<TaTpyCategoryEntity> reqListNew (Integer entType, Integer entID,  JSONArray lstJson) throws Exception {
		if (lstJson == null || lstJson.size() == 0)
			return null;

		List<TaTpyCategoryEntity> list = new ArrayList<TaTpyCategoryEntity>();
		for (Object o : lstJson) {
			TaTpyCategoryEntity inf = new TaTpyCategoryEntity(
					API.reqMapParamsByClass((JSONObject) o, TaTpyCategoryEntity.class));
			inf.reqSet(TaTpyCategoryEntity.ATT_I_ENTITY_TYPE, entType);
			inf.reqSet(TaTpyCategoryEntity.ATT_I_ENTITY_ID, entID);
			inf.reqSet(TaTpyCategoryEntity.ATT_I_ID, null);
			list.add(inf);
		}
		TaTpyCategoryEntity.DAO.doPersist(list);
		return list;
	}	
	
	public static List<TaTpyCategoryEntity> reqListNew (Integer entType, Integer entID,  Set<Integer> lstId) throws Exception {
		if (lstId==null || lstId.size()==0)	return null;
		
		List<TaTpyCategoryEntity> list = new ArrayList<TaTpyCategoryEntity>();
		for (Integer catId: lstId){
			TaTpyCategoryEntity inf	= new TaTpyCategoryEntity(entType, entID, catId);	
			list.add(inf);					
		}			
		TaTpyCategoryEntity.DAO.doPersist(list);
		return list;
	}
	
	public static List<TaTpyCategoryEntity> reqListMod(Integer entType, Integer entID,  JSONArray lstJson) throws Exception {
		if (lstJson==null || lstJson.size()==0)	return null;
		if (entType==null || entID==null)		return null;

		List		<TaTpyCategoryEntity> 		lstMod 			= new ArrayList<TaTpyCategoryEntity > 	();
		List		<Map<String, Object>> 		lstModVal 		= new ArrayList<Map<String, Object>>();
		List		<TaTpyCategoryEntity> 		lstNew 			= new ArrayList<TaTpyCategoryEntity > 	();
		Collection	<TaTpyCategoryEntity> 		lstDel 			= null;
		Collection	<TaTpyCategoryEntity>  		lstObj 			= TaTpyCategoryEntity.DAO.reqList(	Restrictions.eq(TaTpyCategoryEntity.ATT_I_ENTITY_TYPE, entType), 
				Restrictions.eq(TaTpyCategoryEntity.ATT_I_ENTITY_ID	 , entID));		

		HashMap		<Integer,TaTpyCategoryEntity> 	map 		= new HashMap<Integer,TaTpyCategoryEntity>();


		if (lstObj!=null){
			for(TaTpyCategoryEntity d:lstObj){
				Integer id = (Integer) d.req(TaTpyCategoryEntity.ATT_I_ID);			
				d.reqSet(TaTpyCategoryEntity.ATT_I_ENTITY_TYPE	, entType 	);
				d.reqSet(TaTpyCategoryEntity.ATT_I_ENTITY_ID	, entID		);				
				map.put(id, d);			 
			}
		}

		for(int i = 0; i < lstJson.size(); i++) {
			JSONObject 			o 		= (JSONObject) lstJson.get(i);
			if(o == null) continue;
			Map<String, Object> attr 	= API.reqMapParamsByClass(o, TaTpyCategoryEntity.class);


			Integer 			id		= (Integer) attr.get(TaTpyCategoryEntity.ATT_I_ID);

			if (id!=null && map.containsKey(id)){
				attr.remove(TaTpyCategoryEntity.ATT_I_ENTITY_TYPE);
				attr.remove(TaTpyCategoryEntity.ATT_I_ENTITY_ID);

				lstMod		.add	(map.get(id));					
				lstModVal	.add	(attr);
				map			.remove	(id);
			}else{
				TaTpyCategoryEntity poO	= new TaTpyCategoryEntity(API.reqMapParamsByClass(o, TaTpyCategoryEntity.class));		
				poO.reqSet(TaTpyCategoryEntity.ATT_I_ENTITY_TYPE	, entType);
				poO.reqSet(TaTpyCategoryEntity.ATT_I_ENTITY_ID		, entID);
				poO.reqSet(TaTpyCategoryEntity.ATT_I_ID				, null);

				lstNew		.add	(poO);
			}
		}

		if (map.size()>0){
			lstDel = map.values();				
		}

		Session sess = TaTpyCategoryEntity.DAO.reqSessionCurrent();
		try {
			TaTpyCategoryEntity.DAO.doMerge			(sess, lstMod, lstModVal);
			TaTpyCategoryEntity.DAO.doPersist		(sess, lstNew);
			TaTpyCategoryEntity.DAO.doRemove		(sess, lstDel);
			TaTpyCategoryEntity.DAO.doSessionCommit	(sess);
			lstMod.addAll(lstNew);
			return lstMod;
		}catch(Exception e){
			e.printStackTrace();
			TaTpyCategoryEntity.DAO.doSessionRollback(sess);
		}
		return null;
	}
	
	
	public static List<TaTpyCategoryEntity> reqListMod (Integer entType, Integer entID,  Set<Integer> lstId) throws Exception {
		if (lstId==null || lstId.size()==0)	return null;
		if (entType==null || entID==null)		return null;

		List		<TaTpyCategoryEntity> 		lstMod 			= new ArrayList<TaTpyCategoryEntity > 	();
		List		<Map<String, Object>> 		lstModVal 		= new ArrayList<Map<String, Object>>();
		List		<TaTpyCategoryEntity> 		lstNew 			= new ArrayList<TaTpyCategoryEntity > 	();
		Collection	<TaTpyCategoryEntity> 		lstDel 			= null;
		Collection	<TaTpyCategoryEntity>  		lstObj 			= TaTpyCategoryEntity.DAO.reqList(	Restrictions.eq(TaTpyCategoryEntity.ATT_I_ENTITY_TYPE, entType), 
				Restrictions.eq(TaTpyCategoryEntity.ATT_I_ENTITY_ID	 , entID));		

		HashMap		<Integer,TaTpyCategoryEntity> 	map 		= new HashMap<Integer,TaTpyCategoryEntity>();


		if (lstObj!=null){
			for(TaTpyCategoryEntity d:lstObj){
				Integer id = (Integer) d.req(TaTpyCategoryEntity.ATT_I_ID);			
				d.reqSet(TaTpyCategoryEntity.ATT_I_ENTITY_TYPE	, entType 	);
				d.reqSet(TaTpyCategoryEntity.ATT_I_ENTITY_ID	, entID		);				
				map.put(id, d);			 
			}
		}

		for(Integer catId : lstId) {
			if(catId == null) continue;
			JSONObject o = new JSONObject();
			o.put("catId", catId);

			Map<String, Object> attr 	= API.reqMapParamsByClass(o, TaTpyCategoryEntity.class);

			Integer 			id		= (Integer) attr.get(TaTpyCategoryEntity.ATT_I_ID);

			if (id!=null && map.containsKey(id)){
				attr.remove(TaTpyCategoryEntity.ATT_I_ENTITY_TYPE);
				attr.remove(TaTpyCategoryEntity.ATT_I_ENTITY_ID);

				lstMod		.add	(map.get(id));					
				lstModVal	.add	(attr);
				map			.remove	(id);
			}else{
				TaTpyCategoryEntity poO	= new TaTpyCategoryEntity(API.reqMapParamsByClass(o, TaTpyCategoryEntity.class));		
				poO.reqSet(TaTpyCategoryEntity.ATT_I_ENTITY_TYPE	, entType);
				poO.reqSet(TaTpyCategoryEntity.ATT_I_ENTITY_ID		, entID);
				poO.reqSet(TaTpyCategoryEntity.ATT_I_ID				, null);

				lstNew		.add	(poO);
			}
		}

		if (map.size()>0){
			lstDel = map.values();				
		}

		Session sess = TaTpyCategoryEntity.DAO.reqSessionCurrent();
		try {
			TaTpyCategoryEntity.DAO.doMerge			(sess, lstMod, lstModVal);
			TaTpyCategoryEntity.DAO.doPersist		(sess, lstNew);
			TaTpyCategoryEntity.DAO.doRemove		(sess, lstDel);
			TaTpyCategoryEntity.DAO.doSessionCommit	(sess);
			lstMod.addAll(lstNew);
			return lstMod;
		}catch(Exception e){
			e.printStackTrace();
			TaTpyCategoryEntity.DAO.doSessionRollback(sess);
		}
		return null;
	}
	
	public static void doListDel(Session sess, Integer entType, Integer entID) throws Exception {
		Collection	<TaTpyCategoryEntity>  		lstObj 			= TaTpyCategoryEntity.DAO.reqList(	sess,
																									Restrictions.eq(TaTpyCategoryEntity.ATT_I_ENTITY_TYPE, entType), 
																									Restrictions.eq(TaTpyCategoryEntity.ATT_I_ENTITY_ID	 , entID));		

		TaTpyCategoryEntity.DAO.doRemove		(sess, lstObj);
	}

}
