package com.hnv.db.aut;

import java.io.Serializable;
import java.util.Collection;
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
import org.hibernate.criterion.Restrictions;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.def.DefDBExt;
/**
* TaAutRole by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_AUT_ROLE )
public class TaAutRole extends EntityAbstract<TaAutRole> {

	private static final long serialVersionUID = 1L;

	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              	= "I_ID";
	public static final String	COL_T_NAME                            	= "T_Name";
	public static final String 	COL_T_INFO_01 							= "T_Info_01"; //descp
	public static final String 	COL_T_INFO_02							= "T_Info_02"; //group
	public static final String	COL_T_AUT_RIGHT                		  	= "T_Aut_Right";
	public static final String	COL_I_STATUS                          	= "I_Status";

	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              	= "I_ID";
	public static final String	ATT_T_NAME                            	= "T_Name";
	public static final String  ATT_T_INFO_01 							= "T_Info_01";
	public static final String  ATT_T_INFO_02 							= "T_Info_02";
	public static final String	ATT_T_AUT_RIGHT                		  	= "T_Aut_Right";
	public static final String	ATT_I_STATUS                          	= "I_Status";
	
	public static final String	ATT_O_RIGHTS          	  			 	= "O_Rights";


	public static final int		STAT_ON									= 1;
	public static final int		STAT_OFF							= 0;
	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaAutRole> 	DAO;
	static{
		DAO = new EntityDAO<TaAutRole>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaAutRole.class,RIGHTS, HISTORY, DefDBExt.TA_AUT_ROLE, DefDBExt.ID_TA_AUT_ROLE);
	
	}

	//-----------------------Class Attributs-------------------------
	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_I_STATUS, nullable = true)
	private	Integer         I_Status;
       
	@Column(name=COL_T_NAME, nullable = false)
	private	String          T_Name;
       
	@Column(name=COL_T_INFO_01, nullable = true)
	private	String            T_Info_01;
    
	@Column(name=COL_T_INFO_02, nullable = true)
	private	String            T_Info_02;

	@Column(name=COL_T_AUT_RIGHT, nullable = true)
	private	String          T_Aut_Right;

	
	//-----------------------Transient Variables-------------------------
	//---------------------Constructeurs-----------------------
	private TaAutRole(){}

	public TaAutRole(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		//doInitDBFlag();
	}
	
	public TaAutRole(String T_Name) throws Exception {
		this.reqSetAttr(
			ATT_T_NAME       , T_Name
		);
		//doInitDBFlag();
	}
	
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaAutRole ent) {
		if (ent == this) return;
		this.T_Name                 = ent.T_Name;
		this.T_Info_01         		= ent.T_Info_01;
		this.T_Info_02         		= ent.T_Info_02;
		this.T_Aut_Right       		= ent.T_Aut_Right;	
		this.I_Status       		= ent.I_Status;	
		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((TaAutRole)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}
	
}
