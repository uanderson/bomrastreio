<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=true; section>
    <#if section = "title">
        ${msg("emailForgotTitle")}
    <#elseif section = "header">
        ${msg("emailForgotTitle")}
    <#elseif section = "form">
      <form class="update-password-form" action="${url.loginAction}" method="post">
        <div class="form-group">
          <label for="username" class="control-label">
              <#if !realm.loginWithEmailAllowed>
                ${msg("username")}
              <#elseif !realm.registrationEmailAsUsername>
                ${msg("usernameOrEmail")}
              <#else>
                ${msg("email")}
              </#if>
          </label>
          <div class="input-icon">
            <i class="fa fa-user"></i>
            <input type="text" id="username" name="username" class="form-control" autofocus/>
          </div>
        </div>
        <div class="form-actions">
          <a href="${url.loginUrl}" class="btn grey-salsa btn-outline">${msg("backToLogin")}</a>
          <button type="submit" class="btn green pull-right">${msg("doSubmit")}</button>
        </div>
      </form>
    <#elseif section = "info" >
      ${msg("emailInstruction")}
    </#if>
</@layout.registrationLayout>
