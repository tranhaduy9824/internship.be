package com.hnv.db.tpy;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
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

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.nso.vi.ViNsoOfferSearch;
import com.hnv.db.nso.vi.ViNsoPostNew;
import com.hnv.db.tpy.vi.ViTpyCategoryDyn;
import com.hnv.def.DefDBExt;

@Entity
@Table(name = DefDBExt.TA_TPY_FAVORITE)
public class TaTpyFavorite extends EntityAbstract<TaTpyFavorite>{
	
	private static final long serialVersionUID = 1L;
	
	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_I_AUT_USER                        =	"I_Aut_User";
	public static final String	COL_I_ENTITY_TYPE                     =	"I_Entity_Type";
	public static final String	COL_I_ENTITY_ID                       = "I_Entity_ID";
	public static final String	COL_I_PRIORITY                        = "I_Priority";
	public static final String	COL_D_DATE                        	  = "D_Date";
	public static final String	COL_T_TITLE                           =	"T_Title";
	public static final String	COL_T_DESCRIPTION                     =	"T_Description";
	public static final String	COL_I_TYPE                            =	"I_Type";
	

	//---------------------------List of ATTR of Class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_I_AUT_USER                        =	"I_Aut_User";
	public static final String	ATT_I_ENTITY_TYPE                     =	"I_Entity_Type";
	public static final String	ATT_I_ENTITY_ID                       = "I_Entity_ID";
	public static final String	ATT_I_PRIORITY                        = "I_Priority";
	public static final String	ATT_D_DATE                        	  = "D_Date";
	public static final String	ATT_T_TITLE                           =	"T_Title";
	public static final String	ATT_T_DESRCIPTION                     =	"T_Description";
	public static final String	ATT_I_TYPE                            =	"I_Type";

	public static final String	ATT_O_ENTITY                          =	"O_Entity";
	
	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static	final boolean				API_CACHE 	= false;
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaTpyFavorite> 	DAO;	
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	public static final int FAVORITE_TYPE_UNLIKE			= 0;
	public static final int FAVORITE_TYPE_LIKE				= 1;
	public static final int FAVORITE_TYPE_STICKY_NOTE_01	= 100;
	public static final int FAVORITE_TYPE_STICKY_NOTE_02	= 200;
		
