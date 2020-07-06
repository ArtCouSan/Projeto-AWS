package br.com.trips.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Date;

@DynamoDBTable(tableName = "trip")
public class Trip {

    @DynamoDBHashKey(attributeName = "country")
    private String coutry;

    @DynamoDBIndexRangeKey(attributeName = "city" , localSecondaryIndexName = "cityIndex")
    private String city;

    @DynamoDBAttribute(attributeName = "date")
    private Date date;

    @DynamoDBAttribute(attributeName = "reason")
    private String reason;

    public Trip(String coutry, String city, Date date, String reason) {
        this.coutry = coutry;
        this.city = city;
        this.date = date;
        this.reason = reason;
    }

    public Trip() {
    }

    public String getCoutry() {
        return coutry;
    }

    public void setCoutry(String coutry) {
        this.coutry = coutry;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
