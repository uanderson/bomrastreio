<#macro registrationLayout bodyClass="" displayInfo=false displayMessage=true>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8"/>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <meta content="width=device-width, initial-scale=1" name="viewport"/>
  <meta content="Preview page of Metronic Admin Theme #5 for " name="description"/>
  <meta name="robots" content="noindex, nofollow"/>

  <!-- Required -->
  <link href="//fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/>
  <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
  <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">

  <!-- Global -->
  <link href="//web.bomrastreio.com.br/static/css/metronic/css/components-rounded.min.css" rel="stylesheet" type="text/css"/>
  <link href="//web.bomrastreio.com.br/static/css/metronic/css/plugins.min.css" rel="stylesheet" type="text/css"/>
  <link href="//web.bomrastreio.com.br/static/css/metronic/css/login.min.css" rel="stylesheet" type="text/css"/>
  <link href="${url.resourcesPath}/css/custom.css" rel="stylesheet" type="text/css"/>

  <title><#nested "title"></title>
  <link rel="icon" href="${url.resourcesPath}/img/favicon.ico" />
</head>
<body class="login">
  <div class="logo">
    <a href="//bomrastreio.com.br">
      <img src="//web.bomrastreio.com.br/static/img/logo-login.png" />
    </a>
  </div>
  <div class="content">
    <div class="header">
      <h3><#nested "header"></h3>
    </div>

    <#if displayMessage && message?has_content>
      <div class="feedback">
        <#if message.type = 'success'><#assign messageType = 'success'></#if>
        <#if message.type = 'warning'><#assign messageType = 'warning'></#if>
        <#if message.type = 'error'><#assign messageType = 'danger'></#if>
        <#if message.type = 'info'><#assign messageType = 'info'></#if>

        <div class="alert alert-${messageType}">
          ${message.summary}
        </div>
      </div>
    </#if>

    <div>
      <#nested "form">
    </div>

    <div>
      <#nested "info">
    </div>
  </div>
</body>
</html>
</#macro>
