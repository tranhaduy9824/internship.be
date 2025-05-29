package com.hnv.db.tpy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
* TaTpyTranslation by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_TPY_TRANSLATION )
public class TaTpyTranslation extends EntityAbstract<TaTpyTranslation> {

	private static final long serialVersionUID = 1L;

	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_I_ENTITY_TYPE                     =	"I_Entity_Type";
	public static final String	COL_I_ENTITY_ID                       =	"I_Entity_ID";
	public static final String	COL_I_VAL_01                          =	"I_Val_01";
	public static final String	COL_I_VAL_02                          =	"I_Val_02";
	public static final String	COL_T_INFO_01                         =	"T_Info_01";
	public static final String	COL_T_INFO_02                         =	"T_Info_02";



	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_I_ENTITY_TYPE                     =	"I_Entity_Type";
	public static final String	ATT_I_ENTITY_ID                       =	"I_Entity_ID";
	public static final String	ATT_I_VAL_01                          =	"I_Val_01";
	public static final String	ATT_I_VAL_02                          =	"I_Val_02";
	public static final String	ATT_T_INFO_01                         =	"T_Info_01";
	public static final String	ATT_T_INFO_02                         =	"T_Info_02";



	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaTpyTranslation> 	DAO;
	static{
		DAO = new EntityDAO<TaTpyTranslation>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaTpyTranslation.class,RIGHTS, HISTORY, DefDBExt.TA_TPY_TRANSLATION, DefDBExt.ID_TA_TPY_TRANSLATION);

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
  
	@Column(name=COL_I_VAL_01, nullable = true)
	private	Integer         I_Val_01;
     
	@Column(name=COL_I_VAL_02, nullable = true)
	private	Integer         I_Val_02;
     
	@Column(name=COL_T_INFO_01, nullable = true)
	private	String          T_Info_01;
    
	@Column(name=COL_T_INFO_02, nullable = true)
	private	String          T_Info_02;
    

    
	//-----------------------Transient Variables-------------------------


	//---------------------Constructeurs-----------------------
	private TaTpyTranslation(){}

	public TaTpyTranslation(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		doInitDBFlag();
	}
	
	public TaTpyTranslation(Integer I_Entity_Type, Integer I_Entity_ID) throws Exception {
		this.reqSetAttr(
			ATT_I_ENTITY_TYPE, I_Entity_Type,
			ATT_I_ENTITY_ID  , I_Entity_ID
		);
		doInitDBFlag();
	}
	public TaTpyTranslation(Integer I_Entity_Type, Integer I_Entity_ID, Integer I_Val_01, Integer I_Val_02, String T_Info_01, String T_Info_02) throws Exception {
		this.reqSetAttr(
			ATT_I_ENTITY_TYPE          , I_Entity_Type,
			ATT_I_ENTITY_ID            , I_Entity_ID,
			ATT_I_VAL_01               , I_Val_01,
			ATT_I_VAL_02               , I_Val_02,
			ATT_T_INFO_01              , T_Info_01,
			ATT_T_INFO_02              , T_Info_02
		);
		doInitDBFlag();
	}
	
