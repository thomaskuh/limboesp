app.component('pageApps', {
  templateUrl: 'app/pageApps.html',
  bindings: {},
  controller: function(client) {
	  var ctrl = this;
	  
	  ctrl.platforms = ["ESP8266", "ESP32"];
	  
	  ctrl.items = [];
	  ctrl.nu = {name: 'My Awesome App', platform: ctrl.platforms[0]};
	  
	  ctrl.reload = function() {
	    client.appsGet().then((resp) => ctrl.items = resp.data);
	  };
	  
	  ctrl.create = function() {
	    client.appCreate(ctrl.nu).then((resp) => ctrl.items.push(resp.data));
	  };

	  ctrl.reload();
  }
});
