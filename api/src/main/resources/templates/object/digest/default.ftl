<!doctype html>
<html>
<head>
  <meta name="viewport" content="width=device-width" />
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>Simple Transactional Email</title>
  <#include "/common/mail/style.ftl">

  <style>
    .object-column {
      padding-top: 4px;
      padding-bottom: 4px;
    }

    .label {
      text-align: center;
      white-space: nowrap;
      vertical-align: baseline;
      border-radius: .25em;
      text-shadow: none!important;
      font-size: 14px;
      font-weight: 300;
      padding: 3px 6px;
      color: #fff;
      line-height: 1;
      display: inline;
    }

    .label-DELIVERED,
    .label-success {
      background-color: #5cb85c;
    }

    .label-UNDEFINED,
    .label-PENDING,
    .label-default {
      background-color: #777;
    }

    .label-IN_TRANSIT,
    .label-info {
      background-color: #5bc0de;
    }

    .label-DELAYED,
    .label-warning {
      background-color: #f0ad4e;
    }

    .label-LOSS,
    .label-danger {
      background-color: #d9534f;
    }

    .label-ACTION_REQUIRED,
    .label-primary {
      background-color: #337ab7;
    }
  </style>
</head>
<body class="">
<table border="0" cellpadding="0" cellspacing="0" class="body">
  <tr>
    <td>&nbsp;</td>
    <td class="container">
      <div class="content">
        <span class="preheader">
          Seus objetos geraram ${referencesCount} evento(s) hoje
        </span>

        <#assign statuses = {
          "UNDEFINED": "Indefinido",
          "ACTION_REQUIRED": "Ação requerida",
          "DELAYED": "Atrasado",
          "DELIVERED": "Entregue",
          "IN_TRANSIT": "Em trânsito",
          "LOSS": "Extraviado",
          "PENDING": "Pendente"
        }>

        <table class="main">
          <tr>
            <td class="wrapper">
              <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td>
                    <p>Olá,</p>
                    <p>Aqui estão todos os eventos ocorridos hoje com os seus objetos</p>
                    <table border="0" cellpadding="0" cellspacing="0" style="margin-bottom: 16px;">
                      <tbody>
                      <tr>
                        <td align="left">
                          <table border="0" cellpadding="0" cellspacing="0">
                            <tbody>
                            <#list references as reference>
                              <tr>
                                <td class="object-column">
                                  <a href="http://websro.correios.com.br/sro_bin/txect01$.QueryList?P_LINGUA=001&P_TIPO=001&P_COD_UNI=${reference.code}" target="_blank">${reference.code}</a>
                                </td>
                                <td class="object-column">
                                  <span class="label label-${reference.status}">${statuses[reference.status]}</span>
                                </td>
                              </tr>
                            </#list>
                            </tbody>
                          </table>
                        </td>
                      </tr>
                      </tbody>
                    </table>
                    <p>Para saber mais informações sobre os seus objetos, acesse o site do <a href="https://web.bomrastreio.com.br" target="_blank">BomRastreio</a>.</p>
                    <p>Rastreador BomRastreio</p>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </div>
    </td>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
</html>