	public TaTpyTranslation(Integer I_Entity_Type, Integer I_Entity_ID, Integer I_Val_01,  String T_Info_01) throws Exception {
		this.reqSetAttr(
			ATT_I_ENTITY_TYPE          , I_Entity_Type,
			ATT_I_ENTITY_ID            , I_Entity_ID,
			ATT_I_VAL_01               , I_Val_01,
			ATT_T_INFO_01              , T_Info_01
		);
		doInitDBFlag();
	}
	
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaTpyTranslation ent) {
		if (ent == this) return;
		this.I_Entity_Type          = ent.I_Entity_Type;
		this.I_Entity_ID            = ent.I_Entity_ID;
		this.I_Val_01               = ent.I_Val_01;
		this.I_Val_02               = ent.I_Val_02;
		this.T_Info_01              = ent.T_Info_01;
		this.T_Info_02              = ent.T_Info_02;



		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((TaTpyTranslation)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}
	
	
	//---------------------------------------------------------------------------------
	public static List<TaTpyTranslation> reqTpyTranslations (Integer entType, Integer entID) throws Exception{		
		Criterion cri = Restrictions.gt("I_ID", 0);	

		if (entType!=null)
			cri = Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyTranslation.ATT_I_ENTITY_TYPE, entType));

		if (entID!=null)
			cri = Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyTranslation.ATT_I_ENTITY_ID, entID));


		List<TaTpyTranslation> lst = TaTpyTranslation.DAO.reqList(cri);
		return lst;
	}
	
	public static void doTpyTranslationDel (Integer entType, Integer entID) throws Exception {
		Collection	<TaTpyTranslation>  	lstObj 			= TaTpyTranslation.DAO.reqList(Restrictions.eq(TaTpyTranslation.ATT_I_ENTITY_TYPE, entType), Restrictions.eq(TaTpyTranslation.ATT_I_ENTITY_ID, entID));		
		for (TaTpyTranslation doc : lstObj){
			doc.reqSet(TaTpyTranslation.ATT_I_ENTITY_TYPE	, -1);
			doc.reqSet(TaTpyTranslation.ATT_I_ENTITY_ID	, -1);
		}
		TaTpyTranslation.DAO.doMerge		(lstObj);	
	}
	
	public static void doTpyTranslationDel (Integer entType, Collection entID) throws Exception {
		Collection	<TaTpyTranslation>  	lstObj 			= TaTpyTranslation.DAO.reqList(Restrictions.eq(TaTpyTranslation.ATT_I_ENTITY_TYPE, entType), Restrictions.in(TaTpyTranslation.ATT_I_ENTITY_ID, entID));		
		for (TaTpyTranslation doc : lstObj){
			doc.reqSet(TaTpyTranslation.ATT_I_ENTITY_TYPE	, -1);
			doc.reqSet(TaTpyTranslation.ATT_I_ENTITY_ID	, -1);
		}
		TaTpyTranslation.DAO.doMerge		(lstObj);	
	}
	
	public static void doTpyTranslationDel (Session sess,Integer entType, Integer entID) throws Exception {
		Collection	<TaTpyTranslation>  	lstObj 			= TaTpyTranslation.DAO.reqList(sess, Restrictions.eq(TaTpyTranslation.ATT_I_ENTITY_TYPE, entType), Restrictions.eq(TaTpyTranslation.ATT_I_ENTITY_ID, entID));		
		for (TaTpyTranslation doc : lstObj){
			doc.reqSet(TaTpyTranslation.ATT_I_ENTITY_TYPE	, -1);
			doc.reqSet(TaTpyTranslation.ATT_I_ENTITY_ID	, -1);
		}
		TaTpyTranslation.DAO.doMerge		(sess, lstObj);	
	}
	public static void doTpyTranslationDel (Session sess, Integer entType, Collection entID) throws Exception {
		Collection	<TaTpyTranslation>  	lstObj 			= TaTpyTranslation.DAO.reqList(sess, Restrictions.eq(TaTpyTranslation.ATT_I_ENTITY_TYPE, entType), Restrictions.in(TaTpyTranslation.ATT_I_ENTITY_ID, entID));		
		for (TaTpyTranslation doc : lstObj){
			doc.reqSet(TaTpyTranslation.ATT_I_ENTITY_TYPE	, -1);
			doc.reqSet(TaTpyTranslation.ATT_I_ENTITY_ID	, -1);
		}
		TaTpyTranslation.DAO.doMerge		(sess, lstObj);	
	}
	
	//---------------------------------------------------------------------------------
	public static  List<TaTpyTranslation>  reqTpyTranslationMod(Integer entType, Integer entID,  JSONArray lstJson) throws Exception {
		if (lstJson==null) 						lstJson 		= new JSONArray();

		List		<TaTpyTranslation> 			lstMod 			= new ArrayList<TaTpyTranslation > 	();
		List		<Map<String, Object>> 		lstModVal 		= new ArrayList<Map<String, Object>>();
		List		<TaTpyTranslation> 			lstNew 			= new ArrayList<TaTpyTranslation > 	();
		List		<Map<String, Object>> 		lstNewVal 		= new ArrayList<Map<String, Object>>();
		Collection	<TaTpyTranslation> 			lstDel 			= null;
		Collection	<TaTpyTranslation>  		lstObj 			= TaTpyTranslation.DAO.reqList(Restrictions.eq(TaTpyTranslation.ATT_I_ENTITY_TYPE,entType ), Restrictions.eq(TaTpyTranslation.ATT_I_ENTITY_ID, entID));		

		HashMap		<Integer,TaTpyTranslation> 	map 			= new HashMap<Integer,TaTpyTranslation>();
		
						
		if (lstObj!=null){
			for(TaTpyTranslation d:lstObj){
				Integer id = (Integer) d.req(TaTpyTranslation.ATT_I_ID);			
				map.put(id, d);			 
			}
		}
		
		for(int i = 0; i < lstJson.size(); i++) {
			JSONObject 			o 		= (JSONObject) lstJson.get(i);
			Map<String, Object> attr 	= API.reqMapParamsByClass(o, TaTpyTranslation.class);
			attr.remove(TaTpyTranslation.ATT_I_ENTITY_TYPE	);
			attr.remove(TaTpyTranslation.ATT_I_ENTITY_ID	);
			attr.remove(TaTpyTranslation.ATT_I_VAL_01		);
			
			Integer 			idDoc		= (Integer) attr.get(TaTpyTranslation.ATT_I_ID);
			if (idDoc!=null &&  map.containsKey(idDoc)){
				lstMod		.add(map.get(idDoc));				
				lstModVal	.add(attr);
				map			.remove(idDoc);				
			}else{
				TaTpyTranslation poO	= TaTpyTranslation.DAO.reqEntityByRef(idDoc);		
				attr.put(TaTpyTranslation.ATT_I_ENTITY_TYPE	, entType);
				attr.put(TaTpyTranslation.ATT_I_ENTITY_ID	, entID);
				
				lstNewVal	.add(attr);
				lstNew		.add(poO);
			}
		}

		if (map.size()>0){
			lstDel = map.values();				
			for (TaTpyTranslation doc: lstDel){
				doc.reqSet(TaTpyTranslation.ATT_I_ENTITY_TYPE	, -1);
				doc.reqSet(TaTpyTranslation.ATT_I_ENTITY_ID		, -1);
			}	
		}

		Session sess = TaTpyTranslation.DAO.reqSessionCurrent();
		try {
			TaTpyTranslation.DAO.doMerge		(sess, lstMod, lstModVal);
			TaTpyTranslation.DAO.doMerge		(sess, lstNew, lstNewVal);
			TaTpyTranslation.DAO.doMerge		(sess, lstDel);
			TaTpyTranslation.DAO.doSessionCommit(sess);
		}catch(Exception e){
			TaTpyTranslation.DAO.doSessionRollback(sess);
		}
		lstMod.addAll(lstNew);
		return lstMod;
	}
	//---------------------------------------------------------------------------------
	public static List<TaTpyTranslation> reqTpyTranslationNew (Integer entType, Integer entID,  JSONArray lstJson) throws Exception {
		if (lstJson!=null && lstJson.size()>0){ 
			List<TaTpyTranslation> list = new ArrayList<TaTpyTranslation>();
			for (Object o: lstJson){
				TaTpyTranslation inf	= new TaTpyTranslation(API.reqMapParamsByClass((JSONObject)o, TaTpyTranslation.class));	
				inf.reqSet(TaTpyTranslation.ATT_I_ENTITY_TYPE	, entType 	);
				inf.reqSet(TaTpyTranslation.ATT_I_ENTITY_ID		, entID		);
				inf.reqSet(TaTpyTranslation.ATT_I_ID			, null		);
				list.add(inf);					
			}			
			TaTpyTranslation.DAO.doPersist(list);
			return list;
		}
		return null;
	}
	
	public static TaTpyTranslation reqTpyTranslationNew (Integer entType, Integer entId, Integer lang,  String content ) throws Exception{		
		TaTpyTranslation doc = new TaTpyTranslation(entType, entId, lang, content);
		return doc;
	}
	
	public static TaTpyTranslation reqTpyTranslationMod (int id, String content) throws Exception{		
		TaTpyTranslation doc = TaTpyTranslation.DAO.reqEntityByRef(id);
		if (doc!=null) {
			doc.reqSet(TaTpyTranslation.ATT_T_INFO_01		, content);
			TaTpyTranslation.DAO.doMerge(doc);
		}
		return doc;
	}
	public static TaTpyTranslation reqTpyTranslationMod (int id, Integer entType, Integer entId, String content ) throws Exception{		
		TaTpyTranslation doc = TaTpyTranslation.DAO.reqEntityByRef(id);
		if (doc!=null) {
			doc.reqSet(TaTpyTranslation.ATT_I_ENTITY_TYPE	, entType);
			doc.reqSet(TaTpyTranslation.ATT_I_ENTITY_ID		, entId);
			doc.reqSet(TaTpyTranslation.ATT_T_INFO_01		, content);
			TaTpyTranslation.DAO.doMerge(doc);
		}
		return doc;
	}
	
	public static List<TaTpyTranslation> reqTpyTranslationDel (Integer... ids ) throws Exception{		
		List<TaTpyTranslation> docs = TaTpyTranslation.DAO.reqList(Restrictions.in(TaTpyTranslation.ATT_I_ID, ids));
		if (docs!=null && docs.size()>0) TaTpyTranslation.DAO.doRemove(docs);
		return docs;
	}
	
	public static List<TaTpyTranslation> reqTpyTranslationDel (Integer entType, Integer entId, Integer lang, Integer otherOpt) throws Exception{		
		Criterion cri = Restrictions.eq(TaTpyTranslation.ATT_I_ENTITY_TYPE, entType);	

		if (entId!=null)
			cri = Restrictions.and(cri, Restrictions.eq(TaTpyTranslation.ATT_I_ENTITY_ID, entId));

		if (otherOpt!=null)
			cri = Restrictions.and(cri, Restrictions.eq(TaTpyTranslation.ATT_I_VAL_02, otherOpt));
		
		if (lang!=null)
			cri = Restrictions.and(cri, Restrictions.eq(TaTpyTranslation.ATT_I_VAL_01, lang));

		
		List<TaTpyTranslation> docs = TaTpyTranslation.DAO.reqList(cri);
		if (docs!=null && docs.size()>0) TaTpyTranslation.DAO.doRemove(docs);
		return docs;
	}
	
	//--------------------------------------------------------------------------------------------
	public static List<TaTpyTranslation> reqTpyTranslations (Integer entType, Integer entId, Integer lang, Integer otherOpt) throws Exception{		
		Criterion cri = Restrictions.eq(TaTpyTranslation.ATT_I_ENTITY_TYPE, entType);	

		if (entId!=null)
			cri = Restrictions.and(cri, Restrictions.eq(TaTpyTranslation.ATT_I_ENTITY_ID, entId));

		if (otherOpt!=null)
			cri = Restrictions.and(cri, Restrictions.eq(TaTpyTranslation.ATT_I_VAL_02, otherOpt));
		
		if (lang!=null)
			cri = Restrictions.and(cri, Restrictions.eq(TaTpyTranslation.ATT_I_VAL_01, lang));

		return TaTpyTranslation.DAO.reqList(cri);
	}
	
	public static List<TaTpyTranslation> reqTpyTranslations (Integer entType, Set<Integer> entIds) throws Exception{		
		return TaTpyTranslation.DAO.reqList_In(TaTpyTranslation.ATT_I_ENTITY_ID, entIds, Restrictions.eq(TaTpyTranslation.ATT_I_ENTITY_TYPE, entType));
	}
	
	
	public static void doBuildTpyTranslations(List listEnts, Integer entType, String attrTranslName, boolean forced) throws Exception{
		Hashtable<Integer, List				> tabTransl 	= new Hashtable<Integer, List>();
		Hashtable<Integer, EntityAbstract	> tabEnt 	= new Hashtable<Integer, EntityAbstract>();
		for (Object o: listEnts) {
			EntityAbstract 	e 		= (EntityAbstract)o;
			List 			transl 	= (List)e.req(attrTranslName);
			if (!forced && transl!=null) continue;
			
			if (transl == null||forced) {
				transl = new ArrayList();
				e.reqSet(attrTranslName, transl);
			}
			
			tabTransl	.put((Integer)e.reqRef(), transl);
			tabEnt		.put((Integer)e.reqRef(), e);
		}
		
		List<TaTpyTranslation> transls = reqTpyTranslations(entType, tabTransl.keySet());
		
		if (transls!=null && transls.size()>0) {
			for (TaTpyTranslation d : transls) {
				Integer eId 	= (Integer) d.req(TaTpyTranslation.ATT_I_ENTITY_ID);
				List 	eDocs 	= tabTransl.get(eId);
				eDocs.add(d);
				tabEnt.remove(eId); //remove ent has doc
			}
		}
		
		if (tabEnt.size()>0) {
			for (Entry<Integer, EntityAbstract> e: tabEnt.entrySet()) {
				EntityAbstract ent = e.getValue();
				ent.reqSet(attrTranslName, null); //reset doc list to null
			}
		}
	}
}
