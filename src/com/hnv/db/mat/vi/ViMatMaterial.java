package com.hnv.db.mat.vi;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.criterion.Restrictions;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.common.tool.ToolSet;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.mat.TaMatMaterial;
import com.hnv.db.mat.TaMatMaterialDetail;
import com.hnv.db.mat.TaMatPrice;
import com.hnv.db.mat.TaMatUnit;
import com.hnv.db.per.TaPerPerson;
import com.hnv.db.tpy.TaTpyCategoryEntity;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.def.DefDBExt;

/**
 * TaMatMaterial by H&V SAS
 */
@Entity
public class ViMatMaterial extends EntityAbstract<ViMatMaterial> {
	public static int	TYPE_02_SINGLE 		= 1;
	public static int	TYPE_02_BOM 		= 2; //bill of material 
	public static int	TYPE_02_WHOLESALE 	= 3;

	private static final long serialVersionUID = 1L;

	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_T_CODE_01                         =	"T_Code_01";
	public static final String	COL_T_CODE_02                         =	"T_Code_02";
	public static final String	COL_T_CODE_03                         =	"T_Code_03";
	public static final String	COL_T_CODE_04                         =	"T_Code_04";
	public static final String	COL_T_NAME_01                         =	"T_Name_01";
	public static final String	COL_T_NAME_02                         =	"T_Name_02";
	
	public static final String	COL_I_STATUS                          =	"I_Status";
	public static final String	COL_I_TYPE_01                         =	"I_Type_01";
	public static final String	COL_I_TYPE_02                         =	"I_Type_02";
	
	public static final String	COL_I_PER_PERSON_01                   =	"I_Per_Producer";
	public static final String	COL_I_PER_MANAGER                     =	"I_Per_Manager";
	
	public static final String	COL_I_CATEGORY                     	  =	"I_Category";
	public static final String	COL_T_CATEGORY                     	  =	"T_Category";
		
	public static final String	COL_I_UNIT		                      =	"I_Unit";
	public static final String	COL_T_UNIT		                      =	"T_Unit";
	public static final String	COL_F_UNIT_RATIO                      =	"F_Unit_Ratio";
	
	public static final String	COL_I_PRICE		                  	  =	"I_Price";
	public static final String	COL_F_PRICE_01		                  =	"F_Price01";
	public static final String	COL_F_PRICE_02		                  =	"F_Price02";
	public static final String	COL_F_DISCOUNT		                  =	"F_Discount";
	public static final String	COL_T_CURRENCY		                  =	"T_Currency";
	
	public static final String	COL_T_MAN_NAME	                      =	"T_Man_Name";
	public static final String	COL_T_MAN_INFO07	                  =	"T_Man_Info07";
	public static final String	COL_T_MAN_INFO09	                  =	"T_Man_Info09";
	public static final String	COL_T_PROD_NAME		                  =	"T_Prod_Name";
	
	public static final String	COL_D_DATE_01					  	  = "D_Date_01";
	public static final String	COL_D_DATE_02					      = "D_Date_02";

	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_T_CODE_01                         =	"T_Code_01";
	public static final String	ATT_T_CODE_02                         =	"T_Code_02";
	public static final String	ATT_T_CODE_04                         =	"T_Code_04";
	public static final String	ATT_T_NAME_01                         =	"T_Name_01";
	public static final String	ATT_T_NAME_02                         =	"T_Name_02";
	
	public static final String	ATT_I_STATUS                          =	"I_Status";
	
	public static final String	ATT_I_CATEGORY                     	  =	"I_Category";
	public static final String	ATT_T_CATEGORY                     	  =	"T_Category";
	
	public static final String	ATT_I_PER_PRODUCER                    =	"I_Per_Producer";
	public static final String	ATT_I_PER_MANAGER                    =	"I_Per_Manager";
	
	public static final String	ATT_I_UNIT		                      =	"I_Unit";
	public static final String	ATT_T_UNIT                            =	"T_Unit";
	public static final String	ATT_F_UNIT_RATIO                      =	"F_Unit_Ratio";
	
	public static final String	ATT_I_PRICE		                  	  =	"I_Price";
	public static final String	ATT_F_PRICE_01		                  =	"F_Price01";
	public static final String	ATT_F_PRICE_02		                  =	"F_Price02";
	public static final String	ATT_F_DISCOUNT		                  =	"F_Discount";
	public static final String	ATT_T_CURRENCY		                  =	"T_Currency";
	
	public static final String	ATT_T_MAN_NAME	                      =	"T_Man_Name";
	public static final String	ATT_T_PROD_NAME		                  =	"T_Prod_Name";
	
	public static final String	ATT_D_DATE_01					  	  = "D_Date_01";
	public static final String	ATT_D_DATE_02					      = "D_Date_02";
	
	public static final String	ATT_O_UNITS                     	  =	"O_Units";
	public static final String	ATT_O_PRICES                          =	"O_Prices";
	public static final String	ATT_O_BUY_PRICES                      =	"O_Buy_Prices";
	public static final String	ATT_O_PROPERTIES                      =	"O_Properties";
	public static final String	ATT_O_TPY_INFO                     	  =	"O_Tpy_Info";
	public static final String	ATT_O_MANAGER                  	      =	"O_Manager";	
	public static final String	ATT_O_PRODUCER                  	  =	"O_Producer";
	