	//---------------------------------------------------------------------------------------------------------------------------------------
	static{
		DAO = new EntityDAO<TaTpyFavorite>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN) , TaTpyFavorite.class,RIGHTS);

	}
	
	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_I_AUT_USER, nullable = false)
	private	Integer         I_Aut_User;
        
	@Column(name=COL_I_ENTITY_TYPE, nullable = false)
	private	Integer         I_Entity_Type;
      
	@Column(name=COL_I_ENTITY_ID, nullable = false)
	private	Integer         I_Entity_ID;
 
	@Column(name=COL_I_PRIORITY, nullable = true)
	private	Integer         I_Priority;
 
	@Column(name=COL_D_DATE, nullable = true)
	private	Date          	D_Date; 
 
	@Column(name=COL_T_TITLE, nullable = true)
	private	String          T_Title;
 
	@Column(name=COL_T_DESCRIPTION, nullable = true)
	private	String          T_Description;
 
	@Column(name=COL_I_TYPE, nullable = true)
	private	Integer         I_Type;
	
	//-----------------------Transient Variables-------------------------
	@Transient
	private	ViNsoOfferSearch  	O_Entity;
	
	
	//---------------------Constructeurs-----------------------
	public TaTpyFavorite() {
		// TODO Auto-generated constructor stub
	}

	public TaTpyFavorite(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		//doInitDBFlag();
	}
	
	public TaTpyFavorite(String T_Title) throws Exception {
		this.reqSetAttr(
			ATT_T_TITLE      , T_Title
		);
		//doInitDBFlag();
	}
	
	public TaTpyFavorite(String I_ID, String I_Aut_User, String I_Entity_Type, String I_Entity_ID, String I_Priority, String D_Date, String T_Title, String T_Description, String I_Type) throws Exception {
		this.reqSetAttr(      
			ATT_I_AUT_USER             , I_Aut_User,     
			ATT_I_ENTITY_TYPE          , I_Entity_Type,          
			ATT_I_ENTITY_ID            , I_Entity_ID,          
			ATT_I_PRIORITY             , I_Priority,          
			ATT_D_DATE                 , D_Date,       	  
			ATT_T_TITLE                , T_Title,      
			ATT_T_DESRCIPTION          , T_Description,   
			ATT_I_TYPE                 , I_Type
				
		);
		//doInitDBFlag();
	}
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaTpyFavorite ent) {
		if (ent == this) return;
		
		this.I_ID                  = ent.I_ID;
		this.I_Aut_User            = ent.I_Aut_User;
		this.I_Entity_Type         = ent.I_Entity_Type;
		this.I_Entity_ID           = ent.I_Entity_ID;
		this.I_Priority            = ent.I_Priority;
		this.D_Date                = ent.D_Date;
		this.T_Title               = ent.T_Title;
		this.T_Description         = ent.T_Description;
		this.I_Type                = ent.I_Type;


		//---------------------Merge Transient Variables if exist-----------------------
		this.O_Entity                 = ent.O_Entity;
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((TaTpyFavorite)o).I_ID);
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
		return 	"TaTpyFavorite { " +
				"I_ID:"+                     	I_ID 			+"," + 
				"I_Aut_User:"+                	I_Aut_User 		+"," + 
				"I_Entity_Type:"+             	I_Entity_Type 	+"," + 
				"I_Entity_ID:"+               	I_Entity_ID 	+"," + 
				"I_Priority:"+              	I_Priority		+"," + 
				"D_Date:"+              		D_Date 			+"," + 
				"T_Title:"+              		T_Title			+"," + 
				"T_Description:"+             	T_Description 	+"," + 
				"I_Type:"+                 		I_Type 			+"," + 
				 "}";
	}
	
	//--------------------------------------------------------------------------
	@Transient
	private boolean isBuilt_public 	= false;
	@Transient
	private boolean isBuilt_manager = false;
	
	public static List<TaTpyFavorite> reqListFavorite(Integer userId, String searchkey, Integer nbLine) throws Exception {
		Criterion cri = null;
		cri = Restrictions.or(Restrictions.like(TaTpyFavorite.ATT_I_ID	, "%"+searchkey+"%"),
				Restrictions.like(TaTpyFavorite.ATT_T_TITLE	, "%"+searchkey+"%"));
		
//			if(stat != null)
//				cri 	= Restrictions.and(cri, Restrictions.eq(TaNsoFavorite.ATT_I_STATUS, stat));
		
		if(userId != null)
			cri 	= Restrictions.and(cri, Restrictions.eq(TaTpyFavorite.ATT_I_AUT_USER, userId));
		
		List<TaTpyFavorite> lstResult = TaTpyFavorite.DAO.reqList(0, nbLine, cri);
		return lstResult;
	}
	
	public static void doBuildEntities(List listFav, Integer entTyp) throws Exception {       
		Hashtable<Integer, EntityAbstract> tabEnt 	= new Hashtable<Integer, EntityAbstract>();
		Set<Integer> lstId = new HashSet<Integer>();
		
		for(Object o : listFav) {
			EntityAbstract 	e 		= (EntityAbstract)o;
			Integer			eId		= (Integer)e.req(TaTpyFavorite.ATT_I_ENTITY_ID);
			
			tabEnt.put(eId, e);
			lstId.add(eId);
		}
		
		List lst = reqListByIds (lstId, entTyp);
		
		if (lst!=null)
			for(Object ent : lst) {
				EntityAbstract e = tabEnt.get(((EntityAbstract)ent).reqId());
				e.reqSet(TaTpyFavorite.ATT_O_ENTITY, ent);
			}
	}
	
	public static List reqListByIds(Set<Integer> lstId, Integer entTyp) throws Exception { 
		switch(entTyp) {
		case DefDBExt.ID_TA_NSO_OFFER: 
			return ViNsoOfferSearch.DAO.reqList( 
				Restrictions.in(ViNsoOfferSearch.ATT_I_ID	, lstId)
				);
		case DefDBExt.ID_TA_NSO_POST:
			return ViNsoPostNew.DAO.reqList( 
					Restrictions.in(ViNsoPostNew.ATT_I_ID	, lstId)
					);
		}
		return null;
	}
	public static void doListDel(Session sess, Integer userId) throws Exception {
		Collection	<TaTpyFavorite>  		lst 	= TaTpyFavorite.DAO.reqList(sess,	Restrictions.eq(TaTpyFavorite.ATT_I_AUT_USER, userId));		
		TaTpyFavorite.DAO.doRemove(sess, lst);
	}
}
