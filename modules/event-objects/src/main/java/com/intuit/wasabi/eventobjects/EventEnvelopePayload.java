/*******************************************************************************
 * Copyright 2016 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.wasabi.eventobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.wasabi.analyticsobjects.Event;
import com.intuit.wasabi.assignmentobjects.Assignment;
import com.intuit.wasabi.assignmentobjects.AssignmentEnvelopePayload;
import com.intuit.wasabi.experimentobjects.Application;
import com.intuit.wasabi.experimentobjects.Application.Name;
import com.intuit.wasabi.experimentobjects.Experiment;
import com.intuit.wasabi.experimentobjects.Experiment.Label;
import com.intuit.wasabi.export.EnvelopePayload;
import com.intuit.wasabi.export.MessageType;
import org.apache.cassandra.utils.UUIDGen;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Export envelope payload for events
 */
public class EventEnvelopePayload implements EnvelopePayload {
    private static final Logger LOG = LoggerFactory.getLogger(EventEnvelopePayload.class);
    private final MessageType messageType = MessageType.EVENT;
    private final String version = "3.0";
    private final UUID timeUUID = UUIDGen.getTimeUUID();
    private Name applicationName;
    private Label experimentLabel;
    private Assignment assignment;
    private Event event;

    /**
     * @param applicationName the application name
     * @param experimentLabel the experiment label
     * @param assignment      the assignment {@link Assignment}
     * @param event           the event {@link Event}
     */
    @JsonCreator
    public EventEnvelopePayload(@JsonProperty("applicationName") Application.Name applicationName,
                                @JsonProperty("experimentLabel") Experiment.Label experimentLabel,
                                @JsonProperty("assignment") Assignment assignment,
                                @JsonProperty("event") Event event) {
        this.applicationName = applicationName;
        this.experimentLabel = experimentLabel;
        this.assignment = assignment;
        this.event = event;
    }

    public Name getApplicationName() {
        return applicationName;
    }

    public Label getExperimentLabel() {
        return experimentLabel;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public Event getEvent() {
        return event;
    }

    /**
     * @return the time UUID
     */
    public UUID getTimeUUID() {
        return timeUUID;
    }

    /**
     * @return the message type
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    @Override
    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOG.warn("Can not serialize {} in EventEnvelopePayload#toJson.", this);
            Map<String, Object> error = new HashMap<>(2);
            error.put("error", e.getMessage());
            if (LOG.isDebugEnabled()) {
                error.put("debug", Arrays.toString(e.getStackTrace()));
            }
            try {
                return objectMapper.writeValueAsString(error);
            } catch (JsonProcessingException e1) {
                throw new RuntimeException("Can not serialize error handling in EventEnvelopePayload#toJson.", e1);
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof EventEnvelopePayload && EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
