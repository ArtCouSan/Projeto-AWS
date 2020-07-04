package br.com.trips.handler;

import br.com.trips.dao.TripRepository;
import br.com.trips.model.HandlerRequest;
import br.com.trips.model.HandlerResponse;
import br.com.trips.model.Trip;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

public class GetTripByCity implements RequestHandler<HandlerRequest, HandlerResponse> {

    private final TripRepository repository = new TripRepository();

    @Override
    public HandlerResponse handleRequest(HandlerRequest request, Context context) {

        final String city = request.getPathParameters().get("city");
        final String country = request.getPathParameters().get("country");

        context.getLogger()
                .log("Searching for registered trips for city");
        final List<Trip> trips = this.repository.findByCity(city, country);

        if (trips == null || trips.isEmpty()) {
            return HandlerResponse.builder().setStatusCode(404).build();
        }

        return HandlerResponse.builder().setStatusCode(200).setObjectBody(trips).build();

    }
}
