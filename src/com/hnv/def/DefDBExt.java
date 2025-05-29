package com.hnv.def;

public class DefDBExt {

	//--------------------------Database Schema----------------------------
	public static final String SCHEMA			=		"";

	//---------------------------List of Table-----------------------------

	public static final String	TA_PER_PERSON						= 	"TA_PER_PERSON";

	public static final String	TA_AUT_USER							= 	"TA_AUT_USER";
	public static final String	TA_AUT_AUTH_USER					= 	"TA_AUT_AUTH_USER";
	public static final String	TA_AUT_AUTH_SERVICE					= 	"TA_AUT_AUTH_SERVICE";
	public static final String	TA_AUT_ROLE							= 	"TA_AUT_ROLE";
	public static final String	TA_AUT_RIGHT						= 	"TA_AUT_RIGHT";
	public static final String	TA_AUT_HISTORY						= 	"TA_AUT_HISTORY";
	
	public static final String	TA_CFG_VALUE                        =	"TA_CFG_VALUE";
	public static final String	TA_CFG_GROUP_VALUE                  =	"TA_CFG_GROUP_VALUE";
	
	public static final String	TA_TPY_INFORMATION            		=	"TA_TPY_INFORMATION";
	public static final String	TA_TPY_TRANSLATION            		=	"TA_TPY_TRANSLATION";
	public static final String	TA_TPY_RELATIONSHIP            		=	"TA_TPY_RELATIONSHIP";
	public static final String	TA_TPY_HISTORY            			=	"TA_TPY_HISTORY";
	public static final String	TA_TPY_PROPERTY_QUANT            	=	"TA_TPY_PROPERTY_QUANT";
	public static final String	TA_TPY_DOCUMENT            			=	"TA_TPY_DOCUMENT";
	public static final String	TA_TPY_CATEGORY             	   	=	"TA_TPY_CATEGORY";
	public static final String	TA_TPY_CATEGORY_ENTITY             	=	"TA_TPY_CATEGORY_ENTITY";
	public static final String	TA_TPY_FAVORITE                    =	"TA_TPY_FAVORITE";
	
	public static final String 	TA_SYS_LOCK 					   = 	"TA_SYS_LOCK";
	
	public static final String	TA_NSO_POST                        =	"TA_NSO_POST";
	public static final String	VI_NSO_POST                        =	"VI_NSO_POST";
	
	public static final String	TA_NSO_OFFER                       =	"TA_NSO_OFFER";
	public static final String	VI_NSO_OFFER                       =	"VI_NSO_OFFER"; 
	
	public static final String	TA_NSO_GROUP                       =	"TA_NSO_GROUP";
	public static final String	TA_NSO_GROUP_MEMBER                =	"TA_NSO_GROUP_MEMBER";
	
	public static final String	TA_FIN_FINANCE                     =	"TA_FIN_FINANCE";
	
	public static final String	TA_MAT_MATERIAL                    =	"TA_MAT_MATERIAL";
	public static final String	TA_MAT_MATERIAL_DATA               =	"TA_MAT_MATERIAL_DATA";
	public static final String	TA_MAT_MATERIAL_DETAIL             =	"TA_MAT_MATERIAL_DETAIL";
	public static final String	VI_MAT_MATERIAL                    =	"VI_MAT_MATERIAL";
	
	public static final String	TA_MAT_WAREHOUSE                   =	"TA_MAT_WAREHOUSE";
	public static final String	TA_MAT_STOCK                       =	"TA_MAT_STOCK";
	public static final String	TA_MAT_STOCK_IO              	   =	"TA_MAT_STOCK_IO";
	public static final String	TA_MAT_STOCK_MONTH			   	   = 	"TA_MAT_STOCK_MONTH";

	public static final String	TA_MAT_UNIT                        =	"TA_MAT_UNIT";
	public static final String	TA_MAT_PRICE                       =	"TA_MAT_PRICE";
	
	public static final String	TA_SOR_ORDER                       =	"TA_SOR_ORDER";
	public static final String	TA_SOR_ORDER_DETAIL                =	"TA_SOR_ORDER_DETAIL";
	public static final String  TA_SOR_DEAL						   =	"TA_SOR_DEAL";
	
	public static final String	TA_MSG_MESSAGE                     =	"TA_MSG_MESSAGE";
	public static final String	TA_MSG_MESSAGE_HISTORY             =	"TA_MSG_MESSAGE_HISTORY";
	public static final String	VI_MSG_MESSAGE                     =	"VI_MSG_MESSAGE";
	public static final String	TA_MSG_NOTIFICATION                =	"TA_MSG_NOTIFICATION";
	public static final String	TA_MSG_MESSAGE_STORE               =	"TA_MSG_MESSAGE_STORE";
	
	
	public static final String	TA_PRJ_PROJECT               		=	"TA_PRJ_PROJECT";
	//----------------------------------------------------------------------------------------------------
	
