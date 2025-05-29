package com.hnv.db.nso;

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
import org.hibernate.criterion.Restrictions;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.common.tool.ToolDBEntity;
import com.hnv.common.tool.ToolSet;
import com.hnv.data.json.JSONObject;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.aut.TaAutUser;
import com.hnv.db.aut.vi.ViAutUserMember;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.def.DefDBExt;		

/**
* TaNsoGroup by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_NSO_GROUP )
public class TaNsoGroup extends EntityAbstract<TaNsoGroup> {

	private static final long serialVersionUID 		= 1L;
	private static final int  ENT_TYP				= DefDBExt.ID_TA_NSO_GROUP;
	
	public static final int		STAT_01_NEW 		= 0;
	public static final int		STAT_01_ACTIVE 		= 1;
	public static final int		STAT_01_REVIEW 		= 5;
	public static final int		STAT_01_DELETED 	= 10;
	
	public static final int		STAT_02_YES			= 1;
	public static final int		STAT_02_NO 			= 0;
	public static final int		STAT_02_WAIT		= 5;
	
	public static final int TYP_02_PRIVATE			= 401;
	public static final int TYP_02_PUBLIC			= 402;

	public static final int TYP_01_MEETING  		= 100;
	public static final int TYP_01_WORK_TASK  		= 200;
	public static final int TYP_01_WORK  			= 300;
	public static final int TYP_01_ROOM_MULTI		= 201;
	public static final int TYP_01_ROOM_SINGLE		= 200;
	public static final int TYP_01_EMAIL  			= 500;
	public static final int TYP_01_PLANNING  		= 600;
	public static final int TYP_01_PLAN_WORK  		= 900;
	
	public static final int TYP_02_MEET_OUT			= 100;
	
	public static final int STAT_EMAIL_NEW			= 1;
	public static final int STAT_EMAIL_VALIDATE		= 2;
	public static final int STAT_EMAIL_WAIT_DEL  	= 3;
	
	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_T_REF                             =	"T_Ref";
	public static final String	COL_T_NAME                            =	"T_Name";
	
	public static final String	COL_D_DATE_01                         =	"D_Date_01";
	public static final String	COL_D_DATE_02                         =	"D_Date_02";
	public static final String	COL_D_DATE_03                         =	"D_Date_03";
	public static final String	COL_D_DATE_04                         =	"D_Date_04";
	public static final String	COL_I_STATUS_01                       =	"I_Status_01";
	public static final String	COL_I_STATUS_02                       =	"I_Status_02";
	public static final String	COL_I_STATUS_03                       =	"I_Status_03";
	public static final String	COL_I_TYPE_01                         =	"I_Type_01";
	public static final String	COL_I_TYPE_02                         =	"I_Type_02";
	public static final String	COL_I_TYPE_03                         =	"I_Type_03";
	public static final String	COL_T_INFO_01                         =	"T_Info_01";
	public static final String	COL_T_INFO_02                         =	"T_Info_02";
	public static final String	COL_T_INFO_03                         =	"T_Info_03";
	public static final String	COL_T_INFO_04                         =	"T_Info_04";
	public static final String	COL_T_INFO_05                         =	"T_Info_05";
	public static final String	COL_T_VAL_01                          =	"T_Val_01";
	public static final String	COL_T_VAL_02                          =	"T_Val_02";
	
	public static final String	COL_F_VAL_01                          =	"F_Val_01";
	public static final String	COL_F_VAL_02                          =	"F_Val_02";
	public static final String	COL_F_VAL_03                          =	"F_Val_03";
	public static final String	COL_F_VAL_04                          =	"F_Val_04";
	public static final String	COL_F_VAL_05                          =	"F_Val_05";
	public static final String	COL_I_PARENT                          =	"I_Parent";
	public static final String	COL_T_CODE_01                         =	"T_Code_01";
	public static final String	COL_T_CODE_02                         =	"T_Code_02";
	
	public static final String	COL_I_AUT_USER                        =	"I_Aut_User";
	public static final String	COL_I_PER_MANAGER                     =	"I_Per_Manager";

	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	
	public static final String	ATT_T_REF                             =	"T_Ref";
	public static final String	ATT_T_NAME                            =	"T_Name";
	public static final String	ATT_I_PARENT                          =	"I_Parent";
	public static final String	ATT_D_DATE_01                         =	"D_Date_01";
	public static final String	ATT_D_DATE_02                         =	"D_Date_02";
	public static final String	ATT_D_DATE_03                         =	"D_Date_03";
	public static final String	ATT_D_DATE_04                         =	"D_Date_04";
	public static final String	ATT_I_STATUS_01                       =	"I_Status_01";
	public static final String	ATT_I_STATUS_02                       =	"I_Status_02";
	public static final String	ATT_I_STATUS_03                       =	"I_Status_03";
	public static final String	ATT_I_TYPE_01                         =	"I_Type_01";
	public static final String	ATT_I_TYPE_02                         =	"I_Type_02";
	public static final String	ATT_I_TYPE_03                         =	"I_Type_03";
	public static final String	ATT_T_INFO_01                         =	"T_Info_01";
	public static final String	ATT_T_INFO_02                         =	"T_Info_02";
	public static final String	ATT_T_INFO_03                         =	"T_Info_03";
	public static final String	ATT_T_INFO_04                         =	"T_Info_04";
	public static final String	ATT_T_INFO_05                         =	"T_Info_05";
	public static final String	ATT_T_VAL_01                          =	"T_Val_01";
	public static final String	ATT_T_VAL_02                          =	"T_Val_02";
	public static final String	ATT_I_AUT_USER                        =	"I_Aut_User";
	public static final String	ATT_I_PER_MANAGER                     =	"I_Per_Manager";
	
	
	public static final String	ATT_T_CODE_01                         =	"T_Code_01";
	public static final String	ATT_T_CODE_02                         =	"T_Code_02";
	public static final String	ATT_F_VAL_01                          =	"F_Val_01";
	public static final String	ATT_F_VAL_02                          =	"F_Val_02";
	public static final String	ATT_F_VAL_03                          =	"F_Val_03";
	public static final String	ATT_F_VAL_04                          =	"F_Val_04";
	public static final String	ATT_F_VAL_05                          =	"F_Val_05";
	
	public static final String	ATT_O_PARENT                          =	"O_Parent";
	public static final String	ATT_O_AVATAR                          =	"O_Avatar";
	public static final String	ATT_O_DOCUMENTS                       =	"O_Documents";
	public static final String	ATT_O_USER_ROLE                       =	"O_User_Role";
	public static final String	ATT_O_MEMBERS                         =	"O_Members";
	public static final String	ATT_O_LASTMESSAGE                     =	"O_LastMessage";
	
	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static	final boolean				API_CACHE 	= false;
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaNsoGroup> 	DAO ;
	static{
		DAO = new EntityDAO<TaNsoGroup>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaNsoGroup.class,RIGHTS);
	}

	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_T_REF, nullable = true)
	private	String          T_Ref;
        
	@Column(name=COL_T_NAME, nullable = true)
	private	String          T_Name;
       
	@Column(name=COL_D_DATE_01, nullable = true)
	private	Date            D_Date_01;
    
	@Column(name=COL_D_DATE_02, nullable = true)
	private	Date            D_Date_02;
    
	@Column(name=COL_D_DATE_03, nullable = true)
	private	Date            D_Date_03;
 
	@Column(name=COL_D_DATE_04, nullable = true)
	private	Date            D_Date_04;
    
	@Column(name=COL_I_STATUS_01, nullable = true)
	private	Integer         I_Status_01;
	
	@Column(name=COL_I_STATUS_02, nullable = true)
	private	Integer         I_Status_02;
     
	@Column(name=COL_I_STATUS_03, nullable = true)
	private	Integer         I_Status_03;
	
	@Column(name=COL_I_TYPE_01, nullable = true)
	private	Integer         I_Type_01;
    
	@Column(name=COL_I_TYPE_02, nullable = true)
	private	Integer         I_Type_02;
    
	@Column(name=COL_I_PER_MANAGER, nullable = true)
	private	Integer         I_Per_Manager;
	
	@Column(name=COL_T_INFO_01, nullable = true)
	private	String          T_Info_01;
    
	@Column(name=COL_T_INFO_02, nullable = true)
	private	String          T_Info_02;
	
	@Column(name=COL_T_INFO_03, nullable = true)
	private	String          T_Info_03;
	
	@Column(name=COL_T_INFO_04, nullable = true)
	private	String          T_Info_04;
	
	@Column(name=COL_T_INFO_05, nullable = true)
	private	String          T_Info_05;
	
	@Column(name=COL_T_VAL_01, nullable = true)
	private	String          T_Val_01;
	
	@Column(name=COL_T_VAL_02, nullable = true)
	private	String          T_Val_02;

	@Column(name=COL_I_AUT_USER, nullable = true)
	private	Integer        I_Aut_User;
	
	@Column(name=COL_I_PARENT, nullable = true)
	private	Integer        I_Parent;
	
	
	/* use for other project*/
	@Column(name=COL_I_TYPE_03, nullable = true)
	private	Integer         I_Type_03;
	
	@Column(name=COL_T_CODE_01, nullable = true)
	private	String          T_Code_01;
    
	@Column(name=COL_T_CODE_02, nullable = true)
	private	String          T_Code_02;
	
	@Column(name=COL_F_VAL_01, nullable = true)
	private	Double          F_Val_01;
     
	@Column(name=COL_F_VAL_02, nullable = true)
	private	Double          F_Val_02;
     
	@Column(name=COL_F_VAL_03, nullable = true)
	private	Double          F_Val_03;
     
	@Column(name=COL_F_VAL_04, nullable = true)
	private	Double          F_Val_04;
     
	@Column(name=COL_F_VAL_05, nullable = true)
	private	Double          F_Val_05;
	//-----------------------Transient Variables-------------------------
	@Transient
	private TaTpyDocument			O_Avatar;

	@Transient
	private List<TaTpyDocument>		O_Documents;

	@Transient
	private Hashtable				O_User_Role			= null;
	
	@Transient
	private List<TaNsoGroupMember>	O_Members;
	
	@Transient
	public Object			   	 	O_Parent			= null;
	
	//---------------------Constructeurs-----------------------
	private TaNsoGroup(){}

	public TaNsoGroup(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		doInitDBFlag();
	}
	
	public TaNsoGroup (String T_Ref, Integer I_Type_01) {
		this.T_Ref = T_Ref;
		this.I_Type_01 = I_Type_01;
	}
	
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaNsoGroup ent) {
		if (ent == this) return;
		this.T_Ref                  = ent.T_Ref;
		this.T_Name                 = ent.T_Name;
		this.D_Date_01              = ent.D_Date_01;
		this.D_Date_02              = ent.D_Date_02;
		this.I_Status_01            = ent.I_Status_01;
		this.I_Status_02            = ent.I_Status_02;
		this.I_Type_01              = ent.I_Type_01;
		this.I_Type_02              = ent.I_Type_02;
		this.I_Per_Manager          = ent.I_Per_Manager;
		this.T_Info_01              = ent.T_Info_01;
		this.T_Info_02              = ent.T_Info_02;
		this.T_Info_03              = ent.T_Info_03;
		this.T_Info_04              = ent.T_Info_04;
		this.T_Info_05              = ent.T_Info_05;
		this.T_Val_01				= ent.T_Val_01;
		this.T_Val_02				= ent.T_Val_02;
		this.I_Aut_User        		= ent.I_Aut_User;


		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((TaNsoGroup)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}

	//---khong chung session duoc vi nam trong package khac
	public void doBuildDocuments(boolean forced) throws Exception {
		if (this.O_Documents != null && !forced) return;
		this.O_Documents = TaTpyDocument.reqTpyDocuments(ENT_TYP, I_ID, null, null);
	}
	
	public void doBuildMembers (boolean forced) throws Exception{
		if (this.O_Members != null && !forced) return;
		this.O_Members = TaNsoGroupMember.reqListByGroupId(this.I_ID);
	}
	
	
	public void doAddMember (TaNsoGroupMember mem) throws Exception{
		if (this.O_Members == null) this.O_Members= new ArrayList<TaNsoGroupMember>();
		this.O_Members.add(mem);
	}
	
	public void doBuilAvatarPath()  throws Exception{
		if (this.O_Documents == null) return;
		
		for (TaTpyDocument doc : O_Documents){
			Integer typ01 = doc.reqInt(TaTpyDocument.ATT_I_TYPE_01);
			Integer typ02 = doc.reqInt(TaTpyDocument.ATT_I_TYPE_02);
			if (typ01!=null && typ02!=null 
					&& typ01.equals(TaTpyDocument.TYPE_01_FILE_MEDIA)  
					&& typ02.equals(TaTpyDocument.TYPE_02_FILE_IMG_AVATAR)){
				
				JSONObject 	ava 	= new JSONObject();
				String 		imgPrv 	= doc.reqStr(TaTpyDocument.ATT_T_INFO_05);
				String 		imgRaw 	= doc.reqStr(TaTpyDocument.ATT_T_INFO_03);
				if (imgPrv==null) imgPrv = imgRaw;
				ava.put("img"	, imgPrv);
				ava.put("imgRaw", imgRaw);
				this.T_Val_01 = ava.toJSONString();
				return;
			}
		}
	}
	
	
	public static void doBuildMemberForList(List<TaNsoGroup>  list) throws Exception {
		if(list==null || list.size()== 0) return;
		
		Set<Integer> 			ids 		= ToolSet.reqSetInt(list, TaNsoGroupMember.ATT_I_ID);
		List<TaNsoGroupMember> 	lstGrMems	= TaNsoGroupMember.DAO.reqList(Restrictions.in(TaNsoGroupMember.ATT_I_NSO_GROUP, ids));
		
		List<ViAutUserMember> 	lstUsers 	= TaAutUser		.reqBuildUserMember	(lstGrMems, TaNsoGroupMember.ATT_I_AUT_USER, TaNsoGroupMember.ATT_O_MEMBER);
		TaTpyDocument	.reqBuildAvatar(lstUsers, DefDBExt.ID_TA_AUT_USER, ViAutUserMember.ATT_O_AVATAR);
		
		Hashtable<Integer, EntityAbstract> tabs = ToolDBEntity.reqTabKeyInt(list, TaNsoGroup.ATT_I_ID);
		for (TaNsoGroupMember mem :lstGrMems) {
			Integer 	idG = mem.reqInt(TaNsoGroupMember.ATT_I_NSO_GROUP);
			TaNsoGroup 	grp = (TaNsoGroup) tabs.get(idG);
			if (grp!=null) grp.doAddMember(mem);
		}
	}
	
	
	public static void doBuildParentForList(List<TaNsoGroup>  list) throws Exception {
		if (list==null || list.size()==0) return;
		TaNsoGroup.reqBuildParent(list, DefDBExt.ID_TA_NSO_GROUP, ATT_O_PARENT);
	}
	
	public static void doBuildDocumentsForList(List<TaNsoGroup>  list) throws Exception {
		if (list==null || list.size()==0) return;
		TaTpyDocument.doBuildTpyDocuments(list, DefDBExt.ID_TA_NSO_GROUP,TaTpyDocument.TYPE_01_FILE_OTHER,null, ATT_O_DOCUMENTS,false);
	}
	
	public static List<TaNsoGroup> reqBuildParent(List lst, int entTyp, String colParentName) throws Exception {
		if (colParentName==null) colParentName 		= ATT_O_PARENT;
		if (lst==null) return null;
		Set<Integer> ids 							= ToolSet.reqSetInt(lst, ATT_I_PARENT);
		if(ids.size()>0) {
			Map<Integer, TaNsoGroup> 	mapPar		= new HashMap<Integer, TaNsoGroup>();
			
			List<TaNsoGroup> 			pars 		= reqNsoParent (entTyp, ids, TaNsoGroup.TYP_01_WORK);

			if(pars != null && pars.size() > 0) {
				for(TaNsoGroup a : pars) {
					mapPar.put((Integer) a.req(TaNsoGroup.ATT_I_ID), a);
				}
				for(Object g : lst) {
					EntityAbstract 	ent 			= ((EntityAbstract)g);
					Integer 		id 				= ent.reqInt(ATT_I_PARENT);
					if(id!=null && mapPar.containsKey(id)){
						ent.reqSet(colParentName, mapPar.get(id));
					}
				}
			}
			return pars;
		}
		return null;
	}
	public static List<TaNsoGroup> reqNsoParent (Integer entType, Set<Integer> entId, Integer type01) throws Exception{		
		Criterion 		cri 	= Restrictions.gt("I_ID", 0);	

		if (type01!=null)
				  	    cri 	= Restrictions.and(cri, Restrictions.eq(TaNsoGroup.ATT_I_TYPE_01, type01));
		
		List<TaNsoGroup> lst 	= TaNsoGroup.DAO.reqList_In(TaNsoGroup.ATT_I_ID,entId, cri);
		
		return lst;
	}
	public boolean canHaveRole (Integer userId, Integer role) {
		if (userId==null) return false;
		if (this.O_User_Role==null) return false;
		
		Integer level = (Integer) O_User_Role.get(userId);	
		if (level!=null && level<=role) return true;
		return false;	
	}

	public void doPutRole (Integer userId, Integer roleLev) {
		if (userId==null || roleLev==null) return;
		if (this.O_User_Role==null) this.O_User_Role = new Hashtable<Integer, Integer>();
		O_User_Role.put(userId, roleLev);
	}
	
	public static void doBuildAvatarForList(List<TaNsoGroup>  list) throws Exception {
		if (list==null || list.size()==0) return;
		TaTpyDocument.reqBuildAvatar(list, DefDBExt.ID_TA_PER_PERSON, ATT_O_AVATAR);
	}
	public void doBuildAvatar(boolean forced) throws Exception {
		if (this.O_Avatar != null && !forced) return;
		
		if (this.O_Documents != null && !forced) {
			for (TaTpyDocument doc: this.O_Documents) {
				Integer typ01 = doc.reqInt(TaTpyDocument.ATT_I_TYPE_01);
				Integer typ02 = doc.reqInt(TaTpyDocument.ATT_I_TYPE_02);
				if (typ01!=null && typ02!=null 
						&& typ01.equals(TaTpyDocument.TYPE_01_FILE_MEDIA)  
						&& typ02.equals(TaTpyDocument.TYPE_02_FILE_IMG_AVATAR)){
					this.O_Avatar = doc;
					return;
				}
			}
		}
		TaTpyDocument.reqBuildAvatar (this, DefDBExt.ID_TA_AUT_USER, ATT_O_AVATAR);
	}
	
}
