package com.hnv.api.service.process;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.hnv.api.def.DefTime;
import com.hnv.db.aut.TaAutUser;
import com.hnv.process.ThreadManager;

public class ProcessAut {
	//------------------------------------------------Process for alert stock for date and Quantity-----------------------------------------
	//------------------------------------------------------------------
	public static void do_RemoveUserPulicInactive(){			
		Thread t = new Thread(){
			public void run(){
				try {
					List<TaAutUser>  	lst	 	= TaAutUser.DAO.reqList(	
//							Restrictions.eq(TaAutUser.ATT_I_TYPE_01, TaAutUser.TYPE_01_VISITOR),
							Restrictions.gt(TaAutUser.ATT_D_DATE_03, new Date())
							);
					for (TaAutUser u : lst) {
						//request and remove all entities connect to these user like person, document
						
						TaAutUser.DAO.doRemove(u);
					}

				} catch (Exception e) {	
					e.printStackTrace();
				}
			}				
		};
		ThreadManager.doExecuteInfini(t, DefTime.TIME_01_00_00_000, DefTime.TIME_24_00_00_000);
	}

}
