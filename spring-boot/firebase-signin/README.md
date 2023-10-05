POST
https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key={API_KEY}

API_KEY can be fetch from Project Settings in firebase console
~~~
{
    "email": "[EMAIL]",
    "password": "[PASSWORD]",
    "returnSecureToken": true
}
~~~


Endpoint to check Auth header:

http://localhost:8080/auth/check
