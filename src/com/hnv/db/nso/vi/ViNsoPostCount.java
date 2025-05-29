package com.hnv.db.nso.vi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.hnv.api.def.DefTime;
import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.api.service.common.ResultPagination;
import com.hnv.common.tool.ToolDBEntity;
import com.hnv.common.tool.ToolData;
import com.hnv.common.tool.ToolSet;
import com.hnv.common.util.CacheData;
import com.hnv.data.json.JSONObject;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.aut.TaAutUser;
import com.hnv.db.aut.vi.ViAutUserMember;
import com.hnv.db.tpy.TaTpyCategoryEntity;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.db.tpy.TaTpyTranslation;
import com.hnv.def.DefDBExt;

/**
 * TaNsoPost by H&V SAS
 */
@Entity
@Table(name = DefDBExt.TA_NSO_POST )
public class ViNsoPostCount extends EntityAbstract<ViNsoPostCount> {
	public static final double EVAL_MIN = 1.0;
	public static final double EVAL_MAX = 5.0;

	private static final long serialVersionUID 	= 1L;
	private static final int  ENT_TYP			= DefDBExt.ID_TA_NSO_POST;
	
	
	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	

	public static final String	COL_I_COUNT                          =	"I_Count";  //count total
	
	
	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	
	public static final String	ATT_I_COUNT                          =	"I_Count";
	

	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<ViNsoPostCount> 	DAO;

	//---------------------------------------------------------------------------------------------------------------------------------------
	
	
	static{
		DAO = new EntityDAO<ViNsoPostCount>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN) , ViNsoPostCount.class, RIGHTS, HISTORY, DefDBExt.TA_NSO_POST, DefDBExt.ID_TA_NSO_POST);
	}

	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
     
	@Column(name=COL_I_COUNT, nullable = true)
	private	Integer         I_Count;
     
	
	
	//-----------------------Transient Variables-------------------------
	public static String SQL_COUNT_CMT = "SELECT tnp.I_VAL_02 as I_ID, count(*) as I_COUNT from ta_nso_post tnp where i_type_01 = 110 and tnp.I_Val_02  in (%s) group by tnp.I_VAL_02";



	@Override
	public void doMergeWith(ViNsoPostCount arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public Serializable reqRef() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
