package br.com.trips.handler;

import br.com.trips.dao.TripRepository;
import br.com.trips.model.HandlerRequest;
import br.com.trips.model.HandlerResponse;
import br.com.trips.model.Trip;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class CreateTrip implements RequestHandler<HandlerRequest, HandlerResponse> {

    private final TripRepository repository = new TripRepository();

    @Override
    public HandlerResponse handleRequest(final HandlerRequest request, final Context context) {

        Trip trip = null;
        try {
            trip = new ObjectMapper().readValue(request.getBody(), Trip.class);
        } catch (IOException e) {
            return HandlerResponse.builder().setStatusCode(400).setRawBody("There is a error in your Trip!").build();
        }
        context.getLogger().log("Creating a new trip to " + trip.getCity());
        final Trip tripSaved = repository.save(trip);
        return HandlerResponse.builder().setStatusCode(201).setObjectBody(tripSaved).build();
    }

}
