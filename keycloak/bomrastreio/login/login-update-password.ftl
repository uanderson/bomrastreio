<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=true; section>
  <#if section = "title">
    ${msg("updatePasswordTitle")}
  <#elseif section = "header">
    ${msg("updatePasswordTitle")}
  <#elseif section = "form">
    <form class="register-form" action="${url.loginAction}" method="post">
      <input type="text" readonly value="this is not a login form" style="display: none;">
      <input type="password" readonly value="this is not a login form" style="display: none;">

      <div class="form-group">
        <label for="password" class="control-label">${msg("passwordNew")}</label>
        <div class="input-icon">
          <i class="fa fa-lock"></i>
          <input type="password" id="password-new" name="password-new" class="form-control" autofocus autocomplete="off" />
        </div>
      </div>
      <div class="form-group">
        <label for="password-confirm" class="control-label">${msg("passwordConfirm")}</label>
        <div class="controls">
          <div class="input-icon">
            <i class="fa fa-check"></i>
            <input type="password" id="password-confirm" name="password-confirm" class="form-control" autocomplete="off" />
          </div>
        </div>
      </div>

      <div class="form-actions">
        <button type="submit" class="btn green pull-right">${msg("doSubmit")}</button>
      </div>
    </form>
  </#if>
</@layout.registrationLayout>