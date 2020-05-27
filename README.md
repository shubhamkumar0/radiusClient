# radiusClient

## This is the Web Application for Radius. 
You can register any property to our platform through this page and search for properties listed on our platform.

#### Latitude and Longitude are given in degrees. 
#### Currently there is no support for multiple currency on the platform.
#### Distances are given in miles.
#### minimum number of bedrooms & bathrooms is 1
#### minimum price is 1

### The following algorithm assigns percentage matches to the properties:
Percentages are assigned based on distance(30%), price(30%), number of bedroom & bathrooms(20%+20%).

All matches above 40% can only be considered useful.

For a property and requirement to be considered a valid match, distance should be within 10 miles, the budget is +/- 25%, bedroom and bathroom should be +/- 2.

### Distance:
For a valid match, distance should be within 10 miles.

If the distance is within 2 miles, distance contribution for the match percentage is fully 30%

Otherwise percentage decreases linearly upto 10 miles, with 10 miles adding 1%.

### Budget
For a valid match, the budget should be +/- 25%

If both max and min budget are given-

  Budget contribution for the match percentage is full 30%, when price of property lies between these two. 
  
If min or max is not given, +/- 10% of either budget is a full 30% match.

In both cases for -/+15% range of the budget we give 20% contribution, for -/+20% we give 10% contribution, otherwise we give 5% contribution.

### Number of bedroom and bathroom
If bedroom and bathroom fall between min and max(provided both are given), each will contribute full 20%, else 10%.

If min or max is not given, +/- 1 is 20% contribution and else, 10% contribution.


## Run the file radiusDB.sql to setup the mysql database.
## How to start
```
spring.datasource.url=jdbc:mysql://localhost:3306/radius
spring.datasource.username=root
spring.datasource.password=root123
```
change these values in application properties to your local mysql username & password. Also if the port for mysql is not 3306 change that too.

Then navigating to the project folder 
```
cd radiusCLient
mvn clean install
```
The project should be running on http://localhost:90/
