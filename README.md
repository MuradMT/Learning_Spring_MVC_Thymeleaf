# All essential notes about Thymeleaf:
1. Thymeleaf is template engine.Spring MVC is a web module of spring boot.When we create a new project we should add  Thymeleaf and Web package on dependencies part.We generally follow MVC stucture here.

2.Here on top of controllers we use @Controller annotation.It redirects us to pages do not try to return data.

3.We can use Model on controller parameters and can add attributes to it.

```java
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Hello World!");
        return "index";
    }
}

```

4.Here on resources/templates we just create html files.If i create index.html,then in controller when i say return “index” it will open index html file.

5.

In Thymeleaf, the prefix `**th:**` stands for **"Thymeleaf attribute"**.

> It tells the Thymeleaf engine:
>
>
> **"Hey, I want to dynamically generate or replace this part of the HTML using server-side logic."**
>

---

## Examples of Common `th:` Attributes

| Thymeleaf Attribute | Purpose |
| --- | --- |
| `th:text` | Replaces inner text of the tag |
| `th:href` | Dynamically sets `href` attribute in `<a>` tag |
| `th:src` | Sets image source |
| `th:value` | Sets the `value` of an input or option tag |
| `th:each` | Loops over a list (like `forEach`) |
| `th:if` / `th:unless` | Conditional rendering |
| `th:object`, `th:field` | Used in forms for model binding |

### Example

```html
html
CopyEdit
<a th:href="@{/students}">View Students</a>

```

This will generate:

```html
html
CopyEdit
<a href="/students">View Students</a>

```

Or:

```html
html
CopyEdit
<span th:text="${user.name}">Default</span>

```

Will render:

```html
html
CopyEdit
<span>Murad</span>

```

Example usages in View side:

```java
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head><title>Students</title></head>
<body>
    <h2>Student List</h2>
    <ul>
        <li th:each="student : ${students}" th:text="${student}">Name</li>
    </ul>
</body>
</html>
```

6.ORM for OOP languages,JPA is ORM implementation in java,Hibernate is library built on top of jap.That is all.

<h1>Testing</h1>

### 1. What is Testing?

Testing ensures your code works as intended. It checks **correctness**, **stability**, and **performance**. You write tests that run your code and assert expected outcomes.

---

### 2. Types of Testing

| Type | Description | Example Tools |
| --- | --- | --- |
| Unit Testing | Test individual methods/classes in isolation | JUnit, TestNG |
| Integration Testing | Test combined components (e.g., service + repo) | Spring Boot Test, JUnit |
| Functional Testing | Validate functionality based on business requirements | JUnit, RestAssured |
| End-to-End (E2E) | Simulate real user actions across the full stack | Selenium, RestAssured |
| Mock Testing | Replace real dependencies with fakes/mocks | Mockito |
| TDD (Test-Driven Development) | Write tests before writing actual code, guiding development | JUnit, Mockito |
| BDD (Behavior-Driven Development) | Focuses on behavior and examples using human-readable syntax | Cucumber, JBehave |

---

### 3. Common JUnit Assertions (with Optional Messages)

| Method | Usage Example | Description |
| --- | --- | --- |
| `assertEquals(a, b)` | `assertEquals(5, sum(2,3), "Sum should be 5")` | Checks if `a == b` |
| `assertNotEquals(a, b)` | `assertNotEquals(6, sum(2,3), "Should not be 6")` | Checks if `a != b` |
| `assertTrue(condition)` | `assertTrue(age > 18, "Age must be greater than 18")` | Passes if condition is true |
| `assertFalse(condition)` | `assertFalse(list.isEmpty(), "List should not be empty")` | Passes if condition is false |
| `assertNull(object)` | `assertNull(obj.getValue(), "Value must be null")` | Checks object is null |
| `assertNotNull(object)` | `assertNotNull(obj.getId(), "ID should not be null")` | Checks object is not null |
| `assertSame(a, b)` | `assertSame(a, b, "Objects must be the same reference")` | Checks if `a == b` (reference equality) |
| `assertNotSame(a, b)` | `assertNotSame(a, b, "Objects must not be the same")` | Checks if `a != b` |
| `assertArrayEquals(a, b)` | `assertArrayEquals(expected, actual, "Arrays differ")` | Compares arrays |
| `assertThrows(Exception.class)` | `assertThrows(IllegalArgumentException.class, () -> { ... })` | Passes if exception is thrown |

---

### 4. Structure of a Good Unit Test

```
@Test
void testLoginSuccess() {
    // Arrange
    AuthService service = new AuthService();

    // Act
    boolean result = service.login("admin", "password");

    // Assert
    assertTrue(result, "Login should succeed with correct credentials");
}
```

