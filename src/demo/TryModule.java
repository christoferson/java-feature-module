package demo;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.ServiceLoader;

import demo.model.Account;
import demo.service.IAccountService;


public class TryModule {

	public static void main(String[] args) {
		System.out.println("Demo Module");
		
		tryExportsModel();
		
		tryProvidesWith();
		
	}
	
	// Try exports demo.model; from lib
	private static void tryExportsModel() {
		Account account = new Account();
		account.setId(91);
		account.setBalance(BigDecimal.valueOf(1092));
		System.out.println(account);
	}
	
	// Try provides IAccountService with AccountServiceMemory, AccountServiceJpa
	private static void tryProvidesWith() {
		ServiceLoader<IAccountService> loader = ServiceLoader.load(IAccountService.class);
		Iterator<IAccountService> itr = loader.iterator();
		while (itr.hasNext()) {
			System.out.println(itr.next());
		}
	}
	
}
