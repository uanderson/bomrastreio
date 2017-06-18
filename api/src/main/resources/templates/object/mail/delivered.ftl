<!doctype html>
<html>
<head>
  <meta name="viewport" content="width=device-width" />
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>Objeto ${code} entregue</title>
  <#include "/common/mail/style.ftl">
</head>
<body class="">
  <table border="0" cellpadding="0" cellspacing="0" class="body">
    <tr>
      <td>&nbsp;</td>
      <td class="container">
        <div class="content">
          <span class="preheader">Objeto ${code} entregue</span>
          <table class="main">
            <tr>
              <td class="wrapper">
                <table border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td>
                      <p>Olá,</p>
                      <p>O seu objeto ${code} chegou ao destino em ${date} às ${time}.</p>
                      <table border="0" cellpadding="0" cellspacing="0" class="btn btn-primary">
                        <tbody>
                        <tr>
                          <td align="left">
                            <table border="0" cellpadding="0" cellspacing="0">
                              <tbody>
                              <tr>
                                <td>
                                  <a href="http://websro.correios.com.br/sro_bin/txect01$.QueryList?P_LINGUA=001&P_TIPO=001&P_COD_UNI=${code}" target="_blank">Visualizar objeto</a>
                                </td>
                              </tr>
                              </tbody>
                            </table>
                          </td>
                        </tr>
                        </tbody>
                      </table>
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
