package demo;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;

import demo.model.Account;
import demo.service.IAccountService;
import demo.service.ICustomerService;
import demo.service.pub1.IPublisherService1;
import legacy.LegacyClass;
//import demo.service.pub2.IPublisherService2; //The type demo.service.pub2.IPublisherService2 is not accessible


public class TryModule {

	public static void main(String[] args) throws Exception {
		
		System.out.println("Demo Module");
		
		tryStandardLibJavaxXml();
		
		tryExportsModel();
		
		tryProvidesWith();
		
		tryProvidesWithAndMatches();
		
		tryExportsToThisModule();
		
		tryOpenPackage();
		
		tryAccessLegacyClass();
		
		tryServiceProviderFactory();
		
	}
	
	// requires java.xml;
	private static void tryStandardLibJavaxXml() {
		System.out.println(javax.xml.XMLConstants.XML_NS_PREFIX);
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
		
		ServiceLoader.load(IAccountService.class).stream()
		    .map(Provider::get)
		    .forEach(System.out::println);
	}
	
	// Try provides IAccountService with AccountServiceMemory, AccountServiceJpa
	private static void tryProvidesWithAndMatches() {
		ServiceLoader.load(IAccountService.class).stream()
		    .map(Provider::get)
		    .filter(svc -> svc.matches("jpa"))
		    .forEach(System.out::println);
	}
	
	//	exports demo.service.pub1 to demo.module;
	//	exports demo.service.pub2 to demo.module2;
	private static void tryExportsToThisModule() {
		System.out.println(IPublisherService1.class.getName());
		//IPublisherService2.class.getName(); // Only demo.module2 has access to this class
	}
	
	// opens demo.service.open1 to demo.module;
	private static void tryOpenPackage() throws ClassNotFoundException {
		//System.out.println(ClassInOpenPackage.class.getName());
		Class<?> openClazz = Class.forName("demo.service.open1.ClassInOpenPackage");
		System.out.println(openClazz);
		Field[] fields = openClazz.getDeclaredFields();
		Arrays.asList(fields).stream().map(Field::getName).forEach(System.out::println);
	}
	//
	private static void tryAccessLegacyClass() {
		System.out.println(LegacyClass.class.getName());
		//IPublisherService2.class.getName(); // Only demo.module2 has access to this class
	}
	
	
	private static void tryServiceProviderFactory() {
		ServiceLoader.load(ICustomerService.class).stream()
		.map(p -> p.getClass().getCanonicalName())
	    .forEach(System.out::println);
		
		ServiceLoader.load(ICustomerService.class).stream()
	    .map(Provider::get)
	    .forEach(System.out::println);
	}
}
