
<h1>TASK APP CLIENT-SERVER</h1>
<h2>Using: java in Backend and Javascript in Frontend</h2>
<p>This is a basic application with tasks, based on client-server development. With the programming languages; java using the spring framework for the backend and Javascript using Angular in the frontend.</p>
<hr>

<h2>ROUTES SUPPORTED</h2>
<ul>
    <li>Route ROOT BASE Is "/api"</li>
    <li>From tasks</li>
        <ul>
            <li>
                Method: GET, ROUTE: "/task"
                <p>This method allows to obtain all the tasks that are in the DB.</p>
            </li>
            <li>
                Method: GET, ROUTE: "/task/{idUser}", params: idUser, 
                <p> This method allows you to search for tasks created by a specific user. </p> 
            </li>
            <li>
                Method: POST, ROUTE: "/task/{idUser}", params: idUser, task (json)
                <p>This method receives by path variable the id of the user to which the task belongs. In addition, by Request body (JSON) it receives the task in question. </p>
            </li>
            <li>
                Method: PUT, ROUTE: ""
            </li>
        </ul>
</ul>

<hr>
##BACKEND

###Codes error

<p>These codes represent error in the logic backend-server. As request or errors or procesing data</p>

###Our own codes errors

- Code: 1000, represent error in user or data  of user.
    - 1001: user not exist
    - 1002: user exist (mainly in the registry)
