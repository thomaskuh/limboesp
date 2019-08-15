app.component('pageApp', {
  templateUrl: 'app/pageApp.html',
  bindings: {},
  controller: function($scope, client, tkUploadService) {
	  var ctrl = this;
	  
	  ctrl.platforms = ["ESP8266", "ESP32"];
	  
	  ctrl.itemId = null;
	  ctrl.item = null;

	  ctrl.versions = [];
	  
    ctrl.$routerOnActivate = function(next, previous) {
      ctrl.itemId = next.params.appId;
      ctrl.reload();
    };
	  
	  ctrl.reload = function() {
	    client.appGet(ctrl.itemId).then((resp) => ctrl.item = resp.data);
	    client.appVersionsGet(ctrl.itemId).then((resp) => ctrl.versions = resp.data);
	  };

	  ctrl.create = function() {
	    client.appCreate(ctrl.nu).then((resp) => ctrl.items.push(resp.data));
	  };
	  
	  
    /* Functions for direct JS calls (non-angular) */ 
    ctrl.onFileSelect = function(valueFiles) {
      $scope.$apply(function() {
        var files = new Array();
        for(x in valueFiles) {
          var el = valueFiles[x];
          if(el instanceof File) {
            files.push(el);
          }
        };

        /* let upload service manage this */
        var par = new Object();
        // par.folder = ctrl.folder == null ? 0 : ctrl.folder.id;
        tkUploadService.uploadFiles(files, '/api/app/' + ctrl.itemId + '/version', par);
      });
    };
    
    ctrl.onClassicUpload = function(element) {
      ctrl.onFileSelect(element.files);
      element.value = "";
    };

    // (De)Register upload listener
    tkUploadService.registerListener(ctrl.reload);
    /* destructor */
    $scope.$on('$destroy', function iVeBeenDismissed() {
      tkUploadService.registerListener(null);
    });
	  
  }
});
