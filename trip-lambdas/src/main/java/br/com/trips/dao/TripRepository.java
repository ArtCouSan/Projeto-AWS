package br.com.trips.dao;

import br.com.trips.model.Trip;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripRepository {

    private static final DynamoDBMapper mapper = DynamoDBManager.mapper();

    public Trip save(final Trip trip) {
        mapper.save(trip);
        return trip;
    }

    public List<Trip> findByCountry(final String country) {

        final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(country));

        final DynamoDBQueryExpression<Trip> queryExpression = new DynamoDBQueryExpression<Trip>()
                .withKeyConditionExpression("country = :val1").withExpressionAttributeValues(eav);

        final List<Trip> trips = mapper.query(Trip.class, queryExpression);

        return trips;
    }

    public List<Trip> findByCity(final String city, final String country) {

        final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(country));
        eav.put(":val2", new AttributeValue().withS(city));

        final DynamoDBQueryExpression<Trip> queryExpression = new DynamoDBQueryExpression<Trip>()
                .withKeyConditionExpression("country =:val1")
                .withFilterExpression("contains(city, :val2)")
                .withExpressionAttributeValues(eav);

        final List<Trip> trips = mapper.query(Trip.class, queryExpression);

        return trips;
    }

    public List<Trip> findByPeriod(final String starts, final String ends) {

        final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        ;
        eav.put(":val1", new AttributeValue().withS(starts));
        eav.put(":val2", new AttributeValue().withS(ends));

        final Map<String, String> expression = new HashMap<>();

        // date is a reserver word in DynamoDB
        expression.put("#date", "date");

        final DynamoDBQueryExpression<Trip> queryExpression = new DynamoDBQueryExpression<Trip>()
                .withKeyConditionExpression("#date between :val1 and :val2")
                .withExpressionAttributeValues(eav).withExpressionAttributeNames(expression);

        final List<Trip> trips = mapper.query(Trip.class, queryExpression);

        return trips;
    }
}
