package com.hnv.db.nso.vi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import org.hibernate.criterion.Restrictions;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.common.tool.ToolData;
import com.hnv.common.tool.ToolString;
import com.hnv.data.json.JSONObject;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.nso.TaNsoOffer;
import com.hnv.db.tpy.TaTpyCategoryEntity;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.db.tpy.vi.ViTpyCategoryDyn;
import com.hnv.def.DefDBExt;
import com.hnv.index.tool.GeoPoint;	

/**
* TaNsoOffer by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_NSO_OFFER )
public class ViNsoOfferSearch extends EntityAbstract<ViNsoOfferSearch> {

	private static final long serialVersionUID = 1L;
	 
	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_T_TITLE                           =	"T_Title";
	public static final String	COL_I_STATUS_01                       =	"I_Status_01";
	public static final String	COL_I_STATUS_02                       =	"I_Status_02";
	public static final String	COL_I_PARENT                          =	"I_Parent";
	public static final String	COL_T_CODE_01                         =	"T_Code_01";
	public static final String	COL_I_TYPE_01                         =	"I_Type_01";
	public static final String	COL_I_TYPE_02                         =	"I_Type_02";
	public static final String	COL_I_TYPE_03                         =	"I_Type_03";
	public static final String	COL_T_CONTENT_02                      =	"T_Content_02";
	public static final String	COL_T_CONTENT_03                      =	"T_Content_03";
	public static final String	COL_T_CONTENT_04                      =	"T_Content_04";
	public static final String	COL_T_CONTENT_05                      =	"T_Content_05";
	public static final String	COL_T_INFO_02                         =	"T_Info_02";
	public static final String	COL_D_DATE_05                         =	"D_Date_05";
	public static final String	COL_I_VAL_02                          =	"I_Val_02";
	public static final String	COL_I_VAL_03                          =	"I_Val_03";
	public static final String	COL_I_VAL_04                          =	"I_Val_04";
	public static final String	COL_I_VAL_05                          =	"I_Val_05";
	public static final String	COL_F_VAL_01                          =	"F_Val_01";
	public static final String	COL_F_VAL_02                          =	"F_Val_02";

	
	public static final String	COL_T_AVATAR                          =	"T_Avatar";

	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_T_TITLE                           =	"T_Title";
	public static final String	ATT_I_STATUS_01                       =	"I_Status_01";
	public static final String	ATT_I_STATUS_02                       =	"I_Status_02";
	public static final String	ATT_I_PARENT                          =	"I_Parent";
	public static final String	ATT_T_CODE_01                         =	"T_Code_01";
	public static final String	ATT_I_TYPE_01                         =	"I_Type_01";
	public static final String	ATT_I_TYPE_02                         =	"I_Type_02";
	public static final String	ATT_I_TYPE_03                         =	"I_Type_03";
	public static final String	ATT_T_CONTENT_02                      =	"T_Content_02";
	public static final String	ATT_T_CONTENT_03                      =	"T_Content_03";
	public static final String	ATT_T_CONTENT_04                      =	"T_Content_04";
	public static final String	ATT_T_CONTENT_05                      =	"T_Content_05";
	public static final String	ATT_T_INFO_02                         =	"T_Info_02";
	public static final String	ATT_D_DATE_03                         =	"D_Date_03";
	public static final String	ATT_D_DATE_04                         =	"D_Date_04";
	public static final String	ATT_D_DATE_05                         =	"D_Date_05";
	public static final String	ATT_I_VAL_02                          =	"I_Val_02";
	public static final String	ATT_I_VAL_03                          =	"I_Val_03";
	public static final String	ATT_I_VAL_04                          =	"I_Val_04";
	public static final String	ATT_I_VAL_05                          =	"I_Val_05";
	public static final String	ATT_F_VAL_01                          =	"F_Val_01";
	public static final String	ATT_F_VAL_02                          =	"F_Val_02";
	
	public static final String 	ATT_O_CATS 					  		  = "O_Cats";
	public static final String	ATT_O_DOCUMENTS                       =	"O_Documents";



	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<ViNsoOfferSearch> 	DAO;
	static{
		DAO = new EntityDAO<ViNsoOfferSearch>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), ViNsoOfferSearch.class,RIGHTS, HISTORY, DefDBExt.TA_NSO_OFFER, DefDBExt.ID_TA_NSO_OFFER);

	}

	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_T_TITLE, nullable = true)
	private	String          T_Title;
    
	@Column(name=COL_I_STATUS_01, nullable = true)
	private	Integer         I_Status_01;
	
	@Column(name=COL_I_STATUS_02, nullable = true)
	private	Integer         I_Status_02;
     
	@Column(name=COL_I_PARENT, nullable = true)
	private	Integer         I_Parent;
     
	@Column(name=COL_T_CODE_01, nullable = true)
	private	String          T_Code_01;
    
	@Column(name=COL_I_TYPE_01, nullable = true)
	private	Integer         I_Type_01;
    
	@Column(name=COL_I_TYPE_02, nullable = true)
	private	Integer         I_Type_02;
    
	@Column(name=COL_I_TYPE_03, nullable = true)
	private	Integer         I_Type_03;
	
	@Column(name=COL_T_AVATAR, nullable = true)
	private	String          T_Avatar;
	
	@Column(name=COL_T_CONTENT_02, nullable = true)
	private	String          T_Content_02;
	
	@Column(name=COL_T_CONTENT_03, nullable = true)
	private	String          T_Content_03;
 
	@Column(name=COL_T_CONTENT_04, nullable = true)
	private	String          T_Content_04;
	 
	@Column(name=COL_T_CONTENT_05, nullable = true)
	private	String          T_Content_05;
    
	@Column(name=COL_T_INFO_02, nullable = true)
	private	String          T_Info_02;
    
	@Column(name=COL_D_DATE_05, nullable = true)
	private	Date            D_Date_05;
	@Column(name=COL_I_VAL_02, nullable = true)
	private	Integer         I_Val_02;
     
	@Column(name=COL_I_VAL_03, nullable = true)
	private	Integer         I_Val_03;
     
	@Column(name=COL_I_VAL_04, nullable = true)
	private	Integer         I_Val_04;
     
	@Column(name=COL_I_VAL_05, nullable = true)
	private	Integer         I_Val_05;
    
	@Column(name=COL_F_VAL_01, nullable = true)
	private	Double         	F_Val_01;
    
	@Column(name=COL_F_VAL_02, nullable = true)
	private	Double         	F_Val_02;

	
	//-----------------------Transient Variables-------------------------

	@Transient
	private	List<TaTpyCategoryEntity>  	O_Category_Entity;
	
	@Transient
	private	List<ViTpyCategoryDyn>  	O_Cats;

	@Transient
	private List<TaTpyDocument>			O_Documents;

	//---------------------Constructeurs-----------------------
	private ViNsoOfferSearch(){}

	public ViNsoOfferSearch(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		// doInitDBFlag();
	}
	
	
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(ViNsoOfferSearch ent) {
		if (ent == this) return;
		this.T_Title                = ent.T_Title;		
		this.I_Parent               = ent.I_Parent;
		this.T_Code_01              = ent.T_Code_01;
		this.I_Type_01              = ent.I_Type_01;
		this.I_Type_02              = ent.I_Type_02;
		this.I_Type_03              = ent.I_Type_03;
		this.T_Content_03           = ent.T_Content_03;
		this.T_Content_04           = ent.T_Content_04;
		this.T_Info_02              = ent.T_Info_02;
		this.D_Date_05              = ent.D_Date_05;
		this.I_Val_02               = ent.I_Val_02;
		this.I_Val_03               = ent.I_Val_03;
		this.I_Val_04               = ent.I_Val_04;
		this.I_Val_05               = ent.I_Val_05;


		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((ViNsoOfferSearch)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}
	
	
	//-------------------------------------------------------------
	private static final String SQL_REQ_LIST_SELECT_SEARCH 		= "SELECT"
			+   " a." 			+ ViNsoOfferSearch.COL_I_ID						+ " AS " + COL_I_ID 					+ ","
			+   " a." 			+ ViNsoOfferSearch.COL_I_PARENT					+ " AS " + COL_I_PARENT 				+ ","
			+   " a." 			+ ViNsoOfferSearch.COL_I_STATUS_01				+ " AS " + COL_I_STATUS_01 				+ ","
			+   " a." 			+ ViNsoOfferSearch.COL_I_STATUS_02				+ " AS " + COL_I_STATUS_02 				+ ","
			
			+	"COALESCE(d."	+ TaTpyDocument.COL_T_INFO_05 					+ ", d." + TaTpyDocument.COL_T_INFO_03 	+") AS " + COL_T_AVATAR	+ ","
			
			+   " a." 			+ ViNsoOfferSearch.COL_T_CODE_01				+ " AS " + COL_T_CODE_01 				+ ","
			+   " a." 			+ ViNsoOfferSearch.COL_T_TITLE					+ " AS " + COL_T_TITLE 					+ ","
			+   " a." 			+ ViNsoOfferSearch.COL_T_CONTENT_05				+ " AS " + COL_T_CONTENT_05				+ ","
			
			+   " a." 			+ ViNsoOfferSearch.COL_F_VAL_01					+ " AS " + COL_F_VAL_01 				+ ","
			+   " a." 			+ ViNsoOfferSearch.COL_F_VAL_02					+ " AS " + COL_F_VAL_02 				+ ","
			
			+   " a." 			+ ViNsoOfferSearch.COL_T_INFO_02				+ " AS " + COL_T_INFO_02	 			+ ","	//address									
			
			+   " a." 			+ ViNsoOfferSearch.COL_I_TYPE_01				+ " AS " + COL_I_TYPE_01 				+ ","
			+   " a." 			+ ViNsoOfferSearch.COL_I_TYPE_02				+ " AS " + COL_I_TYPE_02 				+ ","
			+   " a." 			+ ViNsoOfferSearch.COL_I_TYPE_03				+ " AS " + COL_I_TYPE_03 				+ ","
			+   " a." 			+ ViNsoOfferSearch.COL_I_VAL_02					+ " AS " + COL_I_VAL_02 				+ ","
			+   " a." 			+ ViNsoOfferSearch.COL_I_VAL_03					+ " AS " + COL_I_VAL_03 				+ ","
			+   " a." 			+ ViNsoOfferSearch.COL_I_VAL_04					+ " AS " + COL_I_VAL_04 				+ ","
			+   " a." 			+ ViNsoOfferSearch.COL_I_VAL_05					+ " AS " + COL_I_VAL_05 				+ ","
			+   " a." 			+ ViNsoOfferSearch.COL_T_CONTENT_02				+ " AS " + COL_T_CONTENT_02 			+ ","
			+   " a." 			+ ViNsoOfferSearch.COL_T_CONTENT_03				+ " AS " + COL_T_CONTENT_03 			+ ","
			+   " a." 			+ ViNsoOfferSearch.COL_T_CONTENT_04				+ " AS " + COL_T_CONTENT_04 			+ ","
			+   " a." 			+ ViNsoOfferSearch.COL_T_CONTENT_05				+ " AS " + COL_T_CONTENT_05 			+ ","
			+   " a." 			+ ViNsoOfferSearch.COL_D_DATE_05				+ " AS " + COL_D_DATE_05 				+ "";

	private static final String SQL_REQ_LIST_FROM_SUB			= " FROM (SELECT * ";
		
	public void doBuildCats(boolean forced) throws Exception {       
		if (this.O_Cats!=null && !forced) return;
		
		Criterion 					cri 			= Restrictions.eq(TaTpyCategoryEntity.ATT_I_ENTITY_ID, this.I_ID);
		List<TaTpyCategoryEntity> 	catEntities 	= TaTpyCategoryEntity.DAO.reqList(cri);
		
		Set<Integer> lstId = new HashSet<Integer>();
		for(TaTpyCategoryEntity catEntity : catEntities) {
			lstId.add(catEntity.reqInt(TaTpyCategoryEntity.ATT_I_TPY_CATEGORY));
		}
		
		this.O_Cats = ViTpyCategoryDyn.DAO.reqList(
				Restrictions.in(ViTpyCategoryDyn.ATT_I_ID	, lstId)
		);
	}
	
	
	private static String reqLstByIdsSql(Set<Integer> ids) throws Exception {
		String sql	= SQL_REQ_LIST_SELECT_SEARCH;
		
		sql += SQL_REQ_LIST_FROM_SUB;
		sql += TaNsoOffer.SQL_REQ_LIST_FROM;
		sql += TaNsoOffer.SQL_REQ_WHERE;
		sql += TaNsoOffer.SQL_AND + String.format(TaNsoOffer.SQL_REQ_WHERE_IDS, ToolString.reqStringFromCollection(ids, ",")); 
		sql += ") a";
		sql += TaNsoOffer.SQL_REQ_LIST_JOIN_DOCUMENT;
		return sql;
	}
	
	public static List<ViNsoOfferSearch> reqLstByIds(Set<Integer> ids, Integer begin, Integer nbLine) throws Exception {
		List<ViNsoOfferSearch> 	list 	= new ArrayList<ViNsoOfferSearch>();		
		String 					sql 	= reqLstByIdsSql(ids);		
		list = ViNsoOfferSearch.DAO.reqList(begin, nbLine, sql);
			
		return list;
	}
	
	private static String reqRestriction(Set<String> searchKey) {	
		if (searchKey==null) 		return null;
		if (searchKey.equals("%")) 	return null;
		String restr = "";
		
		for(String s : searchKey) {
			restr += String.format(TaNsoOffer.SQL_REQ_WHERE_SEARCH, s, s) + TaNsoOffer.SQL_OR;
		}

		if (restr.length()==0) return null;
		else restr = "(" + restr.substring(0, restr.length() - TaNsoOffer.SQL_OR.length() - 1) + ")";
		return restr;
	}
	
	public static String reqLstSql(Integer stat01, Integer stat02, Integer typ01, Integer typ03, Set<String> searchKey, Set<Integer> lstCat, JSONObject mon, Integer monTyp) throws Exception {
		String sql	= SQL_REQ_LIST_SELECT_SEARCH;
		sql += reqSqlWithoutSelect(stat01, stat02, typ01, typ03, searchKey, lstCat, mon,monTyp);
		
		return sql;
	}
	
	public static String reqLstSql(String sqlWithoutSelect) throws Exception {
		return SQL_REQ_LIST_SELECT_SEARCH + sqlWithoutSelect;
	}
	
	public static String reqSqlWithoutSelect(Integer stat01, Integer stat02, Integer typ01, Integer typ03, Set<String> searchKey, Set<Integer> lstCat, JSONObject mon, Integer monTyp) {
		String sql = "";
		
		sql += TaNsoOffer.SQL_REQ_LIST_FROM;

		sql += TaNsoOffer.SQL_REQ_LIST_JOIN_DOCUMENT;
		
		sql += TaNsoOffer.SQL_REQ_WHERE;
		
		if (lstCat!=null && lstCat.size()>0){
			sql += TaNsoOffer.SQL_AND + String.format(TaNsoOffer.SQL_REQ_WHERE_TYPES, lstCat);
		}
		
		if(mon != null) {
			Integer moneyMin = ToolData.reqInt	(mon, "min"		, 0); ;
			Integer moneyMax = ToolData.reqInt	(mon, "max"		, Integer.MAX_VALUE);  
			sql += TaNsoOffer.SQL_AND + String.format(TaNsoOffer.SQL_REQ_WHERE_BETWEEN_MON, moneyMin, moneyMax);
		}
		
		if(typ01 != null)
			sql += TaNsoOffer.SQL_AND + String.format(TaNsoOffer.SQL_REQ_WHERE_TYP_01, typ01);
		if(typ03 != null)
			sql += TaNsoOffer.SQL_AND + String.format(TaNsoOffer.SQL_REQ_WHERE_TYP_03, typ03);
		
		//status
		if(stat01 != null)
			sql += TaNsoOffer.SQL_AND + String.format(TaNsoOffer.SQL_REQ_WHERE_STAT_01, stat01);
		if(stat02 != null)
			sql += TaNsoOffer.SQL_AND + String.format(TaNsoOffer.SQL_REQ_WHERE_STAT_02, stat02);
		
		//searchKey
		String restriction	= reqRestriction(searchKey);
		
		if(restriction != null) {
			sql += TaNsoOffer.SQL_AND + restriction;
		}
		
		sql += " Order By a." + ATT_I_ID + " DESC";
		
		return sql;
	}
	
	public static List<ViNsoOfferSearch> reqLst(Integer stat01, Integer stat02, Integer typ01, Integer typ03, Set<String> searchKey, Set<Integer> lstCat, JSONObject mon,Integer monTyp, Integer begin, Integer nbLine) throws Exception {
		List<ViNsoOfferSearch> 	list 	= new ArrayList<ViNsoOfferSearch>();		
		String 					sql 	= reqLstSql(stat01, stat02, typ01, typ03, searchKey, lstCat, mon,monTyp);	
		
		list = ViNsoOfferSearch.DAO.reqList(begin, nbLine, sql);
			
		return list;
	}


	public static String reqCountSql(String sqlWithoutSelect) throws Exception {
		return TaNsoOffer.SQL_REQ_LIST_SELECT_COUNT + sqlWithoutSelect;
	}
}