	public static final String	TA_JOB_POSITION					   = 	"TA_JOB_POSITION";
	public static final String	TA_JOB_CONTROLLER                  =	"TA_JOB_CONTROLLER";
	public static final String	TA_JOB_CONTROLLER_HISTORY          =	"TA_JOB_CONTROLLER_HISTORY";
	public static final String	TA_JOB_DAYOFF_REQUEST              =	"TA_JOB_DAYOFF_REQUEST";
	public static final String	TA_JOB_DAYOFF_RESUME               =	"TA_JOB_DAYOFF_RESUME";
	public static final String	TA_JOB_HOLIDAY                     =	"TA_JOB_HOLIDAY";
	public static final String	TA_JOB_REPORT                      =	"TA_JOB_REPORT";
	public static final String	TA_JOB_REPORT_DETAIL               =	"TA_JOB_REPORT_DETAIL";
	public static final String	TA_JOB_REPORT_RESUME               =	"TA_JOB_REPORT_RESUME";
	public static final String	VI_JOB_REPORT_USER				   =    "VI_JOB_REPORT_USER";
	//----------------------------------------------------------------------------------------------------
	
	//---------------------------List of Table ID for audit (add id here)-----------------------------
	public static final int	ID_TA_				                             		=   0;
	
	public static final int	ID_TA_PER_PERSON			                            =   10000;
	
	public static final int	ID_TA_AUT_USER				                            =   1000;
	public static final int	ID_TA_AUT_AUTH_USER				                   	 	=   1001;
	public static final int	ID_TA_AUT_AUTH_SERVICE			                   	 	=   1010;
	public static final int	ID_TA_AUT_ROLE				                    		=   1002;
	public static final int	ID_TA_AUT_RIGHT				                     		=   1003;
	
	public static final int	ID_TA_CFG_VALUE                      	 				=	2000;
	
	public static final int	ID_TA_TPY_INFORMATION            						=	50100;
	public static final int	ID_TA_TPY_DOCUMENT            							=	50200;
	public static final int	ID_TA_TPY_CATEGORY             	   						=	50300;
	public static final int	ID_TA_TPY_CATEGORY_ENTITY             					=	50301;
	public static final int	ID_TA_TPY_TRANSLATION            						=	54000;
	public static final int ID_TA_TPY_RELATIONSHIP 									= 	55000;
	
	public static final int	ID_TA_SYS_LOCK             								=	100001;
	
	public static final int	ID_TA_NSO_POST			                            	=   200001;
	public static final int	ID_TA_NSO_OFFER			                            	=   200101;
	
	public static final int	ID_TA_SOR_ORDER                                         =   11000;
	public static final int	ID_TA_SOR_ORDER_DETAIL                                  =   11001;
	public static final int	ID_TA_SOR_DEAL                                          =   12000;
	
	public static final int	ID_TA_MAT_MATERIAL                                      =   20000;
	public static final int	ID_TA_MAT_MATERIAL_DATA                                 =   20010;
	public static final int	ID_TA_MAT_MATERIAL_DETAIL                               =   20001;
	
	public static final int	ID_TA_MAT_WAREHOUSE                                     =   20100;
	public static final int	ID_TA_MAT_STOCK                                      	=   20200;	
	public static final int	ID_TA_MAT_STOCK_MONTH			   	   					= 	20201;
	public static final int	ID_TA_MAT_STOCK_IO                                		=   20300;
	public static final int	ID_TA_MAT_STOCK_IO_DETAIL                         		=   20301;
	public static final int	ID_TA_MAT_STOCK_IO_HISTORY                              =   20302;
	public static final int	ID_TA_MAT_UNIT                                          =   20400;
	public static final int	ID_TA_MAT_PRICE                                         =   20500;
	
	public static final int	ID_TA_FIN_FINANCE                                       =   30000;
	
	//----------------------------------------------------------------------------------------------------
	public static final int	ID_TA_PRJ_PROJECT                                       =   250000;
	public static final int	ID_TA_PRJ_PROJECT_TASK                                  =   250001;
	
	public static final int	ID_TA_PRJ_SPRINT                                   		=   250100;
	public static final int	ID_TA_PRJ_APPOINTMENT                                   =   250300;
	public static final int	ID_TA_PRJ_WORKFLOW                                   	=   250200;
	
	
	//----------------------------------------------------------------------------------------------------
	//---------------------------List of Table NSO-----------------------------

	public static final int ID_TA_NSO_GROUP 										= 5000;
	public static final int ID_TA_NSO_GROUP_MEMBER 									= 5001;
	
	
	public static final int	ID_TA_MSG_MESSAGE                                        =      9000;
	public static final int	ID_TA_MSG_MESSAGE_HISTORY                                =      9001;

	
	
	public static final int	ID_TA_JOB_POSITION			                             =     2000;
	public static final int	ID_TA_JOB_HOLIDAY                                       =      2001;
	public static final int	ID_TA_JOB_REPORT                                        =      2002;
	public static final int	ID_TA_JOB_REPORT_DETAIL                                 =      2003;
	public static final int	ID_TA_JOB_CONTROLLER                                    =      2010;
	public static final int	ID_TA_JOB_CONTROLLER_HISTORY                            =      2011;
	public static final int	ID_TA_JOB_DAYOFF_REQUEST                                =      2020;
	public static final int	ID_TA_JOB_DAYOFF_RESUME                                 =      2021;
	public static final int	ID_TA_JOB_REPORT_USER										=      2022;
	//---------------------------List of Table ID for audit (add id here)-----------------------------
	
	public static final int	ID_TA_TYP_STICKY_NOTE                                  	=   300000;
}

	