<#macro login path isRegisterForm>
    <form action="${path}" method="post">

        <!-- Username form -->
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Username:</label>
            <div class="col-sm-4">
                <input type="text" name="username" class="form-control" placeholder="username"/>
            </div>
        </div>

        <!-- e-mail form -->
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">E-mail:</label>
                <div class="col-sm-4">
                    <input type="email" name="email" class="form-control" placeholder="e-mail"/>
                </div>
            </div>
        </#if>

        <!-- Password form -->
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-4">
                <input type="password" name="password" class="form-control" placeholder="password"/>
            </div>
        </div>

        <!-- Sign in button -->
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if !isRegisterForm><a href="/registration">Add new user</a></#if>
        <button class="btn btn-primary ml-5" type="submit"><#if isRegisterForm>Create<#else>Sign In</#if></button>
    </form>
</#macro>

<!-- Sign out button-->
<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary ml-5" type="submit">Sign Out</button>
    </form>
</#macro>

