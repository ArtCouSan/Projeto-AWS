package br.com.trips.dao;

import br.com.trips.model.Trip;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class TripRepository {

    private static final DynamoDBMapper mapper = DynamoDBManager.mapper();

    public Trip save(final Trip trip) {
        mapper.save(trip);
        return trip;
    }

}
