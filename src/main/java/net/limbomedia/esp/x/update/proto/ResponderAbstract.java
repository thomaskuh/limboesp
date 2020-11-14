package net.limbomedia.esp.x.update.proto;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

public abstract class ResponderAbstract implements Responder {

	protected HttpServletResponse res;
	protected boolean withData;

	public ResponderAbstract(HttpServletResponse res, boolean withData) {
		this.res = res;
		this.withData = withData;
	}

	@Override
	public void onNoUpdate() {
		res.setStatus(HttpStatus.NOT_MODIFIED.value());
	}

}
