package com.hnv.db.mat;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
import org.hibernate.criterion.Restrictions;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.def.DefDBExt;

/**
* TaMatMaterialDetail by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_MAT_MATERIAL_DETAIL )
public class TaMatMaterialDetail extends EntityAbstract<TaMatMaterialDetail> {

	private static final long serialVersionUID = 1L;

	public static final int	STAT_NEW 			= 0;
	public static final int	STAT_ACTIVE 		= 1; 
	public static final int	STAT_REVIEW 		= 5; 
	public static final int	STAT_DELETED 		= 10;
	 
	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_I_MAT_MATERIAL_01                 =	"I_Mat_Material_01";//parent of BOM
	public static final String	COL_I_MAT_MATERIAL_02                 =	"I_Mat_Material_02";//child of BOM
	public static final String	COL_T_INFO_01                         =	"T_Info_01";
	public static final String	COL_T_INFO_02                         =	"T_Info_02";
	public static final String	COL_T_INFO_03                         =	"T_Info_03";
	public static final String	COL_T_INFO_04                         =	"T_Info_04";
	public static final String	COL_T_INFO_05                         =	"T_Info_05";
	public static final String	COL_I_TYPE_01                         =	"I_Type_01";
	public static final String	COL_I_TYPE_02                         =	"I_Type_02";
	public static final String	COL_I_STATUS_01                       =	"I_Status_01";
	public static final String	COL_I_STATUS_02                       =	"I_Status_02";
	public static final String	COL_I_VAL_01                          =	"I_Val_01"; //prioritt
	public static final String	COL_I_VAL_02                          =	"I_Val_02";
	public static final String	COL_F_VAL_01                          =	"F_Val_01"; //quant
	public static final String	COL_F_VAL_02                          =	"F_Val_02"; //unit ratio
	public static final String	COL_F_VAL_03                          =	"F_Val_03"; //unit label
	public static final String	COL_F_VAL_04                          =	"F_Val_04";
	public static final String	COL_F_VAL_05                          =	"F_Val_05";

	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_I_MAT_MATERIAL_01                 =	"I_Mat_Material_01";
	public static final String	ATT_I_MAT_MATERIAL_02                 =	"I_Mat_Material_02";
	public static final String	ATT_T_INFO_01                         =	"T_Info_01";
	public static final String	ATT_T_INFO_02                         =	"T_Info_02";
	public static final String	ATT_T_INFO_03                         =	"T_Info_03";
	public static final String	ATT_T_INFO_04                         =	"T_Info_04";
	public static final String	ATT_T_INFO_05                         =	"T_Info_05";
	public static final String	ATT_I_TYPE_01                         =	"I_Type_01";
	public static final String	ATT_I_TYPE_02                         =	"I_Type_02";
	public static final String	ATT_I_STATUS_01                       =	"I_Status_01";
	public static final String	ATT_I_STATUS_02                       =	"I_Status_02";
	public static final String	ATT_I_VAL_01                          =	"I_Val_01";
	public static final String	ATT_I_VAL_02                          =	"I_Val_02";
	public static final String	ATT_F_VAL_01                          =	"F_Val_01";
	public static final String	ATT_F_VAL_02                          =	"F_Val_02";
	public static final String	ATT_F_VAL_03                          =	"F_Val_03";
	public static final String	ATT_F_VAL_04                          =	"F_Val_04";
	public static final String	ATT_F_VAL_05                          =	"F_Val_05";


	public static final String	ATT_O_CHILD                           =	"O_Child";
	public static final String	ATT_O_PRICE                           =	"O_Price";
	public static final String	ATT_F_PRICE                           =	"F_Price";
	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaMatMaterialDetail> 	DAO;
	static{
		DAO = new EntityDAO<TaMatMaterialDetail>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaMatMaterialDetail.class,RIGHTS, HISTORY, DefDBExt.TA_MAT_MATERIAL_DETAIL, DefDBExt.ID_TA_MAT_MATERIAL_DETAIL);

	}

	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_I_MAT_MATERIAL_01, nullable = true)
	private	Integer         I_Mat_Material_01;

	@Column(name=COL_I_MAT_MATERIAL_02, nullable = true)
	private	Integer         I_Mat_Material_02;

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
    
	@Column(name=COL_I_TYPE_01, nullable = true)
	private	Integer         I_Type_01;
    
	@Column(name=COL_I_TYPE_02, nullable = true)
	private	Integer         I_Type_02;
    
	@Column(name=COL_I_STATUS_01, nullable = true)
	private	Integer         I_Status_01;
  
	@Column(name=COL_I_STATUS_02, nullable = true)
	private	Integer         I_Status_02;
  
	@Column(name=COL_I_VAL_01, nullable = true)
	private	Integer         I_Val_01;
     
	@Column(name=COL_I_VAL_02, nullable = true)
	private	Integer         I_Val_02;
     
	@Column(name=COL_F_VAL_01, nullable = true)
	private	Double          F_Val_01;
     
	@Column(name=COL_F_VAL_02, nullable = true)
	private	Double          F_Val_02;
     
	@Column(name=COL_F_VAL_03, nullable = true)
	private	Double          F_Val_03;
     
	@Column(name=COL_F_VAL_04, nullable = true)
	private	Double          F_Val_04;
     
	@Column(name=COL_F_VAL_05, nullable = true)
	private	String          F_Val_05;
    

	//-----------------------Transient Variables-------------------------
	//-----------------------Transient Variables-------------------------
	@Transient
	private	TaMatMaterial   		O_Child;
	
	@Transient
	private	TaMatMaterial   		O_Parent;

//	@Transient
//	private	TaMatPrice    			O_Price;
	
	@Transient
	private	List<TaMatMaterialDetail>   O_Children;


	//---------------------Constructeurs-----------------------
	private TaMatMaterialDetail(){}

	public TaMatMaterialDetail(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		// doInitDBFlag();
	}
	
	
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaMatMaterialDetail ent) {
		if (ent == this) return;
		this.I_Mat_Material_01      = ent.I_Mat_Material_01;
		this.I_Mat_Material_02      = ent.I_Mat_Material_02;
		this.T_Info_01              = ent.T_Info_01;
		this.T_Info_02              = ent.T_Info_02;
		this.T_Info_03              = ent.T_Info_03;
		this.T_Info_04              = ent.T_Info_04;
		this.T_Info_05              = ent.T_Info_05;
		this.I_Type_01              = ent.I_Type_01;
		this.I_Type_02              = ent.I_Type_02;
		this.I_Status_01            = ent.I_Status_01;
		this.I_Status_02            = ent.I_Status_02;
		this.I_Val_01               = ent.I_Val_01;
		this.I_Val_02               = ent.I_Val_02;
		this.F_Val_01               = ent.F_Val_01;
		this.F_Val_02               = ent.F_Val_02;
		this.F_Val_03               = ent.F_Val_03;
		this.F_Val_04               = ent.F_Val_04;
		this.F_Val_05               = ent.F_Val_05;



		//---------------------Merge Transient Variables if exist-----------------------
	}

    

	//---------------------Constructeurs-----------------------

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((TaMatMaterialDetail)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}

	public void dobuildMat() throws Exception{
		int idChild 	= (int) this.req(TaMatMaterialDetail.ATT_I_MAT_MATERIAL_02);
		this.O_Child	= TaMatMaterial.DAO.reqEntityByRef(idChild);
	}
	

	
	public static List<TaMatMaterialDetail> reqBuildBOMStruct(Session sess, Integer matID) throws Exception{
		List<TaMatMaterialDetail> bom = TaMatMaterialDetail.DAO.reqList(sess, Restrictions.eq(TaMatMaterialDetail.ATT_I_MAT_MATERIAL_01, matID));
		
		for (TaMatMaterialDetail det: bom) {
			List<TaMatMaterial> mats = TaMatMaterial.DAO.reqList(sess, 
					Restrictions.and(	Restrictions.eq(TaMatMaterial.ATT_I_ID		, det.req(TaMatMaterialDetail.ATT_I_MAT_MATERIAL_02)), 
										Restrictions.eq(TaMatMaterial.ATT_I_TYPE_01	, TaMatMaterial.TYPE_01_MAT), 
										Restrictions.eq(TaMatMaterial.ATT_I_TYPE_02	, TaMatMaterial.TYPE_02_BOM)));
			if (mats!=null && mats.size()>0) {
				for (TaMatMaterial mat : mats) {
					List<TaMatMaterialDetail> dets = reqBuildBOMStruct (sess, mat.reqID());
					if (det.O_Children==null) 
						det.O_Children = dets;
					else 
						det.O_Children.addAll( dets);
				}
			} 
		}
		return bom;
	}
	
	public static void doBuildListBase(Session sess, Integer matID, Double ratio, List<TaMatMaterialDetail> listBase, int deep, int deepMax) throws Exception{
		if (deep>deepMax) return;
		
		List<TaMatMaterialDetail> bom = TaMatMaterialDetail.DAO.reqList(sess, Restrictions.eq(TaMatMaterialDetail.ATT_I_MAT_MATERIAL_01, matID));
		
		for (TaMatMaterialDetail det: bom) {
			Integer mId	= det.reqInt	(det, TaMatMaterialDetail.ATT_I_MAT_MATERIAL_02	);
//			Double 	q 	= det.reqDouble	(det, TaMatMaterialDetail.ATT_F_QUANTITY		);
//			Double	r 	= det.reqDouble	(det, TaMatMaterialDetail.ATT_F_UNIT_RATIO		);
			
//			q = q*ratio;
//			det.reqSet(TaMatMaterialDetail.ATT_F_QUANTITY, q);
			
			List<TaMatMaterial> mats = TaMatMaterial.DAO.reqList(sess, 
					Restrictions.and(	Restrictions.eq(TaMatMaterial.ATT_I_ID		, mId), 
										Restrictions.eq(TaMatMaterial.ATT_I_TYPE_01	, TaMatMaterial.TYPE_01_MAT), 
										Restrictions.eq(TaMatMaterial.ATT_I_TYPE_02	, TaMatMaterial.TYPE_02_BOM)));
				
			if (mats!=null && mats.size()>0) {
//				doBuildListBase (sess, mId, q*r, listBase, deep+1, deepMax);					
			}else 
				listBase.add(det); 
		}
		
		
	}

}
