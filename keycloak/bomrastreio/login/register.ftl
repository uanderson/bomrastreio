<#import "template.ftl" as layout>
<@layout.registrationLayout; section>
  <#if section = "title">
    ${msg("c_registerWithTitle")}
  <#elseif section = "header">
    ${msg("c_registerWithHeader")}
  <#elseif section = "form">
    <form class="register-form" action="${url.registrationAction}" method="post">
      <input type="text" readonly value="this is not a login form" style="display: none;">
      <input type="password" readonly value="this is not a login form" style="display: none;">

      <p>${msg("c_enterPersonalDetail")}</p>

      <div class="form-group">
        <label for="firstName" class="control-label">${msg("firstName")}</label>
        <div class="input-icon">
          <i class="fa fa-font"></i>
          <input type="text" id="firstName" class="form-control" name="firstName" value="${(register.formData.firstName!'')?html}" />
        </div>
      </div>
      <div class="form-group">
        <label for="lastName" class="control-label">${msg("lastName")}</label>
        <div class="input-icon">
          <i class="fa fa-font"></i>
          <input type="text" id="lastName" class="form-control" name="lastName" value="${(register.formData.lastName!'')?html}" />
        </div>
      </div>
      <div class="form-group">
        <label for="email" class="control-label">${msg("email")}</label>
        <div class="input-icon">
          <i class="fa fa-envelope"></i>
          <input type="text" id="email" class="form-control" name="email" value="${(register.formData.email!'')?html}" />
        </div>
      </div>

      <p>${msg("c_enterAccountDetail")}</p>

      <#if !realm.registrationEmailAsUsername>
        <div class="form-group">
          <label class="control-label" for="username">${msg("username")}</label>
          <div class="input-icon">
            <i class="fa fa-user"></i>
            <input type="text" id="username" class="form-control" name="username" value="${(register.formData.username!'')?html}" />
          </div>
        </div>
      </#if>

      <#if passwordRequired>
        <div class="form-group">
          <label for="password" class="control-label">${msg("password")}</label>
          <div class="input-icon">
            <i class="fa fa-lock"></i>
            <input type="password" id="password" class="form-control" name="password" autocomplete="off" />
          </div>
        </div>
        <div class="form-group">
          <label for="password-confirm" class="control-label">${msg("passwordConfirm")}</label>
          <div class="controls">
            <div class="input-icon">
              <i class="fa fa-check"></i>
              <input type="password" id="password-confirm" class="form-control" name="password-confirm" autocomplete="off" />
            </div>
          </div>
        </div>
      </#if>

      <#if recaptchaRequired??>
        <div class="form-group">
          <div class="g-recaptcha" data-size="compact" data-sitekey="${recaptchaSiteKey}"></div>
        </div>
      </#if>

      <div class="form-actions">
        <a href="${url.loginUrl}" class="btn grey-salsa btn-outline">${msg("backToLogin")}</a>
        <button type="submit" class="btn green pull-right">${msg("doRegister")}</button>
      </div>
    </form>
  </#if>
</@layout.registrationLayout>