package com.hnv.db.mat;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.def.DefDBExt;		

/**
 * TaTpyInformation by H&V SAS
 */
@Entity
@Table(name = DefDBExt.TA_MAT_MATERIAL_DATA )
public class TaMatMaterialData extends EntityAbstract<TaMatMaterialData> {
	public static int STAT_WAITING          = 0;
	public static int STAT_VALIDATED        = 1; //--seen
	public static int STAT_DELETED          = 2; //--wait for deleting

	public  static int TYPE_01_BASE  		= 100;
	public  static int TYPE_01_REPORT   	= 1000; //--img


	private static final long serialVersionUID = 1L;

	public static final int TYP_01_HISTO	= 100;

	private static final int  ENT_TYP			= DefDBExt.ID_TA_MAT_MATERIAL_DATA;

	//---------------------------List of Column from DB-----------------------------
	public static final String COL_I_ID           = "I_ID";

	public static final String COL_I_MAT_MATERIAL = "I_Mat_Material";

	public static final String	COL_I_STATUS_01   =	"I_Status_01";
	public static final String	COL_I_STATUS_02   =	"I_Status_02";
	public static final String	COL_I_STATUS_03   =	"I_Status_03";

	public static final String	COL_I_TYPE_01      =	"I_Type_01";
	public static final String	COL_I_TYPE_02      =	"I_Type_02";
	public static final String	COL_I_TYPE_03      =	"I_Type_03";

	public static final String COL_F_VAL_01       = "F_Val_01";
	public static final String COL_F_VAL_02       = "F_Val_02";
	public static final String COL_F_VAL_03       = "F_Val_03";
	public static final String COL_F_VAL_04       = "F_Val_04";
	public static final String COL_F_VAL_05       = "F_Val_05";
	public static final String COL_F_VAL_06       = "F_Val_06";
	public static final String COL_F_VAL_07       = "F_Val_07";
	public static final String COL_F_VAL_08       = "F_Val_08";
	public static final String COL_F_VAL_09       = "F_Val_09";
	public static final String COL_F_VAL_10       = "F_Val_10";
	public static final String COL_F_VAL_11       = "F_Val_11";
	public static final String COL_F_VAL_12       = "F_Val_12";
	public static final String COL_F_VAL_13       = "F_Val_13";
	public static final String COL_F_VAL_14       = "F_Val_14";
	public static final String COL_F_VAL_15       = "F_Val_15";
	public static final String COL_F_VAL_16       = "F_Val_16";
	public static final String COL_F_VAL_17       = "F_Val_17";
	public static final String COL_F_VAL_18       = "F_Val_18";
	public static final String COL_F_VAL_19       = "F_Val_19";
	public static final String COL_F_VAL_20       = "F_Val_20";

	public static final String COL_T_INFO_01      = "T_Info_01";
	public static final String COL_T_INFO_02      = "T_Info_02";
	public static final String COL_T_INFO_03      = "T_Info_03";

	public static final String	COL_D_DATE_01                         =	"D_Date_01";
	public static final String	COL_D_DATE_02                         =	"D_Date_02";
	public static final String	COL_D_DATE_03                         =	"D_Date_03";
	public static final String	COL_D_DATE_04                         =	"D_Date_04";

	public static final String COL_I_AUT_USER_01   = "I_Aut_User_01";
	public static final String COL_I_AUT_USER_02   = "I_Aut_User_02";



	public static final String ATT_I_ID           = "I_ID";

	public static final String ATT_I_MAT_MATERIAL = "I_Mat_Material";

	public static final String	ATT_I_STATUS_01   =	"I_Status_01";
	public static final String	ATT_I_STATUS_02   =	"I_Status_02";
	public static final String	ATT_I_STATUS_03   =	"I_Status_03";

	public static final String	ATT_I_TYPE_01      =	"I_Type_01";
	public static final String	ATT_I_TYPE_02      =	"I_Type_02";
	public static final String	ATT_I_TYPE_03      =	"I_Type_03";

	public static final String ATT_F_VAL_01       = "F_Val_01";
	public static final String ATT_F_VAL_02       = "F_Val_02";
	public static final String ATT_F_VAL_03       = "F_Val_03";
	public static final String ATT_F_VAL_04       = "F_Val_04";
	public static final String ATT_F_VAL_05       = "F_Val_05";
	public static final String ATT_F_VAL_06       = "F_Val_06";
	public static final String ATT_F_VAL_07       = "F_Val_07";
	public static final String ATT_F_VAL_08       = "F_Val_08";
	public static final String ATT_F_VAL_09       = "F_Val_09";
	public static final String ATT_F_VAL_10       = "F_Val_10";
	public static final String ATT_F_VAL_11       = "F_Val_11";
	public static final String ATT_F_VAL_12       = "F_Val_12";
	public static final String ATT_F_VAL_13       = "F_Val_13";
	public static final String ATT_F_VAL_14       = "F_Val_14";
	public static final String ATT_F_VAL_15       = "F_Val_15";
	public static final String ATT_F_VAL_16       = "F_Val_16";
	public static final String ATT_F_VAL_17       = "F_Val_17";
	public static final String ATT_F_VAL_18       = "F_Val_18";
	public static final String ATT_F_VAL_19       = "F_Val_19";
	public static final String ATT_F_VAL_20       = "F_Val_20";

