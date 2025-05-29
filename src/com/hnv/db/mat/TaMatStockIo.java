package com.hnv.db.mat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Restrictions;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.def.DefDBExt;

/**
* TaMatStockIoOrder by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_MAT_STOCK_IO )
public class TaMatStockIo extends EntityAbstract<TaMatStockIo> {
	public static final int 	STAT_CANCEL		= 0 ;
	public static final int 	STAT_WAITING 	= 1 ;
	public static final int 	STAT_VALIDATION = 2 ;
	public static final int 	STAT_OK 		= 3 ;
	public static final int 	STAT_KO 		= 4 ;
	public static final int 	STAT_OK_PART	= 5 ;
	
	
	public static final int 	STAT_OUTSTK     = 20;
	public static final int 	STAT_TRACING    = 10;
	
	
//	public static final int 	TYPE_01_STOCK_IN		= 1;
//	public static final int 	TYPE_01_STOCK_OUT		= 2;
//	
//	public static final int 	TYPE_02_BUY				= 200001; //cfg value	
//	public static final int 	TYPE_02_SELL			= 200003;
//	
//	public static final int 	TYPE_02_TRANSFER_IN		= 200002;
//	public static final int 	TYPE_02_EXPIRATION		= 200004;
//	public static final int 	TYPE_02_FAILURE			= 200005;
//	public static final int 	TYPE_02_LOSS			= 200006;
//	public static final int 	TYPE_02_TRANSFER_OUT	= 200007;
	
	private static final long serialVersionUID = 1L;
	
	//---------------------------List of Column from DB-----------------------------
	
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_I_PER_MANAGER                     =	"I_Per_Manager";
	public static final String	COL_I_MAT_MATERIAL                    =	"I_Mat_Material";
	public static final String	COL_I_MAT_STOCK                       =	"I_Mat_Stock";
	public static final String	COL_I_MAT_WAREHOUSE                   =	"I_Mat_Warehouse";
	public static final String	COL_I_SOR_ORDER                       =	"I_Sor_Order";
	public static final String	COL_I_SOR_ORDER_DETAIL                =	"I_Sor_Order_Detail";
	public static final String	COL_I_STATUS                          =	"I_Status";
	public static final String	COL_I_TYPE                            =	"I_Type";
	public static final String	COL_F_VAL_00                          =	"F_Val_00";
	public static final String	COL_F_VAL_01                          =	"F_Val_01";// quantity
	public static final String	COL_F_VAL_02                          =	"F_Val_02";// ratio
	public static final String	COL_F_VAL_03                          =	"F_Val_03";// Q1 * R1 
	public static final String	COL_F_VAL_04                          =	"F_Val_04";// RQ base of stock before operation 
	public static final String	COL_F_VAL_05                          =	"F_Val_05";
	public static final String	COL_D_DATE_01                         =	"D_Date_01";
	public static final String	COL_D_DATE_02                         =	"D_Date_02";
	public static final String	COL_D_DATE_03                         =	"D_Date_03";
	public static final String	COL_D_DATE_04                         =	"D_Date_04";

	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_I_PER_MANAGER                     =	"I_Per_Manager";
	public static final String	ATT_I_MAT_MATERIAL                    =	"I_Mat_Material";
	public static final String	ATT_I_MAT_STOCK                       =	"I_Mat_Stock";
	public static final String	ATT_I_MAT_WAREHOUSE                   =	"I_Mat_Warehouse";
	public static final String	ATT_I_SOR_ORDER                       =	"I_Sor_Order";
	public static final String	ATT_I_SOR_ORDER_DETAIL                =	"I_Sor_Order_Detail";
	public static final String	ATT_I_STATUS                          =	"I_Status";
	public static final String	ATT_I_TYPE                            =	"I_Type";
	public static final String	ATT_F_VAL_00                          =	"F_Val_00";
	public static final String	ATT_F_VAL_01                          =	"F_Val_01";
	public static final String	ATT_F_VAL_02                          =	"F_Val_02";
	public static final String	ATT_F_VAL_03                          =	"F_Val_03";
	public static final String	ATT_F_VAL_04                          =	"F_Val_04";
	public static final String	ATT_F_VAL_05                          =	"F_Val_05";
	public static final String	ATT_D_DATE_01                         =	"D_Date_01";
	public static final String	ATT_D_DATE_02                         =	"D_Date_02";
	public static final String	ATT_D_DATE_03                         =	"D_Date_03";
	public static final String	ATT_D_DATE_04                         =	"D_Date_04";

	
	public static final String	ATT_O_PARENT_CODE                     =	"O_Parent_Code";
	public static final String	ATT_O_FILES                           =	"O_Files";
	public static final String	ATT_O_DETAILS						  = "O_Details";
	public static final String	ATT_O_STOCKIO_PARENT				  = "O_StockIo_Parent";

	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaMatStockIo> 	DAO;
	static{
		DAO = new EntityDAO<TaMatStockIo>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaMatStockIo.class,RIGHTS, HISTORY, DefDBExt.TA_MAT_STOCK_IO, DefDBExt.ID_TA_MAT_STOCK_IO);

	}

	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_I_TYPE, nullable = true)
	private	Integer         I_Type;
       
	@Column(name=COL_D_DATE_01, nullable = true)
	private	Date            D_Date_01;
    
	@Column(name=COL_D_DATE_02, nullable = true)
	private	Date            D_Date_02;
    
	@Column(name=COL_D_DATE_03, nullable = true)
	private	Date            D_Date_03;
	
	@Column(name=COL_D_DATE_04, nullable = true)
	private	Date            D_Date_04;
    
	@Column(name=COL_I_STATUS, nullable = true)
	private	Integer         I_Status;

	@Column(name=COL_F_VAL_00, nullable = true)
	private	Double	        F_Val_00;
	
	@Column(name=COL_F_VAL_01, nullable = true)
	private	Double           F_Val_01;

	@Column(name=COL_F_VAL_02, nullable = true)
	private	Double           F_Val_02;

	@Column(name=COL_F_VAL_03, nullable = true)
	private	Double           F_Val_03;

	@Column(name=COL_F_VAL_04, nullable = true)
	private	Double           F_Val_04;

	@Column(name=COL_F_VAL_05, nullable = true)
	private	Double           F_Val_05;
	
	@Column(name=COL_I_MAT_WAREHOUSE, nullable = true)
	private	Integer         I_Mat_Warehouse;
	
	@Column(name=COL_I_PER_MANAGER, nullable = true)
	private	Integer         I_Per_Manager;
	
	@Column(name=COL_I_MAT_MATERIAL , nullable = true)
	private	Integer         I_Mat_Material;
	
	@Column(name=COL_I_MAT_STOCK , nullable = true)
	private	Integer         I_Mat_Stock;
	
	@Column(name=COL_I_SOR_ORDER , nullable = true)
	private	Integer         I_Sor_Order;
	
	@Column(name=COL_I_SOR_ORDER_DETAIL , nullable = true)
	private	Integer         I_Sor_Order_Detail;
      
	//-----------------------Transient Variables-------------------------
	@Transient
	private String	 O_Parent_Code;
	
	@Transient
	private List<TaTpyDocument> 		O_Files = null;
	
	@Transient
	private TaMatStockIo 				O_StockIo_Parent = null;
	
	//---------------------Constructeurs-----------------------
	public TaMatStockIo(Integer I_Mat_Stock) throws Exception {
		this.reqSetAttr(
			ATT_I_MAT_WAREHOUSE      , I_Mat_Stock
		);
		//doInitDBFlag();
	}
	public TaMatStockIo(Integer I_Type, Date D_Date_01, Date D_Date_02, Date D_Date_03, Date D_Date_04, Float F_Val_00, Float F_Val_01, Float F_Val_02, Float F_Val_03, Float F_Val_04, Float F_Val_05, Integer I_Status, Integer I_Mat_Stock, Integer I_Per_Manager, Integer I_Mat_Material) throws Exception {
		this.reqSetAttr(
			ATT_I_TYPE                 , I_Type,
			ATT_D_DATE_01              , D_Date_01,
			ATT_D_DATE_02              , D_Date_02,
			ATT_D_DATE_03              , D_Date_03,
			ATT_D_DATE_04              , D_Date_04,
			ATT_F_VAL_00			   , F_Val_00,
			ATT_F_VAL_01			   , F_Val_01,
			ATT_F_VAL_02			   , F_Val_02,
			ATT_F_VAL_03			   , F_Val_03,
			ATT_F_VAL_04			   , F_Val_04,
			ATT_F_VAL_05			   , F_Val_05,
			ATT_I_STATUS               , I_Status,
			ATT_I_MAT_STOCK            , I_Mat_Stock,
			ATT_I_PER_MANAGER          , I_Per_Manager,
			ATT_I_MAT_MATERIAL		   , I_Mat_Material
		);
		//doInitDBFlag();
	}
	
	public TaMatStockIo(Integer typ, Integer orderId, Integer manId, Date ordDate) throws Exception {
		this.I_Type              	= typ;
		this.D_Date_01              = ordDate;
		this.I_Per_Manager			= manId;
		this.D_Date_02              = new Date();
//		this.T_Code                 = order.req(TaSorOrder.ATT_T_CODE);
	}
	
	//---------------------EntityInterface-----------------------
	public TaMatStockIo(){}

	public TaMatStockIo(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		// doInitDBFlag();
	}
	
	
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaMatStockIo ent) {
		if (ent == this) return;
		this.I_Per_Manager          = ent.I_Per_Manager;
		this.I_Mat_Material         = ent.I_Mat_Material;
		this.I_Mat_Stock            = ent.I_Mat_Stock;
		this.I_Mat_Warehouse        = ent.I_Mat_Warehouse;
		this.I_Sor_Order            = ent.I_Sor_Order;
		this.I_Sor_Order_Detail     = ent.I_Sor_Order_Detail;
		this.I_Status               = ent.I_Status;
		this.I_Type                 = ent.I_Type;
		this.F_Val_00               = ent.F_Val_00;
		this.F_Val_01               = ent.F_Val_01;
		this.F_Val_02               = ent.F_Val_02;
		this.F_Val_03               = ent.F_Val_03;
		this.F_Val_04               = ent.F_Val_04;
		this.F_Val_05               = ent.F_Val_05;
		this.D_Date_01              = ent.D_Date_01;
		this.D_Date_02              = ent.D_Date_02;
		this.D_Date_03              = ent.D_Date_03;
		this.D_Date_04              = ent.D_Date_04;



		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((TaMatStockIo)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}

	public List<TaTpyDocument> doBuildFiles(HttpServletRequest request) throws Exception {
		if(O_Files == null) {
			O_Files = TaTpyDocument.DAO.reqList(
								Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_TYPE	, DefDBExt.ID_TA_MAT_STOCK_IO),
								Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_ID	, this.I_ID));
			for (TaTpyDocument d : O_Files) {
				d.reqSet(TaTpyDocument.ATT_T_INFO_03, null);
				d.reqSet(TaTpyDocument.ATT_T_INFO_04, null);
			}
		}
		
		if(request != null) {
			String serverUrl = request.getRequestURL().toString();
			for(TaTpyDocument f : O_Files) {
				f.doBuildURL(serverUrl);
			}
		}
		
		return O_Files;
	}
	
	public static TaMatStockIo reqClone(TaMatStockIo ent) {
		TaMatStockIo io = new TaMatStockIo();
		io.doMergeWith(ent);
		return io;
	}
}