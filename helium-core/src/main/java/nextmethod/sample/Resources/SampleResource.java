package nextmethod.sample.Resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/sample")
@Consumes("application/json")
@Produces("application/json")
public class SampleResource {

	@GET
	@Path("/test")
	public SampleUser test() {
		return new SampleUser();
	}

}
