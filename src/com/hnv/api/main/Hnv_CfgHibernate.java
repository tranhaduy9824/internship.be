package com.hnv.api.main;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hnv.common.tool.ToolLogServer;
import com.hnv.db.FactoryEMHibernate;
import com.hnv.db.FactoryEMSession;


public class Hnv_CfgHibernate {	
	private final static Logger LOGGER 			= Logger.getLogger("HibernateConfig");
	
	public static final Integer ID_FACT_MAIN 	= 1;
	public static final Integer ID_FACT_SYS 	= 2;
	public static final Integer ID_FACT_NSO 	= 10;
	
	private static 		Integer[] factIds	= new Integer[] {ID_FACT_MAIN, ID_FACT_SYS, ID_FACT_NSO};
	
	public static  	 	String CFG_XML_MAIN		= "hibernate_main.cfg.xml"; 
	public static  	 	String CFG_XML_SYS 		= "hibernate_sys.cfg.xml"; 
	public static  	 	String CFG_XML_NSO 		= "hibernate_nso.cfg.xml"; 
	
	private static 	Hashtable<Integer, FactoryEMHibernate> hbTab		= new Hashtable<Integer, FactoryEMHibernate>();
	static {
		for (Integer id: factIds) {
			hbTab.put(id, new FactoryEMHibernate());
		}
	}
	
	//----hien tuong asynchro, initSessionFactory diễn ra sau khi các table init DAO
	public static void doInitSessionFactory(Integer id, String cfgPath) {
		try{
			ToolLogServer.doLogInf("--- Load hibernate config: "+cfgPath);
			
			FactoryEMHibernate hbFactory = hbTab.get(id);
			if (hbFactory==null) throw new Exception("Null hbFactory");
			hbFactory.doInitSessionFactory(cfgPath);
			
		}catch(Exception ex){
			LOGGER.log(Level.SEVERE, "--- HibernateConfig: cannot doInitSessionFactory for id = " + id + "-" + cfgPath);
//			ToolLogServer.doLogErr(ex, "", "HibernateConfig", "doInitSessionFactory");
		}
	}

	public static void doCloseSessionFactory(){
		try{
			for (FactoryEMHibernate hb : hbTab.values())
				hb.doCloseSessionFactory();
		}catch(Exception ex){
			ToolLogServer.doLogErr(ex, "", "HibernateConfig", "doCloseSessionFactory");
		}
	}
	
	public synchronized static FactoryEMSession reqFactoryEMSession(Integer factId) {
		FactoryEMHibernate hbF = hbTab.get(factId);
		if (hbF!=null) return hbF.reqEMSessionFactory();
		
		LOGGER.log(Level.SEVERE, "--- HibernateConfig: cannot reqFactoryEMSession for factId=" + factId);
		return null;
	}

	
}