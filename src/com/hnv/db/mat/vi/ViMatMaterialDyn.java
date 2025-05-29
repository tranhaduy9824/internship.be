package com.hnv.db.mat.vi;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
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
import com.hnv.common.tool.ToolDBEntity;
import com.hnv.common.tool.ToolSet;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.aut.vi.ViAutUserDyn;
import com.hnv.db.mat.TaMatMaterial;
import com.hnv.db.mat.TaMatMaterialDetail;
import com.hnv.db.mat.TaMatPrice;
import com.hnv.db.mat.TaMatUnit;
import com.hnv.db.per.TaPerPerson;
import com.hnv.db.tpy.TaTpyCategoryEntity;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.def.DefDBExt;

/**
 * TaMatMaterial by H&V SAS
 */
@Entity
@Table(name = DefDBExt.TA_MAT_MATERIAL )
public class ViMatMaterialDyn extends EntityAbstract<ViMatMaterialDyn> {
	public static int	TYPE_02_SINGLE 		= 1;
	public static int	TYPE_02_BOM 		= 2; //bill of material 
	public static int	TYPE_02_WHOLESALE 	= 3;

	private static final long serialVersionUID = 1L;

	public static final int	STAT_NEW 			= 0;
	public static final int	STAT_ACTIVE 		= 1; 
	public static final int	STAT_REVIEW 		= 5; 
	public static final int	STAT_DELETED 		= 10;

	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_T_CODE_01                         =	"T_Code_01";
	public static final String	COL_T_CODE_02                         =	"T_Code_02";
	public static final String	COL_T_CODE_03                         =	"T_Code_03";
	public static final String	COL_T_CODE_04                         =	"T_Code_04";
	public static final String	COL_T_CODE_05                         =	"T_Code_05";
	public static final String	COL_T_NAME_01                         =	"T_Name_01";
	public static final String	COL_T_NAME_02                         =	"T_Name_02";
	
	public static final String	COL_I_STATUS                          =	"I_Status_01";

	public static final String	COL_I_TYPE_01                         =	"I_Type_01";
	public static final String	COL_I_TYPE_02                         =	"I_Type_02";
	public static final String	COL_I_TYPE_03                         =	"I_Type_03";
	
	public static final String	COL_I_PER_PERSON_01                   =	"I_Per_Producer";
	public static final String	COL_I_PER_MANAGER                     =	"I_Per_Manager";
	
	public static final String	COL_I_CATEGORY                     	  =	"I_Category";
	public static final String	COL_T_CATEGORY                     	  =	"T_Category";
		
	public static final String	COL_I_UNIT		                      =	"I_Unit";
	public static final String	COL_T_UNIT		                      =	"T_Unit";
	public static final String	COL_F_UNIT_RATIO                      =	"F_Unit_Ratio";
	
	public static final String	COL_I_PRICE		                  	  =	"I_Price";
	public static final String	COL_F_PRICE_01		                  =	"F_Price01";
	public static final String	COL_F_PRICE_02		                  =	"F_Price02";
	public static final String	COL_F_DISCOUNT		                  =	"F_Discount";
	public static final String	COL_T_CURRENCY		                  =	"T_Currency";
	
	public static final String	COL_T_MAN_NAME	                      =	"T_Man_Name";
	public static final String	COL_T_MAN_INFO07	                  =	"T_Man_Info07";
	public static final String	COL_T_MAN_INFO09	                  =	"T_Man_Info09";
	public static final String	COL_T_PROD_NAME		                  =	"T_Prod_Name";
	
	public static final String	COL_D_DATE_01					  	  = "D_Date_01";
	public static final String	COL_D_DATE_02					      = "D_Date_02";

	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_T_CODE_01                         =	"T_Code_01";
	public static final String	ATT_T_CODE_02                         =	"T_Code_02";
	public static final String	ATT_T_CODE_03                         =	"T_Code_03";
	public static final String	ATT_T_CODE_04                         =	"T_Code_04";
	public static final String	ATT_T_CODE_05                         =	"T_Code_05";
	public static final String	ATT_T_NAME_01                         =	"T_Name_01";
	public static final String	ATT_T_NAME_02                         =	"T_Name_02";
	
	public static final String	ATT_I_STATUS                          =	"I_Status_01";

