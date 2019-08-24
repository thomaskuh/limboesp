app.component('pageHome', {
  templateUrl: 'app/pageHome.html',
  bindings: {},
  controller: function(client) {
	  var ctrl = this;
	  
	  client.probe().then((resp) => {});
  }
});
