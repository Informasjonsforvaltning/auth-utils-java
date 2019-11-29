#FAQ

##What is the access string?
The access string is passed in the jwt and is used to define the rights a user has on a system. 

##How do I know if I have the correct access string?
1. Open a browser and go to ut1
2. Open the developers console on the tab network
3. Log in
4. Find the response called "token"
5. Copy the token and decode it for example using [jwt.io](https://jwt.io/)
6. See "authorities" field in token

##What is the audience?
The audience field in the jwt defines which services a user has access to. 

##How do I know if I have the correct audience?
1. Open a browser and go to ut1
2. Open the developers console on the tab network
3. Log in
4. Find the response called "token"
5. Copy the token and decode it for example using [jwt.io](https://jwt.io/)
6. See "aud" field in token


