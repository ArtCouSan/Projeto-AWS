package br.com.trips.handler;

import br.com.trips.dao.TripRepository;
import br.com.trips.model.HandlerRequest;
import br.com.trips.model.HandlerResponse;
import br.com.trips.model.Trip;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

public class GetTripByCountry implements RequestHandler<HandlerRequest, HandlerResponse> {

    private final TripRepository repository = new TripRepository();

    @Override
    public HandlerResponse handleRequest(HandlerRequest request, Context context) {

        final String city = request.getQueryStringParameters().get("city");
        final String country = request.getPathParameters().get("country");
        List<Trip> trips = null;

        if(city != null){
            context.getLogger()
                    .log("Searching for registered trips for city");
            trips = this.repository.findByCity(city, country);
        }else {
            context.getLogger()
                    .log("Searching for registered trips for country");
            trips = this.repository.findByCountry(country);
        }

        if (trips == null || trips.isEmpty()) {
            return HandlerResponse.builder().setStatusCode(404).build();
        }

        return HandlerResponse.builder().setStatusCode(200).setObjectBody(trips).build();

    }

}
