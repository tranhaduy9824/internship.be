package com.hnv.db.cfg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.criterion.Restrictions;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.def.DefDBExt;		

/**
* TaCfgGroup by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_CFG_VALUE )
public class TaCfgValue extends EntityAbstract<TaCfgValue> {

	private static final long serialVersionUID = 1L;

	public static final int		STAT_01_NEW 			= 0;
	public static final int		STAT_01_ACTIVE 			= 1;
	public static final int		STAT_01_REVIEW 			= 5;
	public static final int		STAT_01_DELETED 		= 10;
	
	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_T_NAME                            =	"T_Name";
	public static final String	COL_T_CODE                            =	"T_Code";
	public static final String	COL_T_INFO_01                         =	"T_Info_01";
	public static final String	COL_T_INFO_02                         =	"T_Info_02";
	public static final String	COL_T_INFO_03                         =	"T_Info_03";
	public static final String	COL_I_PARENT                          =	"I_Parent";
	public static final String	COL_I_TYPE_01                         =	"I_Type_01";
	public static final String	COL_I_TYPE_02                         =	"I_Type_02";
	public static final String	COL_I_STATUS_01                       =	"I_Status_01";
	public static final String	COL_I_STATUS_02                       =	"I_Status_02";
	public static final String	COL_I_PER_MANAGER                     =	"I_Per_Manager";



	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_I_TYPE_01                         =	"I_Type_01";
	public static final String	ATT_I_TYPE_02                         =	"I_Type_02";
	public static final String	ATT_I_STATUS_01                       =	"I_Status_01";
	public static final String	ATT_I_STATUS_02                       =	"I_Status_02";
	public static final String	ATT_T_NAME                            =	"T_Name";
	public static final String	ATT_T_CODE                            =	"T_Code";
	public static final String	ATT_T_INFO_01                         =	"T_Info_01";
	public static final String	ATT_T_INFO_02                         =	"T_Info_02";
	public static final String	ATT_T_INFO_03                         =	"T_Info_03";
	public static final String	ATT_I_PARENT                          =	"I_Parent";
	public static final String	ATT_I_PER_MANAGER                     =	"I_Per_Manager";
	
	public static final String	ATT_O_CHILDREN                   	  =	"O_Children";

	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 

	public		static 	final EntityDAO<TaCfgValue> 	DAO;
	static{
		DAO = new EntityDAO<TaCfgValue>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaCfgValue.class,RIGHTS);

	}

	//-----------------------Class Attributs-------------------------
	@Id
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_I_TYPE_01, nullable = true)
	private	Integer         I_Type_01;
    
	@Column(name=COL_I_TYPE_02, nullable = true)
	private	Integer         I_Type_02;
    
	@Column(name=COL_I_STATUS_01, nullable = true)
	private	Integer         I_Status_01;
  
	@Column(name=COL_I_STATUS_02, nullable = true)
	private	Integer         I_Status_02;
  
	@Column(name=COL_T_NAME, nullable = false)
	private	String          T_Name;
       
	@Column(name=COL_T_CODE, nullable = true)
	private	String          T_Code;
       
	@Column(name=COL_T_INFO_01, nullable = true)
	private	String          T_Info_01;
    
	@Column(name=COL_T_INFO_02, nullable = true)
	private	String          T_Info_02;
    
	@Column(name=COL_T_INFO_03, nullable = true)
	private	String          T_Info_03;
    
	@Column(name=COL_I_PARENT, nullable = true)
	private	Integer         I_Parent;
     
	@Column(name=COL_I_PER_MANAGER, nullable = true)
	private	Integer         I_Per_Manager;
     

    
	//-----------------------Transient Variables-------------------------
	@Transient
	private List<TaCfgValue>		O_Children;

	//---------------------Constructeurs-----------------------
	private TaCfgValue(){}

	public TaCfgValue(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		doInitDBFlag();
	}
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaCfgValue ent) {
		if (ent == this) return;
		this.I_Type_01              = ent.I_Type_01;
		this.I_Type_02              = ent.I_Type_02;
		this.I_Status_01            = ent.I_Status_01;
		this.I_Status_02            = ent.I_Status_02;
		this.T_Name                 = ent.T_Name;
		this.T_Code                 = ent.T_Code;
		this.T_Info_01              = ent.T_Info_01;
		this.T_Info_02              = ent.T_Info_02;
		this.T_Info_03              = ent.T_Info_03;
		this.I_Parent               = ent.I_Parent;
		this.I_Per_Manager          = ent.I_Per_Manager;
		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((TaCfgValue)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}

	
//	public void doBuildAll(boolean forced){
//		doBuildGroups		(forced);
//	}
	
	private void addGroupChild(TaCfgValue val){
		if (O_Children==null) O_Children = new ArrayList<TaCfgValue>(); 
		O_Children.add(val);
	}
	/*private void buildSubLevel(boolean forced, int level, List<TaCfgValue> grpChildren){
		if (grpChildren==null || grpChildren.size()==0) return;
		
		List<Integer> 					listId  = new ArrayList<Integer>();
		Hashtable<Integer, TaCfgValue> 	dict 	= new Hashtable<Integer, TaCfgValue> ();
		
		for (TaCfgValue grp: grpChildren ){
			if (forced){				
				grp.O_Children = null;
			}else
				if (grp.O_Children!=null) continue;
			
			int id = (int)grp.reqRef();
			
			listId.add(id);
			dict.put(id, grp);
		}
		
		
		try {
			List<TaCfgGroupValue> 	values 	= TaCfgGroupValue	.DAO.reqList(Restrictions.in(TaCfgGroupValue.ATT_I_CFG_GROUP, listId));
			List<TaCfgValue> 		grps 	= TaCfgValue		.DAO.reqList(Restrictions.in(TaCfgGroupValue.ATT_I_PARENT	, listId));
			
			for (TaCfgGroupValue val : values){
				int 		idG = (int)val.req(TaCfgGroupValue.ATT_I_CFG_GROUP);
				TaCfgValue 	g 	= dict.get(idG);
			}
			
			for (TaCfgValue val : grps){
				int 		idG = (int)val.req(TaCfgGroupValue.ATT_I_CFG_GROUP);
				TaCfgValue 	g 	= dict.get(idG);
				if (g!=null) g.addGroupChild(val);
			}
			
			if (grps.size()>0)
				for (TaCfgValue grp: dict.values()){
					buildSubLevel(forced, level+1, grp.O_Children);
				}
			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}*/
	
//	public void doBuildGroups(boolean forced){
//	
//		if (!forced && this.O_Children!=null) return;
//		try {
//			this.O_Children = TaCfgValue.DAO.reqList(Restrictions.eq(TaCfgGroupValue.ATT_I_PARENT, this.I_ID));
//			if (this.O_Children!=null){
//				/*for (TaCfgGroup gr : this.O_Groups ){
//					gr.buildGroups(forced, level+1);
//					gr.buildValues(forced);
//				}*/
//				buildSubLevel (forced, 0, this.O_Children);
//			}
//		} catch (Exception e) {		
//			e.printStackTrace();
//		}		
//	}
	
	private static int MAX_LEVEL = 20;


}
