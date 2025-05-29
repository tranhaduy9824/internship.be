package com.hnv.db.mat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.common.tool.ToolDBEntity;
import com.hnv.common.tool.ToolSet;
import com.hnv.data.json.JSONObject;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.per.TaPerPerson;
import com.hnv.db.tpy.TaTpyCategoryEntity;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.db.tpy.TaTpyInformation;
import com.hnv.def.DefDBExt;	


/**
 * TaMatMaterial by H&V SAS
 */
@Entity
@Table(name = DefDBExt.TA_MAT_MATERIAL )
public class TaMatMaterial extends EntityAbstract<TaMatMaterial> {

	private static final long serialVersionUID 	= 1L;
	private static final int  ENT_TYP			= DefDBExt.ID_TA_MAT_MATERIAL;
	
	public static final int	TYPE_01_MAT 		= 1;
	public static final int	TYPE_01_FINANCE 	= 100;
	public static final int	TYPE_01_SERVICE 	= 200;
	
	public static final int	TYPE_02_SINGLE      = 1;
	public static final int	TYPE_02_BOM 	    = 2;
	
	public static final int	STAT_NEW 			= 0;
	public static final int	STAT_ACTIVE 		= 1; 
	public static final int	STAT_REVIEW 		= 5; 
	public static final int	STAT_DELETED 		= 10;
	 
	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	
	public static final String	COL_I_STATUS_01                       =	"I_Status_01";
	public static final String	COL_I_STATUS_02                       =	"I_Status_02";
	public static final String	COL_I_STATUS_03                       =	"I_Status_03";
	public static final String	COL_I_STATUS_04                       =	"I_Status_04";
	public static final String	COL_I_STATUS_05                       =	"I_Status_05";
	
	public static final String	COL_I_TYPE_01                         =	"I_Type_01";
	public static final String	COL_I_TYPE_02                         =	"I_Type_02";
	public static final String	COL_I_TYPE_03                         =	"I_Type_03";
	public static final String	COL_I_TYPE_04                         =	"I_Type_04";
	public static final String	COL_I_TYPE_05                         =	"I_Type_05";
	
	public static final String	COL_T_NAME_01                         =	"T_Name_01";
	public static final String	COL_T_NAME_02                         =	"T_Name_02";
	public static final String	COL_T_NAME_03                         =	"T_Name_03";
	
	public static final String	COL_T_CODE_01                         =	"T_Code_01";
	public static final String	COL_T_CODE_02                         =	"T_Code_02";
	public static final String	COL_T_CODE_03                         =	"T_Code_03";
	public static final String	COL_T_CODE_04                         =	"T_Code_04";
	public static final String	COL_T_CODE_05                         =	"T_Code_05";
	public static final String	COL_T_INFO_01                         =	"T_Info_01";
	public static final String	COL_T_INFO_02                         =	"T_Info_02";
	public static final String	COL_T_INFO_03                         =	"T_Info_03";
	public static final String	COL_T_INFO_04                         =	"T_Info_04";
	public static final String	COL_T_INFO_05                         =	"T_Info_05";
	public static final String	COL_T_INFO_06                         =	"T_Info_06";
	public static final String	COL_T_INFO_07                         =	"T_Info_07";
	public static final String	COL_T_INFO_08                         =	"T_Info_08";
	public static final String	COL_T_INFO_09                         =	"T_Info_09";
	public static final String	COL_T_INFO_10                         =	"T_Info_10";
	public static final String	COL_D_DATE_01                         =	"D_Date_01";
	public static final String	COL_D_DATE_02                         =	"D_Date_02";
	public static final String	COL_D_DATE_03                         =	"D_Date_03";
	public static final String	COL_D_DATE_04                         =	"D_Date_04";
	public static final String	COL_I_AUT_USER_01                     =	"I_Aut_User_01";
	public static final String	COL_I_AUT_USER_02                     =	"I_Aut_User_02";
	public static final String	COL_I_PER_MANAGER                     =	"I_Per_Manager";
	public static final String	COL_I_PER_PERSON_01                   =	"I_Per_Person_01";
	public static final String	COL_I_PER_PERSON_02                   =	"I_Per_Person_02";



	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_I_STATUS_01                       =	"I_Status_01";
	public static final String	ATT_I_STATUS_02                       =	"I_Status_02";
	public static final String	ATT_I_STATUS_03                       =	"I_Status_03";
	public static final String	ATT_I_STATUS_04                       =	"I_Status_04";
	public static final String	ATT_I_STATUS_05                       =	"I_Status_05";
	
