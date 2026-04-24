/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isach.smartcampus.mapper;

/**
 *
 * @author isach
 */

import com.isach.smartcampus.exception.RoomNotEmptyException;
import com.isach.smartcampus.model.APIError;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(RoomNotEmptyException ex) {
        APIError error = new APIError(
                409,
                "Conflict",
                ex.getMessage(),
                uriInfo.getPath(),
                System.currentTimeMillis()
        );

        return Response.status(Response.Status.CONFLICT).entity(error).build();
    }
}
