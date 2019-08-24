app.component('pageDevice', {
  templateUrl: 'app/pageDevice.html',
  bindings: {},
  controller: function(client) {
    var ctrl = this;
	  
    ctrl.states = ['NEW', 'APPROVED', 'BLOCKED'];
    ctrl.appNone = {id: null, name: 'NONE / DISABLED'};
    
    ctrl.dirty = false;
    
    ctrl.itemId = null;
	  ctrl.item = null;
	  ctrl.items = [];
	  
	  ctrl.apps = [];
	  
	  ctrl.$routerOnActivate = function(next, previous) {
	    ctrl.itemId = next.params.deviceId;
	    ctrl.reload();
	  };
	  
	  ctrl.setItem = function(responseWithItem) {
      ctrl.dirty = false;
      ctrl.item = responseWithItem.data;
      if(ctrl.item.app == null) {
        ctrl.item.app = ctrl.appNone;
      }
      
      client.appsGet(ctrl.item.platform).then((resp) => {
        ctrl.apps = resp.data;
        ctrl.apps.unshift(ctrl.appNone);
      });
	  };
	  
    ctrl.reload = function() {
      client.deviceGet(ctrl.itemId).then(ctrl.setItem);
    };
    
    ctrl.save = function() {
      var req = {name: ctrl.item.name, state: ctrl.item.state, appId: ctrl.item.app.id};
      client.deviceUpdate(ctrl.item.id, req).then(ctrl.setItem);
    };
    
    ctrl.undo = function() {
      ctrl.reload();
    }

    
  }
});
