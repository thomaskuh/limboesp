app.component('pageDevices', {
  templateUrl: 'app/pageDevices.html',
  bindings: {},
  controller: function(client) {
	  var ctrl = this;
	  
	  ctrl.items = [];

	  ctrl.reload = function() {
	    client.devicesGet().then((resp) => ctrl.items = resp.data);
	  };
    
    ctrl.isUpToDate = function(it) {
      return it.versionLatest && it.versionCurrent && it.versionLatest.nr == it.versionCurrent.nr;
    };	  
	  
	  ctrl.reload();
  }
});
