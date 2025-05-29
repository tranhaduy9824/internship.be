package com.hnv.db.aut;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.def.DefDBExt;	

/**
* TaAutHistory by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_AUT_HISTORY )
public class TaAutHistory extends EntityAbstract<TaAutHistory> {

	private static final long serialVersionUID = 1L;

	public static final int	STAT_NEW 			= 0;
	public static final int	STAT_ACTIVE 		= 1; 
	public static final int	STAT_REVIEW 		= 5; 
	public static final int	STAT_DELETED 		= 10;
	
	public static final int	TYPE_CONN 			= 1;
	public static final int	TYPE_REQ 			= 2; 
	 
	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_I_AUT_USER                        =	"I_Aut_User";
	public static final String	COL_D_DATE                            =	"D_Date";
	public static final String	COL_I_TYPE                            =	"I_Type";
	public static final String	COL_T_VAL                             =	"T_Val";



	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_I_AUT_USER                        =	"I_Aut_User";
	public static final String	ATT_D_DATE                            =	"D_Date";
	public static final String	ATT_T_VAL                             =	"T_Val";
	public static final String	ATT_I_TYPE                            =	"I_Type";


	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 

	public		static 	final EntityDAO<TaAutHistory> 	DAO;
	static{
		DAO = new EntityDAO<TaAutHistory>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaAutHistory.class,RIGHTS);

	}

	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_I_AUT_USER, nullable = false)
	private	Integer         I_Aut_User;
   
	@Column(name=COL_D_DATE, nullable = false)
	private	Date            D_Date;
	
	@Column(name=COL_I_TYPE, nullable = false)
	private	Integer         I_Type;
       
	@Column(name=COL_T_VAL, nullable = true)
	private	String          T_Val;
        

    
	//-----------------------Transient Variables-------------------------
	//---------------------Constructeurs-----------------------
	private TaAutHistory(){}

	public TaAutHistory(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		// doInitDBFlag();
	}
	
	public TaAutHistory(int uId, int typ, Date dt, String val) throws Exception {
		this.reqSetAttr(
			ATT_I_AUT_USER             , uId,
			ATT_D_DATE                 , dt,
			ATT_I_TYPE                 , typ,
			ATT_T_VAL                  , val
		);
		doInitDBFlag();
	}
	
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaAutHistory ent) {
		if (ent == this) return;
		this.I_Aut_User             = ent.I_Aut_User;
		this.D_Date                 = ent.D_Date;
		this.T_Val                  = ent.T_Val;



		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((TaAutHistory)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}
	
	
	public static void doListDel(Session sess, Integer userId) throws Exception {
		Collection	<TaAutHistory>  		lst 	= TaAutHistory.DAO.reqList(sess,	Restrictions.eq(TaAutHistory.ATT_I_AUT_USER, userId));		
		TaAutHistory.DAO.doRemove(sess, lst);
	}
}
