package com.hnv.db.tpy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.hnv.api.def.DefAPI;
import com.hnv.api.main.API;
import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.common.tool.ToolDBEntity;
import com.hnv.common.tool.ToolSet;
import com.hnv.data.json.JSONArray;
import com.hnv.data.json.JSONObject;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.aut.TaAutAuthUser;
import com.hnv.db.aut.TaAutRole;
import com.hnv.db.nso.TaNsoOffer;
import com.hnv.db.tpy.vi.ViTpyCategoryCount;
import com.hnv.def.DefDBExt;

/**
* TaTpyCategory by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_TPY_CATEGORY )
public class TaTpyCategory extends EntityAbstract<TaTpyCategory> {

	private static final long serialVersionUID = 1L;
	
	private static final int  ENT_TYP			= DefDBExt.ID_TA_TPY_CATEGORY;
	//---------------------------------------------------------------------------------
	public static final	int  STAT_NEW 			= 0;
	public static final	int  STAT_ACTIV			= 1;
	public static final	int  STAT_DESACTIV		= 10;
	
	public static final	int  TYPE_01_REF 		= 100;
	public static final	int  TYPE_01_OTHERS		= 200;
	
	public static final	int  TYPE_01_DISEASE		= 1000;
	public static final	int  TYPE_01_TEST_BLOOD		= 2000;
	public static final	int  TYPE_01_TEST_IMG		= 3000;
	
	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_T_NAME                            =	"T_Name";
	public static final String	COL_T_CODE                            =	"T_Code";
	public static final String	COL_T_INFO                            =	"T_Info";
	public static final String	COL_I_TYPE_01                         =	"I_Type_01";// ID_Table using this cat
	public static final String	COL_I_TYPE_02                         =	"I_Type_02";
	public static final String	COL_I_TYPE_03                         =	"I_Type_03";
	public static final String	COL_I_STATUS                     	  =	"I_Status";
	
	public static final String	COL_I_PARENT                      	  =	"I_Parent";
	public static final String	COL_I_PER_MANAGER                     =	"I_Per_Manager";
	
	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_T_NAME                            =	"T_Name";
	public static final String	ATT_T_CODE                            =	"T_Code";
	public static final String	ATT_T_INFO                            =	"T_Info";
	public static final String	ATT_I_TYPE_01                         =	"I_Type_01";
	public static final String	ATT_I_TYPE_02                         =	"I_Type_02";
	public static final String	ATT_I_TYPE_03                         =	"I_Type_03";
	public static final String	ATT_I_STATUS                     	  =	"I_Status";
	
	public static final String	ATT_I_PARENT                      	  =	"I_Parent";
	public static final String	ATT_I_PER_MANAGER                     =	"I_Per_Manager";
	
	public static final	String 	ATT_O_COUNT						  	  = "O_Count";
	public static final String  ATT_O_CHILDREN                     	  =	"O_Children";
	public static final String  ATT_O_PARENT_NAME                  	  =	"O_Parent_Name";
	public static final String  ATT_O_DOCUMENTS                       =	"O_Documents";
	public static final String  ATT_O_INFOS	                     	  =	"O_Infos";
	public static final String	ATT_O_AVATAR                       	  =	"O_Avatar";
	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaTpyCategory> 	DAO;
	static{
		DAO = new EntityDAO<TaTpyCategory>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN) , TaTpyCategory.class, RIGHTS, HISTORY, DefDBExt.TA_TPY_CATEGORY, DefDBExt.ID_TA_TPY_CATEGORY);
	}

	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_T_NAME, nullable = false)
	private	String          T_Name;
       
	@Column(name=COL_T_CODE, nullable = true)
	private	String          T_Code;
       
	@Column(name=COL_T_INFO, nullable = true)
	private	String          T_Info;
       
	@Column(name=COL_I_TYPE_01, nullable = false)
	private	Integer         I_Type_01;
    
	@Column(name=COL_I_TYPE_02, nullable = true)
	private	Integer         I_Type_02;
    
	@Column(name=COL_I_TYPE_03, nullable = true)
	private	Integer         I_Type_03;
	
	@Column(name=COL_I_STATUS, nullable = true)
	private	Integer         I_Status;
    
	@Column(name=COL_I_PARENT, nullable = true)
	private	Integer         I_Parent;
 
	@Column(name=COL_I_PER_MANAGER, nullable = false)
	private	Integer         I_Per_Manager;
    
	
	//-----------------------Transient Variables-------------------------
	@Transient
	private	List 	O_Children;
	
	@Transient
	private	String 	O_Parent_Name;
	
	@Transient
	private	Integer O_Count;
	
	@Transient
	private List<TaTpyDocument>			O_Documents;

	@Transient
	private List<TaTpyInformation>		O_Infos;
	
	@Transient
	private	Object 						O_Avatar;
	//---------------------Constructeurs-----------------------
	private TaTpyCategory(){}

	public TaTpyCategory(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		doInitDBFlag();
	}
	
	public TaTpyCategory(String T_Name, Integer I_Type_01, Integer I_Per_Manager) throws Exception {
		this.reqSetAttr(
			ATT_T_NAME       , T_Name,
			ATT_I_TYPE_01    , I_Type_01,
			ATT_I_PER_MANAGER, I_Per_Manager
		);
		doInitDBFlag();
	}
	
	public TaTpyCategory(String T_Name, String T_Code, String T_Info, Integer I_Type_01, Integer I_Type_02, Integer I_Type_03, Integer I_Parent, Integer I_Per_Manager) throws Exception {
		this.reqSetAttr(
			ATT_T_NAME                 , T_Name,
			ATT_T_CODE                 , T_Code,
			ATT_T_INFO                 , T_Info,
			ATT_I_TYPE_01              , I_Type_01,
			ATT_I_TYPE_02              , I_Type_02,
			ATT_I_TYPE_03              , I_Type_03,
			ATT_I_PARENT           , I_Parent,
			ATT_I_PER_MANAGER          , I_Per_Manager
		);
		doInitDBFlag();
	}
	
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaTpyCategory ent) {
		if (ent == this) return;
		this.T_Name                 = ent.T_Name;
		this.T_Code                 = ent.T_Code;
		this.T_Info                 = ent.T_Info;
		this.I_Type_01              = ent.I_Type_01;
		this.I_Type_02              = ent.I_Type_02;
		this.I_Type_03              = ent.I_Type_03;
		this.I_Parent           	= ent.I_Parent;
		this.I_Per_Manager          = ent.I_Per_Manager;



		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((TaTpyCategory)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;
	}

	
	public void doBuildDocuments(boolean forced) throws Exception {
		if (this.O_Documents != null && !forced) return;
		this.O_Documents = TaTpyDocument.reqTpyDocuments(ENT_TYP, I_ID, null, null);
	}
	
	public void doBuildAvatar(boolean forced) throws Exception {
		if (this.O_Avatar != null && !forced) return;
		
		if (this.O_Documents != null && !forced) {
			for (TaTpyDocument doc: this.O_Documents) {
				Integer typ01 = doc.reqInt(TaTpyDocument.ATT_I_TYPE_01);
				Integer typ02 = doc.reqInt(TaTpyDocument.ATT_I_TYPE_02);
				if (typ01!=null && typ02!=null 
						&& typ01.equals(TaTpyDocument.TYPE_01_FILE_MEDIA)  
						&& typ02.equals(TaTpyDocument.TYPE_02_FILE_IMG_AVATAR)){
					this.O_Avatar = doc;
					return;
				}
			}
		}
		TaTpyDocument.reqBuildAvatar (this, DefDBExt.ID_TA_AUT_USER, ATT_O_AVATAR);
	}
	
	
	public static void doBuildAvatarForList(List<TaTpyCategory>  list) throws Exception {
		if (list==null || list.size()==0) return;
		TaTpyDocument.reqBuildAvatar(list, DefDBExt.ID_TA_MAT_MATERIAL, ATT_O_AVATAR);
	}
	
	public void doAddChild(TaTpyCategory child) {
		if (O_Children==null)
			O_Children = new ArrayList<TaTpyCategory>();
		
		O_Children.add(child);
		
		child.O_Parent_Name = this.T_Name;
	}
	
	public void doBuildChildren () throws Exception {
		doBuildChildren(this);
	}
	
	public void doBuildParent () throws Exception {
		Integer pId = this.I_Parent;
		if (pId!=null) {
			TaTpyCategory par = TaTpyCategory.DAO.reqEntityByRef(pId);
			if (par!=null) {
				this.O_Parent_Name = par.T_Name;
			}
		}
	}
	
	public void doBuildInfo () throws Exception {
		this.O_Infos = TaTpyInformation.reqList(DefDBExt.ID_TA_TPY_CATEGORY, this.I_ID, null);
	}
	//-------------------------------------------------------------------------------------------------------
	public static void doBuildChildren (TaTpyCategory ent)  throws Exception{
		List<TaTpyCategory> list = new ArrayList<TaTpyCategory> ();
		list.add(ent);
		doBuildChildren(list);
	}
	
	public static void doBuildChildren (List<TaTpyCategory> list)  throws Exception{
		Hashtable<Integer, EntityAbstract> 	tab 		= ToolDBEntity.reqTabKeyInt(list, TaTpyCategory.ATT_I_ID);
		Set<Integer> 						ids 		= tab.keySet();
		List<TaTpyCategory> 				lstChild 	= TaTpyCategory.DAO.reqList_In(TaTpyCategory.ATT_I_PARENT, ids);
		
		if (lstChild!=null && lstChild.size()>0) {
			for (TaTpyCategory child: lstChild) {
				TaTpyCategory p = (TaTpyCategory) tab.get(child.I_Parent);
				p.doAddChild(child);
			}
			doBuildChildren (lstChild);
		}
		
	}
	
	public static void doBuildParent (List<TaTpyCategory> list)  throws Exception{
		Set<Integer> 						ids 		= ToolSet.reqSetInt(list, TaTpyCategory.ATT_I_PARENT);
		List<TaTpyCategory> 				lstParent 	= TaTpyCategory.DAO.reqList_In(TaTpyCategory.ATT_I_ID, ids);
		Hashtable<Integer, EntityAbstract> 	tab 		= ToolDBEntity.reqTabKeyInt(lstParent, TaTpyCategory.ATT_I_ID);
		
		for (TaTpyCategory ent: list) {
			Integer pId = ent.I_Parent;
			if (pId!=null) {
				TaTpyCategory par = (TaTpyCategory) tab.get(pId);
				if (par!=null) {
					ent.O_Parent_Name = par.T_Name;
				}
			}
		}
	}
	
	public static void doBuildDocuments (List<TaTpyCategory> list)  throws Exception{
		TaTpyDocument.doBuildTpyDocuments(list, ENT_TYP, null, null, TaTpyCategory.ATT_O_DOCUMENTS, false);
	}
	
	public static void doBuildCount (List<TaTpyCategory> list, Integer entTyp, Integer stat01, Integer stat02)  throws Exception{
		ViTpyCategoryCount.doBuildCount(list, entTyp, stat01, stat02);
	}
	
	public static List<TaTpyCategory> reqListByType (Integer type, Integer manId) throws Exception{		
		Criterion cri = Restrictions.gt("I_ID", 0);	

		if (type!=null)
			cri = Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyCategory.ATT_I_TYPE_01, type));

		if (manId!=null)
			cri = Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyCategory.ATT_I_PER_MANAGER, manId));
		
		List lst = TaTpyCategory.DAO.reqList(Order.asc(TaTpyCategory.ATT_T_NAME), cri);
				
		return lst;
	}

	public static Map<Integer, TaTpyCategory> reqMapByType (Integer type, Integer manId) throws Exception{		
		Criterion cri = Restrictions.gt("I_ID", 0);	

		if (type!=null)
			cri = Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyCategory.ATT_I_TYPE_01, type));

		if (manId!=null)
			cri = Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyCategory.ATT_I_PER_MANAGER, manId));
		
		return TaTpyCategory.DAO.reqMap(cri);
	}
	
	public static List<TaTpyCategory> reqListMultiLevel (Integer type, Integer manId) throws Exception{		
		Criterion cri = Restrictions.gt("I_ID", 0);	

		if (type!=null)
			cri = Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyCategory.ATT_I_TYPE_01, type));

		if (manId!=null)
			cri = Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyCategory.ATT_I_PER_MANAGER, manId));
		
		List<TaTpyCategory> lst = TaTpyCategory.DAO.reqList(Order.asc(TaTpyCategory.ATT_T_CODE), cri);
		
		ToolDBEntity.reqTabKeyInt(lst, TaTpyCategory.ATT_I_ID);
		
		List lstRS = new ArrayList<TaTpyCategory>();
		
		for (TaTpyCategory cat : lst) {
			Integer pId = (Integer) cat.req(TaTpyCategory.ATT_I_PARENT);
			if (pId == null) 
				lstRS.add(cat);
			else {
				TaTpyCategory par = (TaTpyCategory)lstRS.get(pId);
				par.doAddChild(cat);
			}
		}
		
		return lstRS;
	}
	
	public static List<TaTpyCategory> reqListMod(Integer entID,  JSONArray lstJson) throws Exception {
//		if (lstJson==null || lstJson.size()==0)	return null;

		List		<TaTpyCategory> 			lstMod 			= new ArrayList<TaTpyCategory > 	();
		List		<Map<String, Object>> 		lstModVal 		= new ArrayList<Map<String, Object>>();
		List		<TaTpyCategory> 			lstNew 			= new ArrayList<TaTpyCategory > 	();
		Collection	<TaTpyCategory> 			lstDel 			= null;
		Collection	<TaTpyCategory>  			lstObj 			= TaTpyCategory.DAO.reqList(	Restrictions.eq(TaTpyCategory.ATT_I_PARENT, entID));		

		HashMap		<Integer,TaTpyCategory> 	map 		= new HashMap<Integer,TaTpyCategory>();


		if (lstObj!=null){
			for(TaTpyCategory d:lstObj){
				Integer id = (Integer) d.req(TaTpyCategoryEntity.ATT_I_ID);		
				d.reqSet(TaTpyCategory.ATT_I_PARENT	, entID		);				
				map.put(id, d);			 
			}
		}

		for(int i = 0; i < lstJson.size(); i++) {
			JSONObject 			o 		= (JSONObject) lstJson.get(i);
			if(o == null) continue;
			Map<String, Object> attr 	= API.reqMapParamsByClass(o, TaTpyCategory.class);


			Integer 			id		= (Integer) attr.get(TaTpyCategory.ATT_I_ID);

			if (id!=null && map.containsKey(id)){
				attr.remove(TaTpyCategory.ATT_I_PARENT);

				lstMod		.add	(map.get(id));					
				lstModVal	.add	(attr);
				map			.remove	(id);
			}else{
				TaTpyCategory poO	= new TaTpyCategory(API.reqMapParamsByClass(o, TaTpyCategory.class));
				poO.reqSet(TaTpyCategory.ATT_I_PARENT			, entID);
				poO.reqSet(TaTpyCategory.ATT_I_ID				, null);

				lstNew		.add	(poO);
			}
		}

		if (map.size()>0){
			lstDel = map.values();
			if(lstDel != null && lstDel.size() > 0) {
				for (TaTpyCategory a : lstDel) {
					a.reqSet(ATT_I_PARENT, null);
				}
			}
		}

		Session sess = TaTpyCategory.DAO.reqSessionCurrent();
		try {
			TaTpyCategory.DAO.doMerge			(sess, lstMod, lstModVal);
			TaTpyCategory.DAO.doPersist			(sess, lstNew);
			TaTpyCategory.DAO.doMerge			(sess, lstDel);
			TaTpyCategory.DAO.doSessionCommit	(sess);
			lstMod.addAll(lstNew);
			return lstMod;
		}catch(Exception e){
			e.printStackTrace();
			TaTpyCategory.DAO.doSessionRollback(sess);
		}
		return null;
	}
}
