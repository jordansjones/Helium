package nextmethod.sample;

import nextmethod.helium.HeliumConfig;
import nextmethod.sample.Resources.SampleResource;

public class SampleService {

	public static void main(String[] args) throws Exception {
		HeliumConfig.Instance
			.addServiceClass(SampleResource.class)
			.runService(8080);
	}
}
