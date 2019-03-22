package io.dracula.test.dubbo.brave;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.*;

/**
 * @author dk
 */
@Path("/interfaceB")
public interface InterfaceB {

    /**
     *
     * @param name
     * @return
     */
    @GET
    @Path("/toB")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    String toB(@QueryParam("name") String name);

}
