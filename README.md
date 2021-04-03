# Addressing requirements
* The application should use the hosted API described in GitHub page provided in Description.
  ## External API 
  ### Technologies
  * Python 3.7
  ## To run External API
	* Clone Project git clone https://github.com/hipo/university-domains-list.git 
	* Setup and activate virtual environment
	* Install requirements ```pip install -r requirements.txt```
	* Run server ```python app.py```

	External API runs at http://127.0.0.1:5000/ (localhost)
	
  ## Consumer API
  ### Technologies
  * Java: 1.8
  * Spring Boot: 2.5.0  
  * Project build manager: Apache Maven 3.5.2
  * IDE: Eclipse
  * Postman to perform API queries
 
   ### Project's implementation
   The consumer API runs on default port: 8080. 3 endpoints were implemented:
   * ```/universities```: Supports the listing of all available universities and pagination. Returns page of universities with the following JSON response:
		```
		[
		{"url": ["http://www.example.com/"], "name": "Example University", "country": "Exaple Country"},
		{"url": ["http://www.example2.com/"], "name": "Example University 2", "country": "Exaple Country 2"}
		]
		``` 
		Default page number and size from pageable are:	
		```
		page=0&size=20
		```
		
		#### Examples
		```
		http://localhost:8080/universities
		http://localhost:8080/universities?page=5
		http://localhost:8080/universities?page=10&size=40
		```
		
   * ```/universities/{country}```: Supports searching available universities by country returning. Returns a list of all universities by country JSON response in the form:
        ```
		[
		{"url": ["http://www.example.com/"], "name": "Example University"},
		{"url": ["http://www.example2.com/"], "name": "Example University 2"}
		]
		```
		
		#### Examples
		```
		http://localhost:8080/universities/Canada
		```
		
   * ```/universities/statistics```: Supports a statictics report where the total number of universities for each country is provided in the following JSON response:   
   ```
	[
	{"country": "Example Country Name", "number_of_universities": "100"},
	{"country": "Example Country Name 2", "number_of_universities": "0"}
	]
   ```
   
		#### Examples
		```
		http://localhost:8080/universities/statistics
		```
   
   ### The application design should address (assuming one docker instance):
	* High request throughput.
	* Multiple concurrent clients.
	
	  To address the above requirements WebClient is used for HTTP calls. WebClient is the reactive HTTP client that is part of Spring WebFlux. It provides a reactive, non-blocking interface for sending HTTP requests.
	         
	  
   ### The application project should be runnable through Docker
   1. Use Google's Jib. Add maven dependency to pom.xml
   ```
               <plugin>
                <groupId>com.google.cloud.tools</groupId>
                <artifactId>jib-maven-plugin</artifactId>
                <configuration>
                    <to>
                        <image>${image.path}</image>
                    </to>
                </configuration>
            </plugin>
   ```
   
      where ${image.path} should declare the repo/registry that the image should be pushed to and the image name.
   2. Provide repo credentials to .m2/settings.xml:
		```
		<servers>
			<server>
				<id>${registry}</id>
				<username><Username></username>
				<password><Password></password>
			</server>
		</servers>
		```
   3. ```mvn clean install jib:build``` to create and push docker image to defined registry
   4. In provided pom,xml this plugin is commented
   
   
# Assumptions
* External API updates it's data only once per day : 
  If the university dataset changes, the external API won't automatically update it. ```/update``` endpoint should be used to update it. University data can only be updated once in a day. As we do not know the exact time of the update it is a safe assumption that the data will be updated by end of day. As a result, a cron job is running every day at 23.59.00(one minute before end of day) to ensure data are updated. 
  There are two kind of JSON responses on external API update: 
  * success: ```{"status": "success", "message": "Dataset updated!"}``` .This is the response if ```/update``` endpoint is invoked for the first time in a day
  * error: ```{"status": "error", "message": "Dataset had been updated recently. Try again later."}``` . This is the response if ```/update``` endpoint is invoked again after data update
  This JSON response is mapped to a UpdateResponseEntity DTO. Logs are generated according to response's status.
  
* External API may not always be available
  In case external API is not available and ```/update``` endpoint is invoked. Webflux Mono has two concepts for re-subscribing (and thus, re-triggering the request):
  * retry = re-subscribe if the upstream completed with an exception
  * repeat = re-subscribe if the upstream completed successfully

  In this case retryWhen is needed. 
  ```

  .retryWhen(Retry.fixedDelay(11, Duration.ofSeconds(5)))

  ```
  It is used to retry if the mono completed with an exception a maximum of 11 times with 5 seconds between each attempt. By doing this, the action will be performed every 5 seconds for the next minute. When external API is available again, the action is performed. If external API is not available after the retries, data will be updated next night. Cron and retries should change upon business requirements(e.g. if we know that data update is at 15.00.00 , cron job could be scheduled at 15.05.00 and retries could be schediled for the rest of the day in case external API is unavailable).

# Run application
  1. Clone project git clone: https://github.com/soumkon/projects
  2. Navigate to ```/university-api-consumer```
  3. mvn clean install
  4. mvn spring-boot:run
  5. To run external api  	
    * Clone Project git clone https://github.com/hipo/university-domains-list.git 
	* Setup and activate virtual environment
	* Install requirements ```pip install -r requirements.txt```
	* Run server ```python app.py```