	public static final String ATT_T_INFO_01      = "T_Info_01";
	public static final String ATT_T_INFO_02      = "T_Info_02";
	public static final String ATT_T_INFO_03      = "T_Info_03";

	public static final String	ATT_D_DATE_01                         =	"D_Date_01";
	public static final String	ATT_D_DATE_02                         =	"D_Date_02";
	public static final String	ATT_D_DATE_03                         =	"D_Date_03";
	public static final String	ATT_D_DATE_04                         =	"D_Date_04";

	public static final String ATT_I_AUT_USER_01   = "I_Aut_User_01";
	public static final String ATT_I_AUT_USER_02   = "I_Aut_User_02";
	
	
	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 

	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaMatMaterialData> 	DAO;
	static{
		DAO = new EntityDAO<TaMatMaterialData>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaMatMaterialData.class,RIGHTS);

	}

	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;

	@Column(name=COL_I_MAT_MATERIAL, nullable = true)
	private	Integer         I_Mat_Material;

	@Column(name=COL_I_STATUS_01, nullable = true)
	private	Integer         I_Status_01;
	@Column(name=COL_I_STATUS_02, nullable = true)
	private	Integer         I_Status_02;
	@Column(name=COL_I_STATUS_03, nullable = true)
	private	Integer         I_Status_03;
	

	@Column(name=COL_I_TYPE_01, nullable = true)
	private	Integer         I_Type_01;
	@Column(name=COL_I_TYPE_02, nullable = true)
	private	Integer         I_Type_02;
	@Column(name=COL_I_TYPE_03, nullable = true)
	private	Integer         I_Type_03;
	
	

	@Column(name=COL_T_INFO_01, nullable = true)
	private	String          T_Info_01;
	@Column(name=COL_T_INFO_02, nullable = true)
	private	String          T_Info_02;
	@Column(name=COL_T_INFO_03, nullable = true)
	private	String          T_Info_03;

	
	@Column(name=COL_F_VAL_01, nullable = true)
	private	Double          F_Val_01;
	@Column(name=COL_F_VAL_02, nullable = true)
	private	Double          F_Val_02;
	@Column(name=COL_F_VAL_03, nullable = true)
	private	Double          F_Val_03;
	@Column(name=COL_F_VAL_04, nullable = true)
	private	Double          F_Val_04;
	@Column(name=COL_F_VAL_05, nullable = true)
	private	Double          F_Val_05;
	@Column(name=COL_F_VAL_06, nullable = true)
	private	Double          F_Val_06;
	@Column(name=COL_F_VAL_07, nullable = true)
	private	Double          F_Val_07;
	@Column(name=COL_F_VAL_08, nullable = true)
	private	Double          F_Val_08;
	@Column(name=COL_F_VAL_09, nullable = true)
	private	Double          F_Val_09;
	@Column(name=COL_F_VAL_10, nullable = true)
	private	Double          F_Val_10;
	@Column(name=COL_F_VAL_11, nullable = true)
	private	Double          F_Val_11;
	@Column(name=COL_F_VAL_12, nullable = true)
	private	Double          F_Val_12;
	@Column(name=COL_F_VAL_13, nullable = true)
	private	Double          F_Val_13;
	@Column(name=COL_F_VAL_14, nullable = true)
	private	Double          F_Val_14;
	@Column(name=COL_F_VAL_15, nullable = true)
	private	Double          F_Val_15;
	@Column(name=COL_F_VAL_16, nullable = true)
	private	Double          F_Val_16;
	@Column(name=COL_F_VAL_17, nullable = true)
	private	Double          F_Val_17;
	@Column(name=COL_F_VAL_18, nullable = true)
	private	Double          F_Val_18;
	@Column(name=COL_F_VAL_19, nullable = true)
	private	Double          F_Val_19;
	@Column(name=COL_F_VAL_20, nullable = true)
	private	Double          F_Val_20;
	
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



	//-----------------------Transient Variables-------------------------
	//---------------------Constructeurs-----------------------
	public TaMatMaterialData(){}


	public TaMatMaterialData(int matId, Double v01, Double v02, Double v03, Double v04, Double v05, Double v06, Double v07, Double v08, Double v09, Double v10){
		this.I_Mat_Material = matId;
		this.F_Val_01		= v01;
		this.F_Val_02		= v02;
		this.F_Val_03		= v03;
		this.F_Val_04		= v04;
		this.F_Val_05		= v05;
		this.F_Val_06		= v06;
		this.F_Val_07		= v07;
		this.F_Val_08		= v08;
		this.F_Val_09		= v09;
		this.F_Val_10		= v10;
		
		this.D_Date_01		= new Date();
	}
	
	public TaMatMaterialData(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		doInitDBFlag();
	}


	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaMatMaterialData ent) {
		if (ent == this) return;


		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;

		ok = (I_ID == ((TaMatMaterialData)o).I_ID);
		if (!ok) return ok;


		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}

}