---

### 5. JUnit Lifecycle Annotations

| Annotation | Description |
| --- | --- |
| `@Test` | Marks a test method |
| `@BeforeEach` | Runs before each test method |
| `@AfterEach` | Runs after each test method |
| `@BeforeAll` | Runs once before all tests in the class (must be static) |
| `@AfterAll` | Runs once after all tests in the class (must be static) |
| `@Disabled` | Disables the test temporarily |
| `@Nested` | Groups tests inside inner test classes |
| `@DisplayName` | Sets a custom name for a test |

```
@BeforeEach
void init() {
    service = new AuthService();
}

@AfterEach
void tearDown() {
    service = null;
}
```

---

### 6. Mockito Basics and Explanation

**Mockito** is a mocking framework that allows you to simulate dependencies so you can test units in isolation. It helps isolate business logic by replacing real objects (like DBs or APIs) with controllable mock versions.

**Key Concepts:**

- **@Mock**: Declares a mock object.
- **@InjectMocks**: Injects the mock objects into the object being tested.
- **when(...).thenReturn(...)**: Mocks the expected return of a method.
- **verify(...)**: Verifies if a method was called with expected arguments.

**Example:**

```
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserService userService;

    @Test
    void testFindUserById() {
        // Arrange
        when(userRepo.findById(1L)).thenReturn(Optional.of(new User("Murad")));

        // Act
        User result = userService.getUser(1L);

        // Assert
        assertEquals("Murad", result.getName(), "User name should match");
        verify(userRepo).findById(1L);
    }
}
```

---

### 7. Integration Testing with Spring Boot

```
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetProduct() throws Exception {
        mockMvc.perform(get("/products/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("iPhone"));
    }
}
```

---

### 8. Code Coverage

**Code coverage** measures how much of your code is exercised by tests.

| Tool | Usage |
| --- | --- |
| JaCoCo | Popular Gradle/Maven plugin |
| IntelliJ | Built-in code coverage viewer |

**With Gradle (example):**

```
plugins {
    id 'jacoco'
}

test {
    useJUnitPlatform()
    finalizedBy jacocoTestReport
}

jacocoTestReport {
    reports {
        html.required = true
    }
}
```

Run:

```
./gradlew test jacocoTestReport
```

View HTML report in `build/reports/jacoco/test/html`

---

### 9. Test-Driven Development (TDD)

TDD is a software development approach where you:

1. **Write a failing test first** (Red)
2. **Write just enough code to pass the test** (Green)
3. **Refactor** to improve design without changing behavior (Refactor)

**Benefits:**

- Guides design
- Ensures test coverage
- Encourages small, maintainable code

**Example Flow:**

```
@Test
void shouldReturnZeroForEmptyList() {
    assertEquals(0, calculator.sum(Collections.emptyList()));
}
```

> Write this before the method sum() even exists.
>

---

### 10. Behavior-Driven Development (BDD)

BDD encourages writing tests in **natural language style** to describe application behavior. It’s focused on collaboration and clarity using tools like **Cucumber** or **JBehave**.

**Benefits:**

- Improves communication between technical and non-technical people
- Defines clear acceptance criteria
- Makes tests more readable and business-focused

**Example (Cucumber):**

```
Feature: Login
  Scenario: Valid credentials
    Given user is on the login page
    When user enters valid credentials
    Then user should be redirected to the dashboard
```

Mapped to step definitions in Java using annotations like `@Given`, `@When`, and `@Then`.

---

### 11. Tips for Writing Good Tests

- Keep tests independent and isolated
- Use descriptive method names like `shouldReturnUserWhenIdIsValid`
- Test positive, negative, and edge cases
- Mock dependencies using Mockito
- Include assertion messages for clarity
- Use coverage tools like JaCoCo or Cobertura to measure test effectiveness

---

### 12. Tools for Testing Ecosystem

| Tool | Purpose |
| --- | --- |
| JUnit 4/5 | Core unit testing frameworks |
| Mockito | Mocking dependencies |
| AssertJ | Advanced fluent assertion library |
| Hamcrest | Matcher-based assertions for readability |
| Spring Test | Integration testing with Spring context |
| Testcontainers | Real DBs, queues, etc. in Docker for tests |
| Cucumber | BDD with Gherkin syntax |
| JBehave | Alternative BDD framework for Java |

---

<h1>Linux</h1>

---

**1. Basic Shell Navigation**

```bash
pwd         # Print current directory
ls          # List files and directories
cd /path    # Change to specific directory
cd ~        # Go to home directory
cd ..       # Go up one level

```

---

**2. File and Directory Operations**