	public static final String	ATT_I_TYPE_01                         =	"I_Type_01";
	public static final String	ATT_I_TYPE_02                         =	"I_Type_02";
	public static final String	ATT_I_TYPE_03                         =	"I_Type_03";
	public static final String	ATT_I_TYPE_04                         =	"I_Type_04";
	public static final String	ATT_I_TYPE_05                         =	"I_Type_05";
	
	public static final String	ATT_T_NAME_01                         =	"T_Name_01";
	public static final String	ATT_T_NAME_02                         =	"T_Name_02";
	public static final String	ATT_T_NAME_03                         =	"T_Name_03";
	
	public static final String	ATT_T_CODE_01                         =	"T_Code_01";
	public static final String	ATT_T_CODE_02                         =	"T_Code_02";
	public static final String	ATT_T_CODE_03                         =	"T_Code_03";
	public static final String	ATT_T_CODE_04                         =	"T_Code_04";
	public static final String	ATT_T_CODE_05                         =	"T_Code_05";
	public static final String	ATT_T_INFO_01                         =	"T_Info_01";
	public static final String	ATT_T_INFO_02                         =	"T_Info_02";
	public static final String	ATT_T_INFO_03                         =	"T_Info_03";
	public static final String	ATT_T_INFO_04                         =	"T_Info_04";
	public static final String	ATT_T_INFO_05                         =	"T_Info_05";
	public static final String	ATT_T_INFO_06                         =	"T_Info_06";
	public static final String	ATT_T_INFO_07                         =	"T_Info_07";
	public static final String	ATT_T_INFO_08                         =	"T_Info_08";
	public static final String	ATT_T_INFO_09                         =	"T_Info_09";
	public static final String	ATT_T_INFO_10                         =	"T_Info_10";
	
	public static final String	ATT_D_DATE_01                         =	"D_Date_01";
	public static final String	ATT_D_DATE_02                         =	"D_Date_02";
	public static final String	ATT_D_DATE_03                         =	"D_Date_03";
	public static final String	ATT_D_DATE_04                         =	"D_Date_04";
	
	public static final String	ATT_I_AUT_USER_01                     =	"I_Aut_User_01";
	public static final String	ATT_I_AUT_USER_02                     =	"I_Aut_User_02";
	public static final String	ATT_I_PER_MANAGER                     =	"I_Per_Manager";
	public static final String	ATT_I_PER_PERSON_01                   =	"I_Per_Person_01";
	public static final String	ATT_I_PER_PERSON_02                   =	"I_Per_Person_02";

	//----------------------------------------------------------------------------------------------
	public static final String	ATT_O_PRODUCER                   	  =	"O_Producer";
	public static final String	ATT_O_MANAGER                   	  =	"O_Manager";
	
	public static final String	ATT_O_AVATAR                       	  =	"O_Avatar";
	public static final String	ATT_O_DOCUMENTS                       =	"O_Documents";
	public static final String	ATT_O_DETAILS                      	  =	"O_Details";
	
	public static final String	ATT_O_CATS                      	  =	"O_Cats";
	
