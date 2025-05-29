package com.hnv.db.tpy.vi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.nso.TaNsoOffer;
import com.hnv.db.tpy.TaTpyCategory;
import com.hnv.db.tpy.TaTpyCategoryEntity;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.def.DefDBExt;

@Entity
public class ViTpyCategoryCount extends EntityAbstract<ViTpyCategoryCount> {
	
	
	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_CAT                             =	"I_Tpy_Category";
	
	public static final String	COL_I_COUNT                           =	"I_Count";
	
	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_CAT                             = "I_Tpy_Category";
	
	public static final String	ATT_I_COUNT                           =	"I_Count";
	
	
	//-------every entity class must initialize its DAO from here -----------------------------
		private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
		private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

		public		static 	final EntityDAO<ViTpyCategoryCount> 	DAO;
		static{
			DAO = new EntityDAO<ViTpyCategoryCount>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), ViTpyCategoryCount.class,RIGHTS, HISTORY, DefDBExt.TA_TPY_CATEGORY_ENTITY, DefDBExt.ID_TA_TPY_CATEGORY_ENTITY);
//			try {
//				doLstGen();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		
		
		//-----------------------Class Attributs-------------------------
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name=COL_I_CAT, nullable = false)
		private	Integer         I_Tpy_Category;
		
		@Column(name=COL_I_COUNT, nullable = false)
		private	Integer         I_Count;

		
		//-----------------------Transient Variables-------------------------
		
		
		//---------------------Constructeurs-----------------------
		public ViTpyCategoryCount(){}

		public ViTpyCategoryCount(Map<String, Object> attrs) throws Exception {
			this.reqSetAttrFromMap(attrs);
			// doInitDBFlag();
		}
		

		//---------------------EntityInterface-----------------------
		@Override
		public Serializable reqRef() {
			return this.I_Tpy_Category;
		}

		@Override
		public void doMergeWith(ViTpyCategoryCount ent) {
			if (ent == this) return;

			this.I_Count          = ent.I_Count;
		}
		
		public static void doBuildCount(List listEnts, Integer entTyp, Integer stat01, Integer stat02) {
			if(entTyp == null) return;
			
			String tabName = "";
			switch(entTyp) {
			case DefDBExt.ID_TA_MAT_MATERIAL: tabName = DefDBExt.TA_MAT_MATERIAL; break;
			case DefDBExt.ID_TA_NSO_OFFER	: tabName = DefDBExt.TA_NSO_OFFER; break;
			}
			if (tabName.length()==0) return;
			
			
			String sql = "Select I_Tpy_Category, COUNT(*) I_Count" 					+ " "
					   + "from Ta_Tpy_Category_Entity e" 							+ " "
					   + "left join "+ tabName+ " o on e.I_Entity_ID = o.I_ID" 		+ " "
					   + "where e.I_Entity_Type = " + entTyp 						+ " ";
			
			if(stat01 != null) {
				sql += "and o.I_Status_01 = " 	+ stat01 + " ";
			}
			if(stat02 != null) {
				sql += "and o.I_Status_02 = " 	+ stat02 + " ";
			}
			
			sql += "Group by I_Tpy_Category";
			
			Hashtable<Integer, EntityAbstract> tabEnt 	= new Hashtable<Integer, EntityAbstract>();
			for (Object o: listEnts) {
				EntityAbstract 	e 		= (EntityAbstract)o;

				tabEnt	.put((Integer)e.reqRef(), e);
			}
			
			try {
				List<ViTpyCategoryCount> list = ViTpyCategoryCount.DAO.reqList(sql);
				
				if(list == null || list.size() <= 0) {
					return;
				}
				
				for (ViTpyCategoryCount d : list) {
					if(d == null) continue;
					Integer eCat 	= (Integer) d.req(ViTpyCategoryCount.ATT_I_CAT);
					Integer eCount 	= (Integer) d.req(ViTpyCategoryCount.ATT_I_COUNT);
					EntityAbstract ent = tabEnt.get(eCat);
					
					ent.reqSet(TaTpyCategory.ATT_O_COUNT, eCount);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

}
