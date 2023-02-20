package org.training360.sv2022jvjbfzarovizsgapotvizsga.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class EntityNotFoundException extends AbstractThrowableProblem {
    public EntityNotFoundException(String entity, Long id) {
        super(
                URI.create("/api/hospitals/entity-not-found"),
                "Not found",
                Status.NOT_FOUND,
                String.format("%s not found with id: %d", entity, id));
    }
}