	public static final String	ATT_O_PRICES_OUT                      =	"O_Prices_Out";
	public static final String	ATT_O_PRICES_INP                      =	"O_Prices_Inp";
	public static final String	ATT_O_PRICES_ADVICE                   =	"O_Prices_Advice";

	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaMatMaterial> 	DAO;
	static	{
		DAO = new EntityDAO<TaMatMaterial>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaMatMaterial.class,RIGHTS, HISTORY, DefDBExt.TA_MAT_MATERIAL, DefDBExt.ID_TA_MAT_MATERIAL);
	}

	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_I_STATUS_01, nullable = true)
	private	Integer         I_Status_01;
	@Column(name=COL_I_STATUS_02, nullable = true)
	private	Integer         I_Status_02;
	@Column(name=COL_I_STATUS_03, nullable = true)
	private	Integer         I_Status_03;
	@Column(name=COL_I_STATUS_04, nullable = true)
	private	Integer         I_Status_04;
	@Column(name=COL_I_STATUS_05, nullable = true)
	private	Integer         I_Status_05;
     
	@Column(name=COL_I_TYPE_01, nullable = true)
	private	Integer         I_Type_01;
	@Column(name=COL_I_TYPE_02, nullable = true)
	private	Integer         I_Type_02;
	@Column(name=COL_I_TYPE_03, nullable = true)
	private	Integer         I_Type_03;
	@Column(name=COL_I_TYPE_04, nullable = true)
	private	Integer         I_Type_04;
	@Column(name=COL_I_TYPE_05, nullable = true)
	private	Integer         I_Type_05;
    
	@Column(name=COL_T_NAME_01, nullable = true)
	private	String          T_Name_01;
	@Column(name=COL_T_NAME_02, nullable = true)
	private	String          T_Name_02;
	@Column(name=COL_T_NAME_03, nullable = true)
	private	String          T_Name_03;
    
	@Column(name=COL_T_CODE_01, nullable = true)
	private	String          T_Code_01;
    
	@Column(name=COL_T_CODE_02, nullable = true)
	private	String          T_Code_02;
    
	@Column(name=COL_T_CODE_03, nullable = true)
	private	String          T_Code_03;
    
	@Column(name=COL_T_CODE_04, nullable = true)
	private	String          T_Code_04;
    
	@Column(name=COL_T_CODE_05, nullable = true)
	private	String          T_Code_05;
    
	@Column(name=COL_T_INFO_01, nullable = true)
	private	String          T_Info_01;
    
	@Column(name=COL_T_INFO_02, nullable = true)
	private	String          T_Info_02;
    
	@Column(name=COL_T_INFO_03, nullable = true)
	private	String          T_Info_03;
    
	@Column(name=COL_T_INFO_04, nullable = true)
	private	String          T_Info_04;
    
	@Column(name=COL_T_INFO_05, nullable = true)
	private	String          T_Info_05;
    
	@Column(name=COL_T_INFO_06, nullable = true)
	private	String          T_Info_06;
    
	@Column(name=COL_T_INFO_07, nullable = true)
	private	String          T_Info_07;
    
	@Column(name=COL_T_INFO_08, nullable = true)
	private	String          T_Info_08;
    
	@Column(name=COL_T_INFO_09, nullable = true)
	private	String          T_Info_09;
    
	@Column(name=COL_T_INFO_10, nullable = true)
	private	String          T_Info_10;
    
	@Column(name=COL_D_DATE_01, nullable = true)
	private	Date            D_Date_01;
    
	@Column(name=COL_D_DATE_02, nullable = true)
	private	Date            D_Date_02;
    
	@Column(name=COL_D_DATE_03, nullable = true)
	private	Date            D_Date_03;
    
	@Column(name=COL_D_DATE_04, nullable = true)
	private	Date            D_Date_04;
    
	@Column(name=COL_I_AUT_USER_01, nullable = true)
	private	Integer         I_Aut_User_01;

	@Column(name=COL_I_AUT_USER_02, nullable = true)
	private	Integer         I_Aut_User_02;

	@Column(name=COL_I_PER_MANAGER, nullable = true)
	private	Integer         I_Per_Manager;

	@Column(name=COL_I_PER_PERSON_01, nullable = true)
	private	Integer         I_Per_Person_01;

	@Column(name=COL_I_PER_PERSON_02, nullable = true)
	private	Integer         I_Per_Person_02;


	//-----------------------Transient Variables-------------------------
	@Transient
	private	List<TaTpyDocument> 		O_Documents;

	@Transient
	private	List<TaMatMaterialDetail> 	O_Details;

	@Transient
	private List<TaTpyCategoryEntity>	O_Cats;
	
	@Transient
	private	TaPerPerson 				O_Producer;

	@Transient
	private	TaPerPerson 				O_Manager;

	
	@Transient
	private	List<TaMatPrice> 			O_Prices_Out;
	
	@Transient
	private	List<TaMatPrice> 			O_Prices_Inp;
	
	@Transient
	private	List<TaMatPrice> 			O_Prices_Advice;
	
	@Transient
	private	Object 						O_Avatar;
	
	//---------------------Constructeurs-----------------------
	public TaMatMaterial(){}

	public TaMatMaterial(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		//doInitDBFlag();
	}

	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaMatMaterial ent) {
		if (ent == this) return;
		this.I_Status_01            = ent.I_Status_01;
		this.I_Status_02            = ent.I_Status_02;
		this.I_Status_03            = ent.I_Status_03;
		this.I_Status_04            = ent.I_Status_04;
		this.I_Status_05            = ent.I_Status_05;
		
		this.I_Type_01              = ent.I_Type_01;
		this.I_Type_02              = ent.I_Type_02;
		this.I_Type_03              = ent.I_Type_03;
		this.I_Type_04              = ent.I_Type_04;
		this.I_Type_05              = ent.I_Type_05;
		
		this.T_Name_01              = ent.T_Name_01;
		this.T_Name_02              = ent.T_Name_02;
		this.T_Name_03              = ent.T_Name_03;
		
		this.T_Code_01              = ent.T_Code_01;
		this.T_Code_02              = ent.T_Code_02;
		this.T_Code_03              = ent.T_Code_03;
		this.T_Code_04              = ent.T_Code_04;
		this.T_Code_05              = ent.T_Code_05;
		this.T_Info_01              = ent.T_Info_01;
		this.T_Info_02              = ent.T_Info_02;
		this.T_Info_03              = ent.T_Info_03;
		this.T_Info_04              = ent.T_Info_04;
		this.T_Info_05              = ent.T_Info_05;
		this.T_Info_06              = ent.T_Info_06;
		this.T_Info_07              = ent.T_Info_07;
		this.T_Info_08              = ent.T_Info_08;
		this.T_Info_09              = ent.T_Info_09;
		this.T_Info_10              = ent.T_Info_10;
		this.D_Date_01              = ent.D_Date_01;
		this.D_Date_02              = ent.D_Date_02;
		this.D_Date_03              = ent.D_Date_03;
		this.D_Date_04              = ent.D_Date_04;
		this.I_Aut_User_01          = ent.I_Aut_User_01;
		this.I_Aut_User_02          = ent.I_Aut_User_02;
		this.I_Per_Manager          = ent.I_Per_Manager;
		this.I_Per_Person_01        = ent.I_Per_Person_01;
		this.I_Per_Person_02        = ent.I_Per_Person_02;

		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;

		ok = (I_ID == ((TaMatMaterial)o).I_ID);
		if (!ok) return ok;


		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}

	public Integer reqID(){
		return this.I_ID;
	}

	public void doBuildDetails(boolean forced) throws Exception{
		if (!forced && O_Details!=null) return;
		this.O_Details 	= TaMatMaterialDetail.DAO.reqList(Restrictions.eq(TaMatMaterialDetail.ATT_I_MAT_MATERIAL_01, I_ID));	
	} 
	
	public void doBuildDetails(boolean forced, Session sess) throws Exception{
		if (!forced && O_Details!=null) return;
		this.O_Details 	= TaMatMaterialDetail.DAO.reqList(sess, Restrictions.eq(TaMatMaterialDetail.ATT_I_MAT_MATERIAL_01, I_ID));
	} 

