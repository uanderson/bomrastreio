<template>
  <div class="router-view-content">
    <div class="breadcrumbs">
      <h1>Configurações</h1>
      <ol class="breadcrumb">
        <router-link to="/" tag="li">
          <a href="/" class="text-uppercase">
            <i class="icon-home"></i> Home
          </a>
        </router-link>
        <li class="active">Configurações</li>
      </ol>
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".page-sidebar">
        <span class="sr-only">Habilita a navegação</span>
        <span class="toggle-icon">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </span>
      </button>
    </div>
    <div class="page-content-container">
      <div class="page-content-row">
        <div class="page-sidebar">
          <nav class="navbar" role="navigation">
            <ul class="nav navbar-nav margin-bottom-35">
              <router-link to="/settings/notifications" tag="li">
                <a href="/settings/notifications" class="text-uppercase">
                  <i class="icon-bell"></i> Notificações
                </a>
              </router-link>
            </ul>
          </nav>
        </div>
        <div class="page-content-col">
          <router-view></router-view>
        </div>
      </div>
    </div>
  </div>
</template>
