<#import "parts/common.ftlh" as c>

<@c.page>
    List of users
    <table>
        <head>
            <tr>
                <th>Name</th>
                <th>Role</th>
                <th></th>
            </tr>
        </head>
        <body>
        <#list users as user>
            <tr>
                <td>${user.username!"null or missing"}</td>
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/user/${user.id}">edit</a></td>
            </tr>
        </#list>
        </body>
    </table>
</@c.page>