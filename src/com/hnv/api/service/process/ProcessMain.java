package com.hnv.api.service.process;

public class ProcessMain {

	//---call directly from PersistenceListener
	public static void do_LaunchJob() {
		ProcessSys.do_RemoveSysLock();
		
		ProcessAut.do_RemoveUserPulicInactive();
		
		ProcessNso.do_Offer_ChangeStatusToInactive();
		
	}
}
