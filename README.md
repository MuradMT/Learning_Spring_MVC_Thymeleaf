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
