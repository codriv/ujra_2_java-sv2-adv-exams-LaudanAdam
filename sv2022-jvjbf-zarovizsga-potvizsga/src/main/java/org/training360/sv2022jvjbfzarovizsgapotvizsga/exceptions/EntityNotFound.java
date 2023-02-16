package org.training360.sv2022jvjbfzarovizsgapotvizsga.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class EntityNotFound extends AbstractThrowableProblem {

    public EntityNotFound(String entity, long id) {
        super(
                URI.create("/api/hospitals/entity-not-found"),
                "Entity not found",
                Status.NOT_FOUND,
                String.format("%s not found with id: %d", entity, id));
    }
}
