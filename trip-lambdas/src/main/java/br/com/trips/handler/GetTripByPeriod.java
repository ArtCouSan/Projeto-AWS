package br.com.trips.handler;

import br.com.trips.dao.TripRepository;
import br.com.trips.model.HandlerRequest;
import br.com.trips.model.HandlerResponse;
import br.com.trips.model.Trip;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

public class GetTripByPeriod implements RequestHandler<HandlerRequest, HandlerResponse> {

    private final TripRepository repository = new TripRepository();

    @Override
    public HandlerResponse handleRequest(HandlerRequest request, Context context) {

        final String starts = request.getQueryStringParameters().get("starts");
        final String ends = request.getQueryStringParameters().get("ends");

        final List<Trip> trips = this.repository.findByPeriod(starts, ends);

        if (trips == null || trips.isEmpty()) {
            return HandlerResponse.builder().setStatusCode(404).build();
        }

        return HandlerResponse.builder().setStatusCode(200).setObjectBody(trips).build();

    }
}
