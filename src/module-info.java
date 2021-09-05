module demo.module {
	//requires <module>
	requires java.xml;
	requires transitive demo.module.lib;
	uses demo.service.IAccountService;
	uses demo.service.ICustomerService;
	uses demo.service.pub1.IPublisherService1;
	//uses demo.service.pub2.IPublisherService2; // The type demo.service.pub2.IPublisherService2 is not accessible
}