	public static final String ATT_I_TYPE_01 						  = "I_Type_01";
	public static final String ATT_I_TYPE_02 						  = "I_Type_02";
	public static final String ATT_I_TYPE_03 						  = "I_Type_03";
	
	public static final String	ATT_I_CATEGORY                     	  =	"I_Category";
	public static final String	ATT_T_CATEGORY                     	  =	"T_Category";
	
	public static final String	ATT_I_PER_PRODUCER                    =	"I_Per_Producer";
	public static final String	ATT_I_PER_MANAGER                    =	"I_Per_Manager";
	
	public static final String	ATT_I_UNIT		                      =	"I_Unit";
	public static final String	ATT_T_UNIT                            =	"T_Unit";
	public static final String	ATT_F_UNIT_RATIO                      =	"F_Unit_Ratio";
	
	public static final String	ATT_I_PRICE		                  	  =	"I_Price";
	public static final String	ATT_F_PRICE_01		                  =	"F_Price01";
	public static final String	ATT_F_PRICE_02		                  =	"F_Price02";
	public static final String	ATT_F_DISCOUNT		                  =	"F_Discount";
	public static final String	ATT_T_CURRENCY		                  =	"T_Currency";
	
	public static final String	ATT_T_MAN_NAME	                      =	"T_Man_Name";
	public static final String	ATT_T_PROD_NAME		                  =	"T_Prod_Name";
	
	public static final String	ATT_D_DATE_01					  	  = "D_Date_01";
	public static final String	ATT_D_DATE_02					      = "D_Date_02";
	
	public static final String	ATT_O_UNITS                     	  =	"O_Units";
	public static final String	ATT_O_PRICES                          =	"O_Prices";
	public static final String	ATT_O_BUY_PRICES                      =	"O_Buy_Prices";
	public static final String	ATT_O_PROPERTIES                      =	"O_Properties";
	public static final String	ATT_O_TPY_INFO                     	  =	"O_Tpy_Info";
	public static final String	ATT_O_MANAGER                  	      =	"O_Manager";	
	public static final String	ATT_O_PRODUCER                  	  =	"O_Producer";
	
	public static final String	ATT_O_AVATAR                  	      =	"O_Avatar";
	public static final String	ATT_O_MAN_AVATAR                  	  =	"O_Man_Avatar";
	public static final String	ATT_O_PROD_AVATAR                  	  =	"O_Prod_Avatar";

	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, false, false, false, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<ViMatMaterialDyn> 	DAO;
	static{
		DAO = new EntityDAO<ViMatMaterialDyn>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN) , ViMatMaterialDyn.class, RIGHTS,  HISTORY, DefDBExt.TA_MAT_MATERIAL, DefDBExt.ID_TA_MAT_MATERIAL);
	}

	//-----------------------Class Attributs-------------------------
	@Id
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;

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

	@Column(name=COL_T_NAME_01, nullable = true)
	private	String          T_Name_01;

	@Column(name=COL_T_NAME_02, nullable = true)
	private	String          T_Name_02;

	@Column(name=COL_I_TYPE_01, nullable = true)
	private	Integer         I_Type_01;

	@Column(name=COL_I_TYPE_02, nullable = true)
	private	Integer         I_Type_02;

	@Column(name=COL_I_TYPE_03, nullable = true)
	private	Integer         I_Type_03;

	@Column(name=COL_I_STATUS, nullable = true)
	private	Integer         I_Status_01;

	@Column(name=COL_I_PER_MANAGER, nullable = true)
	private	Integer         I_Per_Manager;

	@Transient
	private TaTpyDocument O_Avatar;
		
	
	//---------------------Constructeurs-----------------------
	private ViMatMaterialDyn(){}
 
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(ViMatMaterialDyn ent) {
	}

	@Override
	public int hashCode() {
		return this.I_ID;
	}
	
	public static Number reqCountMaterialFilter(Criterion cri) throws Exception {
		return ViMatMaterialDyn.DAO.reqCount(cri);
	}
	
	public static void doBuildAvatarForList(List<ViMatMaterialDyn>  list) throws Exception {
		if (list==null || list.size()==0) return;
		TaTpyDocument.reqBuildAvatar(list, DefDBExt.ID_TA_MAT_MATERIAL, ATT_O_AVATAR);
	}
}


