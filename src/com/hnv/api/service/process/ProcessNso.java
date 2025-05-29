package com.hnv.api.service.process;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.hnv.api.def.DefTime;
import com.hnv.db.aut.TaAutUser;
import com.hnv.db.nso.TaNsoOffer;
import com.hnv.process.ThreadManager;

public class ProcessNso {
	//------------------------------------------------Process for alert stock for date and Quantity-----------------------------------------
	//------------------------------------------------------------------
	public static void do_Offer_ChangeStatusToInactive(){			
		Thread t = new Thread(){
			public void run(){
				try {
					List<TaNsoOffer>  	lst	 	= TaNsoOffer.DAO.reqList(	
							Restrictions.eq(TaNsoOffer.ATT_I_STATUS_01, TaNsoOffer.STAT_01_ACTIVE),
							Restrictions.gt(TaNsoOffer.ATT_D_DATE_04, new Date())
							);
					for (TaNsoOffer e : lst) {
						e.reqSet(TaNsoOffer.ATT_I_STATUS_01, TaNsoOffer.STAT_01_EXPIRED);
					}

					TaNsoOffer.DAO.doMerge(lst);
					
				} catch (Exception e) {	
					e.printStackTrace();
				}
			}				
		};
		ThreadManager.doExecuteInfini(t, DefTime.TIME_01_00_00_000, DefTime.TIME_24_00_00_000);
	}

}
