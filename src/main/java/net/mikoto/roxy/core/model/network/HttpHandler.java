package net.mikoto.roxy.core.model.network;

public interface HttpHandler<Request, Response, Resource> {
    Response run(Object httpRequest);
    Request getRequest(Object resource, Object... objects);
}
