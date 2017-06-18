<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=social.displayInfo; section>
  <#if section = "title">
    ${msg("c_loginTitle")}
  <#elseif section = "header">
    ${msg("c_loginHeader")}
  <#elseif section = "form">
    <form class="login-form" action="${url.loginAction}" method="post">
      <#if realm.password>
        <div class="form-group username">
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
            <#if usernameEditDisabled??>
              <input id="username" class="form-control" name="username" value="${(login.username!'')?html}" type="text" disabled />
            <#else>
              <input id="username" class="form-control" name="username" value="${(login.username!'')?html}" type="text" autofocus autocomplete="off" />
            </#if>
          </div>
        </div>
        <div class="form-group password">
          <label for="password" class="control-label">${msg("password")}</label>
          <div class="input-icon">
            <i class="fa fa-lock"></i>
            <input id="password" class="form-control" name="password" type="password" autocomplete="off" />
          </div>
        </div>

        <div class="form-actions">
          <#if realm.rememberMe && !usernameEditDisabled??>
            <label class="rememberme mt-checkbox mt-checkbox-outline">
              <#if login.rememberMe??>
                <input id="rememberMe" name="rememberMe" type="checkbox" tabindex="3" checked> ${msg("rememberMe")}
              <#else>
                <input id="rememberMe" name="rememberMe" type="checkbox" tabindex="3"> ${msg("rememberMe")}
              </#if>
              <span></span>
            </label>
          </#if>

          <input class="btn green pull-right" name="login" id="kc-login" type="submit" value="${msg("doLogIn")}"/>
        </div>

        <#if realm.resetPasswordAllowed || (realm.password && social.providers??)>
          <div class="item-divider"></div>
        </#if>

        <#if realm.password && social.providers??>
          <div class="login-options">
            <h4>${msg("c_orLoginWith")}</h4>
            <ul class="social-icons">
              <#list social.providers as p>
                <li><a href="${p.loginUrl}" class="${p.providerId}"></a></li>
              </#list>
            </ul>
          </div>
        </#if>

        <#if realm.resetPasswordAllowed>
          <div class="forget-password">
            <h4>${msg("c_forgetPasswordTitle")}</h4>
            <p>${msg("c_forgetPasswordText", '<a href="${url.loginResetCredentialsUrl}">', '</a>')}</p>
          </div>
        </#if>

        <#if realm.password && realm.registrationAllowed && !usernameEditDisabled??>
          <div class="item-divider"></div>
          <div class="create-account">
            <span>${msg("noAccount")} <a href="${url.registrationUrl}">${msg("doRegister")}</a></span>
          </div>
        </#if>
      </#if>
    </form>
  </#if>
</@layout.registrationLayout>
