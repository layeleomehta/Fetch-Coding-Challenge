# Fetch: Receipt Processor Challenge

This is a web service I built in response to Fetch's receipt processor challenge, the instructions for which were found in this instruction repo: https://github.com/fetch-rewards/receipt-processor-challenge. 

The service is built with Spring Boot and Kotlin, which stores data in a PostgreSQL database. The application is deployed on Google App Engine, which interfaces with a PostgreSQL database hosted in Google Cloud SQL. 

The application has been tested, and successfully returns the expected responses for all four example receipts given in the instruction repo. 

## Instructions: How To Use This Web Service

The service is deployed on Google App Engine, and it has a base URL of: 

    https://sapient-metrics-379819.uc.r.appspot.com
    
When hitting the endpoints, add the respective path to the end of this base URL to use the web service. Use Postman to hit the API endpoints. 

**Endpoint 1: Process receipt:**
-   Path:  `/receipts/process`
-   Method:  `POST`
-   Payload: Receipt JSON
-   Response: JSON containing an id for the receipt.

Hitting this endpoint with a correctly formatted request body will save the receipt and its metadata in the Receipt table of the database, along with an entry in the Item table for every item in the receipt. 

This is an example response object: 

    {"id":  "04acaf0d-ee86-4ff4-a29c-60a1523bb2dd"}

**Endpoint 2: Get points**
-   Path:  `/receipts/{id}/points`
-   Method:  `GET`
-   Response: A JSON object containing the number of points awarded.

Once the receipt has been processed successfully, you can use the receipt id from the response as a path variable in this endpoint. Hitting this endpoint will calculate the number of points the receipt is worth based on the rules outlined in the instruction repo. 

This is an example response object: 

    {"points":  109}


## Key Assumptions 

- **Assumption 1**:  All required fields in the receipt request body must be present in order to successfully process the receipt. Any omitted fields in the request body will result in the receipt not being processed. 
- **Assumption 2**: The `purchaseDate` field in the receipt request body follows the `YY:MM:DD` convention, and it will always be a valid date.
- **Assumption 3**: The `purchaseTime` field in the receipt request body follows the 24 hour time notation, and it will always be a valid time.
-  **Assumption 4**: The rule for adding 10 points if the time of purchase is between 2 PM and 4 PM does NOT include the actual time at 2 PM and 4 PM, only the times in between.  

## Design & Architecture overview


