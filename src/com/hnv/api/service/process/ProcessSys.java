package com.hnv.api.service.process;

import java.util.List;

import com.hnv.api.def.DefTime;
import com.hnv.db.sys.TaSysLock;
import com.hnv.process.ThreadManager;

public class ProcessSys {
	//------------------------------------------------Process for alert stock for date and Quantity-----------------------------------------
	//------------------------------------------------------------------
	public static void do_RemoveSysLock(){			
		Thread t = new Thread(){
			public void run(){
				try {
					List<TaSysLock>  	lst	 	= TaSysLock.DAO.reqList();
					TaSysLock.DAO.doRemove(lst);
				} catch (Exception e) {	
					e.printStackTrace();
				}
			}				
		};
		ThreadManager.doExecute(t, DefTime.TIME_00_00_01_000);
	}

}
