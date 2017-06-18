<#import "template.ftl" as layout>
<@layout.registrationLayout; section>
  <#if section = "title">
    ${msg("loginProfileTitle")}
  <#elseif section = "header">
    ${msg("loginProfileTitle")}
  <#elseif section = "form">
    <form class="update-profile-form" action="${url.loginAction}" method="post">
      <input type="text" readonly value="this is not a login form" style="display: none;">
      <input type="password" readonly value="this is not a login form" style="display: none;">

      <p>${msg("c_updatePersonalDetail")}</p>

      <div class="form-group">
        <label for="firstName" class="control-label">${msg("firstName")}</label>
        <div class="input-icon">
          <i class="fa fa-font"></i>
          <input type="text" id="firstName" name="firstName" value="${(user.firstName!'')?html}" class="form-control" />
        </div>
      </div>
      <div class="form-group">
        <label for="lastName" class="control-label">${msg("lastName")}</label>
        <div class="input-icon">
          <i class="fa fa-font"></i>
          <input type="text" id="lastName" name="lastName" value="${(user.lastName!'')?html}" class="form-control" />
        </div>
      </div>
      <div class="form-group">
        <label for="email" class="control-label">${msg("email")}</label>
        <div class="input-icon">
          <i class="fa fa-envelope"></i>
          <input type="text" id="email" name="email" value="${(user.email!'')?html}" class="form-control" />
        </div>
      </div>

      <#if user.editUsernameAllowed>
        <p>${msg("c_updateAccountDetail")}</p>
      </#if>

      <#if user.editUsernameAllowed>
        <div class="form-group">
          <label class="control-label" for="username">${msg("username")}</label>
          <div class="input-icon">
            <i class="fa fa-user"></i>
            <input type="text" id="username" name="username" class="form-control" value="${(user.username!'')?html}" />
          </div>
        </div>
      </#if>

      <div class="form-actions">
        <button type="submit" class="btn green pull-right">${msg("doSubmit")}</button>
      </div>
    </form>
  </#if>
</@layout.registrationLayout>