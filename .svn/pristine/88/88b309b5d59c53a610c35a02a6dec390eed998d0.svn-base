package cokr.xit.modules.post.domains.sign.service;

public class ApiCaller {
	private ApiStrategy apiStrategy;

	public synchronized void setApiStrategy(ApiStrategy apiStrategy) {
		this.apiStrategy = apiStrategy;
	}
	
	public void execute() {
		apiStrategy.execute();
	}
	
	public Response getResponse(){
		return apiStrategy.getResponse();
		
	}

}
