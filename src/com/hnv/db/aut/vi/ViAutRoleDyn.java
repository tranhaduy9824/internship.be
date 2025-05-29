package com.hnv.db.aut.vi;

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
public class ViAutRoleDyn extends EntityAbstract<ViAutRoleDyn> {

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
	public static final String 	ATT_T_INFO_01 							= "T_Info_01"; //descp
	public static final String 	ATT_T_INFO_02							= "T_Info_02"; //group
	public static final String	ATT_T_AUT_RIGHT                		  	= "T_Aut_Right";
	public static final String	ATT_I_STATUS                          	= "I_Status";
	

	public static final int		STAT_ON									= 1;
	public static final int		STAT_OFF								= 0;
	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, false, false, false, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<ViAutRoleDyn> 	DAO;
	static{
		DAO = new EntityDAO<ViAutRoleDyn>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), ViAutRoleDyn.class,RIGHTS, HISTORY, DefDBExt.TA_AUT_ROLE, DefDBExt.ID_TA_AUT_ROLE);
	
	}

	//-----------------------Class Attributs-------------------------
	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
       
	@Column(name=COL_T_NAME, nullable = false)
	private	String          T_Name;
       
	@Column(name=COL_T_INFO_01, nullable = true)
	private	String          T_Info_01;
    
	@Column(name=COL_T_INFO_02, nullable = true)
	private	String          T_Info_02;

	@Column(name=COL_I_STATUS, nullable = true)
	private	Integer         I_Status;
	//-----------------------Transient Variables-------------------------
	//---------------------Constructeurs-----------------------
	private ViAutRoleDyn(){}

	public ViAutRoleDyn(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		//doInitDBFlag();
	}
	
	public ViAutRoleDyn(String T_Name) throws Exception {
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
	public void doMergeWith(ViAutRoleDyn ent) {
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((ViAutRoleDyn)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}
	
}
