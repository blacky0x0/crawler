**Running the application**

Type the following command in your shell:

```
mvn jetty:run
```

After the application starts it will be available 
via the [localhost:8080](http://localhost:8080) in your browser.

**There are two REST interfaces**

REST uri     |    HTTP method | Response format  | Accept
------------ | -------------- | ---------------  | ---------------
`<domain>/rest/tasks/new` | GET | `application/json` | key-value pairs with keys: `domain` and `keyword`
`<domain>/rest/tasks/{id}`| GET | `application/json` | path variable, where `{id}` is integer number  

Request example | Response example
--------------- | -----------------
`http://localhost:8080/rest/tasks/new?domain=example.com&keyword=example`  | `{"id":1}`
`http://localhost:8080/rest/tasks/1` | `{"id":1,"domain":"http://example.com", "keyword":"example","title":"Example Domain","amountWordsInBody":30,"density":{"h1":50,"title":50,"body":6}}`
`http://localhost:8080/rest/tasks/42` | `{"status":-1}`
