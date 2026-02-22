# 🏗️ REST API Infrastructure Design - EMR Project

## 📋 סקירה כללית

תשתית REST API מצומצמת וממוקדת לשימוש עם EMR API בלבד.
- **מטרה:** שליחת בקשות ל-API (GET, POST, PUT, DELETE)
- **Responses:** Strings בלבד (לא צריך Object mapping)
- **ספריות:** RestAssured, Gson (אם צריך parsing), Lombok

---

## 🎯 מבנה הפרויקט

```
src/test/java/api/
├── base/
│   └── BaseApiClient.java                    # Base class with basic HTTP methods
├── clients/
│   └── EmrApiClient.java                     # EMR-specific implementation
├── models/
│   └── request/
│       └── LoginRequest.java                 # Request objects
└── tests/
    └── EmrApiTests.java                      # Test cases
```

---

## 🔧 BaseApiClient.java

**מטרה:** כל ה-HTTP methods בסיסיים (GET, POST, PUT, DELETE)

```java
public abstract class BaseApiClient {
    
    protected String baseUrl;
    protected RequestSpecification requestSpec;
    protected static final Logger logger = LoggerFactory.getLogger(BaseApiClient.class);
    
    // Constructor
    public BaseApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        initializeRequestSpec();
    }
    
    // Initialize RestAssured
    protected void initializeRequestSpec() {
        requestSpec = RestAssured.given()
            .baseUri(baseUrl)
            .contentType("application/json")
            .accept("application/json")
            .log().all();
    }
    
    // Basic HTTP Methods - return String only
    protected String get(String endpoint) {
        logger.info("GET request to: {}", endpoint);
        Response response = requestSpec.get(endpoint);
        logger.info("Response Status: {}", response.getStatusCode());
        return response.asString();
    }
    
    protected String post(String endpoint, Object body) {
        logger.info("POST request to: {}", endpoint);
        Response response = requestSpec.body(body).post(endpoint);
        logger.info("Response Status: {}", response.getStatusCode());
        return response.asString();
    }
    
    protected String put(String endpoint, Object body) {
        logger.info("PUT request to: {}", endpoint);
        Response response = requestSpec.body(body).put(endpoint);
        logger.info("Response Status: {}", response.getStatusCode());
        return response.asString();
    }
    
    protected String delete(String endpoint) {
        logger.info("DELETE request to: {}", endpoint);
        Response response = requestSpec.delete(endpoint);
        logger.info("Response Status: {}", response.getStatusCode());
        return response.asString();
    }
    
    // Utility methods
    protected void addHeader(String key, String value) {
        requestSpec.header(key, value);
    }
    
    protected void addQueryParam(String key, String value) {
        requestSpec.queryParam(key, value);
    }
    
    protected void addPathParam(String key, String value) {
        requestSpec.pathParam(key, value);
    }
    
    protected void addBearerToken(String token) {
        requestSpec.header("Authorization", "Bearer " + token);
    }
    
    protected void resetRequestSpec() {
        initializeRequestSpec();
    }
}
```

---

## 📱 EmrApiClient.java

**מטרה:** EMR-specific API calls

```java
public class EmrApiClient extends BaseApiClient {
    
    public static final String BASE_URL = "https://lanwebapp.laniado.org.il/emrServerApi/api";
    
    public EmrApiClient() {
        super(BASE_URL);
    }
    
    public EmrApiClient(String customBaseUrl) {
        super(customBaseUrl);
    }
    
    // EMR-specific methods
    public String login(LoginRequest request) {
        return post("/ApiList/GetloginAuthentication", request);
    }
    
    public String getPatient(Integer patientId) {
        addPathParam("id", patientId.toString());
        return get("/Patient/{id}");
    }
    
    public String createAppointment(Object appointmentData) {
        return post("/Appointment/Create", appointmentData);
    }
    
    public String getAppointments(Integer patientId) {
        addPathParam("id", patientId.toString());
        return get("/Appointment/GetByPatient/{id}");
    }
    
    // Add more EMR endpoints as needed
}
```

---

## 📝 Request Objects (using Lombok)

### LoginRequest.java

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    
    @SerializedName("userId")
    private String userId;
    
    @SerializedName("password")
    private String password;
    
    @SerializedName("firstTime")
    private Boolean firstTime;
}
```

---

## 🧪 EmrApiTests.java

**מטרה:** Test cases ללא Object validation

```java
public class EmrApiTests {
    
    private EmrApiClient emrClient;
    
    @BeforeClass
    public void setUp() {
        emrClient = new EmrApiClient();
    }
    
    @Test(description = "Login to EMR API")
    public void testLogin() {
        LoginRequest request = new LoginRequest("test", "Te231121", true);
        
        String response = emrClient.login(request);
        
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertTrue(response.contains("login"));
    }
    
    @Test(description = "Get patient data")
    public void testGetPatient() {
        String response = emrClient.getPatient(12345);
        
        assertNotNull(response);
        assertTrue(response.contains("\"Id\""));
    }
    
    @Test(description = "Get appointments")
    public void testGetAppointments() {
        String response = emrClient.getAppointments(12345);
        
        assertNotNull(response);
    }
}
```

---

## 📦 Dependencies (pom.xml)

```xml
<!-- REST Assured -->
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.4.0</version>
</dependency>

<!-- Gson for JSON parsing (optional) -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.30</version>
    <scope>provided</scope>
</dependency>

<!-- SLF4J Logging -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.9</version>
</dependency>

<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.5.0</version>
</dependency>
```

---

## 💡 שימוש בטסטים

### דוגמה 1: Login פשוט
```java
EmrApiClient client = new EmrApiClient();
LoginRequest request = new LoginRequest("user", "password", true);
String response = client.login(request);

// Response הוא String - בדוק אם הצליח
assertTrue(response.contains("login"));
```

### דוגמה 2: עם JsonPath parsing
```java
String response = client.login(request);
String loginName = JsonPath.read(response, "$.login.LoginName");
Boolean isActive = JsonPath.read(response, "$.login.Active");
```

### דוגמה 3: עם custom base URL
```java
EmrApiClient client = new EmrApiClient("http://localhost:8080/api");
String response = client.login(request);
```

---

## ✨ Key Features

✅ **מחלקה בסיסית אחת** - כל ה-HTTP methods  
✅ **Responses כ-Strings** - ללא Object mapping  
✅ **Lombok** - Minimal boilerplate  
✅ **Logging** - Automatic via SLF4J  
✅ **Method chaining support** - Fluent API  
✅ **Easy to extend** - הוסף endpoints ב-EmrApiClient  

---

## 🔄 Flow

```
Test
  ↓
EmrApiClient.login(request)
  ↓
BaseApiClient.post("/endpoint", request)
  ↓
RestAssured sends request
  ↓
Returns response as String
  ↓
Test validates String response
```

---

## 📚 Future Extensions

אם תצטרך:
- **More endpoints** - הוסף methods ב-EmrApiClient
- **Authentication token** - השתמש ב-`addBearerToken(token)`
- **Custom headers** - השתמש ב-`addHeader(key, value)`
- **Query parameters** - השתמש ב-`addQueryParam(key, value)`
- **Another API** - צור `AnotherApiClient extends BaseApiClient`

---

## 🎯 סיכום

- **BaseApiClient** = Base functionality
- **EmrApiClient** = EMR-specific methods
- **LoginRequest** = Request object
- **EmrApiTests** = Tests with String responses
- **No Object mapping** = Simple, clean, maintainable

זה מוכן להממוש? 👍
