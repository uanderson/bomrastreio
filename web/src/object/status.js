class ObjectStatus {

  static get STATUSES() {
    return {
      'UNDEFINED': {
        id: 'UNDEFINED',
        description: 'Indefinido',
        labelClass: 'label-default'
      },
      'ACTION_REQUIRED': {
        id: 'ACTION_REQUIRED',
        description: 'Ação requerida',
        labelClass: 'label-primary'
      },
      'LOSS': {
        id: 'LOSS',
        description: 'Atrasado',
        labelClass: 'label-danger'
      },
      'DELIVERED': {
        id: 'DELIVERED',
        description: 'Entregue',
        labelClass: 'label-success'
      },
      'IN_TRANSIT': {
        id: 'IN_TRANSIT',
        description: 'Em trânsito',
        labelClass: 'label-info'
      },
      'DELAYED': {
        id: 'DELAYED',
        description: 'Extraviado',
        labelClass: 'label-warning'
      },
      'PENDING': {
        id: 'PENDING',
        description: 'Aguardando',
        labelClass: 'label-default'
      }
    };
  }

  static of(status) {
    return ObjectStatus.STATUSES[status];
  }

}

export default ObjectStatus;
