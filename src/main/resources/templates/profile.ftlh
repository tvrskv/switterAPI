<#import "parts/common.ftlh" as c>

<@c.page>
    <h5>${username}</h5>
    ${message?ifExists}  <!-- отображение сообщений -->
        <form method="post">
            <!-- e-mail form -->
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label">E-mail:</label>
                        <div class="col-sm-4">
                            <input type="email" name="email" class="form-control" placeholder="some@some.com" value="${email!''}"/>
                        </div>
                    </div>


            <!-- Password form -->
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">Password:</label>
                    <div class="col-sm-4">
                        <input type="password" name="password" class="form-control" placeholder="password"/>
                    </div>
                </div>

            <!-- Sign in button -->
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-primary ml-5" type="submit">Save</button>
        </form>
</@c.page>