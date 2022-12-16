-- INNER JOIN

-- Write a query to retrieve the name and state abbreviation for the 2 cities named "Columbus" in the database
SELECT city_name, state_abbreviation
FROM city
WHERE city_name = 'Columbus';


-- Modify the previous query to retrieve the names of the states AND those cities with their populations (rather than their abbreviations).
SELECT city_name, state_name, city.population AS city_population
FROM city
INNER JOIN state ON city.state_abbreviation = state.state_abbreviation
WHERE city_name = 'Columbus';



-- Write a query to retrieve the names of all the national parks with their state abbreviations.
-- (Some parks will appear more than once in the results, because they cross state boundaries.)
SELECT park_name, state_name, state.state_abbreviation
FROM park
INNER JOIN park_state ON park.park_id = park_state.park_id
JOIN state ON park_state.state_abbreviation = state.state_abbreviation;


-- The park_state table is an associative table that can be used to connect the park and state tables.
-- Modify the previous query to retrieve the names of the states rather than their abbreviations.


-- Modify the previous query to include the name of the state's capital city.
SELECT park_name, state_name, state.state_abbreviation
FROM park
INNER JOIN park_state ON park.park_id = park_state.park_id
JOIN state ON park_state.state_abbreviation = state.state_abbreviation
JOIN city ON state.capital = city.city_ID;


-- Modify the previous query to include the area of each park.
-- SELECT city_name, c.population
-- FROM city c
-- WHERE census_region = 'Midwest'



-- Write a query to retrieve the names and populations of all the cities in the Midwest census region.
SELECT count(*) as numberOfCities, state_name
FROM city
JOIN state ON city.state_abbreviation = state.state_abbreviation
WHERE census_region = 'Midwest'
GROUP BY state_name
ORDER BY state_name

-- Write a query to retrieve the number of cities in the city table for each state in the Midwest census region.
SELECT count(*) as numberOfCities, state_name
FROM city
JOIN state ON city.state_abbreviation = state.state_abbreviation
WHERE census_region = 'Midwest'
GROUP BY state_name
ORDER BY numberOfCities DESC




-- Modify the previous query to sort the results by the number of cities in descending order.



-- LEFT JOIN

-- Write a query to retrieve the state name and the earliest date a park was established in 
-- that state (or territory) for every record in the state table that has park records associated with it.
SELECT state_name, MIN(date_established) as earliestParkDate
FROM state
JOIN park_state ON state.state_abbreviation = park_state.state_abbreviation
JOIN park ON park_state.park_id = park.park_id
GROUP BY state_name


-- Modify the previous query so the results include entries for all the records in the state table, even if they have no park records associated with them.



-- UNION

-- Write a query to retrieve all the place names in the city and state tables that begin with "W" sorted alphabetically. (Washington is the name of a city and a state--how many times does it appear in the results?)


-- Modify the previous query to include a column that indicates whether the place is a city or state.



-- MovieDB
-- After creating the MovieDB database and running the setup script, make sure it is selected in pgAdmin and confirm it is working correctly by writing queries to retrieve...

-- The names of all the movie genres


-- The titles of all the Comedy movies

