<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    User editor
    <form action="/user" method="post">
        <input type="text" name="username" value="${user.username!"null or misssing"}"> <!-- поле для редактирования имени пользователя -->

        <!-- отображение списка ролей -->
        <!-- итерация по списку ролей -->
        <!-- name="{role}" -- название роли -->
        <!-- {user.roles?seq_contains(role)?string("checked", "") – пометка активна или неактивна данная роль, -->
        <!-- user.roles?seq_contains(role) - проверка наличия элемента в коллекции, метод возвращает булево знач -->
        <!-- ?string("checked", "") -- преобразование в строку -->
        <#list roles as role>
            <div>
                <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
            </div>
        </#list>

        <input type="hidden" value="${user.id}" name="userId"> <!-- идентификатор пользователя -->
        <input type="hidden" value="${_csrf.token}" name="_csrf"> <!-- без токена форма не будет принята сервером -->
        <button type="submit">Save</button>
    </form>
</@c.page>