var app = tkDefaultApp();

app.factory('client', ['$http', function($http) {
	return {
		probe: () => $http.get('/api/probe'),
		
		devicesGet: () => $http.get('/api/device'),
		appsGet: () => $http.get('/api/app'),
		appGet: (appId) => $http.get('/api/app/' + appId),
		appCreate: (body) => $http.post('/api/app', body),
		appVersionsGet: (appId) => $http.get('/api/app/' + appId + '/version')
	}
}]);



app.component('app', {
	templateUrl: 'app/app.html',
	bindings: { $router: '<' },
	$routeConfig: [
		{path: '/home',   name: 'Home',   component: 'pageHome', useAsDefault: true},
		{path: '/login',  name: 'Login',   component: 'tkLoginForm'},
		{path: '/devices',  name: 'Devices',   component: 'pageDevices'},
		{path: '/apps',  name: 'Apps',   component: 'pageApps'},
		{path: '/app/:appId', name: 'App', component: 'pageApp'}
	],
	controller: function($scope, $location, $element) {
		var ctrl = this;
		ctrl.path = '';

		ctrl.toggle = function() {
			$element.find('.navbar-burger').toggleClass("is-active");
			$element.find('.navbar-menu').toggleClass("is-active");
		};
		
		$scope.$on('$routeChangeSuccess', function(scope, current, pre) {
			ctrl.path = $location.path();
		});
	}
});

