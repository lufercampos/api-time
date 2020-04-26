package com.devlf.apitime.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.devlf.apitime.facade.UserFacade;
import com.devlf.apitime.vo.UserVO;

@Path("user")
public class UserRest {
	
	@PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("saveUser")
    public Response saveJob(
            @HeaderParam("token") String token,
            UserVO userVO
    ) throws Exception {
        try {

            UserFacade userFacade = new UserFacade();

            if (userFacade.getUserByToken(token) != null) {
            	
            	userFacade.save(userVO);

                return Response.status(Response.Status.OK).build();
            } else {
                return Response
                        .status(Response.Status.FORBIDDEN)
                        .entity("invalid user")
                        .build();
            }

        } catch (Exception e) {
        	return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

	@GET
	@Produces("application/json")
	@Path("getUserByToken")
	public Response getUserByToken(@DefaultValue("") @HeaderParam("token") String token) throws Exception {
		try {
			UserFacade userFacade = new UserFacade();
			UserVO userVO = userFacade.getUserByToken(token);

			if (userVO != null) {
				return Response.status(Response.Status.OK).entity(userVO).build();

			} else {
				return Response.status(Response.Status.FORBIDDEN).entity("invalid user!").build();
			}

		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

}
