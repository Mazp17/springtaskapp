
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
                Method: GET, ROUTE: "/task" | 
                <p>This method allows to obtain all the tasks that are in the DB.</p>
            </li>
            <li>
                Method: GET, ROUTE: "/task/{idUser}", params: idUser | 
                <p> This method allows you to search for tasks created by a specific user. </p> 
            </li>
        </ul>
</ul>