	public static final String	ATT_O_AVATAR                  	      =	"O_Avatar";
	public static final String	ATT_O_MAN_AVATAR                  	  =	"O_Man_Avatar";
	public static final String	ATT_O_PROD_AVATAR                  	  =	"O_Prod_Avatar";

	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	
	private 	static 	final boolean[]				HISTORY		= {true, true, true}; //add, mod, del

	public		static 	final EntityDAO<ViMatMaterial> 	DAO;
	static{
		DAO = new EntityDAO<ViMatMaterial>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN) , ViMatMaterial.class, RIGHTS,  HISTORY, DefDBExt.TA_MAT_MATERIAL, DefDBExt.ID_TA_MAT_MATERIAL);
	}

	//-----------------------Class Attributs-------------------------
	@Id
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;

	@Column(name=COL_T_CODE_01, nullable = true)
	private	String          T_Code_01;

	@Column(name=COL_T_CODE_02, nullable = true)
	private	String          T_Code_02;
	
	@Column(name=COL_T_CODE_03, nullable = true)
	private	String          T_Code_03;

	@Column(name=COL_T_CODE_04, nullable = true)
	private	String          T_Code_04;

	@Column(name=COL_T_NAME_01, nullable = true)
	private	String          T_Name_01;

	@Column(name=COL_T_NAME_02, nullable = true)
	private	String          T_Name_02;

	@Column(name=COL_I_STATUS, nullable = true)
	private	Integer         I_Status;
		
	
	@Column(name=COL_I_CATEGORY, nullable = true)
	private	Integer         I_Category;
	
	@Column(name=COL_I_UNIT, nullable = true)
	private	Integer          I_Unit;
	
	@Column(name=COL_T_UNIT, nullable = true)
	private	String          T_Unit;

	@Column(name=COL_F_UNIT_RATIO, nullable = true)
	private	Double          F_Unit_Ratio;
	
	@Column(name=COL_I_PRICE, nullable = true)
	private	Double          I_Price;
	
	@Column(name=COL_F_PRICE_01, nullable = true)
	private	Double          F_Price01;
	
	@Column(name=COL_F_PRICE_02, nullable = true)
	private	Double          F_Price02;

	@Column(name=COL_F_DISCOUNT, nullable = true)
	private	Double          F_Discount;
	
	@Column(name=COL_T_CURRENCY, nullable = true)
	private	String          T_Currency;

	@Column(name=COL_I_PER_MANAGER, nullable = true)
	private	Integer         I_Per_Manager;
	
	@Column(name=COL_I_PER_PERSON_01, nullable = true)
	private	Integer         I_Per_Producer;
	
	@Column(name=COL_T_MAN_NAME, nullable = true)
	private	String          T_Man_Name;
	
	@Column(name=COL_T_MAN_INFO07, nullable = true)
	private	String          T_Man_Info07;
	
	@Column(name=COL_T_MAN_INFO09, nullable = true)
	private	String          T_Man_Info09;

	@Column(name=COL_T_PROD_NAME, nullable = true)
	private	String          T_Prod_Name;
	
	@Column(name=COL_D_DATE_01, nullable = true)
	private	Date           D_Date_01;
	
	@Column(name=COL_D_DATE_02, nullable = true)
	private	Date           D_Date_02;

	//-----------------------Transient Variables-------------------------

	@Transient
	private	List<TaMatUnit> 			O_Units;
	
	@Transient
	private	List<TaMatMaterialDetail> 	O_Details;

	@Transient
	private	List<TaMatPrice> 			O_Prices;
		
		
	@Transient
	private	TaPerPerson 				O_Manager;
		
	@Transient
	private	TaPerPerson		        	O_Producer;
	
	@Transient
	private	TaTpyDocument		        O_Avatar;
	
	@Transient
	private	TaTpyDocument		        O_Man_Avatar;
	
	@Transient
	private	TaTpyDocument		        O_Prod_Avatar;
	
	//---------------------Constructeurs-----------------------
	private ViMatMaterial(){}

	public ViMatMaterial(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		//doInitDBFlag();
	}

	public ViMatMaterial(String T_Code_01, String T_Code_02, String T_Code_03, String T_Code_04, String T_Name_01, String T_Name_02, String T_Description_01, String T_Description_02, String T_Description_03, Integer I_Type_01, Integer I_Type_02, Integer I_Status, String T_Comment, Integer I_Unit_Ref, String T_Unit, Integer I_Per_Producer, Integer I_Per_Manager) throws Exception {
		this.reqSetAttr(
				ATT_T_CODE_01              , T_Code_01,
				ATT_T_CODE_02              , T_Code_02,
				ATT_T_CODE_04              , T_Code_04,
				ATT_T_NAME_01              , T_Name_01,
				ATT_T_NAME_02              , T_Name_02,
				ATT_I_STATUS               , I_Status
				);
		//doInitDBFlag();
	}


	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(ViMatMaterial ent) {
		if (ent == this) return;
		this.T_Code_01              = ent.T_Code_01;
		this.T_Code_02              = ent.T_Code_02;
		this.T_Code_04              = ent.T_Code_04;
		this.T_Name_01              = ent.T_Name_01;
		this.T_Name_02              = ent.T_Name_02;
		this.I_Status               = ent.I_Status;
		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;

		ok = (I_ID == ((ViMatMaterial)o).I_ID);
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
		return 	"TaMatMaterial { " +
				"I_ID:"+                      I_ID +"," + 
				"T_Code_01:"+                 T_Code_01 +"," + 
				"T_Code_02:"+                 T_Code_02 +"," + 
				"T_Code_04:"+                 T_Code_04 +"," + 
				"T_Name_01:"+                 T_Name_01 +"," + 
				"T_Name_02:"+                 T_Name_02 +"," + 
				"I_Status:"+                  I_Status +"," + 
				"}";

	}

	public Integer reqID(){
		return this.I_ID;
	}


	//-------------------------------------------------------------------------------------------------------------
	public static final String SQL_REQ_LIST_DYN_SELECT = "SELECT "
			+		" mat."	+ TaMatMaterial.COL_I_ID			+ " AS "	+ COL_I_ID				+ ","
			+		" mat."	+ TaMatMaterial.COL_T_NAME_01		+ " AS "	+ COL_T_NAME_01			+ ","
			+		" mat."	+ TaMatMaterial.COL_T_NAME_02		+ " AS "	+ COL_T_NAME_02			+ ","
			+		" mat."	+ TaMatMaterial.COL_T_CODE_01		+ " AS "	+ COL_T_CODE_01			+ ","
			+		" mat."	+ TaMatMaterial.COL_T_CODE_02		+ " AS "	+ COL_T_CODE_02			+ ","
			+		" mat."	+ TaMatMaterial.COL_T_CODE_04		+ " AS "	+ COL_T_CODE_04			+ ","
			+		" mat."	+ TaMatMaterial.COL_I_PER_MANAGER	+ " AS "	+ COL_I_PER_MANAGER		+ ","
			+		" mat."	+ TaMatMaterial.COL_I_PER_PERSON_01	+ " AS "	+ COL_I_PER_PERSON_01	+ ","
			+		" mat."	+ TaMatMaterial.COL_I_STATUS_01		+ " AS "	+ COL_I_STATUS			+ ","
			+		" mat."	+ TaMatMaterial.COL_D_DATE_01		+ " AS "	+ COL_D_DATE_01			+ ","
			+		" mat."	+ TaMatMaterial.COL_D_DATE_02		+ " AS "	+ COL_D_DATE_02			+ ","
			
			+		" man."	+ TaPerPerson.COL_T_NAME_01			+ " AS "	+ COL_T_MAN_NAME		+ ","
			+		" man."	+ TaPerPerson.COL_T_INFO_01			+ " AS "	+ COL_T_MAN_INFO07		+ ","
			+		" man."	+ TaPerPerson.COL_T_INFO_02			+ " AS "	+ COL_T_MAN_INFO09		+ ","
			+		" prod."+ TaPerPerson.COL_T_NAME_01			+ " AS "	+ COL_T_PROD_NAME		+ ","
			
