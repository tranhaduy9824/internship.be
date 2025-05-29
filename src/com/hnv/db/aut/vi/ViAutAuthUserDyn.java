package com.hnv.db.aut.vi;

import java.io.Serializable;
import java.util.Date;
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
import com.hnv.db.aut.TaAutAuthUser;
import com.hnv.def.DefDBExt;

@Entity
@Table(name = DefDBExt.TA_AUT_AUTH_USER)
public class ViAutAuthUserDyn extends EntityAbstract<ViAutAuthUserDyn>{
		private static final long serialVersionUID = 1000L;
		
		//---------------------------List of Column from DB-----------------------------
		public static final String	COL_I_ID                              =	"I_ID";
		public static final String	COL_I_AUT_USER                        =	"I_Aut_User";
		public static final String	COL_I_AUT_ROLE                        =	"I_Aut_Role";
		public static final String	COL_T_AUT_RIGHT                  	  =	"T_Aut_Right";
		public static final String	COL_I_STATUS                          =	"I_Status";
		public static final String	COL_D_DATE_01                         =	"D_Date_01"; //begin
		public static final String	COL_D_DATE_02                         =	"D_Date_02"; //end

		//---------------------------List of ATTR of class-----------------------------
		public static final String	ATT_I_ID                              =	"I_ID";
		public static final String	ATT_I_AUT_USER                        =	"I_Aut_User";
		public static final String	ATT_I_AUT_ROLE                        =	"I_Aut_Role";
		public static final String	ATT_T_AUT_RIGHT                  	  =	"T_Aut_Right";
		public static final String	ATT_I_STATUS                          =	"I_Status";
		public static final String	ATT_D_DATE_01                         =	"D_Date_01"; //begin
		public static final String	ATT_D_DATE_02                         =	"D_Date_02"; //end
		
		public static final int		STAT_ON								= 1;
		public static final int		STAT_OFF							= 0;

		//-------every entity class must initialize its DAO from here -----------------------------
		private 	static 	final boolean[] 			RIGHTS		= {true, false, false, false, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
		private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

		public		static 	final EntityDAO<ViAutAuthUserDyn> 	DAO;
		static{
			DAO = new EntityDAO<ViAutAuthUserDyn>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), ViAutAuthUserDyn.class,RIGHTS, HISTORY, DefDBExt.TA_AUT_AUTH_USER, DefDBExt.ID_TA_AUT_AUTH_USER);
		}
		
		//-----------------------Class Attributes-------------------------
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name=COL_I_ID, nullable = true)
		private	Integer         I_ID;
	         
		@Column(name=COL_I_AUT_USER, nullable = false)
		private	Integer         I_Aut_User;
	       
		@Column(name=COL_I_AUT_ROLE, nullable = false)
		private	Integer         I_Aut_Role;
		
		@Column(name=COL_I_STATUS, nullable = true)
		private	Integer         I_Status;
		
		@Column(name=COL_D_DATE_01, nullable = true)
		private	Date            D_Date_01;
	    
		@Column(name=COL_D_DATE_02, nullable = true)
		private	Date            D_Date_02;
		
		private ViAutAuthUserDyn(){}
		
		public ViAutAuthUserDyn(Map<String, Object> attrs) throws Exception {
			this.reqSetAttrFromMap(attrs);
			//doInitDBFlag();
		}
		
		public  ViAutAuthUserDyn( TaAutAuthUser a) {
			this.I_ID			= a.reqId();
			this.I_Aut_User 	= a.reqInt(TaAutAuthUser.ATT_I_AUT_USER);
			this.I_Aut_Role		= a.reqInt(TaAutAuthUser.ATT_I_AUT_ROLE);
			this.I_Status		= a.reqInt(TaAutAuthUser.ATT_I_STATUS);
			this.D_Date_01		= a.reqDate(TaAutAuthUser.ATT_D_DATE_01);
			this.D_Date_02		= a.reqDate(TaAutAuthUser.ATT_D_DATE_02);
		}
		
		public ViAutAuthUserDyn(int userId, int roleId, int stat, Date d01, Date d02) throws Exception {
			this.I_Aut_User 	= userId;
			this.I_Aut_Role		= roleId;
			this.I_Status		= stat;
			this.D_Date_01		= d01;
			this.D_Date_02		= d02;
		}
		
		@Override
		public void doMergeWith(ViAutAuthUserDyn e) {
		}
		
		@Override
		public Serializable reqRef() {
			return this.I_ID;
		}
		
		//----------------------------------------------------------------------------------------------------------------------------
		
		
		
}
