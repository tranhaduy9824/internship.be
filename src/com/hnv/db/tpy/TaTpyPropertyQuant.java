package com.hnv.db.tpy;

import java.io.Serializable;
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
* TaTpyPropertyQuant by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_TPY_PROPERTY_QUANT )
public class TaTpyPropertyQuant extends EntityAbstract<TaTpyPropertyQuant> {

	private static final long serialVersionUID = 1L;

	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	
	public static final String	COL_I_TYPE                            =	"I_Type";
	public static final String	COL_I_PRIORITY                        =	"I_Priority";
	public static final String	COL_I_STATUS                          =	"I_Status";
	public static final String	COL_T_TITLE                           =	"T_Title";
	public static final String	COL_T_COMMENT                         =	"T_Comment";
	public static final String	COL_F_PROP_01                         =	"F_Prop_01";
	public static final String	COL_F_PROP_02                         =	"F_Prop_02";
	public static final String	COL_F_PROP_03                         =	"F_Prop_03";
	public static final String	COL_F_PROP_04                         =	"F_Prop_04";
	
	public static final String	COL_I_PARENT_TYPE                     =	"I_Parent_Type";
	public static final String	COL_I_PARENT_ID                       =	"I_Parent_ID";
	public static final String	COL_I_ENTITY_TYPE                     =	"I_Entity_Type";
	public static final String	COL_I_ENTITY_ID                       =	"I_Entity_ID";


	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	
	public static final String	ATT_I_TYPE                            =	"I_Type";
	public static final String	ATT_I_PRIORITY                        =	"I_Priority";
	public static final String	ATT_I_STATUS                          =	"I_Status";
	public static final String	ATT_T_TITLE                           =	"T_Title";
	public static final String	ATT_T_COMMENT                         =	"T_Comment";
	public static final String	ATT_F_PROP_01                         =	"F_Prop_01";
	public static final String	ATT_F_PROP_02                         =	"F_Prop_02";
	public static final String	ATT_F_PROP_03                         =	"F_Prop_03";
	public static final String	ATT_F_PROP_04                         =	"F_Prop_04";
	
	public static final String	ATT_I_PARENT_TYPE                     =	"I_Parent_Type";
	public static final String	ATT_I_PARENT_ID                       =	"I_Parent_ID";
	public static final String	ATT_I_ENTITY_TYPE                     =	"I_Entity_Type";
	public static final String	ATT_I_ENTITY_ID                       =	"I_Entity_ID";



	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 

	public		static 	final EntityDAO<TaTpyPropertyQuant> 	DAO;
	static{
		DAO = new EntityDAO<TaTpyPropertyQuant>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaTpyPropertyQuant.class,RIGHTS);
	}

	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_I_PARENT_TYPE, nullable = false)
	private	Integer         I_Parent_Type;

	@Column(name=COL_I_PARENT_ID, nullable = false)
	private	Integer         I_Parent_ID;
  
	@Column(name=COL_I_TYPE, nullable = true)
	private	Integer         I_Type;
       
	@Column(name=COL_I_PRIORITY, nullable = true)
	private	Integer         I_Priority;
   
	@Column(name=COL_I_STATUS, nullable = true)
	private	Integer         I_Status;
     
	@Column(name=COL_T_TITLE, nullable = true)
	private	String          T_Title;
      
	@Column(name=COL_T_COMMENT, nullable = true)
	private	String          T_Comment;
    
	@Column(name=COL_F_PROP_01, nullable = true)
	private	Double          F_Prop_01;
    
	@Column(name=COL_F_PROP_02, nullable = true)
	private	Double          F_Prop_02;
    
	@Column(name=COL_F_PROP_03, nullable = true)
	private	Double          F_Prop_03;
    
	@Column(name=COL_F_PROP_04, nullable = true)
	private	Double          F_Prop_04;
    

	@Column(name=COL_I_ENTITY_TYPE, nullable = true)
	private	Integer         I_Entity_Type;

	@Column(name=COL_I_ENTITY_ID, nullable = true)
	private	Integer         I_Entity_ID;
	//-----------------------Transient Variables-------------------------


	//---------------------Constructeurs-----------------------
	private TaTpyPropertyQuant(){}

	public TaTpyPropertyQuant(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		//doInitDBFlag();
	}
	
	public TaTpyPropertyQuant(Integer I_Parent_Type, Integer I_Parent_ID) throws Exception {
		this.reqSetAttr(
			ATT_I_PARENT_TYPE, I_Parent_Type,
			ATT_I_PARENT_ID  , I_Parent_ID
		);
		doInitDBFlag();
	}
	public TaTpyPropertyQuant(Integer I_Parent_Type, Integer I_Parent_ID, Integer I_Type, Integer I_Priority, Integer I_Status, String T_Title, String T_Comment, Double F_Prop_01, Double F_Prop_02, Double F_Prop_03, Double F_Prop_04) throws Exception {
		this.reqSetAttr(
			ATT_I_PARENT_TYPE          , I_Parent_Type,
			ATT_I_PARENT_ID            , I_Parent_ID,
			ATT_I_TYPE                 , I_Type,
			ATT_I_PRIORITY             , I_Priority,
			ATT_I_STATUS               , I_Status,
			ATT_T_TITLE                , T_Title,
			ATT_T_COMMENT              , T_Comment,
			ATT_F_PROP_01              , F_Prop_01,
			ATT_F_PROP_02              , F_Prop_02,
			ATT_F_PROP_03              , F_Prop_03,
			ATT_F_PROP_04              , F_Prop_04
		);
		doInitDBFlag();
	}
	
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaTpyPropertyQuant ent) {
		if (ent == this) return;
		this.I_Parent_Type          = ent.I_Parent_Type;
		this.I_Parent_ID            = ent.I_Parent_ID;
		this.I_Type                 = ent.I_Type;
		this.I_Priority             = ent.I_Priority;
		this.I_Status               = ent.I_Status;
		this.T_Title                = ent.T_Title;
		this.T_Comment              = ent.T_Comment;
		this.F_Prop_01              = ent.F_Prop_01;
		this.F_Prop_02              = ent.F_Prop_02;
		this.F_Prop_03              = ent.F_Prop_03;
		this.F_Prop_04              = ent.F_Prop_04;



		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((TaTpyPropertyQuant)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}

	@Override
	public String toString() {
		return 	"TaTpyPropertyQuant { " +
				"I_ID:"+                      I_ID +"," + 
				"I_Parent_Type:"+             I_Parent_Type +"," + 
				"I_Parent_ID:"+               I_Parent_ID +"," + 
				"I_Type:"+                    I_Type +"," + 
				"I_Priority:"+                I_Priority +"," + 
				"I_Status:"+                  I_Status +"," + 
				"T_Title:"+                   T_Title +"," + 
				"T_Comment:"+                 T_Comment +"," + 
				"F_Prop_01:"+                 F_Prop_01 +"," + 
				"F_Prop_02:"+                 F_Prop_02 +"," + 
				"F_Prop_03:"+                 F_Prop_03 +"," + 
				"F_Prop_04:"+                 F_Prop_04 +"," + 
				 "}";

	}


}