```bash
touch file.txt         # Create a new file
mkdir folder           # Create a new directory
rm file.txt            # Delete a file
rm -r folder           # Delete directory recursively
cp file1 file2         # Copy file
mv old new             # Rename or move file
cat file.txt           # Display file contents
less file.txt          # Scroll through file

```

---

**3. File Permissions and Ownership**

```bash
chmod +x script.sh     # Make script executable
chmod 755 file         # Set read/write/execute for owner
chown user:group file  # Change file owner and group
ls -l                  # View permissions

```

---

**4. Process Management**

```bash
ps aux                 # View running processes
top                    # Live system process viewer
htop                   # Enhanced version of top (optional)
kill <PID>             # Terminate a process
kill -9 <PID>          # Force kill

```

To stop a Java process:

```bash
ps aux | grep java
kill <PID>

```

---

**5. Java Commands**

```bash
java -version                  # Check installed Java version
javac MyClass.java             # Compile Java source
java MyClass                   # Run Java class
jar cf myapp.jar *.class       # Create JAR file
java -jar myapp.jar            # Run a JAR

```

Set environment variables:

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
export PATH=$JAVA_HOME/bin:$PATH

```

---

**6. Network Commands**

```bash
ping google.com               # Test connectivity
curl http://localhost:8080    # Call endpoint
netstat -tulnp                # Check open ports
ss -tulwn                     # Alternative to netstat
telnet localhost 8080        # Test port access

```

---

**7. Package Management**

On Ubuntu/Debian:

```bash
sudo apt update
sudo apt install openjdk-17-jdk
sudo apt install maven

```

On CentOS/RHEL:

```bash
sudo yum install java-17-openjdk
sudo yum install maven

```

---

**8. Systemd and Services**

```bash
sudo systemctl start myapp
sudo systemctl stop myapp
sudo systemctl status myapp
sudo systemctl enable myapp

```

Example service file at `/etc/systemd/system/myapp.service`:

```
[Unit]
Description=My Java App

[Service]
ExecStart=/usr/bin/java -jar /home/user/myapp.jar
User=user
Restart=always

[Install]
WantedBy=multi-user.target

```

---

**9. Log Management**

```bash
tail -f logs/app.log        # Live monitor logs
less /var/log/syslog        # Scroll through system log
grep ERROR logs/app.log     # Search log for specific terms

```

---

**10. SSH and PuTTY (for Windows Users)**

Using PuTTY:

1. Download PuTTY from putty.org
2. Enter server IP in "Host Name"
3. Use port 22, connection type SSH
4. Login with username and password

For private key login:

- Convert `.pem` to `.ppk` using PuTTYgen
- In PuTTY: Connection > SSH > Auth > Browse to `.ppk` file

From Git Bash or WSL:

```bash
ssh username@host

```

---

**11. Environment Variables**

Temporary:

```bash
export DB_USER=myuser
export SPRING_PROFILES_ACTIVE=prod

```

Permanent: Add to `~/.bashrc` or `~/.bash_profile`, then:

```bash
source ~/.bashrc

```

---

**12. Build Tools**

Maven:

```bash
mvn clean install
mvn spring-boot:run

```

Gradle:

```bash
./gradlew build
./gradlew bootRun

```

---

**13. Vim Basics (Editing on Server)**

```bash
vim file.txt       # Open file
i                  # Enter insert mode
Esc + :wq          # Save and quit
Esc + :q!          # Quit without saving

```

---

**14. Useful Shortcuts**

```bash
Ctrl + C   # Interrupt running process
Ctrl + D   # Logout or end input
Ctrl + R   # Search command history
history    # Show command history
!!         # Run previous command

```

---

**15. Backup and File Transfer**

```bash
scp myapp.jar user@host:/path/
rsync -avz target/ user@host:/deploy/

```

---

**16. Scheduling with Cron**

```bash
crontab -e

# Run JAR daily at 2am
0 2 * * * java -jar /home/user/myapp.jar >> /home/user/logs/cron.log 2>&1

```

---

**Summary Checklist**

| Category | Key Commands |
| --- | --- |
| Navigation | `cd`, `ls`, `pwd` |
| File ops | `cp`, `mv`, `rm`, `chmod` |
| Java runtime | `java`, `javac`, `jar` |
| Process mgmt | `ps`, `kill`, `top`, `htop` |
| SSH | `ssh`, PuTTY |
| Network | `curl`, `ping`, `netstat`, `ss` |
| Services | `systemctl` |
| Logs | `tail`, `grep`, `less` |
| Build tools | `mvn`, `gradle` |
| Environment | `export`, `.bashrc` |
| Scheduling | `crontab` |

---