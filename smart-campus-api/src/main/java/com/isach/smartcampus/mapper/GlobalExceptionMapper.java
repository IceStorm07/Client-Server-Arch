/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isach.smartcampus.mapper;

/**
 *
 * @author isach
 */

import com.isach.smartcampus.model.APIError;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(Throwable ex) {
        if (ex instanceof WebApplicationException) {
            WebApplicationException webEx = (WebApplicationException) ex;
            int status = webEx.getResponse().getStatus();

            APIError error = new APIError(
                    status,
                    "Request Error",
                    ex.getMessage(),
                    uriInfo != null ? uriInfo.getPath() : "",
                    System.currentTimeMillis()
            );

            return Response.status(status).entity(error).build();
        }

        APIError error = new APIError(
                500,
                "Internal Server Error",
                "An unexpected error occurred.",
                uriInfo != null ? uriInfo.getPath() : "",
                System.currentTimeMillis()
        );

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
    }
}