//	public void doBuildPricesPublic(boolean forced) throws Exception{
//		if (!forced && O_Prices!=null) return;	
//
//		this.O_Prices 	= TaMatPrice.DAO.reqList(
//				Restrictions.eq(TaMatPrice.ATT_I_STATUS		, TaMatPrice.STAT_ACTIV), 
//				Restrictions.eq(TaMatPrice.ATT_I_ARTICLE_TYPE	, DefDBExt.ID_TA_MAT_MATERIAL), 
//				Restrictions.eq(TaMatPrice.ATT_I_ARTICLE_ID	, this.I_ID)
//				);		
//
//	}
	
	public void doBuildPrices(boolean forced) throws Exception{
		if (!forced && O_Prices_Out!=null) return;	

		this.O_Prices_Out 	= TaMatPrice.DAO.reqList(Restrictions.eq(TaMatPrice.ATT_I_MAT_MATERIAL, this.I_ID));		
	}
	
	public void doBuildPrices(boolean forced, Session sess) throws Exception{
		if (!forced && O_Prices_Out!=null) return;	

		this.O_Prices_Out 	= TaMatPrice.DAO.reqList(sess,
				Restrictions.eq(TaMatPrice.ATT_I_MAT_MATERIAL, this.I_ID)
				);		

	}
	
	public void doBuildProducer (boolean forced) throws Exception {
		if (!forced && O_Producer!=null) return;	
		if (this.I_Per_Person_01 == null) {
			O_Producer = TaPerPerson.DAO.reqEntityByRef(1);
		} else {
			O_Producer = TaPerPerson.DAO.reqEntityByRef(this.I_Per_Person_01);
		}
	}
	
	//---khong chung session duoc vi nam trong package khac
	public void doBuildDocuments(boolean forced) throws Exception {
		if (this.O_Documents != null && !forced) return;
		this.O_Documents = TaTpyDocument.reqTpyDocuments(ENT_TYP, I_ID, null, null);
		
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
	
	private void doAddPrice (TaMatPrice p) {
		if (this.O_Prices_Out ==null) {
			this.O_Prices_Out = new ArrayList<TaMatPrice>();
		}
		O_Prices_Out.add(p);
	}
	
	private void doAddDetail (TaMatMaterialDetail p) {
		if (this.O_Details ==null) {
			this.O_Details = new ArrayList<TaMatMaterialDetail>();
		}
		O_Details.add(p);
	}
	
	public static void doBuildPrices(List<TaMatMaterial> mats) throws Exception{
		Set<Integer> ids = ToolSet.reqSetInt(mats, TaMatMaterial.ATT_I_ID);
		Hashtable<Integer,EntityAbstract> map = ToolDBEntity.reqTabKeyInt(mats, TaMatMaterial.ATT_I_ID);
		
		List<TaMatPrice> prices 	= TaMatPrice.DAO.reqList_In(TaMatPrice.ATT_I_MAT_MATERIAL, ids, Restrictions.eq(TaMatPrice.ATT_I_STATUS, TaMatPrice.STAT_ACTIV));	
		
		if (prices != null && prices.size() >0 ) {
			Collections.sort(prices, new Comparator<TaMatPrice>() {
				public int compare(TaMatPrice o1, TaMatPrice o2) {
					Integer ord1 = (Integer) o1.req(TaMatPrice.ATT_I_PRIORITY);
					Integer ord2 = (Integer) o2.req(TaMatPrice.ATT_I_PRIORITY);
					if (ord1 == null)	ord1 =  Integer.MAX_VALUE;
					if (ord2 == null)	ord2 =  Integer.MAX_VALUE;
					return ord1 - ord2;
				}
			});
		}
		
		Date now = new Date();
		for (TaMatPrice p: prices) {
			Integer 		matId 	= p.reqInt(p, TaMatPrice.ATT_I_MAT_MATERIAL);
			TaMatMaterial 	mat 	= (TaMatMaterial) map.get(matId);
			
			Date dtBegin = (Date) p.req(TaMatPrice.ATT_D_DATE_03); 
			Date dtEnd 	 = (Date) p.req(TaMatPrice.ATT_D_DATE_04); 
			
			if (dtBegin==null) 	dtBegin = now;
			if (dtEnd==null) 	dtEnd 	= now;
			if (dtBegin.compareTo(now)<=0 && dtEnd.compareTo(now)>=0)
				mat.doAddPrice(p);
		}
	}
	
	public static void doBuildDetails(List<TaMatMaterial> mats) throws Exception{
		Set<Integer> ids = ToolSet.reqSetInt(mats, TaMatMaterial.ATT_I_ID);
		Hashtable<Integer,EntityAbstract> map = ToolDBEntity.reqTabKeyInt(mats, TaMatMaterial.ATT_I_ID);

		List<TaMatMaterialDetail> dets 	= TaMatMaterialDetail.DAO.reqList_In(TaMatMaterialDetail.ATT_I_MAT_MATERIAL_01, ids);	

		for (TaMatMaterialDetail d: dets) {
			Integer matId = d.reqInt(d, TaMatMaterialDetail.ATT_I_MAT_MATERIAL_01);
			TaMatMaterial mat = (TaMatMaterial) map.get(matId);
			mat.doAddDetail(d);
		}

		//----O_Child------
		Set<Integer> 		setUids = ToolSet.reqSetInt(dets, TaMatMaterialDetail.ATT_I_MAT_MATERIAL_02);
		List<TaMatMaterial> lstChild = TaMatMaterial.DAO.reqList_In(TaMatMaterial.ATT_I_ID, setUids);
		map = ToolDBEntity.reqTabKeyInt(lstChild, TaMatMaterial.ATT_I_ID);
		for(TaMatMaterialDetail d: dets){
			Integer idChild = (Integer) d.req(TaMatMaterialDetail.ATT_I_MAT_MATERIAL_02);
			d.reqSet(TaMatMaterialDetail.ATT_O_CHILD, map.get(idChild));
		}
	}
	
	public void doBuildCats(boolean forced) throws Exception {
		if (!forced && O_Cats!=null) return;
		Integer matId	= this.reqID();
		
		this.O_Cats = TaTpyCategoryEntity.DAO.reqList(
				Restrictions.eq(TaTpyCategoryEntity.ATT_I_ENTITY_TYPE	, DefDBExt.ID_TA_MAT_MATERIAL), 
				Restrictions.eq(TaTpyCategoryEntity.ATT_I_ENTITY_ID		, matId)
		);
	}
	
	public static void doBuildAvatarForList(List<TaMatMaterial>  list) throws Exception {
		if (list==null || list.size()==0) return;
		TaTpyDocument.reqBuildAvatar(list, DefDBExt.ID_TA_MAT_MATERIAL, ATT_O_AVATAR);
	}

	public void doBuildAvatarPath()  throws Exception{
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
}
