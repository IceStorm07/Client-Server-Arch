/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isach.smartcampus.mapper;

/**
 *
 * @author isach
 */

import com.isach.smartcampus.exception.LinkedResourceNotFoundException;
import com.isach.smartcampus.model.APIError;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(LinkedResourceNotFoundException ex) {
        APIError error = new APIError(
                422,
                "Unprocessable Entity",
                ex.getMessage(),
                uriInfo.getPath(),
                System.currentTimeMillis()
        );

        return Response.status(422).entity(error).build();
    }
}
