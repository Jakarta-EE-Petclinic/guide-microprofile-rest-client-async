// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2019 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package io.openliberty.guides.gateway;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.openliberty.guides.gateway.client.JobClient;
import io.openliberty.guides.models.JobList;
import io.openliberty.guides.models.Job;
import io.openliberty.guides.models.JobResult;

@Path("/jobs")
public class GatewayJobResource {

    @Inject
    @RestClient
    private JobClient jobClient;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JobList getJobs() {
        try {
            return new JobList(jobClient.getJobs().getResults());
        } catch (Exception ex) {
            // Respond with empty list on error
            return new JobList();
        }
    }

    @GET
    @Path("{jobId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JobResult getJob(@PathParam("jobId") String jobId) {
        return jobClient.getJob(jobId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Job createJob() {
        return jobClient.createJob();
    }
}
