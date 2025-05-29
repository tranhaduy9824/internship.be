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
import javax.persistence.Transient;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.def.DefDBExt;		

/**
* TaMatStockIo by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_MAT_STOCK_MONTH )
public class TaMatStockMonth extends EntityAbstract<TaMatStockMonth> {
	public static final int		STAT_KO 	= 0;
	public static final int		STAT_OK 	= 1;
	private static final long serialVersionUID = 1L;

	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              	=	"I_ID";
	public static final String	COL_I_MAT_MATERIAL                      =	"I_Mat_Material";
	public static final String	COL_I_MAT_WAREHOUSE                     =	"I_Mat_Warehouse";
	public static final String	COL_I_PER_MANAGER                       =	"I_Per_Manager";
	public static final String	COL_I_AUT_USER                       	=	"I_Aut_User";
	
	public static final String	COL_D_DATE_01                        	=	"D_Date_01";
	public static final String	COL_D_DATE_02                        	=	"D_Date_02";
	
	public static final String	COL_F_VAL_00                        	=	"F_Val_00";//--Ratio
	public static final String	COL_F_VAL_01                        	=	"F_Val_01";//--Q base input a time range 
	public static final String	COL_F_VAL_02                        	=	"F_Val_02";//--Q base output a time range 
	public static final String	COL_F_VAL_03                        	=	"F_Val_03";
	public static final String	COL_F_VAL_04                        	=	"F_Val_04";//--sum RQ base all stock
	public static final String	COL_F_VAL_05                        	=	"F_Val_05";//--RQ real from user check;

	public static final String	COL_I_TYPE                            	=	"I_Type";
	
	public static final String	COL_T_INFO_01                         	=	"T_Info_01";
	public static final String	COL_T_INFO_02                         	=	"T_Info_02";
	public static final String	COL_T_INFO_03                         	=	"T_Info_03";
	public static final String	COL_T_INFO_04                         	=	"T_Info_04";
	public static final String	COL_T_INFO_05                         	=	"T_Info_05";
	
/*
--  `F_Val_01` : 'QBaseIn tổng của tháng trước(tất cả Q nhập vào trong tháng trước)',
--  `F_Val_02` : 'RQBase tổng của tháng trước',
--  `F_Val_03` : 'QBaseOut tổng của tháng trước',

--  `F_Val_00` :  Ratio của đv đang check nếu làm kiểm ke tồn kho
--  `F_Val_04` :  Nếu kiểm kê thì giá trị hiện đang có tại cửa hàng

--  `F_Val_05` : 
 */

	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_I_PER_MANAGER                     =	"I_Per_Manager";
	public static final String	ATT_I_MAT_MATERIAL                    =	"I_Mat_Material";
	public static final String	ATT_I_MAT_WAREHOUSE                   =	"I_Mat_Warehouse";
	public static final String	ATT_F_VAL_00                          =	"F_Val_00";
	public static final String	ATT_F_VAL_01                          =	"F_Val_01";
	public static final String	ATT_F_VAL_02                          =	"F_Val_02";
	public static final String	ATT_F_VAL_03                          =	"F_Val_03";
	public static final String	ATT_F_VAL_04                          =	"F_Val_04";
	public static final String	ATT_F_VAL_05                          =	"F_Val_05";
	public static final String	ATT_D_DATE_01                         =	"D_Date_01";
	public static final String	ATT_D_DATE_02                         =	"D_Date_02";
	public static final String	ATT_I_TYPE                            =	"I_Type";
	public static final String	ATT_I_AUT_USER                        =	"I_Aut_User";
	public static final String	ATT_T_INFO_01                         =	"T_Info_01";
	public static final String	ATT_T_INFO_02                         =	"T_Info_02";
	public static final String	ATT_T_INFO_03                         =	"T_Info_03";
	public static final String	ATT_T_INFO_04                         =	"T_Info_04";
	public static final String	ATT_T_INFO_05                         =	"T_Info_05";



	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaMatStockMonth> 	DAO;
	static{
		DAO = new EntityDAO<TaMatStockMonth>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaMatStockMonth.class,RIGHTS, HISTORY, DefDBExt.TA_MAT_STOCK_MONTH, DefDBExt.ID_TA_MAT_STOCK_MONTH);

	}
	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_I_PER_MANAGER, nullable = true)
	private	Integer         I_Per_Manager;

	@Column(name=COL_I_MAT_MATERIAL, nullable = true)
	private	Integer         I_Mat_Material;

	@Column(name=COL_I_MAT_WAREHOUSE, nullable = true)
	private	Integer         I_Mat_Warehouse;

	@Column(name=COL_F_VAL_00, nullable = true)
	private	Double          F_Val_00;
     
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
     
	@Column(name=COL_D_DATE_01, nullable = true)
	private	Date            D_Date_01;
    
	@Column(name=COL_D_DATE_02, nullable = true)
	private	Date            D_Date_02;
    
	@Column(name=COL_I_TYPE, nullable = true)
	private	Integer         I_Type;
       
	@Column(name=COL_I_AUT_USER, nullable = true)
	private	Integer         I_Aut_User;
   
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
    

    
	//-----------------------Transient Variables-------------------------
	@Transient
	private TaMatMaterial O_Mat_Material;

	@Transient
	private TaMatWarehouse O_Mat_Warehouse;
	
	//---------------------Constructeurs-----------------------
	//---------------------Constructeurs-----------------------
	public TaMatStockMonth(){}

	public TaMatStockMonth(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		// doInitDBFlag();
	}

	public TaMatStockMonth(TaMatStock stk) throws Exception {
		this.I_Mat_Material 	= (Integer) stk.req(TaMatStock.ATT_I_MAT_MATERIAL);
		this.I_Mat_Warehouse 	= (Integer) stk.req(TaMatStock.ATT_I_MAT_WAREHOUSE);
		this.I_Per_Manager 		= (Integer) stk.req(TaMatStock.ATT_I_PER_MANAGER);
		
		this.D_Date_01 			= (Date) stk.req(TaMatStock.ATT_D_DATE_01);
		this.D_Date_02 			= (Date) stk.req(TaMatStock.ATT_D_DATE_02);
		
		this.F_Val_04			= (Double) stk.req(TaMatStock.ATT_F_VAL_04); //RQ base from db data
	}
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaMatStockMonth ent) {
		if (ent == this) return;
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((TaMatStockMonth)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}

	public void doBuildMat() throws Exception{
		if (this.I_Mat_Material!=null)  
			this.O_Mat_Material = TaMatMaterial.DAO.reqEntityByValue(TaMatMaterial.ATT_I_ID, this.I_Mat_Material);
	}
	
	public void doBuildWarehouse() throws Exception{
		if (this.I_Mat_Warehouse!=null)  
			this.O_Mat_Warehouse = TaMatWarehouse.DAO.reqEntityByValue(TaMatWarehouse.ATT_I_ID, this.I_Mat_Warehouse);
	}
	
	
	public String reqKey() {
		return this.I_Mat_Material + "_" + this.I_Mat_Warehouse;
	}
}
