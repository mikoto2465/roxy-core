package net.mikoto.roxy.core.model.network;

public interface HttpHandler<Request, Response, Resource> {
    Response run(Request httpRequest);
    Request getRequest(Resource resource, Object... objects);
}
