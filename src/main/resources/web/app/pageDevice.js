app.component('pageDevice', {
  templateUrl: 'app/pageDevice.html',
  bindings: {},
  controller: function($scope, client, tkUploadService) {
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
        tkUploadService.uploadFiles(files, '/api/device/' + ctrl.itemId + '/imageData', par);
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