//			+		" cat."	+ TaTpyCategoryEntity.COL_I_CATEGORY+ " AS "	+ COL_I_CATEGORY		+ ","
			+		" null "									+ " AS "	+ COL_I_CATEGORY		+ ","
			
			+		" p."	+ TaMatPrice.COL_I_ID				+ " AS "	+ COL_I_PRICE			+ ","
			+		" p."	+ TaMatPrice.COL_I_MAT_UNIT			+ " AS "	+ COL_I_UNIT			+ ","
			+		" p."	+ TaMatPrice.COL_T_INFO_01			+ " AS "	+ COL_T_UNIT			+ ","
			+		" p."	+ TaMatPrice.COL_T_INFO_02			+ " AS "	+ COL_T_CURRENCY		+ ","

			+		" p."	+ TaMatPrice.COL_F_VAL_00			+ " AS "	+ COL_F_UNIT_RATIO		+ ","
			+		" p."	+ TaMatPrice.COL_F_VAL_01			+ " AS "	+ COL_F_PRICE_01		+ ","
			+		" p."	+ TaMatPrice.COL_F_VAL_02			+ " AS "	+ COL_F_PRICE_02		+ ","
			+		" p."	+ TaMatPrice.COL_F_VAL_03			+ " AS "	+ COL_F_DISCOUNT		;	

	static final String SQL_REQ_LIST_DYN_COUNT 		= "SELECT count(DISTINCT(mat."+ TaMatMaterial.COL_I_ID		+")) as I_Nb";

	static final String SQL_REQ_LIST_FROM			= " FROM "	+ DefDBExt.TA_MAT_MATERIAL						+ " mat";

	static final String SQL_REQ_LIST_JOIN_CAT		= " INNER JOIN " 	+ DefDBExt.TA_TPY_CATEGORY_ENTITY 		+ " cat"
													+ " ON  cat." 		+ TaTpyCategoryEntity.COL_I_ENTITY_TYPE + " = "	+ DefDBExt.ID_TA_MAT_MATERIAL	
													+ " %s " //--- SQL_REQ_WHERE_CAT
													+ " AND cat."		+ TaTpyCategoryEntity.COL_I_ID 			+ " = (Select "		
																												+ TaTpyCategoryEntity.COL_I_ID 		+ " From "	+  DefDBExt.TA_TPY_CATEGORY_ENTITY
																												+ " tmp where tmp."					+ TaTpyCategoryEntity.COL_I_ENTITY_ID + " = mat." + TaMatMaterial	.COL_I_ID		
																												+ " %s "
																												+ " limit 1)";
	
	static final String SQL_REQ_LIST_JOIN_CAT_02	= " AND mat." 										+ TaMatMaterial	.COL_I_ID 			+ " in (Select distinct("		
													+ TaTpyCategoryEntity.COL_I_ENTITY_ID 				+ ") From "							+ DefDBExt.TA_TPY_CATEGORY_ENTITY + " cat where "	
													+ "cat." + TaTpyCategoryEntity.COL_I_ENTITY_TYPE 	+ " = "								+ DefDBExt.ID_TA_MAT_MATERIAL
													+ " %s )";
	
	
	static final String SQL_REQ_LIST_JOIN_PRICING	= " INNER JOIN "+ DefDBExt.TA_MAT_PRICE	+ " p"	
			+	" ON p."		+ TaMatPrice.COL_F_VAL_02 								+ " IS NOT NULL"	
			+	" AND p."		+ TaMatPrice.COL_I_STATUS 				+ " = "				+ TaMatPrice	.STAT_ACTIV
			+	" AND p."		+ TaMatPrice.COL_I_ID 					+ " = (Select "		
																			+ TaMatPrice.COL_I_ID 		+ " From "	+  DefDBExt.TA_MAT_PRICE 
																			+ " tmp where tmp."			+ TaMatPrice.COL_I_MAT_MATERIAL + " = mat." + TaMatMaterial	.COL_I_ID		
																			+ " %s "
																			+ " limit 1)";
	
	static final String SQL_REQ_LIST_JOIN_PRICING_02	= " %s mat." 		+ TaMatMaterial	.COL_I_ID	+ " in (Select distinct("
			+ 	TaMatPrice.COL_I_MAT_MATERIAL + ") From "					+ DefDBExt.TA_MAT_PRICE		+ " p where"	
			+	"     p."		+ TaMatPrice.COL_F_VAL_02 					+ " IS NOT NULL"	
			+	" AND p."		+ TaMatPrice.COL_I_STATUS 					+ " = "						+ TaMatPrice	.STAT_ACTIV
			+   " %s )";


	static final String SQL_REQ_LIST_JOIN_MAN		= " INNER JOIN " 	+ DefDBExt.TA_PER_PERSON 		+" man"
			+	" ON mat." 		+ TaMatMaterial.COL_I_PER_MANAGER		+ " = man." 	+ TaPerPerson.COL_I_ID;
	
	static final String SQL_REQ_LIST_JOIN_PROD		= " LEFT JOIN " 	+ DefDBExt.TA_PER_PERSON 		+" prod"
			+	" ON mat." 		+ TaMatMaterial.COL_I_PER_PERSON_01		+ " = prod." 	+ TaPerPerson.COL_I_ID ;
	
	
	static final String SQL_REQ_WHERE				=  " WHERE mat."	+ TaMatMaterial.COL_I_ID				+ " >0 " 	;
	
	static final String SQL_REQ_WHERE_MAT 			=  " AND mat."		+ TaMatMaterial.COL_I_ID				+ " = %d "	;
	static final String SQL_REQ_WHERE_MAN			=  " AND mat."		+ TaMatMaterial.COL_I_PER_MANAGER		+ " = %d " 	;
	static final String SQL_REQ_WHERE_CAT 			=  " AND cat."		+ TaTpyCategoryEntity.COL_I_TPY_CATEGORY+ " in %s "	;
	static final String SQL_REQ_WHERE_PROD 			=  " AND mat."		+ TaMatMaterial.COL_I_PER_PERSON_01		+ " = %d "	;

	static final String SQL_REQ_WHERE_TYP01 		=  " AND mat."		+ TaMatMaterial.COL_I_TYPE_01			+ " = %d "	;
	static final String SQL_REQ_WHERE_TYP02 		=  " AND mat."		+ TaMatMaterial.COL_I_TYPE_02			+ " = %d "	;
	static final String SQL_REQ_WHERE_STATUS 		=  " AND mat."		+ TaMatMaterial.COL_I_STATUS_01			+ " = %d "	;

	static final String SQL_WHERE_SEARCH 			= " lower(mat."		+ TaMatMaterial.COL_T_CODE_01 + ") like lower('%s') OR lower(mat." + TaMatMaterial.COL_T_NAME_01 + ") like lower('%s')";

