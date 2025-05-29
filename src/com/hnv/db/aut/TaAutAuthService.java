package com.hnv.db.aut;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.common.tool.ToolSet;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.def.DefDBExt;	

/**
* TaAutAuthService by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_AUT_AUTH_SERVICE )
public class TaAutAuthService extends EntityAbstract<TaAutAuthService> {

	private static final long serialVersionUID = 1L;

	public static final int	STAT_NEW 			= 0;
	public static final int	STAT_ACTIVE 		= 1; 
	public static final int	STAT_REVIEW 		= 5; 
	public static final int	STAT_DELETED 		= 10;
	 
	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_T_INFO_01                         =	"T_Info_01"; //service class
	public static final String	COL_T_INFO_02                         =	"T_Info_02"; //sv name
	public static final String	COL_I_TYPE_01                         =	"I_Type_01";
	public static final String	COL_I_TYPE_02                         =	"I_Type_02";
	public static final String	COL_I_STATUS                          =	"I_Status";
	public static final String	COL_D_DATE_01                         =	"D_Date_01";
	public static final String	COL_D_DATE_02                         =	"D_Date_02";
	public static final String	COL_T_AUT_ROLE                        =	"T_Aut_Role"; //ex: 100, 101 & 102
	public static final String	COL_T_AUT_RIGHT                       =	"T_Aut_Right";



	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_T_INFO_01                         =	"T_Info_01";
	public static final String	ATT_T_INFO_02                         =	"T_Info_02";
	public static final String	ATT_I_TYPE_01                         =	"I_Type_01";
	public static final String	ATT_I_TYPE_02                         =	"I_Type_02";
	public static final String	ATT_I_STATUS                          =	"I_Status";
	public static final String	ATT_D_DATE_01                         =	"D_Date_01";
	public static final String	ATT_D_DATE_02                         =	"D_Date_02";
	public static final String	ATT_T_AUT_ROLE                        =	"T_Aut_Role";
	public static final String	ATT_T_AUT_RIGHT                       =	"T_Aut_Right";



	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaAutAuthService> 	DAO;
	static{
		DAO = new EntityDAO<TaAutAuthService>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaAutAuthService.class,RIGHTS, HISTORY, DefDBExt.TA_AUT_AUTH_SERVICE, DefDBExt.ID_TA_AUT_AUTH_SERVICE);

	}

	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_T_INFO_01, nullable = true)
	private	String         T_Info_01;
    
	@Column(name=COL_T_INFO_02, nullable = true)
	private	String         T_Info_02;
    
	@Column(name=COL_I_TYPE_01, nullable = true)
	private	Integer         I_Type_01;
    
	@Column(name=COL_I_TYPE_02, nullable = true)
	private	Integer         I_Type_02;
    
	@Column(name=COL_I_STATUS, nullable = true)
	private	Integer         I_Status;
     
	@Column(name=COL_D_DATE_01, nullable = true)
	private	Date            D_Date_01;
    
	@Column(name=COL_D_DATE_02, nullable = true)
	private	Date            D_Date_02;
    
	@Column(name=COL_T_AUT_ROLE, nullable = true)
	private	String          T_Aut_Role;
   
	@Column(name=COL_T_AUT_RIGHT, nullable = true)
	private	String          T_Aut_Right;
  

    @Transient
    private List<Set<Integer>> O_Roles;
    
    @Transient
    private List<Set<Integer>> O_Rights;
	//-----------------------Transient Variables-------------------------


	//---------------------Constructeurs-----------------------
	private TaAutAuthService(){}

	public TaAutAuthService(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		// doInitDBFlag();
	}
	
	
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaAutAuthService ent) {
		if (ent == this) return;
		this.T_Info_01              = ent.T_Info_01;
		this.T_Info_02              = ent.T_Info_02;
		this.I_Type_01              = ent.I_Type_01;
		this.I_Type_02              = ent.I_Type_02;
		this.I_Status               = ent.I_Status;
		this.D_Date_01              = ent.D_Date_01;
		this.D_Date_02              = ent.D_Date_02;
		this.T_Aut_Role             = ent.T_Aut_Role;
		this.T_Aut_Right            = ent.T_Aut_Right;



		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((TaAutAuthService)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}
	//-----------------------------------------------------------------------------------------
	private List<Set<Integer>> reqSetRoleRight(String s) {
		if (s!=null) {
			List<Set<Integer>> set = new ArrayList<Set<Integer>>();
			s = s.replace(" ", "");
			Set<String> lst = ToolSet.reqSetStr(s, ",;|!-_");
			for (String st : lst) {
				Set<Integer> lstSub = ToolSet.reqSetInt(s, "&");
				set.add(lstSub);
			}
			return set;
		}
		return null;
	}
	
	public boolean canAuthorize(TaAutUser user) {
		if (O_Roles==null) 	O_Roles 	= reqSetRoleRight(this.T_Aut_Role);
		if (O_Rights==null) O_Rights 	= reqSetRoleRight(this.T_Aut_Right);
		
		if (O_Roles!=null) {
			for (Set<Integer> r : O_Roles)
				if (user.canHaveRoles(r)) return true;
		}
		
		if (O_Rights!=null) {
			for (Set<Integer> r : O_Rights)
				if (user.canHaveRights(r)) return true;
		}
		
		if (O_Roles!=null || O_Rights!=null) return false;
		
		return true;
	}
}
