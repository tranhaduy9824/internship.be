package com.hnv.api.service.process;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.hnv.api.def.DefTime;
import com.hnv.common.tool.ToolFile;
import com.hnv.common.tool.ToolLogServer;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.process.ThreadManager;

public class ProcessTpy {
	//------------------------------------------------Process for alert stock for date and Quantity-----------------------------------------
	//------------------------------------------------------------------
	public static void do_Document_RemoveNull(){			
		Thread t = new Thread(){
			public void run(){
				try {
					List<TaTpyDocument>  lst = TaTpyDocument.DAO.reqList(	
							Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_TYPE	, -1),
							Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_ID	, -1)
							);
					if(lst != null && !lst.isEmpty()) {
						TaTpyDocument.DAO.doRemove(lst);
						for (TaTpyDocument doc : lst) {
							String path = doc.reqStr(TaTpyDocument.ATT_T_INFO_02);
							if (path!=null) {
								ToolLogServer.doLogInf("---Del file :" + path);
								ToolFile.canDelFile(path);
							}
						}
					}
				} catch (Exception e) {	
					e.printStackTrace();
				}
			}				
		};
		ThreadManager.doExecuteInfini(t, DefTime.TIME_24_00_00_000*10, DefTime.TIME_24_00_00_000*30);
	}

}