//	static final String SQL_REQ_LIST_JOIN_FAVORTIE	= " INNER JOIN " 	+ DefDBExt.TA_TPY_FAVORITE 		+" fav"
//			+	" ON  mat." 	+ TaMatMaterial.COL_I_ID				+ " = fav." 	+ TaTpyFavorite.COL_I_ENTITY_ID	
//			+	" AND fav." 	+ TaTpyFavorite.COL_I_ENTITY_TYPE		+ " = " 		+ DefDBExt.ID_TA_MAT_MATERIAL
//			+	" AND fav." 	+ TaTpyFavorite.ATT_I_TYPE				+ " = " 		+ TaTpyFavorite.FAVORITE_TYPE_LIKE
//			+	" AND fav." 	+ TaTpyFavorite.COL_I_AUT_USER			+ " = %d" 		;
//	
	static final String SQL_ORDER					= " ORDER BY %s %s";
	

	
	private static String reqRestriction(Set<String> searchKey) {	
		if (searchKey==null) return null;
		
		String restr = "";
		String orT	 =  "";	
		for (String s: searchKey){	
			if (s.equals("%")) continue;
			restr = restr + orT + String.format(SQL_WHERE_SEARCH, s, s);
			orT	  = " AND ";	
		}
		if (restr.length()==0) return null;
		else restr = "( " + restr + " )";
		return restr;
	}

	//---------------------------------------------------------------------------------------------------------------------------
	private static String reqSQL(String sql, Integer manId,  Integer producerId, Set<Integer> catIds, Set<String> searchKey, String orderCol, String orderDir, Integer typ01, Integer typ02, Integer status, Double priceMin, Double priceMax, Integer uIdFavorite, boolean forHome) {
		sql += SQL_REQ_LIST_FROM ;
		
		if (priceMin!=null && priceMax!=null) {
			sql += String.format(SQL_REQ_LIST_JOIN_PRICING, " AND tmp." + TaMatPrice.COL_F_VAL_02 + " between " + priceMin + " and " + priceMax + " ");
		}else {
			sql += String.format(SQL_REQ_LIST_JOIN_PRICING, "" );
		}
		
		if(forHome) sql += 	" AND p."		+ TaMatPrice.COL_F_VAL_02 				+ " > 0";

//		sql += SQL_REQ_WHERE;
		
		//-----ad condition -----
		if (manId!=null)
			sql +=  String.format(SQL_REQ_WHERE_MAN, manId) ;

		if(producerId != null)
			sql += String.format(SQL_REQ_WHERE_PROD, producerId); 
		
		if(typ01 != null)
			sql += String.format(SQL_REQ_WHERE_TYP01, typ01); 
		
		if(typ02 != null)
			sql += String.format(SQL_REQ_WHERE_TYP02, typ02); 
		
		if(status != null)
			sql += String.format(SQL_REQ_WHERE_STATUS, status); 
		
		String 					restriction 			= reqRestriction(searchKey);
		if(restriction != null) {
			sql += " AND " + restriction;
		}
		
		
//		String catCond = "";		
//		if(catIds != null) {
//			String sCatIds = catIds.toString();
//			sCatIds = sCatIds.replace("[", "(").replace("]", ")"); 
//			catCond = String.format(SQL_REQ_WHERE_CAT, sCatIds);
//		}
//			
//
//		sql = sql + String.format(SQL_REQ_LIST_JOIN_CAT, catCond, catCond);
		
		String catCond = "";		
		if(catIds != null) {
			String sCatIds = catIds.toString();
			sCatIds = sCatIds.replace("[", "(").replace("]", ")"); 
			catCond = String.format(SQL_REQ_WHERE_CAT, sCatIds);
		}
			

		if (catCond.length()>0) sql = sql + String.format(SQL_REQ_LIST_JOIN_CAT_02, catCond);
		
		
		
		//----join per ------------------
		sql = sql + SQL_REQ_LIST_JOIN_MAN + SQL_REQ_LIST_JOIN_PROD;
		
//		if (uIdFavorite!=null)
//			sql = sql + String.format(SQL_REQ_LIST_JOIN_FAVORTIE, uIdFavorite); 
		
		//---very slow with SQL_REQ_LIST_JOIN_CAT
		if (orderCol!=null && orderDir!=null )
			sql += String.format(SQL_ORDER, orderCol, orderDir);
		
		return sql;
	}
	
	private static String reqSQLCount(String sql, Integer manId,  Integer producerId, Set<Integer> catIds, Set<String> searchKey, String orderCol, String orderDir, Integer typ01, Integer typ02, Integer status, Double priceMin, Double priceMax, Integer uIdFavorite, boolean forHome) {
		sql += SQL_REQ_LIST_FROM ;
		
		if (priceMin!=null && priceMax!=null) {
			sql += String.format(SQL_REQ_LIST_JOIN_PRICING, " AND tmp." + TaMatPrice.COL_F_VAL_02 + " between " + priceMin + " and " + priceMax + " ");
		}else {
			sql += String.format(SQL_REQ_LIST_JOIN_PRICING, "" );
		}
		
		if(forHome) sql += 	" AND p."		+ TaMatPrice.COL_F_VAL_02 				+ " > 0";
		
//		if (priceMin!=null && priceMax!=null) {
//			sql += String.format(SQL_REQ_LIST_JOIN_PRICING_02, "WHERE", " AND p." + TaMatPrice.COL_F_PRICE_02 + " between " + priceMin + " and " + priceMax + " ");
//		}else {
//			sql += String.format(SQL_REQ_LIST_JOIN_PRICING_02, "WHERE", "" );
//		}

//		sql += SQL_REQ_WHERE;
		
		//-----ad condition -----
		if (manId!=null)
			sql +=  String.format(SQL_REQ_WHERE_MAN, manId) ;

		if(producerId != null)
			sql += String.format(SQL_REQ_WHERE_PROD, producerId); 
		
		if(typ01 != null)
			sql += String.format(SQL_REQ_WHERE_TYP01, typ01); 
		
		if(typ02 != null)
			sql += String.format(SQL_REQ_WHERE_TYP02, typ02); 
		
		if(status != null)
			sql += String.format(SQL_REQ_WHERE_STATUS, status); 
		
		String 					restriction 			= reqRestriction(searchKey);
		if(restriction != null) {
			sql += " AND " + restriction;
		}
		
		
//		String catCond = "";		
//		if(catIds != null) {
//			String sCatIds = catIds.toString();
//			sCatIds = sCatIds.replace("[", "(").replace("]", ")"); 
//			catCond = String.format(SQL_REQ_WHERE_CAT, sCatIds);
//		}
//
//		sql = sql + String.format(SQL_REQ_LIST_JOIN_CAT, catCond, catCond);
		
		String catCond = "";		
		if(catIds != null && catIds.size()>0) {
			String sCatIds = catIds.toString();
			sCatIds = sCatIds.replace("[", "(").replace("]", ")"); 
			catCond = String.format(SQL_REQ_WHERE_CAT, sCatIds);
		}
			

		if (catCond.length()>0) sql = sql + String.format(SQL_REQ_LIST_JOIN_CAT_02, catCond);
		
		//----join per ------------------
//		sql = sql + SQL_REQ_LIST_JOIN_MAN;
		
		sql = sql + SQL_REQ_LIST_JOIN_MAN + SQL_REQ_LIST_JOIN_PROD;
		
//		if (uIdFavorite!=null)
//			sql = sql + String.format(SQL_REQ_LIST_JOIN_FAVORTIE, uIdFavorite); 
		
		return sql;
	}
	
	public static List<ViMatMaterial> reqListMatFilter(Integer manId,  Set<Integer> catIds, Integer producerId, int begin, int end, Set<String> searchKey, String orderCol, String orderDir, Integer typ01, Integer typ02, Integer status, Double priceMin, Double priceMax, Integer uIdFavorite, boolean buildMatInfo, boolean forHome) throws Exception {
		List<ViMatMaterial> result = null;
		String sql =  reqSQL (SQL_REQ_LIST_DYN_SELECT, manId, producerId, catIds, searchKey, orderCol, orderDir, typ01, typ02, status,  priceMin, priceMax, uIdFavorite, forHome);
		result = ViMatMaterial.DAO.reqList(begin, end, sql);
		
		if (buildMatInfo) doBuildImageLstMat(result);
		return result;
	}

	public static int reqCountMatFilter(Integer manId, Set<Integer> catIds,Integer producerId, Set<String> searchKey, Integer typ01, Integer typ02,  Integer status, Double priceMin, Double priceMax, Integer uIdFavorite, boolean forHome) throws Exception {
		String sql =  reqSQLCount (SQL_REQ_LIST_DYN_COUNT, manId, producerId,  catIds, searchKey, null, null, typ01, typ02, status,  priceMin, priceMax, uIdFavorite, forHome);		
		int count = ViMatMaterial.DAO.reqCount(sql).intValue();
//		List<ViMatCount> count = ViMatCount.DAO.reqList(sql);
//		return (int)count.get(0).req( ViMatCount.ATT_I_NB);
		return count;
	}

	public static int reqCountMatFilter(Integer manId, Set<Integer> catIds, Integer producerId, Integer typ01, Integer typ02, Integer status, Double priceMin, Double priceMax, Integer uIdFavorite, boolean forHome) throws Exception {
		String sql = reqSQLCount (SQL_REQ_LIST_DYN_COUNT, manId,producerId,  catIds, null, null, null, typ01, typ02, status,  priceMin, priceMax, uIdFavorite, forHome);
		int count = ViMatMaterial.DAO.reqCount(sql).intValue();
		return count;
	}
	
	//---------------------------------------------------------------------------------------------------------------------------
	public static List<ViMatMaterial> reqLstByProducerGrid(Integer status, Integer begin, Integer nbRes, Integer producerId) throws Exception {
		List<ViMatMaterial> list = new ArrayList<ViMatMaterial>();
		String sql = reqLstByProducerGridSql(status, producerId);
		list = ViMatMaterial.DAO.reqList(begin, nbRes, sql);
		attachRelatedTablesToList(list, producerId);
		
		return list;
	}
	
	private static String reqLstByProducerGridSql(Integer status, Integer producerId) throws Exception {
		String sql = SQL_REQ_LIST_DYN_SELECT;
		sql += SQL_REQ_LIST_FROM;
		sql += " WHERE true ";
		
		if(status != null)
			sql += String.format(SQL_REQ_WHERE_STATUS, status);
		
		if(producerId != null)
			sql += String.format(SQL_REQ_WHERE_PROD, producerId);
		
		//searchKey = %
		String restriction	= reqRestriction("%");
		
		if(restriction != null) {
			sql += " AND " + restriction;
		}	
		
		return sql;
	}
	
	private static String reqRestriction(String searchKey) {			
		String restr = "";
		
		restr = String.format(SQL_WHERE_SEARCH, searchKey, searchKey);

		if (restr.length()==0) return null;
		else restr = "(" + restr + ")";
		return restr;
	}

	private static void attachRelatedTablesToList(List<ViMatMaterial> list, Integer producerId) throws Exception {
		if(list != null && list.size() > 0){
			Set<Integer> materialCollection = new HashSet<Integer>();
			
			for(ViMatMaterial material : list){
				materialCollection.add(material.reqID());
			}
			
			// Variables help map each Product with its own Properties
			Map<Integer,TaTpyDocument> 			mapDocs 	= new HashMap<Integer,TaTpyDocument>();
			Map<Integer, List<TaMatPrice>> 	mapSor      = new HashMap<Integer, List<TaMatPrice>>();
			Map<Integer, List<TaMatUnit>> 	    mapUnits    = new HashMap<Integer, List<TaMatUnit>>();
			Map<Integer, TaPerPerson> 			mapProducer = new HashMap<Integer, TaPerPerson>();		
			
			// Get Properties
			List<TaMatPrice>       	lstPrices   = TaMatPrice	.DAO.reqList_In(TaMatPrice.ATT_I_MAT_MATERIAL, materialCollection, Restrictions.eq(TaMatPrice.ATT_I_STATUS, TaMatPrice.STAT_ACTIV));
//			List<TaMatUnit>         lstUnit    	= TaMatUnit		.DAO.reqList_In(TaMatUnit.ATT_I_MAT_MATERIAL, materialCollection, Restrictions.eq(TaMatUnit.ATT_I_STATUS, 1));
			List<TaPerPerson> 		lstProducer	= TaPerPerson	.DAO.reqList(Restrictions.eq(TaPerPerson.ATT_I_ID, producerId));
			List<TaTpyDocument> 	lstDocs 	= TaTpyDocument	.DAO.reqList_In(
					TaTpyDocument	.ATT_I_ENTITY_ID, materialCollection, 
					Restrictions.and(
							Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_TYPE	, DefDBExt.ID_TA_MAT_MATERIAL),
							Restrictions.eq(TaTpyDocument.ATT_I_TYPE_01		, TaTpyDocument.TYPE_01_FILE_MEDIA),
							Restrictions.eq(TaTpyDocument.ATT_I_TYPE_02		, TaTpyDocument.TYPE_02_FILE_IMG_AVATAR)
					) );
			
			// Map Properties with their appropriated Product
			if(lstPrices != null && lstPrices.size()>0){
				for(int i = 0; i < lstPrices.size(); i++){
					TaMatPrice s = lstPrices.get(i);
					Integer matId = (Integer) s.req(TaMatPrice.ATT_I_MAT_MATERIAL);
					if(mapSor.get(matId) == null){
						List<TaMatPrice> sElement = new ArrayList<TaMatPrice>();
						sElement.add(s);
						mapSor.put(matId, sElement);
					} else {
						mapSor.get(matId).add(s);
					}
				}
			}
			
//			if(lstUnit != null && lstUnit.size()>0){
//				for(int i = 0; i < lstUnit.size(); i++){
//					TaMatUnit s = lstUnit.get(i);
//					Integer matId = (Integer) s.req(TaMatUnit.ATT_I_MAT_MATERIAL);
//					if(mapUnits.get(matId) == null){
//						List<TaMatUnit> sElement = new ArrayList<TaMatUnit>();
//						sElement.add(s);
//						mapUnits.put(matId, sElement);
//					} else {
//						mapUnits.get(matId).add(s);
//					}
//				}
//			}
			
			if(lstProducer != null && lstProducer.size()>0){
				for(ViMatMaterial material : list){
					TaPerPerson p = lstProducer.get(0);
					mapProducer.put(material.reqID(), p);
				}
			}
			
			if(lstDocs != null && lstDocs.size()>0){
				for(int i = 0; i < lstDocs.size(); i++){
					TaTpyDocument 	s 		= lstDocs.get(i);
					Integer 		infoId 	= (Integer) s.req(TaTpyDocument.ATT_I_ENTITY_ID);
					mapDocs.put(infoId, s);
				}
			} 
			for(ViMatMaterial mat : list){
				mat.reqSet(ViMatMaterial.ATT_O_PRICES,   mapSor.get(mat.reqID()));
				mat.reqSet(ViMatMaterial.ATT_O_UNITS,  mapUnits.get(mat.reqID()));
				mat.reqSet(ViMatMaterial.ATT_O_PRODUCER,  mapProducer.get(mat.reqID()));
				mat.reqSet(ViMatMaterial.ATT_O_AVATAR , mapDocs.get(mat.reqID()));
			}
			
		}
	}
	
	public static Integer reqLstByProducerGridCount(Integer status, Integer producerId) throws Exception {
		String sql = reqLstByProducerGridSqlCount(status, producerId);			
		Integer count = ViMatMaterial.DAO.reqCount(sql).intValue();
			
		return count;
	}
	
	private static String reqLstByProducerGridSqlCount(Integer status, Integer productId) throws Exception {
		String sql	= SQL_REQ_LIST_DYN_COUNT;
		
		sql += SQL_REQ_LIST_FROM;

		sql += " WHERE true ";
			
		if(status != null && productId != 0)
			sql += String.format(SQL_REQ_WHERE_STATUS, status);
		
		if(productId != null )
			sql += String.format(SQL_REQ_WHERE_PROD, productId);
		
		//searchKey = %
		String restriction	= reqRestriction("%");
		
		if(restriction != null) {
			sql += " AND " + restriction;
		}
		
		return sql;
	}
	
	//-----------------------------------------------------------------------------------------------------------------------------------
	
	private static void doBuildImageLstMat(List<ViMatMaterial> list) throws Exception {
		if(list != null && list.size() > 0){
			Set<Integer> materialCollection = ToolSet.reqSetInt(list, ViMatMaterial.ATT_I_ID);
			Set<Integer> manIdCollection 	= ToolSet.reqSetInt(list, ViMatMaterial.ATT_I_PER_MANAGER);
			Set<Integer> prodIdCollection 	= ToolSet.reqSetInt(list, ViMatMaterial.ATT_I_PER_PRODUCER);
			manIdCollection.addAll(prodIdCollection);
					
			// Variables help map each Product with its own Properties
			Map<Integer, TaTpyDocument> 		mapMatDocs 	= new HashMap<Integer,TaTpyDocument>();
			Map<Integer, TaTpyDocument> 		mapPerDocs 	= new HashMap<Integer,TaTpyDocument>();
			
			// Get Properties
			List<TaTpyDocument> 	 lstMatDocs = TaTpyDocument	.DAO.reqList_In	(
					TaTpyDocument.ATT_I_ENTITY_ID	, materialCollection, 
					Restrictions.and(
							Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_TYPE	, DefDBExt.ID_TA_MAT_MATERIAL),
							Restrictions.eq(TaTpyDocument.ATT_I_TYPE_01		, TaTpyDocument.TYPE_01_FILE_MEDIA), 
							Restrictions.eq(TaTpyDocument.ATT_I_TYPE_02		, TaTpyDocument.TYPE_02_FILE_IMG_AVATAR)
							));
			
			List<TaTpyDocument> 	 lstPerDocs = TaTpyDocument	.DAO.reqList_In	(
					TaTpyDocument.ATT_I_ENTITY_ID	, manIdCollection, 
					Restrictions.and(
							Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_TYPE	, DefDBExt.ID_TA_PER_PERSON),
							Restrictions.eq(TaTpyDocument.ATT_I_TYPE_01		, TaTpyDocument.TYPE_01_FILE_MEDIA),
							Restrictions.eq(TaTpyDocument.ATT_I_TYPE_02		, TaTpyDocument.TYPE_02_FILE_IMG)
							));
			
					
			if(lstMatDocs != null && lstMatDocs.size()>0){
				for(int i = 0; i < lstMatDocs.size(); i++){
					TaTpyDocument 	s 		= lstMatDocs.get(i);
					Integer 		infoId 	= (Integer) s.req(TaTpyDocument.ATT_I_ENTITY_ID);
					s.reqSet(TaTpyDocument.ATT_T_INFO_02, null);
					mapMatDocs.put(infoId, s);
				}
			} 
			
			if(lstPerDocs != null && lstPerDocs.size()>0){
				for(int i = 0; i < lstPerDocs.size(); i++){
					TaTpyDocument 	s 		= lstPerDocs.get(i);
					Integer 		infoId 	= (Integer) s.req(TaTpyDocument.ATT_I_ENTITY_ID);
					s.reqSet(TaTpyDocument.ATT_T_INFO_02, null);
					mapPerDocs.put(infoId, s);
				}
			} 
			
			for(ViMatMaterial mat : list){
				mat.reqSet(ViMatMaterial.ATT_O_AVATAR 		, 	mapMatDocs	.get(mat.reqID()));
				
				Integer prodId = 	mat.reqInt(mat, ViMatMaterial.ATT_I_PER_PRODUCER);
				if (prodId !=null) 	mat.reqSet(ViMatMaterial.ATT_O_PROD_AVATAR	,  	mapPerDocs	.get(prodId));
				
				Integer manId = 	mat.reqInt(mat, ViMatMaterial.ATT_I_PER_MANAGER);
				if (manId !=null) 	mat.reqSet(ViMatMaterial.ATT_O_MAN_AVATAR	,  	mapPerDocs	.get(manId));
			}
			
		}
	}
}


